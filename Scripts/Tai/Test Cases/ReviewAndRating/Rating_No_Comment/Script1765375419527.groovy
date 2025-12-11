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
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

def clickGuiDanhGia() {
	TestObject btn = findTestObject('Object Repository/Tai/Rating/Page_GuiDanhGia/div_GuiDanhGia')
	WebUI.waitForElementVisible(btn, 15)
	WebUI.scrollToElement(btn, 1)
	WebUI.delay(1)

	try {
		WebUI.click(btn)
	} catch (Exception e) {
		WebElement el = WebUI.findWebElement(btn)
		WebUI.executeJavaScript("arguments[0].click();", java.util.Arrays.asList(el))
	}
}

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_TrangPhuc/a_TRANGPHUC'))

WebUI.waitForPageLoad(15)

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_TrangPhuc/a_NEW_pro-loop'))

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_DanhGia/span_DanhGiaVaNhanXet'))

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_DanhGia/button_VietDanhGia'))

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_Rating/rating_3'))

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_GioiThieuSanPham/input_No'))

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_GioiThieuSanPham/input_ONLINE'))

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_SIZE/label_2'))

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_Width/label_2'))

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_Comfort/label_2'))

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_Quanlity/label_2'))

clickGuiDanhGia()

WebUI.verifyElementText(findTestObject('Object Repository/Tai/Rating/Page_ThongBaoLoi/div_Vuilongnhapthongtin'), 'Vui lòng nhập thông tin')

WebUI.delay(3)

WebUI.refresh()

WebUI.waitForPageLoad(20)


