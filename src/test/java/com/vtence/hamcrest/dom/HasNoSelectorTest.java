package com.vtence.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;

import static com.vtence.hamcrest.dom.HTML.html;
import static com.vtence.hamcrest.dom.HasNoSelector.hasNoSelector;

public class HasNoSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasNoSelector("#unknown");
    }

    @Test
    public void
    matchesWhenSelectorHasNoSubject() {
        assertMatches("does not match unselected", hasNoSelector("#unknown"), html("<div id='content'>content</div>"));
        assertDoesNotMatch("matches selected", hasNoSelector("#content"), html("<div id=\"content\">content</div>"));
    }

    @Test
    public void
    hasAReadableDescription() {
        assertDescription("has no selector \"#unknown\"", hasNoSelector("#unknown"));
    }

    @Test
    public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("found element \"div\"", hasNoSelector("#content"), html("<div id='content'></div>"));
    }
}