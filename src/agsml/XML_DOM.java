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

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.DataOutputStream;

//import java.io.OutputStreamWriter;
//import java.io.PrintStream;
//import java.io.PrintWriter;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Base64;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;


class XML_DOM extends Base_Class {
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
//    private OutputStreamWriter errorWriter;
    protected Document m_doc;
    protected Node m_root;
    protected String source;
//    private PrintStream out;
    private int indent;
    protected enum fileState {
        CREATEIFNOTEXIST, MUSTEXIST,  FROMSTRING      
    }
public XML_DOM() {
}
public String source() {
    return source;
}

public XML_DOM (XML_DOM xml_from, String node_name) {
    
    try {
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
      
        // Same source as xml_from 
        source = xml_from.source;
        m_doc = db.newDocument();
        Element root = m_doc.createElement(Constants.AGSML_ROOT);
        m_doc.appendChild(root);
        
        Element root2 = m_doc.getDocumentElement();
        Element root1 = xml_from.m_doc.getDocumentElement();
        NodeList nodeList = root1.getChildNodes();
          
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n1 =  nodeList.item(i);
            if (node_name.isEmpty() || node_name==n1.getNodeName()) {
                Node in1 = m_doc.importNode(n1, true);
                root2.appendChild(in1);
            }
        }
    } catch (Exception e) {
       log.severe(e.getMessage());
    }
}
public XML_DOM(String xmlFileName) {
      this(xmlFileName, fileState.CREATEIFNOTEXIST);
}

public XML_DOM(String s1,fileState var ){
    super(agsml.Main.log);
    try {
       
        source = s1; 
        AGS_Data f = new AGS_Data(s1);
        if (f.IsHTTPPath()){
            OpenURL(s1) ;
        } else {
            if (f.IsXMLFile()) {
                if (f.File().exists()) {
                    OpenFile(f.File());
                } 
                if (!f.File().exists() && var == fileState.CREATEIFNOTEXIST) {
                    NewFile();
                } 
                if (!f.File().exists() && var == fileState.MUSTEXIST) {
                throw new FileNotFoundException("Could not find file: " + s1);
                }
            }  else {
                   NewFromString(s1);
                } 
            }
           
    // Default the m_root marker
     m_root = m_doc.getDocumentElement() ;
     
   } catch (Exception e) {
       log.log(Level.SEVERE, e.getMessage());
   }
   
}

private void NewFromString(String s1) {
    try {
    //    out = System.out;
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
    //    errorWriter = new OutputStreamWriter(System.err);
    //    db.setErrorHandler(new MyErrorHandler(new PrintWriter(errorWriter, true)));
        m_doc = db.parse(s1);
    } catch (Exception e){
         log.log(Level.SEVERE, e.getMessage()); 
    }
    
}
private void OpenURL(String xmlHTTPPath) {
    try {
        throw new Exception("URL detected (" + xmlHTTPPath + ") functionality not implemented");
    } catch (Exception e) {
        log.severe(e.getMessage());
    }
    
}
private void OpenURL1(String xmlHTTPPath) {
    try {
      //  out = System.out;
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
     //   errorWriter = new OutputStreamWriter(System.err);
    //    db.setErrorHandler(new MyErrorHandler(new PrintWriter(errorWriter, true)));
        
        URL url = new URL(xmlHTTPPath);
        
        URLConnection uc = url.openConnection();
        
        String username = agsml.Main.config.getProperty(Constants.ATHENTICATE_USER);
        String password = agsml.Main.config.getProperty(Constants.ATHENTICATE_PASSWORD);
        String loginpage = agsml.Main.config.getProperty(Constants.ATHENTICATE_PAGE);
        
        String userpass = username + ":" + password;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
        
        uc.setRequestProperty ("Authorization", basicAuth);
        
        InputStream in = uc.getInputStream();
        m_doc = db.parse(in);
                
  //      m_doc = db.parse(new URL(xmlHTTPPath).openStream());
    } catch (Exception e){
         log.log(Level.SEVERE, e.getMessage()); 
    }
}
private void OpenURL2(String xmlHTTPPath) {
    try {
      //  out = System.out;
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
     //   errorWriter = new OutputStreamWriter(System.err);
    //    db.setErrorHandler(new MyErrorHandler(new PrintWriter(errorWriter, true)));
        xmlHTTPPath="http://emi-gis-ps.scottwilson.co.uk/ge_repository/Identity/Account/Login?ReturnUrl=%2Fge_repository%2Fge_file%2FView%2Fa0ceac08-7917-4fde-3489-08d68dedce72";
        URL url = new URL(xmlHTTPPath);
        
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        
        String username = agsml.Main.config.getProperty(Constants.ATHENTICATE_USER);
        String password = agsml.Main.config.getProperty(Constants.ATHENTICATE_PASSWORD);
        String loginpage = agsml.Main.config.getProperty(Constants.ATHENTICATE_PAGE);
        
        uc.setRequestMethod("POST");
        uc.setDoOutput(true);
        uc.setDoInput(true);
        DataOutputStream out = new DataOutputStream(uc.getOutputStream());
        
        String postValues = URLEncoder.encode("Input_Email", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
        postValues += "&" + URLEncoder.encode("Input_Password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
        out.writeBytes(postValues);
        out.flush();
        out.close();
        InputStream in = uc.getInputStream();
        m_doc = db.parse(in);
                
  //      m_doc = db.parse(new URL(xmlHTTPPath).openStream());
    } catch (Exception e){
         log.log(Level.SEVERE, e.getMessage()); 
    }
}

  public void setLogger(Logger log) {
        Base_Class.log = log;
    }

private void NewFile() {
  try {
//    out = System.out;
    dbf = DocumentBuilderFactory.newInstance();
    db = dbf.newDocumentBuilder();
//    errorWriter = new OutputStreamWriter(System.err);
//    db.setErrorHandler(new MyErrorHandler(new PrintWriter(errorWriter, true)));
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
public String RootNodeName() {
    return m_root.getNodeName();
}
public Document doc() {
    return m_doc;
}
private void OpenFile(File xmlFile) {   
try {
    
//    out = System.out;
    dbf = DocumentBuilderFactory.newInstance();
    db = dbf.newDocumentBuilder();
//    errorWriter = new OutputStreamWriter(System.err);
 //   db.setErrorHandler(new MyErrorHandler(new PrintWriter(errorWriter, true)));
    m_doc = db.parse(xmlFile);

} 
catch (Exception e) {
   e.printStackTrace();
   log.log(Level.SEVERE, e.getMessage());
   
   }
 }
//public NodeList getElementByTagName(String NodeName) {
//    return m_doc.getElementsByTagName(NodeName);
//}

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
public Node setRoot (Node n1) {
    m_root = n1;
    return m_root;
}
public Node setRoot(String sUniqueNodeString) throws Exception {
    try {
      Node n1 = m_doc.getDocumentElement() ;
       m_root = this.findSubNode(n1, sUniqueNodeString);
      if (m_root == null) throw new Exception ("Unable to set root node " + sUniqueNodeString + " not found " ); 
      return m_root; 
    }
   catch  (Exception e) {
       log.log(Level.SEVERE, e.getMessage()); 
      return null;
     
    }
  }
public Node setRoot(String sUniqueNodeString, String AttributeName, String AttributeValue){
    try {
          Node n1 = m_doc.getDocumentElement() ;
          m_root = this.findSubNode(n1, sUniqueNodeString, AttributeName, AttributeValue);
      if (m_root == null) throw new Exception ("Unable to set root node " + sUniqueNodeString + " " + AttributeName + " " + AttributeValue + " not found " );
      return m_root;
     }
    catch (Exception e) {
        log.log(Level.SEVERE, e.getMessage());
        return m_root;
    }
    }


//public void echo_root() {
//    echo(m_root);
//}
//
//private void outputIndentation(){
//for (int i = 0; i < indent; i++) {
//          //  out.print();
// }
//}
//private void echo(Node n) {
//        outputIndentation();
//
//        int type = n.getNodeType();
//        switch (type) {
//        case Node.ATTRIBUTE_NODE:
//            out.print("ATTR:");
//            printlnCommon(n);
//            break;
//        case Node.CDATA_SECTION_NODE:
//            out.print("CDATA:");
//            printlnCommon(n);
//            break;
//        case Node.COMMENT_NODE:
//            out.print("COMM:");
//            printlnCommon(n);
//            break;
//        case Node.DOCUMENT_FRAGMENT_NODE:
//            out.print("DOC_FRAG:");
//            printlnCommon(n);
//            break;
//        case Node.DOCUMENT_NODE:
//            out.print("DOC:");
//            printlnCommon(n);
//            break;
//        case Node.DOCUMENT_TYPE_NODE:
//            out.print("DOC_TYPE:");
//            printlnCommon(n);
//
//            NamedNodeMap nodeMap = ((DocumentType)n).getEntities();
//            indent += 2;
//            for (int i = 0; i < nodeMap.getLength(); i++) {
//                Entity entity = (Entity)nodeMap.item(i);
//                echo(entity);
//            }
//            indent -= 2;
//            break;
//        case Node.ELEMENT_NODE:
//            out.print("ELEM:");
//            printlnCommon(n);
//
//            NamedNodeMap atts = n.getAttributes();
//            indent += 2;
//            for (int i = 0; i < atts.getLength(); i++) {
//                Node att = atts.item(i);
//                echo(att);
//            }
//            indent -= 2;
//            break;
//        case Node.ENTITY_NODE:
//            out.print("ENT:");
//            printlnCommon(n);
//            break;
//        case Node.ENTITY_REFERENCE_NODE:
//            out.print("ENT_REF:");
//            printlnCommon(n);
//            break;
//        case Node.NOTATION_NODE:
//            out.print("NOTATION:");
//            printlnCommon(n);
//            break;
//        case Node.PROCESSING_INSTRUCTION_NODE:
//            out.print("PROC_INST:");
//            printlnCommon(n);
//            break;
//        case Node.TEXT_NODE:
//            out.print("TEXT:");
//            printlnCommon(n);
//            break;
//        default:
//            out.print("UNSUPPORTED NODE: " + type);
//            printlnCommon(n);
//            break;
//        }
//
//        indent++;
//        for (Node child = n.getFirstChild(); child != null;
//             child = child.getNextSibling()) {
//            echo(child);
//        }
//        indent--;
//    }
//private void printlnCommon(Node n) {
//        out.print(" nodeName=\"" + n.getNodeName() + "\"");
//
//        String val = n.getNamespaceURI();
//        if (val != null) {
//            out.print(" uri=\"" + val + "\"");
//        }
//
//        val = n.getPrefix();
//        if (val != null) {
//            out.print(" pre=\"" + val + "\"");
//        }
//
//        val = n.getLocalName();
//        if (val != null) {
//            out.print(" local=\"" + val + "\"");
//        }
//
//        val = n.getNodeValue();
//        if (val != null) {
//            out.print(" nodeValue=");
//            if (val.trim().equals("")) {
//                // Whitespace
//                out.print("[WS]");
//            } else {
//                out.print("\"" + n.getNodeValue() + "\"");
//            }
//        }
//        out.println();
//    }
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

//private static class MyErrorHandler implements ErrorHandler {
//     
//        private PrintWriter out;
//
//        MyErrorHandler(PrintWriter out) {
//            this.out = out;
//        }
//
//        private String getParseExceptionInfo(SAXParseException spe) {
//            String systemId = spe.getSystemId();
//            if (systemId == null) {
//                systemId = "null";
//            }
//            String info = "URI=" + systemId +
//                " Line=" + spe.getLineNumber() +
//                ": " + spe.getMessage();
//            return info;
//          }
//
//        public void warning(SAXParseException spe) throws SAXException {
//            out.println("Warning: " + getParseExceptionInfo(spe));
//        }
//        
//        public void error(SAXParseException spe) throws SAXException {
//            String message = "Error: " + getParseExceptionInfo(spe);
//            throw new SAXException(message);
//        }
//
//        public void fatalError(SAXParseException spe) throws SAXException {
//            String message = "Fatal Error: " + getParseExceptionInfo(spe);
//            throw new SAXException(message);
//        }
//    }
}

