package org.example.appium.ui.widget;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.apache.commons.lang.ArrayUtils;
import org.example.appium.tools.DateTools;
import org.example.appium.ui.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.Date;

@As("Выбор даты")
public class DatePicker extends Widget {

    @As("День")
    @FindBy(xpath = ".//android.widget.NumberPicker[1]")
    private NumberPicker dayPicker;

    @As("Месяц")
    @FindBy(xpath = ".//android.widget.NumberPicker[2]")
    private NumberPicker monthPicker;

    @As("Год")
    @FindBy(xpath = ".//android.widget.NumberPicker[3]")
    private NumberPicker yearPicker;

    @Find("ОК")
    private Button okButton;

    @Find("ОТМЕНА")
    private Button cancelButton;

    private static String[] months = {
            "янв.",
            "февр.",
            "мар.",
            "апр.",
            "мая",
            "июн.",
            "июл.",
            "авг.",
            "сент.",
            "окт.",
            "нояб.",
            "дек.",
    };

    public DatePicker() {
        super(Selenide.$(Locate.popupDatePicker()));
    }

    public DatePicker shouldVisible() {
        getSelf().should(Condition.visible);
        return this;
    }

    public DatePicker shouldNotVisible() {
        getSelf().shouldNot(Condition.visible);
        return this;
    }

    public DatePicker clickOK() {
        okButton.click();
        return this;
    }

    public DatePicker peek(Date date) {
        if (date == null) {
            throw new NullPointerException(getAliasFull() + ": Дата для выбора не может быть null");
        }

        //Год
        int year = DateTools.getYear(date);
        int currentYear = Integer.parseInt(yearPicker.getValue());
        if (year > currentYear) {
            yearPicker.downTo(Integer.toString(year), 100);
        } else if (year < currentYear) {
            yearPicker.upTo(Integer.toString(year), 100);
        }

        //Месяц
        int month = DateTools.getMonth(date);
        String currentMonthStr = monthPicker.getValue();
        int currentMonth = ArrayUtils.indexOf(months, currentMonthStr);
        Assert.assertTrue(currentMonth >= 0,
                getAliasFull() + ": неизвестный месяц '" + currentMonthStr + "'");
        if (month > currentMonth) {
            monthPicker.downTo(months[month], 14);
        } else if (month < currentMonth) {
            monthPicker.upTo(months[month], 14);
        }

        //День
        int day = DateTools.getDay(date);
        String dayStr = String.format("%02d", day);
        int currentDay = Integer.parseInt(dayPicker.getValue());
        if (day > currentDay) {
            dayPicker.downTo(dayStr, 34);
        } else if (day < currentDay) {
            dayPicker.upTo(dayStr, 34);
        }

        return this;
    }
}
