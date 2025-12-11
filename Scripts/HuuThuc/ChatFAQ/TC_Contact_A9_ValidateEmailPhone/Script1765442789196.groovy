import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H
import com.kms.katalon.core.util.KeywordUtil

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A9_ValidateEmailPhone ===")

def btnKhieuNai = H.createCssObject("a[name='email']")
H.scrollAndClick(btnKhieuNai)

H.safeSetText("#youremail", "abc@")
H.safeSetText("#yourphone", "123")

def btnSubmit = H.createCssObject("#addthis-modalContact .btnSubmit-modal")
H.scrollAndClick(btnSubmit)

if(H.verifyInvalidInputPresent(3)) {
    H.comment("PASS - invalid format detected")
} else {
    KeywordUtil.markWarning("Invalid format not detected")
}

WebUI.closeBrowser()
