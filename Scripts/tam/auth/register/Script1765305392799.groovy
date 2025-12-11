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

WebUI.openBrowser('')

WebUI.navigateToUrl(url)

WebUI.waitForElementPresent(findTestObject('tam/midtern/Page_Register/Page_alert/alert_nitification'), 30)

WebUI.click(findTestObject('tam/midtern/Page_Register/Page_alert/button_chophep'))

// Khai báo object ra biến cho gọn
TestObject btnCloseAd = findTestObject('tam/midtern/Page_Register/Page_adventis/btn_closeAd')

// 1. Chờ và kiểm tra xem nút tắt quảng cáo có hiện ra không (trong 10s)
// Quan trọng: Dùng FailureHandling.OPTIONAL để nếu không có quảng cáo, test KHÔNG BỊ FAIL
boolean isAdPresent = WebUI.waitForElementVisible(btnCloseAd, 10, FailureHandling.OPTIONAL)

if (isAdPresent) {
    // 2. Nếu tìm thấy thì mới click
    WebUI.click(btnCloseAd)

    println('Đã tìm thấy và tắt quảng cáo.' // 3. Nếu không thấy thì bỏ qua, chạy tiếp các bước điền form
        )
} else {
    println('Không có quảng cáo xuất hiện hoặc không bắt được object.')
}

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