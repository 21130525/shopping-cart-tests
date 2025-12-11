import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import productFilter.FilterKeywords as FU

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://aristino.com/')
FU.closePopupIfExists()
FU.closeAntsBanner()
FU.openProductListPage()

boolean clicked = FU.clickFilter("Nhãn hàng", "Aristino")
println clicked ? "PASS – Filter Nhãn hàng: Aristino" : "FAIL – Không tìm thấy filter"

FU.removeFilter()
WebUI.closeBrowser()
