/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agsml;
import org.w3c.dom.Node;
/**
 *
 * @author thomsonsj
 */
public class AGS_Dictionary extends XML_DOM {
    
    private String m_n1;
    private String m_n2;
    private Node m_table;
    private Node m_table1;
    private Node m_table2;
    
    private Node m_field;
    private Node m_field1;
    private Node m_field2;
    
    public final String constDictionaryAGSMLTag = "DictionaryAGSML";
    private final String default_n1 = "ags";
    private final String default_n2 = "agsml";
    private final String constGintRecID = "GintRecID";
    private final String constGintProjectID = "gINTProjectID";
    
   public void convert_n1 (String newStr){
       m_n1 = newStr;
   }
   public void convert_n2 (String newStr){
       m_n2 = newStr;
   }  
   
   public AGS_Dictionary (String fXMLFile) {
        super (fXMLFile);
        m_n1 = default_n1;
        m_n2 = default_n2;
        setRoot(constDictionaryAGSMLTag, "Id","Default");
   }
            
   public AGS_Dictionary (String fXMLFile, String id){
        super (fXMLFile);
        m_n1 = default_n1;
        m_n2 = default_n2;
        setRoot(constDictionaryAGSMLTag, "id",id);
    }
   public String DictionaryAGSMLTag(){
       return constDictionaryAGSMLTag;
   }
   public String field_name(){
        return find_Value(m_field, "name");
    }
   public String field1_name(){
        return find_Value(m_field1,"name");
    }
   public String field2_name(){
        return find_Value(m_field2, "name");
    }
   public String table1_name(){
        return find_Value(m_table1, "name");
    }
   public String table2_name(){
        return find_Value(m_table2, "name");
    }
   public String table_name(){
        return find_Value(m_table, "name");
    }
   public Node next_table(){
        Node n1 = m_table;
        Node n2;
        do {
        n2 = n1.getNextSibling();
            if (n2 != null && n2.getNodeType() == n1.ELEMENT_NODE) {
                if (n2.getNodeName().equals("table")) {
                    m_table= n2;
                    get_table12();
                    return m_table;
                }
            }
        n1 = n2;
        } while (n2 != null);
                  
        return null;
    }  
   public Node first_table(){
        Node n1;
        m_table = findSubNode("table");
        get_table12();
        return m_table;
    }
   public Node first_field(){
        Node n1 = m_table;
        m_field = findSubNode(n1,"field");
        get_field12();
        return m_field;
    }
   private void get_field12(){
        m_field1 = findSubNode(m_field,m_n1);
        m_field2 = findSubNode(m_field,m_n2);
    }
   private void get_table12(){
        m_table1 = findSubNode(m_table,m_n1);
        m_table2 = findSubNode(m_table, m_n2);
    }
   public Node next_field(){
        Node n1 = m_field;
        Node n2;
        do {
        n2 = n1.getNextSibling();
            if (n2 !=null && n2.getNodeType() == Node.ELEMENT_NODE){
                if (n2.getNodeName().equals("field")) {
                    m_field = n2;
                    get_field12();
                return m_field;
                }
            }
        n1 = n2;
        } while (n2 !=null);
        return null;
    }
   private String find_Value( Node n1, String s1){
        Node n2 = findSubNode(n1,s1);
        if (n2 != null) {
            return n2.getTextContent();
        } else
            return null;

    }
   public String find_tableValue(String s1){
        return find_Value(m_table, s1);
    }
   public String find_table1Value(String s1){
        return find_Value( m_table1, s1);
    }
   public String find_table2Value(String s1){
        return find_Value(m_table2, s1);
    }
   public String find_fieldValue(String s1){
        return find_Value( m_field, s1);
    }
   public String find_field1Value(String s1){
        return find_Value(m_field1, s1);
    }  
   public String find_field2Value(String s1){
        return find_Value(m_field2, s1);
    } 
   public Node find_table(String tname, String lang){
    try {
       if (lang == null) {
            first_table();
            do {
                if (table_name().equals(tname)) {
                    return m_table;
                }
            }   while( next_table() != null);
        }

        if (lang.equals(m_n1)){
            return find_table1(tname);
        }

        if (lang.equals(m_n2)) {
            return find_table2(tname);
        } 
    return null;
    }
    
    catch (Exception e) {
        return null;
    }
       
    }
        
   public Node find_table1 (String tname){
         first_table();
            do {
                if (table1_name().equals(tname)) {
                    return m_table;
                }
            }   while( next_table() != null);
           return null;
   } 
   public Node find_table2 (String tname){
        first_table();
            do {
                if (table2_name().equals(tname)) {
                    return m_table;
                }
            }   while( next_table() != null);
           return null;
       
 }
        
        
   public Node find_field (String fname, String lang){
        if (lang == null) {
            first_field();
            do {
                if (field_name().equals(fname)) {
                    return m_field;
                }
            }   while( next_field() != null);
        }

        if (lang.equals(m_n1)){
            return find_field1(fname);
        }

        if (lang.equals(m_n2)) {
            return find_field2(fname);
        }
       return null;
        }
   public Node find_field2 (String fname){
       first_field();
            do {
                if (field2_name().equals(fname)) {
                    return m_field;
                }
            }   while( next_field() != null);
       return null;
      }
   public Node find_field1 (String fname){
       first_field();
            do {
                if (field1_name().equals(fname)) {
                    return m_field;
                }
            }   while( next_field() != null);
       return null;
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
       private String UCaseFirst(String s1){
       String s2;
       String s3;
       s2 = s1.substring(0,1);
       s2.toUpperCase();
       s3 = s1.substring(1);
       return s2 + s3;
       }
   public String format_XMLTry(String field1, String table1){
       try {
       
       if (field1.equalsIgnoreCase(constGintRecID) ||
               field1.equalsIgnoreCase(constGintProjectID)
                  ) {
           return field1;
       }
       
       String xml_field = RemoveStars(field1);
       String xml_table = RemoveStars(table1).toLowerCase();
       
       if (field1.contains("_") ){
       xml_field = RemoveStars(field1).substring(field1.lastIndexOf("_"));
       } 
       
       return xml_table + UCaseFirst(xml_field);
       }
       
       catch (Exception e) {
           return null;
       }
  
   }
   
}




