package org.example.appium.ui;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactoryFinder;
import org.openqa.selenium.support.pagefactory.Annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public abstract class WidgetInitializer {

    private static class ClassElement {
        Class<?> cls;
        ClassElement child;

        public ClassElement(Class<?> cls, ClassElement child) {
            this.cls = cls;
            this.child = child;
        }
    }

    private WidgetInitializer() {
    }

    public static void initFields(Widget obj, SelenideElement context) {
        Class<?> cls = obj.getClass();
        ClassElement stack = null;

        while (cls != Object.class && cls != Widget.class) {
            stack = new ClassElement(cls, stack);
            cls = cls.getSuperclass();
        }

        while (stack != null) {
            initFields(stack, obj, context);
            stack = stack.child;
        }
    }

    private static void initFields(ClassElement cls, Widget obj, SelenideElement context) {
        Field[] fields = cls.cls.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (isAnnotationPresent(field)) {
                    field.setAccessible(true);
                    if (field.get(obj) == null) {
                        Object value = getValue(field, cls, context, obj);
                        if (value != null) {
                            field.set(obj, value);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getValue(Field field, ClassElement cls, SelenideElement context, Widget parent) {
        By selector = new Annotations(field).buildBy();
        Type fieldType = field.getGenericType();
        String alias = getAlias(field);

        //если поле с дженерик типом, то пытаемся определить фактический тип из параметров класса
        if (fieldType instanceof TypeVariable) {
            fieldType = resolveTypeVariableAsClass(cls, (TypeVariable) fieldType);
        }

        if (fieldType instanceof Class) {
            Class fieldTypeClass = (Class) fieldType;
            if (WebElement.class.isAssignableFrom(fieldTypeClass)) {
                SelenideElement ret = context.$(selector);
                if (!StringUtils.isEmpty(alias)) {
                    if (parent != null && parent.getAliasFull() != null && !parent.getAliasFull().isEmpty()) {
                        ret.as(parent.getAliasFull() + " -> " + alias);
                    } else {
                        ret.as(alias);
                    }
                }
                return ret;
            } else if (ElementsCollection.class.isAssignableFrom(fieldTypeClass)) {
                ElementsCollection ret = context.$$(selector);
                if (!StringUtils.isEmpty(alias)) {
                    if (parent != null && parent.getAliasFull() != null && !parent.getAliasFull().isEmpty()) {
                        ret.as(parent.getAliasFull() + " -> " + alias);
                    } else {
                        ret.as(alias);
                    }
                }
                return ret;
            } else if (Widget.class.isAssignableFrom(fieldTypeClass)) {
                Widget ret = createWidget(fieldTypeClass, context.$(selector));
                setParent(ret, parent);
                if (!StringUtils.isEmpty(alias)) {
                    ret.setAlias(alias);
                }
                return ret;
            }
        }

        throw new RuntimeException("Не получилось определить значение для поля '"
                + field.getDeclaringClass().getSimpleName() + "." + field.getName() + "': " +
                "не поддерживается тип поля " + fieldType.getTypeName());
    }

    public static void setParent(Widget child, Widget parent) {
        child.parent = parent;
    }

    private static Class<?> resolveTypeVariableAsClass(ClassElement cls, TypeVariable typeVariable) {
        Class type = recursiveTypeSearch(cls, typeVariable);

        //Проверяем что тип является классом без параметров
        if (type != null && type.getTypeParameters().length == 0) {
            return type;
        }

        //Кидаем сообщение об ошибке
        ClassElement current = cls;
        while (current.child != null) {
            current = current.child;
        }
        throw new RuntimeException(
                "При инициализации " + current.cls.getSimpleName() +
                        " не получилось определить значение параметризированного типа " + typeVariable.getName() +
                        " для класса " + cls.cls.getSimpleName());
    }

    private static Class recursiveTypeSearch(ClassElement current, TypeVariable type) {
        ClassElement child = current.child;
        if (child == null) {
            return null;
        }
        TypeVariable<? extends Class<?>>[] typeParameters = current.cls.getTypeParameters();
        for (int i = 0; i < typeParameters.length; i++) {
            Type genericSuperclass = child.cls.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                ParameterizedType parametrizedType = (ParameterizedType) genericSuperclass;
                Type typeArgument = parametrizedType.getActualTypeArguments()[i];
                if (typeArgument instanceof Class) {
                    return (Class) typeArgument;
                } else if (typeArgument instanceof TypeVariable) {
                    return recursiveTypeSearch(child, (TypeVariable) typeArgument);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    private static Widget createWidget(Class<?> type, SelenideElement context) {
        try {
            Constructor<?> constructor = type.getDeclaredConstructor(SelenideElement.class);
            constructor.setAccessible(true);
            return (Widget) constructor.newInstance(context);
        } catch (NoSuchMethodException
                 | InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getAlias(Field field) {
        As as = field.getAnnotation(As.class);
        if (as != null) {
            return as.value();
        }
        Find find = field.getAnnotation(Find.class);
        if (find != null) {
            return find.value();
        }
        return null;
    }

    private static boolean isAnnotationPresent(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(PageFactoryFinder.class)) {
                return true;
            }
        }
        return false;
    }
}
