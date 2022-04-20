package org.example.appium.ui.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.example.appium.ui.As;
import org.example.appium.ui.widget.Button;
import org.example.appium.ui.widget.Switch;
import org.example.appium.ui.widget.Text;
import org.example.appium.ui.Widget;
import org.example.appium.ui.field.BirthdayField;
import org.example.appium.ui.field.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.function.Consumer;

@As("Страница регистрации")
public class RegForm extends Widget {

    @As("Заголовок")
    @FindBy(xpath = "./*/android.widget.TextView[1]")
    protected Text title;

    @As("Номер телефона")
    @FindBy(id = "biz.growapp.winline:id/vgPhone")
    protected TextField regPhoneNumField;

    @As("Дата рождения")
    @FindBy(id = "biz.growapp.winline:id/vgBirthday")
    protected BirthdayField birthBirthdayField;

    @As("Пароль")
    @FindBy(id = "biz.growapp.winline:id/vgPassword")
    protected TextField passwordField;

    @As("У меня есть промокод")
    @FindBy(id = "biz.growapp.winline:id/tvHavePromoCode")
    protected Button promoCodeButton;

    @As("промокод")
    @FindBy(id = "biz.growapp.winline:id/vgPromoCode")
    protected TextField promoCodeField;

    @As("Согласен с правилами")
    @FindBy(id = "biz.growapp.winline:id/switchAgreement")
    protected Switch agreeSwitch;

    @As("Зарегистрироваться")
    @FindBy(id = "biz.growapp.winline:id/btnGetSmsCode")
    protected Button registerButton;

    @As("Уже зарегистрирован ?")
    @FindBy(id = "biz.growapp.winline:id/tvAlreadyRegistered")
    protected Text alreadyRegisteredText;

    @As("Войти")
    @FindBy(id = "biz.growapp.winline:id/btnLogIn")
    protected Button loginButton;

    public RegForm() {
        super(Selenide.$(By.id("biz.growapp.winline:id/vgRegContent")));
    }

    public RegForm checkTitle() {
        title.checkText("Регистрация");
        return this;
    }

    public RegForm checkButtons() {
        promoCodeButton.checkText();
        registerButton.checkText();
        registerButton.shouldNotEnabled();
        agreeSwitch.shouldNotChecked();
        alreadyRegisteredText.checkText();
        loginButton.checkText();
        return this;
    }

    public RegForm regPhoneNumField(Consumer<TextField> c) {
        c.accept(regPhoneNumField);
        return this;
    }

    public RegForm birthdayField(Consumer<BirthdayField> c) {
        c.accept(birthBirthdayField);
        return this;
    }

    public RegForm passwordField(Consumer<TextField> c) {
        c.accept(passwordField);
        return this;
    }

    public RegForm clickPromoCodeButton() {
        promoCodeButton.click();
        promoCodeButton.getSelf().shouldNot(Condition.exist);
        promoCodeField.getSelf().should(Condition.visible);
        return this;
    }
    public RegForm promoCodeField(Consumer<TextField> c) {
        c.accept(promoCodeField);
        return this;
    }

    public Switch getAgreeSwitch() {
        return agreeSwitch;
    }

    public RegForm agreeSwitch(Consumer<Switch> c) {
        c.accept(agreeSwitch);
        return this;
    }

    public RegForm registerButton(Consumer<Button> c) {
        c.accept(registerButton);
        return this;
    }

}
