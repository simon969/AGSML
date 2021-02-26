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
    protected AGS_Header m_row; 
    protected HashMap <String, Integer> m_hm;
    protected agsml.Constants.AGS_SOURCETYPE m_sourcetype;
    
   
   
 
    
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
        setProperty(agsml.Constants.AGS_DATASOURCE,agsml.Constants.AGS_DATASOURCE_STRING);
        m_sourcetype=agsml.Constants.AGS_SOURCETYPE.STRING;
        
    }
    public void setFileDataSource (String s1) {
        setProperty(agsml.Constants.AGS_DATASOURCE,s1);
        m_sourcetype=agsml.Constants.AGS_SOURCETYPE.FILE;
    }
    
    public String FileDataSource() {
        return getProperty (agsml.Constants.AGS_DATASOURCE);
    }
    public String DataSource() {
        if (m_sourcetype==agsml.Constants.AGS_SOURCETYPE.FILE) {
            return getProperty(agsml.Constants.AGS_DATASOURCE);
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
        log.severe(e.getMessage());
        }
    }    
    public void getFileDataSource () {
        try {
         String m_fname = getProperty (agsml.Constants.AGS_DATASOURCE);
         m_fr = new FileReader(m_fname);
         init_BufferedReader(new BufferedReader(m_fr));
         }
        catch (IOException e) {
        // catch possible io errors from readLine()
        log.severe(e.getMessage());
        } 
    }
    public boolean NewBufferedReader() {
          init_BufferedReader(new BufferedReader(m_fr));
          return true;
    }

    protected void init_BufferedReader(BufferedReader br){
        m_br = br;
        m_row = new AGS_Header();
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
    
    class AGS_Header extends Header {
        protected String HoleId_fieldName;
        protected String depthTOP_fieldName;
        protected String depthBASE_fieldName;
        protected int col_holeid = -1;
        protected int col_depthTOP = -1;
        protected int col_depthBASE = -1;
        public AGS_Header(){}
        public AGS_Header (String[] Columns) {
            super(Columns);
        }
        public void setHoleId_fieldName(String s1, int id) {
           HoleId_fieldName = s1;
           col_holeid = id;
        }
        public void setDepthTOP_fieldName(String s1, int id) {
           depthTOP_fieldName = s1;
           col_depthTOP = id;
        }
        public void setDepthBASE_fieldName(String s1, int id) {
            depthBASE_fieldName = s1;
            col_depthBASE = id; 
        }
        public int get_col_HoleId () {return col_holeid;}
        public int get_col_depthTOP () {return col_depthTOP;}
        public int get_col_depthBASE () {return col_depthBASE;}
        
        public int setHoleId_fieldName(String s1) {
            
           HoleId_fieldName = s1;     
           col_holeid = this.FindColumn(s1);
           return col_holeid;
        }
        public int setDepthBASE_fieldName(String s1) {
           int i = this.FindColumn(s1);
           if (i >=0 ) {
             col_depthBASE = i;
             depthBASE_fieldName = s1;
             return i;
           }
           return -1; 
        }
        public int setDepthTOP_fieldName(String s1) {
           int i = this.FindColumn(s1);
            if (i >= 0) {
            col_depthTOP = i;
            depthTOP_fieldName = s1;
            return  i;
           }
           return -1;
           
        }

   }     
   public AGS_ReaderLine.LineType ReadLine(){return AGS_ReaderLine.LineType.Error;};
   public AGS_ReaderLine.LineType NextLine(){return AGS_ReaderLine.LineType.Error;};
     
   public String getAllDataForHole(String HoleId) {
       
       StringBuilder sb =  new StringBuilder();
       AGS_ReaderLine.LineType lt = AGS_ReaderLine.LineType.Empty;
       String tableName = "";
       String tableHeader ="";
       String tableUnit = "";
       String tableType = "";
       
       Boolean in_table = false;
        
       do {
            lt = ReadLine();
            switch (lt) {
                case tableHeader:
                    tableHeader = m_read_line;
                    break;
                case tableName:
                    tableName = m_read_line;
                    break;
                case tableUnits:
                    tableUnit = m_read_line;
                    break;
                case tableType:
                    tableType = m_read_line;
                    break;
                case rowData: 
                    if (m_read_line.contains(HoleId)) {
                        if (in_table == false) {
                            sb.append(tableName + "\r\n");
                            sb.append(tableHeader + "\r\n");
                            sb.append(tableUnit + "\r\n");
                            sb.append(tableType + "\r\n");
                        }
                        sb.append(m_read_line +  "\r\n");
                        in_table = true;
                    }
                    break;
                case Empty:
                    in_table = false;
                    tableName = "";
                    tableHeader = "";
                    tableUnit = "";
                    tableType = "";
                    sb.append("\r\n");
                    
                case Error:
                case EOF:
                    break;
                default:
                    sb.append(m_read_line +  "\r\n");
            }
        
            lt = NextLine();
        
       } while (lt!=AGS_ReaderLine.LineType.EOF);
       
       return sb.toString();
   }
}
