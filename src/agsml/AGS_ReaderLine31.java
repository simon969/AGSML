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
    protected final String HoleIdFieldName = "*HOLEID"; 
    protected final String const_strCRLFQ = "\r\"\n";
    protected final String const_strCRLF= "\r\n";
    protected final String keyTABLE = "**";
    protected final String keyHEADER = "*";
    protected final String keyDELIMETER = ",";
    protected final String keyCONT = "<CONT>";
    protected final String keyUNITS = "<UNITS>";
    
    public String HoleTableName() {
       return HoleTableName; 
    };
    public String HoleIdFieldName() {
        return HoleIdFieldName;
    };
    public AGS_ReaderLine31 Copy() {
        AGS_ReaderLine31 lr1  =  new AGS_ReaderLine31(DataSource());
        return lr1;
    }
    public AGS_ReaderLine31 (BufferedReader br1) {
        super(br1);
    }
    public AGS_ReaderLine31(String fName){
        super(fName);
    }
   
    public AGS_ReaderLine.LineType NextTable(){
        AGS_ReaderLine.LineType result;
        do {
              result = ReadLine();
              if (result == AGS_ReaderLine.LineType.tableName) {
              return result;
            }
        } while (result != AGS_ReaderLine.LineType.Error);
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
              readTABLENAME();
              s2 = get_tablename(RemoveStars, RemoveQMarks, RemoveWhiteSpace,AllToLowerCase);
              if (s2 != null) {
                  if (s1.equals(s2)) {
                         return result;
                  }
                }
        } while (result != AGS_ReaderLine.LineType.Error);
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
    public Collection<String> get_headers(){
    return get_headers(false,false,false,false);
    }
    private Collection<String> get_headers(boolean RemoveStars, boolean RemoveQMarks,boolean RemoveWhiteSpace, boolean AllToLowerCase) {
       return  FormatCollection(m_row.columnINCollection(), RemoveStars, RemoveQMarks, RemoveWhiteSpace, AllToLowerCase);
    }
    public Collection<String> get_units(){
    return get_units(false,false,false,false);
    }
    private Collection<String> get_units(boolean RemoveStars, boolean RemoveQMarks, boolean RemoveWhiteSpace, boolean AllToLowerCase){
        return FormatCollection(m_row.unitsCollection(), RemoveStars, RemoveQMarks, RemoveWhiteSpace, AllToLowerCase);
     }
    public Collection<String> get_data(){
      return m_row.dataCollection();
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
      
     private Collection<String> FormatCollection(Collection c, boolean RemoveStars, boolean RemoveQMarks, boolean RemoveWhiteSpace, boolean AllToLowerCase) {
      
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

    public AGS_ReaderLine.LineType NextLine (){
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
             m_log.log(Level.SEVERE, e.getMessage());
            return AGS_ReaderLine.LineType.Error; 
         }
} 
    public AGS_ReaderLine.LineType ReadLine () {
        
        AGS_ReaderLine.LineType retval = AGS_ReaderLine.LineType.Empty;

        try {
            retval = getLineType();
           
        if (retval.equals(AGS_ReaderLine.LineType.rowData)) {
            readDATA();
            if (next_containsCONT()){
                retval = ReadLine();
            }
        }      
        if (retval.equals(AGS_ReaderLine.LineType.tableHeader)) {
            readHEADER();
            if (next_containsCONT()) {
                retval = ReadLine();
            }
        }
        if (retval.equals(AGS_ReaderLine.LineType.tableName)) { 
            readTABLENAME();
        }
        if (retval.equals(AGS_ReaderLine.LineType.tableUnits)) {
            readUNITS();
        }
    //    if (retval == intCONT()) { retval = read_cont();}
        if (retval.equals(AGS_ReaderLine.LineType.rowCONT)) {
            retval = read_cont();
            if (next_containsCONT()) {
                retval = ReadLine();
            }
        }
        }  catch (Exception e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
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
        if (containsCONT()) {retval = AGS_ReaderLine.LineType.rowCONT;}
        return retval;
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
          m_row = new Header();
          int from_i = 0;
          int to_i = 0;
          do {
                to_i = find_next(from_i, keyDELIMETER , true, true);
                m_substr = get_substring(from_i, to_i, const_strCRLFQ);
                m_tablename = m_substr;
           } while (to_i < m_read_line.length());

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

   
    private int readHEADER() {
          int from_i = 0;
          int to_i = 0;
          
          SQL_Base.SQLDataType def_datatype = SQL_Base.SQLDataType.NVARCHAR;
        
          do {
                to_i = find_next(from_i, ",", true, true);
                m_substr = get_substring(from_i, to_i, const_strCRLFQ);
                if (m_substr.length()> 0) {
                m_row.AddColumn(m_substr, def_datatype.getValue());
                }
                from_i = to_i + 1;
           } while (to_i < m_read_line.length());

          return m_row.columnIN().size();
  
 }
   

   }