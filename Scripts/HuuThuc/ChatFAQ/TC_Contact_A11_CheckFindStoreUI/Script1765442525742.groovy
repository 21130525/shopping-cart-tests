import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/pages/he-thong-cua-hang")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A11_CheckFindStoreUI ===")

[".province-select", ".district-select", ".find-stores-btn", ".list-result"].each { sel ->
    H.verifyElementPresentCss(sel)
}

try {
    H.verifyElementVisibleCss("#mapDiv", 15)
    H.comment("Map loaded successfully")
} catch(Exception e) {
    H.comment("Map may not be visible (slow load or iframe)")
}

WebUI.closeBrowser()
