package helpers

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import java.util.Arrays

class WebUIHelper {

    static TestObject createCssObject(String css) {
        return new TestObject().addProperty("css", ConditionType.EQUALS, css)
    }

    static void comment(String msg) {
        KeywordUtil.logInfo(msg)
    }

    static void scrollAndClick(TestObject to, FailureHandling fh = FailureHandling.STOP_ON_FAILURE) {
        try {
            WebUI.scrollToElement(to, 3, fh)
            WebUI.waitForElementClickable(to, 5, fh)
            WebUI.click(to, fh)
        } catch (Exception e) {
            WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUI.findWebElement(to, 5)))
        }
    }

    static void safeSetText(String css, String text, FailureHandling fh = FailureHandling.STOP_ON_FAILURE) {
        TestObject to = createCssObject(css)
        try {
            WebUI.waitForElementVisible(to, 5, fh)
            WebUI.setText(to, text, fh)
        } catch (Exception e) {
            String script = "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
                            "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));"
            WebUI.executeJavaScript(script, Arrays.asList(WebUI.findWebElement(to), text))
        }
    }

    static boolean verifyInvalidInputPresent(int timeout = 3) {
        try {
            return WebUI.verifyElementPresent(createCssObject("input:invalid, textarea:invalid"), timeout, FailureHandling.OPTIONAL)
        } catch (Exception e) {
            return WebUI.verifyElementPresent(createCssObject(".is-invalid, .input-error"), timeout, FailureHandling.OPTIONAL)
        }
    }

    static void verifyElementPresentCss(String css, int timeout = 5, FailureHandling fh = FailureHandling.STOP_ON_FAILURE) {
        TestObject to = createCssObject(css)
        WebUI.verifyElementPresent(to, timeout, fh)
    }

    static void verifyElementVisibleCss(String css, int timeout = 5, FailureHandling fh = FailureHandling.STOP_ON_FAILURE) {
        TestObject to = createCssObject(css)
        WebUI.waitForElementVisible(to, timeout, fh)
    }

    static void checkBtn(String name, String cssSelector, FailureHandling fh = FailureHandling.STOP_ON_FAILURE) {
        verifyElementPresentCss(cssSelector, 5, fh)
        comment("${name} present -> ${cssSelector}")
    }
}
