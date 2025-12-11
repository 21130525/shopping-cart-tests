import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/pages/noi-dung-faq")
WebUI.maximizeWindow()
H.comment("=== START TC_FAQ_B2_OpenFirstQuestion ===")

def faqToggle = H.createCssObject(".accordion-toggle")
H.scrollAndClick(faqToggle)
H.verifyElementVisibleCss(".accordion-panel", 5)
H.comment("PASS - FAQ content opened")

WebUI.closeBrowser()
