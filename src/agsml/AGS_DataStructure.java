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
        final String DataStructureSeries = "DataStructureSeries";
        final String DataStructureAGSVersion = "agsversion";
        final String DefaultDataStructureAGSMLId = "Default";
        final String DataStructureAGSMLId_AttributeTag = "id";
  public AGS_DataStructure(){
   }
  public AGS_DataStructure (String fXMLFile) throws Exception{
        super (fXMLFile,fileState.MUSTEXIST);
        setDataStructureNode(DefaultDataStructureAGSMLId); 
    }   
   
   public AGS_DataStructure (String fXMLFile, String id) throws Exception {
       super (fXMLFile,fileState.MUSTEXIST);
       setDataStructureNode(id);
   }
   
   public AGS_DataStructure (String fXMLFile, String id, AGS_Dictionary.AGSVersion ags_version) throws Exception {
       super (fXMLFile,fileState.MUSTEXIST);
       setDataStructureNode(id, ags_version);
   }
 public Node setDataStructureNode(String id)  {
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
  public Node setDataStructureNode(String id, AGS_Dictionary.AGSVersion ags_version )  {
    try {
            Node n1 = setRoot(DataStructureSeries, DataStructureAGSMLId_AttributeTag, id);
                if (n1 == null) { throw new Exception ("Failed set AGSStructureAGSML node, unable to find <DataStructureSeries"+  DataStructureAGSMLId_AttributeTag + "=" + id + ">"); }
                    Node n2 = this.findSubNode(n1, DataStructureAGSML, DataStructureAGSVersion, ags_version.toMajorVersion());
                        if (n2 == null) throw new Exception ("Failed set AGSStructureAGSML node, unable to find <" + DataStructureAGSVersion + "=" + ags_version + ">");
                        setRoot (n2);    
                        return n2;
           
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
  public NodeList allAGSDataStructureSeriesNodes() {
     return m_doc.getElementsByTagName(DataStructureSeries);
  }
}

