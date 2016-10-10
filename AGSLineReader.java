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

public class AGSLineReader  extends AGS_Base{
    private String m_fname;
    private FileReader m_fr;
    private CharSequence m_ch;
    private BufferedReader m_br;
    private int m_linecounter;
    protected List<String> m_units;
    private String m_read_line;
    private String m_next_line;
    private String m_substr;
    private StringBuilder m_sb;
    private String const_strCRLFQ;
    private String m_NL;
    
    public final int AGSLR_LineType_Error          = -1;
    public final int AGSLR_LineType_Empty         = 0;
    public final int AGSLR_LineType_rowData     = 1;
    public final int AGSLR_LineType_tableHeader   = 2;
    public final int AGSLR_LineType_tableName     = 3;
    public final int AGSLR_LineType_tableUnits    = 4;
    public final int AGSLR_LineType_rowCONT    = 5;
    
    public final String AGSLR_HoleTableName = "**HOLE";
    public final String AGSLR_HoleIdFieldName = "*HOLEID";
    
    public AGSLineReader(AGSLineReader lr1){
        try {
         m_fname = lr1.m_fname;
         m_fr = new FileReader(m_fname);
         init_BufferedReader(new BufferedReader(m_fr));
         }
        catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        }
    }

    public AGSLineReader(String fName){
         try {
         m_fname = fName;
         m_fr = new FileReader(fName);
         init_BufferedReader(new BufferedReader(m_fr));
         }
        catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        }
    }
    public AGSLineReader(BufferedReader br){
        init_BufferedReader(br);
    }
    public int NextTable(){
        int result;
        do {
              result = ReadLine();
              if (result == intTableName()) {
              return result;
            }
        } while (result != intError());
        return result;
    }
    public int find_tablename(String s1, boolean RemoveStars, boolean RemoveQMarks, boolean RemoveWhiteSpace, boolean AllToLowerCase){
        int result;
        String s2;
        do {
              result = NextTable();
              s2 = get_tablename(RemoveStars, RemoveQMarks, RemoveWhiteSpace,AllToLowerCase);
              if (s2 != null) {
                  if (s1.equals(s2)) {
                         return result;
                  }
                }
        } while (result != intError());
        return result;
    }
    public boolean NewBufferedReader() {
          init_BufferedReader(new BufferedReader(m_fr));
          return true;
    }

    private void init_BufferedReader(BufferedReader br){
    m_br = br;
    m_headers = new LinkedHashMap<String, Integer>();
    m_units = new ArrayList<String>();
    m_data = new ArrayList<String>();
    m_read_line = new String();
    m_next_line = new String();
    const_strCRLFQ = "\r\"\n";
    m_linecounter = 0;
    }

    public int line_counter(){
        return m_linecounter;
    }
    public Collection get_headers(){
    return get_headers(false,false,false,false);
    }
    public Collection get_headers(boolean RemoveStars, boolean RemoveQMarks,boolean RemoveWhiteSpace, boolean AllToLowerCase) {
        Collection c = m_headers.keySet();
        c = FormatCollection(c, RemoveStars, RemoveQMarks, RemoveWhiteSpace, AllToLowerCase);
        return c;
    }
    public Collection get_units(){
    return get_units(false,false,false,false);
    }
    public Collection get_units(boolean RemoveStars, boolean RemoveQMarks, boolean RemoveWhiteSpace, boolean AllToLowerCase){
        Collection c = m_units;
        c = FormatCollection(c, RemoveStars, RemoveQMarks, RemoveWhiteSpace, AllToLowerCase);
        return c;
     }
    public Collection get_data(){
        Collection c = m_data;
        return c;
    }
    public String get_tablename(){
        return get_tablename(false,false,false,false);
    }
    public String get_tablename(boolean RemoveStars, boolean RemoveQMarks, boolean RemoveWhiteSpace, boolean AllToLowerCase){
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
      
     private Collection FormatCollection(Collection c, boolean RemoveStars, boolean RemoveQMarks, boolean RemoveWhiteSpace, boolean AllToLowerCase) {
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
    public int intError(){
        return AGSLR_LineType_Error ;
    }
    public int intEmpty(){
        return AGSLR_LineType_Empty ;
    }
    public int intData(){
        return AGSLR_LineType_rowData;
    }
    public int intHeader(){
        return AGSLR_LineType_tableHeader ;
    }
    public int intTableName(){
        return AGSLR_LineType_tableName;
    }
    public int intUnits(){
        return AGSLR_LineType_tableUnits ;
    }
    public int intCONT(){
        return AGSLR_LineType_rowCONT ;
    }
    public  int ReadLine () {
        
        int retval = 0;

        try {

        m_read_line = m_next_line;
        m_next_line = m_br.readLine();

        if (m_read_line == null && m_next_line == null) {
            retval = -1;
        } else {
        m_linecounter++;

        if (containsData()) {retval = intData();}
        if (containsHeader()) {retval = intHeader();}
        if (containsTableName()) {retval = intTableName();}
        if (containsUnits()) {retval = intUnits();}
        if (containsCONT()) {retval = intCONT();}

  //      if (retval == intCONT()) {
    //        read_cont();
      //      if (next_containsCONT()){
        //        retval = ReadLine();
         //   }
        }
        if (retval == intData()) {
            read_data();
            if (next_containsCONT()){
                retval = ReadLine();
            }
        }      
        if (retval == intHeader()) {
            read_header();
            if (next_containsCONT()) {
                retval = ReadLine();
            }
        }
        if (retval == intTableName()) { read_tablename();}
        if (retval == intUnits()) { read_units();}
    //    if (retval == intCONT()) { retval = read_cont();}
        if (retval == intCONT()) {
            retval = read_cont();
            if (next_containsCONT()) {
                retval = ReadLine();
            }
        }
        }  catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        retval = -1;
        }

        return retval;
    }

    private boolean containsCONT() {
    if  (m_read_line == null) {
        return false;
    } else {
        return m_read_line.contains("<CONT>");
        } 
    }
    
    private boolean containsHeader() {
     if  (m_read_line == null) {
        return false;
    } else {
        int from_i = 0;
        int to_i = 0;
        if (m_read_line.contains("*")) {
          to_i = find_next(from_i, ",", true, true);
          m_substr = get_substring(from_i, to_i, const_strCRLFQ);
          if (m_substr.startsWith("*")) {
              return true;
          }
          return false;
        }
        return false;
    }
    }
    private boolean containsTableName () {
    if  (m_read_line == null) {
        return false;
    } else {
    return (m_read_line.contains("**")&&  m_read_line.contains(",") != true);
    } 
    }
    private boolean containsUnits () { 
    try {
    return m_read_line.contains("<UNITS>");
    } catch(NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean containsData () {
    
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
        return m_next_line.contains("<CONT>");
        }
    }
   // try {
   // return m_next_line.contains("<CONT>");
   // } catch(NullPointerException e) {
   //         // e.printStackTrace();
   //         return false;
  
    private int read_tablename(){
        // initialise all table variables'
         m_tablename = new String();
         m_headers = new LinkedHashMap<String, Integer>();
         m_units = new ArrayList<String>();

          int from_i = 0;
          int to_i = 0;
          do {
                to_i = find_next(from_i, ",", true, true);
                m_substr = get_substring(from_i, to_i, const_strCRLFQ);
                m_tablename = m_substr;
           } while (to_i < m_read_line.length());

          return m_tablename.length();
    }
    
    private int read_units(){
        int header_count  =  m_headers.size();
        int unit_count = 0;
        int from_i = 0;
          int to_i = 0;
          do {
                to_i = find_next(from_i, ",", true, true);
                m_substr = get_substring(from_i, to_i, const_strCRLFQ);
                m_units.add(m_substr);
                unit_count++;
                from_i = to_i + 1;
           } while (unit_count < header_count && to_i < m_read_line.length());

        return m_units.size();
    }
    
    private int read_data () {
         int data_count = 0;
         int to_i = 0;
         int from_i = 0;
         m_data = new ArrayList<String>();
         do {
             to_i = find_next(from_i, ",", false, true);
             if (to_i > from_i) {
             m_substr= get_substring(from_i, to_i, const_strCRLFQ);
             m_data.add(data_count, m_substr);
             data_count++;
             }
             from_i = to_i + 1;
         } while (to_i < m_read_line.length());
      return m_data.size();
    }

     private int read_cont() {
         int retval = 0;

         if (containsData()) {retval = intData();}
         if (containsHeader()) {retval = intHeader();}
         if (containsUnits()) {retval = intUnits();}
         
         if (retval == intData()) { read_cont_data();} 
         if (retval == intHeader()) { read_header();} 
         if (retval == intUnits()) { read_units();} 
     
         return retval;
     }

     private int read_cont_data() {
         int data_count = 0;
         int to_i = 0;
         int from_i = 0;
         List<String> l1 = new ArrayList<String>();
         List<String> l2 = new ArrayList<String>();
         l1.addAll(m_data);
         m_data = new ArrayList<String>();
         do {
             to_i = find_next(from_i, ",", false, true);
             if (to_i > from_i) {
             m_substr = get_substring(from_i, to_i, const_strCRLFQ);
             if (m_substr.equals("<CONT>")) {
                 m_substr="";}
             l2.add(data_count, m_substr);
             data_count++;
             }
             from_i = to_i + 1;
         } while (to_i < m_read_line.length());
      merge_cont (l1, l2);
      return m_data.size();
    }

     private void merge_cont(List<String> l1, List<String> l2) {
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
               m_data.add(s3);
            }
            for (i=l1.size(); i<l2.size(); i++) {
                m_data.add(l2.get(i));
            }
    }

   
    private int read_header() {
          int from_i = 0;
          int to_i = 0;
          do {
                to_i = find_next(from_i, ",", true, true);
                m_substr = get_substring(from_i, to_i, const_strCRLFQ);
                if (m_substr.length()> 0) {
                m_headers.put(m_substr, m_headers.size());
                }
                from_i = to_i + 1;
           } while (to_i < m_read_line.length());

          return m_headers.size();
  
 }
    private String get_substring(int from_i, int to_i, String NotAllowed) {
         m_sb = new StringBuilder();
            for (int i=from_i; i<to_i; i++) {
                m_ch = m_read_line.subSequence(i, i+1);
                if (NotAllowed.contains(m_ch)== false) {
                        m_sb.append(m_ch);
                    }
            }
        m_substr = m_sb.toString();
        return m_substr;
    }

            
    private int find_next(int from_i, String find_ch, boolean ignore_quotes, boolean returnEOS) {
        boolean in_quote = false;
        int i;
        for (i=from_i; i<m_read_line.length(); i++) {
            m_ch = m_read_line.subSequence(i, i+1);
            if (ignore_quotes == false ){
                if (m_read_line.charAt(i) == '"' ) {
                    if (in_quote == false) in_quote = true;
                    else in_quote = false;
                }
             }
             if (find_ch.contains(m_ch) == true && in_quote == false) {
             return i;
             }
            if (returnEOS == true && i == m_read_line.length()) {
                return i;
            }
         }
         return i;
      }

   }