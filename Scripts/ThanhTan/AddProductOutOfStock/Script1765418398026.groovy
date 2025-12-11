import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import java.util.Arrays
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
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
WebUI.comment('--- TC 5: HẾT HÀNG (Last Case) ---')

boolean isBrowserOpen = false
try {
    if (DriverFactory.getWebDriver() != null) {
        WebUI.getUrl()
        isBrowserOpen = true
        KeywordUtil.logInfo(">>> 🔄 Trình duyệt đang mở. Chuyển hướng trang...")
    }
} catch (Exception e) { isBrowserOpen = false }

if (!isBrowserOpen) {
    KeywordUtil.logInfo(">>> 🚀 Mở trình duyệt mới cho Test Case cuối...")
    ChromeOptions options = new ChromeOptions()
    options.addArguments('--disable-blink-features=AutomationControlled')
    options.addArguments('--start-maximized')
    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
    
    WebDriver driver = new ChromeDriver(options)
    DriverFactory.changeWebDriver(driver)
}

// ======================================================================
//  LOGIC CHÍNH
// ======================================================================

// 1. Navigate trực tiếp đến trang chủ (hoặc URL sản phẩm cụ thể nếu có)
WebUI.navigateToUrl('https://aristino.com/') 
WebUI.maximizeWindow()

// Dọn dẹp
handlePopup()
hideAnnoyingMenus()

// Vào sản phẩm (Bạn có thể thay bước này bằng Navigate URL trực tiếp vào sản phẩm hết hàng để nhanh hơn)
WebUI.click(findTestObject('Object Repository/ThanhTan/midterm/trang_chu/link_trang_phuc'))
WebUI.delay(1)

handlePopup()
hideAnnoyingMenus()

// Chọn sản phẩm (Cần đảm bảo đây là Object của sản phẩm HẾT HÀNG thực tế)
TestObject productObject = findTestObject('Object Repository/ThanhTan/midterm/trang_hien_thi_danh_muc_trang_phuc/Nam_Xanh_Ru_Aristino_Regular_505514') 
WebUI.waitForElementPresent(productObject, 10)

WebElement elementProduct = WebUiCommonHelper.findWebElement(productObject, 30)
WebUI.executeJavaScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'});", Arrays.asList(elementProduct))
WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(elementProduct))

// Dọn dẹp tại trang chi tiết
WebUI.delay(2)
handlePopup()
hideAnnoyingMenus()

// 2. Kiểm tra Class của nút thêm
TestObject btnAddToCart = findTestObject('Object Repository/ThanhTan/midterm/trang_chi_tiet_san_pham/btn_them_vao_gio')
WebUI.waitForElementPresent(btnAddToCart, 10)

// Cuộn tới nút
WebElement elementBtn = WebUiCommonHelper.findWebElement(btnAddToCart, 10)
WebUI.executeJavaScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'});", Arrays.asList(elementBtn))

String buttonClasses = WebUI.getAttribute(btnAddToCart, 'class')
boolean isDisabled = buttonClasses.contains("disabled") || buttonClasses.contains("disable")

// Logic verify (Tùy thuộc sản phẩm bạn chọn là Hết hay Còn)
if (isDisabled) {
    KeywordUtil.markPassed('PASSED: Nút mua bị khóa do hết hàng.')
} else {
    // Nếu sản phẩm CÒN HÀNG thì logic này là Fail, hoặc bạn đổi lại logic để test "Mua được"
    KeywordUtil.markFailed('FAILED: Nút mua vẫn Active (Sản phẩm này còn hàng).')
}

// 3. ĐÓNG TRÌNH DUYỆT (KẾT THÚC SUITE)
WebUI.closeBrowser()