import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import productFilter.FilterKeywords as FU

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://aristino.com/')
FU.closePopupIfExists()
FU.closeAntsBanner()
FU.openProductListPage()

boolean clicked = FU.clickFilter("Form dáng", "Slim fit")
println clicked ? "PASS – Filter Form dáng: Slim fit" : "FAIL – Không tìm thấy filter"

FU.removeFilter()
WebUI.closeBrowser()
