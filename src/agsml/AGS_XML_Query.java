/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agsml;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author thomsonsj
 */
class AGS_XML_Query extends XML_DOM {
      
 public AGS_XML_Query(){
   }   
  public NodeList Where(NodeList nList1, String fName, String fValue) {
      return Where(nList1, fName, fValue,true);
  }
  public NodeList WhereNOT(NodeList nList1, String fName, String fValue) {
      return Where(nList1, fName, fValue,false);
  }
  public NodeList Where(NodeList nList1, String fName, String fValue, boolean bolTrueFalse) {
   try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      m_doc = builder.newDocument();  
      // Create from whole cloth
      Node root = m_doc.createElement("root"); 
      m_doc.appendChild(root);
      
       for (int i = 0; i < nList1.getLength(); i++) {
            Node n1 = nList1.item(i);
            NodeList nList2 = n1.getChildNodes();
                for (int j = 0; j < nList2.getLength(); j++) {
                    if (nList2.item(j).getNodeName().equalsIgnoreCase(fName)) {
                        if (nList2.item(j).getTextContent().contains(fValue) == bolTrueFalse) {
                           Node newNode = n1.cloneNode(true);
                            m_doc.adoptNode(newNode);
                            root.appendChild(newNode);
                        }
                    }
                }
        }
       
       return root.getChildNodes();
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    }   
}
