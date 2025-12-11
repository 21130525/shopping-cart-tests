import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/pages/noi-dung-faq")
WebUI.maximizeWindow()
H.comment("=== START TC_FAQ_B3_CloseFirstQuestion ===")

def faqToggle = H.createCssObject(".accordion-toggle")
H.scrollAndClick(faqToggle)  // open
H.scrollAndClick(faqToggle)  // close
WebUI.delay(0.5)
WebUI.verifyElementNotVisible(H.createCssObject(".accordion-panel"))
H.comment("PASS - FAQ content closed")

WebUI.closeBrowser()
