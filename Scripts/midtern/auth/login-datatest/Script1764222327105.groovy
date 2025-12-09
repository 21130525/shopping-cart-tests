import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
// Thêm thư viện này để dùng KeywordUtil
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint


def testData = findTestData('Data Files/data_login')
int totalRows = testData.getRowNumbers()
print(totalRows)
for (int i = 1; i <= totalRows; i++) {
	
	String user = testData.getValue('Username', i)
	String pass = testData.getValue('Password', i)
	String type = testData.getValue('Type', i)
	String expected = testData.getValue('ExpectedOutput', i)
	
	WebUI.openBrowser('')
	
	WebUI.navigateToUrl(GlobalVariable.urlLogin)
	
	TestObject alertObj = findTestObject('midtern/Page_Register/Page_alert/alert_nitification')
	
	TestObject btnAllow = findTestObject('midtern/Page_Register/Page_alert/button_chophep')
	
	if (WebUI.waitForElementVisible(alertObj, 5, FailureHandling.OPTIONAL)) {
	    WebUI.click(btnAllow)
	
	    println('Đã click nút Cho phép.')
	}
	
	TestObject btnCloseAd = findTestObject('midtern/Page_Register/Page_adventis/btn_closeAd')
	
	if (WebUI.waitForElementVisible(btnCloseAd, 5, FailureHandling.OPTIONAL)) {
	    WebUI.click(btnCloseAd)
	
	    println('Đã tắt quảng cáo.')
	}
	
	WebUI.setText(findTestObject('midtern/Page_login/input_Email'), user)
	
	WebUI.setText(findTestObject('midtern/Page_login/input_password'), pass)
	
	WebUI.click(findTestObject('midtern/Page_login/btn_login'))
	
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
		TestObject errorMsgObj = findTestObject('midtern/Page_login/div_login_fail')
		WebUI.waitForElementVisible(errorMsgObj, 5)
		
		WebUI.verifyElementText(errorMsgObj, expected,FailureHandling.CONTINUE_ON_FAILURE)
		
		WebUI.closeBrowser()
	}
}

