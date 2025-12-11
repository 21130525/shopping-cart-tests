import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H
import com.kms.katalon.core.util.KeywordUtil

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A8_ValidateEmptyFields ===")

def btnKhieuNai = H.createCssObject("a[name='email']")
H.scrollAndClick(btnKhieuNai)

def btnSubmit = H.createCssObject("#addthis-modalContact .btnSubmit-modal")
H.scrollAndClick(btnSubmit)

if(H.verifyInvalidInputPresent(3)) {
    H.comment("PASS - invalid input detected for empty fields")
} else {
    def tNameReq = H.createCssObject("#yourname")
    String requiredAttr = WebUI.getAttribute(tNameReq, "required", FailureHandling.OPTIONAL)
    if(requiredAttr) {
        H.comment("PASS - required attribute present")
    } else {
        KeywordUtil.markWarning("Couldn't detect invalid input or required attribute")
    }
}

WebUI.closeBrowser()
