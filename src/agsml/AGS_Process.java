package agsml;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


/**
 *
 * @author thomsonsj
 */
class AGS_Process extends XML_DOM {
    
    public static final String AGS_DATASTRUCTURE = "DataStructureAGSML";
    public static final String AGS_DATASTRUCTURETYPE = "type";
    public static final String AGS_STYLESHEET = "stylesheet"; 
    public final static String AGS_OUTPUTFILE = "AGSOutputAGSMLFile";
    
    public enum DataType {
     NotKnown,
     AGS,
     XML,
     KML,
     AccessDb,
     ServerDb,
     }
    
     public enum DataStructureType {
     NotKnown,
     AGStoXML,
     AGStoSQL,
     SQLtoXML,
     SQLtoKML,
     SQLtoAGS,
     XMLtoAGS,
     XMLtoSQL,
     } 
     
  public DataStructureType getDataStructureType(String s1) {
     
      if (s1.contains("AGStoSQL")) return DataStructureType.AGStoSQL;
      if (s1.contains("AGStoXML")) return DataStructureType.AGStoXML;
      if (s1.contains("SQLtoXML")) return DataStructureType.SQLtoXML; 
      if (s1.contains("SQLtoKML")) return DataStructureType.SQLtoKML; 
      
      return DataStructureType.NotKnown;
  }

   public AGS_Process (String fXMLFile) {
        super (fXMLFile,fileState.MUSTEXIST); 
        
  }
  
  public DataStructureType getDataStructureType(String sourcefile, String outputfile ) {
      
        DataType source_dt =  getDataType (sourcefile);
        DataType output_dt =  getDataType (outputfile);

        if (source_dt.equals(DataType.AGS)  &&  output_dt.equals(DataType.AccessDb)) {
        return DataStructureType.AGStoSQL;
        }

        if (source_dt.equals(DataType.AGS)  &&  output_dt.equals(DataType.ServerDb)) {
        return DataStructureType.AGStoSQL;
        }

        if (source_dt.equals(DataType.AGS)  &&  output_dt.equals(DataType.XML)) {
        return DataStructureType.AGStoXML;
        }

        if (source_dt.equals(DataType.AccessDb)  &&  output_dt.equals(DataType.XML)) {
        return DataStructureType.SQLtoXML;
        }

        if (source_dt.equals(DataType.ServerDb)  &&  output_dt.equals(DataType.XML)) {
        return DataStructureType.SQLtoXML;
        }
        
        if (source_dt.equals(DataType.ServerDb)  &&  output_dt.equals(DataType.KML)) {
        return DataStructureType.SQLtoKML;
        }
        
        return DataStructureType.NotKnown;
   
  } 
  
  DataType getDataType (String source) {
       
         File db = new File (source); 
         
         if (db.isFile()) {
             Filename f1 = new Filename(db.getName(),'\\' ,'.');
             String ext = f1.extension();
                if (ext.equals("mdb") || ext.equals("gpj") || ext.equals("accdb")) {
                    return DataType.AccessDb;
                }
                if (ext.equals("xml")) {
                    return DataType.XML;
                }
                if (ext.equals("ags")) {
                    return DataType.AGS;
                }
                if (ext.equals("kml")) {
                    return DataType.XML;
                }
         } else {
                if (source.contains(";")) {
                    return DataType.ServerDb;
                }
                if (source.contains (".ags")) {
                    return DataType.AGS;
                }
                if (source.contains(".xml")) {
                    return DataType.XML;
                }
                if (source.contains(".kml")) {
                    return DataType.KML;
                }
        }
         
         return DataType.NotKnown;
   }
   
 
   
}

