import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WebUIHelper as H

WebUI.openBrowser('')
WebUI.navigateToUrl("https://aristino.com/pages/he-thong-cua-hang")
WebUI.maximizeWindow()
H.comment("=== START TC_Contact_A13_SearchByDistrict ===")

def provinceSelect = H.createCssObject(".province-select")
WebUI.waitForElementVisible(provinceSelect, 5)
WebUI.selectOptionByLabel(provinceSelect, "Hồ Chí Minh", false)

def districtSelect = H.createCssObject(".district-select")
WebUI.waitForElementVisible(districtSelect, 5)
try {
    WebUI.selectOptionByLabel(districtSelect, "Quận 1", false)
} catch(Exception e) {
    WebUI.executeJavaScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change'));",
        Arrays.asList(WebUI.findWebElement(districtSelect), "Quận 1"))
}

def btnSearch = H.createCssObject(".find-stores-btn")
H.scrollAndClick(btnSearch)
WebUI.delay(1)
H.comment("PASS - searched by district Qu
