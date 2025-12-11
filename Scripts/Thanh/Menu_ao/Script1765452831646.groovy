import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// 1. Mở trình duyệt & xử lý popup
CustomKeywords.'thanh.CommonActions.openBrowserAndNavigate'()
CustomKeywords.'thanh.CommonActions.handlePopup'()

println("\n=== TEST MENU: ÁO (ADVANCED FILTER) ===")

// 2. Click vào Menu "Tất cả Áo"
def menuAo = CustomKeywords.'thanh.CommonActions.makeTo'("//a[contains(@href, 'tat-ca-ao')]")
CustomKeywords.'thanh.CommonActions.smartClick'(menuAo)

// Chờ trang tải và tắt popup nếu nó hiện lại
WebUI.waitForPageLoad(10)
CustomKeywords.'thanh.CommonActions.handlePopup'()

// ==================================================
// CHỨC NĂNG 1: LỌC THEO SIZE (Vd: Size L hoặc 40)
// ==================================================
println("--- Step 1: Filter by Size ---")
def filterSize = CustomKeywords.'thanh.CommonActions.makeTo'("//label[contains(text(),'L') or contains(text(),'40')]")

// Cuộn xuống tìm bộ lọc
WebUI.scrollToElement(filterSize, 3)
CustomKeywords.'thanh.CommonActions.smartClick'(filterSize)
WebUI.delay(2)

// Kiểm tra xem URL có thay đổi không (dấu hiệu đã lọc)
if(WebUI.getUrl().contains("?")) {
	println("PASS: Đã chọn filter Size.")
} else {
	println("WARNING: URL chưa thay đổi, có thể filter chưa ăn.")
}

// ==================================================
// CHỨC NĂNG 2: LỌC THÊM MÀU SẮC / GIÁ (Vd: Màu Xanh)
// ==================================================
println("--- Step 2: Add more Filter (Color/Price) ---")
// Bạn có thể sửa text 'Xanh' thành 'Đỏ', 'Trắng' hoặc mức giá '500.000' tùy ý
def filterSecond = CustomKeywords.'thanh.CommonActions.makeTo'("//label[contains(text(),'Xanh') or contains(text(),'Blue')]")

if(WebUI.waitForElementPresent(filterSecond, 3, FailureHandling.OPTIONAL)) {
	CustomKeywords.'thanh.CommonActions.smartClick'(filterSecond)
	WebUI.delay(2)
	println("PASS: Đã chọn thêm filter thứ 2 (Màu sắc/Giá).")
} else {
	println("INFO: Không tìm thấy filter thứ 2, bỏ qua bước này.")
}

// ==================================================
// KIỂM TRA KẾT QUẢ & RESET
// ==================================================
// Cuộn trang để xem danh sách sản phẩm sau khi lọc
CustomKeywords.'thanh.CommonActions.scrollFullPage'()

println("--- Step 3: Uncheck Filters (Clean up) ---")
// Bỏ chọn Size
WebUI.scrollToElement(filterSize, 3)
CustomKeywords.'thanh.CommonActions.smartClick'(filterSize)
WebUI.delay(1)

// Bỏ chọn Filter thứ 2 (nếu có)
if(WebUI.waitForElementPresent(filterSecond, 3, FailureHandling.OPTIONAL)) {
	CustomKeywords.'thanh.CommonActions.smartClick'(filterSecond)
}

WebUI.closeBrowser()