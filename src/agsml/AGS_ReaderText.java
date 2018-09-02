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
import java.util.Iterator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;

class AGS_ReaderText extends AGS_Base {
//    private static final Logger log = Logger.getLogger(.class.getName() );
    private int m_i;
    private int m_eoh;
    private CharSequence m_ch;
    private StringBuilder m_sb;
    private String m_table_name;
    private String m_str;
    private String m_substr;
    private boolean m_data_end;
   
    private LinkedHashMap<String, Integer> m_tables;
    private LinkedHashMap<String, Integer> m_headers;
    private LinkedHashMap<String, Integer> m_units;
    private List<String> m_rowdata;
    private List<String> m_ags;
    
    private Collection m_coll_tables;
    private Iterator m_iter_tables;
    
    protected final String const_strCRLFQ = "\r\"\n";
    protected final String const_strCRLF= "\r\n";
    
    protected final String const_TableIdentifierAGS3 = "**";
    protected final String const_HeaderIdentifierAGS3 = "*";
    
    protected final String const_TableIdentifierAGS4 = "GROUP";
    protected final String const_HeaderIdentifierAGS4 = "HEADING";
    
    protected AGS_Dictionary.AGSVersion ags_version = AGS_Dictionary.AGSVersion.AGS31a;
    
    public AGS_ReaderText(String str){

    m_str = str;
    String lines[] = m_str.split("\\n");
    m_ags = Arrays.asList(lines);
    m_tables = new LinkedHashMap<String, Integer>();
    m_rowdata = new ArrayList<String>();
    
    findAGSVersion();
    find_tables();

    }
    public AGS_Dictionary.AGSVersion findAGSVersion() {
     
        ags_version=AGS_Dictionary.AGSVersion.NONE;
     
        for (int i=0; i < m_ags.size(); i++) {
           String line = m_ags.get(i);
           if (line.contains("*HOLE_ID")){
               ags_version = AGS_Dictionary.AGSVersion.AGS31a;
               break;
           }
           if (line.contains("LOCA_ID")){
               ags_version = AGS_Dictionary.AGSVersion.AGS404;
               break;
           }
        }
        return ags_version;
    }
    
    public AGS_Dictionary.AGSVersion AGSVersion() {
        return ags_version;    
    }
    
    private void readFile(String filename) {
        m_ags = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null){
            sb.append(line);    
            m_ags.add(line);
            }
            reader.close();
            m_str = sb.toString();
            
            m_tables = new LinkedHashMap<String, Integer>();
            m_rowdata = new ArrayList<String>();
            findAGSVersion();
            find_tables();
        }
        catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
        }
    }
    
    public int table_count(){
        return m_tables.size();
    }

    public Collection get_headers() {
        Collection c = m_headers.keySet();
        return c;
}
    public Collection get_tables(){
        Collection c = m_tables.keySet();
        return c;
    }
        public Collection get_units(){
        Collection c = m_units.keySet();
        return c;
    }
    public List first_data() {
        m_data_end = false;
        get_data (m_eoh);
        return m_rowdata;
    }
    private int get_data (int from_i) {
         m_rowdata = new ArrayList<String>();
         int header_count;
         int data_count=0;
         int to_i;
         String data;
         CharSequence ch;
         header_count =  m_headers.size();
         do {
             to_i = find_next(from_i, ",\r", false, true);
             data = get_substring(from_i, to_i, const_strCRLFQ);
             if (data.length() > 1 ) {
                 ch = data.subSequence(0, 2);
                  if (const_TableIdentifierAGS3.contains(ch)){
                    m_data_end = true;
                    m_i = from_i;
                }
             }

             if (m_data_end == false ) {
                m_rowdata.add(data);
                data_count++;
                from_i = to_i + 1;
                m_i = from_i;
             }
              if (to_i == m_str.length()) {
                m_data_end = true;
              } 

         } while (data_count < header_count && m_data_end == false);
         
        return 1;
    }
    private String check_CONT(int from_i, int data_id) {
       int to_i;
       String str;
             to_i = find_next(from_i, ",", false, true);
             str = get_substring(from_i, to_i, "");


       return str;

    }
    public List next_data() {
        if (m_data_end == false){
        get_data (m_i);
        }
        return m_rowdata;
    }
    public boolean data_end(){
        return m_data_end;
    }
    public boolean next_table(){
    if (m_iter_tables.hasNext()) {
        m_table_name = m_iter_tables.next().toString();
        find_headers();
        find_units();
        return true;
       } else { return false;}
    }
    public int find_headers() {
        
        if (ags_version.toInt() <= AGS_Dictionary.AGSVersion.AGS31a.toInt()) {
           return find_headersAGS3();
        };
        
        if (ags_version.toInt() > AGS_Dictionary.AGSVersion.AGS31a.toInt()) {
           // return find_headersAGS4();
           return -1;
        };
        
        return -1;
    }
    
    public int find_tables() {
        if (ags_version.toInt() <= AGS_Dictionary.AGSVersion.AGS31a.toInt()) {
           return find_tablesAGS3();
        };
        if (ags_version.toInt() > AGS_Dictionary.AGSVersion.AGS31a.toInt()) {
           // return find_tablesAGS4();
           return -1;
        };
        return -1;
    }
    public int first_table(){
      m_coll_tables = m_tables.keySet();
      m_iter_tables = m_coll_tables.iterator();
      next_table();
      return 1;

    }
    public String table_name(){
        return  m_table_name;
    }

    private int find_tablesAGS3() {
        CharSequence ch;
        int from_i;
        int to_i;
        for (m_i=0; m_i<m_str.length()-2; m_i++) {
            ch = m_str.subSequence(m_i, m_i+2);
            if (const_TableIdentifierAGS3.contains(ch)) {
                from_i = m_i;    
                to_i = find_next(from_i,"\r",true,true);
                 m_substr = get_substring(from_i, to_i,const_strCRLFQ);
                 m_tables.put(m_substr, m_i);
               }
        }
        return m_tables.size();
   }
   
    private int find_headersAGS3() {
           CharSequence ch1;
           CharSequence ch2;
           m_headers = new LinkedHashMap<String, Integer>();
           int from_i = m_tables.get(m_table_name);
           int to_i;
           boolean header_end = false;
           
           // find line feed after table name
           from_i = find_next(from_i, "\r", true, true) + 1;
           
           do {
                to_i = find_next(from_i, ",\r", true, true);
                m_eoh = from_i;
                m_substr = get_substring(from_i, to_i, const_strCRLFQ);
                if (m_substr.length() > 0) {
                   ch1 = m_substr.subSequence(0, 1);
                   ch2 = m_substr.subSequence(0, 2);
                     if (const_TableIdentifierAGS3.contains(ch2)) {
                            header_end = true;
                     }
                     else {
                           if (const_HeaderIdentifierAGS3.contains(ch1)) {
                             m_headers.put(m_substr, from_i);
                           }
                           else {
                             header_end = true;  
                           }
                    }
                }
                 from_i = to_i + 1;
            } while (header_end == false);

            return m_headers.size();
           
 }
   private int find_units(){
       m_units= new LinkedHashMap<String, Integer>();

       return m_units.size();
   }
//    private String get_substring(int from_i, int to_i, String NotAllowed) {
//        m_sb = new StringBuilder();
//            for (int i=from_i; i<to_i; i++) {
//                m_ch = m_str.subSequence(i, i+1);
//                if (NotAllowed.contains(m_ch)== false) {
//                        m_sb.append(m_ch);
//                    }
//            }
//        m_substr = m_sb.toString();
//        return m_substr;
//    }
//
//            
//    private int find_next(int from_i, String find_ch, boolean ignore_quotes, boolean returnEOS) {
//        boolean in_quote = false;
//        int i;
//        for (i=from_i; i<m_str.length(); i++) {
//            m_ch = m_str.subSequence(i, i+1);
//            if (ignore_quotes == false ){
//                if (m_str.charAt(i) == '"' ) {
//                    if (in_quote == false) in_quote = true;
//                    else in_quote = false;
//                }
//             }
//             if (find_ch.contains(m_ch) == true && in_quote == false) {
//             return i;
//             }
//            if (returnEOS == true && i == m_str.length()) {
//                return i;
//            }
//         }
//         return i;
//      }

   }