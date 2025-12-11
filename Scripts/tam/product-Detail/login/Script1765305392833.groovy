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

WebUI.navigateToUrl(GlobalVariable.urlLogin)

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

WebUI.setText(findTestObject('tam/midtern/Page_login/input_Email'), GlobalVariable.username)

WebUI.setText(findTestObject('tam/midtern/Page_login/input_password'), GlobalVariable.password)

WebUI.click(findTestObject('tam/midtern/Page_login/btn_login'))

WebUI.delay(5)

// 3. Kiểm tra kết quả sau khi chờ
String currentUrl = WebUI.getUrl()

if (currentUrl.contains('logged=true')) {
    println('PASS: Đăng nhập thành công (URL chứa \'logged=true\')' // Nếu sai thì đánh Fail test case
        )
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

