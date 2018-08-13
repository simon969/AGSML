package agsml;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Types;
import java.util.HashMap;
/**
 *
 * @author thomsonsj
 */
class AGS_Base extends Base_Class{
    
    protected FileReader m_fr;
    protected CharSequence m_ch;
    protected BufferedReader m_br;
    protected String m_ags_data; 
    protected int m_linecounter;
    protected String m_read_line;
    protected String m_next_line;
    protected String m_substr;
    protected StringBuilder m_sb;
    protected String m_NL;
    protected String m_tablename;
    protected Header m_row; 
    protected HashMap <String, Integer> m_hm;
    protected AGS_SOURCETYPE m_sourcetype;
    
    public final static String AGS_DATASOURCE = "AGSDataSource"; 
   
    public enum AGS_SOURCETYPE {
        FILE {
            public String toString() {
            return "FILE";
            }
        },
        STRING {
            public String toString() {
            return "STRING";
            }
        }
}
    
    public AGS_Base () {
    }
    
    public AGS_Base (BufferedReader br1) {
        init_BufferedReader(br1);
    }
    
    public AGS_Base (String s1) {
        File f = new File (s1);
        if (f.isFile()) {
            setFileDataSource(s1);
            getFileDataSource();
        } else {
            setStringDataString();
            getStringDataSource(s1);
        }         
    }
 
    public void setDataSource(String s1){
        File f = new File (s1);
        if (f.isFile()) {
            setFileDataSource(s1);
        } else {
            setStringDataString();
           m_ags_data = s1;
        }         
    }
    
    public void setStringDataString(){
        setProperty(AGS_DATASOURCE,"AGS_STRING");
        m_sourcetype=AGS_SOURCETYPE.STRING;
        
    }
    public void setFileDataSource (String s1) {
        setProperty(AGS_DATASOURCE,s1);
        m_sourcetype=AGS_SOURCETYPE.FILE;
    }
    
    public String FileDataSource() {
        return getProperty (AGS_DATASOURCE);
    }
    public String DataSource() {
        if (m_sourcetype==AGS_SOURCETYPE.FILE) {
            return getProperty(AGS_DATASOURCE);
        } else
            return m_ags_data;
    }
    public void getStringDataSource (String s1) {
        try {
         m_ags_data = s1;
         ByteArrayInputStream bis = new ByteArrayInputStream(s1.getBytes());
         init_BufferedReader(new BufferedReader(new InputStreamReader(bis)));
         }
        catch (Exception e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        }
    }    
    public void getFileDataSource () {
        try {
         String m_fname = getProperty (AGS_DATASOURCE);
         m_fr = new FileReader(m_fname);
         init_BufferedReader(new BufferedReader(m_fr));
         }
        catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        } 
    }
    public boolean NewBufferedReader() {
          init_BufferedReader(new BufferedReader(m_fr));
          return true;
    }

    protected void init_BufferedReader(BufferedReader br){
        m_br = br;
        m_row = new Header();
        m_tablename = new String();
        m_read_line = new String();
        m_next_line = new String();
     //   const_strCRLFQ = "\r\"\n";
        m_linecounter = 0;
    }
    
    protected String get_substring(int from_i, int to_i, String NotAllowed) {
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

            
    protected int find_next(int from_i, String find_ch, boolean ignore_quotes, boolean returnEOS) {
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
