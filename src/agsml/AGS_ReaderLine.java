package agsml;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author thomsonsj
 */

interface AGS_ReaderLine {
    
public AGS_ReaderLine.LineType ReadLine ();
public AGS_ReaderLine.LineType NextLine ();
public AGS_ReaderLine.LineType NextTable();
public AGS_ReaderLine.LineType FindTable(String s1);
public List<String> get_headers();
public List<String> get_units();
public List<String> get_data();
public String get_tablename();  
public int rowcount (String tablename); 
public int holecount ();
public int geolcount ();
public void setDataSource (String fName) ;
public void setLogger(Logger log);
public void setProperty (String Name, String Value );
public String getProperty (String Name);
public AGS_ReaderLine Copy();
public AGS_ReaderLine Copy(String HoleID);
public String HoleTableName();
public String HoleIdFieldName();
public String GeolTableName();
public String GeolDepthTOPFieldName();
public String GeolDepthBASEFieldName();
public int get_col (String fieldname);
public int get_col_depthTOP();
public int get_col_depthBASE();
public String getAGSVersion();

 public enum LineType {
    EOF         (-2),
    Error       (-1),
    Empty       (0),
    rowData     (1),
    tableHeader (2),
    tableName   (3),
    tableUnits  (4),
    tableType   (5),
    rowCONT     (6);
    
    private int value;
    private LineType(int value) {
      this.value = value;
    }
    public int getValue() {
        return value;
    }
    
    
    } 
}