package agsml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;
/**
 *
 * @author thomsonsj
 */
public class AGS_Base {
    protected Logger m_log;
    protected String m_tablename;
    protected LinkedHashMap<String, Integer> m_headers;
    protected List<String> m_data;
    
    public AGS_Base(){
    m_headers = new LinkedHashMap<String, Integer>();
    m_data = new ArrayList<String>();
}
    public void setLogger(Logger log) {
        m_log = log;
    }
}

