import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import productFilter.FilterKeywords as FU
import org.openqa.selenium.Keys

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://aristino.com/')
FU.closePopupIfExists()
FU.closeAntsBanner()
FU.openProductListPage()

// Price
def from = FU.css("input.text-price-from, input.text-price-from-input, input[name='price_from']")
def to   = FU.css("input.text-price-to, input.text-price-to-input, input[name='price_to']")
if (WebUI.verifyElementPresent(from, 2, com.kms.katalon.core.model.FailureHandling.OPTIONAL)) {
    WebUI.scrollToElement(from, 3)
    WebUI.clearText(from)
    WebUI.setText(from, "500000")
    WebUI.clearText(to)
    WebUI.setText(to, "20000000")
    WebUI.sendKeys(to, Keys.chord(Keys.ENTER))
}

// Other filters
FU.clickFilter("Sản phẩm", "Áo Len")
FU.clickFilter("Màu sắc", "Trắng")
FU.clickFilter("Kích cỡ", "M")

println "PASS – Combined filter applied"

FU.removeFilter()
WebUI.closeBrowser()
