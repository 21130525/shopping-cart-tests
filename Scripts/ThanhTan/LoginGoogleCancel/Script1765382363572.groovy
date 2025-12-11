import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.util.KeywordUtil

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.util.Collections

// ============================================================
// PART 1: SETUP ANTI-BOT
// ============================================================

ChromeOptions options = new ChromeOptions()
options.addArguments("--disable-blink-features=AutomationControlled")
options.addArguments("--start-maximized")
options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))

WebDriver driver = new ChromeDriver(options)
DriverFactory.changeWebDriver(driver)

// ============================================================
// PART 2: ACTIONS
// ============================================================

try {
    WebUI.navigateToUrl("https://aristino.com/")

    // --- [FIX 1] ẨN MENU DROP-DOWN & HEADER DÍNH ---
    try {
        WebUI.executeJavaScript("document.querySelectorAll('.header-menu--mega, .header-sticky, .header-mobile').forEach(function(el) { el.style.setProperty('display', 'none', 'important'); });", null)
        KeywordUtil.logInfo(">>> Đã ẩn menu dropdown thành công.")
    } catch (Exception e) {}
    // ----------------------------------------------

    // --- [FIX 2] XỬ LÝ POPUP QUẢNG CÁO ---
    TestObject closePopupBtn = new TestObject("closePopupBtn")
    closePopupBtn.addProperty("xpath", ConditionType.EQUALS,
            "//button[contains(@class,'CloseButtonWrapper') or contains(text(),'Bỏ qua')]")

    if (WebUI.waitForElementPresent(closePopupBtn, 5, FailureHandling.OPTIONAL)) {
        WebUI.delay(1)
        WebUI.click(closePopupBtn)
        KeywordUtil.logInfo(">>> Đã đóng popup quảng cáo.")
    }

    // 2.2 Click login → chọn Google
    WebUI.waitForElementClickable(findTestObject("Object Repository/ThanhTan/midterm/LoginGoogle/icon_login"), 10)
    WebUI.click(findTestObject("Object Repository/ThanhTan/midterm/LoginGoogle/icon_login"))

    WebUI.delay(1)

    WebUI.waitForElementClickable(findTestObject("Object Repository/ThanhTan/midterm/LoginGoogle/btn_google"), 10)
    WebUI.click(findTestObject("Object Repository/ThanhTan/midterm/LoginGoogle/btn_google"))

    // --- [QUAN TRỌNG] CHỜ POPUP BẬT LÊN ---
    WebUI.delay(3)
    // --------------------------------------

    // 2.3 Switch to Google popup
    WebUI.switchToWindowIndex(1)
    WebUI.delay(2)

    String popupTitle = WebUI.getWindowTitle()
    KeywordUtil.logInfo(">>> Đang ở popup Google: " + popupTitle)

    // ❗ Cancel Login: Đóng cửa sổ Google (Giả lập người dùng tắt popup)
    driver.close()

    // ============================================================
    // PART 3: VERIFICATION
    // ============================================================

    WebUI.switchToWindowIndex(0)
    WebUI.delay(1)

    // Verify URL đúng (Vẫn ở trang gốc)
    String currentUrl = WebUI.getUrl()
    if (currentUrl.contains("aristino.com")) {
        KeywordUtil.markPassed("PASS: Đã quay về trang chủ Aristino sau khi cancel login.")
    } else {
        KeywordUtil.markFailed("FAIL: Browser không quay lại Aristino, URL hiện tại: " + currentUrl)
    }

    // Verify icon login vẫn hiện (vì chưa đăng nhập)
    boolean loginVisible = WebUI.verifyElementPresent(
            findTestObject("Object Repository/ThanhTan/midterm/LoginGoogle/icon_login"),
            5,
            FailureHandling.OPTIONAL
    )

    if (loginVisible) {
        KeywordUtil.markPassed("PASS: Cancel Google login thành công — icon login vẫn hiển thị.")
    } else {
        KeywordUtil.markWarning("⚠ WARNING: Không thấy icon login — UI có thể thay đổi.")
    }

} catch (Exception e) {
    KeywordUtil.markFailed("❌ ERROR: " + e.getMessage())
}

// ============================================================
// PART 4: TEARDOWN
// ============================================================

WebUI.closeBrowser()