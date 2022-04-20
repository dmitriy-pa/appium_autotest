package org.example.appium.ui.field;

import com.codeborne.selenide.SelenideElement;
import org.example.appium.ui.As;
import org.example.appium.ui.Widget;
import org.example.appium.ui.widget.DatePicker;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.Date;

import static org.example.appium.tools.DateTools.*;


public class BirthdayField extends Widget {

    @As("Подсказка")
    @FindBy(id = "biz.growapp.winline:id/tvBirthdayHint")
    protected SelenideElement hintText;

    @As("Подсказка при заполненном поле")
    @FindBy(id = "biz.growapp.winline:id/tvBirthdayFilledText")
    protected SelenideElement filledHintText;

    @As("Значение поля")
    @FindBy(id = "biz.growapp.winline:id/tvBirthdayFilled")
    protected SelenideElement editText;

    public BirthdayField(SelenideElement self) {
        super(self);
    }

    public String getHintText() {
        return getDate() == null ? hintText.getText() : filledHintText.getText();
    }

    public BirthdayField checkHintText(String expectText) {
        Assert.assertEquals(getHintText(), expectText,
                "Не совпала подсказка для поля '" + getAliasFull() + "'");
        return this;
    }

    public Date getDate() {
        if (editText.exists()) {
            String text = editText.getText();
            if (!text.isEmpty()) {
                return formatSimple(text);
            }
        }
        return null;
    }

    public BirthdayField setDate(Date bDate) {
        getSelf().click();
        new DatePicker()
                .shouldVisible()
                .peek(bDate)
                .clickOK()
                .shouldNotVisible();
        return this;
    }

    public BirthdayField checkDate(Date bDate) {
        Assert.assertEquals(dropTime(bDate), dropTime(getDate()),
                "Не совпало значение поля '" + getAliasFull() + "'");
        return this;
    }
}
