import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import productFilter.FilterKeywords as FU

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://aristino.com/')
FU.closePopupIfExists()
FU.closeAntsBanner()
FU.openProductListPage()

List rareFilters = [
    ["Sản phẩm", "Áo Siêu Hiếm 123"],
    ["Màu sắc", "Hồng Neon"],
    ["Kích cỡ", "XXXL"]
]

for (rf in rareFilters) FU.clickFilter(rf[0], rf[1])

def noProductMsg = FU.xpath("//*[contains(text(),'Không tìm thấy sản phẩm') or contains(text(),'No products found') or contains(text(),'Không có sản phẩm phù hợp')]")
boolean displayed = WebUI.verifyElementPresent(noProductMsg, 3, com.kms.katalon.core.model.FailureHandling.OPTIONAL)
println displayed ? "PASS – Không có sản phẩm phù hợp hiển thị đúng" : "FAIL – Không thấy thông báo"

FU.removeFilter()
WebUI.closeBrowser()
