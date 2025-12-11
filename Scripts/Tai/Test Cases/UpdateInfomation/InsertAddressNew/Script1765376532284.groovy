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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory



//WebUI.callTestCase(findTestCase('Test Cases/UpdateInfomation/UpdateAddressOld'), [:], FailureHandling.CONTINUE_ON_FAILURE)
//Địa chỉ mới
TestObject btnThemDiaChi = findTestObject('Tai/Update/Page_chinhsua_ARISTINO/a_ThemDiaChi')

WebUI.waitForElementClickable(btnThemDiaChi, 20)
WebUI.click(btnThemDiaChi)

// CHẤP NHẬN RELOAD XONG MỚI BẮT FORM
TestObject firstNameNew = findTestObject('Tai/Update/Page_DiaChiMoi_ARISTINO/input_firstname_new')
TestObject lastNameNew  = findTestObject('Tai/Update/Page_DiaChiMoi_ARISTINO/input_lastname_new')
TestObject phoneNew     = findTestObject('Tai/Update/Page_DiaChiMoi_ARISTINO/input_phone_new')
TestObject addressNew  = findTestObject('Tai/Update/Page_DiaChiMoi_ARISTINO/input_address-new')

TestObject provinceNew = findTestObject('Tai/Update/Page_DiaChiMoi_ARISTINO/select_Province')
TestObject districtNew = findTestObject('Tai/Update/Page_DiaChiMoi_ARISTINO/select_District')
TestObject wardNew     = findTestObject('Tai/Update/Page_DiaChiMoi_ARISTINO/select_Ward')

TestObject nhaRiengNew = findTestObject('Tai/Update/Page_DiaChiMoi_ARISTINO/label_Nharieng')
TestObject btnCreate  = findTestObject('Tai/Update/Page_DiaChiMoi_ARISTINO/button_create')

// Đợi form load xong
WebUI.waitForElementVisible(firstNameNew, 20)
WebUI.scrollToElement(firstNameNew, 2)

// Nhập thông tin
WebUI.setText(firstNameNew, 'Tam')
WebUI.setText(lastNameNew, 'Vo')
WebUI.setText(phoneNew, '0988888888')

// Chọn Tỉnh / Huyện / Xã
WebUI.waitForElementClickable(provinceNew, 10)
WebUI.selectOptionByLabel(provinceNew, 'Bình Dương', false)
WebUI.delay(2)

WebUI.selectOptionByLabel(districtNew, 'Thành Phố Dĩ An', false)
WebUI.delay(2)

WebUI.selectOptionByLabel(wardNew, 'Phường Dĩ An', false)
WebUI.delay(1)

// Nhập địa chỉ cụ thể
WebUI.setText(addressNew, '123 Phạm Văn Bạch')

// Chọn Nhà riêng
WebUI.scrollToElement(nhaRiengNew, 2)
WebUI.waitForElementClickable(nhaRiengNew, 5)
WebUI.click(nhaRiengNew)

// Nhấn tạo địa chỉ
WebUI.scrollToElement(btnCreate, 2)
WebUI.waitForElementClickable(btnCreate, 10)
WebUI.click(btnCreate)


