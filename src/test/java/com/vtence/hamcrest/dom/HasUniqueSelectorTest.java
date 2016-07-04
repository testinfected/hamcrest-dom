package com.vtence.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;

import static com.vtence.hamcrest.dom.HTML.html;
import static com.vtence.hamcrest.dom.HasTag.hasTag;
import static com.vtence.hamcrest.dom.HasUniqueSelector.hasUniqueSelector;
import static org.hamcrest.Matchers.equalTo;

public class HasUniqueSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasUniqueSelector("#content");
    }

    @Test
    public void
    matchesWhenASingleChildIsSelected() {
        assertMatches("does mot match single subject", hasUniqueSelector("#content"), html("<div id='content'>content</div>"));
        assertDoesNotMatch("matches a different subject", hasUniqueSelector("#content"), html("<div>content</div>"));
        assertDoesNotMatch("matches subject several times", hasUniqueSelector("li"), html("<ol><li>first</li><li>second</li></ol>"));
    }

    @Test
    public void
    matchesWhenSelectedChildMatches() {
        assertMatches("does not match child", hasUniqueSelector("#content", hasTag("div")), html("<div id='content'>content</div>"));
        assertDoesNotMatch("matches a different child", hasUniqueSelector("#content", hasTag("div")), html("<span id='content'>content</span>"));
    }

    @Test
    public void
    hasAReadableDescription() {
        assertDescription("has unique selector \"#content\" has tag \"div\"", hasUniqueSelector("#content", hasTag(equalTo("div"))));
    }

    @Test
    public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("2 selector(s) \"li\"", hasUniqueSelector("li"), html("<ol><li>first</li><li>second</li></ol>"));
    }
}
