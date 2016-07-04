package com.vtence.hamcrest.dom;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

public class HasSelector extends TypeSafeDiagnosingMatcher<Element> {
    private final String selector;
    private final Matcher<Iterable<Element>> subjectsMatcher;

    public HasSelector(String selector) {
        this(selector, null);
    }

    public HasSelector(String selector, Matcher<Iterable<Element>> subjectsMatchers) {
        this.selector = selector;
        this.subjectsMatcher = subjectsMatchers;
    }

    @Override
    protected boolean matchesSafely(Element element, Description mismatchDescription) {
        List<Node> selected = Selector.from(element).selectAll(selector);
        if (selected.isEmpty()) {
            mismatchDescription.appendText("no selector ");
            mismatchDescription.appendText("\"" + selector + "\"");
            return false;
        }
        if (subjectsMatcher == null) return true;

        boolean valueMatches = subjectsMatcher.matches(selected);
        if (!valueMatches) {
            mismatchDescription.appendText(selector + " ");
            subjectsMatcher.describeMismatch(selected, mismatchDescription);
        }
        return valueMatches;
    }

    public void describeTo(Description description) {
        description.appendText("has selector \"");
        description.appendText(selector);
        description.appendText("\"");
        if (subjectsMatcher != null) {
            description.appendText(" ");
            subjectsMatcher.describeTo(description);
        }
    }

    public static Matcher<Element> hasSelector(String selector) {
        return new HasSelector(selector);
    }

    @SafeVarargs
    public static Matcher<Element> hasSelector(String selector, Matcher<? super Element>... subjectsMatchers) {
        return hasSelector(selector, Matchers.hasItems(subjectsMatchers));
    }

    public static Matcher<Element> hasSelector(String selector, Matcher<Iterable<Element>> subjectsMatcher) {
        return new HasSelector(selector, subjectsMatcher);
    }

}

