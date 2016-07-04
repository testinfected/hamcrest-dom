package com.vtence.hamcrest.dom;

import org.hamcrest.Matcher;
import org.junit.Test;

import static com.vtence.hamcrest.dom.HTML.html;
import static com.vtence.hamcrest.dom.HasAttributeValue.hasClassName;
import static com.vtence.hamcrest.dom.HasSelector.hasSelector;
import static com.vtence.hamcrest.dom.HasTag.hasTag;
import static org.hamcrest.Matchers.equalTo;

public class HasSelectorTest extends AbstractMatcherTest {

    @Override
    protected Matcher<?> createMatcher() {
        return hasSelector("#content");
    }

    @Test
    public void
    matchesWhenAtLeastOneChildIsSelected() {
        assertMatches("does not match single subject", hasSelector("#content"), html("<div id='content'>content</div>"));
        assertMatches("does not match multiple subjects", hasSelector("li"), html("<ol><li>first</li><li>second</li></ol>"));
        assertDoesNotMatch("matches a different subject", hasSelector("#content"), html("<div>content</div>"));
    }

    @Test
    public void
    matchesSelectedChildrenInAnyOrder() {
        assertMatches("does not match child", hasSelector("#content", hasTag("div")), html("<div id='content'>content</div>"));
        assertMatches("does not match some children", hasSelector("ol > li", hasClassName("odd")), html("<ol><li class='odd'>first</li><li class='even'>second</li></ol>"));
        assertMatches("does not match all children", hasSelector("ol > li", hasClassName("even"), hasClassName("odd")), html("<ol><li class='odd'>first</li><li class='even'>second</li></ol>"));
        assertDoesNotMatch("matches different element", hasSelector("#content", hasTag("div")), html("<span id='content'>content</span>"));
    }

    @Test
    public void
    hasAReadableDescription() {
        assertDescription("has selector \"#content\"", hasSelector("#content"));
        assertDescription("has selector \"#content\" (a collection containing has tag \"div\")", hasSelector("#content", hasTag(equalTo("div"))));
    }

    @Test
    public void
    hasAReadableMismatchDescription() {
        assertMismatchDescription("no selector \"ul li\"", hasSelector("ul li"), html("<ol><li>first</li><li>second</li></ol>"));
        assertMismatchDescription("#content a collection containing has tag \"div\" mismatches were: [tag was \"span\"]", hasSelector("#content", hasTag(equalTo("div"))), html("<span id='content'>content</span>"));
    }
}