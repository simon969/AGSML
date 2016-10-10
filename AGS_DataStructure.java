/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agsml;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

/**
 *
 * @author Simon
 */

public class AGS_DataStructure extends XML_DOM {
        final String constDataStructureTag = "DataStructureAGSML";
   public AGS_DataStructure (String fXMLFile){
        super (fXMLFile);
        set_DataStructureAGSML("Default"); 
    }   
      public AGS_DataStructure (String fXMLFile, String id){
        super (fXMLFile);
        set_DataStructureAGSML(id); 
    }
 public Node set_DataStructureAGSML(String id) {
       return setRoot(constDataStructureTag, "id", id);
    }
}

