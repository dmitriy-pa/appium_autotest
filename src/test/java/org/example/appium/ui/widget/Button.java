package org.example.appium.ui.widget;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.example.appium.ui.Widget;
import org.testng.Assert;

public class Button extends Widget {
    public Button(SelenideElement self) {
        super(self);
    }

    public String getText() {
        return getSelf().getText();
    }

    public Button checkText() {
        return checkText(getAlias());
    }

    public Button checkText(String expectText) {
        Assert.assertEquals(getText(), expectText,
                "Не совпал текст кнопки '" + getAlias() + "'");
        return this;
    }

    public Button click() {
        getSelf().click();
        return this;
    }

    public boolean isEnabled() {
        return "true".equals(getSelf().getAttribute(ENABLED));
    }

    public Button shouldNotEnabled() {
        getSelf().shouldNot(Condition.attribute(ENABLED, "true"));
        return this;
    }

    public Button shouldEnabled() {
        getSelf().should(Condition.attribute(ENABLED, "true"));
        return this;
    }
}
