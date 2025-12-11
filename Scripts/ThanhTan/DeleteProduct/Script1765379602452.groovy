import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import java.util.Arrays
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

// ==========================================================
// 1. HÀM XỬ LÝ POPUP (Đã tối ưu để không báo lỗi đỏ)
// ==========================================================
def handlePopup = {
    try {
        // Dùng Selenium thuần tìm element để tránh log lỗi "Unable to click" của Katalon
        WebDriver driver = DriverFactory.getWebDriver()
        List<WebElement> closeBtns = driver.findElements(By.xpath("//button[contains(@class,'CloseButtonWrapper') or contains(text(),'Bỏ qua')]"))
        
        // Nếu tìm thấy nút và nút đang hiện
        if (closeBtns.size() > 0 && closeBtns.get(0).isDisplayed()) {
            closeBtns.get(0).click()
            KeywordUtil.logInfo(">>> Đã đóng popup quảng cáo.")
            WebUI.delay(1)
        }
    } catch (Exception e) {}
}

// ==========================================================
// 2. HÀM ẨN MENU DROP-DOWN (Thêm mới theo yêu cầu)
// ==========================================================
def hideAnnoyingMenus = {
    try {
        // Tìm tất cả các menu, header dính và ẩn đi để không che nút
        String js = "document.querySelectorAll('.header-menu--mega, .header-sticky, .header-mobile').forEach(function(el) { el.style.setProperty('display', 'none', 'important'); });"
        WebUI.executeJavaScript(js, null)
    } catch (Exception e) {}
}

// ==========================================================
// BẮT ĐẦU TEST CASE (Code cũ của bạn)
// ==========================================================

WebUI.comment('--- TC 4: XÓA SẢN PHẨM ---')

// [MỚI] Gọi hàm ẩn menu ngay đầu để dọn đường
hideAnnoyingMenus()
handlePopup()

// Đảm bảo popup giỏ hàng đang mở
boolean isPopupVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/ThanhTan/midterm/trang_chi_tiet_san_pham/icon_dong_gio_hang'), FailureHandling.OPTIONAL)

if (!isPopupVisible) {
    // Nếu chưa mở thì click mở lại
    // [MỚI] Ẩn menu trước khi click để chắc chắn không bấm nhầm
    hideAnnoyingMenus() 
    WebUI.click(findTestObject('Object Repository/ThanhTan/midterm/trang_chu/link_trang_phuc')) // Click đại diện (Lưu ý: Nếu object này chỉ là link chuyển trang thì bạn nên thay bằng icon giỏ hàng)
}

// [MỚI] Quét popup và ẩn menu lần nữa trước khi thực sự thao tác xóa
handlePopup()
hideAnnoyingMenus()

// 1. Thực hiện Xóa
TestObject btnDeletePopup = findTestObject('Object Repository/ThanhTan/midterm/trang_chi_tiet_san_pham/a_delete_product')
WebUI.waitForElementVisible(btnDeletePopup, 10)

WebElement elementBtnDelete = WebUiCommonHelper.findWebElement(btnDeletePopup, 10)
WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(elementBtnDelete))

// Xử lý Alert
try {
    WebUI.delay(1)
    WebUI.acceptAlert()
} catch (Exception e) {}

// 2. Verify
WebUI.delay(2)
boolean isProductStillVisible = WebUI.verifyTextPresent(GlobalVariable.TEN_SAN_PHAM, false, FailureHandling.OPTIONAL)

if (!isProductStillVisible) {
    KeywordUtil.markPassed('PASSED: Sản phẩm đã bị xóa.')
} else {
     if(WebUI.verifyTextPresent('Chưa có sản phẩm nào', false, FailureHandling.OPTIONAL)){
        KeywordUtil.markPassed('PASSED: Giỏ hàng trống.')
    } else {
        KeywordUtil.markFailed('FAILED: Xóa thất bại.')
    }
}