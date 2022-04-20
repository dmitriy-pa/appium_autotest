package org.example.appium.ui.widget;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.example.appium.ui.As;
import org.example.appium.ui.Find;
import org.example.appium.ui.Locate;
import org.example.appium.ui.Widget;
import org.openqa.selenium.support.FindBy;

@As("Всплывающее сообщение об ошибке")
public class ErrorMessage extends Widget {

    @As("Текст сообщения")
    @FindBy(id = "android:id/message")
    private Text text;

    @Find("ОК")
    private Button okButton;

    public ErrorMessage() {
        super(Selenide.$(Locate.popupMessage("Ошибка")));
    }

    public ErrorMessage shouldVisible() {
        getSelf().should(Condition.visible);
        return this;
    }

    public ErrorMessage checkMessage(String msg) {
        text.checkText(msg);
        return this;
    }

    public ErrorMessage clickOk() {
        okButton.click();
        return this;
    }

    public ErrorMessage shouldNotVisible() {
        getSelf().shouldNot(Condition.visible);
        return this;
    }
}
