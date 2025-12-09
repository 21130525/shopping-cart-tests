import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject // <--- Anh đang thiếu dòng này
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable


WebUI.openBrowser('')

WebUI.navigateToUrl('https://aristino.com/collections/trang-phuc')

TestObject alertObj = findTestObject('Object Repository/midtern/Page_Register/Page_alert/alert_nitification')

TestObject btnAllow = findTestObject('Object Repository/midtern/Page_Register/Page_alert/button_chophep')

TestObject btnCloseAd = findTestObject('Object Repository/midtern/Page_Register/Page_adventis/btn_closeAd')

if (WebUI.waitForElementVisible(alertObj, 5, FailureHandling.OPTIONAL)) {
    WebUI.click(btnAllow)

    println('Đã click nút Cho phép.')
}


if (WebUI.waitForElementVisible(btnCloseAd, 5, FailureHandling.OPTIONAL)) {
    WebUI.click(btnCloseAd)

    println('Đã tắt quảng cáo.')
}


WebUI.click(findTestObject('Object Repository/midtern/Page_home/product'))

if (WebUI.waitForElementVisible(alertObj, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(btnAllow)

	println('Đã click nút Cho phép.')
}


if (WebUI.waitForElementVisible(btnCloseAd, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(btnCloseAd)

	println('Đã tắt quảng cáo.')
}


WebUI.verifyElementText(findTestObject('Object Repository/midtern/Page_product-detail/button_addtocart'), 'ĐẶT HÀNG TRƯỚC')

WebUI.verifyElementClickable(findTestObject('midtern/Page_product-detail/size_S'))

WebUI.verifyElementClickable(findTestObject('midtern/Page_product-detail/size_M'))

WebUI.verifyElementClickable(findTestObject('midtern/Page_product-detail/size_L'))

WebUI.verifyElementClickable(findTestObject('midtern/Page_product-detail/size_XL'))

WebUI.verifyElementClickable(findTestObject('midtern/Page_product-detail/size_XXL'))

WebUI.verifyElementVisible(findTestObject('midtern/Page_product-detail/div_image'))

WebUI.verifyElementVisible(findTestObject('midtern/Page_product-detail/div_image'))

WebUI.click(findTestObject('midtern/Page_product-detail/div_image'))


