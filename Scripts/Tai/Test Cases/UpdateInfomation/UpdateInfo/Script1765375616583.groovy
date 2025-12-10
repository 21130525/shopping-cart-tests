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
//WebUI.callTestCase(findTestCase('Test Cases/Login/Login'), [:], FailureHandling.STOP_ON_FAILURE)

// Cập nhật sinh nhật
WebUI.setText(findTestObject('Tai/Update/Page_ThongTinARISTINO/input_Sinhnhat_birthday'), 'abcs')

WebUI.click(findTestObject('Tai/Update/Page_ThongTinARISTINO/button_LUUTHAYDOI'))

WebUI.delay(1)

WebUI.setText(findTestObject('Tai/Update/Page_ThongTinARISTINO/input_Sinhnhat_birthday'), '28/03/2003')

WebUI.click(findTestObject('Tai/Update/Page_ThongTinARISTINO/button_LUUTHAYDOI'))

WebUI.refresh()

WebUI.waitForPageLoad(20)

WebUI.verifyElementText(findTestObject('Tai/Update/Page_ThongTinARISTINO/label_Sinhnhat'),'Sinh nhật')


