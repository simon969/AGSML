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
import java.util.logging.Logger;
/**
 *
 * @author thomsonsj
 */
public class AGS_XML_Writer extends XML_Writer {
    private AGSLineReader m_lr;
  
    
public AGS_XML_Writer (BufferedWriter bw ){
 super (bw);
}
public AGS_XML_Writer(String fout) {
 super(fout);
}
public void setAGSDictionary(String xmlDictFile) {
   
 m_dic = new AGS_Dictionary (xmlDictFile);
 m_ds = new AGS_DataStructure (xmlDictFile,"");
 m_hs = new HashSet();
 m_dic.convert_n1("ags");
 m_dic.convert_n2("agsml");
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
public void AGS_to_XML(AGSLineReader lr){
    AGS_to_XML_Table(lr);
}
public Collection convertHeaderx(String t1, Collection header){
    List  c = new ArrayList<String>();
    String f1;
    String f2;
    m_dic.find_table1(t1);
        Iterator itr_header = header.iterator();
               while (itr_header.hasNext()){
             //     while (itr_header.hasNext()){
                         f1 = itr_header.next().toString();
                         m_dic.find_field1(f1);
                         f2 = m_dic.field2_name();
                         c.add(f2);
                         }
     return c;
}
public void setAGSLineReader(AGSLineReader lr1) {
     m_lr = lr1; 
}
public void processAGSML() {
      Node n1 =  m_ds.m_root;

      processAGSML(n1);
         
}
public void processAGSML(Node n1) {
  try {      
        
        if (m_folder.isEmpty()) {
     
         processAGSMLNode (n1,null);
     
     } else { 
     
        Collection data = null;
        Collection ags_header = null;
        Collection xml_header = null; 
        String hole_id;
        String Filename;
        
        AGSLineReader lr = new AGSLineReader(m_lr);
        int result = lr.find_tablename(lr.AGSLR_HoleTableName, false, true, false, false);
     
        do {
            result =  lr.ReadLine();
       
            if (result == lr.AGSLR_LineType_tableHeader) {
                ags_header = lr.get_headers();
                xml_header = Header_XChange(ags_header,lr.AGSLR_HoleTableName);
            }
     
            if (result == lr.AGSLR_LineType_rowData) {
                data = lr.get_data();
                hole_id = getData(xml_header, data, "holeId"); 
                
                if (IsHoleInList(hole_id) && ContainsSpecialCharacters(hole_id) == false) {
          
                    m_onlyHoleId = hole_id;
                    
                    Filename = m_folder + "\\" + hole_id + ".xml";
            
                    NewFile(Filename);
            
                    processAGSMLNode (n1, null);
            
                    close();
      
                    m_onlyHoleId = "";
                    m_log.info("File " + Filename + " created ");  
                }
             
            }
        } while (result == lr.intData()|| result == lr.intHeader() || result == lr.intUnits());
       }
  }
  catch (Exception e) {
      
  }   
}
public void AGS_to_AGSML(AGSLineReader lr1){
  try {      
        
        m_lr = lr1; 
        
        Node n1 =  m_ds.m_root;
        
        if (m_folder.isEmpty()) {
     
         processAGSMLNode (n1,null);
     
     } else { 
     
        Collection data = null;
        Collection ags_header = null;
        Collection xml_header = null; 
        String hole_id;
        String Filename;
        
        AGSLineReader lr = new AGSLineReader(m_lr);
        int result = lr.find_tablename(lr.AGSLR_HoleTableName, false, true, false, false);
     
        do {
            result =  lr.ReadLine();
       
            if (result == lr.AGSLR_LineType_tableHeader) {
                ags_header = lr.get_headers();
                xml_header = Header_XChange(ags_header,lr.AGSLR_HoleTableName);
            }
     
            if (result == lr.AGSLR_LineType_rowData) {
                data = lr.get_data();
                hole_id = getData(xml_header, data, "holeId"); 
                
                if (IsHoleInList(hole_id) && ContainsSpecialCharacters(hole_id) == false) {
          
                    m_onlyHoleId = hole_id;
                    
                    Filename = m_folder + "\\" + hole_id + ".xml";
            
                    NewFile(Filename);
            
                    processAGSMLNode (n1, null);
            
                    close();
      
                    m_onlyHoleId = "";
                    m_log.info("File " + Filename + " created ");  
                }
             
            }
        } while (result == lr1.intData()|| result == lr1.intHeader() || result == lr1.intUnits());
       }
  }
  catch (Exception e) {
      
  }
}


protected int addTable(String xml_tname, String xml_fname, String fvalue){
Collection data = null;
Collection ags_header = null;
Collection xml_header = null;
int result = 0;
int row_count = 0;
String s1;
String ags_fname= null;
String ags_tname=null;
AGSLineReader lr1;

lr1 = new AGSLineReader(m_lr);
m_dic.find_table2(xml_tname);

ags_tname = m_dic.table1_name();

if (xml_fname!=null){
m_dic.find_field2(xml_fname);
ags_fname = m_dic.field1_name();
}

if (ags_tname == null) {
    return 0;
} 

if (lr1.find_tablename(ags_tname, false, true, false, false) >= 1){
        do {
        result = lr1.ReadLine();
            if (result == lr1.intHeader()) {
                ags_header = lr1.get_headers();
                xml_header = Header_XChange( ags_header, ags_tname);
            }
            if (result == lr1.intUnits()){

            }
            if (result == lr1.intData()){
                data = lr1.get_data();
                if (ags_fname == null) {
                        openNode (xml_tname);
                        addRow(xml_header, data, false);
                        closeNode();
                        row_count++;

                } else {
                    s1 = getData(ags_header, data, ags_fname);
                    if (fvalue.equals(s1)){
                        openNode (xml_tname);
                        addRow(xml_header, data, false);
                        closeNode();
                        row_count++;
                    }

                }

            }
        } while (result == lr1.intData()|| result == lr1.intHeader() || result == lr1.intUnits());
    }
m_log.info(String.valueOf(row_count) + " rows table " + xml_tname + " " + xml_fname + " " + fvalue );
return row_count;
}
protected Node addHoles(Node n1) {

if (!n1.getNodeName().equals("holes")) {
    return n1;
}

int result;
int count = 0;
boolean holeInList;
Collection ags_header = null;
Collection data = null;
Collection xml_header = null;
String hole_id = null;
Node n2 = null;
AGSLineReader lr = new AGSLineReader(m_lr);

result = lr.find_tablename(lr.AGSLR_HoleTableName, false, true, false, false);

if (result <= 0) {
    return n1;
}

n1 = m_ds.findSubNode(n1, "Hole");

if (n1.hasChildNodes() == true)  { 
    NodeList list = n1.getChildNodes();
    n2 = list.item(1);
    }

do {
    result =  lr.ReadLine();
    if (result == lr.intHeader()) {
    ags_header = lr.get_headers();
    xml_header = Header_XChange(ags_header,lr.AGSLR_HoleTableName);
    }
    if (result == lr.intData()) {
    data = lr.get_data();
    hole_id = getData(xml_header, data, "holeId");
    holeInList = IsHoleInList(hole_id);
         if (holeInList) {
               openNode ("Hole");
                addRow (xml_header, data, false);
                count++;
                if (n2 != null) {
                    processAGSMLNode (n2, hole_id);
                }
            closeNode();
          }
    }
}  while (result != lr.intTableName());

return n2;
}


public void AGS_to_XML_Nest(AGSLineReader lr1){
      String table_name = "";
      Collection header = null;
      Collection data = null;
      String hole_id = null;
      boolean data_found = false;
      int nested_data_count = 0;
      int result;
      AGSLineReader lr2;
      do {
              result = lr1.ReadLine();

              if (result == lr1.intTableName()) {

                  table_name = lr1.get_tablename(true,true,true,true);
                    
                  if (table_name.equalsIgnoreCase("proj")){
                    do {
                         result = lr1.ReadLine();
                          if (result == lr1.intHeader()) {
                            header = lr1.get_headers(true,true,true,true);
                            openElement("proj");
                          }
                          if (result == lr1.intData()) {
                          data = lr1.get_data();
                          addData(header, data);
                          data_found = true;
                        } 
                    } while (data_found == false);
                  }

                  if (table_name.equalsIgnoreCase("hole")) {
                      openElement ("holes");
                        do {
                            result = lr1.ReadLine();
                                if (result == lr1.intTableName()) {
                                table_name = lr1.get_tablename(true,true,true,true);
                                }
                                if (result == lr1.intHeader()) {
                                header = lr1.get_headers(true,true,true,true);
                                }

                                if (result == lr1.intData()) {
                                    openElement("hole");
                                    data = lr1.get_data();
                                    addData(header, data);
                                    hole_id = getData(header,data,"hole_id");
                                    lr2 = new AGSLineReader(lr1);
                                        System.out.print("Finding nested data for " + hole_id);
                                        nested_data_count = AddNestedTables(lr2, "hole","hole_id", hole_id);
                                        System.out.print(" " + nested_data_count + " found \n");
                                    closeElement("hole");
                                }
                            } while (table_name.equalsIgnoreCase("hole"));
                    closeElement("holes");  
                   }
                }
      } while (result != lr1.intError()); 
      if (data_found==true) {
      closeElement("proj");
    }
}

private int AddNestedTables(AGSLineReader lr1, String parent_table, String q_header, String q_data ) {
      String table_name = "";
      Collection header = null;
      Collection data = null;
      int data_count = 0;
      int result;
       do {
             result = lr1.ReadLine();

                if (result == lr1.intTableName()) {
                table_name = lr1.get_tablename(true, true, true, true);
                }
                            
                if (result == lr1.intHeader()) {
                header = lr1.get_headers(true,true,true,true);
                }
                    
                 
                if (result == lr1.intData() ) {
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
           } while (result != lr1.intError());
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
public void AGS_to_XML_Table(AGSLineReader lr){
     
      String table_name = "";
      String table_name_plural = "";
      String proj_name = "";
      Collection header = null;
      Collection data = null;
      
      
       int result;
       do {
              result = lr.ReadLine();

              if (result == lr.intTableName()) {
                  table_name = lr.get_tablename(true,true,true,true);

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
              
              if (result == lr.intHeader()) {
                 header = lr.get_headers(true,true,true,true);
               }

              if (result == lr.intUnits()) {
                 
              }
              if (result == lr.intData()) {

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
          } while (result != lr.intError());
       closeElement(table_name_plural);
       closeElement(proj_name);
}
   public class cColumn {
     String m_Header;
     String m_Data;
     String m_Description;
     String m_Example;
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
private void addRow(Collection header, Collection data, Boolean AddEmptyData) {
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
}
   }
