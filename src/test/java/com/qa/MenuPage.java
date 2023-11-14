package com.qa;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pages.SettingsPage;


public class MenuPage extends BaseTest{

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView") private WebElement settingBtn;

    public SettingsPage pressSettingBtn()
    {
        click(settingBtn);
        return new SettingsPage();
    }

}
