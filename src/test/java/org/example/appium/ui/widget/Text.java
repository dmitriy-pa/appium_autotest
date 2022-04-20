package org.example.appium.ui.widget;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.example.appium.ui.Widget;
import org.testng.Assert;

public class Text extends Widget {
    public Text(SelenideElement self) {
        super(self);
    }

    public String getText() {
        return getSelf().getText();
    }

    public Text checkText() {
        return checkText(getAlias());
    }

    public Text checkText(String expectText) {
        Assert.assertEquals(getText(), expectText,
                "Не совпал текст надписи '" + getAlias() + "'");
        return this;
    }

    public Text shouldText(String expectText) {
        getSelf().should(Condition.exactText(expectText));
        return this;
    }


}
