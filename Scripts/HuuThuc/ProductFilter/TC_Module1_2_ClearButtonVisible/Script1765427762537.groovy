import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import productFilter.FilterKeywords as FU

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://aristino.com/')
FU.closePopupIfExists()
FU.closeAntsBanner()
FU.openProductListPage()

boolean clicked = FU.clickFilter("Màu sắc", "Đen")
boolean visible = FU.isClearButtonVisible()

if (!clicked) println "FAIL – Cannot click filter"
else println visible ? "PASS – Clear button hiện sau filter" : "FAIL – Clear button không hiện"

FU.removeFilter()
WebUI.closeBrowser()
