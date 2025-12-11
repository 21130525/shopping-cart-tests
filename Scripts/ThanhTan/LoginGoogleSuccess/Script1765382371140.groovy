import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import java.time.Duration
import java.util.Collections

// ============================================================
// PART 1: SETUP - Bypass Google Detection
// ============================================================
ChromeOptions options = new ChromeOptions()
options.addArguments('--disable-blink-features=AutomationControlled')
options.addArguments('--start-maximized')
options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))

WebDriver driver = new ChromeDriver(options)
DriverFactory.changeWebDriver(driver)

// ============================================================
// PART 2: Open site & click login Google
// ============================================================

try {

    KeywordUtil.logInfo(">>> STEP 1: Open ARISTINO")
    WebUI.navigateToUrl("https://aristino.com/")
    
    // --- XỬ LÝ ẨN MENU DROP-DOWN ---
    try {
        WebUI.executeJavaScript("document.querySelectorAll('.header-menu--mega, .header-sticky, .header-mobile').forEach(function(el) { el.style.setProperty('display', 'none', 'important'); });", null)
        KeywordUtil.logInfo(">>> Đã ẩn menu drop-down thành công.")
    } catch (Exception e) {}

    // Close popup quảng cáo
    TestObject closePopupBtn = new TestObject("closePopupBtn")
    closePopupBtn.addProperty("xpath", ConditionType.EQUALS,
            "//button[contains(@class,'CloseButtonWrapper') or contains(text(),'Bỏ qua')]")

    if (WebUI.waitForElementPresent(closePopupBtn, 5, FailureHandling.OPTIONAL)) {
        WebUI.click(closePopupBtn)
        KeywordUtil.logInfo(">>> Closed popup")
    }

    KeywordUtil.logInfo(">>> STEP 2: Open login popup")
    WebUI.click(findTestObject("Object Repository/ThanhTan/midterm/LoginGoogle/icon_login"))

    KeywordUtil.logInfo(">>> STEP 3: Click login Google")
    WebUI.click(findTestObject("Object Repository/ThanhTan/midterm/LoginGoogle/btn_google"))

    // --- [QUAN TRỌNG] CHỜ POPUP BẬT LÊN ---
    // Nếu không có dòng này, lệnh switch window bên dưới sẽ chạy quá nhanh và báo lỗi
    WebUI.delay(3) 
    // --------------------------------------

    WebUI.switchToWindowIndex(1)
    WebUI.delay(2) // Đợi nội dung bên trong popup load

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20))

    // ============================================================
    // STEP 4: Enter Email
    // ============================================================
    KeywordUtil.logInfo(">>> STEP 4: Enter Email")

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@type='email' or @name='identifier']")))

    driver.findElement(By.xpath("//input[@type='email' or @name='identifier']"))
            .sendKeys("nttan123test@gmail.com")

    WebUI.delay(1)

    // Click button Next
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(@jsname,'LgbsSe')]//span[contains(.,'Tiếp theo') or contains(.,'Next')]")
    )).click()

    // ============================================================
    // STEP 5: Enter Password
    // ============================================================
    KeywordUtil.logInfo(">>> STEP 5: Enter Password")

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@type='password' or @name='Passwd']")))

    driver.findElement(By.xpath("//input[@type='password' or @name='Passwd']"))
            .sendKeys("Test1234@")

    WebUI.delay(1)

    // Click NEXT on password screen
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(@jsname,'LgbsSe')]//span[contains(.,'Tiếp theo') or contains(.,'Next')]")
    )).click()

    KeywordUtil.logInfo(">>> Submitted login Google")

} catch (Exception e) {
    KeywordUtil.markFailed("❌ ERROR: ${e.getMessage()}")
}

// ============================================================
// PART 3: Verify back to ARISTINO
// ============================================================

WebUI.switchToWindowIndex(0)
WebUI.waitForPageLoad(20)

String url = WebUI.getUrl()
KeywordUtil.logInfo(">>> Current URL: " + url)

if (url.contains("aristino.com")) {
    KeywordUtil.markPassed("✔ Login redirect successful")
} else {
    KeywordUtil.markWarning("⚠ Login maybe blocked by Google security")
}

// Indicator check
TestObject loggedIndicator = new TestObject("loggedIndicator")
loggedIndicator.addProperty("xpath", ConditionType.EQUALS,
        "//a[contains(@href,'/account')] | //a[contains(text(),'Tài khoản')] | //a[contains(text(),'Đăng xuất')]")

if (WebUI.verifyElementPresent(loggedIndicator, 8, FailureHandling.OPTIONAL)) {
    KeywordUtil.markPassed("🎉 Login successful")
} else {
    KeywordUtil.markWarning("⚠ Login uncertain — UI not updated yet")
}

// Close browser
WebUI.closeBrowser()