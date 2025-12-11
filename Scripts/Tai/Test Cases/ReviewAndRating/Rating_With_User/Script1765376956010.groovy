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
import com.kms.katalon.core.testobject.ConditionType

TestObject profileBtn = findTestObject("Object Repository/Tai/Rating/Page_Profile/a_Profile")

if (WebUI.waitForElementVisible(profileBtn, 10)) {
    WebUI.click(profileBtn, FailureHandling.CONTINUE_ON_FAILURE)
} else {
    WebUI.comment("❌ Không tìm thấy nút Profile – có thể chưa đăng nhập")
}

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_Profile/span_DangXuat'))
WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_TrangPhuc/a_TRANGPHUC'))
TestObject overlay = new TestObject()
overlay.addProperty("css", ConditionType.EQUALS, ".menuOverlay")
WebUI.waitForElementNotVisible(overlay, 15)
WebUI.waitForPageLoad(15)
WebUI.click(findTestObject("Object Repository/Tai/Rating/Page_TrangPhuc/a_NEW_pro-loop"), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_DanhGia/span_DanhGiaVaNhanXet'))
WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_DanhGia/button_VietDanhGia'))
WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_Rating/rating_5'))
WebUI.setText(findTestObject('Object Repository/Tai/Rating/Page_Customer/input__ten_hien_thi'), 'Ho Phu')
WebUI.setText(findTestObject('Object Repository/Tai/Rating/Page_Customer/input_email'), 'mintaeyeon1978@gmail.com')
WebUI.setText(findTestObject('Object Repository/Tai/Rating/Page_Customer/input_phone'), '0765231189')
WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_GioiThieuSanPham/input_Yes'))
WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_GioiThieuSanPham/input_ONLINE'))
WebUI.setText(findTestObject('Object Repository/Tai/Rating/Page_GioiThieuSanPham/textarea__share_review_product'),
	'Sản phẩm rất tốt đẹp, rất mát')
WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_SIZE/label_3'))
WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_Width/label_3'))
WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_DanhGiaComfort/label_5'))
WebUI.click(findTestObject('Object Repository/Tai/Rating/Page_DanhGiaQUanlity/label_5'))

WebUI.closeBrowser()