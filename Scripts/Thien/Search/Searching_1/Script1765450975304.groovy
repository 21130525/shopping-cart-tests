import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.testng.asserts.SoftAssert as SoftAssert
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

SoftAssert softAssertion = new SoftAssert()

// Bước 1: mở browser Google (giữ nguyên)
WebUI.openBrowser('https://www.google.com/')

// Bước 2: chuyển driver vào biến (giữ nguyên)
def driver = com.kms.katalon.core.webui.driver.DriverFactory.getWebDriver()

// Điều hướng tới trang Aristino
WebUI.navigateToUrl('https://aristino.com/')

// Chờ trang load hoàn toàn
WebUI.waitForPageLoad(10)


// ============================
// KIỂM TRA SLIDEDOWN TRONG 3s
// ============================
TestObject slideDown = new TestObject('slidedown')
slideDown.addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS,
    "//div[@id='antsomi-slidedown-dialog']")

boolean isSlideDownVisible = WebUI.waitForElementVisible(slideDown, 3, FailureHandling.OPTIONAL)

// Nếu popup xuất hiện → bấm Cancel
if (isSlideDownVisible) {
    WebUI.click(findTestObject('Object Repository/Thien/Search/button_cancel_slidedown'))
}

WebUI.click(findTestObject('Object Repository/Thien/Search/js-click-search'))

WebUI.setText(findTestObject('Object Repository/Thien/Search/input_SUPPORT_inputSearchAuto'), 'quần')

WebUI.sendKeys(findTestObject('Object Repository/Thien/Search/input_SUPPORT_inputSearchAuto'), Keys.chord(Keys.ENTER))


// chờ thêm 7 giây để quan sát
WebUI.delay(7)
// Chụp màn hình trước khi đóng browser
WebUI.takeScreenshot()
WebUI.closeBrowser()


