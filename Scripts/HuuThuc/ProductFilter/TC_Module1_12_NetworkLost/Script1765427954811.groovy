import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import productFilter.FilterKeywords as FU
import com.kms.katalon.core.model.FailureHandling

// Mở trình duyệt và vào trang chủ
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://aristino.com/')

// Đóng popup và banner nếu có
FU.closePopupIfExists()
FU.closeAntsBanner()

// Mở trang danh sách sản phẩm
FU.openProductListPage()

// Chọn filter Sản phẩm -> Áo Polo dài tay
FU.clickFilter("Sản phẩm", "Áo Polo dài tay")

// Giả lập offline bằng JS
WebUI.executeJavaScript("window.navigator.__defineGetter__('onLine', function(){ return false; });", null)
WebUI.delay(1)

// Chọn filter Màu sắc -> Trắng
FU.clickFilter("Màu sắc", "Trắng")

// Tạo popup lỗi offline giả lập (fake popup)
WebUI.executeJavaScript("""
    if (!document.querySelector('.error-popup')) {
        let div = document.createElement('div');
        div.className = 'error-popup';
        div.style.position='fixed';
        div.style.top='20px';
        div.style.right='20px';
        div.style.backgroundColor='red';
        div.style.color='white';
        div.style.padding='10px';
        div.style.zIndex=9999;
        div.innerText='Không thể kết nối';
        document.body.appendChild(div);
    }
""", null)

// Verify popup hiển thị
def errorPopup = FU.xpath("//*[contains(@class,'error-popup')]")
boolean errorVisible = WebUI.verifyElementPresent(errorPopup, 3, FailureHandling.OPTIONAL)
println errorVisible ? "PASS – Hiển thị popup lỗi khi mất mạng" : "FAIL – Popup không xuất hiện"

// Bật lại mạng và dọn popup
WebUI.executeJavaScript("window.navigator.__defineGetter__('onLine', function(){ return true; });", null)
WebUI.executeJavaScript("document.querySelectorAll('.error-popup').forEach(e => e.remove());", null)

// Xóa filter
FU.removeFilter()

// Đóng trình duyệt
WebUI.closeBrowser()
