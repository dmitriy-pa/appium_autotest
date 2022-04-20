package org.example.appium.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.example.appium.driver.AndroidDriverProvider;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite
    public void driverSetup() {
        Configuration.timeout = 12000;
        Configuration.browserSize = null;
        Configuration.browser = AndroidDriverProvider.class.getName();
        Selenide.open();
        closeAdvert();
    }

    protected void closeAdvert() {
        //убираем рекламу
        SelenideElement splashAdvert = Selenide.$x(
                "//*[@resource-id='biz.growapp.winline:id/rootView']/android.widget.ImageView")
                .as("Реклама");
        splashAdvert.should(Condition.visible);
        splashAdvert.click();
        splashAdvert.shouldNot(Condition.visible);
    }

    @AfterSuite
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
