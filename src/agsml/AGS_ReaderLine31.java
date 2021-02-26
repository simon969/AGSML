/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package agsml;
/**
 *
 * @author Simon
 */

import java.util.ArrayList; 
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.io.FileReader;
import java.util.HashMap;
import java.util.logging.Level;
import org.w3c.dom.Node;

class AGS_ReaderLine31  extends AGS_Base implements AGS_ReaderLine{
    
    protected final String HoleTableName = "**HOLE";
    protected final String HoleIdFieldName = "*HOLE_ID";
    
    protected final String GeolTableName = "**GEOL";
    protected final String GeolDepthTOPFieldName = "*GEOL_TOP"; 
    protected final String GeolDepthBASEFieldName = "*GEOL_BASE";
    
    protected final String keyDepthTOP = "_TOP";
    protected final String keyDepthBASE = "_BASE";
    protected final String keyDepthStrike = "_DEP";
    
    protected final String const_strCRLFQ = "\r\"\n";
    protected final String const_strCRLF= "\r\n";
    protected final String keyTABLE = "**";
    protected final String keyHEADER = "*";
    protected final String keyDELIMETER = ",";
    protected final String keyCONT = "<CONT>";
    protected final String keyUNITS = "<UNITS>";
    protected final String keyTYPE = "<TYPE>";
      
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
    public String getAGSVersion(){return "AGS3.1";}
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
    public AGS_ReaderLine31 Copy() {
        AGS_ReaderLine31 lr1  =  new AGS_ReaderLine31(DataSource());
        return lr1;
    }
    public AGS_ReaderLine31 Copy (String HoleId) {
        AGS_ReaderLine31 lr2  =  new AGS_ReaderLine31(DataSource());
        String hole_ags = lr2.getAllDataForHole(HoleId);
        AGS_ReaderLine31 lr1  =  new AGS_ReaderLine31(hole_ags);
        return lr1;
    }
    public AGS_ReaderLine31 (BufferedReader br1) {
        super(br1);
    }
    public AGS_ReaderLine31(String fName){
        super(fName);
    }
   public int get_col (String s1) {
    return m_row.FindColumn(s1);
   }
   public AGS_ReaderLine.LineType NextTable(){
        AGS_ReaderLine.LineType result;
        do {
              result = NextLine();
              if (result == AGS_ReaderLine.LineType.tableName) {
              return result;
            }
        } while (result != AGS_ReaderLine.LineType.Error && result != AGS_ReaderLine.LineType.EOF);
        return result;
    }
    public AGS_ReaderLine.LineType FindTable(String s1){
       return find_tablename(s1 , false, true, false, false);
    }
    private AGS_ReaderLine.LineType find_tablename(String s1, boolean RemoveStars, boolean RemoveQMarks, boolean RemoveWhiteSpace, boolean AllToLowerCase){
        AGS_ReaderLine.LineType result;
        String s2;
        do {
              result = NextTable();
              if (result!=AGS_ReaderLine.LineType.tableName) {
                 return result; 
              }
              readTABLENAME();
              s2 = get_tablename(RemoveStars, RemoveQMarks, RemoveWhiteSpace,AllToLowerCase);
              if (s2 != null) {
                  if (s1.equals(s2)) {
                         return result;
                  }
                }
        } while (result != AGS_ReaderLine.LineType.Error && result != AGS_ReaderLine.LineType.EOF);
        return result;
    }

   private int index_tables() {
     int count = 0;
     try {
            m_hm = new HashMap<String, Integer>();
            while  ( NextTable() != null) {
                m_hm.put(get_tablename(), line_counter());
                count++;
            }  ;
     return count++;
     }
     
       
    catch (Exception e) {
        return count;
    }  
   }
    public int line_counter(){
        return m_linecounter;
    }
    public List<String> get_headers(){
    return get_headers(false,true,false,false);
    }
    private List<String> get_headers(boolean RemoveStars, boolean RemoveQMarks,boolean RemoveWhiteSpace, boolean AllToLowerCase) {
       return  FormatList(m_row.m_columnsIN, RemoveStars, RemoveQMarks, RemoveWhiteSpace, AllToLowerCase);
    }
      
    public List<String> get_units(){
    return get_units(false,false,false,false);
    }
    private List<String> get_units(boolean RemoveStars, boolean RemoveQMarks, boolean RemoveWhiteSpace, boolean AllToLowerCase){
        return FormatList(m_row.m_units, RemoveStars, RemoveQMarks, RemoveWhiteSpace, AllToLowerCase);
     }
    public List<String> get_data(){
      return m_row.m_data;
    }
    public String get_tablename(){
        return get_tablename(false,false,false,false);
    }
    private String get_tablename(boolean RemoveStars, boolean RemoveQMarks, boolean RemoveWhiteSpace, boolean AllToLowerCase){
       String s1 = m_tablename;
       if (RemoveStars == true) {s1 = RemoveStars(s1);}
       if (RemoveQMarks == true) {s1 = RemoveQMarks(s1);}
       if (RemoveWhiteSpace == true) {s1 = RemoveWhiteSpace(s1);}
       if (AllToLowerCase == true) {s1 = s1.toLowerCase();}
       
       return s1;
    }
    
    private String RemoveStars(String s1){
       char ch;
       StringBuilder sb;
       sb = new StringBuilder();
            for (int i=0; i<s1.length(); i++) {
                ch = s1.charAt(i);
                if (ch !='*') {
                       sb.append(ch);
                    }
            }
        return sb.toString();
        
     }
    
       private String RemoveQMarks(String s1){
       char ch;
       StringBuilder sb;
       sb = new StringBuilder();
            for (int i=0; i<s1.length(); i++) {
                ch = s1.charAt(i);
                if (ch !='?') {
                       sb.append(ch);
                    }
            }
        return sb.toString();
        
     }
      private String RemoveWhiteSpace(String s1){
       char ch;
       StringBuilder sb;
       sb = new StringBuilder();
            for (int i=0; i<s1.length(); i++) {
                ch = s1.charAt(i);
                if (ch !=' ') {
                       sb.append(ch);
                    }
            }
        return sb.toString();
    }
     private List<String> FormatList(List c, boolean RemoveStars, boolean RemoveQMarks, boolean RemoveWhiteSpace, boolean AllToLowerCase) {
      
        String s1;
        List<String> temp = new ArrayList<String>();
        Iterator itr = c.iterator();
        while (itr.hasNext()){
                    s1 = itr.next().toString();
                    if (RemoveStars == true) {s1 = RemoveStars(s1);}
                    if (RemoveQMarks == true) {s1 = RemoveQMarks(s1);}
                    if (RemoveWhiteSpace == true) {s1 = RemoveWhiteSpace(s1);}
                    if (AllToLowerCase == true) {s1 = s1.toLowerCase();}
                    
                    temp.add(s1);
        }
        return temp;
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
                retval = getLineType();    
                }
                return retval;
         }
         catch (Exception e) {
             log.log(Level.SEVERE, e.getMessage());
            return AGS_ReaderLine.LineType.Error; 
         }
} 
   @Override public AGS_ReaderLine.LineType ReadLine () {
        
        AGS_ReaderLine.LineType retval = AGS_ReaderLine.LineType.Empty;

        try {
        
            retval = getLineType();
           
        if (retval.equals(AGS_ReaderLine.LineType.rowData)) {
            readDATA();
            while (next_containsCONT()){
                NextLine();
                read_cont_data();
            }
        }      
        if (retval.equals(AGS_ReaderLine.LineType.tableHeader)) {
            readHEADER();
        }
        
        if (retval.equals(AGS_ReaderLine.LineType.tableName)) { 
            readTABLENAME();
        }
        if (retval.equals(AGS_ReaderLine.LineType.tableUnits)) {
            readUNITS();
        }
    //    if (retval == intCONT()) { retval = read_cont();}
        if (retval.equals(AGS_ReaderLine.LineType.rowCONT)) {
        
        }
        
        }  catch (Exception e) {
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
        if (containsUNITS()) {retval = AGS_ReaderLine.LineType.tableUnits;}
        if (containsTYPE()) {retval = AGS_ReaderLine.LineType.tableType;}
        if (containsCONT()) {retval = AGS_ReaderLine.LineType.rowCONT;}
        return retval;
    }
    private boolean containsTYPE() {
        if  (m_read_line == null) {
            return false;
        } else {
        return m_read_line.contains(keyTYPE);
        } 
    }
    private boolean containsCONT() {
    if  (m_read_line == null) {
        return false;
    } else {
        return m_read_line.contains(keyCONT);
        } 
    }
    
    private boolean containsHEADER() {
     if  (m_read_line == null) {
        return false;
    } else {
        int from_i = 0;
        int to_i = 0;
        if (m_read_line.contains(keyHEADER)) {
          to_i = find_next(from_i, ",", true, true);
          m_substr = get_substring(from_i, to_i, const_strCRLFQ);
          if (m_substr.startsWith(keyHEADER)) {
              return true;
          }
          return false;
        }
        return false;
    }
    }
    private boolean containsTABLENAME () {
    if  (m_read_line == null) {
        return false;
    } else {
    return (m_read_line.contains("**")&&  m_read_line.contains(",") != true);
    } 
    }
    private boolean containsUNITS () { 
    try {
    return m_read_line.contains("<UNITS>");
    } catch(NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean containsDATA () {
    
    if (m_read_line == null) {
        return false;
    } else {
     return (m_read_line.length() > 0);
     }
   }
   

    private boolean next_containsCONT(){

    if (m_next_line == null) {
        return false;
    } else {
        return m_next_line.contains(keyCONT);
        }
    }
   // try {
   // return m_next_line.contains("<CONT>");
   // } catch(NullPointerException e) {
   //         // e.printStackTrace();
   //         return false;
  
    private int readTABLENAME(){
        // initialise all table variables'
          m_row = new AGS_Header();
          int from_i = 0;
          int to_i = 0;
          do {
                to_i = find_next(from_i, keyDELIMETER , true, true);
                m_substr = get_substring(from_i, to_i, const_strCRLFQ);
                m_tablename = m_substr;
           } while (to_i < m_read_line.length());
          m_row.m_tableIN = m_tablename;
          return m_tablename.length();
    }
    
    private int readUNITS(){
        int header_count  =  m_row.columnIN().size();
        int unit_count = 0;
        int from_i = 0;
          int to_i = 0;
          do {
                to_i = find_next(from_i, keyDELIMETER , true, true);
                m_substr = get_substring(from_i, to_i, const_strCRLFQ);
                m_row.units().add(m_substr);
                unit_count++;
                from_i = to_i + 1;
           } while (unit_count < header_count && to_i < m_read_line.length());

        return m_row.units().size();
    }
    
    private int readDATA() {
         int data_count = 0;
         int to_i = 0;
         int from_i = 0;
         m_row.newData();
         do {
             to_i = find_next(from_i, keyDELIMETER , false, true);
             if (to_i > from_i) {
             m_substr= get_substring(from_i, to_i, const_strCRLFQ);
             m_row.data().add(data_count, m_substr);
             data_count++;
             }
             from_i = to_i + 1;
         } while (to_i < m_read_line.length());
      return m_row.data().size();
    }

     private AGS_ReaderLine.LineType read_cont() {
         
         AGS_ReaderLine.LineType retval = AGS_ReaderLine.LineType.Empty;

         if (containsDATA()) {retval = AGS_ReaderLine.LineType.rowData;}
         if (containsHEADER()) {retval = AGS_ReaderLine.LineType.tableHeader;}
         if (containsUNITS()) {retval = AGS_ReaderLine.LineType.tableUnits;}
         
         if (retval == AGS_ReaderLine.LineType.rowData) { read_cont_data();} 
         if (retval == AGS_ReaderLine.LineType.tableHeader) { readHEADER();} 
         if (retval == AGS_ReaderLine.LineType.tableUnits) { readUNITS();} 
     
         return retval;
     }

     private int read_cont_data() {
         int data_count = 0;
         int to_i = 0;
         int from_i = 0;
         List<String> l1 = new ArrayList<String>();
         List<String> l2 = new ArrayList<String>();
         l1.addAll(m_row.data());
       
         do {
             to_i = find_next(from_i, keyDELIMETER , false, true);
             if (to_i > from_i) {
             m_substr = get_substring(from_i, to_i, const_strCRLFQ);
             if (m_substr.equals(keyCONT)) {
                 m_substr="";}
             l2.add(data_count, m_substr);
             data_count++;
             }
             from_i = to_i + 1;
            } while (to_i < m_read_line.length());
         
      m_row.newData();   
      merge_lists(l1, l2, m_row.data());
      return m_row.data().size();
    }

     protected void merge_lists(List<String> l1, List<String> l2,List<String> l3 ) {
         int i;
         String s1;
         String s2;
         String s3;
         
         for (i=0; i<l1.size(); i++) {
               s1 = l1.get(i);
               s2 = l2.get(i);
               m_sb = new StringBuilder();
               m_sb.append(s1);
               m_sb.append(s2);
               s3 = m_sb.toString();
               l3.add(s3);
         }
         for (i=l1.size(); i<l2.size(); i++) {
               l3.add(l2.get(i));
         }
    }

    private boolean isFieldHoleId(String s1) {
        if (s1 == HoleIdFieldName) {
            return true;
        }
        return false;
    }
    
    private boolean isFieldDepthTOP(String s1) {

        if (s1.equalsIgnoreCase("*" + m_tablename.substring(2)+ keyDepthTOP)) {
             return true;
        }
        if (s1.equalsIgnoreCase("*" + m_tablename.substring(2)+ keyDepthStrike)) {
             return true;
        }
        if (s1.equalsIgnoreCase("*SAMP_TOP")) {
             return true;
        }
        if (s1.equalsIgnoreCase("*" + m_tablename.substring(2)+ "_FROM")) {
             return true;
        }
        if (s1.equalsIgnoreCase("*" + m_tablename + "_DPTH")) {
             return true;
        }
        if (s1.equalsIgnoreCase("*MONP_DIS")) {
             return true;
        }
        if (s1.equalsIgnoreCase("*MONR_DIS")) {
             return true;
        }
        if (s1.equalsIgnoreCase("*INST_TDEP")) {
             return true;
        }  
        if (s1.equalsIgnoreCase("*PREF_TDEP")) {
             return true;
        } 
        if (s1.equalsIgnoreCase("*PROB_TDEP")) {
             return true;
        } 
        if (s1.equalsIgnoreCase("*PROF_TRPS")) {
             return true;
        } 
        return false;       
    }
    
    private boolean isFieldDepthBASE(String s1) {
        
        if (s1.equalsIgnoreCase("*" + m_tablename.substring(2) + keyDepthBASE)) {
             return true;
        }
     
        if (s1.equalsIgnoreCase("*SAMP_BASE")) {
             return true;
        }
        if (s1.equalsIgnoreCase("*" + m_tablename.substring(2)+ "_TO")) {
             return true;
        }
        
        
        
        
        
        
        return false;  
    }
    
    private int readHEADER() {
          int from_i = 0;
          int to_i = 0;
          int count = 0;
          SQL_Base.SQLDataType def_datatype = SQL_Base.SQLDataType.NVARCHAR;
        
          do {
                to_i = find_next(from_i, ",", true, true);
                m_substr = get_substring(from_i, to_i, const_strCRLFQ);
                if (m_substr.length()> 0) {
                    m_row.AddColumn(m_substr, def_datatype.getValue());
                    if (isFieldHoleId(m_substr)) {
                       m_row.setHoleId_fieldName(m_substr, count);
                    }
                    if (isFieldDepthBASE(m_substr)) {
                       m_row.setDepthBASE_fieldName(m_substr, count);
                    }
                    if (isFieldDepthTOP(m_substr)) {
                       m_row.setDepthTOP_fieldName(m_substr, count);
                    }
                 count = count + 1;
                }
            from_i = to_i + 1;
                
           } while (to_i < m_read_line.length());

          return m_row.columnIN().size();
  
 }
   

   }