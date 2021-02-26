/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agsml;
// import sun.jdbc.odbc.JdbcOdbc;
import static agsml.Base_Class.getBoolean;
import java.io.BufferedWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection ;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.lang.StringBuilder;
import java.lang.Math;
import org.w3c.dom.NamedNodeMap;

/**
 *
 * @author thomsonsj
 */
class SQL_XML_Writer extends  XML_Writer implements Converter{

    protected SQL_Base m_db;
    protected Constants.AGSVersion ags_version = Constants.AGSVersion.AGS31a;
             
    public SQL_XML_Writer() {
    super();
    }
    public SQL_XML_Writer(String fileout, String stylesheet, String fieldout) {
    super(fileout, stylesheet);    
    
    }
    public SQL_XML_Writer(String fout, String stylesheet) {
    super(fout, stylesheet);    
    }
    
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

 
 public void setDictionary(String xmlDictFile, String s1) {
     super.setAGSDictionary(xmlDictFile, s1);  
 }
 public int getDictionary()  {
    super.getAGSDictionary();
    return 1;
 }
 
 public void setAGSSQLReader(SQL_Base sr ) {
     m_db = sr; 
 }
 public int getAGSReader() {
    try {
        String s1 = getProperty(XML_Writer.AGS_DATASOURCE);
        log.log(Level.INFO, "Connecting to " + s1 + "....");
        m_db = new SQL_Base(s1);
        
        if (fieldOut().length() > 0) {
        m_db.setOutputAGSMLField(fieldOut());    
        }
     
        if (projectId()>0) {
        m_db.setgINTProjectID(projectId());
        } 
        
        log.log(Level.INFO, "Connection made to " + s1);
        return 1;
    }
    catch (Exception e) {
        log.log(Level.SEVERE, "Problem connecting " );
        log.log(Level.SEVERE, e.getMessage()); 
       return -1;
    }
 }
 
//  if (n1.getNodeType() == Node.ELEMENT_NODE) {
//        String s1 = n1.getNodeName(); 
//        if(s1.equals (m_ds.DataStructureAGSML))
//            retvar = true;
//        } else {
//            retvar = false;
//  }
//   return retvar;
//     
 

  
 @Override public  void Process() {
     
    try { 
        
        Node n1 = m_ds.RootNode();
        Process(n1);
         
    }
    
    catch (Exception e) {
       log.log(Level.SEVERE, e.getMessage());   
    }
}

 public void  Process(Node n1) {
     
 try {
        String s1 = n1.toString();
        boolean NodeProcessed = false;
        
        if (folderOut().isEmpty() && fileOut().isEmpty()) {
         Process (n1,null,null,null);
         NodeProcessed = true;
        }
     
        if (folderOut().isEmpty() && !fileOut().isEmpty() ) {
            openFile(true, false);
            Process (n1,null,null,null);
            closeFile();
            NodeProcessed = true;
        }
     
        if (!folderOut().isEmpty() && fileOut().isEmpty()) {

            String sql_tname = m_db.AGSSQL_PointTableName;
            String sql_fname = m_db.AGSSQL_PointIdFieldName;

            String PointId;
            String holename;

            ResultSet rs = m_db.get_table(sql_tname);

            rs.first();

            do {

            PointId = getData(rs,  sql_fname);   

            String s2 = getData(rs, m_db.gINTProjectIdField);

            if (s2 != null) {
                if (s2.length()>0) {
                    setProjectId(Integer.parseInt( s2));
                }
            } else {
                setProjectId(0);
            }


            if (projectId()>0) {
            m_db.setgINTProjectID(projectId());
            }        

            holename = removeSpecialCharacters(PointId); 


            if (IsHoleInList(PointId)) {

                   m_onlyHoleId = PointId;

                   setOutputFile ( folderOut() + "\\" + holename + ".xml");

                   openFile(true, false);
                   Process (n1, null,null,null);

                   if (fieldOut().length() > 0) {
                   // Write the xml string to the defined field'
                   String s3 = super.toString();
                   m_db.updateAGSMLField(PointId,  s3);
                   }
                   closeFile();

                m_onlyHoleId = "";

            }
            } while (rs.next());

            rs.close();
            NodeProcessed = true;
     
        }
     
     if (NodeProcessed == false) {
     throw new Exception (this.fileOut() + " and " + this.folderOut() + " not clear if single or multiple files are required for output");
     }
  }
 
 catch (SQLException e) {
     log.log(Level.SEVERE, e.getMessage());
 }   
 catch (Exception e) {
     log.log(Level.SEVERE, e.getMessage());
 }   
}
protected Node Process(Node n1, String hole_id, Double depth_top, Double depth_base){
 try {
      
    do {
           if (n1.getNodeType() == Node.ELEMENT_NODE) {
               
                String s1 = n1.getNodeName();
                String ags_table = "";
                String ntype = "";
                boolean IsAGSTable;
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
                                ags_version = m_db.getAGSVersion("");
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
                
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_PROJ_NODE) && hole_id == null) {
                    openNode(s1);
                    addTable (ags_table, false);
                    if (n1.hasChildNodes() == true) {
                        Node n2 = n1.getFirstChild();
                        Process(n2, null, null, null);
                    }
                    closeNode();
                    ChildNodesProcessed = true;
                }
                
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_PROJ_NODE) && hole_id != null) {
                    addTable (ags_table, true);
                }
                
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_ABBR_NODE)) {
                    addTable (ags_table, true);
                }
                
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_UNIT_NODE)) {
                    addTable (ags_table, true);
                }
                
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_HOLE_NODE) && hole_id == null && ForEach == true) {
                    addHoles(n1);
                    ChildNodesProcessed = true;
                }
                
                if (ags_table.equals(Constants.AGS_DATATSTRUCTURE_GEOL_NODE) && hole_id != null && ForEach == true) {
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
                   // log.log(Level.INFO ,"["+ hole_id + "][" + s1 + "][" + depth_top + "-" + depth_base + "]") ;  
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
protected int addGeols(Node n1, String hole_id) {

   Double geol_depthTOP = null;
   Double geol_depthBASE = null;
   
   String xml_tname = "Geol";
   
   String sql_fieldTOP  = "";
   String sql_fieldBASE = "";
   
   String ags_tname = "";
   String sql_tname = "";
   String ags_fields = "";
   
   String sql_select = "";
   String sql_where = "";
   
   Node n2 = null;
   
   
   try {
       
   
        if (n1.hasChildNodes() == true)  { 
                NodeList list = n1.getChildNodes();
                n2 = list.item(1);
        }
        if (m_dic.find_tableXML(xml_tname) !=  null) {
                sql_tname = m_dic.tableGINT_name();
                ags_tname = m_dic.tableAGS_name();
        }

        if (m_dic.find_fieldAGSContains("TOP")!=null) {
                sql_fieldTOP = m_dic.fieldGINT_name();
        }
        if (m_dic.find_fieldAGSContains("BASE")!=null) {
                sql_fieldBASE = m_dic.fieldGINT_name();
        }

        if (AGSFieldsOnly()) {
             ags_fields = m_dic.get_GINTselect(ags_tname, agsml.Constants.Lang.AGS,true);
        } else {
             ags_fields = "*";
        }

        sql_select = "select " + ags_fields + " from " + sql_tname;
        sql_where =  m_db.AGSSQL_PointIdFieldName + "='" + hole_id + "'";

        ResultSet rs = m_db.get_table(sql_select, sql_where);

        Header h = convertXMLHeader(rs, sql_tname, agsml.Constants.Lang.GINT, AGSFieldsOnly() );
        
        if (rs !=null) {
            if (rs.first()) {
               do {
                geol_depthTOP = getData_double(rs,sql_fieldTOP);
                geol_depthBASE = getData_double(rs,sql_fieldBASE);
                openNode ("Geol");
                        addRow(h, rs, true);
                        if (n2 != null) {
                            log.log(Level.INFO, "Processing " + hole_id + " depth(" + geol_depthTOP + "-" + geol_depthBASE + ")");
                            Process (n2, hole_id,  geol_depthTOP, geol_depthBASE);
                        }
                closeNode();
               } while (rs.next());
        rs.close();
        }
        }
   }
        
     
    catch (SQLException e) {
        log.log(Level.SEVERE, e.getMessage());
     return -1;
    }
    
   catch (Exception e) {
    log.log(Level.SEVERE, e.getMessage());
   return -1;
    } 
   
return 1;
}
protected int addTable (String xml_tname, boolean OpenCloseTags) {
    return addTable (xml_tname, null, null, null,null, OpenCloseTags);
}
protected int addTable (String xml_tname, String xml_fname, String fvalue, boolean OpenCloseTags) {
    return addTable (xml_tname, xml_fname, fvalue, null,null, OpenCloseTags);
}
protected int addTable(String xml_tname, String xml_fname, String fvalue, Double depthTOP, Double depthBASE, boolean OpenCloseTags) {
     
 try {

    int row_count = 0;
   
    String ags_tname = "";
    String sql_tname = "";
    String sql_fname = "";
    String sql_select = "";
    String sql_where = "";
    String ags_fields = "";

    
    if (xml_tname != null) {
        if (xml_tname.length() > 0) {
            if (m_dic.find_tableXML(xml_tname) !=  null) {
                ags_tname = m_dic.tableAGS_name();
                sql_tname = m_dic.tableGINT_name();
            }
        if (ags_tname == null) {
        return 0;
        }
        }
        else return 0;
    } else return 0;
         
     
    if (AGSFieldsOnly()) {
         ags_fields = m_dic.get_GINTselect(ags_tname, agsml.Constants.Lang.AGS,true);
    } else {
         ags_fields = "*";
    }
    
    sql_select = "select " + ags_fields + " from " + sql_tname;
    
    
    if (xml_fname != null) {
        if (xml_fname.length()> 0) {
                if (m_dic.find_fieldXML(xml_fname)!=null) {
                    sql_fname = m_dic.fieldGINT_name();
                }
        sql_where = sql_fname + "='"  + fvalue + "'";   
        }
    } 
    
    if (depthTOP != null && depthBASE !=null) {
        if (m_dic.find_fieldDepthTOP()!=null) {
            String fieldTOP = m_dic.fieldGINT_name();
            sql_where += " AND " + fieldTOP + ">=" + depthTOP.toString() + " AND " + fieldTOP + "<=" + depthBASE.toString();  
        } else {
            log.info( sql_tname + " [" + fvalue + "][" + depthTOP + "-"  + depthBASE + "] depthTOP and depthBASE provided and no fieldDepthTOP found");
            return 0;
        }
    } 
    
    if (m_db.find_tablename(sql_tname) >= 1) {
       
        ResultSet rs = m_db.get_table(sql_select, sql_where);
        
        Header h = convertXMLHeader(rs, sql_tname, agsml.Constants.Lang.GINT, AGSFieldsOnly() );
     
        if (rs !=null) {
            if (rs.first()) {
                
                     do {
                       if (OpenCloseTags) openNode (xml_tname);
                            addRow(h, rs, true);
                       if (OpenCloseTags) closeNode();
                        row_count++;
                    } while (rs.next());
            } 
            
        rs.close();
        }
        
    }
    
    if (row_count > 0) {
    log.log(Level.INFO,"["+ fvalue + "]["+ xml_tname+ "][" + String.valueOf(row_count) +"]") ;
    }
    
   
            
    return row_count;
    
 }
 
 
 catch (SQLException e) {
    log.log(Level.SEVERE, e.getMessage());
     return -1;
 }
  catch (Exception e) {
    log.log(Level.SEVERE, e.getMessage());
     return -1;
 }
 }
 protected int getData_int(ResultSet rs, String field) {
     return Integer.parseInt(getData(rs, field));
 }
 
  protected float getData_float(ResultSet rs, String field) {
     return Float.parseFloat(getData(rs, field));
 }
 protected Double getData_double(ResultSet rs, String field) {
     return Double.parseDouble(getData(rs, field));
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
 protected Header convertXMLHeader(ResultSet rs, String tname1, agsml.Constants.Lang lang) {
      return convertXMLHeader(rs,tname1, lang, AGSFieldsOnly());
 }
 protected Header convertXMLHeader(ResultSet rs, String tname1, agsml.Constants.Lang lang, boolean boolAGSFieldsOnly) {
 try {
     
        Header r = new Header();
        ResultSetMetaData rsmd = rs.getMetaData();
        
        String tname2;
        String fname1;
        String fname2;
        
        String ftype;
        
        boolean TableFound = false;
        boolean FieldFound = false;
        
        TableFound = (m_dic.find_table(tname1, lang) != null );
        
        if (TableFound) {
            
            tname2 = m_dic.tableXML_name();
            r.set_tableOUT(tname2);
            r.set_tableIN(tname1);  
        
            splitBinary64Fields();
        
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                
                fname1 = rsmd.getColumnName(i);
                fname2 = "";
                ftype = rsmd.getColumnTypeName(i);
            
                    if (IsBinary64Field(tname1+ '.' + fname1)) {
                    ftype = "BINARY";
                    fname2 = fname1;
                    }
                    
                    FieldFound = (m_dic.find_field(fname1, lang) != null);
                    
                    if (FieldFound) {
                        fname2 = m_dic.fieldXML_name();
                    } else {
                        if (boolAGSFieldsOnly == false && ftype != "BINARY") {
                        fname2 = m_dic.format_XMLTry(tname2, fname1);
                        }
                    }
            r.AddColumn(fname1, fname2, ftype, "",  "", i);
            } 
        }
        return r;
 }
 catch (Exception e) {
//     log(Level.SEVERE, e.getMessage());      
     return null;
 }
}

 protected Header convertSQLHeader(ResultSet rs, String tname1, agsml.Constants.Lang Lang) {
 try {
        Header r = new Header();    
      
        boolean TableFound = false;
        boolean FieldFound = false;
        
        
        ResultSetMetaData rsmd = rs.getMetaData();
        
        TableFound = (m_dic.find_table(tname1, Lang) != null );
                
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
         
         String fname1 = rsmd.getColumnName(i);
         String ftype1 = rsmd.getColumnTypeName(i);
         String fname2 = "";
         String ftype2 = "NONE";
         
            if (TableFound) {
                FieldFound = (m_dic.find_field(fname1, Lang)!=null);
                    if (FieldFound) {
                        fname2 = m_dic.fieldGINT_name();
                        ftype2 = ftype1;
                    } 
            }
        r.AddColumn(fname1, fname2,  ftype2 ,"","", i );
        }
        return r;
        
 }
 catch (Exception e) {
//     log(Level.SEVERE, e.getMessage());      
     return null;
 }  
 }

 protected int addRow(Header r, ResultSet rs,  boolean AddEmptyData) {
      
    try {
              
           
          for (int i = 0; i < r.columnINCollection().size(); i++) {  
              
               SQL_Base.SQLDataType type = m_db.getSQLDataType(r.get_Type(i));
               String h_out  = r.get_ColumnOut(i);
               String h_in = r.get_ColumnIn(i);
               
               int id = r.get_Id(i);
               if (h_out.isEmpty()== false) {
               
                        if (type == SQL_Base.SQLDataType.BINARY) {
                             byte[] b1 = rs.getBytes(id);
                             if (b1 != null) { 
                             add64bit(h_out, b1 );
                             }
                        }    

                        if (type != SQL_Base.SQLDataType.BINARY) {                  
                             String s1 = rs.getString(id);
                             if (s1 == null) s1 = "";
                              if (!(s1.length() == 0 && AddEmptyData == false)){
                                  openNode(h_out);
                                      addValid (s1);
                                  closeNode();
                                  if (h_out.equals(this.OutputFileNameFromField()) && agsml.Constants.SQLXML_ADDHOLEFILENAME == true) {
                                       String holefileName = removeSpecialCharacters(s1) + ".xml";
                                       openNode (agsml.Constants.SQLXML_HOLEFILETAGNAME);
                                       addValid (holefileName);
                                       closeNode();
                                  }
                              }
                        }
                }
            }
          
    return 1;        
    }
    catch (SQLException e) {
    return -1;
    }
 }
 
 
 protected int addHole(Node n1, String hole_id) {
     
     return -1;
  }
protected int  addHoles(Node n1) {

   try   {
         
    int result;
    int count = 0;
    boolean holeInList;


    String hole_id = null;
    Node n2 = null;
    
    String sql_select = "";
    String ags_fields = "";
    String sql_where = "";
    String hole_list = "";
    String ags_table = "";
     
    result = m_db.find_tablename(m_db.AGSSQL_PointTableName);

    if (result <= 0) {
    return -1;
    }
    if (AGSFieldsOnly())  {
        if (m_dic.find_tableGINT(m_db.AGSSQL_PointTableName)!= null) {
            ags_table = m_dic.tableAGS_name();
                if (ags_table.length()>0 ) {
                ags_fields = m_dic.get_GINTselect(ags_table,agsml.Constants.Lang.AGS,true);
                }
        }
       
    } else {
           ags_fields = "*";
          }
          
    sql_select = "select " + ags_fields + " from " +  m_db.AGSSQL_PointTableName;
    hole_list  = getHoleList(",");
    
    if (hole_list.length() > 0) {
    sql_where = m_db.AGSSQL_PointIdFieldName + " in (" + hole_list + ")";
    }        
      
    ResultSet  rs = m_db.get_table(sql_select, sql_where);
    Header r = convertXMLHeader(rs, m_db.AGSSQL_PointTableName, agsml.Constants.Lang.GINT); 
    
    if (n1.hasChildNodes() == true)  { 
        NodeList list = n1.getChildNodes();
        n2 = list.item(0);
    } else {
        n2 = n1;
    }

    if (rs.first()) {
     
        do {
            hole_id = getData(rs, m_db.AGSSQL_PointIdFieldName);  
            holeInList = IsHoleInList(hole_id);
                if (holeInList) {
                    addNodeAttrib("id",hole_id);
                    openNode ("Hole");
                    addRow (r, rs, false);
                    count++;
                        if (n2 != null) {
                           Process (n2, hole_id, null, null);
                        }
                    closeNode();
               log.info("Found " + hole_id + " in list ");
              }
           } while (rs.next());
    }
    
    rs.close();
    
return count;  
}

catch (SQLException e) {
   log.log(Level.SEVERE, e.getMessage()); 
   return -1;    
}
}

  protected String getDictionaryTable(Node n1) {
    return getSubNodeValue(n1, agsml.Constants.SQLXML_DICTIONARY_TABLE);
}
protected String getOutputTableName(Node n1) {
    return getSubNodeValue(n1,agsml.Constants.SQLXML_TABLE_TAG);
}
protected String getOutputRowName(Node n1) {
    String s1 = getSubNodeValue(n1,agsml.Constants.SQLXML_ROW_TAG);
    if (s1.isEmpty()) {
               s1 = getAttributeValue(n1, agsml.Constants.SQLXML_ROW_TAG);
    }
    if (s1.isEmpty()) {
        s1 = "row";
    }
    return s1;
}
protected String getOutputTableID(Node n1) {
    String s1 = getAttributeValue(n1,agsml.Constants.SQLXML_TABLE_ID);
    if (s1.isEmpty()) {
        s1 = getSubNodeValue(n1,agsml.Constants.SQLXML_TABLE_ID); 
        } 
    if (s1.isEmpty()) {
        s1 = getAttributeValue(n1,"id"); 
        } 
    return s1;
}
protected String getSelect(Node n1) {
     return getSubNodeValue(n1,agsml.Constants.SQLXML_SELECT);
}
protected String getFrom(Node n1) {
     return getSubNodeValue(n1,agsml.Constants.SQLXML_FROM);
}
protected String getWhere(Node n1) {
     return getSubNodeValue(n1,agsml.Constants.SQLXML_WHERE);
}
protected String getExplicitQuery(Node n1) {
     return getSubNodeValue(n1,agsml.Constants.SQLXML_QUERY);
}
protected String getQuery (Node n1) {
try {

    StringBuilder  sb1 = new StringBuilder();

    String select = getSelect (n1);
    String from = getFrom(n1);
    String where = getWhere(n1);
    String query = getExplicitQuery(n1);
    String query1 =  n1.getTextContent();
    
if (select.isEmpty()!=true) {
    sb1.append(agsml.Constants.SQLXML_SELECT);
        sb1.append(agsml.Constants.SQLXML_SINGLESPACE);
            sb1.append(select);
                sb1.append(agsml.Constants.SQLXML_SINGLESPACE);
    sb1.append(agsml.Constants.SQLXML_FROM);
    sb1.append(agsml.Constants.SQLXML_SINGLESPACE);
        sb1.append(from);
        
    if (!where.isEmpty()) {       
            sb1.append(agsml.Constants.SQLXML_SINGLESPACE);
    sb1.append(agsml.Constants.SQLXML_WHERE);
        sb1.append(agsml.Constants.SQLXML_SINGLESPACE);
            sb1.append(where);
                sb1.append(agsml.Constants.SQLXML_END); 
    } else {
        sb1.append(agsml.Constants.SQLXML_END); 
    }
return sb1.toString();
}

if (query.isEmpty()) {
    if (sb1.length()==0) {
        return query1;
    }
    else { 
        return sb1.toString();
    }
} else {
    return query;  
  }
} 
catch (Exception e) {
    log.log(Level.SEVERE,e.getMessage());
  return null;  
}    
}
private String getCoordinatePair(ResultSet rs, String fLatitude, String fLongitude){
   try {
       return rs.getString(fLatitude) + "," +  rs.getString(fLongitude);
                
} catch (Exception e) {
        return "";
}
}
private String getCoordinateConvertPair(ResultSet rs, String fEasting, String fNorthing){
   try {
       double _easting = rs.getDouble(fEasting);
       double _northing = rs.getDouble(fNorthing);
       return getCoordinateConvertPair(_easting, _northing);
                
} catch (Exception e) {
        return "";
}
}
private String getCircleConvertCoordinates(ResultSet rs, String fEasting, String fNorthing, String fRadius, double ScaleFactor) {
 try {  
     
     double _originX = rs.getDouble(fEasting);
     double _originY = rs.getDouble(fNorthing);
     double _radius = rs.getDouble(fRadius) * ScaleFactor;
     
     return getCircleConvertCoordinates (_originX,_originY, _radius);
     
 } catch (Exception e) {
     
  return "";
 } 
}

private String getCircleCoordinates(ResultSet rs, String fLatitude, String fLongitude, String fRadius, double ScaleFactor) {
 try {  
     
        OSNationalGridConversion OSNatGrid = new OSNationalGridConversion();
         
        OSNatGrid.Latitude=  rs.getDouble(fLatitude);
        OSNatGrid.Longitude = rs.getDouble(fLongitude);
        OSNatGrid.dbLatLongToEastingNorthingPair(); 
     
        double _originX = OSNatGrid.Easting;
        double _originY = OSNatGrid.Northing;
        double _radius = rs.getDouble(fRadius) * ScaleFactor;
     
        return getCircleConvertCoordinates ( _originX,_originY, _radius);
     
 } catch (Exception e) {
     
        return "";
 }    
     
}
private String getCircleConvertCoordinates( Double originX, Double originY, Double Radius) {
 try  {
    
     return "";
    
 }
 catch (Exception e) {
     return "";
 }
    
}
private String getCoordinateConvertPair(Double Easting, Double Northing){
   try {
       
        OSNationalGridConversion OSNatGrid = new OSNationalGridConversion();
         
        OSNatGrid.Easting =  Easting;
        OSNatGrid.Northing = Northing;
        
        return OSNatGrid.dbOSGridToLongitudeLatitudePair(); 
        
} catch (Exception e) {
        return "";
}
}

private String getCoordinatesConvert(ResultSet rs, String fEasting, String fNorthing) {
 try {
      
    String coordinate;
    String _space = " ";
    StringBuilder sb1 = new StringBuilder();
    
    if (rs.first()) {
        do {
         coordinate = getCoordinateConvertPair(rs, fEasting, fNorthing) + ",0.0";
         sb1.append(_space);
         sb1.append(coordinate);
        } while (rs.next());                       
    }
    
    return sb1.toString();
    
  } catch (Exception e) {
      return "";
  }   
}
public String getCoordinates(ResultSet rs, String fLatitude, String fLongitude){
  try {
      
    String coordinate;
    String _space = "    ";
    StringBuilder sb1 = new StringBuilder();
    
    if (rs.first()) {
        do {
         coordinate = rs.getString(fLongitude) + "," +  rs.getString(fLatitude);
         sb1.append(_space);
         sb1.append(coordinate);
        } while (rs.next());                       
    }
    
    return sb1.toString();
    
  } catch (Exception e) {
      return "";
  }
}


public  int processQueryNode(Node n1) {
    try {
        
        int row_count = 0;
        
        String query = getQuery (n1) ;
        String dict_table = getDictionaryTable(n1);
        String row_name = getOutputRowName(n1);
        
        ResultSet rs = m_db.get_query(query);
        Header h = convertXMLHeader(rs,dict_table,agsml.Constants.Lang.GINT);
        
        if (rs !=null) {
                    
            if (rs.first()) {
                   do {
                        openNode (row_name);
                            addRow(h, rs, true);
                        closeNode();
                        row_count++;
                    } while (rs.next());
               } 
            
         rs.close();
        }
    
        return row_count;

    } catch (Exception e) {
        log.log(Level.SEVERE,e.getMessage());
      return -1;
    }
}


 public Constants.AGSVersion findAGSVersion() {
     
        /* assume that the database is AGS31 */
        ags_version = Constants.AGSVersion.AGS31a;
        
        /* test for some ags 4 tables */
        
        int tret = m_db.find_tablename("TRET");
        int lmnc = m_db.find_tablename("LNMC");
        int llpl = m_db.find_tablename("LLPL");
        
        if (tret > 0| lmnc > 0 | llpl > 0) {
        ags_version = Constants.AGSVersion.AGS404;
        }
        
        return ags_version;
    }
    
    public Constants.AGSVersion AGSVersion() {
        return ags_version;    
    }

}
 

 


