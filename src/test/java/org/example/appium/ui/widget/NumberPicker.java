package org.example.appium.ui.widget;

import com.codeborne.selenide.SelenideElement;
import org.example.appium.ui.As;
import org.example.appium.ui.Widget;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class NumberPicker extends Widget {

    @As("Кнопка вверх")
    @FindBy(xpath = ".//android.widget.Button[1]")
    private Button up;

    @As("Кнопка вниз")
    @FindBy(xpath = ".//android.widget.Button[2]")
    private Button down;

    @As("Текущее значение")
    @FindBy(id = "android:id/numberpicker_input")
    private Text value;

    public NumberPicker(SelenideElement self) {
        super(self);
    }

    public NumberPicker shouldValue(String expect) {
        value.shouldText(expect);
        return this;
    }

    public String getValue() {
        return value.getText();
    }

    public String up() {
        String nextValue = up.getText();
        up.click();
        shouldValue(nextValue);
        return nextValue;
    }

    public NumberPicker upTo(String text, int limit) {
        if (text.equals(getValue())) {
            return this;
        }
        while (!text.equals(up())) {
            limit--;
            Assert.assertTrue(limit > 0,
                    "Не найдено значение '" + text + "' для '" + getAliasFull() + "'");
        }
        return this;
    }

    public String down() {
        String nextValue = down.getText();
        down.click();
        shouldValue(nextValue);
        return nextValue;
    }

    public NumberPicker downTo(String text, int limit) {
        if (text.equals(getValue())) {
            return this;
        }
        while (!text.equals(down())) {
            limit--;
            Assert.assertTrue(limit > 0,
                    "Не найдено значение '" + text + "' для '" + getAliasFull() + "'");
        }
        return this;
    }
}
