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
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hans on 13.04.14.
 */
public class Main {
    public HashMap<String, String> nameToIdMap = new HashMap<String, String>();
    public List<Transaction> transactionList = new LinkedList<Transaction>();
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
        WRONG_XPATH,
        UNKNOWN
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
            System.out.println(String.format("Loaded %d of %d payees", nameToIdMap.size(), payeeNodes.getLength()));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean loadTransactions() {
        try {
            Element transactionsNode = (Element)xpath.evaluate("//TRANSACTIONS", doc, XPathConstants.NODE);
            NodeList transactionNodes = (NodeList)xpath.evaluate("./TRANSACTION", transactionsNode, XPathConstants.NODESET);

            for(int i = 0; i < transactionNodes.getLength(); ++i)
            {
                Element transactionElement = (Element)transactionNodes.item(i);

                List<Split> splitList = new LinkedList<Split>();
                Element splitsNode = (Element)xpath.evaluate("./SPLITS", transactionElement, XPathConstants.NODE);
                NodeList splitNodes = (NodeList)xpath.evaluate("./SPLIT", splitsNode, XPathConstants.NODESET);

                for(int j = 0; j < splitNodes.getLength(); ++j) {
                    Element splitElement = (Element)splitNodes.item(j);
                    splitList.add(new Split(splitElement.getAttribute("id"), splitElement.getAttribute("payee"), splitElement.getAttribute("bankid"), splitElement.getAttribute("account"), splitElement.getAttribute("shares")));
                }

                transactionList.add(new Transaction(transactionElement.getAttribute("id"), transactionElement.getAttribute("postdate"), splitList));
            }
            System.out.println(String.format("Loaded %d of %d transactions", transactionList.size(), transactionNodes.getLength()));
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
        if (!loadTransactions()) return ErrorType.WRONG_XPATH;

        TransactionGraph.write(this, "output/transactions.csv");

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
