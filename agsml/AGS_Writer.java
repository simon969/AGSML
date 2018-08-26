package agsml;

import java.io.BufferedWriter;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
/**
 *
 * @author thomsonsj
 */
class AGS_Writer extends Base_Writer {
    
     private BufferedWriter m_bw;
     private StringBuilder m_sb;  
     protected int m_linecount;
     protected static String m_NL = System.getProperty("line.separator");
     protected static String m_Quote = "\"";
     protected static String m_Delimeter = ",";
  
     public int addLine (String s1) {
    try {
        m_sb.append(s1 + m_NL);
        m_linecount++;
     return m_linecount;    
    }
   
    catch (Exception e) {
    return -1;         
    }
 } 
  
 public int addLine (Collection  c, boolean AddEmptyData) {
  try {
           Iterator d_it;
           String d1;
           d_it = c.iterator();
           
           do {
               d1 = d_it.next().toString();
               if (d1 == null) d1= "";
                if (!(d1.length() == 0 && AddEmptyData == false)){
                
                }
            } while(d_it.hasNext());
          
    return 1;
 }
    catch (Exception e) {
    return -1;
    }  
     
 }   
}
