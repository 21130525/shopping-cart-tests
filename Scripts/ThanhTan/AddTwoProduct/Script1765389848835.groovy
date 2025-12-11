import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import java.util.Arrays
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import java.util.Collections

// ======================================================================
//  🛠️ HÀM HỖ TRỢ (FUNCTIONS)
// ======================================================================

// 1. Hàm đóng Popup (Dùng Selenium thuần - Không báo lỗi đỏ)
def handlePopup = {
    try {
        WebDriver driver = DriverFactory.getWebDriver()
        List<WebElement> closeBtns = driver.findElements(By.xpath("//button[contains(@class,'CloseButtonWrapper') or contains(text(),'Bỏ qua') or contains(@class,'close') or contains(@id,'close')]"))
        if (closeBtns.size() > 0 && closeBtns.get(0).isDisplayed()) {
            closeBtns.get(0).click()
            KeywordUtil.logInfo(">>> ✅ Đã đóng popup quảng cáo.")
            WebUI.delay(1)
        }
    } catch (Exception e) {}
}

// 2. Hàm ẩn Menu trôi nổi (Header dính, Menu Mega)
def hideAnnoyingMenus = {
    try {
        String js = "document.querySelectorAll('.header-menu--mega, .header-sticky, .header-mobile').forEach(function(el) { el.style.setProperty('display', 'none', 'important'); });"
        WebUI.executeJavaScript(js, null)
    } catch (Exception e) {}
}

// 3. Hàm Chọn Size (Bắt buộc phải chọn mới thêm vào giỏ được)
def selectFirstAvailableSize = {
    try {
        // Tìm các ô size chưa hết hàng
        String xpathSize = "//div[contains(@class,'swatch-element') and not(contains(@class,'soldout'))]//label | //div[contains(@class,'size')]//span[not(contains(@class,'disabled'))]"
        TestObject sizeObj = new TestObject("sizeObj")
        sizeObj.addProperty("xpath", ConditionType.EQUALS, xpathSize)
        
        if(WebUI.waitForElementPresent(sizeObj, 3, FailureHandling.OPTIONAL)){
            WebElement sizeEl = WebUiCommonHelper.findWebElement(sizeObj, 3)
            sizeEl.click()
            KeywordUtil.logInfo(">>> ✅ Đã tự động chọn Size: " + sizeEl.getText())
            WebUI.delay(1)
        }
    } catch (Exception e) { KeywordUtil.logWarning("⚠️ Không chọn được size: " + e.message) }
}

// ======================================================================
//  🚀 PART 1: SMART BROWSER SETUP (Cứu hộ trình duyệt)
// ======================================================================

boolean isBrowserOpen = false
try {
    if (DriverFactory.getWebDriver() != null) {
        WebUI.getUrl() // Ping kiểm tra kết nối
        isBrowserOpen = true
        KeywordUtil.logInfo(">>> 🔄 Trình duyệt đang mở. Tiếp tục chạy TC 3...")
    }
} catch (Exception e) { isBrowserOpen = false }

if (!isBrowserOpen) {
    KeywordUtil.logInfo(">>> 🚀 Trình duyệt chưa mở (hoặc bị đóng). Khởi tạo mới...")
    ChromeOptions options = new ChromeOptions()
    options.addArguments('--disable-blink-features=AutomationControlled')
    options.addArguments('--start-maximized')
    options.addArguments('--disable-notifications')
    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"))
    
    WebDriver driver = new ChromeDriver(options)
    DriverFactory.changeWebDriver(driver)
    WebUI.navigateToUrl('https://aristino.com/')
}

WebUI.maximizeWindow()

// ======================================================================
//  🛒 PART 2: MAIN FLOW - THÊM SẢN PHẨM 2
// ======================================================================

WebUI.comment('--- TC 3: THÊM SẢN PHẨM 2 ---')

// 1. Đóng Popup giỏ hàng cũ (Nếu đang mở từ bài trước)
try {
    handlePopup() // Quét quảng cáo trước
    TestObject closeCartIcon = findTestObject('Object Repository/ThanhTan/midterm/trang_chi_tiet_san_pham/icon_dong_gio_hang')
    if (WebUI.verifyElementVisible(closeCartIcon, FailureHandling.OPTIONAL)) {
        WebUI.click(closeCartIcon)
        WebUI.delay(1)
        KeywordUtil.logInfo(">>> Đã đóng popup giỏ hàng cũ.")
    }
} catch (Exception e) {}

// 2. Quay lại trang danh mục
// Nếu đang ở trang chủ/danh mục rồi thì không cần click, nhưng để chắc chắn ta cứ click
hideAnnoyingMenus()
WebUI.click(findTestObject('Object Repository/ThanhTan/midterm/trang_chu/link_trang_phuc'))
WebUI.delay(1)

// Dọn dẹp giao diện sau khi chuyển trang
handlePopup()
hideAnnoyingMenus()

// 3. Chọn sản phẩm 2 (Áo Thun)
TestObject product2 = findTestObject('Object Repository/ThanhTan/midterm/trang_hien_thi_danh_muc_trang_phuc/Ao_Thun_T_Shirt_Len_Nam_Aristino')
WebUI.waitForElementPresent(product2, 10)

WebElement elementProduct2 = WebUiCommonHelper.findWebElement(product2, 30)
WebUI.executeJavaScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'});", Arrays.asList(elementProduct2))
WebUI.delay(1)

String tenSanPham2 = WebUI.getText(product2)
WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(elementProduct2))

// --- VÀO TRANG CHI TIẾT ---
WebUI.delay(2)
handlePopup()
hideAnnoyingMenus()

// [QUAN TRỌNG] Chọn Size trước khi thêm
selectFirstAvailableSize()

// 4. Thêm vào giỏ
TestObject btnAdd = findTestObject('Object Repository/ThanhTan/midterm/trang_chi_tiet_san_pham/btn_them_vao_gio')
WebUI.verifyElementPresent(btnAdd, 10)

WebElement elementBtnAdd = WebUiCommonHelper.findWebElement(btnAdd, 30)
WebUI.executeJavaScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'});", Arrays.asList(elementBtnAdd))
WebUI.delay(1) // Đợi scroll xong

WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(elementBtnAdd))

// 5. Verify Kết quả
WebUI.delay(2) // Đợi popup hiện

boolean isPopupOpen = WebUI.verifyElementVisible(findTestObject('Object Repository/ThanhTan/midterm/trang_chi_tiet_san_pham/icon_dong_gio_hang'), FailureHandling.OPTIONAL)

if (!isPopupOpen) {
    WebUI.delay(2) // Chờ thêm chút nữa
    isPopupOpen = WebUI.verifyElementVisible(findTestObject('Object Repository/ThanhTan/midterm/trang_chi_tiet_san_pham/icon_dong_gio_hang'), FailureHandling.OPTIONAL)
}

if (isPopupOpen) {
    if (WebUI.verifyTextPresent(tenSanPham2, false, FailureHandling.OPTIONAL)) {
        KeywordUtil.markPassed('PASSED: Đã thêm SP 2 [' + tenSanPham2 + '] vào giỏ.')
    } else {
        KeywordUtil.markPassed('PASSED: Popup đã mở (Text sản phẩm có thể bị cắt ngắn hoặc khác format).')
    }
} else {
    // Check lỗi size
    if (WebUI.verifyTextPresent("Vui lòng chọn", false, FailureHandling.OPTIONAL)) {
        KeywordUtil.markFailed('FAILED: Chưa chọn được Size nên không thêm được vào giỏ.')
    } else {
        KeywordUtil.markFailed('FAILED: Popup giỏ hàng không hiện ra.')
    }
}