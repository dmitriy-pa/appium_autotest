package org.example.appium.ui;

import org.example.appium.ui.widget.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;
import org.openqa.selenium.support.PageFactoryFinder;

import java.lang.annotation.*;
import java.lang.reflect.Field;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@PageFactoryFinder(Find.FindBuilder.class)
public @interface Find {
    String value();

    class FindBuilder extends AbstractFindByBuilder {
        @Override
        public By buildIt(Object annotation, Field field) {
            Find find = (Find) annotation;
            Class cls = field.getType();

            if (Button.class.isAssignableFrom(cls)) {
                return Locate.nativeButton(find.value());
            }

            throw new RuntimeException("Не получилось определить локатор для поля " +
                    field.getDeclaringClass().getSimpleName() + "." + field.getName() + " с типом " + cls.getSimpleName());
        }
    }
}
