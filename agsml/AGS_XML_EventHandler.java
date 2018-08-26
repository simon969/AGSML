/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agsml;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Locator;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * @author thomsonsj
 */
class AGS_XML_EventHandler extends DefaultHandler {

    public AGS_XML_EventHandler(){}


    public void setDocumentLocator (Locator locator) {
	  System.out.println("Hello from setDocumentLocator()!");
	}

    public void startDocument ()
	throws SAXException  {
	  System.out.println("Hello from startDocument()!");
	}

    public void endDocument()
	throws SAXException {
	  System.out.println("Hello from endDocument()!");
	}

    public void startPrefixMapping (String prefix, String uri)
	throws SAXException {
	  System.out.println("Hello from startPrefixMapping()!");
	}

    public void endPrefixMapping (String prefix)
	throws SAXException  {
	  System.out.println("Hello from endPrefixMapping()!");
	}

    public void startElement (String uri, String localName,
			      String qName, Attributes atts)
	throws SAXException  {
	  System.out.println("Hello from startElement()!");
	}

    public void endElement (String uri, String localName,
			    String qName)
	throws SAXException {
	  System.out.println("Hello from endElement()!");
	}

    public void characters (char ch[], int start, int length)
	throws SAXException {
	  System.out.println("Hello from characters()!");
	}

    public void ignorableWhitespace (char ch[], int start, int length)
	throws SAXException {
	  System.out.println("Hello from ignorableWhitespace()!");
	}

    public void processingInstruction (String target, String data)
	throws SAXException {
	  System.out.println("Hello from processingInstruction()!");
	}

    public void skippedEntity (String name)
	throws SAXException {
	  System.out.println("Hello from skippedEntity()!");
	}
}


