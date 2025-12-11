import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// 1. Mở trình duyệt & Xử lý popup
CustomKeywords.'thanh.CommonActions.openBrowserAndNavigate'()
CustomKeywords.'thanh.CommonActions.handlePopup'()

println("\n=== TEST MENU: PHỤ KIỆN (ADVANCED FILTER) ===")

// 2. Click vào Menu "Phụ Kiện"
def menuPhuKien = CustomKeywords.'thanh.CommonActions.makeTo'("//a[contains(@href, 'phu-kien')]")
CustomKeywords.'thanh.CommonActions.smartClick'(menuPhuKien)

// Chờ trang tải và xử lý popup lại
WebUI.waitForPageLoad(10)
CustomKeywords.'thanh.CommonActions.handlePopup'()

// ==================================================
// CHỨC NĂNG 1: LỌC THEO MÀU (Vd: Màu Đen)
// ==================================================
println("--- Step 1: Filter by Color (Black) ---")
def filterColor = CustomKeywords.'thanh.CommonActions.makeTo'("//label[contains(text(),'Đen') or contains(text(),'Black')]")

// Cuộn xuống tìm bộ lọc
WebUI.scrollToElement(filterColor, 3)
CustomKeywords.'thanh.CommonActions.smartClick'(filterColor)
WebUI.delay(2)

// Kiểm tra URL
if(WebUI.getUrl().contains("?")) {
    println("PASS: Đã chọn filter Màu sắc.")
} else {
    println("WARNING: URL chưa thay đổi, có thể filter chưa ăn.")
}

// ==================================================
// CHỨC NĂNG 2: LỌC THÊM GIÁ (Vd: Khoảng 500k - 1tr)
// ==================================================
println("--- Step 2: Add Filter Price (500k) ---")
// Tìm label chứa mức giá 500.000đ
def filterPrice = CustomKeywords.'thanh.CommonActions.makeTo'("//label[contains(text(),'500.000') or contains(text(),'1.000.000')]")

if(WebUI.waitForElementPresent(filterPrice, 3, FailureHandling.OPTIONAL)) {
    CustomKeywords.'thanh.CommonActions.smartClick'(filterPrice)
    WebUI.delay(2)
    println("PASS: Đã chọn thêm filter Giá.")
} else {
    println("INFO: Không tìm thấy filter Giá, bỏ qua bước này.")
}

// ==================================================
// KIỂM TRA KẾT QUẢ & RESET (CLEANUP)
// ==================================================
// Cuộn trang để xem danh sách sản phẩm sau khi lọc 2 tiêu chí
CustomKeywords.'thanh.CommonActions.scrollFullPage'()

println("--- Step 3: Uncheck Filters ---")

// Bỏ chọn Màu Đen
WebUI.scrollToElement(filterColor, 3)
CustomKeywords.'thanh.CommonActions.smartClick'(filterColor)
WebUI.delay(1)

// Bỏ chọn Giá (nếu nãy có chọn được)
if(WebUI.waitForElementPresent(filterPrice, 3, FailureHandling.OPTIONAL)) {
    CustomKeywords.'thanh.CommonActions.smartClick'(filterPrice)
}

WebUI.closeBrowser()