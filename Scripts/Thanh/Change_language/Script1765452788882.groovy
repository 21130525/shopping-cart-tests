import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// ==================================================
// SETUP: KHỞI TẠO
// ==================================================
CustomKeywords.'thanh.CommonActions.openBrowserAndNavigate'()
CustomKeywords.'thanh.CommonActions.handlePopup'()

// Định nghĩa các đối tượng dùng chung
def googleLangDropdown = CustomKeywords.'thanh.CommonActions.makeTo'("//*[@id=':0.targetLanguage']/select")
// Chọn sản phẩm đầu tiên trong danh sách (dùng chung cho cả 2 lần click)
def firstProduct = CustomKeywords.'thanh.CommonActions.makeTo'("(//div[contains(@class, 'product')]//a)[1]")
def btnAddToCart = CustomKeywords.'thanh.CommonActions.makeTo'("//button[contains(@id, 'add-to-cart') or contains(@class, 'add-to-cart')]")

// ==================================================
// PHASE 1: CHUYỂN TIẾNG ANH -> XEM CHI TIẾT
// ==================================================
println("\n>>> BẮT ĐẦU PHASE 1: ENGLISH MODE")

if (WebUI.waitForElementPresent(googleLangDropdown, 5, FailureHandling.OPTIONAL)) {
    // 1. Đổi sang Tiếng Anh
    try {
        WebUI.selectOptionByValue(googleLangDropdown, 'en', false)
        println("Log: Đã chọn English")
    } catch (Exception e) {
        WebUI.selectOptionByLabel(googleLangDropdown, 'English', false)
    }
    
    // Đợi Google dịch xong & Cuộn trang
    WebUI.delay(3)
    CustomKeywords.'thanh.CommonActions.scrollFullPage'()

    // 2. Click vào sản phẩm để xem chi tiết
    WebUI.scrollToElement(firstProduct, 3)
    println("Log: Click xem chi tiết sản phẩm (Lần 1)...")
    CustomKeywords.'thanh.CommonActions.smartClick'(firstProduct)
    
    // 3. Kiểm tra trang chi tiết
    WebUI.waitForPageLoad(10)
    CustomKeywords.'thanh.CommonActions.handlePopup'() // Xử lý popup nếu hiện lại ở trang chi tiết
    
    if(WebUI.waitForElementPresent(btnAddToCart, 5, FailureHandling.OPTIONAL)){
         println("PASS: Đã vào trang chi tiết (Giao diện Tiếng Anh)")
    }
}

// ==================================================
// PHASE 2: QUAY VỀ HOME -> ĐỔI TIẾNG VIỆT -> XEM CHI TIẾT
// ==================================================
println("\n>>> BẮT ĐẦU PHASE 2: VIETNAMESE MODE")

// 1. Quay về trang chủ để bắt đầu lại quy trình
WebUI.navigateToUrl('https://aristino.com/')
WebUI.waitForPageLoad(10)
CustomKeywords.'thanh.CommonActions.handlePopup'()

if (WebUI.waitForElementPresent(googleLangDropdown, 5, FailureHandling.OPTIONAL)) {
    // 2. Đổi về Tiếng Việt
    try {
        WebUI.selectOptionByValue(googleLangDropdown, 'vi', false)
        println("Log: Đã chọn Vietnamese")
    } catch (Exception e) {
        WebUI.selectOptionByLabel(googleLangDropdown, 'Vietnamese', false)
    }
    
    WebUI.delay(3)
    CustomKeywords.'thanh.CommonActions.scrollFullPage'()

    // 3. Click vào sản phẩm lần nữa
    WebUI.scrollToElement(firstProduct, 3)
    println("Log: Click xem chi tiết sản phẩm (Lần 2)...")
    CustomKeywords.'thanh.CommonActions.smartClick'(firstProduct)
    
    // 4. Kiểm tra trang chi tiết
    WebUI.waitForPageLoad(10)
    
    if(WebUI.waitForElementPresent(btnAddToCart, 5, FailureHandling.OPTIONAL)){
         println("PASS: Đã vào trang chi tiết (Giao diện Tiếng Việt)")
         String btnText = WebUI.getText(btnAddToCart)
         println("Log: Text trên nút mua hàng là: " + btnText)
    }
}

// ==================================================
// KẾT THÚC
// ==================================================
WebUI.closeBrowser()