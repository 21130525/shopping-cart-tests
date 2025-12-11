package productFilter

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.WebElement
import org.openqa.selenium.Keys
import java.util.Arrays
import java.text.SimpleDateFormat
import java.util.Date
import java.io.BufferedWriter
import com.kms.katalon.core.exception.StepFailedException

class FilterKeywords {

	static TestObject xpath(String x) {
		TestObject to = new TestObject()
		to.addProperty("xpath", ConditionType.EQUALS, x)
		return to
	}

	static TestObject css(String c) {
		TestObject to = new TestObject()
		to.addProperty("css", ConditionType.EQUALS, c)
		return to
	}

	static void closePopupIfExists() {
		// Tạo TestObject tạm cho nút close popup
		TestObject closePopup = new TestObject('tmpClosePopup')
		closePopup.addProperty('css', com.kms.katalon.core.testobject.ConditionType.EQUALS, '.close')

		try {
			// Chờ tối đa 5 giây để popup visible
			if (WebUI.waitForElementVisible(closePopup, 5, FailureHandling.OPTIONAL)) {
				WebUI.click(closePopup)
				println("✅ Popup closed successfully")
			} else {
				println("ℹ️ No popup to close")
			}
		} catch (StepFailedException e) {
			// Nếu click fail, bỏ qua, không fail test
			println("⚠️ Popup exists but not interactable, skipping close")
		}
	}

	static void closeAntsBanner() {
		TestObject banner = css("#antsomi-slidedown-container")
		if (WebUI.verifyElementPresent(banner, 3, FailureHandling.OPTIONAL)) {
			try {
				WebUI.executeJavaScript("document.getElementById('antsomi-slidedown-container').style.display='none';", null)
			} catch (Exception e) {}
			WebUI.delay(0.5)
		}
	}

	static boolean safeScrollAndClick(TestObject to) {
		try {
			WebUI.scrollToElement(to, 3)
			WebUI.delay(0.3)
			WebUI.click(to)
			WebUI.delay(0.6)
			return true
		} catch (Exception e) {
			try {
				WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUI.findWebElement(to)))
				WebUI.delay(0.6)
				return true
			} catch (Exception ex) {
				return false
			}
		}
	}

	static boolean clickFilter(String group, String option) {
		// Size special handling
		if (group.toLowerCase().contains("kích") || group.toLowerCase().contains("size")) {
			List<TestObject> tries = [
				xpath("//input[@name='size-filter' and (translate(@value,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz') = '${option.toLowerCase()}')]"),
				xpath("//ul//li//label[normalize-space(text())='${option}']"),
				xpath("//ul//li//input[@type='checkbox' and @value='${option}']")
			]
			for (TestObject to : tries) {
				if (WebUI.verifyElementPresent(to, 2, FailureHandling.OPTIONAL)) {
					if (safeScrollAndClick(to)) return true
				}
			}
			println("WARN – Không tìm thấy SIZE: ${option}")
			return false
		}

		List<TestObject> tries = [
			xpath("//label[contains(normalize-space(.),'${option}')]"),
			xpath("//span[contains(normalize-space(.),'${option}')]"),
			xpath("//input[@type='checkbox' and following-sibling::label[contains(normalize-space(.),'${option}')]]"),
			xpath("//label//span[contains(normalize-space(.),'${option}')]")
		]
		for (TestObject to : tries) {
			if (WebUI.verifyElementPresent(to, 2, FailureHandling.OPTIONAL)) {
				if (safeScrollAndClick(to)) return true
			}
		}
		println("WARN – Không tìm thấy filter option: ${option} (group: ${group})")
		return false
	}
	static void removeFilter() {
		TestObject removeAll = css(".filter-tags.remove-all, .btn-clear-filters, .clear-all-filters, .filter-clear")

		// Kiểm tra element tồn tại trước khi thao tác
		if (WebUI.verifyElementPresent(removeAll, 1, FailureHandling.OPTIONAL)) {
			try {
				// Chỉ click nếu element thật sự visible
				if (WebUI.verifyElementVisible(removeAll, FailureHandling.OPTIONAL)) {
					try {
						WebUI.click(removeAll)
					} catch (Exception e) {
						// fallback bằng JS click nếu click thường fail
						try {
							WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUI.findWebElement(removeAll)))
						} catch (ignored) {}
					}
				}
			} catch (Exception ignored) {}
		}
	}


	//	static void removeFilter() {
	//		TestObject removeAll = css(".filter-tags.remove-all, .btn-clear-filters, .clear-all-filters, .filter-clear")
	//		if (WebUI.verifyElementPresent(removeAll, 1, FailureHandling.OPTIONAL) && WebUI.verifyElementVisible(removeAll, FailureHandling.OPTIONAL)) {
	//			try {
	//				WebUI.click(removeAll)
	//			} catch (Exception e) {
	//				try {
	//					WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUI.findWebElement(removeAll)))
	//				} catch (ignored) {}
	//			}
	//			return
	//		}
	//	}

	static boolean isClearButtonVisible() {
		TestObject clearBtn = css(".filter-tags.remove-all, .btn-clear-filters, .filter-clear")
		return WebUI.verifyElementPresent(clearBtn, 1, FailureHandling.OPTIONAL) && WebUI.verifyElementVisible(clearBtn, FailureHandling.OPTIONAL)
	}

	static void openProductListPage() {
		TestObject menuAo = xpath("//a[contains(normalize-space(.),'Áo') and contains(@href, '/collections')]")
		if (WebUI.verifyElementPresent(menuAo, 5, FailureHandling.OPTIONAL)) {
			WebUI.scrollToElement(menuAo, 10)
			WebUI.click(menuAo)
			WebUI.delay(2)
		} else {
			WebUI.navigateToUrl("https://aristino.com/collections")
			WebUI.delay(2)
		}
	}
}

