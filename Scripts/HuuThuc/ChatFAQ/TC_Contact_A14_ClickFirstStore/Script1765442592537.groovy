import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H
import com.kms.katalon.core.util.KeywordUtil

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/he-thong-cua-hang")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A14_ClickFirstStore ===")

def firstStore = H.createCssObject(".store-list .store-item:first-child")
if(WebUI.waitForElementVisible(firstStore, 10, FailureHandling.OPTIONAL)) {
    H.scrollAndClick(firstStore)
    H.comment("PASS - clicked first store")
} else {
    KeywordUtil.markWarning("First store not found")
}

WebUI.closeBrowser()
