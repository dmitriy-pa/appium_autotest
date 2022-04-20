package org.example.appium.ui.field;

import com.codeborne.selenide.SelenideElement;
import org.example.appium.ui.As;
import org.example.appium.ui.Widget;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class TextField extends Widget {

    @As("Подсказка")
    @FindBy(id = "biz.growapp.winline:id/tvReg")
    protected SelenideElement hintText;

    @As("Поле ввода")
    @FindBy(xpath = ".//android.widget.EditText")
    protected SelenideElement editText;

    public TextField(SelenideElement self) {
        super(self);
    }

    public TextField startEdit() {
        getSelf().click();
        return this;
    }

    public TextField setText(String text) {
        editText.setValue(text);
        return this;
    }

    public String getText() {
        return editText.getText();
    }

    public TextField checkText(String expectText) {
        Assert.assertEquals(getText(), expectText,
                "Не совпал текст для поля '" + getAliasFull() + "'");
        return this;
    }

    public TextField checkHintText(String expectText) {
        Assert.assertEquals(getHintText(), expectText,
                "Не совпала подсказка для поля '" + getAliasFull() + "'");
        return this;
    }

    public String getHintText() {
        return hintText.getText();
    }
}
