package agsml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.logging.Level;
import org.w3c.dom.NamedNodeMap;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.lang.StringBuilder;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author thomsonsj
 */
class Base_Class {
    protected HashMap<String, String> m_props;
    protected Logger m_log;
    protected  final String const_Blank = "\\s+";
    protected  final String const_Empty = "";
    
    public Base_Class(){
     Object obj = this;
     m_log = Logger.getLogger( obj.getClass().getName());
     m_log.setLevel(Level.ALL);
     m_props = new HashMap<String, String>();
    }
    
    public void setLogger(Logger log) {
        m_log = log;
    }

 public void setProperty (String Name, String Value ) {
        
 m_props.put(Name, Value);
 }
 
 public void log_INFO(String message) {
     m_log.log(Level.INFO, message);
 }
 public String getProperty (String name) {
     return getProperty(name,"");
 }
 public String getProperty (String name, String valueIfEmpty) {
     try {
          String s1 = m_props.get(name);
          if (s1 == null) {
              throw new Exception (name + " not found in property bag");
          }
          return s1;
     }
     catch (Exception e) {
 //    m_log.log(Level.INFO, e.getMessage());
     return valueIfEmpty;
     }
 }
 public  boolean getProperty_bool(String name) {
     
    String s1 = getProperty (name);
     
    switch (s1.toUpperCase()) {
        case "1": 
        case "Y": case "YES":
        case "T": case "TRUE" : 
                return true;
         case "0": case "":
         case "N": case "NO":  
         case "F": case "FALSE": 
                return false;
     }
    return false;
 }
 

 
 public int getProperty_int(String name) {
     return getProperty_int(name,0);
 }
 public int getProperty_int (String name, int valueIfEmpty) {
     String s1 = getProperty(name);
     if (s1.isEmpty()) {
         return valueIfEmpty;
     }
     return Integer.valueOf(s1);
 }
  public float getProperty_float (String name, float valueIfEmpty) {
      String s1 = getProperty(name);
     if (s1.isEmpty()) {
         return valueIfEmpty;
     } 
     return Float.valueOf(getProperty(name));
 }
  public float getProperty_float(String name) {
     return getProperty_float(name,0);
 } 
  
 protected void setProperties  (NamedNodeMap nodeAttribs) {
     for (int j = 0; j < nodeAttribs.getLength(); j++) {
         m_props.put(nodeAttribs.item(j).getNodeName(),nodeAttribs.item(j).getNodeValue());
     }
 }
 protected void setProperties  (HashMap<String, String> hm) {
     for (String key : hm.keySet()) {
         m_props.put(key, hm.get(key));
     }
 }
 
 protected void passProperties(Node n1) {
     Element e1 = (Element) n1;
             
     for (Map.Entry<String, String> entrySet : m_props.entrySet()) {
             String key = entrySet.getKey();
             String value = entrySet.getValue();
                e1.setAttribute(key, value);
                }
 }
 
 final static String getAttributeValue(Node n1, String name) {
  if (n1.getNodeType() == Node.ELEMENT_NODE) {
        Element e = (Element) n1;
        return e.getAttribute(name);
  }
  return "";
  } 
 
final static  String getSubNodeValue(Node node, String nName ) {

try {
     NodeList nodeList = node.getChildNodes();
     for (int i = 0; i < nodeList.getLength(); i++) {
         Node currentNode = nodeList.item(i);
          if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
            String nodeName = currentNode.getNodeName();
                if (nodeName == nName) {
                 return currentNode.getTextContent();
                }
          }
     }
    return "";
 
} catch (Exception e) {
    return "";
}
}

final static Node getSubNode(Node node, String nName ) {

try {
     NodeList nodeList = node.getChildNodes();
     for (int i = 0; i < nodeList.getLength(); i++) {
         Node currentNode = nodeList.item(i);
          if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
            String nodeName = currentNode.getNodeName();
                if (nodeName == nName) {
                 return currentNode;
                }
          }
     }
    return null;
 
} catch (Exception e) {
    return null;
}
}
public static boolean empty( final String s ) {
  // Null-safe, short-circuit evaluation.
  return s == null || s.trim().isEmpty();
}
}

 class Header extends Base_Class {
    protected String m_tableIN;
    protected String m_tableOUT;
    protected List<String> m_columnsIN;
    protected List<String> m_columnsOUT;
    protected List<Integer> m_id;
    protected List<String> m_data; 
    protected List<String> m_units;
    protected List<String> m_types;
    
    public Header(){
    m_columnsIN = new ArrayList<String>();
    m_columnsOUT = new ArrayList<String>();
    m_types = new ArrayList<String>();
    m_data = new ArrayList<String>();
    m_units = new ArrayList<String>();
    m_id = new ArrayList<Integer>();
    }  
    public Header (String[] Columns) {
       this(Columns, 16);
    }
     public int AddColumn (String columnNameIN, String columnNameOut, String type, String data, String units) {
         int id = m_columnsIN.size() + 1;
         return AddColumn(columnNameIN, columnNameOut, type, data,units, id);
    }
    public void set_tableIN(String s1) {
        m_tableIN = s1;
    }
    public void set_tableOUT(String s1) {
        m_tableOUT = s1;
    }
    public int AddColumn (String columnNameIN, String columnNameOut, String type, String data, String units, int id) {
       
        m_columnsIN.add (columnNameIN);
        m_columnsOUT.add (columnNameOut);
        m_types.add(type);
        m_data.add(data);
        m_units.add(units);
        m_id.add (id);
        return m_columnsIN.size();
    }
   
    public int AddColumn (String ColumnName, int ColumnType) {
       return AddColumn(ColumnName,"",String.valueOf(ColumnType),"","");
            
    }
   public int FindColumn(String ColumnName) {
       
       for (int i = 0 ; i < m_columnsIN.size(); i++ ) {
           if (ColumnName == m_columnsIN.get(i)) {
              return i;
           }
       }
       return -1;
   }
     public Header (String[] ColumnsIN, int constType) {
        this();
        try {
            for ( int i = 0 ; i < ColumnsIN.length ; i++ ) {
              AddColumn(ColumnsIN[i],"",String.valueOf(constType),"","");
            } 
        } catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());  
        }
    }
    public Header (String[] ColumnsIN, int[] Types) {
        this();
        String field;
        int type;
        
        try {
            for ( int i = 0 ; i < ColumnsIN.length ; i++ ) {
               AddColumn(ColumnsIN[i],"",String.valueOf(Types[i]),"","");
            } 
        } catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());  
        }
    }
    public Header (ResultSetMetaData md) {
        
     this();
    
     try {
         
         int count = md.getColumnCount(); //number of column
        // ResultSetMetaData indices start from 1
            for (int i = 1; i <= count; i++)
                {
                    String h1 = md.getColumnLabel(i);
                    int c1 =  md.getColumnType(i); 
                    if (h1.contains(" ")) {
                       String h2 = h1;
                       h1 = h1.replaceAll(const_Blank,const_Empty);
                       m_log.log(Level.INFO, "Blanks removed from table header [" + h2 + "]=[" + h1 + "]");
                    }
                 AddColumn(h1,"", String.valueOf(c1),"",""); 
            }
        }
     
             catch(SQLException e) {
             m_log.log(Level.SEVERE, e.getMessage());
      }     
    } 
   
    public void setData(String[] data) {
        m_data = new ArrayList<String>(data.length); 
        try {
            for ( int i = 0 ; i < data.length ; i++ ) {
             m_data.add(i, data[i]); 
            } 
        } catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());  
        }
        
    }
    public void setUnits(String[] units) {
        m_units = new ArrayList<String>(units.length); 
        try {
            for ( int i = 0 ; i < units.length ; i++ ) {
             m_units.add(i, units[i]); 
            } 
        } catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());  
        }
        
    }
        public void setTypes(String[] types) {
        String SQLType;
        m_types = new ArrayList<String>(types.length); 
        try {
            for ( int i = 0 ; i < types.length ; i++ ) {
            SQLType = getFieldType(types[i]).toString();
            m_types.add(i, SQLType); 
            } 
        } catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());  
        }
        
    }
    
    public String getFieldType(String s) {
            return "";
    }
   
    public List<String> newData() {
     return newData(m_columnsIN.size());
    }
    public List<String> newUnits() {
     return newUnits(m_columnsIN.size());
    }
    public List<String> newTypes() {
     return newTypes(m_columnsIN.size());
    }
    private List<String> newData(int size) {
      m_data = new ArrayList<String>(size);
      return m_data;
    }
    private List<String> newUnits(int size) {
      m_units = new ArrayList<String>(size);
      return m_units;
    }
    private List<String> newTypes(int size) {
      m_types = new ArrayList<String>(size);
      return m_types;
    }
    public Collection<String> typeCollection() {
       return m_types;  
    }
    public Collection<String> columnINCollection() {
       return m_columnsIN;  
    }
    
    public List<String> type() {
       return m_types;  
    }
    public List<String> columnIN(){
       return m_columnsIN;
    }
    public List<String> data() {
       return m_data;  
    }
    public Collection<String> dataCollection() {
       return m_data;  
    }
    public List<String> units() {
       return m_units;  
    }
    public Collection<String> unitsCollection() {
       return m_units;  
    }
    public String header_toString(String delimeter, boolean AddQuotes){
        return toString (m_columnsIN, delimeter,AddQuotes);
    }
    public String data_toString(String delimeter, boolean AddQuotes){
        return toString (m_data, delimeter,AddQuotes);
    }
    public String units_toString(String delimeter, boolean AddQuotes){
        return toString (m_units, delimeter,AddQuotes);
    } 
    public String type_toString(String delimeter, boolean AddQuotes){
        return toString (m_types, delimeter,AddQuotes);
    } 
    private String toString(List al, String Delimeter, boolean AddQuotes) {
        StringBuilder sb1 = new StringBuilder();
        Iterator itr = al.iterator();
        String QuoteMark = "";
        
        if (AddQuotes) {
            QuoteMark = "\"";
        }
      
        while(itr.hasNext()) {
         Object e1 = itr.next();
         if (sb1.length() == 0) {
           sb1.append(QuoteMark + e1 + QuoteMark); 
         } else {
           sb1.append(Delimeter + QuoteMark + e1 + QuoteMark);
           }
//         System.out.print(e1 + " ");
      }
        return sb1.toString();
 } 
    public String get_Type(int i) {
        return m_types.get(i);
    }
    public String get_ColumnIn(int i) {
        return m_columnsIN.get(i);
    }
    public String get_ColumnOut(int i) {
        return m_columnsOUT.get(i);
    }
    public String get_Data(int i) {
        return m_columnsOUT.get(i);
    }
    public String get_Units(int i) {
        return m_columnsOUT.get(i);
    }
    public int get_Id(int i) {
        return m_id.get(i);
    }
}


