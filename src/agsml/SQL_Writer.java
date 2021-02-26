package agsml;

import static agsml.XML_Writer.AGS_OUTPUTFILE;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import org.w3c.dom.Node;

/**
 *
 * @author thomsonsj
 */
class SQL_Writer extends Base_Writer{
    
    SQL_Base m_db;
     
    public String AGSSQL_OutputFieldName;

    SQL_Writer (String source) {
       m_db= new SQL_Base(source);
    }
    
    public void setOutputFile (String s1) {
        setProperty(AGS_OUTPUTFILE,s1);
    }
    
 protected Header convertHeaderGINT(ResultSetMetaData rsmd, String tname1, agsml.Constants.Lang Lang) {
 try {
        Header r = new Header();    
        String fname2="";
        String s1="";
        int ftype2 = m_db.SQL_VARCHAR;
        boolean TableFound = false;
        boolean FieldFound = false;
        
        
        TableFound = (m_dic.find_table(tname1, Lang) != null );
                
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
         String fname1 = rsmd.getColumnName(i);
            if (TableFound) {
                FieldFound = (m_dic.find_field(fname1, Lang)!=null);
                    if (FieldFound) {
                        fname2 = m_dic.fieldGINT_name();
                        s1 = m_dic.fieldGINT_type();
                        ftype2 = Integer.getInteger(s1);
                            if (ftype2 == 0) {
                                ftype2 = m_db.SQL_VARCHAR;
                            }
                    } 
            }
        r.AddColumn(fname1, fname2, String.valueOf(ftype2) ,"","" );
        }
        return r;
        
 }
 catch (Exception e) {
//     m_log(Level.SEVERE, e.getMessage());      
     return null;
 }  
 }
 
 protected Header convertHeaderGINT(Collection<String> header1, String tname1,agsml.Constants.Lang Lang) {
 try {
        Header r = new Header();    
        String fname2="";
        String s1="";
        int ftype2 = m_db.SQL_VARCHAR;
        String length;
        boolean TableFound = false;
        boolean FieldFound = false;
        
        
        TableFound = (m_dic.find_table(tname1, Lang) != null );
                
        for (String fname1 : header1) {
           if (TableFound) {
                FieldFound = (m_dic.find_field(fname1, Lang)!=null);
                    if (FieldFound) {
                        fname2 = m_dic.fieldGINT_name();
                        s1 = m_dic.fieldGINT_type();
                        ftype2 = Integer.getInteger(s1);
                            if (ftype2 == 0) {
                                ftype2 = m_db.SQL_VARCHAR;
                            }
                    } 
            }
        r.AddColumn(fname1, fname2, String.valueOf(ftype2) ,"","" );
        }
        return r;
        
 }
 catch (Exception e) {
//     m_log(Level.SEVERE, e.getMessage());      
     return null;
 }
} 
 protected String getTableInsert(String tablename, List<String> columns) {
 
     String s1 = "insert into " + tablename;
     String s2 = "";
     
      for (int i = 0; i < columns.size(); i++) {
          if (s2.length()==0) {
              s2 = columns.get(i);
          } else {
              s2 = "," + columns.get(i);
          }
      }
     return s1 + "(" + s2 + ")"; 
 }
 
  protected String getTableInsertData(List<String> data,  int[] datatype) {
      String s2 = "";
      for (int i = 0; i < data.size(); i++) {
         if (i==0) {
            s2 = format_data(data.get(i) ,datatype[i]);
            }
        else {
              s2 = "," + format_data(data.get(i),datatype[i]);
            }
     }
     return "(" + s2 + ")"; 
 } 

 protected String format_data(String data, int type) {
    
    if (type==1) {
    return "'" + data + "'";
    } else
    return data;
}
 protected boolean createTable(String tablename, Header columnsRequired) {
   
     
     return true;
 }
 protected boolean ensureTable(String tablename, Header columnsRequired, boolean createNew) {
    
     try {
       
            String  Msg = "";
            boolean columnMissing = false;
            boolean tableMissing = false;
            boolean columnTypeWrong = false;
            
            ResultSetMetaData rsmd = m_db.getTableMetaData (tablename);
           
            if (rsmd != null ) {
                if (createNew) {
                    tableMissing = createTable(tablename, columnsRequired);
                } else {
                tableMissing = true;
                }
            }
            else {    
                for ( int i = 0; i < rsmd.getColumnCount(); i++) {
                    String columnName = rsmd.getColumnName(i);
                    int columnType = rsmd.getColumnType(i);
                    int offset = columnsRequired.FindColumn(columnName);
                        if (offset >=0 ){
                            int ctype = Integer.getInteger(columnsRequired.type().get(offset));
                                if (ctype!=columnType) {
                                     columnTypeWrong = true;
                                      Msg = Msg +  "Column Type Wrong=" + columnType;
                                }
                        }
                        else {columnMissing=true;
                            Msg = Msg +  "Column Missing=" + columnName;
                        }
                }
            }
            
            return (!tableMissing && !columnMissing && !columnTypeWrong) ;
                    
            }
       
    catch (SQLException e) {
    return false;
    }
   
}
Collection AddDictionaryTypes(String table, Collection header) {
    
 //   Node t1 = m_dic.find_table1(table);
    
//    Node f1 = m_dic.find_field1(table).find_field(  table, table);
    
    
    
  return header;  
    
}
 
}

