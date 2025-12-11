import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H
import com.kms.katalon.core.util.KeywordUtil

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A10_OpenFindStorePage ===")

def btnTimCuaHang = H.createCssObject("a.cta-submitform")
H.scrollAndClick(btnTimCuaHang)
WebUI.delay(2)

String currentUrl = WebUI.getUrl()
if(currentUrl.contains("showroom") || currentUrl.contains("he-thong-cua-hang")) {
    H.comment("PASS - navigated to: ${currentUrl}")
} else {
    KeywordUtil.markFailed("URL not contain showroom/he-thong-cua-hang: ${currentUrl}")
}

WebUI.closeBrowser()
