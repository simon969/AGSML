package agsml;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Types;

/**
 *
 * @author thomsonsj
 */


class SQL_Base extends Base_Class{
    protected Connection m_Conn;
    
    public final String AGSSQL_PointTableName ="POINT";
    public final String AGSSQL_PointIdFieldName = "PointID";
    public final String gINTProjectIdField = "gINTProjectID";
    public final String AGS_PROJECTID = "AGSProjectID";
    public final String AGSSQL_OutputFieldName = "AGSOutputAGSMLField";
    
    public final int SQL_FLOAT = 6;
    public final int SQL_INTEGER = 4;
    public final int SQL_DATE = 91;
    public final int SQL_TIME = 92;
    public final int SQL_VARCHAR = 12;
    public final int SQL_NVARCHAR = -9;
    public final int SQL_BIT = -7;
    public final int SQL_BINARY = -2;
    public final int SQL_DATETIME = 93;
    public final int SQL_XML = -10 ;
    
    public final String[] AGS31_Tables = {
        "","",""
    };
    
    public final String[] AGS4_Tables = {
        "AAVT","ACVT","AELO","AFLK","GCHM","LLPL"
    };
    
    public enum SQLDataType  {
        
       // http://documentation.progress.com/output/DataDirect/DataDirectCloud/index.html#page/queries/microsoft-sql-server-data-types.html 
       
        NONE (-99),
        FLOAT (6),
        INTEGER (4),
        DATE  (91),
        TIME  (92),
        VARCHAR (12),
        NVARCHAR (-9),
        NVARCHAR_MAX (-1),
        BIT (-7),
        BINARY (-2),
        DATETIME (93),
        XML (-10);
        private int value;
    
        private SQLDataType(int value) {
          this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
        
    SQL_Base (String source) {
       try {
           
         File db = new File (source); 
         if (db.exists()) {
             AGS_Data f = new AGS_Data(db.getName());
             String ext = f.FileExtension();
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
              log.log(Level.SEVERE, e.getMessage());
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
           log.log(Level.SEVERE, e.getMessage());
           return false;
    }
   }
   
      boolean get_AccessConnection(String filename) throws SQLException{
        
   try {
       Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
       String url = "jdbc:ucanaccess://" + filename ;
         m_Conn = DriverManager.getConnection(url);
        return true;
    }
    catch (SQLException e) {
           log.log(Level.SEVERE, e.getMessage());
           m_Conn =  null;
           return false; }
   
    catch (ClassNotFoundException e) {
           log.log(Level.SEVERE, e.getMessage());
           m_Conn =  null;
           return false; } 
   
    catch (Exception e) {
           log.log(Level.SEVERE, e.getMessage());
           m_Conn =  null;
           return false; } 
    }
  public String getAGSVersion(String defaultAGS) {
   
        if (defaultAGS.isEmpty()) {
            defaultAGS = "AGS31";
        }
        
        try {
            int result = 0;
            DatabaseMetaData dbm = m_Conn.getMetaData();
            // check if "employee" table is there
            ResultSet resultSet = dbm.getTables(null, null, null, new String[]{"TABLE"});
        
            while(resultSet.next()) {
                String tName = resultSet.getString("TABLE_NAME");
                     if (inArray(AGS4_Tables, tName,false)>0)
                     return "AGS4";
            }
        
            return defaultAGS;
        } 
        catch  (SQLException e ) {
            log.log(Level.SEVERE, e.getMessage());
            return defaultAGS;
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
            log.log(Level.SEVERE, e.getMessage());
            return -1;
        } 
    }
   public ResultSet get_table(String sql_tname) {
       
       return get_table(sql_tname,"","");
   }
   public ResultSet get_table(String select, String where) {
     try {
            String s1;
           
            if (!where.isEmpty()) {
                s1 = select  + " where " + where + ADDgINTProjectId(" and "); 
            } else {
                s1 = select +  ADDgINTProjectId(" where ");
            }
           
            
            Statement stmt = m_Conn.createStatement(
                                      ResultSet.TYPE_SCROLL_SENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
             
            ResultSet rs = stmt.executeQuery(s1 );
             
          //  log.log(Level.INFO, "executeQuery:" + s1);  
            
             return rs;
        }
          
        catch  (SQLException e ) {
            log.log(Level.SEVERE, e.getMessage()); 
            return null;
        }
  }  
   
   public ResultSet get_table(String sql_tname, String sql_fname, String fvalue) {
       
      String s11 = "select * from " + sql_tname ;
      String s12 = "";
      
      if (!sql_fname.isEmpty()) {
          s12=sql_fname + "='" + fvalue + "'";
      }
      return get_table(s11, s12);
      
//      try {
//            
//            String s1 = "select * from " + sql_tname ;
//             
//            if (!sql_fname.isEmpty()) {
//                s1 = s1 + " where " + sql_fname + "='" + fvalue + "'" + ADDgINTProjectId(" and "); 
//            } else {
//                s1 = s1 +  ADDgINTProjectId(" where ");
//            }
//                
//             
//            Statement stmt = m_Conn.createStatement(
//                                      ResultSet.TYPE_SCROLL_SENSITIVE,
//                                      ResultSet.CONCUR_UPDATABLE);
//            ResultSet rs = stmt.executeQuery(s1 );
//            return rs;
//        }
//          
//        catch  (SQLException e ) {
//            log.log(Level.SEVERE, e.getMessage()); 
//            return null;
//        }
  }
   public ResultSet get_query(String s1) {
    
      try {
            
            Statement stmt = m_Conn.createStatement(
                                      ResultSet.TYPE_SCROLL_SENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(s1 );
         
            return rs;
        }
          
        catch  (SQLException e ) {
            log.log(Level.SEVERE, e.getMessage()); 
            return null;
        }
  }
   
    public int get_update(String s1) {
    
      try {
            
            Statement stmt = m_Conn.createStatement(
                                      ResultSet.TYPE_SCROLL_SENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
            int retvar = stmt.executeUpdate(s1);
            return retvar;
        }
          
        catch  (SQLException e ) {
            log.log(Level.SEVERE, e.getMessage()); 
            return -1;
        }
      
  }  
  
    public int updateAGSMLField (
            String PointId,
            String xmlData )
     throws SQLException
{
  try
  {
    int ret;
    // create our java preparedstatement using a sql update query
    String s1 =  "UPDATE " + AGSSQL_PointTableName + " SET " + AGSSQL_OutputFieldName + " = ? WHERE " + AGSSQL_PointIdFieldName + " = ?" + ADDgINTProjectId (" AND ");;
    
    PreparedStatement ps = m_Conn.prepareStatement( s1);
     
    // set the preparedstatement parameters
    ps.setString(1,xmlData);
    ps.setString(2,PointId);

    // call executeUpdate to execute our sql update statement
    ret = ps.executeUpdate();
    ps.close();
    return ret;
  }
  catch (SQLException se)
  {
    // log the exception
    throw se;
  }
} 
    
   ResultSetMetaData getTableMetaData (String tablename) {
       try {
            PreparedStatement WSps = m_Conn.prepareStatement("SELECT * FROM " + tablename + " WHERE 1 = 0");
           // query is never executed on the server - only prepared
           return WSps.getMetaData(); 
       }
       catch (SQLException e) {
           return null;
       }
   }
   public void setOutputAGSMLField(String s1) {
       setProperty(AGSSQL_OutputFieldName, s1);
   }
   public String OutputFieldName () {
       return getProperty(AGSSQL_OutputFieldName);
   }
   
   public void setgINTProjectID (int id) {
       if (id >=0 ) {
           String s1 = String.valueOf(id);
           setProperty(AGS_PROJECTID, s1);
       }
        
   }
   
   public int gINTProjectId() {
       return getProperty_int(AGS_PROJECTID);
   }
   
   private String ADDgINTProjectId(String Prefix) {
       if (gINTProjectId() > 0) { 
        return Prefix + "gINTProjectId=" + gINTProjectId();
       }
      return "";         
   }
   
   public SQL_Base.SQLDataType getAGSDataType(String s) {
        
        SQL_Base.SQLDataType retvar = SQL_Base.SQLDataType.NONE; 

        switch (s.toUpperCase()) {
                          case "2DP":
                          retvar = SQL_Base.SQLDataType.FLOAT;
                          case "ID": case "X":
                          retvar = SQL_Base.SQLDataType.NVARCHAR;    
                          case "0DP":
                          retvar = SQL_Base.SQLDataType.INTEGER;  
                          case "T":
                          retvar = SQL_Base.SQLDataType.TIME; 
                          case "DT":
                          retvar = SQL_Base.SQLDataType.DATETIME; 
        }
    
     return retvar;
   }
   
    public SQL_Base.SQLDataType getSQLDataType(String s) {
        
        SQL_Base.SQLDataType retvar = SQL_Base.SQLDataType.NONE; 

        switch (s.toUpperCase()) {
                          case "FLOAT": case "DOUBLE":
                          retvar = SQL_Base.SQLDataType.FLOAT;
                          break;
                          
                          case "NVARCHAR": case "VARCHAR":
                          retvar = SQL_Base.SQLDataType.NVARCHAR;    
                          break;
                          
                          case "INTEGER":
                          retvar = SQL_Base.SQLDataType.INTEGER;  
                          break;
                          
                          case "TIME":
                          retvar = SQL_Base.SQLDataType.TIME; 
                          break;
                          
                          case "DATE":
                          retvar = SQL_Base.SQLDataType.DATE; 
                          break;
                          
                          case "DATETIME": case "TIMESTAMP":
                          retvar = SQL_Base.SQLDataType.DATETIME; 
                          break;
                          
                          case "BINARY":
                          retvar = SQL_Base.SQLDataType.BINARY; 
                          break;
                          
                          case "XML":
                          retvar = SQL_Base.SQLDataType.XML; 
                          break;
                          
                          default:
                          retvar = SQL_Base.SQLDataType.NONE;
        }
    
     return retvar;  
    }
   
   
   class MyPair
{
    private final Double key;
    private final Double value;

    public MyPair(Double aKey, Double aValue)
    {
        key   = aKey;
        value = aValue;
    }

    public Double key()   { return key; }
    public Double value() { return value; }
}
   
  }
    
