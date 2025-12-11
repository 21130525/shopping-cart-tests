import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import java.util.Arrays
import internal.GlobalVariable
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import java.util.Collections
import org.openqa.selenium.By

// ======================================================================
//  🛠️ HÀM HỖ TRỢ (Functions)
// ======================================================================

// 1. Hàm đóng Popup quảng cáo (Đã tối ưu để không báo lỗi đỏ trong log)
def handlePopup = {
    try {
        // Dùng Selenium thuần để check tồn tại trước, tránh WebUI báo lỗi đỏ lòm
        WebDriver driver = DriverFactory.getWebDriver()
        List<WebElement> closeBtns = driver.findElements(By.xpath("//button[contains(@class,'CloseButtonWrapper') or contains(text(),'Bỏ qua') or contains(@class,'close') or contains(@id,'close')]"))
        
        if (closeBtns.size() > 0) {
            WebElement btn = closeBtns.get(0)
            if (btn.isDisplayed()) {
                btn.click()
                KeywordUtil.logInfo(">>> ✅ Đã đóng popup quảng cáo.")
                WebUI.delay(1)
            }
        }
    } catch (Exception e) {}
}

// 2. Hàm ẩn Menu trôi nổi
def hideAnnoyingMenus = {
    try {
        String js = "document.querySelectorAll('.header-menu--mega, .header-sticky, .header-mobile').forEach(function(el) { el.style.setProperty('display', 'none', 'important'); });"
        WebUI.executeJavaScript(js, null)
    } catch (Exception e) {}
}

// 3. Hàm CHỌN SIZE (Quan trọng để mua được hàng)
def selectFirstAvailableSize = {
    try {
        KeywordUtil.logInfo(">>> Đang tìm Size để chọn...")
        // Tìm size nào chưa hết hàng (không có class soldout/deactive)
        // XPath này tìm các ô size phổ biến trên web thời trang
        String xpathSize = "//div[contains(@class,'swatch-element') and not(contains(@class,'soldout'))]//label | //div[contains(@class,'size')]//span[not(contains(@class,'disabled'))]"
        
        TestObject sizeObj = new TestObject("sizeObj")
        sizeObj.addProperty("xpath", ConditionType.EQUALS, xpathSize)
        
        if(WebUI.waitForElementPresent(sizeObj, 5, FailureHandling.OPTIONAL)){
            // Click vào size đầu tiên tìm thấy
            WebElement sizeEl = WebUiCommonHelper.findWebElement(sizeObj, 5)
            sizeEl.click()
            KeywordUtil.logInfo(">>> ✅ Đã chọn Size: " + sizeEl.getText())
            WebUI.delay(1)
        } else {
            KeywordUtil.logWarning("⚠️ Không tìm thấy ô chọn Size hoặc đã hết Size!")
        }
    } catch (Exception e) {
        KeywordUtil.logWarning("⚠️ Lỗi khi chọn size: " + e.getMessage())
    }
}

// ======================================================================
//  🚀 PART 1: SMART BROWSER SETUP
// ======================================================================

boolean isBrowserOpen = false
try {
    if (DriverFactory.getWebDriver() != null) {
        WebUI.getUrl() // Check kết nối
        isBrowserOpen = true
        KeywordUtil.logInfo(">>> 🔄 Trình duyệt đã mở sẵn. Tái sử dụng...")
    }
} catch (Exception e) { isBrowserOpen = false }

if (!isBrowserOpen) {
    KeywordUtil.logInfo(">>> 🚀 Khởi tạo Chrome Anti-Bot mới...")
    ChromeOptions options = new ChromeOptions()
    options.addArguments('--disable-blink-features=AutomationControlled')
    options.addArguments('--start-maximized')
    options.addArguments('--disable-notifications')
    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
    
    WebDriver driver = new ChromeDriver(options)
    DriverFactory.changeWebDriver(driver)
    WebUI.navigateToUrl('https://aristino.com/')
} else {
    String currentUrl = WebUI.getUrl()
    if (!currentUrl.contains("aristino.com")) { WebUI.navigateToUrl('https://aristino.com/') }
}

WebUI.maximizeWindow()

// ======================================================================
//  🛒 PART 2: MAIN FLOW
// ======================================================================

handlePopup()
hideAnnoyingMenus()

// 1. Vào trang danh mục
WebUI.verifyElementPresent(findTestObject('Object Repository/ThanhTan/midterm/trang_chu/link_trang_phuc'), 10, FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('Object Repository/ThanhTan/midterm/trang_chu/link_trang_phuc'))
WebUI.delay(1)

handlePopup()
hideAnnoyingMenus()

// 2. Vào chi tiết sản phẩm
TestObject productObject = findTestObject('Object Repository/ThanhTan/midterm/trang_hien_thi_danh_muc_trang_phuc/Nam_Xanh_Ru_Aristino_Regular_505514')
WebUI.waitForElementPresent(productObject, 10)

WebElement elementProduct = WebUiCommonHelper.findWebElement(productObject, 30)
WebUI.executeJavaScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'});", Arrays.asList(elementProduct))
WebUI.delay(1)

GlobalVariable.TEN_SAN_PHAM = WebUI.getText(productObject)
WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(elementProduct))

WebUI.delay(2) // Đợi trang chi tiết load
handlePopup()
hideAnnoyingMenus()

// --- [BƯỚC MỚI] CHỌN SIZE TRƯỚC KHI MUA ---
selectFirstAvailableSize()
// ------------------------------------------

// 3. Thêm vào giỏ hàng
TestObject btnAddToCart = findTestObject('Object Repository/ThanhTan/midterm/trang_chi_tiet_san_pham/btn_them_vao_gio')
WebUI.verifyElementPresent(btnAddToCart, 10, FailureHandling.STOP_ON_FAILURE)

WebElement elementBtnAdd = WebUiCommonHelper.findWebElement(btnAddToCart, 30)
WebUI.executeJavaScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'});", Arrays.asList(elementBtnAdd))
WebUI.delay(1)

WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(elementBtnAdd))

// 4. Verify Popup
// Tăng thời gian chờ lên 10s vì popup giỏ hàng có thể load chậm
boolean isCartPopupOpen = WebUI.verifyElementVisible(findTestObject('Object Repository/ThanhTan/midterm/trang_chi_tiet_san_pham/icon_dong_gio_hang'), FailureHandling.OPTIONAL)

if (!isCartPopupOpen) {
    // Nếu chưa thấy, chờ thêm 3s nữa rồi check lại (Double check)
    WebUI.delay(3)
    isCartPopupOpen = WebUI.verifyElementVisible(findTestObject('Object Repository/ThanhTan/midterm/trang_chi_tiet_san_pham/icon_dong_gio_hang'), FailureHandling.OPTIONAL)
}

if (isCartPopupOpen) {
    KeywordUtil.markPassed('TEST PASSED: Popup giỏ hàng đã hiện.')
} else {
    // Check xem có thông báo lỗi "Vui lòng chọn size" không
    if(WebUI.verifyTextPresent("Vui lòng chọn", false, FailureHandling.OPTIONAL)){
        KeywordUtil.markFailed('FAILED: Web yêu cầu chọn Size nhưng script chưa chọn được.')
    } else {
        KeywordUtil.markFailed('FAILED: Không thấy popup giỏ hàng.')
    }
}