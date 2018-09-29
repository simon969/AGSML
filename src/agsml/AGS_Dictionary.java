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

/**
 *
 * @author thomsonsj
 */
public class AGS_Dictionary extends XML_DOM {
    
    private final String constSQL = "sql";
    private final String constAGS = "ags";
    private final String constXML = "agsml";
    private final String constGINT = "gint";
    
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
    
    public final String constDictionaryAGSMLTag = "DictionaryAGSML";
    public final String constDefaultDictionaryId = "AGS3.1a";
//    private final String constGintRecID = "GintRecID";
//    private final String constGintProjectID = "gINTProjectID";
    private final String constGint = "GINT";
    
    public enum Lang {
            GEN (0)  {public int toInt() {return 0;}},
            AGS (1) {public int toInt() {return 1;}},
            SQL (2) {public int toInt() {return 2;}},
            XML (3) {public int toInt() {return 3;}},
            GINT (4) {public int toInt() {return 4;}};
        private int value;     
        public abstract int toInt();
        private Lang(int value) {
           this.value=value;
       }       
    }
    public String getAGSDictionaryId(AGSVersion ags) {
        switch (ags) {
            case NONE: 
                return "NONE";
            case AGS30: 
                return "AGS3.0";  
            case AGS31: 
                return "AGS3.1";  
            case AGS31a: 
                return "AGS3.1a";    
            case AGS403: 
                return "AGS4.03";   
            case AGS404: 
               return "AGS4.04";  
        }
        return "NONE";
    } 
    
    public enum AGSVersion {
        NONE    (0) {   public int toInt() {return 0;}
                            public String toId() {return "NONE";}
                            public double toDouble() {return 0;}
                            public String toVersion() {return "";}
                            public String toMajorVersion() {return "";}
                        },
        AGS30 (300) {   public int toInt() {return 300;}
                            public String toId() {return "AGS3.0";}
                            public double toDouble() {return 3;}
                            public String toVersion() {return "3.0";}
                            public String toMajorVersion() {return "3.0";}
                        },
        AGS31 (310) {   public int toInt() {return 310;}
                            public String toId() {return "AGS3.1";}
                            public double toDouble() {return 3.1;}
                            public String toVersion() {return "3.1";}
                            public String toMajorVersion() {return "3.0";}
                        },
        AGS31a (311){   public int toInt() {return 311;}
                            public String toId() {return "AGS3.1a";}
                            public double toDouble() {return 3.11;}
                            public String toVersion() {return "3.11";}
                            public String toMajorVersion() {return "3.0";}
                        },
        AGS403 (403){   public int toInt() {return 403;}
                            public String toId() {return "AGS4.03";}
                            public double toDouble() {return 4.03;}
                            public String toVersion() {return "4.04";}
                            public String toMajorVersion() {return "4.0";}
                        },
        AGS404 (404){   public int toInt() {return 404;}
                            public String toId() {return "AGS4.04";}
                            public double toDouble() {return 4.04;}
                            public String toVersion() {return "4.04";}
                            public String toMajorVersion() {return "4.0";}
                        };
        private int value;     
        public abstract int toInt();
        public abstract String toId();
        public abstract double toDouble();
        public abstract String toVersion();
        public abstract String toMajorVersion();
        private AGSVersion(int value) {
           this.value=value;
       }
    } 
    private AGSVersion getAGSVersion(int value) {
        switch (value) {
            case 30: 
                return AGSVersion.AGS30;
            case 310: 
                return AGSVersion.AGS31;      
            case 311: 
                return AGSVersion.AGS31a;    
            case 403: 
               return AGSVersion.AGS403;   
            case 404: 
               return AGSVersion.AGS404;  
        }
        return AGSVersion.NONE;
    } 
    public AGSVersion getAGSVersion() {
        
        String s1 = "0.0";
        AGSVersion ags1 = AGSVersion.NONE;
                
        Node n1 = findSubNode("version");
        
    if (n1 == null) {
        return ags1;
    }
      
    NodeList nl1 = n1.getChildNodes();
             
        for (int i = 0; i < nl1.getLength(); i++) {
           n1 = nl1.item(i);
           if (n1.getNodeType() == Node.ELEMENT_NODE) {
               String s2 = n1.getNodeName();
               if (s2 == "ags") {
                 Element e1 = (Element) n1;
                 s1 = e1.getTextContent();
               } 
           }
        } 
        
        String s2 = s1.replaceAll("a", "1");
        
        float f1 = Float.valueOf(s2) * 100;
        ags1 = getAGSVersion (Math.round(f1)) ;
        return ags1;
    }
    
    public AGS_Dictionary () {
    }
    public AGS_Dictionary (String fXMLFile) throws Exception {
        super (fXMLFile,fileState.MUSTEXIST);
        setDictionaryID(constDefaultDictionaryId);
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
           Node n1 = setRoot(constDictionaryAGSMLTag, "id", id);
           if (n1 == null) throw new Exception ("Failed set " + constDictionaryAGSMLTag + " node, unable to find <" + constDictionaryAGSMLTag + " id=" + id + ">");
           return n1;
        } 
       catch (Exception e) {
           m_log.log(Level.SEVERE, id);
           return null;
        }
    }
   
   public String DictionaryAGSMLTag(){
       return constDictionaryAGSMLTag;
   }
   public String field_name(Lang lang) {
       switch (lang) {
           case GEN:
            return find_value(m_field, "name");
           case AGS:
            return find_value(m_fieldAGS, "name");
           case GINT:
            return find_value(m_fieldGINT, "name");   
           case XML:
            return find_value(m_fieldXML, "name");
            
       }
       return "";
   }
   public String fieldGEN_name(){
        return find_value(m_field, "name");
    }
    public  String fieldSQL_name(){
        return find_value(m_fieldGINT,"name");
   }
   public  String fieldGINT_name(){
        return find_value(m_fieldGINT,"name");
    }
   public  String fieldGINT_type(){
        return find_value(m_fieldGINT,"type");
    }
   public  String fieldGINT_length(){
        return find_value(m_fieldGINT,"length");
    }
   public  String fieldAGS_name(){
        return find_value(m_fieldAGS, "name");
    }
    public  String fieldXML_name(){
        return find_value(m_fieldXML, "name");
    }
    
    public String table_name(Lang lang) {
       switch (lang) {
           case GEN:
            return find_value(m_table, "name");
           case AGS:
            return find_value(m_tableAGS, "name");
           case GINT:
            return find_value(m_tableGINT, "name");   
           case SQL:
            return find_value(m_tableSQL, "name");      
           case XML:
            return find_value(m_tableXML, "name");    
       }
       return "";
   }
    
    public  String tableGINT_name(){
        return find_value(m_tableGINT, "name");
    }
   public String tableAGS_name(){
        return find_value(m_tableAGS, "name");
    }
    public  String tableXML_name(){
        return find_value(m_tableXML, "name");
    }
   public  String tableGEN_name(){
        return find_value(m_table, "name");
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
                        m_tableAGS = findSubNode(m_table, constAGS);
                        m_tableXML= findSubNode(m_table, constXML);
                        m_tableSQL = findSubNode(m_table, constSQL);
                        m_tableGINT= findSubNode(m_table, constGINT);
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
        m_table = findSubNode("table");
        m_tableAGS = findSubNode(m_table,constAGS);
        m_tableXML = findSubNode(m_table, constXML);
        m_tableSQL = findSubNode(m_table, constSQL);
        m_tableGINT= findSubNode(m_table, constGINT);
        return m_table;
    }
   public Node first_field(){
        m_field = findSubNode(m_table,"field");
        m_fieldAGS = findSubNode( m_field ,constAGS);
        m_fieldXML = findSubNode( m_field ,constXML);
        m_fieldSQL = findSubNode( m_field ,constSQL); 
        m_fieldGINT = findSubNode( m_field ,constGINT);
        return m_field;
    }
   private void get_fields(){
       try {
        m_fieldAGS = findSubNode( m_field ,constAGS);
        m_fieldXML = findSubNode( m_field ,constXML);
        m_fieldSQL = findSubNode( m_field ,constSQL);
        m_fieldGINT = findSubNode( m_field ,constGINT);
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
                         m_fieldAGS = findSubNode( m_field ,constAGS);
                         m_fieldSQL = findSubNode( m_field ,constSQL);
                         m_fieldXML = findSubNode( m_field ,constXML);  
                         m_fieldGINT = findSubNode( m_field ,constGINT);  
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
   private String find_value( Node n1, String s1){
    try {
        Node n2 = findSubNode(n1, s1);
        if (n2 != null) {
            return n2.getTextContent();
        } else
        
        return "";
        
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
  public Node find_table(String tname, Lang l){
    try {
         first_table();
            do {
                if (l==Lang.GEN && this.tableGEN_name().equals(tname)) {
                    return m_table;
                }
                if (l==Lang.AGS && this.tableAGS_name().equals(tname)) {
                    return m_table;
                }
                if (l==Lang.GINT && this.tableGINT_name().equals(tname)) {
                    return m_table;
                }
                if (l==Lang.SQL && this.tableGINT_name().equals(tname)) {
                    return m_table;
                }
                if (l==Lang.XML && this.tableXML_name().equals(tname)) {
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
       return find_table(tname,Lang.GEN);
     }
       
     public Node find_tableXML (String tname){
     return find_table(tname,Lang.XML);
     }
     
   public Node find_tableAGS (String tname){
     return find_table(tname,Lang.AGS);   
 }
        
   public Node find_tableGINT (String tname){
     return find_table(tname,Lang.GINT);  
}
   
    public Node find_field (String fname, Lang l){
     try {
           first_field();
            
           do {
                if (l==Lang.GEN && this.fieldGEN_name().equals(fname)) {
                    return m_field;
                }
                if (l==Lang.AGS && this.fieldAGS_name().equals(fname)) {
                    return m_field;
                }
                if (l==Lang.GINT && this.fieldGINT_name().equals(fname)) {
                    return m_field;
                }
                if (l==Lang.SQL && this.fieldSQL_name().equals(fname)) {
                    return m_field;
                }
                if (l==Lang.XML && this.fieldXML_name().equals(fname)) {
                    return m_field;
                }
            }   while( next_field() != null);
        return null;
        }
      catch (Exception e) {
          return null;
}
}
   public Node find_fieldAGS (String fname){
      return find_field(fname, Lang.AGS);
   }
   public Node find_fieldXML (String fname){
     return find_field(fname, Lang.XML);
   }
   public Node find_fieldGINT (String fname){
     return find_field(fname, Lang.GINT);
   }
   public Node find_fieldGEN (String fname){
     return find_field(fname, Lang.GEN);
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
       
   public String get_GINTselect(String tablename, AGS_Dictionary.Lang lang, boolean OnlyNamedFields) {
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
       
            if (f1.substring(0, 4).equalsIgnoreCase(constGint)) {
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
     return m_doc.getElementsByTagName(constDictionaryAGSMLTag);
   }
   
}

