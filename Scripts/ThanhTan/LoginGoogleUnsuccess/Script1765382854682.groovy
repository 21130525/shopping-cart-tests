import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.util.KeywordUtil

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import java.time.Duration
import java.util.Collections

// ============================================================
// PART 1: SETUP ANTI-BOT CHROME (Giữ nguyên theo yêu cầu)
// ============================================================

ChromeOptions options = new ChromeOptions()
options.addArguments("--disable-blink-features=AutomationControlled")
options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
options.addArguments("--start-maximized")

// Khởi tạo Driver
WebDriver driver = new ChromeDriver(options)
DriverFactory.changeWebDriver(driver)

// ============================================================
// PART 2: ACTIONS
// ============================================================

try {
    WebUI.navigateToUrl("https://aristino.com/")

    // --- [FIX 1] ẨN MENU DROP-DOWN (Tránh che nút Login) ---
    try {
        WebUI.executeJavaScript("document.querySelectorAll('.header-menu--mega').forEach(function(el) { el.style.display = 'none !important'; });", null)
    } catch (Exception e) {}
    
    // --- [FIX 2] XỬ LÝ POPUP QUẢNG CÁO ---
    TestObject closePopupBtn = new TestObject("closePopupBtn")
    closePopupBtn.addProperty("xpath", ConditionType.EQUALS,
        "//button[contains(@class,'CloseButtonWrapper') or contains(text(),'Bỏ qua')]")

    if (WebUI.waitForElementPresent(closePopupBtn, 8, FailureHandling.OPTIONAL)) {
        WebUI.delay(1)
        WebUI.click(closePopupBtn)
        KeywordUtil.logInfo(">>> Đã đóng popup quảng cáo.")
    }

    // --- CLICK LOGIN ---
    WebUI.waitForElementClickable(findTestObject("Object Repository/ThanhTan/midterm/LoginGoogle/icon_login"), 10)
    WebUI.click(findTestObject("Object Repository/ThanhTan/midterm/LoginGoogle/icon_login"))
    
    WebUI.delay(1)
    
    WebUI.waitForElementClickable(findTestObject("Object Repository/ThanhTan/midterm/LoginGoogle/btn_google"), 10)
    WebUI.click(findTestObject("Object Repository/ThanhTan/midterm/LoginGoogle/btn_google"))

    // ====== Switch Pop-up Google ======
    WebUI.switchToWindowIndex(1)
    
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20))

    // ===== NHẬP EMAIL =====
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email' or @name='identifier']")))
        .sendKeys("nttan123test@gmail.com")

    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='identifierNext']//button | //span[text()='Next' or text()='Tiếp theo']/parent::button")))
        .click()

    WebUI.delay(2)

    // ===== NHẬP SAI PASSWORD =====
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password' or @name='Passwd']")))
        .sendKeys("MatKhauSai123!!!")

    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='passwordNext']//button | //span[text()='Next' or text()='Tiếp theo']/parent::button")))
        .click()

    WebUI.delay(2)

    // ============================================================
    // 📌 VERIFY ERROR MESSAGE (Dựa trên ảnh bạn gửi)
    // ============================================================

    // List các XPath có thể chứa lỗi (Ưu tiên cái trong ảnh của bạn)
    String[] errorXpaths = [
        "//span[contains(text(),'Wrong password')]",       // Text chuẩn trong ảnh
        "//div[contains(text(),'Wrong password')]",        // Trường hợp Google đổi thẻ div
        "//span[contains(text(),'Sai mật khẩu')]",         // Tiếng Việt
        "//div[@aria-live='assertive']",                   // Thẻ chứa lỗi chung của Google
        "//span[contains(text(),'Try again')]"             // Text phụ trong ảnh
    ]

    boolean foundError = false

    for (String xp : errorXpaths) {
        try {
            // Wait nhẹ 3s để tìm lỗi
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3))
            def el = shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xp)))
            
            if (el != null && el.isDisplayed()) {
                KeywordUtil.markPassed("✔ TEST PASSED: Đã hiện thông báo lỗi đúng như mong đợi: " + el.getText())
                foundError = true
                
                // --- XỬ LÝ KHI PASS: Đóng Popup & Về trang chính ---
                driver.close() // Đóng cửa sổ Google Popup
                WebUI.switchToWindowIndex(0) // Quay về Aristino
                break
            }
        } catch (ignored) {}
    }

    if (!foundError) {
        KeywordUtil.markFailed("✘ FAIL — Không tìm thấy dòng chữ đỏ 'Wrong password'")
        // Nếu fail thì vẫn đóng popup để dọn dẹp (Optional)
        driver.close()
        WebUI.switchToWindowIndex(0)
    }

} catch (Exception e) {
    KeywordUtil.markFailed("❌ ERROR: " + e.message)
} finally {
    // Luôn đóng trình duyệt cuối cùng
    WebUI.closeBrowser()
}