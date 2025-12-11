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


def handleAds = {
	TestObject alertObj = findTestObject('tam/midtern/Page_Register/Page_alert/alert_nitification')
	TestObject btnAllow = findTestObject('tam/midtern/Page_Register/Page_alert/button_chophep')
	if (WebUI.waitForElementVisible(alertObj, 3, FailureHandling.OPTIONAL)) {
		WebUI.click(btnAllow)
	}
	
	TestObject btnCloseAd = findTestObject('tam/midtern/Page_Register/Page_adventis/btn_closeAd')
	if (WebUI.waitForElementVisible(btnCloseAd, 3, FailureHandling.OPTIONAL)) {
		WebUI.click(btnCloseAd)
	}
}

String urlRegister = 'https://aristino.com/account/register'
String username ='21130525@st.hcmuaf.edu.vn'
String password = 'Admin@123'
String lastName ='Le'
String firstName = 'Tam'

WebUI.openBrowser('')

// - 1. register

WebUI.navigateToUrl(urlRegister)

WebUI.waitForElementPresent(findTestObject('tam/midtern/Page_Register/Page_alert/alert_nitification'), 30)

WebUI.click(findTestObject('tam/midtern/Page_Register/Page_alert/button_chophep'))

handleAds()

WebUI.setText(findTestObject('tam/midtern/Page_Register/Page_register/input_Email'), username)

WebUI.setText(findTestObject('tam/midtern/Page_Register/Page_register/input_password'), password)

WebUI.setText(findTestObject('tam/midtern/Page_Register/Page_register/input_last-name'), lastName)

WebUI.setText(findTestObject('tam/midtern/Page_Register/Page_register/input_first-name'), firstName)

WebUI.click(findTestObject('tam/midtern/Page_Register/Page_register/input_arrageCondition'))

WebUI.click(findTestObject('tam/midtern/Page_Register/Page_register/input_errage_get_mail'))

WebUI.click(findTestObject('tam/midtern/Page_Register/Page_register/btn_register'))

boolean isRedirected = WebUI.waitForUrl('https://aristino.com/account', 10)

if (isRedirected) {
	println("PASS: Đã đăng ký thành công.")
} else {
	KeywordUtil.markFailed("FAIL: Vẫn đang ở trang cũ hoặc URL sai.")
}

// - login 
String urlLogin = 'https://aristino.com/account/login'
def login_datatest = [
	[
		Username: '21130525@st.hcmuaf.edu.vn',
		Password: 'pass_wrong',
		Type: 'fail',
		ExpectedOutput: 'Thông tin đăng nhập không hợp lệ'
	],
	[
		Username: 'user_not_exist@st.hcmuaf.edu.vn',
		Password: 'pass_any',
		Type: 'fail',
		ExpectedOutput: 'Thông tin đăng nhập không hợp lệ'
	],
	[
		Username: '21130525@st.hcmuaf.edu.vn',
		Password: 'Admin@123',
		Type: 'success',
		ExpectedOutput: 'logged=true'
	]
]

for (def data : login_datatest) {
	
	String user = data.Username
	String pass = data.Password
	String type = data.Type
	String expected = data.ExpectedOutput
	
	WebUI.openBrowser('')
	
	WebUI.navigateToUrl(urlLogin)
	
	handleAds()
	
	WebUI.setText(findTestObject('tam/midtern/Page_login/input_Email'), user)
	
	WebUI.setText(findTestObject('tam/midtern/Page_login/input_password'), pass)
	
	WebUI.click(findTestObject('tam/midtern/Page_login/btn_login'))
	
	if (type == 'success') {
		WebUI.delay(5)
	
		// 3. Kiểm tra kết quả sau khi chờ
		String currentUrl = WebUI.getUrl()
	
		if (currentUrl.contains('logged=true')) {
			println('PASS: Đăng nhập thành công (URL chứa \'logged=true\')') // Nếu sai thì đánh Fail test case
		} else {
			KeywordUtil.markFailed('FAIL: Đăng nhập thất bại hoặc chưa redirect kịp. URL hiện tại: ' + currentUrl)
		}
		
		if (WebUI.waitForElementVisible(alertObj, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(btnAllow)
		
			println('Đã click nút Cho phép.')
		}
		
		
		if (WebUI.waitForElementVisible(btnCloseAd, 5, FailureHandling.OPTIONAL)) {
			WebUI.click(btnCloseAd)
		
			println('Đã tắt quảng cáo.')
		}
		
	} else if (type == 'fail') {
		TestObject errorMsgObj = findTestObject('tam/midtern/Page_login/div_login_fail')
		WebUI.waitForElementVisible(errorMsgObj, 5)
		
		WebUI.verifyElementText(errorMsgObj, expected,FailureHandling.CONTINUE_ON_FAILURE)
		
		WebUI.closeBrowser()
	}
}

// - logout 
WebUI.navigateToUrl('https://aristino.com/account')

handleAds()

WebUI.click(findTestObject('tam/midtern/Page_account/logout'))

// - forgot password


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
	TestObject alertObj = findTestObject('tam/midtern/Page_Register/Page_alert/alert_nitification')
	TestObject btnAllow = findTestObject('tam/midtern/Page_Register/Page_alert/button_chophep')
	
	// Xử lý Alert thông báo
	if (WebUI.waitForElementVisible(alertObj, 5, FailureHandling.OPTIONAL)) {
		WebUI.click(btnAllow)
		println('Đã click nút Cho phép.')
	}
	
	// Xử lý Quảng cáo Popup
	TestObject btnCloseAd = findTestObject('tam/midtern/Page_Register/Page_adventis/btn_closeAd')
	if (WebUI.waitForElementVisible(btnCloseAd, 5, FailureHandling.OPTIONAL)) {
		WebUI.click(btnCloseAd)
		println('Đã tắt quảng cáo.')
	}
	
	// Thao tác chính: Quên mật khẩu
	WebUI.click(findTestObject('Object Repository/tam/midtern/Page_forgotpassword/abtn_forgot-pasword'))
	
	WebUI.setText(findTestObject('Object Repository/tam/midtern/Page_forgotpassword/input_Email'), email)
	
	WebUI.click(findTestObject('Object Repository/tam/midtern/Page_forgotpassword/button_Email_xac-thuc'))
	

	TestObject msgError = findTestObject('Object Repository/tam/midtern/Page_forgotpassword/msg_error_forgot-pasword')
	
	// Chờ message hiện ra trước khi verify (tránh lỗi element not visible)
	if(WebUI.waitForElementVisible(msgError, 5, FailureHandling.OPTIONAL)) {
		WebUI.verifyElementText(msgError, expected, FailureHandling.CONTINUE_ON_FAILURE)
	} else {
		println("Lỗi: Không tìm thấy thông báo lỗi mong đợi.")
	}
	
	WebUI.closeBrowser()
}

// ---------------------------
// product detail
// --------------------------

// click product 

WebUI.openBrowser('')

WebUI.navigateToUrl('https://aristino.com/collections/trang-phuc')

TestObject alertObj = findTestObject('Object Repository/tam/midtern/Page_Register/Page_alert/alert_nitification')

TestObject btnAllow = findTestObject('Object Repository/tam/midtern/Page_Register/Page_alert/button_chophep')

TestObject btnCloseAd = findTestObject('Object Repository/tam/midtern/Page_Register/Page_adventis/btn_closeAd')

if (WebUI.waitForElementVisible(alertObj, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(btnAllow)

	println('Đã click nút Cho phép.')
}


if (WebUI.waitForElementVisible(btnCloseAd, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(btnCloseAd)

	println('Đã tắt quảng cáo.')
}


WebUI.click(findTestObject('Object Repository/tam/midtern/Page_home/product'))

if (WebUI.waitForElementVisible(alertObj, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(btnAllow)

	println('Đã click nút Cho phép.')
}


if (WebUI.waitForElementVisible(btnCloseAd, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(btnCloseAd)

	println('Đã tắt quảng cáo.')
}


WebUI.verifyElementText(findTestObject('Object Repository/tam/midtern/Page_product-detail/button_addtocart'), 'ĐẶT HÀNG TRƯỚC')

WebUI.verifyElementClickable(findTestObject('tam/midtern/Page_product-detail/size_S'))

WebUI.verifyElementClickable(findTestObject('tam/midtern/Page_product-detail/size_M'))

WebUI.verifyElementClickable(findTestObject('tam/midtern/Page_product-detail/size_L'))

WebUI.verifyElementClickable(findTestObject('tam/midtern/Page_product-detail/size_XL'))

WebUI.verifyElementClickable(findTestObject('tam/midtern/Page_product-detail/size_XXL'))

WebUI.verifyElementVisible(findTestObject('tam/midtern/Page_product-detail/div_image'))

WebUI.verifyElementVisible(findTestObject('tam/midtern/Page_product-detail/div_image'))

WebUI.click(findTestObject('tam/midtern/Page_product-detail/div_image'))


// image

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_product-detail/img_product'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_product-detail/button_next_img'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_product-detail/button_next_img'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_product-detail/button_next_img'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_product-detail/button_next_img'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_product-detail/button_next_img'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_product-detail/button_zoom'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_product-detail/button_zoom'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_product-detail/button_zoom_in'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_product-detail/button_zoom_out'))

WebUI.click(findTestObject('tam/midtern/Page_product-detail/button_closeImg'))

// add to cart
WebUI.click(findTestObject('Object Repository/tam/midtern/Page_productp/label_chose_color'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_productp/lsize_M'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_productp/saddtocart'))

WebUI.verifyElementText(findTestObject('Object Repository/tam/midtern/Page_productp/titleGiohang'), 'GIỎ HÀNG')

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_productp/add1'))

WebUI.click(findTestObject('tam/midtern/Page_product-detail/sclose_cart'))

// product relative

WebUI.navigateToUrl('https://aristino.com/products/ao-khoac-2-lop-nam-xanh-reu-aristino-regular-fit-ajk606edp01')

TestObject alertObj = findTestObject('tam/midtern/Page_Register/Page_alert/alert_nitification')

TestObject btnAllow = findTestObject('tam/midtern/Page_Register/Page_alert/button_chophep')

if (WebUI.waitForElementVisible(alertObj, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(btnAllow)

	println('Đã click nút Cho phép.')
}

TestObject btnCloseAd = findTestObject('tam/midtern/Page_Register/Page_adventis/btn_closeAd')

if (WebUI.waitForElementVisible(btnCloseAd, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(btnCloseAd)

	println('Đã tắt quảng cáo.')
}

WebUI.verifyElementPresent(findTestObject('Object Repository/tam/midtern/Page_product-detail/div_product-relatetive-list'),
	0)

WebUI.verifyElementClickable(findTestObject('Object Repository/tam/midtern/Page_product-detail/a_product-relative'))

WebUI.click(findTestObject('Object Repository/tam/midtern/Page_product-detail/a_product-relative'))



