/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agsml;
import java.util.logging.Level;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

/**
 *
 * @author Simon
 */

public class AGS_DataStructure extends XML_DOM {
        final String DataStructureAGSML = "DataStructureAGSML";
        final String DefaultDataStructureAGSMLId = "Default";
        final String DataStructureAGSMLId_AttributeTag = "id";
  public AGS_DataStructure(){
   }
  public AGS_DataStructure (String fXMLFile) throws Exception{
        super (fXMLFile,fileState.MUSTEXIST);
        setDataStructureID(DefaultDataStructureAGSMLId); 
    }   
   
   public AGS_DataStructure (String fXMLFile, String id) throws Exception {
       super (fXMLFile,fileState.MUSTEXIST);
       setDataStructureID(id);
   }
 public Node setDataStructureID(String id)  {
    try {
           Node n1 = setRoot(DataStructureAGSML, DataStructureAGSMLId_AttributeTag, id);
           if (n1 == null) throw new Exception ("Failed set AGSStructureAGSML node, unable to find <DataStructureAGSML id=" + id + ">");
           return n1;
        } 
       catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());
           return null;
        }
    }
 public Node setDataStructureNode(Node n1) {
       return setRoot(n1);
    }
  public NodeList allAGSDataStructureNodes() {
     return m_doc.getElementsByTagName(DataStructureAGSML);
  }
 
}

