package agsml;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author thomsonsj
 */
class AGS_ReaderLine40 extends AGS_Base implements AGS_ReaderLine{
    
    protected final String HoleTableName = "LOCA";
    protected final String HoleIdFieldName = "LOCA_ID";

    protected final String GeolTableName = "GEOL"; 
    protected final String GeolDepthTOPFieldName = "GEOL_TOP"; 
    protected final String GeolDepthBASEFieldName = "GEOL_BASE";
    
    protected final String keyDepthTOP = "_TOP";
    protected final String keyDepthBASE = "_BASE";
    
    protected final String keyTABLE = "GROUP";
    protected final String keyHEADER = "HEADING";
    protected final String keyUNIT = "UNIT";
    protected final String keyTYPE = "TYPE"; 
    protected final String keyDATA = "DATA"; 
    protected final String constDelimiter = "\",\"";
    private String[] m_read_line_split;
    private String[] m_read_line_split_data;
    public AGS_ReaderLine40(String fName) {super(fName);}
    public String HoleTableName() {return HoleTableName;}
    public String HoleIdFieldName() {return HoleIdFieldName;}
    public int holecount() {return rowcount(HoleTableName);}
    public String GeolTableName() {return GeolTableName;}
    public int geolcount(){return rowcount(GeolTableName);}
    public String GeolDepthTOPFieldName(){return GeolDepthTOPFieldName;}
    public String GeolDepthBASEFieldName(){return GeolDepthBASEFieldName;}
    public int get_col_depthTOP() { return m_row.col_depthTOP;}
    public int get_col_depthBASE(){ return m_row.col_depthBASE;}
    public int get_col_HoleId(){ return m_row.col_holeid;}
    public String getAGSVersion(){return "AGS4.0";}
            
    public int rowcount(String TableName) {
        int rowcount = 0; 
        AGS_ReaderLine.LineType result = FindTable (TableName);
        
        if (result==AGS_ReaderLine.LineType.tableName) {
            result = NextLine();
            while (true) {
            if (result == AGS_ReaderLine.LineType.tableName) {
                break;
            }
            if (result == AGS_ReaderLine.LineType.EOF) {
                break;
            } 
            if (result == AGS_ReaderLine.LineType.rowData) { 
                rowcount++;
            }
    
            result =  NextLine();
            }
        }
        return rowcount; 
    }
  public int get_col (String s1) {
    return m_row.FindColumn(s1);
   }
@Override public AGS_ReaderLine.LineType NextLine (){
 try {
            AGS_ReaderLine.LineType retval = AGS_ReaderLine.LineType.Empty;
            m_read_line = m_next_line;
            m_next_line = m_br.readLine();

            if (m_read_line == null && m_next_line == null) {
                retval = AGS_ReaderLine.LineType.EOF;
            } else {
            m_linecounter++;
            get_read_line_split(true,false);
            retval = getLineType();    
            }
            return retval;
 }
 catch (Exception e) {
     log.log(Level.SEVERE, e.getMessage());
     return AGS_ReaderLine.LineType.Error;
 }
}

@Override public AGS_ReaderLine.LineType ReadLine (){
    
        AGS_ReaderLine.LineType retval = AGS_ReaderLine.LineType.Empty;
        
        retval = getLineType();
        
        try {
           
            if (retval.equals(AGS_ReaderLine.LineType.rowData)) {
                readDATA();
            }      
            if (retval.equals( AGS_ReaderLine.LineType.tableHeader)) {
                readHEADER();
            }
            if (retval.equals(AGS_ReaderLine.LineType.tableName)) { 
                readTABLENAME();
            }
            if (retval.equals(AGS_ReaderLine.LineType.tableUnits)) { 
                readUNITS();
            }
        return retval;
        
        }  
        catch (Exception e) {
        // catch possible io errors from readLine()
        log.severe(e.getMessage());
        retval = AGS_ReaderLine.LineType.Error;
        }

        return retval;
}

 public AGS_ReaderLine.LineType getLineType(){
        AGS_ReaderLine.LineType retval = AGS_ReaderLine.LineType.Empty;
        if (containsDATA()) {retval = AGS_ReaderLine.LineType.rowData;}
        if (containsHEADER()) {retval = AGS_ReaderLine.LineType.tableHeader;}
        if (containsTABLENAME()) {retval = AGS_ReaderLine.LineType.tableName;}
        if (containsTYPE()) {retval = AGS_ReaderLine.LineType.tableType;}
        if (containsUNITS()) {retval = AGS_ReaderLine.LineType.tableUnits;}
        return retval;
    }
 private boolean read_line_split_contains(String s1, int Column) {
      if  (m_read_line_split == null) {
        return false;
    } else {
         if (m_read_line_split[Column].contains(s1)) {
                     return true;
          }
          return false;
        }
 }
 private boolean containsHEADER() {return read_line_split_contains(keyHEADER,0);}
 private boolean containsDATA() {return read_line_split_contains(keyDATA,0);}
 private boolean containsUNITS() {return read_line_split_contains(keyUNIT,0);}
 private boolean containsTYPE() {return read_line_split_contains(keyTYPE,0);}
 private boolean containsTABLENAME() {return read_line_split_contains(keyTABLE,0);}
 
    private boolean isFieldHoleId(String s1) {
        if (s1 == HoleIdFieldName) {
            return true;
        }
        return false;
    }
    
    private boolean isFieldDepthTOP(String s1) {
        if (s1.equalsIgnoreCase(m_tablename + keyDepthTOP)) {
             return true;
        }
        if (s1.equalsIgnoreCase("CHOC_FROM")) {
             return false;
        }
        if (s1.equalsIgnoreCase(m_tablename + "_FROM")) {
             return true;
        }
        if (s1.equalsIgnoreCase(m_tablename + "_DPTH")) {
             return true;
        }
        if (s1.equalsIgnoreCase("SAMP_TOP")) {
             return true;
        }
        if (s1.equalsIgnoreCase("WSTG_DPTH")) {
             return true;
        }
        if (s1.equalsIgnoreCase("MONG_DIS")) {
             return true;
        }
        if (s1.equalsIgnoreCase("IPRG_TOP")) {
             return true;
        }
        if (s1.equalsIgnoreCase("MONG_TRZ")) {
             return true;
        }
        if (s1.equalsIgnoreCase("MONG_DIS")) {
             return true;
        }
        if (s1.equalsIgnoreCase("PMTG_DPTH")) {
             return true;
        }
        if (s1.equalsIgnoreCase("SCDG_DPTH")) {
             return true;
        }
        if (s1.equalsIgnoreCase("WSTG_DEP")) {
             return true;
        }
        return false;       
    }
    
    private boolean isFieldDepthBASE(String s1) {
        if (s1.equalsIgnoreCase(m_tablename + keyDepthBASE)) {
             return true;
        }
        if (s1.equalsIgnoreCase("SAMP_BASE")) {
             return true;
        }
        if (s1.equalsIgnoreCase("CHOC_TO")) {
             return false;
        }
        if (s1.equalsIgnoreCase(m_tablename + "_TO")) {
             return true;
        }
        return false;  
    }
 private int readHEADER() {
    try {
        if (m_read_line_split[0].equals(keyHEADER)) {
            get_read_line_split_data();
            m_row = new AGS_Header (m_read_line_split_data);
            for (int i = 1; i< m_row.columnIN().size();i++) {
             String fname = m_row.m_columnsIN.get(i);
                if (isFieldHoleId(fname)) {
                       m_row.setHoleId_fieldName(fname, i);
                    }
                    if (isFieldDepthBASE(fname)) {
                       m_row.setDepthBASE_fieldName(fname, i);
                    }
                    if (isFieldDepthTOP(fname)) {
                       m_row.setDepthTOP_fieldName(fname, i);
                    }
            } 
                    
            return m_row.columnIN().size();
        } 
    return -1;
    }
    catch (Exception e) {
    return -1;   
    }
  }
 
 private int readDATA() {
    try {
        if (m_read_line_split[0].equals(keyDATA)) {
           get_read_line_split_data();
           m_row.setData(m_read_line_split_data);
           return m_row.m_data.size();
         }
    return -1;
    }
 catch (Exception e) {
     return -1;
 }
 }
 
 private int readTABLENAME() {
      try {
            if (m_read_line_split[0].equals(keyTABLE)) {
                m_tablename = m_read_line_split[1];
               return 1;
             }
        return -1;
        }
     catch (Exception e) {
         return -1;
     }
  }
 private int readUNITS() { 
     try {
        if (m_read_line_split[0].equals(keyUNIT)) {
            get_read_line_split_data();
            m_row.setUnits(m_read_line_split_data);
           return m_row.m_data.size();
         }
    return -1;
    }
 catch (Exception e) {
     return -1;
 }}
 
 private int readTYPE() {return -1;}
  
public AGS_ReaderLine.LineType NextTable(){
        AGS_ReaderLine.LineType result;
        do {
              result = NextLine();
              if (result.equals(AGS_ReaderLine.LineType.tableName)) {
              return result;
            }
        } while (result != AGS_ReaderLine.LineType.Error && result != AGS_ReaderLine.LineType.EOF);
        return result;
    }
public AGS_ReaderLine.LineType FindTable(String s1){
        AGS_ReaderLine.LineType result;
        String s2;
        do {
              result = NextTable();
              if (result!=AGS_ReaderLine.LineType.tableName) {
                 return result; 
              }
              readTABLENAME();
              s2 = get_tablename();
              if (s2 != null) {
                  if (s1.equals(s2)) {
                         return result;
                  }
                }
        } while (result != AGS_ReaderLine.LineType.Error && result != AGS_ReaderLine.LineType.EOF);
        return result;
    }
public List<String> get_headers(){return m_row.m_columnsIN;}
public List<String> get_units() {return m_row.m_units;}
public List<String> get_data() {return m_row.m_data;}
public String get_tablename() {return m_tablename;} 
public AGS_ReaderLine40 Copy() {
      AGS_ReaderLine40 lr1  =  new AGS_ReaderLine40(DataSource());
      return lr1;
}
public AGS_ReaderLine40 Copy (String HoleId) {
        AGS_ReaderLine40 lr1  =  new AGS_ReaderLine40(DataSource());
        String hole_ags = lr1.getAllDataForHole(HoleId);
        AGS_ReaderLine40 lr2  =  new AGS_ReaderLine40(hole_ags);
        return lr2;
    }     
private void get_read_line_split(boolean RemoveQuotesFirstandLast, boolean RemoveQuotesAllOtherItems) {
 try {   
    m_read_line_split = m_read_line.split(constDelimiter);
    
    String s1;
    
    if (RemoveQuotesFirstandLast) {
        s1 = m_read_line_split[0];
        m_read_line_split[0] = remove_boundary_quotes (s1.toCharArray()); 
        
        s1 = m_read_line_split[m_read_line_split.length-1];
        m_read_line_split[m_read_line_split.length-1] = remove_boundary_quotes (s1.toCharArray());
    }
    
    if (RemoveQuotesAllOtherItems) {
                for (int i = 1; i< m_read_line_split.length-2;i++) {
                s1 = m_read_line_split[i];
                m_read_line_split[i] = remove_boundary_quotes (s1.toCharArray());
                } 
    }
 }
 catch (Exception e) {
     
 }
}
private void get_read_line_split_data(){
   m_read_line_split_data = new String[m_read_line_split.length-1];
   for (int i = 1; i< m_read_line_split.length;i++) {
          m_read_line_split_data[i-1] = m_read_line_split[i];
   }         
}
private String remove_boundary_quotes(char[] s1) {
    int from = 0;
    int to = s1.length;
    StringBuilder s2 = new StringBuilder();
    
    if (s1[0] == '\"') {from =  1;}
    if (s1[s1.length-1] == '\"') {to = s1.length-1;}
    for (int i = from; i < to; i++) {
        s2.append(s1[i]);
    }
    return s2.toString();
    }
}
