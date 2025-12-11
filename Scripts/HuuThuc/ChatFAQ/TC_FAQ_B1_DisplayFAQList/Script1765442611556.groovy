import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/pages/noi-dung-faq")
WebUI.maximizeWindow()
H.comment("=== START TC_FAQ_B1_DisplayFAQList ===")

H.verifyElementPresentCss(".accordion-toggle")
H.comment("PASS - FAQ list displayed")

WebUI.closeBrowser()
