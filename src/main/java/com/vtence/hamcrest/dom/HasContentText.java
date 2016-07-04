package com.vtence.hamcrest.dom;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.w3c.dom.Element;

import static org.hamcrest.Matchers.blankString;
import static org.hamcrest.Matchers.equalTo;

public class HasContentText extends FeatureMatcher<Element, String> {

    public HasContentText(Matcher<? super String> contentMatcher) {
        super(contentMatcher, "has content text", "text");
    }

    @Override
    protected String featureValueOf(Element actual) {
        return actual.getTextContent();
    }

    public static Matcher<Element> hasBlankContent() {
        return hasContent(blankString());
    }

    public static Matcher<Element> hasContent(String contentText) {
        return hasContent(equalTo(contentText));
    }

    public static Matcher<Element> hasContent(Matcher<? super String> contentMatcher) {
        return new HasContentText(contentMatcher);
    }

}
