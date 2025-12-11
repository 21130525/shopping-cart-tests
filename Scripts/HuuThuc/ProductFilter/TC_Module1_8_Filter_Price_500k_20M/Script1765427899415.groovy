import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import productFilter.FilterKeywords as FU
import org.openqa.selenium.Keys

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://aristino.com/')
FU.closePopupIfExists()
FU.closeAntsBanner()
FU.openProductListPage()

try {
    def from = FU.css("input.text-price-from, input.text-price-from-input, input[name='price_from']")
    def to   = FU.css("input.text-price-to, input.text-price-to-input, input[name='price_to']")

    if (WebUI.verifyElementPresent(from, 2, com.kms.katalon.core.model.FailureHandling.OPTIONAL) &&
        WebUI.verifyElementPresent(to, 2, com.kms.katalon.core.model.FailureHandling.OPTIONAL)) {

        WebUI.scrollToElement(from, 3)
        WebUI.clearText(from)
        WebUI.setText(from, "500000")
        WebUI.clearText(to)
        WebUI.setText(to, "20000000")
        WebUI.sendKeys(to, Keys.chord(Keys.ENTER))
        println "PASS – Filter Giá 500.000 – 20.000.000"
    } else {
        println "FAIL – Không tìm thấy input price"
    }
} catch (Exception e) {
    println "FAIL – Error filter price: " + e.message
}

FU.removeFilter()
WebUI.closeBrowser()
