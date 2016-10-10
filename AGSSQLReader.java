package agsml;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author thomsonsj
 */
public class AGSSQLReader extends AGS_Base{
    protected Connection m_Conn;
    
    public final String AGSSQL_PointTableName ="POINT";
    public final String AGSSQL_PointIdFieldName = "PointID";

    
    
    AGSSQLReader (String source) {
       try {
           
         File db = new File (source); 
         if (db.exists()) {
             Filename f1 = new Filename(db.getName(),'\\' ,'.');
             String ext = f1.extension();
                if (ext.equals("mdb") || ext.equals("gpj") || ext.equals("accdb")) {
                get_AccessConnection(db.getAbsolutePath());
                }
         } else {
            if (source.contains(";")) {
               get_SQLServerConnection(source);
            }  
         }
        
        
         
       } 
       catch (Exception e) {
              m_log.log(Level.SEVERE, e.getMessage());
       }
 }
   boolean get_SQLServerConnection(String url) {
   
       http://vikdor.blogspot.co.uk/2012/10/connecting-to-sql-server-express.html
        
   try {
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      m_Conn = DriverManager.getConnection(url);

            return true;
    }
    catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());
            return false;
    }
   }
   
      boolean get_AccessConnection(String filename) {
        
   try {
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
         String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="+ filename;
         m_Conn = DriverManager.getConnection(url);
        return true;
    }
    catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());
            return false;
    }
   }
   
  public int find_tablename(String s1){
       
        try {
         int result = 0;
         DatabaseMetaData dbm = m_Conn.getMetaData();
        // check if "employee" table is there
        ResultSet tables = dbm.getTables(null, null, s1, null);
        if (tables.next()) {
        // Table exists
            result = 1;
        }
        else {
        // Table does not exist
            result = -1;
        }
        
        return result;
        }
          
        catch  (SQLException e ) {
            m_log.log(Level.SEVERE, e.getMessage());
            return -1;
        } 
    }
   public ResultSet get_table(String sql_tname) {
       m_tablename = sql_tname;
       return get_table(sql_tname,"","");
   }
  public ResultSet get_table(String sql_tname, String sql_fname, String fvalue) {
    
      try {
            
            String s1 = "select * from " + sql_tname;
         
            if (!sql_fname.isEmpty()) {
                s1 = s1 + " where " + sql_fname + "='" + fvalue + "'";  
            }
             
            Statement stmt = m_Conn.createStatement(
                                      ResultSet.TYPE_SCROLL_SENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(s1 );
            return rs;
        }
          
        catch  (SQLException e ) {
            m_log.log(Level.SEVERE, e.getMessage()); 
            return null;
        }
  }
   public ResultSet get_Query(String s1) {
    
      try {
            
            Statement stmt = m_Conn.createStatement(
                                      ResultSet.TYPE_SCROLL_SENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(s1 );
            return rs;
        }
          
        catch  (SQLException e ) {
            m_log.log(Level.SEVERE, e.getMessage()); 
            return null;
        }
  }
   public Collection get_headers(ResultSet rs) {
      try {
            
            ResultSetMetaData metaData = rs.getMetaData();
            m_headers = new LinkedHashMap<String, Integer>();
            int count = metaData.getColumnCount(); //number of column
            for (int i = 1; i <= count; i++)
                {
                 m_headers.put(metaData.getColumnLabel(i), m_headers.size() ); 
            }
            Collection c = m_headers.keySet();
       return c;      
      }
      catch(SQLException e) {
             m_log.log(Level.SEVERE, e.getMessage());
      return null;
      }
      
   }
  }
  
