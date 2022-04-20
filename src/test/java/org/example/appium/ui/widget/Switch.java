package org.example.appium.ui.widget;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.example.appium.ui.Widget;

public class Switch extends Widget {

    public Switch(SelenideElement self) {
        super(self);
    }

    public boolean isChecked() {
        return "true".equals(getSelf().getAttribute(CHECKED));
    }

    public Switch shouldNotChecked() {
        getSelf().shouldNot(Condition.attribute(CHECKED, "true"));
        return this;
    }

    public Switch shouldChecked() {
        getSelf().should(Condition.attribute(CHECKED, "true"));
        return this;
    }

    public Switch click() {
        getSelf().click();
        return this;
    }
}
