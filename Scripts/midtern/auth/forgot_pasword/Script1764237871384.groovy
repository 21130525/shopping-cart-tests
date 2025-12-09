import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject as TestObject

// 1. Load Data
def testData = findTestData('Data Files/data_forgot-pasword')
int totalRows = testData.getRowNumbers()
println("Tổng số dòng data: " + totalRows)


for (int i = 1; i <= totalRows; i++) {
	
	String email = testData.getValue('Email', i)
	String expected = testData.getValue('ExpectedOutput', i)
	
	println("--- Đang chạy dòng " + i + " với Email: " + email + " ---")
	
	WebUI.openBrowser('')
	WebUI.navigateToUrl('https://aristino.com/account/login')
	
	// SỬA LỖI 2: Khai báo object trước khi dùng
	TestObject alertObj = findTestObject('midtern/Page_Register/Page_alert/alert_nitification')
	TestObject btnAllow = findTestObject('midtern/Page_Register/Page_alert/button_chophep')
	
	// Xử lý Alert thông báo
	if (WebUI.waitForElementVisible(alertObj, 5, FailureHandling.OPTIONAL)) {
		WebUI.click(btnAllow)
		println('Đã click nút Cho phép.')
	}
	
	// Xử lý Quảng cáo Popup
	TestObject btnCloseAd = findTestObject('midtern/Page_Register/Page_adventis/btn_closeAd')
	if (WebUI.waitForElementVisible(btnCloseAd, 5, FailureHandling.OPTIONAL)) {
		WebUI.click(btnCloseAd)
		println('Đã tắt quảng cáo.')
	}
	
	// Thao tác chính: Quên mật khẩu
	WebUI.click(findTestObject('Object Repository/midtern/Page_forgotpassword/abtn_forgot-pasword'))
	
	WebUI.setText(findTestObject('Object Repository/midtern/Page_forgotpassword/input_Email'), email)
	
	WebUI.click(findTestObject('Object Repository/midtern/Page_forgotpassword/button_Email_xac-thuc'))
	

	TestObject msgError = findTestObject('Object Repository/midtern/Page_forgotpassword/msg_error_forgot-pasword')
	
	// Chờ message hiện ra trước khi verify (tránh lỗi element not visible)
	if(WebUI.waitForElementVisible(msgError, 5, FailureHandling.OPTIONAL)) {
		WebUI.verifyElementText(msgError, expected, FailureHandling.CONTINUE_ON_FAILURE)
	} else {
		println("Lỗi: Không tìm thấy thông báo lỗi mong đợi.")
	}
	
	WebUI.closeBrowser()
}