package Tests;

import com.qa.BaseTest;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductsPage;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class LoginTests extends BaseTest {

    LoginPage loginpage;
    ProductsPage productsPage;
    JSONObject loginUsers;

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



    }


    @AfterClass
    public void afterClass() {

    }

    @BeforeMethod
    public void beforeMethod(Method m)
    {
        System.out.println("loginTest before method");

        loginpage = new LoginPage();
        launchApp();

        // for print test method name
        System.out.println("Starting test " + m.getName() + "\n");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("loginTest after method");

        closeApp();
    }

    @Test
    public void invalidUserName() {


        loginpage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
        loginpage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
        loginpage.pressLoginBtn();
        String loginError = getStrings().get("err_invalid_username_or_password");

        Assert.assertEquals(loginpage.getErrTxt(), loginError);
        /*
        try {
        }catch (Exception e)
        {

            StringWriter sw=new StringWriter();
            PrintWriter pw= new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println(sw.toString());
            Assert.fail(sw.toString());

        }



         */
    }

    @Test
    public void successUserName() {
        loginpage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
        loginpage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
        productsPage = loginpage.pressLoginBtn();

        Assert.assertEquals(getStrings().get("product_title"), productsPage.getTitle());


    }


}
