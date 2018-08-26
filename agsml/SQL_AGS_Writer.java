package agsml;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.lang.StringBuilder;

/**
 *
 * @author thomsonsj
 */
class SQL_AGS_Writer extends AGS_Writer {
 
     
   protected int addRow(Collection header, ResultSet rs, boolean AddEmptyData) {
      
    try {
           Iterator h_it;
           String h1;
           String d1;
           int colid=0;
           StringBuilder sb = new StringBuilder();
           h_it = header.iterator();
  
           do {
               colid++; 
               h1 = h_it.next().toString();
               d1 = rs.getString(colid);
               if (d1 == null) d1= "";
                if (!(d1.length() == 0 && AddEmptyData == false)){
                    if (sb.length()==0) {
                         sb.append(m_Quote + d1 + m_Quote);   
                    }  else {
                        sb.append(m_Delimeter + m_Quote + d1 + m_Quote);
                    }
                }
            } while(h_it.hasNext());
          
    return 1;        
    }
    catch (SQLException e) {
    return -1;
    }
 }  
}
