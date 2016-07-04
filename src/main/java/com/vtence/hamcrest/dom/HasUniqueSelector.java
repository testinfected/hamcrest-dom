package com.vtence.hamcrest.dom;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

import static com.vtence.hamcrest.dom.DomMatchers.anElement;
import static java.lang.String.valueOf;
import static org.hamcrest.Matchers.anything;

public class HasUniqueSelector extends TypeSafeDiagnosingMatcher<Element> {
    private final String selector;
    private final Matcher<? super Element> subjectMatcher;

    public HasUniqueSelector(String selector, Matcher<? super Element> subjectMatcher) {
        this.selector = selector;
        this.subjectMatcher = subjectMatcher;
    }

    @Override
    protected boolean matchesSafely(Element doc, Description mismatchDescription) {
        List<Node> selected = Selector.from(doc).selectAll(selector);
        if (selected.size() != 1) {
            mismatchDescription.appendText(valueOf(selected.size()));
            mismatchDescription.appendText(" selector(s) ");
            mismatchDescription.appendText("\"" + selector + "\"");
            return false;
        }
        Node element = selected.iterator().next();
        boolean valueMatches = subjectMatcher.matches(element);
        if (!valueMatches) {
            mismatchDescription.appendText(selector + " ");
            subjectMatcher.describeMismatch(element, mismatchDescription);
        }
        return valueMatches;
    }

    public void describeTo(Description description) {
        description.appendText("has unique selector \"");
        description.appendText(selector);
        description.appendText("\" ");
        subjectMatcher.describeTo(description);
    }

    public static Matcher<Element> hasUniqueSelector(String selector) {
        return new HasUniqueSelector(selector, anything());
    }

    @SafeVarargs
    public static Matcher<Element> hasUniqueSelector(String selector, Matcher<? super Element>... subjectMatchers) {
        return hasUniqueSelector(selector, anElement(subjectMatchers));
    }

    public static Matcher<Element> hasUniqueSelector(String selector, Matcher<? super Element> subjectMatcher) {
        return new HasUniqueSelector(selector, subjectMatcher);
    }
}