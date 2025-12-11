import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A5_OpenModal ===")

def btnKhieuNai = H.createCssObject("a[name='email']")
H.scrollAndClick(btnKhieuNai)
def modalContent = H.createCssObject("#addthis-modalContact .modal-content")
WebUI.waitForElementVisible(modalContent, 7)

H.comment("PASS - Modal Liên hệ hiển thị")

WebUI.closeBrowser()
