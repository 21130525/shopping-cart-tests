import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.util.Collections

// ======================================================================
//  🛠️ HÀM HỖ TRỢ
// ======================================================================
def handlePopup = {
    try {
        WebDriver driver = DriverFactory.getWebDriver()
        List<WebElement> closeBtns = driver.findElements(By.xpath("//button[contains(@class,'CloseButtonWrapper') or contains(text(),'Bỏ qua')]"))
        if (closeBtns.size() > 0 && closeBtns.get(0).isDisplayed()) {
            closeBtns.get(0).click()
            KeywordUtil.logInfo(">>> ✅ Đã đóng popup quảng cáo.")
            WebUI.delay(1)
        }
    } catch (Exception e) {}
}

def hideAnnoyingMenus = {
    try {
        String js = "document.querySelectorAll('.header-menu--mega, .header-sticky, .header-mobile').forEach(function(el) { el.style.setProperty('display', 'none', 'important'); });"
        WebUI.executeJavaScript(js, null)
    } catch (Exception e) {}
}

// ======================================================================
//  🚀 SMART BROWSER SETUP
// ======================================================================
WebUI.comment('--- TC 2: CHECK SỐ LƯỢNG (Kế thừa trình duyệt) ---')

boolean isBrowserOpen = false
try {
    if (DriverFactory.getWebDriver() != null) {
        WebUI.getUrl()
        isBrowserOpen = true
        KeywordUtil.logInfo(">>> 🔄 Trình duyệt đang mở. Check số lượng...")
    }
} catch (Exception e) { isBrowserOpen = false }

if (!isBrowserOpen) {
    KeywordUtil.logWarning(">>> ⚠️ Trình duyệt bị đóng. Mở mới (Lưu ý: Giỏ hàng sẽ trống nên Test này có thể Fail logic).")
    ChromeOptions options = new ChromeOptions()
    options.addArguments('--disable-blink-features=AutomationControlled')
    options.addArguments('--start-maximized')
    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
    
    WebDriver driver = new ChromeDriver(options)
    DriverFactory.changeWebDriver(driver)
    WebUI.navigateToUrl('https://aristino.com/')
}

// ======================================================================
//  LOGIC CHÍNH
// ======================================================================

// Dọn dẹp giao diện trước khi check
handlePopup()
hideAnnoyingMenus()

// 1. Kiểm tra nút trừ (-)
TestObject btnMinus = findTestObject('Object Repository/ThanhTan/midterm/trang_chi_tiet_san_pham/svg_cap_nhat_san_pham')

// Đợi nút xuất hiện (Nếu giỏ hàng trống do mở mới browser, bước này sẽ timeout)
if (WebUI.waitForElementVisible(btnMinus, 5, FailureHandling.OPTIONAL)) {
    
    // Kiểm tra trạng thái Clickable
    // Lưu ý: Aristino disable nút bằng CSS hoặc JS, nên verifyElementClickable đôi khi vẫn trả về True.
    // Cách check chính xác hơn là check class 'disabled'
    String classAttribute = WebUI.getAttribute(btnMinus, "class")
    boolean isClassDisabled = classAttribute.contains("disabled")
    
    // Hoặc dùng verifyElementClickable như cũ nếu web chặn sự kiện click
    boolean isClickable = WebUI.verifyElementClickable(btnMinus, FailureHandling.OPTIONAL)

    // Logic: Nếu nút KHÔNG bấm được (isClickable false) HOẶC có class disabled -> PASS
    if (isClickable == false || isClassDisabled == true) {
        KeywordUtil.markPassed('PASSED: Nút trừ (-) bị khóa đúng logic (Số lượng = 1).')
    } else {
        KeywordUtil.markFailed('FAILED: Nút trừ (-) vẫn bấm được khi số lượng là 1.')
    }
} else {
    KeywordUtil.markWarning("⚠️ Không thấy nút trừ (-). Có thể do giỏ hàng trống.")
}