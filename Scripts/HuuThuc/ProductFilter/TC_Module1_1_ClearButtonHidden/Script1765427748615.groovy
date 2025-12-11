import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import productFilter.FilterKeywords as FU

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://aristino.com/')
FU.closePopupIfExists()
FU.closeAntsBanner()
FU.openProductListPage()

boolean visible = FU.isClearButtonVisible()
println visible ? "FAIL – Clear button visible trước filter" : "PASS – Clear button ẩn đúng"

WebUI.closeBrowser()
