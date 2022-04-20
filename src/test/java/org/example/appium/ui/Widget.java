package org.example.appium.ui;

import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang.StringUtils;

public class Widget {

    protected static final String CHECKED = "checked";
    protected static final String ENABLED = "enabled";


    private final SelenideElement self;
    Widget parent;
    private String alias;

    public Widget(SelenideElement self) {
        this.self = self;
        As as = this.getClass().getAnnotation(As.class);
        if (as != null) {
            setAlias(as.value());
        }
        if (this.self != null) {
            WidgetInitializer.initFields(this, this.self);
        }
    }

    public SelenideElement getSelf() {
        return self;
    }

    public Widget getParent() {
        return parent;
    }

    public void setAlias(String text) {
        if (StringUtils.isEmpty(text)) {
            throw new IllegalStateException("Empty alias not allowed");
        }
        alias = text;
        if (self != null) {
            if (parent != null && parent.getAliasFull() != null && !parent.getAliasFull().isEmpty()) {
                self.as(parent.getAliasFull() + " -> " + text);
            } else {
                self.as(text);
            }
        }
    }

    public String getAlias() {
        return alias;
    }

    public String getAliasFull() {
        return self == null ? getAlias() : self.getAlias();
    }

}
