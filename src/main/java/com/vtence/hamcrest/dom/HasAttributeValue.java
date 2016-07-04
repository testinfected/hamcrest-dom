package com.vtence.hamcrest.dom;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.AnyOf.anyOf;

public class HasAttributeValue extends FeatureMatcher<Element, String> {
    private final String attributeName;

    public HasAttributeValue(String attributeName, Matcher<? super String> valueMatcher) {
        super(valueMatcher, "has attribute \"" + attributeName + "\" with value", "\"" + attributeName + "\" value");
        this.attributeName = attributeName;
    }

    @Override
    protected String featureValueOf(Element actual) {
        return actual.getAttribute(attributeName);
    }

    public static Matcher<Element> hasAttribute(String name, String value) {
        return hasAttribute(name, equalTo(value));
    }

    public static Matcher<Element> hasId(String id) {
        return hasAttribute("id", equalTo(id));
    }

    public static Matcher<Element> hasName(String name) {
        return hasAttribute("name", equalTo(name));
    }

    public static Matcher<Element> hasClassName(String className) {
        return hasAttribute("class", anyOf(
                equalTo(className),
                startsWith(className + " "),
                endsWith(" " + className),
                containsString(" " + className + " ")
        ));
    }

    public static Matcher<Element> hasAttribute(String name, Matcher<? super String> valueMatcher) {
        return new HasAttributeValue(name, valueMatcher);
    }
}
