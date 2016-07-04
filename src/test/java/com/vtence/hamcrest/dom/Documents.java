package com.vtence.hamcrest.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public final class Documents {

    private Documents() {}

    public static Document from(String dom) {
        try {
            DocumentBuilderFactory parserFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = parserFactory.newDocumentBuilder();
            return parser.parse(new InputSource(new StringReader(dom)));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new AssertionError("Can't parse into a document:\n" + dom, e);
        }
    }

    public static Element toElement(String dom) {
        return from(dom).getDocumentElement();
    }
}