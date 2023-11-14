package pages;


import com.qa.BaseTest;
import com.qa.MenuPage;
import utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class ProductsPage extends MenuPage {


    @AndroidFindBy(xpath = "//android.widget.ScrollView[@content-desc=\"test-PRODUCTS\"]/preceding-sibling::android.view.ViewGroup/android.widget.TextView")
    private WebElement productTitleTxt;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
    private WebElement SLBTitle;
    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
    private WebElement SLBPrice;

    @AndroidFindBy(accessibility = "test-LOGOUT")
    private WebElement logOutBtn;


    public String getTitle() {
        return getAttribute(productTitleTxt, "text");
    }

    public String getSLBTitle() {
        return getAttribute(SLBTitle, "text");
    }

    public String getSLBPrice() {
        return getAttribute(SLBPrice, "text");
    }

    public pages.ProductDetailsPage pressSLBTitle() {
        click(SLBTitle);
        return new pages.ProductDetailsPage();
    }


}
