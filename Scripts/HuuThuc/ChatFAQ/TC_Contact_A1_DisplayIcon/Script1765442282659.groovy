import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A1_DisplayIcon ===")

def iconLienHe = H.createCssObject(".box-item.box-contact .svgico")
WebUI.verifyElementPresent(iconLienHe, 7)
H.scrollAndClick(iconLienHe)

H.comment("PASS - Icon Liên hệ hiển thị")
WebUI.closeBrowser()
