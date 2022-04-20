package org.example.appium.ui;

import org.openqa.selenium.By;

public abstract class Locate {
    private Locate() {
    }

    public static By nativeButton(String text) {
        return By.xpath(".//android.widget.Button[@text = '"+text+"']");
    }

    public static By popupMessage(String title) {
        return By.xpath(
                ".//android.widget.TextView[@text = '"+title+"']" +
                        "/ancestor::*[@resource-id='biz.growapp.winline:id/parentPanel']");
    }

    public static By popupDatePicker() {
        return By.xpath(".//android.widget.DatePicker/ancestor::*[@resource-id='android:id/parentPanel']");
    }
}
