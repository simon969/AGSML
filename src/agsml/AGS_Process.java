package agsml;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import agsml.AGS_Data;
import agsml.AGS_Data.DataType;

/**
 *
 * @author thomsonsj
 */
class AGS_Process extends XML_DOM {
    
     

    
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
        
        AGS_Data source = new AGS_Data(sourcefile);
        DataType source_dt =  source.getDataType ();
        
        AGS_Data output = new AGS_Data(outputfile);
        DataType output_dt =  output.getDataType ();
        

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
}

 