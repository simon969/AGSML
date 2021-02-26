/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agsml;
import java.io.BufferedWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author thomsonsj
 */




class AGS_XML_Writer extends XML_Writer implements Converter{
    private AGS_ReaderLine m_lr;
    private String rootNode; 
  
     
public AGS_XML_Writer (BufferedWriter bw ){
 super (bw);
}
public AGS_XML_Writer(String fout) {
 super(fout);
}
public AGS_XML_Writer() {
    super();
}

public int getAGSDictionary() {
   try {
        super.getAGSDictionary();
        return 1;
    }
   catch (Exception e) {
       return -1;
   }
}
        
public int setAGSReader (Object lr1) {
    if (lr1.getClass().isInstance(AGS_ReaderLine.class)) {
     m_lr = (AGS_ReaderLine)lr1; 
     return 1;
     }
     return 0;
}

protected String getData(Collection header, Collection data, String qheader) {
      String attName;
      String attValue;
      Iterator itr_header = header.iterator();
      Iterator itr_data = data.iterator();
                  while (itr_data.hasNext()){
                         attName = itr_header.next().toString();
                         attValue = itr_data.next().toString();
                         if (qheader.equalsIgnoreCase(attName)) {
                             return attValue;
                             }
                  }
      return null;
}
protected void addData(Collection header, Collection data){
      String attName;
      String attValue;
      Iterator itr_header = header.iterator();
      Iterator itr_data = data.iterator();
                  while (itr_data.hasNext()){
             //     while (itr_header.hasNext()){
                         attName = itr_header.next().toString();
                         attValue = itr_data.next().toString();
                         addAttrib(attName, attValue);
                         }
  }
public boolean containsData(Collection header, Collection data, String qheader, String qdata) {
      String attName;
      String attValue;
      Iterator itr_header = header.iterator();
      Iterator itr_data = data.iterator();
                  while (itr_data.hasNext()){
                         attName = itr_header.next().toString();
                         attValue = itr_data.next().toString();
                         if (qheader.equalsIgnoreCase(attName) && qdata.equalsIgnoreCase(attValue)) {
                             return true;
                             }
                  }
      return false;
}
public void openRoot(){
openElement("root");
}
public void closeRoot(){
closeElement("root");
}
public void AGS_to_XML(AGS_ReaderLine lr){
    AGS_to_XML_Table(lr);
}
public Collection convertHeaderx(String t1, Collection header){
    List<String>  c = new ArrayList<String>();
    String f1;
    String f2;
    
    if (m_dic.find_tableAGS(t1) == null) {
      return null;  
    };
    
       Iterator itr_header = header.iterator();
               while (itr_header.hasNext()){
             //     while (itr_header.hasNext()){
                         f1 = itr_header.next().toString();
                         if (m_dic.find_fieldAGS(f1) != null) {
                         f2 = m_dic.fieldXML_name();
                         } else {
                         f2 = f1;
                         }
                         c.add(f2);
                         }
     return c;
}
public void setAGSLineReader(AGS_ReaderLine lr1) {
     m_lr = lr1; 
}

public int getAGSReader() {
    try {
        
        String s1 =  getProperty(AGS_DATASOURCE);
        Constants.AGSVersion ags1 = m_dic.getAGSVersion();

        switch (ags1) {
            
            case AGS30: 
            case AGS31: 
            case AGS31a:
            m_lr = new AGS_ReaderLine31 (s1);
            break;
            
            case AGS404: 
            case AGS403:
            m_lr = new AGS_ReaderLine40 (s1);
            break;
        }
        
        return ags1.toInt();
    }
    catch (Exception e) {
        return -1;
    }
      
}


public void Process() {
   
    try {
        
      Process(m_ds.m_root);
//      
    } catch (Exception e) {
        log.log(Level.SEVERE, e.getMessage()); 
    }
}


public void Process(Node n1) {
  try {      
      String s1 = n1.toString();
      boolean NodeProcessed = false;
      
      if (folderOut().isEmpty() && fileOut().isEmpty()) {
         Process (n1,null,null,null);
         NodeProcessed = true;
      }
     
     if (folderOut().isEmpty() && !fileOut().isEmpty()) {
         openFile(true, false);
         Process (n1,null,null,null);
         closeFile();
         NodeProcessed = true;
     }
     
     if (!folderOut().isEmpty() && fileOut().isEmpty()) {
        Collection data = null;
        Collection ags_header = null;
    //    Collection xml_header = null; 
        String hole_id = "";
        int count = 0; 
        AGS_ReaderLine lr1 = m_lr.Copy();
        int hole_count = lr1.holecount();
               
        AGS_ReaderLine lr = m_lr.Copy();

        AGS_ReaderLine.LineType result = lr.FindTable(lr.HoleTableName());
     
        do {
            
            result =  lr.NextLine();
            
            if (result == AGS_ReaderLine.LineType.tableHeader) {
                lr.ReadLine();
                ags_header = lr.get_headers();
       //         xml_header = convertHeader(ags_header,lr.HoleTableName());
            }
     
            if (result == AGS_ReaderLine.LineType.rowData) {
                lr.ReadLine();
                data = lr.get_data();
                hole_id = getData(ags_header, data, lr.HoleIdFieldName()); 
                if (IsHoleInList(hole_id) && ContainsSpecialCharacters(hole_id) == false) {
                     count++;
                     log.log(Level.INFO, "Processing " + hole_id + " (" + count +" of " + hole_count + " holes)");
                     m_onlyHoleId = hole_id;
                     setOutputFile (folderOut() + "\\" + hole_id + ".xml");
                     openFile(true, false);
                     Process (n1,null,null,null);
                     closeFile();
                     m_onlyHoleId = "";
                     log.info("File " + fileOut() + " created ");  
                }
             
            }
        } while  (result != AGS_ReaderLine.LineType.tableName && result != AGS_ReaderLine.LineType.EOF);
                //(result == AGS_ReaderLine.LineType.rowData|| result == AGS_ReaderLine.LineType.tableHeader || result == AGS_ReaderLine.LineType.tableUnits);
     
        NodeProcessed = true;  
     }
     
     if (NodeProcessed=false) {
     throw new Exception ("Node " + s1 + " provides " + this.fileOut() + " and " + this.folderOut() + " not clear if single or multiple files are required for output");
     }
  }
  catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage());
  }   
}
protected int addGeols(Node n1, String hole_id) {
   try {
               
        AGS_ReaderLine.LineType result;
        int count = 0;
        int geol_count = 0;
        String s1 = "";
        
        Double geol_depthTOP = null;
        Double geol_depthBASE = null;
        
        int  col_depthTOP = -1;
        int col_depthBASE = -1;
        
        List<String> ags_header = null;
        List<String> data = null;
        List<String> xml_header = null;
        
        Node n2 = null;
        
        AGS_ReaderLine lr1 = m_lr.Copy();
        geol_count = lr1.geolcount();
        
        AGS_ReaderLine lr = m_lr.Copy();
        result = lr.FindTable(lr.GeolTableName());
       
       
        if (result == AGS_ReaderLine.LineType.Error) {
            return -1;
        } 

        if (n1.hasChildNodes() == true)  { 
            NodeList list = n1.getChildNodes();
            n2 = list.item(1);
            
        }
        
        String ags_fname = lr.HoleIdFieldName();
         
        do {
            result =  lr.NextLine();
            
            if (result == AGS_ReaderLine.LineType.tableHeader) {
            lr.ReadLine();                  
            ags_header = lr.get_headers();
            xml_header = convertHeaderXML(ags_header,lr.GeolTableName(),agsml.Constants.Lang.AGS);
            col_depthTOP = lr.get_col_depthTOP();
            col_depthBASE = lr.get_col_depthBASE();
            }
            
            if (result == AGS_ReaderLine.LineType.rowData) {
            lr.ReadLine();
            data = lr.get_data();
            s1 = getData(ags_header, data, ags_fname);
                if (s1.equalsIgnoreCase(hole_id)) {
                    geol_depthTOP = Double.parseDouble(data.get(col_depthTOP));
                    geol_depthBASE = Double.parseDouble(data.get(col_depthBASE));
                    openNode ("Geol");
                        addRow (xml_header, data, false);
                        if (n2 != null) {
                            count++;
                            log.log(Level.INFO, "Processing " + hole_id + " depth(" + geol_depthTOP + "-" + geol_depthBASE + ")");
                            Process (n2, hole_id, geol_depthTOP, geol_depthBASE);
                        }
                    closeNode();
                }
            }
            } while ((result != AGS_ReaderLine.LineType.tableName) && (result != AGS_ReaderLine.LineType.Error) && (result != AGS_ReaderLine.LineType.EOF));
          
        return count;
    }
    
    catch (Exception e) {
        log.log(Level.SEVERE, e.getMessage());
        return -1;
    }
}  
                   
    


protected void addSamples(String hole_id, Double depth_top, Double depth_base ) {
    
}
protected Node Process(Node n1, String hole_id) {
    return Process(n1,hole_id,null,null);
}
protected Node Process(Node n1, String hole_id, Double depth_top, Double depth_base){
 try {


    
    do {
           if (n1.getNodeType() == Node.ELEMENT_NODE) {
               
                String s1 = n1.getNodeName();
                String ntype = "";
                String ags_table = "";

                boolean IsAGSTable = false;
                boolean ChildNodesProcessed = false;
                boolean ForEach = false;
    
                NamedNodeMap n1_attrib = n1.getAttributes();
                
                for (int i = 0; i < n1_attrib.getLength(); ++i)
                {
                    Node attr = n1_attrib.item(i);
                    
                    if (attr.getNodeName().equalsIgnoreCase(Constants.AGS_DATASTRUCTURE_NODETYPE)) {
                        ntype = attr.getNodeValue();
                    }
                    
                    if (attr.getNodeName().equalsIgnoreCase(Constants.AGS_DATATSTRUCTURE_REPEAT_ATTR)) {
                        ForEach = getBoolean(attr.getNodeValue());
                    }
                    
                    if (attr.getNodeName().equalsIgnoreCase(Constants.AGS_ATTRIBUTE_AGSVERSION)) {
                        String ags_version = attr.getNodeValue();
                            if (ags_version.equalsIgnoreCase("$AGS_VERSION")) {
                                ags_version = m_lr.getAGSVersion();
                            }
                        this.addNodeAttrib(Constants.AGS_ATTRIBUTE_AGSVERSION, ags_version);
                    }
                    if (attr.getNodeName().equalsIgnoreCase(Constants.AGS_ATTRIBUTE_SERIES)) {
                        String ags_series = attr.getNodeValue();
                        this.addNodeAttrib(Constants.AGS_ATTRIBUTE_SERIES, ags_series);
                    }
                    if (attr.getNodeName().equalsIgnoreCase(Constants.AGS_ATTRIBUTE_AGSTABLE)) {
                        ags_table = attr.getNodeValue();
                    }
                }
                
               IsAGSTable = IsAGSTable(s1);
                
               if (!IsAGSTable && ags_table.isEmpty()) {
                    openNode(s1);
                    if (n1.hasChildNodes() == true) {
                        Node n2 = n1.getFirstChild();
                        Process(n2, hole_id, null, null);
                    }
                    closeNode();
                    ChildNodesProcessed = true;
                } else {
                    ags_table = s1;
                }
                
                
                
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_PROJ_NODE) && hole_id == null && ForEach==true) {
                    openNode(s1);
                    addTable (ags_table, false);
                    if (n1.hasChildNodes() == true) {
                        Node n2 = n1.getFirstChild();
                        Process(n2, null, null, null);
                    }
                    closeNode();
                    ChildNodesProcessed = true;
                }
                
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_PROJ_NODE) && ForEach==false) {
                    addTable (ags_table,  true);
                }
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_ABBR_NODE)) {
                    addTable (ags_table, true);
                }
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_UNIT_NODE)) {
                    addTable (ags_table, true);
                }
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_CODE_NODE)) {
                    addTable (ags_table, true);
                }
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_HOLE_NODE) && hole_id == null && ForEach == true) {
                    addHoles(n1);
                    ChildNodesProcessed = true;
                }
                
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_GEOL_NODE) && hole_id != null && ForEach == true) {
                    //Add tables by geol depths
                    addGeols(n1, hole_id);
                    ChildNodesProcessed = true;
                }
                
                if (ntype.equals(Constants.AGS_DATAQUERY)) {
                    processQueryNode(n1);
                }                                    
                
                if (s1.equals(Constants.AGS_DATAQUERY_KML)) {
                    processKMLNode(n1);
                }  
                
                if (IsAGSTable == true && hole_id != null && ags_table.equals(Constants.AGS_DATATSTRUCTURE_PROJ_NODE) == false && ChildNodesProcessed == false) {
                    addTable (ags_table, Constants.AGS_HOLEID, hole_id, depth_top, depth_base, true);
                    // log.log(Level.INFO ,"["+ hole_id + "][" + s1 +"]") ;
                }
                if (IsAGSTable == true && hole_id == null && ChildNodesProcessed == false) {
                    addTable (ags_table, true);
                    // log.log(Level.INFO ,"["+ hole_id + "][" + s1 +"]") ;
                }
                if (n1.hasChildNodes() == true && ChildNodesProcessed==false) {
                            Node n2 = n1.getFirstChild();
                            Process(n2, hole_id, null, null);
                }
 
        }
        
        
        if (n1.equals(m_ds.m_root)) {
            break;   
        } else {        
        n1 = n1.getNextSibling();
        }
     } while (n1 != null);
 
    return n1;
   
 } catch (Exception e) {
     log.log(Level.SEVERE, e.getMessage());
     return n1;
 }
}


public void AGS_to_AGSML(AGS_ReaderLine lr1){
  try {      
        
        m_lr = lr1; 
        
        Node n1 =  m_ds.m_root;
        
        if (folderOut().isEmpty()) {
     
         Process (n1);
     
     } else { 
     
        Collection data = null;
        Collection ags_header = null;
        Collection xml_header = null; 
        String hole_id;
        String Filename;
        
        AGS_ReaderLine lr = m_lr.Copy();
        AGS_ReaderLine.LineType result = lr.FindTable(m_lr.HoleTableName());
     
        do {
            result =  lr.ReadLine();
       
            if (result ==  AGS_ReaderLine.LineType.tableHeader) {
                ags_header = lr.get_headers();
                xml_header = convertHeaderXML(ags_header,lr.HoleTableName(),agsml.Constants.Lang.AGS);
            }
     
            if (result ==  AGS_ReaderLine.LineType.rowData) {
                data = lr.get_data();
                hole_id = getData(xml_header, data, "holeId"); 
                
                if (IsHoleInList(hole_id) && ContainsSpecialCharacters(hole_id) == false) {
          
                    m_onlyHoleId = hole_id;
                    
                    setOutputFile(folderOut() + "\\" + hole_id + ".xml");
            
                    openFile(true,false);
            
                    Process (n1, null, null, null);
            
                    closeFile();
      
                    m_onlyHoleId = "";
                    log.info("File " + fileOut() + " created ");  
                }
             
            }
        } while (result == AGS_ReaderLine.LineType.rowData|| result == AGS_ReaderLine.LineType.tableHeader || result == AGS_ReaderLine.LineType.tableUnits);
       }
  }
  catch (Exception e) {
      
  }
}
protected boolean IsDataWithinDepths(List<String> data, int col_depth, Double depthTOP, Double depthBASE) {
    try {
        Double data_depth = null;
        
        if (depthTOP == null || depthBASE == null) {
            return false;
        }
        
        if (col_depth > 0 ) {
          data_depth = Double.parseDouble(data.get(col_depth));
            if (data_depth>=depthTOP && data_depth <=depthBASE) {
              return true;
            } else {
                return false;
            }
        } else { 
         return false;
        }
    }
    catch (Exception e) {
        log.log(Level.SEVERE, e.getMessage());
        return false;
    }
} 
protected int addTable (String xml_tname, boolean OpenCloseTags) {
    return addTable (xml_tname, null, null, null, null, OpenCloseTags);
}
protected int addTable (String xml_tname, String xml_fname, String fvalue, boolean OpenCloseTags) {
    return addTable (xml_tname, xml_fname, fvalue,null,null, OpenCloseTags);
}
protected int addTable(String xml_tname, String xml_fname, String fvalue, Double depthTOP, Double depthBASE, boolean OpenCloseTags){
    try {
            List<String> data = null;
            List<String> ags_header = null;
            List<String> xml_header = null;
            AGS_ReaderLine.LineType result = AGS_ReaderLine.LineType.Empty;
            int row_count = 0;
            String s1;
            String ags_fname= null;
            String ags_tname=null;
            AGS_ReaderLine lr1;
            int col_depthTOP = 0;
            int col_depthBASE = 0;
            
            lr1 = m_lr.Copy();
            
            if (m_dic.find_tableXML(xml_tname) != null)  {
            ags_tname = m_dic.tableAGS_name();
            } else {
            return 0;
            }
            
            if (xml_fname != null ){
                if (xml_fname.length()>0 && m_dic.find_fieldXML(xml_fname) != null) {
                  ags_fname = m_dic.fieldAGS_name(); 
                }
            }

            result = lr1.FindTable(ags_tname);
            
                       
            if (result.equals(AGS_ReaderLine.LineType.tableName )){
             //Move line pointer off table name and into table
             result = lr1.NextLine(); 
                    do {
                    
                        if (result.equals(AGS_ReaderLine.LineType.tableHeader)) {
                            lr1.ReadLine();
                            ags_header = lr1.get_headers();
                            xml_header = convertHeaderXML( ags_header, ags_tname,agsml.Constants.Lang.AGS); 
                            if (depthTOP !=null && depthBASE !=null) {
                                col_depthTOP = lr1.get_col_depthTOP();
                                col_depthBASE = lr1.get_col_depthBASE();
                            }
                        }
                        if (result.equals(AGS_ReaderLine.LineType.tableUnits)){

                        }
                        
                        if (result.equals(AGS_ReaderLine.LineType.rowData)){
                            lr1.ReadLine();
                            data = lr1.get_data();
                            
                                if (ags_fname == null) {
                                        if (OpenCloseTags) openNode (xml_tname);
                                        addRow(xml_header, data, false);
                                        if (OpenCloseTags) closeNode();
                                        row_count++;

                                } else {
                                    s1 = getData(ags_header, data, ags_fname);
                                    if (fvalue.equals(s1)){
                                        if (depthTOP!=null && depthBASE!=null){
                                            if (IsDataWithinDepths(data, col_depthTOP, depthTOP, depthBASE)) {
                                                if (OpenCloseTags) openNode (xml_tname);
                                                    addRow(xml_header, data, false);
                                                if (OpenCloseTags) closeNode();
                                                row_count++;
                                            }
                                        } else {
                                         
                                          if (OpenCloseTags) openNode (xml_tname);
                                                    addRow(xml_header, data, false);
                                                if (OpenCloseTags) closeNode();
                                                row_count++;  
                                        }
                                            
                                    }

                                }
                              
                        }
                    result = lr1.NextLine();
                    //Keep cycling until next table name hit or EOF 
                    } while ((result != AGS_ReaderLine.LineType.tableName) && (result != AGS_ReaderLine.LineType.Error) && (result != AGS_ReaderLine.LineType.EOF));
                }
            if (row_count> 0) {
            log.finest( fvalue + " "  + xml_tname + " (" + String.valueOf(row_count) + ") rows");
            }
            return row_count;
            } 
    catch (Exception e ) {
        log.log(Level.SEVERE, e.getMessage());
        return -1;
    
    }
}
protected int addHoles(Node n1) {
    try {
        
        
        AGS_ReaderLine.LineType result;
        int count = 0;
        int hole_count = 0;
        boolean holeInList;
        Collection ags_header = null;
        Collection data = null;
        Collection xml_header = null;
        String hole_id = null;
        Node n2 = null;
        
        // take a copy of the current linereader so it can be reset at the end
        AGS_ReaderLine lr_full = m_lr.Copy();
        
        hole_count = lr_full.holecount();
        
        // take another copy to cycle through the holetable
        AGS_ReaderLine lr_holes = m_lr.Copy();
        result = lr_holes.FindTable(lr_holes.HoleTableName());

        if (result == AGS_ReaderLine.LineType.EOF) {
           // No hole table found
            return 0;
        } 
        if (result == AGS_ReaderLine.LineType.Error) {
            // Error finding hole table
            return -1;
        }
        
        if (n1.hasChildNodes() == true)  { 
            NodeList list = n1.getChildNodes();
            n2 = list.item(1);
        }

        do {
            result =  lr_holes.NextLine();
            
            if (result == AGS_ReaderLine.LineType.tableHeader) {
            lr_holes.ReadLine();                  
            ags_header = lr_holes.get_headers();
            xml_header = convertHeaderXML(ags_header,lr_holes.HoleTableName(),agsml.Constants.Lang.AGS);
            }
            
            if (result == AGS_ReaderLine.LineType.rowData) {
            lr_holes.ReadLine();
            data = lr_holes.get_data();
            hole_id = getData(ags_header, data, lr_holes.HoleIdFieldName());
            holeInList = IsHoleInList(hole_id);
            
                 if (holeInList) {
                    // set m_lr to contain data only for holeid
                    m_lr = lr_full.Copy(hole_id);
                    addNodeAttrib("id",hole_id);
                     openNode ("Hole");
                        addRow (xml_header, data, false);
                        if (n2 != null) {
                            count++;
                            log.log(Level.INFO, "Processing " + hole_id + " (" + count +" of " + hole_count + " holes)");
                            Process (n2, hole_id, null, null);
                        }
                    closeNode();
                  }
            }
        }  while (result != AGS_ReaderLine.LineType.tableName);
        
        //reset agslinereader to full version
        m_lr = lr_full;
        
        return count;
    }
    
    catch (Exception e) {
        log.log(Level.SEVERE, e.getMessage());
        return -1;
    }
}

protected Collection<String> convertHeaderXML(Collection header1, String tname1) {
 return  super.convertHeaderXML(header1, tname1,agsml.Constants.Lang.AGS);
}

public void AGS_to_XML_Nest(AGS_ReaderLine lr1){
      String table_name = "";
      Collection header = null;
      Collection data = null;
      String hole_id = null;
      boolean data_found = false;
      int nested_data_count = 0;
      AGS_ReaderLine.LineType result;
      AGS_ReaderLine lr2;
      do {
              result = lr1.NextLine();

              if (result == AGS_ReaderLine.LineType.tableName) {

                  table_name = lr1.get_tablename();
                    
                  if (table_name.equalsIgnoreCase("proj")){
                    do {
                         result = lr1.NextLine();
                          if (result.equals(AGS_ReaderLine.LineType.tableHeader)) {
                            lr1.ReadLine();
                            header = lr1.get_headers();
                            openElement("proj");
                          }
                          if (result.equals(AGS_ReaderLine.LineType.rowData)) {
                           lr1.ReadLine();
                          data = lr1.get_data();
                          addData(header, data);
                          data_found = true;
                        } 
                    } while (data_found == false);
                  }

                  if (table_name.equalsIgnoreCase("hole")) {
                      openElement ("holes");
                        do {
                            result = lr1.NextLine();
                            
                                if (result.equals(AGS_ReaderLine.LineType.tableName)) {
                                    lr1.ReadLine();
                                    table_name = lr1.get_tablename();
                                }
                                if (result.equals(AGS_ReaderLine.LineType.tableHeader)) {
                                    lr1.ReadLine();
                                    header = lr1.get_headers();
                                }

                                if (result.equals(AGS_ReaderLine.LineType.rowData)) {
                                    lr1.ReadLine();
                                    openElement("hole");
                                    data = lr1.get_data();
                                    addData(header, data);
                                    hole_id = getData(header,data,"hole_id");
                                    lr2 = lr1.Copy();
                                        System.out.print("Finding nested data for " + hole_id);
                                        nested_data_count = AddNestedTables(lr2, "hole","hole_id", hole_id);
                                        System.out.print(" " + nested_data_count + " found \n");
                                    closeElement("hole");
                                }
                            } while (table_name.equalsIgnoreCase("hole"));
                    closeElement("holes");  
                   }
                }
      } while (result != AGS_ReaderLine.LineType.Error); 
      if (data_found==true) {
      closeElement("proj");
    }
}

private int AddNestedTables(AGS_ReaderLine lr1, String parent_table, String q_header, String q_data ) {
      String table_name = "";
      Collection header = null;
      Collection data = null;
      int data_count = 0;
      AGS_ReaderLine.LineType result;
       do {
             result = lr1.NextLine();

                if (result == AGS_ReaderLine.LineType.tableName) {
                    lr1.ReadLine();    
                    table_name = lr1.get_tablename();
                }
                            
                if (result == AGS_ReaderLine.LineType.tableHeader) {
                    lr1.ReadLine();
                    header = lr1.get_headers();
                }
                    
                 
                if (result == AGS_ReaderLine.LineType.rowData) {
                    lr1.ReadLine();
                    data = lr1.get_data();
                        if (valid_nested_table(parent_table,table_name)) {
                            if (containsData(header,data, q_header,q_data)){
                                     openElement(table_name);
                                     AddNestedData(header,data, q_header);
                                     closeElement(table_name);
                                     data_count++;
                            }
                        }
                }
           } while (result != AGS_ReaderLine.LineType.Error);
return data_count;
}
private boolean valid_nested_table(String parent_table, String table_name ){
    boolean retval = true;
    if (parent_table.equalsIgnoreCase("hole")) {
    if (table_name.equalsIgnoreCase("proj")) {retval = false;}
    if (table_name.equalsIgnoreCase("hole")) {retval = false;}
    if (table_name.equalsIgnoreCase("abbr")) {retval = false;}
    if (table_name.equalsIgnoreCase("unit")) {retval = false;}
    }
    return retval;
}
private void AddNestedData(Collection header, Collection data, String key_header){
      String attName;
      String attValue;
      Iterator itr_header = header.iterator();
      Iterator itr_data = data.iterator();
                  while (itr_data.hasNext()){
             //     while (itr_header.hasNext()){
                         attName = itr_header.next().toString();
                         attValue = itr_data.next().toString();
                        if (!attName.equalsIgnoreCase(key_header)) {
                             addAttrib(attName, attValue);
                        }
                  }
    }
public void AGS_to_XML_Table(AGS_ReaderLine lr){
     
      String table_name = "";
      String table_name_plural = "";
      String proj_name = "";
      Collection header = null;
      Collection data = null;
      
      
       AGS_ReaderLine.LineType result;
       do {
              result = lr.NextLine();

              if (result == AGS_ReaderLine.LineType.tableName) {
                  lr.ReadLine();
                  table_name = lr.get_tablename();

                 if (table_name.equals("proj")){
                     if (table_name_plural.length()>0) {closeElement(table_name_plural);}
                     if (proj_name.length()>0) {closeElement(proj_name);}
                    proj_name = table_name;
                    openElement(proj_name);
                    table_name_plural="";
                  }
                if (table_name.equals("proj") == false) {
                  if (table_name_plural.length()>0) {
                     closeElement(table_name_plural);
                  }
                  table_name_plural =  table_name + "s";
                  openElement(table_name_plural);
                  }
               }
              
              if (result == AGS_ReaderLine.LineType.tableHeader) {
                 lr.ReadLine(); 
                 header = lr.get_headers();
               }

              if (result == AGS_ReaderLine.LineType.tableUnits) {
                 
              }
              if (result == AGS_ReaderLine.LineType.rowData) {
                    lr.ReadLine();
                    data = lr.get_data();
                    if (header != null) {
                        if (table_name.equals("proj")) {
                            addData(header,data);
                        } else {
                            openElement(table_name);
                            addData(header, data);
                            closeElement(table_name);
                        }
                 newLine();
                 }
              }
          } while (result != AGS_ReaderLine.LineType.Error);
       closeElement(table_name_plural);
       closeElement(proj_name);
}
   public class cColumn {
     String m_Header;
     String m_Data;
     String m_Description;
     String m_Example;
     String m_Units;
    public String getHeader(){
         return m_Header;
    }
    public void setHeader(String vNewData ) {
        m_Header=vNewData;
    }
        public String getData(){
         return m_Data;
    }
    public void setData(String vNewData ) {
        m_Data=vNewData;
    }
        public String getExample(){
         return m_Example;
    }
    public void setExample(String vNewData ) {
        m_Example=vNewData;
    }
        public String getDescription(){
         return m_Description;
    }
    public void setDescription(String vNewData ) {
        m_Description=vNewData;
    }
   }
   public class cRow extends ArrayList<cColumn> {

       public cColumn addHeader(String vHeader, String vData){
       cColumn c;
       c = getHeader(vHeader);
            if (c == null) {
                c = new cColumn();
                c.setHeader(vHeader);
                c.setData(vData);
                    this.add(c);
                        return c;
            } else {
                c.setData(vData);
                        return c;
            }
   }

       public cColumn getHeader(String vHeader) {
       cColumn c;
       for (int i=1; i<this.size();i++) {
           c = this.get(i);
           if (c.getHeader().equals(vHeader)){
           return c;
           }
       }
       return null;
       }
   }
private int addRow(Collection header, Collection data, Boolean AddEmptyData) {
    try {
            Iterator h_it;
            Iterator d_it;
            String h1;
            String d1;
            h_it = header.iterator();
            d_it = data.iterator();
            
            if (header.size() != data.size()){
               log.info("Possible error Header and data size should be same:  Header(" + header.size() + ") Data(" + data.size() + ")");
            }
            
            do {
                h1 = h_it.next().toString();
                d1 = d_it.next().toString();
                    if (!(d1.length() == 0 && AddEmptyData == false)){
                        openNode(h1);
                        addValid (d1);
                        closeNode();
                    }
            } while(h_it.hasNext());
            
            return 1;
    }
    catch (Exception e) {
        log.severe(e.getMessage());
        return -1;
    }
}
}

