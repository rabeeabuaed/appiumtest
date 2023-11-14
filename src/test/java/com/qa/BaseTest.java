package com.qa;


import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.TestUtils;

import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Base64;

/**
 * The BaseTest class serves as a foundation for creating test automation scripts using the Appium framework.
 * It provides common methods for interacting with elements, managing the driver, and handling test-specific settings.
 */

public class BaseTest {






    // Thread-local variables for managing driver instances and test data

    protected static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
    protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>();
    protected static ThreadLocal <HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
    protected static ThreadLocal <String> platform = new ThreadLocal<String>();
    protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();
    protected static ThreadLocal <String> deviceName = new ThreadLocal<String>();
    // private static AppiumDriverLocalService server;
    TestUtils utils = new TestUtils();

    static Logger log= LogManager.getLogger(BaseTest.class.getName());

    public BaseTest() {
        // Initialize elements using AppiumFieldDecorator
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }


    // Method to get the driver instance for the current thread
    public AppiumDriver getDriver() {
        return driver.get();
    }

    public void setDriver(AppiumDriver driver2) {
        driver.set(driver2);
    }

    public Properties getProps() {
        return props.get();
    }

    public void setProps(Properties props2) {
        props.set(props2);
    }

    public HashMap<String, String> getStrings() {
        return strings.get();
    }

    public void setStrings(HashMap<String, String> strings2) {
        strings.set(strings2);
    }

    public String getPlatform() {
        return platform.get();
    }

    public void setPlatform(String platform2) {
        platform.set(platform2);
    }

    public String getDateTime() {
        return dateTime.get();
    }

    public void setDateTime(String dateTime2) {
        dateTime.set(dateTime2);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }




    @BeforeMethod    // BeforeMethod annotation method

    public void beforeMethode()
    {
        System.out.println("super before method");
        // Start screen recording before each test method
        ((CanRecordScreen)getDriver()).startRecordingScreen();
    }
    @AfterMethod	//stop video capturing and create *.mp4 file

    public void afterMethod(ITestResult result) throws FileNotFoundException {
        System.out.println("super after method");
        // Stop screen recording and save as video file

        String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();

        if (result.getStatus()==2)
        {
            Map<String,String> params=result.getTestContext().getCurrentXmlTest().getAllParameters();
            String dir = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName")
                    + File.separator + getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();

            File videoDir=new File(dir);
            synchronized(videoDir){
                if(!videoDir.exists()) {
                    videoDir.mkdirs();
                }
            }
            FileOutputStream stream = null;

            try {
                stream=new FileOutputStream(videoDir+File.separator+result.getName()+".mp4");
                stream.write(Base64.getDecoder().decode(media));
                stream.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }



    }

    @Parameters({"platformName", "deviceName","UDID"})
    @BeforeTest
    public void beforeTest(String platformName, String deviceName ,String UDID) throws Exception {
        ThreadContext.put("ROUTINGKEY", "logs");

        log.info("This is beforeTest");

        InputStream inputStream = null;
        InputStream stringsis = null;
        utils = new TestUtils();
        AppiumDriver driver;
        setPlatform(platformName);
        setDeviceName(deviceName);

        setDateTime(utils.DateTime());
        Properties props=new Properties();
        try {
            props = new Properties();
            String propFileName = "config.properties";
            String xmlFileName = "strings/strings.xml";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);
            setProps(props);

            stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);

            setStrings(utils.parseStringXML(stringsis));

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName", platformName);
            desiredCapabilities.setCapability("deviceName", deviceName);
            //  desiredCapabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
            desiredCapabilities.setCapability(MobileCapabilityType.UDID, UDID);


            desiredCapabilities.setCapability("automationName", props.getProperty("androidAutomationName"));
            desiredCapabilities.setCapability("appPackage", props.getProperty("androidappPackage"));
            desiredCapabilities.setCapability("appActivity", props.getProperty("androidappActivity"));
            URL appurl = getClass().getClassLoader().getResource(props.getProperty("andoridAppLocation"));



            desiredCapabilities.setCapability("app", appurl);
            URL url = new URL(props.getProperty("appiumURL"));
            //to determine which port number you will use for android
            //URL url = new URL(props.getProperty("appiumURL"+"4723"));
            driver = new AndroidDriver(url, desiredCapabilities);

            setDriver(driver);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (stringsis != null) {
                stringsis.close();
            }
        }
    }

    // Wait for an element to become visible
    public void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));

    }

    // Click on an element after ensuring its visibility
    public void click(WebElement element) {
        waitForVisibility(element);
        element.click();

    }

    // Enter text into an element after ensuring its visibility
    public void sendkeys(WebElement element, String txt) {
        waitForVisibility(element);
        element.sendKeys(txt);

    }

    // Get the value of a specified attribute of an element after ensuring its visibility
    public String getAttribute(WebElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);


    }


    // AfterTest annotation method
    @AfterTest
    public void afterTest() throws InterruptedException {
        Thread.sleep(7000);
        getDriver().quit();


    }

    // Clear the input of an element after ensuring its visibility
    public void clear(WebElement e) {
        waitForVisibility(e);
        e.clear();
    }

    // Close the tested app
    public void closeApp() {


        ((InteractsWithApps) getDriver()).terminateApp((getProps().getProperty("androidappPackage")));

    }

    // Launch the tested app
    public void launchApp() {


        ((InteractsWithApps) getDriver()).activateApp((getProps().getProperty("androidappPackage")));

    }


    // Scroll to a specific element on the screen
    public WebElement scrollToElement() {
        return getDriver().findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().text(\"Terms of Service | Privacy Policy\"));"));       /*
        return getDriver().findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector().description(\"test-Price\"));"));*/
    }





}

