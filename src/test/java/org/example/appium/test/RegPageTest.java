package org.example.appium.test;

import org.example.appium.ui.page.RegForm;
import org.example.appium.ui.widget.ErrorMessage;
import org.example.appium.ui.widget.Toolbar;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Random;

import static org.example.appium.tools.DateTools.*;

public class RegPageTest extends BaseTest {

    @Test
    public void elementsState() {

        new Toolbar().shouldExists();

        new RegForm()
                .checkTitle()
                .regPhoneNumField(p -> p
                        .checkHintText("+7 (9XX) XXX-XX-XX"))
                .birthdayField(b -> b.
                        checkHintText("Дата рождения"))
                .passwordField(p -> p
                        .checkHintText("Придумай пароль"))
                .checkButtons();
    }

    @Test
    public void failedRegistration() {

        Date bDate = dropTime(
                plusDays(
                        plusYears(now(), -14),
                        -new Random().nextInt(360)
                ));

        new RegForm()
                .regPhoneNumField(p -> p
                        .startEdit()
                        .checkHintText("Номер телефона")
                        .checkText("+7 (9XX) XXX-XX-XX")
                        .setText("069941111")
                        .checkText("+7 (906) 994-11-11"))

                .birthdayField(b -> b
                        .setDate(bDate)
                        .checkDate(bDate)
                        .checkHintText("К игре допускаются только клиенты старше 18 лет"))

                .passwordField(p -> p
                        .startEdit()
                        .checkHintText("Только латиница и цифры, 6–20 символов")
                        .setText("abcd123456")
                        .checkText("abcd123456"))

                .clickPromoCodeButton()

                .promoCodeField(p -> p
                        .startEdit()
                        .checkHintText("Промокод")
                        .setText("promocode123")
                        .checkText("promocode123"))

                .agreeSwitch(a -> a
                        .click()
                        .shouldChecked())

                .registerButton(b -> b
                        .shouldEnabled()
                        .click());

        new ErrorMessage()
                .shouldVisible()
                .checkMessage("Заполни все поля корректно и попробуй еще раз.")
                .clickOk()
                .shouldNotVisible();
    }

}
