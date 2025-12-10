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
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.driver.DriverFactory


// 1. Mở trình duyệt và vào trang
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://aristino.com')
WebUI.waitForPageLoad(15)
// 2. Xử lý Popup (Giữ nguyên logic của bạn)
WebUI.delay(2)
if (WebUI.verifyElementPresent(findTestObject('Object Repository/Tai/Update/Popup_Page/button_Boqua'), 3, FailureHandling.OPTIONAL)) {
	WebUI.click(findTestObject('Object Repository/Tai/Update/Popup_Page/button_Boqua'))
}

// 3. Thực hiện Login
WebUI.click(findTestObject('Object Repository/Tai/Update/Popup_Page/a_IconLogin'))
WebUI.delay(2)

WebUI.setText(findTestObject('Object Repository/Tai/Update/Page_Login_ARISTINO/input_login-email'), 'mintaeyeon1978@gmail.com')
WebUI.setEncryptedText(findTestObject('Object Repository/Tai/Update/Page_Login_ARISTINO/input_matkhau_password'), 'it+UEM7YdCc2dc6O4DE7BA==')
WebUI.click(findTestObject('Object Repository/Tai/Update/Page_Login_ARISTINO/button_Login'))

// 4. Kiểm tra Login thành công
WebUI.verifyElementText(findTestObject('Object Repository/Tai/Update/Page_ThongTinARISTINO/div_XinChaoTaiVo'), 'Xin Chào, Tài Võ!')
