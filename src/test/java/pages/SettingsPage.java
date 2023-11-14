package pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class SettingsPage extends BaseTest {

    @AndroidFindBy(accessibility = "test-LOGOUT")
    private WebElement logoutBtn;

    public pages.LoginPage presslogoutBtn() {
        click(logoutBtn);
        return new pages.LoginPage();
    }

}
