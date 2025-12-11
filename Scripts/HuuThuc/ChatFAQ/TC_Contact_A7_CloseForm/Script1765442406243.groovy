import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A7_CloseForm ===")

def btnKhieuNai = H.createCssObject("a[name='email']")
H.scrollAndClick(btnKhieuNai)

def btnClose = H.createCssObject("#addthis-modalContact .close")
H.scrollAndClick(btnClose)

H.comment("PASS - Modal closed")
WebUI.closeBrowser()
