package com.vtence.hamcrest.dom;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class HasChildren extends FeatureMatcher<Element, Iterable<Element>> {

    public HasChildren(Matcher<Iterable<Element>> childrenMatcher) {
        super(childrenMatcher, "has children", "children");
    }

    protected Iterable<Element> featureValueOf(Element actual) {
        return Elements.listChildrenOf(actual);
    }

    @SuppressWarnings("unchecked")
    public static Matcher<Element> hasChild(Matcher<? super Element> childMatcher) {
        return hasChildren(Matchers.hasItems(childMatcher));
    }

    @SafeVarargs
    public static Matcher<Element> hasChildren(Matcher<? super Element>... childrenMatchers) {
        return hasChildren(DomMatchers.contains(childrenMatchers));
    }

    public static Matcher<Element> hasChildren(Matcher<Iterable<Element>> childrenMatcher) {
        return new HasChildren(childrenMatcher);
    }

    private static class Elements {
        public static List<Element> listChildrenOf(Element element) {
            List<Element> children = new ArrayList<>();
            NodeList descendantNodes = element.getChildNodes();
            for (int i = 0; i < descendantNodes.getLength(); i++) {
                Node childNode = descendantNodes.item(i);
                if (childNode.getNodeType() == Node.ELEMENT_NODE) children.add((Element) childNode);
            }
            return children;
        }
    }
}
