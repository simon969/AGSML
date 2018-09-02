package agsml;

import java.util.Collection;
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
public Collection<String> get_headers();
public Collection<String> get_units();
public Collection<String> get_data();
public String get_tablename();  
public int rowcount (String tablename); 
public int holecount ();
public void setDataSource (String fName) ;
public void setLogger(Logger log);
public void setProperty (String Name, String Value );
public String getProperty (String Name);
public AGS_ReaderLine Copy();
public String HoleTableName();
public String HoleIdFieldName();

 public enum LineType {
    EOF         (-2),
    Error       (-1),
    Empty       (0),
    rowData     (1),
    tableHeader (2),
    tableName   (3),
    tableUnits  (4),
    rowCONT     (5); 
    private int value;
    private LineType(int value) {
      this.value = value;
    }
    public int getValue() {
        return value;
    }
    
    
    } 
}