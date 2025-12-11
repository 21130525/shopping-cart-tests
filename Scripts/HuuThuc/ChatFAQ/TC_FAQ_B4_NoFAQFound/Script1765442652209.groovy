import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/pages/noi-dung-faq")
WebUI.maximizeWindow()
H.comment("=== START TC_FAQ_B4_NoFAQFound ===")

if(!WebUI.verifyElementPresent(H.createCssObject(".accordion-toggle"), 5, FailureHandling.OPTIONAL)) {
    H.comment("PASS - No FAQ found")
} else {
    H.comment("FAQ list exists")
}

WebUI.closeBrowser()
