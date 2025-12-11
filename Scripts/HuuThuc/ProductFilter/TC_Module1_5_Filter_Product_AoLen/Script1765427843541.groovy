import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import productFilter.FilterKeywords as FU

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://aristino.com/')
FU.closePopupIfExists()
FU.closeAntsBanner()
FU.openProductListPage()

boolean clicked = FU.clickFilter("Sản phẩm", "Áo Polo dài tay")
println clicked ? "PASS – Filter Sản phẩm: Áo Polo tay dài" : "FAIL – Không tìm thấy filter"

FU.removeFilter()
WebUI.closeBrowser()
