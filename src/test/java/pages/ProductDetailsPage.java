package pages;

import com.qa.MenuPage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProductDetailsPage extends MenuPage {


    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]"
            + "")
    private WebElement SLBTxt;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]\n" +
            "")
    private WebElement SLBTitle;

    @AndroidFindBy(xpath ="//android.widget.ScrollView[@content-desc=\"test-Inventory item page\"]/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.TextView[5]")
    private WebElement terms;

    @AndroidFindBy(accessibility = "test-Price")
    private WebElement SLBPrice;
    @AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
    private WebElement backToProductBtn;


    public String getSLBTitle() {
        return getAttribute(SLBTitle, "text");
    }

    public String getSLBTxt() {
        return getAttribute(SLBTxt, "text");
    }

    public String getSLBPrice() {
        String price = getAttribute(SLBPrice, "text");
        return price;
    }

    public String getTerms() {
        return getAttribute(terms, "text");
    }

    public ProductDetailsPage scrollToterms()
    {


        scrollToElement();
        return this;
    }


    public ProductsPage pressBackToProductsBtn() {
        click(backToProductBtn);
        return new ProductsPage();
    }

}
