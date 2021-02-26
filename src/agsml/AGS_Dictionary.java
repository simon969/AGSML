/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agsml;
import java.util.HashMap;
import java.util.logging.Level;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import agsml.Constants;

/**
 *
 * @author thomsonsj
 */
public class AGS_Dictionary extends XML_DOM {
    

    
    private HashMap<String, Node> m_hmtable;
        
    private Node m_table;
    private Node m_tableSQL;
    private Node m_tableAGS;
    private Node m_tableXML;
    private Node m_tableGINT;
    
    private Node m_field;
    private Node m_fieldSQL;
    private Node m_fieldAGS;
    private Node m_fieldXML;
    private Node m_fieldGINT;
    
    public agsml.Constants.AGSVersion  getAGSVersion() {
    
        String s1 = getAttributeValue(m_root,agsml.Constants.AGS_ATTRIBUTE_AGSVERSION);
        if (s1 !=null) {
            return agsml.Constants.getAGSVersion(s1);
        } 
        return agsml.Constants.AGSVersion.NONE;
    }
      
    public double version(String language) {
        
        String s1 = "0.0";
                       
        Node n1 = findSubNode(m_root, agsml.Constants.AGS_VERSION_TAG);
        
        if (n1 == null) {
            return -1;
        }
    
        NodeList nl1 = n1.getChildNodes();

            for (int i = 0; i < nl1.getLength(); i++) {
               n1 = nl1.item(i);
               if (n1.getNodeType() == Node.ELEMENT_NODE) {
                   String s2 = n1.getNodeName();
                   if (s2.equalsIgnoreCase(language)) {
                     Element e1 = (Element) n1;
                     s1 = e1.getTextContent();
                     return Double.valueOf(s1);
                   } 
               }
            } 
        return -1;  
    }
    
    public Node setAGSVersion (agsml.Constants.AGSVersion req_version) {
         
        Node current_version = m_root;
        agsml.Constants.AGSVersion found_version;
        
        NodeList nl1 = m_doc.getElementsByTagName(agsml.Constants.AGS_DICTIONARY_TAG);
        
        for (int i = 0; i < nl1.getLength(); i++) {
           Node n1 = nl1.item(i);
            if (n1.getNodeType() == Node.ELEMENT_NODE) { 
                setRoot(n1);
                found_version = getAGSVersion();
                if (found_version==req_version) {
                    return m_root;
                }
           }    
        }
        // version not found reset m_root to current_version node
        m_root = current_version;
        return null;
    }
    public AGS_Dictionary () {
    }
    public AGS_Dictionary (XML_DOM xml_dom) throws Exception{
        super(xml_dom, agsml.Constants.AGS_DICTIONARY_TAG);
        setDictionaryID(agsml.Constants.AGS_DICTIONARYID_DEFAULT); 
   }   
    public AGS_Dictionary (String fXMLFile) throws Exception {
        super (fXMLFile,fileState.MUSTEXIST);
        setDictionaryID(agsml.Constants.AGS_DICTIONARYID_DEFAULT);
    }
            
   public AGS_Dictionary (String fXMLFile, String id) throws Exception {
        super (fXMLFile,fileState.MUSTEXIST);
        m_hmtable = new HashMap<String, Node>();
        setDictionaryID(id);
    }
  
   private int indexTable() {
     int count = 0;
     try {
            m_hmtable = new HashMap<String, Node>();
            first_table();
            do {
                m_hmtable.put(tableGEN_name(), m_table);
                count++;
            }  while( next_table() != null);
     return count++;
     }
     
       
    catch (Exception e) {
        return count;
    }  
   }
   
   public Node setDictionaryID (String id) throws Exception {
     
   try {
           Node n1 = setRoot(agsml.Constants.AGS_DICTIONARY_TAG, "id", id);
           if (n1 == null) throw new Exception ("Failed set " + agsml.Constants.AGS_DICTIONARY_TAG + " node, unable to find <" + agsml.Constants.AGS_DICTIONARY_TAG + " id=" + id + ">");
           return n1;
        } 
       catch (Exception e) {
           log.log(Level.SEVERE, id);
           return null;
        }
    }
   
   public String DictionaryAGSMLTag(){
       return agsml.Constants.AGS_DICTIONARY_TAG;
   }
   public String field_name(agsml.Constants.Lang lang) {
       switch (lang) {
           case GEN:
            return find_attribute_value(m_field, "name");
           case AGS:
            return find_node_value(m_fieldAGS, "name");
           case GINT:
            return find_node_value(m_fieldGINT, "name");   
           case XML:
            return find_node_value(m_fieldXML, "name");
            
       }
       return "";
   }
   public String fieldGEN_name(){
        return find_attribute_value(m_field, "name");
    }
    public  String fieldSQL_name(){
        return find_node_value(m_fieldGINT,"name");
   }
   public  String fieldGINT_name(){
        return find_node_value(m_fieldGINT,"name");
    }
    public  String fieldGINT_type(){
        return find_node_value(m_fieldGINT,"type");
    }
   public  String fieldGINT_length(){
        return find_node_value(m_fieldGINT,"length");
    }
   public  String fieldAGS_name(){
       String s1 =  find_node_value(m_fieldAGS, "name");
       return s1;
    }
    public  String fieldXML_name(){
        return find_node_value(m_fieldXML, "name");
    }
    
    public String table_name(agsml.Constants.Lang lang) {
       switch (lang) {
           case GEN:
            return find_node_value(m_table, "name");
           case AGS:
            return find_node_value(m_tableAGS, "name");
           case GINT:
            return find_node_value(m_tableGINT, "name");   
           case SQL:
            return find_node_value(m_tableSQL, "name");      
           case XML:
            return find_node_value(m_tableXML, "name");    
       }
       return "";
   }
    
    public  String tableGINT_name(){
        return find_node_value(m_tableGINT, "name");
    }
   public String tableAGS_name(){
        return find_node_value(m_tableAGS, "name");
    }
    public  String tableXML_name(){
        return find_node_value(m_tableXML, "name");
    }
   public  String tableGEN_name(){
        return find_attribute_value(m_table, "name");
    }
    
//   public String getTableNameAGS  (String t1name, ) {
//    if (table2_name() != t2name) {
//      if (find_table2 (t2name) != null ) {
//          return find_Value(m_table1, "name"); 
//      } else { 
//          return null;
//      }
//    }  
//   return find_Value(m_table1, "name"); 
//    }
//    
//   public String lookupTable2 (String t1name) {
//       
//   if (table1_name() != t1name) {
//        if (find_table1(t1name) !=null) {
//           return find_Value(m_table2, "name");  
//        } else {
//           return null; 
//        }
//    } 
//    return find_Value(m_table2, "name");
//    }
//   
//    public String lookupField2 (String t1name, String f1name) {
//    if (find_Value(m_table1, "name").equals(t1name)!=true) {
//       find_table1(t1name);
//    }
//    if (find_field1(f1name) != null){
//       return find_Value(m_field2, "name");
//    }
//    return null;
//}
//   public String lookupValue2 (String t1name, String f1name, String v2name) {
//    if (find_Value(m_table1, "name").equals(t1name)!=true) {
//       find_table1(t1name);
//    }
//    if (find_field1(f1name) != null) {
//    return find_Value(m_field2, "name");
//    }
//    return null; 
//   }
//   public String lookupValue1 (String t2name, String f2name, String v1name) {
//    if (find_Value(m_table2, "name").equals(t2name)!=true) {
//       find_table1(t2name);
//    }
//    if (find_field1(f2name) != null) {
//    return find_Value(m_field2, v1name);
//    }
//    return null;
//   }
//    public String lookupField1 (String t2name, String f2name) {
//    if (find_Value(m_table2, "name").equals(t2name)!=true) {
//       find_table1(t2name);
//    }
//    if (find_field2(f2name) != null) {
//    return find_Value(m_field1, "name");
//    }   
//    return null;
//    }
   public Node next_table(){
        Node n1 = m_table;
        Node n2 = n1.getNextSibling();
        do {
           if (n2 != null ) {
               if (n2.getNodeType() == n1.ELEMENT_NODE) {
                   if (n2.getNodeName().equals("table")) {
                        m_table= n2;
                        m_tableAGS = findSubNode(m_table, agsml.Constants.AGS_FORMAT_AGS);
                        m_tableXML= findSubNode(m_table, agsml.Constants.AGS_FORMAT_XML);
                        m_tableSQL = findSubNode(m_table, agsml.Constants.AGS_FORMAT_SQL);
                        m_tableGINT= findSubNode(m_table, agsml.Constants.AGS_FORMAT_GINT);
                        return m_table;
                    }
                }
           }   else {
               return null;
           }
        n1 = n2;
        n2 = n1.getNextSibling();
        } while (n2 != null);
                  
        return null;
    }  
   public Node first_table(){
        m_table = findSubNode(m_root, "table");
        m_tableAGS = findSubNode(m_table,agsml.Constants.AGS_FORMAT_AGS);
        m_tableXML = findSubNode(m_table, agsml.Constants.AGS_FORMAT_XML);
        m_tableSQL = findSubNode(m_table, agsml.Constants.AGS_FORMAT_SQL);
        m_tableGINT= findSubNode(m_table, agsml.Constants.AGS_FORMAT_GINT);
        return m_table;
    }
   public Node first_field(){
        m_field = findSubNode(m_table,"field");
        m_fieldAGS = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_AGS);
        m_fieldXML = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_XML);
        m_fieldSQL = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_SQL); 
        m_fieldGINT = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_GINT);
        return m_field;
    }
   private void get_fields(){
       try {
        m_fieldAGS = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_AGS);
        m_fieldXML = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_XML);
        m_fieldSQL = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_SQL);
        m_fieldGINT = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_GINT);
       }
       catch (Exception e) {
           
       }
    }

   public Node next_field(){
       try {
        
           if (m_field == null) {
            return null;
           }
        
       Node n2 = m_field.getNextSibling();;
      
        do {
            if (n2 != null) {
                if (n2.getNodeType() == Node.ELEMENT_NODE) {
                      if (n2.getNodeName().equals("field")) {
                         m_field = n2;
                         m_fieldAGS = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_AGS);
                         m_fieldSQL = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_SQL);
                         m_fieldXML = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_XML);  
                         m_fieldGINT = findSubNode(m_field ,agsml.Constants.AGS_FORMAT_GINT);  
                        return m_field;
                      }
                }
            } else {
            return null;
            }
         n2 = n2.getNextSibling();  
        } while (n2 !=null);
        return null; 
       }
       
       catch (Exception e) {
           return null; 
       }
       
    }
   private String find_attribute_value(Node n1, String s1) {
      try {
          return n1.getAttributes().getNamedItem(s1).getNodeValue();
      } catch (Exception e) {
          return null;
      }
   }
   private String find_node_value( Node n1, String s1){
    try {
        Node n2 = findSubNode(n1, s1);
        if (n2 != null) {
            return n2.getTextContent();
        } else
        
        return null;
        
    } 
    catch (Exception e) {
        return null;
    }
    }
//   private String find_tableValue(String s1){
//        return find_Value(m_table, s1);
//    }
//   private String find_table1Value(String s1){
//        return find_Value( m_table1, s1);
//    }
//   private String find_table2Value(String s1){
//        return find_Value(m_table2, s1);
//    }
//   private String find_fieldValue(String s1){
//        return find_Value( m_field, s1);
//    }
//   private String find_field1Value(String s1){
//        return find_Value(m_field1, s1);
//    }  
//   private String find_field2Value(String s1){
//        return find_Value(m_field2, s1);
//    } 
//   private Node find_table(String tname) {
//       return find_table(tname, null);
//   }
  public Node find_table(String tname, agsml.Constants.Lang l){
    try {
         first_table();
            do {
                if (l==agsml.Constants.Lang.GEN && this.tableGEN_name().equals(tname)) {
                    return m_table;
                }
                if (l==agsml.Constants.Lang.AGS && this.tableAGS_name().equals(tname)) {
                    return m_table;
                }
                if (l==agsml.Constants.Lang.GINT && this.tableGINT_name().equals(tname)) {
                    return m_table;
                }
                if (l==agsml.Constants.Lang.SQL && this.tableGINT_name().equals(tname)) {
                    return m_table;
                }
                if (l==agsml.Constants.Lang.XML && this.tableXML_name().equals(tname)) {
                    return m_table;
                } 
                      
            }   while( next_table() != null);
         return null;
    }
    
    catch (Exception e) {
        return null;
    }
       
    }
    public Node find_tableGEN(String tname){
       return find_table(tname,agsml.Constants.Lang.GEN);
     }
       
     public Node find_tableXML (String tname){
     return find_table(tname,agsml.Constants.Lang.XML);
     }
     
   public Node find_tableAGS (String tname){
     return find_table(tname,agsml.Constants.Lang.AGS);   
 }
        
   public Node find_tableGINT (String tname){
     return find_table(tname,agsml.Constants.Lang.GINT);  
}
     public Node find_field (String fname, agsml.Constants.Lang l){
     return find_field (fname, l, true);    
    }
    public Node find_field (String fname, agsml.Constants.Lang l, boolean matchExact){
        try {
            first_field();
            do {
                String s1 = field_name(l);
                 
                if (s1 != null) {
                    if (matchExact && !s1.isEmpty() && fname.contains(s1)) {
                        return m_field;    
                    }
                
                    if (!matchExact && !s1.isEmpty() && s1.contains(fname)) {
                        return m_field;  
                    }
                }
            }   while( next_field() != null);
        return null;
        }
        catch (Exception e) {
            return null;
        }
    }
   public Node find_fieldAGS (String fname){
      return find_field(fname, agsml.Constants.Lang.AGS);
   }
   public Node find_fieldAGSContains (String contains){
      return find_field(contains, agsml.Constants.Lang.AGS,false);
   }
   public Node find_fieldXML (String fname){
     return find_field(fname, agsml.Constants.Lang.XML);
   }
   public Node find_fieldXMLContains (String contains){
     return find_field(contains, agsml.Constants.Lang.XML, false);
   }
   public Node find_fieldGINT (String fname){
     return find_field(fname, agsml.Constants.Lang.GINT);
   }
   public Node find_fieldGINTContains (String contains){
     return find_field(contains, agsml.Constants.Lang.GINT,false);
   }
   public Node find_fieldGEN (String fname){
     return find_field(fname, agsml.Constants.Lang.GEN);
   }
   public Node find_fieldGENContains (String contains){
     return find_field(contains, agsml.Constants.Lang.GEN,false);
   }
   public Node find_fieldDepthTOP() {
       
        StringBuilder depthTOP = new StringBuilder(); 
        
        depthTOP.append(tableGEN_name() + "_TOP;");
        depthTOP.append(tableGEN_name() + "_FROM;");
        depthTOP.append(tableGEN_name() + "_DPTH;");
        depthTOP.append(tableGEN_name() + "_DIS;");
        depthTOP.append("SAMP_TOP;");
        depthTOP.append("MONG_DIS;");
        depthTOP.append("SCDG_DPTH;");
        depthTOP.append("WSTG_DEP;");
        depthTOP.append("MONG_TRZ;");
        depthTOP.append("IPRG_TOP;");
        depthTOP.append("PMTG_DPTH");
        
        if (find_fieldGEN(depthTOP.toString())!=null) {
            return m_field;
        }
        
        return null;
   }
   public Node find_fieldDepthTOP2() {
        
             
        if (find_fieldGEN(this.tableGEN_name() + "_TOP") != null ) {
            return m_field;
        }
        
        if (find_fieldGEN(this.tableGEN_name() + "_FROM") != null ) {
            return m_field;
        }
        
        if (find_fieldGEN(this.tableGEN_name() + "_DPTH") != null) {
             return m_field;
        }
      
        if (find_fieldGEN(this.tableGEN_name() + "_DIS") != null ) {
            return m_field;
        }
        
        if (find_fieldGEN("SAMP_TOP") != null) {
            return m_field;
        }
        
        if (find_fieldGEN("MONG_DIS") !=null) {
            return m_field;
        }
        
        if (find_fieldGEN("SCDG_DPTH") !=null) {
            return m_field;
        }
        
        if (find_fieldGEN("WSTG_DEP") !=null) {
            return m_field;
        }
        
        if (find_fieldGEN("MONG_TRZ") !=null) {
            return m_field;
        }
        
        if (find_fieldGEN("IPRG_TOP") !=null) {
            return m_field;
        }
        
        if (find_fieldGEN("PMTG_DPTH") !=null) {
            return m_field;
        }
        return null;       
    }
    
   public Node find_fieldDepthBASE() {
        
        if (find_fieldGEN(this.tableAGS_name() + "_BASE") != null ) {
            return m_field;
        }
        
        if (find_fieldGEN("SAMP_BASE") != null) {
             return m_field;
        }
        
        return null;       
    }
//   private Node find_field1 (String fname){
//       try {
//       first_field();
//            do {
//                if (field1_name().equals(fname)) {
//                    return m_field;
//                }
//            }   while( next_field() != null);
//       return null;
//       }
//       catch (Exception e) {
//           return null;
//       }
//      }
       private String RemoveStars(String s1){
//       char ch;
//       StringBuilder sb;
//       sb = new StringBuilder();
//            for (int i=0; i<s1.length(); i++) {
//                ch = s1.charAt(i);
//                if (ch !='*') {
//                       sb.append(ch);
//                    }
//            }
//        return sb.toString();
        s1 = s1.replaceFirst("\\*", "");
        return s1;
     }
       private String UCaseFirst(String s1){
       String s2;
       String s3;
       s2 = s1.substring(0,1);
       s2.toUpperCase();
       s3 = s1.substring(1);
       return s2 + s3;
       }
     
   public String get_GINTselect(String tablename, agsml.Constants.Lang lang, boolean OnlyNamedFields) {
       try {
           
       String s1 = "";
       String s2 = "";
       StringBuilder sb1 = new StringBuilder();
           
       if (find_table(tablename,lang)!=null) {
       
            if (first_field()!= null) {

                 do {
                     
                     s1 = field_name(lang);
                     System.out.println(s1);
                     if (s1.isEmpty() && OnlyNamedFields) {
                       s2 = "";
                     } else {
                       s2 = fieldGINT_name();
                        if (s2.isEmpty()) {
                            s2 = "null as " + s1;
                        } 
                     } 
                                       
                     if (s2.length()>0) {
                            if (sb1.length()>0) {
                             sb1.append( "," + s2);
                            }    else {
                             sb1.append(s2); 
                            }
                     }
                 } while (next_field() != null);

            }
       }
       return sb1.toString();
       } 
       catch (Exception e){
         System.out.println(e.getMessage());
        e.printStackTrace();
      return null;    
         } 
       
   }  
   
   public String format_XMLTry(String t1, String  f1){
       try {
       
            if (f1.substring(0, 4).equalsIgnoreCase(agsml.Constants.GINT_NAME)) {
            return f1;
            }
       
      
       f1 = f1.replaceAll("\\*", "");
       f1 = f1.replaceAll(" ", "");
             
       t1 = t1.replaceAll("\\*", "");
       t1 = t1.replaceAll(" ", "");
       
       if (f1.contains("_") ){
        String[] parts = f1.split("_");
        f1 = "";
        for (int i = 0; i < parts.length; i++) {
            f1 = f1 + UCaseFirst(parts[i]);
        }
             } else {
        f1 = UCaseFirst(f1);   
       } 
       
       return UCaseFirst(t1) + f1;
       
       }
       catch (Exception e) {
           return null;
       }
    
   }
   
   public NodeList allDictionaryNodes() {
     return m_doc.getElementsByTagName(agsml.Constants.AGS_DICTIONARY_TAG);
   }
   
}

