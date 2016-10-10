package agsml;
import org.w3c.dom.NodeList;
/**
 *
 * @author thomsonsj
 */
public class AGS_Process  extends XML_DOM {
    
    public final String AGS_Process_DATAQUERY ="DataQueryAGSML";
    public final String AGS_Process_DATASTRUCTURE = "DataStructureAGSML";
    
 public NodeList getAllDataQueryAGSML() {
     return m_doc.getElementsByTagName(AGS_Process_DATAQUERY);
 }
  public NodeList getAllDataStructureAGSML() {
     return m_doc.getElementsByTagName(AGS_Process_DATASTRUCTURE);
 } 
   public AGS_Process (String fXMLFile) {
        super (fXMLFile); 
  }
}

