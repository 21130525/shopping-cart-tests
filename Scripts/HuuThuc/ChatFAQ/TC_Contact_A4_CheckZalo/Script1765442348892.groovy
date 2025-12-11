import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A4_CheckZalo ===")

H.checkBtn("Zalo", "a[href*='zalo']")

WebUI.closeBrowser()
