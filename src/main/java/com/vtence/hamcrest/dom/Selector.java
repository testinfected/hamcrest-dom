package com.vtence.hamcrest.dom;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import se.fishtank.css.selectors.Selectors;
import se.fishtank.css.selectors.dom.W3CNode;

import java.util.List;

public class Selector {
    private Node root;

    private Selector(Node root) {
        this.root = root;
    }

    public static Selector from(Element root) {
        return new Selector(root);
    }

    public List<Node> selectAll(String selector) {
        return new Selectors<>(new W3CNode(root)).querySelectorAll(selector);
    }
}
