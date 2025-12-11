import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import productFilter.FilterKeywords as FU

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://aristino.com/')
FU.closePopupIfExists()
FU.closeAntsBanner()
FU.openProductListPage()

boolean allSeen = true
String[] filterLabels = ["Giá", "Loại", "Màu sắc", "Kích cỡ", "Reset Filter", "Nhãn hàng", "Sản phẩm", "Form dáng"]

for (String label : filterLabels) {
    def to = FU.xpath("//span[contains(normalize-space(.),'"+label+"') or contains(text(),'"+label+"') or //label[contains(.,'"+label+"')]]")
    if (WebUI.verifyElementPresent(to, 3, com.kms.katalon.core.model.FailureHandling.OPTIONAL)) {
        println "PASS – Thấy mục: ${label}"
    } else {
        println "FAIL – Không thấy mục: ${label}"
        allSeen = false
    }
}

WebUI.closeBrowser()
