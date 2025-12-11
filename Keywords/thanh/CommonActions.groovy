package thanh;



import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import java.util.Arrays

public class CommonActions {

	@Keyword
	def static TestObject makeTo(String xpath) {
		TestObject to = new TestObject()
		to.addProperty("xpath", ConditionType.EQUALS, xpath)
		return to
	}

	@Keyword
	def static void openBrowserAndNavigate() {
		Map<String, Object> chromePrefs = new HashMap<String, Object>()
		chromePrefs.put("profile.default_content_setting_values.notifications", 2)
		RunConfiguration.setWebDriverPreferencesProperty("prefs", chromePrefs)
		RunConfiguration.setWebDriverPreferencesProperty("args", ["--disable-notifications"])

		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl('https://aristino.com/')
		WebUI.waitForPageLoad(10)
	}

	@Keyword
	def static void smartClick(TestObject to) {
		try {
			WebUI.click(to)
			println("LOG: Click success")
		} catch (Exception e) {
			println("WARNING: Click intercepted. Using JS Click...")
			try {
				WebElement element = WebUiCommonHelper.findWebElement(to, 5)
				WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(element))
				println("LOG: JS Click success")
			} catch (Exception ex) {
				println("FAIL: Unable to click element.")
			}
		}
	}

	@Keyword
	def static void scrollFullPage() {
		WebUI.executeJavaScript("window.scrollTo({top: document.body.scrollHeight, behavior: 'smooth'});", null)
		WebUI.delay(4)
		WebUI.executeJavaScript("window.scrollTo({top: 0, behavior: 'smooth'});", null)
		WebUI.delay(3)
	}

	@Keyword
	def static void handlePopup() {
		println("--- CHECKING POPUP ---")
		// Giảm thời gian chờ xuống 2s để test chạy nhanh hơn
		if (!WebUI.waitForElementPresent(makeTo("//div[contains(@class, 'template-wrapper')] | //iframe"), 2, FailureHandling.OPTIONAL)) {
			return
		}

		WebDriver driver = DriverFactory.getWebDriver()
		boolean isPopupFound = false
		String xpathClose = "//div[contains(@class, 'template-close')] | //*[contains(@class, 'template-close')] | //div[contains(@class, 'boWLNi')]"
		String xpathOverlay = "//div[contains(@class, 'template-wrapper')]"

		// 1. Scan Buttons
		List<WebElement> closeButtons = driver.findElements(By.xpath(xpathClose))
		for (WebElement btn : closeButtons) {
			if (btn.isDisplayed()) {
				try {
					WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(btn))
					isPopupFound = true; return
				} catch (Exception e) {}
			}
		}
		// 2. Remove Overlay
		if (!isPopupFound) {
			List<WebElement> overlays = driver.findElements(By.xpath(xpathOverlay))
			for (WebElement overlay : overlays) {
				if (overlay.isDisplayed()) {
					((JavascriptExecutor) driver).executeScript("arguments[0].remove();", overlay)
					isPopupFound = true
				}
			}
		}
		// 3. Iframe
		if (!isPopupFound) {
			List<WebElement> iframes = driver.findElements(By.tagName("iframe"))
			for (int i = 0; i < iframes.size(); i++) {
				try {
					driver.switchTo().frame(i)
					List<WebElement> iframeBtns = driver.findElements(By.xpath(xpathClose))
					if (iframeBtns.size() > 0 && iframeBtns.get(0).isDisplayed()) {
						WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(iframeBtns.get(0)))
						driver.switchTo().defaultContent(); return
					}
					driver.switchTo().defaultContent()
				} catch (Exception e) {
					driver.switchTo().defaultContent()
				}
			}
		}
	}

	@Keyword
	def static void runFilterScenario(String categoryName, String xpathMenu, String xpathFilter) {
		println("\n=== TEST CATEGORY: " + categoryName + " ===")
		handlePopup()

		println("STEP 1: Click menu " + categoryName)
		smartClick(makeTo(xpathMenu))
		WebUI.waitForPageLoad(10)
		handlePopup()

		println("STEP 2: Apply Filter...")
		TestObject filterItem = makeTo(xpathFilter)
		WebUI.scrollToElement(filterItem, 3)
		smartClick(filterItem)
		WebUI.delay(2)

		scrollFullPage()

		println("STEP 3: Uncheck Filter...")
		WebUI.scrollToElement(filterItem, 3)
		smartClick(filterItem)
		WebUI.delay(2)
	}
}