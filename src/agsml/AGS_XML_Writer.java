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
    
    public final String AGS_DATASTRUCTURETYPE = "Type";
    public final String AGS_DATAQUERY = "DataQueryAGSML";
     
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
        
public int setReader (Object lr1) {
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
        AGS_Dictionary.AGSVersion ags1 = m_dic.getAGSVersion();

        switch (ags1) {
            case AGS30: case AGS31: case AGS31a:
            m_lr = new AGS_ReaderLine31 (s1);
            case AGS404: case AGS403:
            m_lr = new AGS_ReaderLine40 (s1);
        }
        
        return ags1.toInt();
    }
    catch (Exception e) {
        return -1;
    }
      
}


public void Process() {
   
    try {
        
      Node n1 =  m_ds.m_root;
      if (n1.getNodeType() == Node.ELEMENT_NODE) {
            String s1 = n1.getNodeName();
            Node n2 = n1.getFirstChild();
            openNode (s1);
            if (n2 != null) {
                    Process(n2);
            }
            closeNode();
      }
    } catch (Exception e) {
        
    }
}


public void Process(Node n1) {
  try {      
      String s1 = n1.toString();
      boolean NodeProcessed = false;
      
      if (folderOut().isEmpty() && fileOut().isEmpty()) {
         Process (n1,null);
         NodeProcessed = true;
      }
     
     if (folderOut().isEmpty() && !fileOut().isEmpty()) {
         openFile(true);
         Process (n1,null);
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
                     m_log.log(Level.INFO, "Processing " + hole_id + " (" + count +" of " + hole_count + " holes)");
                     m_onlyHoleId = hole_id;
                     setOutputFile (folderOut() + "\\" + hole_id + ".xml");
                     openFile(true);
                     Process (n1,null);
                     closeFile();
                     m_onlyHoleId = "";
                     m_log.info("File " + fileOut() + " created ");  
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
      m_log.log(Level.SEVERE, e.getMessage());
  }   
}
protected Node Process(Node n1, String hole_id){
 try {
    String s1 = null;
    String ntype = null;

    boolean IsAGSTable;
    boolean ChildNodesProcessed = false;
    
    do {
           if (n1.getNodeType() == Node.ELEMENT_NODE) {
               
                s1 = n1.getNodeName();
              
                ntype = getAttributeValue( n1, "type");
                
                IsAGSTable = IsAGSTable(s1);
                 
                if (IsAGSTable == false) {
                    openNode (s1);
                }
                
                if (s1.equals("Proj") && hole_id == null) {
                    openNode(s1);
                    addTable (s1, null, null, false);
                    if (n1.hasChildNodes() == true) {
                        NodeList list = n1.getChildNodes();
                        Node n2 = list.item(0); 
                        Process(n2, hole_id);
                    }
                    closeNode();
                    ChildNodesProcessed = true;
                }
                
                if (s1.equals("Proj") && hole_id != null) {
                    addTable (s1, null, null, true);
                }
                 
                if (s1.equals("Hole") && hole_id == null) {
                    addHoles(n1);
                    ChildNodesProcessed = true;
                }
                
                if (ntype.equals("DataQueryAGSML")) {
                    processQueryNode(n1);
                }                                    
                
                if (s1.equals("DataQueryKML")) {
                    processKMLNode(n1);
                }  
                
                if (IsAGSTable == true && hole_id != null && s1.equals("Proj") == false) {
                    addTable (s1, "HoleId", hole_id, true);
                    // m_log.log(Level.INFO ,"["+ hole_id + "][" + s1 +"]") ;
                }
                
                if (n1.hasChildNodes() == true && ChildNodesProcessed==false) {
                            NodeList list = n1.getChildNodes();
                            Node n2 = list.item(0); 
                            Process(n2, hole_id);
                }
                
                if (IsAGSTable == false) {
                    closeNode();
                }
        }
      
           n1 = n1.getNextSibling();
      
      
     } while (n1 != null);
   // while (n1 != null );
    return n1;
   
 } catch (Exception e) {
     m_log.log(Level.SEVERE, e.getMessage());
     return n1;
 }
}


public void AGS_to_AGSML(AGS_ReaderLine lr1){
  try {      
        
        m_lr = lr1; 
        
        Node n1 =  m_ds.m_root;
        
        if (folderOut().isEmpty()) {
     
         Process (n1,null);
     
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
                xml_header = convertHeaderXML(ags_header,lr.HoleTableName(),AGS_Dictionary.Lang.AGS);
            }
     
            if (result ==  AGS_ReaderLine.LineType.rowData) {
                data = lr.get_data();
                hole_id = getData(xml_header, data, "holeId"); 
                
                if (IsHoleInList(hole_id) && ContainsSpecialCharacters(hole_id) == false) {
          
                    m_onlyHoleId = hole_id;
                    
                    setOutputFile(folderOut() + "\\" + hole_id + ".xml");
            
                    openFile(true);
            
                    Process (n1, null);
            
                    closeFile();
      
                    m_onlyHoleId = "";
                    m_log.info("File " + fileOut() + " created ");  
                }
             
            }
        } while (result == AGS_ReaderLine.LineType.rowData|| result == AGS_ReaderLine.LineType.tableHeader || result == AGS_ReaderLine.LineType.tableUnits);
       }
  }
  catch (Exception e) {
      
  }
}


protected int addTable(String xml_tname, String xml_fname, String fvalue, boolean OpenCloseTags){
    try {
            Collection<String> data = null;
            Collection<String> ags_header = null;
            Collection<String> xml_header = null;
            AGS_ReaderLine.LineType result = AGS_ReaderLine.LineType.Empty;
            int row_count = 0;
            String s1;
            String ags_fname= null;
            String ags_tname=null;
            AGS_ReaderLine lr1;

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
                            xml_header = convertHeaderXML( ags_header, ags_tname,AGS_Dictionary.Lang.AGS);
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
                                    if (OpenCloseTags) openNode (xml_tname);
                                    addRow(xml_header, data, false);
                                    if (OpenCloseTags) closeNode();
                                    row_count++;
                                }

                            }

                        }
                    result = lr1.NextLine();
                    //Keep cycling until next table name hit or EOF 
                    } while ((result != AGS_ReaderLine.LineType.tableName) && (result != AGS_ReaderLine.LineType.Error) && (result != AGS_ReaderLine.LineType.EOF));
                }
            if (row_count> 0) {
            m_log.fine( fvalue + " "  + xml_tname + " (" + String.valueOf(row_count) + ") rows");
            }
            return row_count;
            } 
    catch (Exception e ) {
        m_log.log(Level.SEVERE, e.getMessage());
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
        
        AGS_ReaderLine lr1 = m_lr.Copy();
        hole_count = lr1.holecount();
        
        AGS_ReaderLine lr = m_lr.Copy();
        result = lr.FindTable(lr.HoleTableName());

        if (result == AGS_ReaderLine.LineType.Error) {
            return -1;
        }

        if (n1.hasChildNodes() == true)  { 
            NodeList list = n1.getChildNodes();
            n2 = list.item(1);
        }

        do {
            result =  lr.NextLine();
            
            if (result == AGS_ReaderLine.LineType.tableHeader) {
            lr.ReadLine();                  
            ags_header = lr.get_headers();
            xml_header = convertHeaderXML(ags_header,lr.HoleTableName(),AGS_Dictionary.Lang.AGS);
            }
            
            if (result == AGS_ReaderLine.LineType.rowData) {
            lr.ReadLine();
            data = lr.get_data();
            hole_id = getData(ags_header, data, lr.HoleIdFieldName());
            holeInList = IsHoleInList(hole_id);
            
                 if (holeInList) {
                   addNodeAttrib("id",hole_id);
                     openNode ("Hole");
                        addRow (xml_header, data, false);
                        if (n2 != null) {
                            count++;
                            m_log.log(Level.INFO, "Processing " + hole_id + " (" + count +" of " + hole_count + " holes)");
                            Process (n2, hole_id);
                        }
                    closeNode();
                  }
            }
        }  while (result != AGS_ReaderLine.LineType.tableName);
        return count;
    }
    
    catch (Exception e) {
        m_log.log(Level.SEVERE, e.getMessage());
        return -1;
    }
}

protected Collection<String> convertHeaderXML(Collection header1, String tname1) {
 return  super.convertHeaderXML(header1, tname1, AGS_Dictionary.Lang.AGS);
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
                System.out.print(header.size());
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
        m_log.log(Level.SEVERE, e.getMessage());
        return -1;
    }
}
}

