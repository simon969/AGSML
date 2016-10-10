/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agsml;
// import sun.jdbc.odbc.JdbcOdbc;
import java.io.BufferedWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection ;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 *
 * @author thomsonsj
 */
public class SQL_XML_Writer extends XML_Writer {

    protected AGSSQLReader m_db;
    protected final String SQLXML_SELECT = "select";
    protected final String SQLXML_WHERE = "where";
    protected final String SQLXML_FROM = "from";
    protected final String SQLXML_QUERY = "query";
    protected final String SQLXML_SPACE = " ";
    protected final String SQLXML_END = ";";
    protected final String SQLXML_DICTIONARY_NODE = "dictionary";
    protected final String SQLXML_OUTPUT_NAME = "output_name";
    protected final String SQLXML_OUTPUT_ROWNAME = "output_rowname";
    protected final String SQLXML_OUTPUT_ID = "output_id";
    
public SQL_XML_Writer(String fout) {
 super(fout);
}

 public SQL_XML_Writer (BufferedWriter bw ) {
 super (bw);
try {
  
     }
 catch (Exception e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an Exception error!");
        e.printStackTrace();
 }

}
 public void setAGSDictionaryFile(String xmlDictFile, String s1) {
 
     super.setAGSDictionaryFile(xmlDictFile, s1);  
 
     m_dic.convert_n1("sql");
     m_dic.convert_n2("agsml");
 
 }
 public void setAGSSQLReader(AGSSQLReader sr ) {
     m_db = sr; 
 }
 
 public void processAGSML() {
      Node n1 =  m_ds.RootNode();
      processAGSML ( n1);
 }
 @Override
 public void processAGSML(Node n1) {
 try {
     
     if (m_folder.isEmpty()) {
     
         processAGSMLNode (n1,null);
     
     } else {
     
     String sql_tname = m_db.AGSSQL_PointTableName;
     String sql_fname = m_db.AGSSQL_PointIdFieldName;
     
     String PointId;
     String filename;
     String holename;
     
     ResultSet rs = m_db.get_table(sql_tname);
     
     rs.first();
     
     do {
     
     PointId = getData(rs,  sql_fname);    
     
     holename = removeSpecialCharacters(PointId); 
     
     if (IsHoleInList(PointId) && ContainsSpecialCharacters(holename) == false) {
         
         m_onlyHoleId = PointId;
            
            filename = m_folder + "\\" + holename + ".xml";
            
            NewFile(filename);
            
            processAGSMLNode (n1, null);
            
            close();
      
         m_onlyHoleId = "";
         m_log.info("File " + filename + " created ");  
     }
     } while (rs.next());
     
     rs.close();
     
     }   
  }
 
 catch (SQLException e) {
     m_log.log(Level.SEVERE, e.getMessage());
 }   
}

 
 public void SQL_AGSML(AGSSQLReader sr )  {
  
 try {
     
 
     m_db = sr; 
    
     Node n1 =  m_ds.RootNode();
    
     if (m_folder.isEmpty()) {
     
         processAGSMLNode (n1,null);
     
     } else {
     
     String sql_tname = m_db.AGSSQL_PointTableName;
     String sql_fname = m_db.AGSSQL_PointIdFieldName;
     
     String PointId;
     String filename;
     String holename;
     
     ResultSet rs = m_db.get_table(sql_tname);
     
     rs.first();
     
     do {
     
     PointId = getData(rs,  sql_fname);    
     
     holename = removeSpecialCharacters(PointId); 
     
     if (IsHoleInList(PointId) && ContainsSpecialCharacters(holename) == false) {
         
         m_onlyHoleId = PointId;
            
            filename = m_folder + "\\" + holename + ".xml";
            
            NewFile(filename);
            
            processAGSMLNode (n1, null);
            
            close();
      
         m_onlyHoleId = "";
         m_log.info("File " + filename + " created ");  
     }
     } while (rs.next());
     
     rs.close();
     
     }   
  }
 
 catch (SQLException e) {
     m_log.log(Level.SEVERE, e.getMessage());
 }
 }
     
 
 protected int addTable(String xml_tname, String xml_fname, String fvalue){
     
 try {
  
    Collection sql_header = null;
    Collection xml_header = null;
   
    int row_count = 0;
   
    String sql_tname = "";
    String sql_fname = "";
  
    ResultSet rs;
    
    if (xml_tname.length() > 0) {
        m_dic.find_table2(xml_tname);
        sql_tname = m_dic.table1_name(); 
        if (sql_tname == null) {
        return 0;
        } 
    } else return 0;
         
      
    if (xml_fname != null) {
        if (xml_fname.length()>0) {
          m_dic.find_field2(xml_fname); 
          sql_fname = m_dic.field1_name();
        }
    }
    
    if (m_db.find_tablename(sql_tname) >= 1) {
    
        rs = m_db.get_table(sql_tname, sql_fname, fvalue );
        if (rs !=null) {
            if (rs.first()) {
                sql_header = m_db.get_headers(rs);
                xml_header = Header_XChange(sql_header, sql_tname); 
                // openNode (xml_tname);
                    do {
                        openNode (xml_tname);
                            addRow(xml_header, rs, true);
                        closeNode();
                        row_count++;
                    } while (rs.next());
                // closeNode();
            } 
            
         rs.close();
        }
        
    }
    
    if (row_count > 0) {
    m_log.log(Level.INFO,"["+ fvalue + "]["+ xml_tname+ "][" + String.valueOf(row_count) +"]") ;
    }
    
   
            
    return row_count;
    
 }
 
 catch (SQLException e) {
    m_log.log(Level.SEVERE, e.getMessage());
     return -1;
 }
 }
 protected String getData(ResultSet rs, String field) {
 try {
      String attName;
      String attValue;
      if (rs != null) {
      ResultSetMetaData rsmd = rs.getMetaData();
      
      for (int i = 1; i < rsmd.getColumnCount(); i++) {
                         attName = rsmd.getColumnName(i);
                         attValue = rs.getString(i);
                         if (field.equalsIgnoreCase(attName)) {
                             return attValue;
                             }
      }
      }
      return null;
    }
 catch (SQLException e) {
     return null;
 }
 }
 protected int addRow(Collection header, ResultSet rs, boolean AddEmptyData) {
      
    try {
           Iterator h_it;
           String h1;
           String d1;
           int colid=0;
           h_it = header.iterator();
  
           do {
               colid++; 
               h1 = h_it.next().toString();
               d1 = rs.getString(colid);
               if (d1 == null) d1= "";
                if (!(d1.length() == 0 && AddEmptyData == false)){
                    openNode(h1);
                        addValid (d1);
                    closeNode();
                }
            } while(h_it.hasNext());
          
    return 1;        
    }
    catch (SQLException e) {
    return -1;
    }
 }
 
 
 protected int addHole(Node n1, String hole_id) {
     
     return -1;
  }
protected Node addHoles(Node n1) {

   try   {
         
    String s1 = n1.getNodeName();
    
    if (!s1.equals("holes") && !s1.equals("Hole")) {
    return n1;
    }
    
    if (!s1.equals("Hole")) {
    n1 = m_ds.findSubNode(n1, "Hole");
    }
    
    int result;
    int count = 0;
    boolean holeInList;
    Collection sql_header = null;
    Collection xml_header = null;

    String hole_id = null;
    Node n2 = null;

  
    result = m_db.find_tablename(m_db.AGSSQL_PointTableName);

    if (result <= 0) {
    return n1;
    }

    ResultSet  rs = m_db.get_table(m_db.AGSSQL_PointTableName);
  
    if (n1.hasChildNodes() == true)  { 
        NodeList list = n1.getChildNodes();
        n2 = list.item(1);
    } else {
        n2 = n1;
    }

    if (rs.first()) {
     sql_header = m_db.get_headers(rs);
     xml_header = Header_XChange(sql_header, m_db.AGSSQL_PointTableName); 
        do {
            hole_id = getData(rs, m_db.AGSSQL_PointIdFieldName);  
            holeInList = IsHoleInList(hole_id);
                if (holeInList) {
                    openNode ("Hole");
                    addRow (xml_header, rs, false);
                    count++;
                        if (n2 != null) {
                            n2 = processAGSMLNode (n2, hole_id);
                        }
                    closeNode();
                m_log.info("Found " + hole_id + " in list ");
              }
           } while (rs.next());
    }
    
    rs.close();
    
return n2;  
}

catch (SQLException e) {
   m_log.log(Level.SEVERE, e.getMessage()); 
   return n1;    
}
}
protected String getDictionaryTable(Node n1) {
    Element e1 = (Element) n1;
    return e1.getAttribute(SQLXML_DICTIONARY_NODE);
}
protected String getOutputTableName(Node n1) {
    Element e1 = (Element) n1;
    return e1.getAttribute(SQLXML_OUTPUT_NAME);
}
protected String getOutputId(Node n1) {
    Element e1 = (Element) n1;
    return e1.getAttribute(SQLXML_OUTPUT_ID);
}
protected String getOutputRowName(Node n1) {
    Element e1 = (Element) n1;
    return e1.getAttribute(SQLXML_OUTPUT_ROWNAME);
}
protected String getQuery (Node n1) {
    
Element e1 = (Element) n1;
StringBuilder  sb1 = new StringBuilder();

String select = e1.getAttribute(SQLXML_SELECT);
String from = e1.getAttribute(SQLXML_FROM);
String where = e1.getAttribute(SQLXML_WHERE);
String query = e1.getAttribute(SQLXML_QUERY);;

sb1.append(SQLXML_SELECT);
    sb1.append(SQLXML_SPACE);
        sb1.append(select);
            sb1.append(SQLXML_SPACE);
sb1.append(SQLXML_FROM);
    sb1.append(SQLXML_SPACE);
        sb1.append(from);
            sb1.append(SQLXML_SPACE);
sb1.append(SQLXML_WHERE);
    sb1.append(SQLXML_SPACE);
        sb1.append(where);
            sb1.append(SQLXML_END); 
            
if (query.isEmpty()) {
    return sb1.toString();
} else {
    return query;  
  }
    
}


protected Node processQueryNode(Node n1) {
    try {
        
        Collection sql_header = null;
        Collection xml_header = null;
        int row_count = 0;
        
        String s1 = getQuery (n1) ;
        String s2 = getDictionaryTable(n1);
        String s3 = getOutputTableName(n1);
        String s5 = getOutputRowName(n1);
        String s4 = getOutputId(n1);
        
        
        ResultSet rs = m_db.get_Query(s1);
        
        if (rs !=null) {
       
        sql_header = m_db.get_headers(rs);
        m_dic.find_table2(s2);
        xml_header = Header_XChange(sql_header,s2);
               
            if (rs.first()) {
                sql_header = m_db.get_headers(rs);
                xml_header = Header_XChange(sql_header, s2); 
                addNodeAttrib("Id=", s4);
                openNode (s3);
                    do {
                        openNode (s5);
                            addRow(xml_header, rs, true);
                        closeNode();
                        row_count++;
                    } while (rs.next());
                closeNode();
            } 
            
         rs.close();
        }
        
     return n1;
    } catch (Exception e) {
      return null;
    }
}
}
 

 


