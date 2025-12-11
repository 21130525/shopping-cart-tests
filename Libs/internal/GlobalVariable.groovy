package internal

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.main.TestCaseMain


/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */
public class GlobalVariable {
     
    /**
     * <p></p>
     */
    public static Object urlLogin
     
    /**
     * <p></p>
     */
    public static Object urlRegister
     
    /**
     * <p></p>
     */
    public static Object username
     
    /**
     * <p></p>
     */
    public static Object password
     
    /**
     * <p></p>
     */
    public static Object firstName
     
    /**
     * <p></p>
     */
    public static Object lastName
     
    /**
     * <p></p>
     */
    public static Object waitPresentTimeout
     
    /**
     * <p></p>
     */
    public static Object urlShop
     
    /**
     * <p></p>
     */
    public static Object companyName
     
    /**
     * <p></p>
     */
    public static Object address
     
    /**
     * <p></p>
     */
    public static Object city
     
    /**
     * <p></p>
     */
    public static Object country
     
    /**
     * <p></p>
     */
    public static Object postCode
     
    /**
     * <p></p>
     */
    public static Object Phone
     
    /**
     * <p></p>
     */
    public static Object uploadPlaceOrderTimeout
     
    /**
     * <p></p>
     */
    public static Object dataFile
     
    /**
     * <p></p>
     */
    public static Object TEN_SAN_PHAM
     

    static {
        try {
            def selectedVariables = TestCaseMain.getGlobalVariables("default")
			selectedVariables += TestCaseMain.getGlobalVariables(RunConfiguration.getExecutionProfile())
    
            urlLogin = selectedVariables['urlLogin']
            urlRegister = selectedVariables['urlRegister']
            username = selectedVariables['username']
            password = selectedVariables['password']
            firstName = selectedVariables['firstName']
            lastName = selectedVariables['lastName']
            waitPresentTimeout = selectedVariables['waitPresentTimeout']
            urlShop = selectedVariables['urlShop']
            companyName = selectedVariables['companyName']
            address = selectedVariables['address']
            city = selectedVariables['city']
            country = selectedVariables['country']
            postCode = selectedVariables['postCode']
            Phone = selectedVariables['Phone']
            uploadPlaceOrderTimeout = selectedVariables['uploadPlaceOrderTimeout']
            dataFile = selectedVariables['dataFile']
            TEN_SAN_PHAM = selectedVariables['TEN_SAN_PHAM']
            
        } catch (Exception e) {
            TestCaseMain.logGlobalVariableError(e)
        }
    }
}
