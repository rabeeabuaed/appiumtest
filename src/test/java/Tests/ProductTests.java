package Tests;

import com.qa.BaseTest;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import pages.SettingsPage;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class ProductTests extends BaseTest {

    LoginPage loginpage;
    ProductsPage productsPage;

    JSONObject loginUsers;
    SettingsPage settingspage;
    ProductDetailsPage productDetailsPage;

    @BeforeClass
    public void beforeClass() throws IOException {
        InputStream datais = null;
        try {
            String dataFileName = "data/loginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(datais);
            loginUsers = new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (datais != null)
                datais.close();
        }
        // its better to put tow next lines in before method or after method




    }


    @AfterClass
    public void afterClass() {

    }

    @BeforeMethod
    public void beforeMethod(Method m) {
        loginpage = new LoginPage();

        launchApp();

        // for print test method name
        System.out.println("Starting test " + m.getName() + "\n");
    }

    @AfterMethod
    public void afterMethod() {
        closeApp();
    }

    @Test
    public void validateProductOnProductsPage() {
        SoftAssert sa = new SoftAssert();
        productsPage = loginpage.login(loginUsers.getJSONObject("validUser").getString("username"), loginUsers.getJSONObject("invalidUser").getString("password"));
        String SLBTitle = productsPage.getSLBTitle();
        sa.assertEquals(SLBTitle, getStrings().get("products_page_slb_title"));

        String SLBPrice = productsPage.getSLBPrice();

        sa.assertEquals(SLBPrice, getStrings().get("products_page_slb_price"));

        settingspage = productsPage.pressSettingBtn();
        loginpage = settingspage.presslogoutBtn();

        sa.assertAll();

    }

    @Test
    public void validateProductOnProductDetailsPage() {
        SoftAssert sa = new SoftAssert();
        productsPage = loginpage.login(loginUsers.getJSONObject("validUser").getString("username"), loginUsers.getJSONObject("invalidUser").getString("password"));
        productDetailsPage = productsPage.pressSLBTitle();

        String SLBTitle = productDetailsPage.getSLBTitle();

        sa.assertEquals(SLBTitle, getStrings().get("product_details_page_slb_title"));

        String SLBTxt = productDetailsPage.getSLBTxt();

        sa.assertEquals(SLBTxt, getStrings().get("product_details_page_slb_txt"));

        String SLBPrice = productDetailsPage.getSLBPrice();

        sa.assertEquals(SLBPrice, getStrings().get("product_details_page_slb_price"));


        productDetailsPage.scrollToterms();
        String terms = productDetailsPage.getTerms();
        sa.assertEquals(terms, getStrings().get("product_details_page_slb_Terms"));


        productsPage = productDetailsPage.pressBackToProductsBtn();
        settingspage = productsPage.pressSettingBtn();
        loginpage = settingspage.presslogoutBtn();
        sa.assertAll();

    }


}
