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

////Call TestCase
//WebUI.callTestCase(findTestCase('Test Cases/UpdateInfomation/UpdateInfo'), [:], FailureHandling.STOP_ON_FAILURE)

TestObject tabThongTin = findTestObject('Tai/Update/Page_ARISTINO/span_Thongtingiaohang')
WebUI.waitForElementClickable(tabThongTin, 15)
WebUI.click(tabThongTin)

TestObject btnEdit = findTestObject('Tai/Update/Page_chinhsua_ARISTINO/path_chinhsua')
WebUI.waitForElementClickable(btnEdit, 15)
WebUI.click(btnEdit)
//Địa chỉ cũ
TestObject firstNameOld = findTestObject('Tai/Update/Page_capnhat_ARISTINO/input_firstname')
TestObject lastNameOld  = findTestObject('Tai/Update/Page_capnhat_ARISTINO/input_lastname')
TestObject phoneOld     = findTestObject('Tai/Update/Page_capnhat_ARISTINO/input_phone')
TestObject addressOld  = findTestObject('Tai/Update/Page_capnhat_ARISTINO/input_Address')

TestObject provinceOld = findTestObject('Tai/Update/Page_capnhat_ARISTINO/select_Province')
TestObject districtOld = findTestObject('Tai/Update/Page_capnhat_ARISTINO/select_District')
TestObject wardOld     = findTestObject('Tai/Update/Page_capnhat_ARISTINO/select_Ward')

TestObject nhaRiengOld = findTestObject('Tai/Update/Page_capnhat_ARISTINO/label_Nharieng')
TestObject btnUpdate  = findTestObject('Tai/Update/Page_capnhat_ARISTINO/button_CapNhatAddress')

WebUI.waitForElementVisible(firstNameOld, 15)
WebUI.scrollToElement(firstNameOld, 2)

WebUI.setText(firstNameOld, 'Tai')
WebUI.setText(lastNameOld, 'Vo')
WebUI.setText(phoneOld, '0987654621')


WebUI.waitForElementClickable(provinceOld, 10)
WebUI.selectOptionByLabel(provinceOld, 'Bình Dương', false)
WebUI.delay(2)

WebUI.selectOptionByLabel(districtOld, 'Thành Phố Dĩ An', false)
WebUI.delay(2)

WebUI.selectOptionByLabel(wardOld, 'Phường Dĩ An', false)

WebUI.setText(addressOld, '123 Pham Van Bach')

WebUI.scrollToElement(nhaRiengOld, 2)
WebUI.waitForElementClickable(nhaRiengOld, 5)
WebUI.click(nhaRiengOld)

WebUI.scrollToElement(btnUpdate, 2)
WebUI.waitForElementClickable(btnUpdate, 10)
WebUI.click(btnUpdate)

WebUI.delay(4)
