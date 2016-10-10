/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agsml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;


import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException; 
import org.xml.sax.SAXParseException;
//import org.xml.sax.helpers.*;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;


public class XML_DOM {
    
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private OutputStreamWriter errorWriter;
    protected Document m_doc;
    protected Node m_root;
    private PrintStream out;
    private int indent;
public XML_DOM() {
    NewFile();
}    
public XML_DOM(String xmlFileName){
   try {
    
       File xmlFile = new File (xmlFileName);
       
            if (xmlFile.exists()) {
                OpenFile(xmlFile);
            } else {
                NewFile();
            } 
    
    // Default the m_root marker
     m_root = m_doc.getDocumentElement() ;
     
   } catch (Exception e) {
       
   }
   
}
private void NewFile() {
  try {
    out = System.out;
    dbf = DocumentBuilderFactory.newInstance();
    db = dbf.newDocumentBuilder();
    errorWriter = new OutputStreamWriter(System.err);
    db.setErrorHandler(new MyErrorHandler(new PrintWriter(errorWriter, true)));
    m_doc = db.newDocument();
    Node n1 = m_doc.createElement("root"); 
    m_doc.appendChild(n1);
    
    
  } catch (Exception e) {
   e.printStackTrace();
   }  
}
public Node RootNode() {
    return m_root;
}

public Document doc() {
    return m_doc;
}
private void OpenFile(File xmlFile) {   
try {
    
    out = System.out;
    dbf = DocumentBuilderFactory.newInstance();
    db = dbf.newDocumentBuilder();
    errorWriter = new OutputStreamWriter(System.err);
    db.setErrorHandler(new MyErrorHandler(new PrintWriter(errorWriter, true)));
    m_doc = db.parse(xmlFile);

} catch (Exception e) {
   e.printStackTrace();
   
   
   }
 }


public void SaveFile(String xmlOutputFilePath ) {
  try
  {
    TransformerFactory factory = TransformerFactory.newInstance();
    Transformer transformer = factory.newTransformer();
    Result result = new StreamResult(new File(xmlOutputFilePath));
    Source source = new DOMSource(m_doc);
    transformer.transform(source, result);
  }
  catch (Exception e) {
   e.printStackTrace();
   }
}
public Node setRoot(String sUniqueNodeString){
          Node n1 = m_doc.getDocumentElement() ;
          m_root = this.findSubNode(n1, sUniqueNodeString);
      return m_root;
    }
public Node setRoot(String sUniqueNodeString, String AttributeName, String AttributeValue){
          Node n1 = m_doc.getDocumentElement() ;
          m_root = this.findSubNode(n1, sUniqueNodeString, AttributeName, AttributeValue);
      return m_root;
    }
public Node findSubNode(String name) {
    return findSubNode(m_root, name);
}
public Node findSubNode(Node n1, String name) {
 return findSubNode(n1,name,"","");
}
public Node findSubNode(Node node, String name, String attribute, String attribute_value) {
try {
     NodeList nodeList = node.getChildNodes();
     for (int i = 0; i < nodeList.getLength(); i++) {
         Node currentNode = nodeList.item(i);
          if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
            String nodeName = currentNode.getNodeName();
                if (nodeName == name) {
                    if (attribute.isEmpty()) {
                        return currentNode;
                    }  else {
                        Element e1 =  (Element) currentNode;
                        String s1 = e1.getAttribute(attribute);
                            if (s1.equalsIgnoreCase(attribute_value)) {
                                return currentNode;
                            } 
                            
                    }
                }
          }
     }
    return null;
 
} catch (Exception e) {
    return null;
}
}
public void echo_root() {
    echo(m_root);
}

private void outputIndentation(){
for (int i = 0; i < indent; i++) {
          //  out.print();
 }
}
private void echo(Node n) {
        outputIndentation();

        int type = n.getNodeType();
        switch (type) {
        case Node.ATTRIBUTE_NODE:
            out.print("ATTR:");
            printlnCommon(n);
            break;
        case Node.CDATA_SECTION_NODE:
            out.print("CDATA:");
            printlnCommon(n);
            break;
        case Node.COMMENT_NODE:
            out.print("COMM:");
            printlnCommon(n);
            break;
        case Node.DOCUMENT_FRAGMENT_NODE:
            out.print("DOC_FRAG:");
            printlnCommon(n);
            break;
        case Node.DOCUMENT_NODE:
            out.print("DOC:");
            printlnCommon(n);
            break;
        case Node.DOCUMENT_TYPE_NODE:
            out.print("DOC_TYPE:");
            printlnCommon(n);

            NamedNodeMap nodeMap = ((DocumentType)n).getEntities();
            indent += 2;
            for (int i = 0; i < nodeMap.getLength(); i++) {
                Entity entity = (Entity)nodeMap.item(i);
                echo(entity);
            }
            indent -= 2;
            break;
        case Node.ELEMENT_NODE:
            out.print("ELEM:");
            printlnCommon(n);

            NamedNodeMap atts = n.getAttributes();
            indent += 2;
            for (int i = 0; i < atts.getLength(); i++) {
                Node att = atts.item(i);
                echo(att);
            }
            indent -= 2;
            break;
        case Node.ENTITY_NODE:
            out.print("ENT:");
            printlnCommon(n);
            break;
        case Node.ENTITY_REFERENCE_NODE:
            out.print("ENT_REF:");
            printlnCommon(n);
            break;
        case Node.NOTATION_NODE:
            out.print("NOTATION:");
            printlnCommon(n);
            break;
        case Node.PROCESSING_INSTRUCTION_NODE:
            out.print("PROC_INST:");
            printlnCommon(n);
            break;
        case Node.TEXT_NODE:
            out.print("TEXT:");
            printlnCommon(n);
            break;
        default:
            out.print("UNSUPPORTED NODE: " + type);
            printlnCommon(n);
            break;
        }

        indent++;
        for (Node child = n.getFirstChild(); child != null;
             child = child.getNextSibling()) {
            echo(child);
        }
        indent--;
    }
private void printlnCommon(Node n) {
        out.print(" nodeName=\"" + n.getNodeName() + "\"");

        String val = n.getNamespaceURI();
        if (val != null) {
            out.print(" uri=\"" + val + "\"");
        }

        val = n.getPrefix();
        if (val != null) {
            out.print(" pre=\"" + val + "\"");
        }

        val = n.getLocalName();
        if (val != null) {
            out.print(" local=\"" + val + "\"");
        }

        val = n.getNodeValue();
        if (val != null) {
            out.print(" nodeValue=");
            if (val.trim().equals("")) {
                // Whitespace
                out.print("[WS]");
            } else {
                out.print("\"" + n.getNodeValue() + "\"");
            }
        }
        out.println();
    }
private static void usage() {

    }


public static void main(String[] args) throws Exception {
    /*    String filename = null;
        boolean dtdValidate = false;
        boolean xsdValidate = false;
        String schemaSource = null;

        for (int i = 0; i < args.length; i++) {
        if (args[i].equals("-dtd"))  {
            {
                dtdValidate = true;
            } else if (args[i].equals("-xsd")) {
                xsdValidate = true;
            } else if (args[i].equals("-xsdss")) {
                if (i == args.length - 1) {
                    usage();
                }
                xsdValidate = true;
                schemaSource = args[++i];

            } else {
                filename = args[i];

                if (i != args.length - 1) {
                    usage();
                }
            }
    }
        if (filename == null) {
            usage();
        }

        DocumentBuilderFactory dbf =
        DocumentBuilderFactory.newInstance();

        dbf.setNamespaceAware(true);
        dbf.setValidating(dtdValidate || xsdValidate);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(filename));


    }
    */
    }

private static class MyErrorHandler implements ErrorHandler {
     
        private PrintWriter out;

        MyErrorHandler(PrintWriter out) {
            this.out = out;
        }

        private String getParseExceptionInfo(SAXParseException spe) {
            String systemId = spe.getSystemId();
            if (systemId == null) {
                systemId = "null";
            }
            String info = "URI=" + systemId +
                " Line=" + spe.getLineNumber() +
                ": " + spe.getMessage();
            return info;
          }

        public void warning(SAXParseException spe) throws SAXException {
            out.println("Warning: " + getParseExceptionInfo(spe));
        }
        
        public void error(SAXParseException spe) throws SAXException {
            String message = "Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }

        public void fatalError(SAXParseException spe) throws SAXException {
            String message = "Fatal Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }
    }
}

