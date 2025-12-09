import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

def forgot_password_data = [
	[
		Email: '21130525@st.hcmuaf.edu.vn',
		ExpectedOutput: 'Vui lòng xác thực email của bạn'
	],
	[
		Email: 'invalicEmai.@gmai.com',
		ExpectedOutput: 'Email không hợp lệ'
	],
	[
		Email: 'notAEmai.gmai.com',
		ExpectedOutput: 'Email không hợp lệ'
	]
]


for (def data : forgot_password_data) {
	
	String email = data.Email
	String expected = data.ExpectedOutput
	
	
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