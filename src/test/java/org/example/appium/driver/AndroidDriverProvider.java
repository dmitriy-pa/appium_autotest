package org.example.appium.driver;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AndroidDriverProvider implements WebDriverProvider {
    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.merge(capabilities);
        options.setUdid("R58M64ZKWLB");
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setPlatformName("Android");
        options.setDeviceName("Galaxy A50");
        options.setPlatformVersion("11.0");
        options.setCapability(CapabilityType.APPLICATION_NAME, "Appium");
        options.setAppPackage("biz.growapp.winline");
        options.setAppActivity("biz.growapp.winline.presentation.splash.SplashActivity");
        options.setNewCommandTimeout(Duration.ofSeconds(8));
        options.setFullReset(false);
        try {
            return new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
