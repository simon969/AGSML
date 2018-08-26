package agsml;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.File;
import java.util.List;
/**
 *
 * @author thomsonsj
 */
class Base_Writer extends Base_Class{
    
    protected AGS_Dictionary m_dic;
    protected AGS_DataStructure m_ds;
    protected Set m_hs;
    
    protected String m_onlyHoleId = "";
    protected NodeList m_holeList;
    protected String[] m_Binary64Fields;
    
    public final static String AGS_HOLELIST = "HoleList";
    public final static String AGS_HOLELISTFILE = "HoleListFile";
    
    public final static String AGS_OUTPUTFOLDER = "AGSOutputAGSMLFolder";
    public final static String AGS_OUTPUTFILE = "AGSOutputAGSMLFile";
    public final static String AGS_OUTPUTFIELD = "AGSOutputAGSMLField";
    public final static String AGS_OUTPUT64BINARYFIELDS = "AGSOutput64BinaryFields";
    
    public final static String AGS_PROJECTID = "AGSProjectID";
    public final static String AGS_OUTPUTFILEBASEDONFIELD = "AGSOutputAGSMLFileFromField";
    
    public final static String AGS_DATASTRUCTURE = "DataStructureAGSML";
    
    public final static String AGS_DATASTRUCTUREFILENAME = "AGSDataStructureFile";
    public final static String AGS_DATASTRUCTUREID = "AGSDataStructureID";
    
    public final static String AGS_DICTIONARYFILENAME = "AGSDictionaryFile";
    public final static String AGS_DICTIONARYID = "AGSDictionaryID";
    
    public final static String AGS_DATASOURCE = "AGSDataSource"; 
    public final static String AGS_STRICTLYONLY = "AGSFieldsOnly";
    
    
    public String onlyHoleId() {
        return m_onlyHoleId;
    }
    public String folderOut() {
        return getProperty(AGS_OUTPUTFOLDER);
    }
    public String fileOut() {
        return getProperty(AGS_OUTPUTFILE);
    }
    public String fieldOut() {
        return getProperty(AGS_OUTPUTFIELD);
    }

    public int projectId() {
        return getProperty_int(AGS_PROJECTID);
    }
    public String OutputFileNameFromField() {
        return getProperty (AGS_OUTPUTFILEBASEDONFIELD);
    }
    public String OutputBinary64Fields() {
         return getProperty (AGS_OUTPUT64BINARYFIELDS);
    }
    protected  int splitBinary64Fields() {
        String s1 = getProperty (AGS_OUTPUT64BINARYFIELDS);
        if (s1.length()> 0) {
        m_Binary64Fields = s1.split(";", 0);
        return m_Binary64Fields.length;
        } else {
        return 0;    
        }
    }
     boolean IsBinary64Field (String s1) {
          for (int i = 0; i < m_Binary64Fields.length; i++) {
             if (m_Binary64Fields[i].equalsIgnoreCase(s1)) {
                 return true;
             }
         }
       return false;
 }
    
    public String AGSDataSource() {
       return getProperty (AGS_DATASOURCE);
   }
    
    void setAGSMLFileNameFromField(String fieldname) {
        if (fieldname != null) {
         setProperty(AGS_OUTPUTFILEBASEDONFIELD,fieldname);
        }
    } 
    void setAGSFieldsOnly(String YN) {
      setProperty(AGS_STRICTLYONLY,YN)  ;
    }
    
    public boolean AGSFieldsOnly() {
        return getProperty_bool(AGS_STRICTLYONLY);
    }
    void setAGSMLOutputField(String fieldname) {
        if (fieldname != null) {
         setProperty(AGS_OUTPUTFIELD,fieldname);
        }
    }
    void setAGSMLPath(String agsmlpath) {
       setProperty(AGS_OUTPUTFIELD,agsmlpath);
    }    
    public void setHoleList (NodeList nl1) {
        m_holeList = nl1;
    }  
     public void setOutputFileNameFromField (String s1) {
        setProperty(AGS_OUTPUTFILEBASEDONFIELD,s1);
        
    }
    
    public void setOutput64BinaryFields (String s1) {
        setProperty(AGS_OUTPUT64BINARYFIELDS,s1);
    }
    
    public void setProjectId(int id) {
       setProperty(AGS_PROJECTID,String.valueOf(id));
    }

    public void setAGSDictionary(String xml_file) {
        setProperty (AGS_DICTIONARYFILENAME, xml_file);   
    }
    public void setAGSDictionary(AGS_Dictionary ags_dic) {
        m_dic = ags_dic;   
    }
    public void setAGSDataStructure(AGS_DataStructure ags_ds) {
        m_ds = ags_ds;   
    }
    public void setAGSDictionaryId(String xml_node) {
        setProperty (AGS_DICTIONARYID, xml_node);   
    }
    public void setAGSDictionary(String xml_file,String xml_node) {
         setProperty (AGS_DICTIONARYFILENAME, xml_file); 
         setProperty (AGS_DICTIONARYID, xml_node);
    }    
    public int getAGSDictionary() {
        try {
            String xml_file = getProperty (AGS_DICTIONARYFILENAME);
            String xml_node = getProperty (AGS_DICTIONARYID);
            
            m_dic = new AGS_Dictionary (xml_file,xml_node);
            log_INFO("AGS_Dictionary set (File:" + xml_file + " Node:" + xml_node +")");
            return 1;
        } 

        catch (Exception e) {
        m_log.log(Level.SEVERE, e.getMessage());
        return -1;
        } 
     }
    public int getAGSDictionary(Node n1) {
        try {
            m_dic = new AGS_Dictionary();
            m_dic.setRoot(n1);
            log_INFO("AGS_Dictionary set (Node:" + n1.toString() + ")");
            return 1;
        }
        catch (Exception e) {
            m_log.log(Level.SEVERE, e.getMessage());
            return -1;
        }
    }
            
    public void setAGSDataStructureFile(String xml_file) {
        setProperty (AGS_DATASTRUCTUREFILENAME, xml_file);   
    }
     
    public void setAGSDataStructureId(String xml_node){
        setProperty (AGS_DATASTRUCTUREID, xml_node);  
    }
    public void setAGSDataStructure(String xml_file, String xml_node){
        setProperty (AGS_DATASTRUCTUREFILENAME, xml_file); 
        setProperty (AGS_DATASTRUCTUREID, xml_node);  
    }
    public int getAGSDataStructure() {
        try {
            
            String xml_file = getProperty(AGS_DATASTRUCTUREFILENAME); 
            String xml_node = getProperty(AGS_DATASTRUCTUREID);

            m_ds = new AGS_DataStructure (xml_file,  xml_node);
            m_hs = new HashSet();
            
            setProperties(m_ds.RootNode().getAttributes());
            
            log_INFO("AGS_DataStructure set (File:" + xml_file + " Node:" + xml_node +")");
            
            return 1;
        } 

        catch (Exception e) {
        m_log.log(Level.SEVERE, e.getMessage());
        return -1;
        }
    }
    public int getAGSDataStructure(Node n1) {
        try {
            m_ds = new AGS_DataStructure();
            m_ds.setRoot(n1);
            
            log_INFO("AGS_DataStructure set (Node:" + n1.toString() + ")");
            
            return 1;
        }
        catch (Exception e) {
             m_log.log(Level.SEVERE, e.getMessage());
             return -1;
        }
    }
    public void setAGSDataSource(String s1) {
        setProperty (AGS_DATASOURCE, s1);   
    }
    
    public void setOutputFolder (String s1) {
        setProperty(AGS_OUTPUTFOLDER,s1);
    }
    
    public void setOutputFile (String s1) {
        setProperty(AGS_OUTPUTFILE,s1);
    }
    
    public void setOutputFileFolder (String s1) {
        try {
            File f = new File(s1);

            if (f.isDirectory()) {
            setProperty(AGS_OUTPUTFOLDER,s1);
            } 

            if (f.isFile()) {
            setProperty(AGS_OUTPUTFILE,s1);
            }
        }
        catch (Exception e){
             m_log.log(Level.SEVERE, e.getMessage());
        }
    }
    
    protected NodeList getHoleList (Node n1) {
      try {
        if (n1 != null) {
          NodeList nodeList = n1.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                 Node currentNode = nodeList.item(i);
                     if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                          String nodeName = currentNode.getNodeName();
                                if (nodeName.equalsIgnoreCase(Base_Writer.AGS_HOLELIST)) {
                                m_holeList = currentNode.getChildNodes();
                                }
                    }
                } 
        }        
        if (m_holeList == null) {
           String xml_hole_file = getProperty (Base_Writer.AGS_HOLELISTFILE);
            if (!xml_hole_file.isEmpty()) {
                XML_DOM holes = new XML_DOM (xml_hole_file);
                m_holeList = holes.doc().getElementsByTagName("HoleId");
            }
        }
        return m_holeList;
        }   
  
        catch (Exception e)  {
         m_log.log(Level.SEVERE, e.getMessage());         
        return null;
        }
  }
}
