package org.example.appium.ui.widget;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.example.appium.ui.Widget;

public class Toolbar extends Widget {
    public Toolbar() {
        super(Selenide.$x("//*[@resource-id='biz.growapp.winline:id/toolbar'][last()]"));
    }

    public Toolbar shouldExists() {
        getSelf().should(Condition.exist);
        return this;
    }

}
