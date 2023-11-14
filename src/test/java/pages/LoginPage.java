package pages;

import com.qa.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BaseTest {

    @AndroidFindBy(accessibility = "test-Username")
    private WebElement userNameTxtfld;
    @AndroidFindBy(accessibility = "test-Password")
    private WebElement passwordTxtfld;
    @AndroidFindBy(accessibility = "test-LOGIN")
    private WebElement loginBtn;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
    private WebElement errorMsg;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.TextView")
    private WebElement product;

    public LoginPage enterUserName(String username) {
        System.out.println("login with " + username);
        sendkeys(userNameTxtfld, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        System.out.println("password " + password);
        sendkeys(passwordTxtfld, password);
        return this;
    }

    public ProductsPage pressLoginBtn() {
        System.out.println("press login button");
        click(loginBtn);
        return new ProductsPage();
    }

    public ProductsPage login(String username, String password) {
        enterUserName(username);
        enterPassword(password);
        return pressLoginBtn();
    }

    public String getErrTxt() {
        return getAttribute(errorMsg, "text");
    }

}
