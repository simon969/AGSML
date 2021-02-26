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
    //    final String AGS_DATASTRUCTURETAG = agsml.Constants.AGS_DATASTRUCTURE;
    //    final String AGS_DATASTRUCTURESERIESTAG = agsml.Constants.AGS_DATASTRUCTURESERIES; 
    //    final String AGS_DATASTRUCTUREAGSVERSION = agsml.Constants.AGS_DATASTRUCTUREAGSVERSION;
    //    final String AGS_DEFAULT = agsml.Constants.AGS_DEFAULT;
    //    final String AGS_ATTRIBUTE_ID = agsml.Constants.AGS_ATTRIBUTE_ID;
  public AGS_DataStructure(){
   }
  public AGS_DataStructure (XML_DOM xml_dom) throws Exception{
        super(xml_dom, agsml.Constants.AGS_DATASTRUCTURESERIES);
   }   
  public AGS_DataStructure (String fXMLFile) throws Exception{
        super (fXMLFile,fileState.MUSTEXIST);
   }   
   
   public AGS_DataStructure (String fXMLFile, String id) throws Exception {
       super (fXMLFile,fileState.MUSTEXIST);
       setDataStructureNode(id);
   }
   
   public AGS_DataStructure (String fXMLFile, String DataStructureSeriesId, Constants.AGSVersion ags_version) throws Exception {
       super (fXMLFile,fileState.MUSTEXIST);
       setDataStructureNode(DataStructureSeriesId, ags_version);
   }
    public Node setDataStructureSeries(String id)  {
    try {
           Node n1 = setRoot(agsml.Constants.AGS_DATASTRUCTURESERIES, agsml.Constants.AGS_ATTRIBUTE_ID, id);
           if (n1 == null) throw new Exception ("Failed set AGSStructureAGSML node, unable to find <DataStructureAGSML id=" + id + ">");
           return n1;
        } 
       catch (Exception e) {
           log.log(Level.SEVERE, e.getMessage());
           return null;
        }
    }
    public Constants.AGSVersion getAGSVersion() {
     try {
            if (m_root == null) throw new Exception ("Failed set AGSStructureAGSML node, DataStructuerSeries root node not specified");

            if(m_root.getNodeName().equals(agsml.Constants.AGS_DATASTRUCTURE)){
                Node n1 = findSubNode(m_root,agsml.Constants.AGS_DATASTRUCTUREAGSVERSION);
                String s1 = n1.getTextContent();
                return Constants.getAGSVersion(s1);
            } else {
                throw new Exception ("Datastructure not identied unable to locate AGS Version"); 
            }
        } catch (Exception e) {
          log.severe(e.getMessage());
          return Constants.AGSVersion.NONE;
        }
    }
    
    public String getDataStructureSeriesName() {
        Node parent = m_root.getParentNode();
        return getAttributeValue(parent,agsml.Constants.AGS_ATTRIBUTE_ID);
    }
    
    public Node setAGSVersion(Constants.AGSVersion ags_version) {
     try {
         
         if (m_root == null) throw new Exception ("Failed set AGSStructureAGSML node, DataStructuerSeries root node not specified");
                
         if (m_root.getNodeName().equals(agsml.Constants.AGS_DATASTRUCTURESERIES)){
             Node n2 = findSubNode(m_root, agsml.Constants.AGS_DATASTRUCTURE, agsml.Constants.AGS_DATASTRUCTUREAGSVERSION, ags_version.toMajorVersion());
             if (n2 == null) throw new Exception ("Failed set AGSStructureAGSML node, unable to find <" + agsml.Constants.AGS_DATASTRUCTUREAGSVERSION + "=" + ags_version + ">");
             setRoot (n2);    
             return n2;
         }
         
         throw new Exception ("DataStructureSeries not identified unable to set AGSDatastructure for AGS version :" + ags_version.toId());
         
     } catch (Exception e) {
          log.log(Level.SEVERE, e.getMessage());
           return null;
     }    
    }
    
 public Node setDataStructureNode(String id)  {
    try {
           Node n1 = findNode(m_doc, agsml.Constants.AGS_DATASTRUCTURE, agsml.Constants.AGS_ATTRIBUTE_ID, id);
           if (n1 == null) throw new Exception ("Failed set AGSStructureAGSML node, unable to find <DataStructureAGSML id=" + id + ">");
           setRoot (n1);
           return n1;
        } 
       catch (Exception e) {
           log.log(Level.SEVERE, e.getMessage());
           return null;
        }
    }
  public Node setDataStructureNode(String DataStructureSeriesId, Constants.AGSVersion ags_version )  {
    try {
                
                Node n1 = setRoot(agsml.Constants.AGS_DATASTRUCTURESERIES, agsml.Constants.AGS_ATTRIBUTE_ID, DataStructureSeriesId);
                if (n1 == null) { throw new Exception ("Failed set AGSStructureAGSML node, unable to find <DataStructureSeries "+  agsml.Constants.AGS_ATTRIBUTE_ID + "=" + DataStructureSeriesId + ">"); }
                return setAGSVersion(ags_version);   
           
        } 
       catch (Exception e) {
           log.log(Level.SEVERE, e.getMessage());
           return null;
        }
    }
 public Node setDataStructureNode(Node n1) {
       return setRoot(n1);
    }
  public NodeList allAGSDataStructureNodes() {
     return m_doc.getElementsByTagName(agsml.Constants.AGS_DATASTRUCTURE);
  }
  public NodeList allAGSDataStructureSeriesNodes() {
     return m_doc.getElementsByTagName(agsml.Constants.AGS_DATASTRUCTURESERIES);
  }
}

