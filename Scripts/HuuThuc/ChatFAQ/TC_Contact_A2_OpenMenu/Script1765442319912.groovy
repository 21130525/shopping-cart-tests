import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A2_OpenMenu ===")

def iconLienHe = H.createCssObject(".box-item.box-contact .svgico")
H.scrollAndClick(iconLienHe)
H.comment("PASS - Menu Liên hệ mở thành công")

WebUI.closeBrowser()
