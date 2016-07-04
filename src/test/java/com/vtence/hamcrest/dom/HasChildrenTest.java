package com.vtence.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.w3c.dom.Element;

import static com.vtence.hamcrest.dom.Documents.toElement;
import static com.vtence.hamcrest.dom.DomMatchers.hasText;
import static com.vtence.hamcrest.dom.HasChildren.hasChild;
import static com.vtence.hamcrest.dom.HasChildren.hasChildren;
import static com.vtence.hamcrest.dom.HasTag.hasTag;

public class HasChildrenTest extends AbstractMatcherTest {

    @Override
    protected Matcher<Element> createMatcher() {
        return hasChildren(hasTag("li"));
    }

    @Test
    public void
    matchesWhenAllChildrenElementsMatch() {
        assertMatches("does not match children", hasChildren(hasText("should match"), hasText("should match too")), anElement("<ol><li>should match</li><li>should match too</li></ol>"));
        assertDoesNotMatch("matches invalid child", hasChildren(hasTag("span")), anElement("<div><p>should not match</p></div>"));
        assertDoesNotMatch("matches one invalid child", hasChildren(hasTag("span"), hasTag("span")), anElement("<div><p>should fail match</p><span>should match</span></div>"));
        assertDoesNotMatch("matches missing child", hasChildren(hasTag("span")), anElement("<div></div>"));
    }

    @Test
    public void
    onlyConsidersFirstLevelChildren() {
        assertDoesNotMatch("matches grand children", hasChildren(hasTag("span")), anElement("<div><p><span>should not match</span></p></div>"));
    }

    @Test
    public void
    providesConvenientMethodForMatchingASingleChildAmongManyChildren() {
        assertMatches("does not match child among many", hasChild(hasTag("span")), anElement("<div><p>won't match</p><span>should match</span></div>"));
    }

    @Test
    public void
    hasAReadableDescription() {
        assertDescription("has children iterable containing [has content text \"should match\"]", hasChildren(hasText("should match")));
    }

    @Test
    public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("children item 0: text was \"does not match\"", hasChildren(hasText("should not match")), anElement("<div><p>does not match</p></div>"));
    }

    private Element anElement(String html) {
        return toElement(html);
    }
}