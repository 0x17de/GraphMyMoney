package de.icetruck.graphmymoney;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by hans on 13.04.14.
 */
public class Main {
    public HashMap<String, String> nameToIdMap = new HashMap<String, String>();
    public Document doc;
    public XPath xpath;

    public Main() {
    }

    boolean loadDocument(String fileName) {
        DOMParser domParser = new DOMParser();
        try {
            domParser.parse(fileName);
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        doc = domParser.getDocument();

        return true;
    }

    public static enum ErrorType {
        OK,
        LOAD_DOCUMENT,
        WRONG_XPATH
    }

    public boolean loadPayeeIdToNameMap() {
        try {
            Element payeesNode = (Element)xpath.evaluate("//PAYEES", doc, XPathConstants.NODE);
            NodeList payeeNodes = (NodeList)xpath.evaluate("./PAYEE", payeesNode, XPathConstants.NODESET);

            for(int i = 0; i < payeeNodes.getLength(); ++i)
            {
                Element e = (Element)payeeNodes.item(i);
                nameToIdMap.put(e.getAttribute("id"), e.getAttribute("name"));
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    ErrorType run(String fileName) {
        if (!loadDocument(fileName)) return ErrorType.LOAD_DOCUMENT;
        xpath = XPathFactory.newInstance().newXPath();
        if (!loadPayeeIdToNameMap()) return ErrorType.WRONG_XPATH;

        return ErrorType.OK;
    }

    public static void main(String[] args) {
        if (args.length < 1)
        {
            System.out.println("Provide xml as first parameter");
            return;
        }
        System.out.println(new Main().run(args[0]).name());
    }
}
