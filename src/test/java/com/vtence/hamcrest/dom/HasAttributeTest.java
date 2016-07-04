package com.vtence.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.w3c.dom.Element;

import static com.vtence.hamcrest.dom.Documents.toElement;
import static com.vtence.hamcrest.dom.HasAttribute.hasAttribute;
import static com.vtence.hamcrest.dom.HasAttribute.isChecked;
import static com.vtence.hamcrest.dom.HasAttribute.isDisabled;
import static com.vtence.hamcrest.dom.HasAttribute.isReadOnly;
import static com.vtence.hamcrest.dom.HasAttribute.isSelected;

public class HasAttributeTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasAttribute("selected");
    }

    @Test
    public void
    matchesDependingOnAttributePresence() {
        assertMatches("attribute not found", hasAttribute("selected"), elementWithAttribute("selected"));
        assertDoesNotMatch("attribute found", hasAttribute("selected"), elementWithAttribute("readonly"));
    }

    @Test
    public void
    providesShortcutForMatchingSelectedElements() {
        assertMatches("not selected", isSelected(), elementWithAttribute("selected"));
        assertDoesNotMatch("selected", isSelected(), elementWithAttribute("readonly"));
    }

    @Test
    public void
    providesShortcutForMatchingReadonlyElements() {
        assertMatches("not readonly", isReadOnly(), elementWithAttribute("readonly"));
        assertDoesNotMatch("readonly", isReadOnly(), elementWithAttribute("checked"));
    }

    @Test
    public void
    providesShortcutForMatchingCheckedElements() {
        assertMatches("not checked", isChecked(), elementWithAttribute("checked"));
        assertDoesNotMatch("checked", isChecked(), elementWithAttribute("disabled"));
    }

    @Test
    public void
    providesShortcutForMatchingDisabledElements() {
        assertMatches("not disabled", isDisabled(), elementWithAttribute("disabled"));
        assertDoesNotMatch("checked", isDisabled(), elementWithAttribute("selected"));
    }

    @Test
    public void
    hasAReadableDescription() {
        assertDescription("has attribute \"name\"", hasAttribute("name"));
    }

    @Test
    public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("no attribute \"name\"", hasAttribute("name"), elementWithAttribute("other"));
    }

    private Element elementWithAttribute(String name) {
        return toElement(String.format("<input %s=\"\"/>", name));
    }
}