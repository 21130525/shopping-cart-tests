import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A6_CheckFormFields ===")

def btnKhieuNai = H.createCssObject("a[name='email']")
H.scrollAndClick(btnKhieuNai)

["#yourname", "#youremail", "#yourphone", "#yourinfor"].each { selector ->
    H.verifyElementPresentCss(selector)
}

H.comment("PASS - All form fields present")

WebUI.closeBrowser()
