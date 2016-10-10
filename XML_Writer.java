/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agsml;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;
import java.util.logging.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.util.logging.Level;
        /**
 *
 * @author thomsonsj
 */
public class XML_Writer {
    protected Logger m_log;
    protected AGS_Dictionary m_dic;
    protected AGS_DataStructure m_ds;
    protected Set m_hs;
    protected String m_StyleSheet = "";
    protected NodeList m_holeList;
    protected String m_onlyHoleId = "";
    protected String m_folder = "";
    
    private BufferedWriter m_bw;
    private StringBuilder m_sb;
    private StringBuilder m_attrib;
    private final String const_Open = "<";
    private final String const_Close =">";
    private final String const_OpenEnd="</";
    private final String const_CDATAstart="<![CDATA[";
    private final String const_CDATAend="]]>";
    private final String const_AGSMLId = "DataStructureAGSMLId";
    private final String const_StylesheetAttribTag = "StyleSheet";
    private final String const_XMLVersion = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private final String const_SPACE = " ";
    private final String const_EQUALS = "=";
    private final String const_QUOTE = "\"";
    
    private Pattern p;
    private Matcher m;
    private Stack m_open;
    
    public XML_Writer(BufferedWriter bw )  {
        m_bw = bw;
        m_sb = new StringBuilder();
        m_attrib = new StringBuilder();
        p = Pattern.compile("[&<>]");
        m_open = new Stack();
    }
    public void setLogger(Logger log) {
        m_log = log;
    }
    public void setHoleList (NodeList nl1) {
        m_holeList = nl1;
   }
   public void setAGSDataStructure (String s1) {
      m_ds.setRoot(m_ds.constDataStructureTag,"id",s1);  
   }
    public void setAGSDictionary (String s1) {
       m_dic.setRoot(m_dic.constDictionaryAGSMLTag,"id",s1);
    }
    
    public void setAGSDataStructureFile(String xmlDataFile, String s1) {
    try {
        if (s1.isEmpty()) {
            s1 = "Project";
         }
        m_ds = new AGS_DataStructure (xmlDataFile,  s1);
        m_hs = new HashSet();
        
    } 
    
    catch (Exception e) {
    m_log.log(Level.SEVERE, e.getMessage());
    }
    }
   
    public void setAGSDictionaryFile(String xmlDictFile, String s1) {
    try {
        if (s1.isEmpty()) {
            s1 = "Defaullt";
         }
        m_dic = new AGS_Dictionary (xmlDictFile, s1);
        m_hs = new HashSet();
    } catch (Exception e) {
    m_log.log(Level.SEVERE, e.getMessage());
    }
    }
   
//    try {
//         
//      =  doc.getElementByTagName("HoleId");
//       
//       Node n1 = doc.findSubNode(doc.RootNode(),const_AGSMLId);
//       
//       if (n1 != null) {
//            String s1 = n1.getTextContent();
//            if (s1.isEmpty() != true) {
//               setDataStructureAGSMLId(s1);
//            } 
//         } 
//       
//      } 
//    catch (Exception e ) {
//       m_log.log(Level.SEVERE, e.getMessage());
//   }
//   }
//   public void setDataStructureAGSMLId(String Id) {
//    Element e1 = (Element) m_ds.set_DataStructureAGSML(Id);
//    m_StyleSheet = e1.getAttribute(const_StylesheetAttribTag);
//    }
   public void processAGSML(Node n1) {
       
   }
   public void processAGSML() {
       
   }
   private int XML_Writer(String fout, boolean overwrite)  {
    try {
        File file = new File(fout);
      
        if (file.exists() && overwrite == true) {
          file.delete();
        }
        
        if (file.isDirectory()) {
            if (!file.exists()) {
            file.mkdir();    
            }
            m_folder = fout;    
        }  else {
            NewFile(fout);
        } 
        
        m_attrib = new StringBuilder();
        
        return 1;
    }
    catch (Exception e) { 
       return -1;
    }
   }
    public XML_Writer(String fout) {
       this.XML_Writer(fout, false);
    } 
    
    public int NewFile(String fout)  {
       try {
           // Close file if alreday open'
           this.close();
           // Initialise and open new bufferwe writer and stringbuilder
            m_bw = new BufferedWriter(new FileWriter(fout));
            m_sb = new StringBuilder();
            p = Pattern.compile("[&<>]");
            m_open = new Stack();
            AddVersionAndStyleSheet();
            return 1;
            }
        catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        m_log.exiting("XMLWriter", fout);
        return -1;
    }
            
   }
    
    public void AddVersionAndStyleSheet() {
    
     add (const_XMLVersion);
     
     if (m_StyleSheet.length()>0) {
        add ("<?xml-stylesheet type=\"text/xsl\" href=\"" + m_StyleSheet + "\"?>");
     }
    }
    
    public String XML_Text(){
       return m_sb.toString();
     }
    public void newLine() {
        add("\n");
    }

    public void add(String s){
        try {
        m_bw.append(s);
//        m_sb.append (s);
        }
        
        catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        }
    }
    public void addNodeAttrib(String elName, String elValue) {
        m_attrib.append(this.const_SPACE);
            m_attrib.append(elName);
                m_attrib.append(this.const_EQUALS);
                    m_attrib.append (this.const_QUOTE);
                        m_attrib.append(elValue);
                            m_attrib.append (this.const_QUOTE); 
    }
    public void openNode (String elName) {
        add(const_Open + elName + m_attrib.toString() + const_Close);
        m_open.push(elName);
        m_attrib = new StringBuilder();
    }
    public void closeNode(){
        add(const_OpenEnd + m_open.pop() + const_Close);
    }
    
    public void openElement (String elName) {
        elName = removeStars (elName);
        add(const_Open);
        add(elName);
        add(const_Close);
      
        }
     private String removeStars(String s1){
         String star="\\*";
         s1.replaceAll(star, null);
         return s1;
     }
     public void addValid(String val){
          m = p.matcher(val);
        if (m.find()!=true) {
        add(val);
         } else {
            add(const_CDATAstart);
            add(val);
            add(const_CDATAend);
         }
     }
     public void addAttrib (String elName, String val) {
        elName = removeStars (elName);
        add(const_Open);
        add(elName);
        add(const_Close);
        addValid(val);
        add(const_OpenEnd);
        add(elName);
        add(const_Close);
        newLine();
        }
    public void closeElement (String elName) {
      elName = removeStars (elName);
      add(const_OpenEnd);
      add(elName);
      add(const_Close);  
      newLine();
    }
    public void close()  {
        try {
            if (m_bw != null) {
            m_bw.close();
            }
        } catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        }
}
 
    
    
protected Node processAGSMLNode(Node n1, String hole_id){
 try {
    String s1 = null;
    boolean IsAGSMLTable;
 
    do {
           if (n1.getNodeType() == Node.ELEMENT_NODE) {
               
                s1 = n1.getNodeName();
                
                IsAGSMLTable = IsAGSTable(s1,"agsml");
                
                m_log.log(Level.INFO ,"["+ hole_id + "][" + n1.getNodeName() +"]") ;
                
                if (s1.equals("holeInformation")) {
                 m_log.log(Level.INFO ,"["+ hole_id + "][" + n1.getNodeName() +"]") ;    
                }
                if (IsAGSMLTable == false) {
                    openNode (s1);
                }
                 if (s1.equals("Proj")) {
                    addTable (s1, null, null);
                }
                 
                if (s1.equals("holes") || s1.equals("Hole")) {
                    n1 = addHoles(n1);
                }
                                                    
                if (IsAGSMLTable == true && hole_id != null) {
                    addTable (s1, "HoleId", hole_id);
                }
                
                if (n1 != null) {
                        if (n1.hasChildNodes() == true) {
                            NodeList list = n1.getChildNodes();
                            Node n2 = list.item(0); 
                            processAGSMLNode(n2, hole_id);
                }} 
                
                if (IsAGSMLTable == false) {
                    closeNode();
                }

        }
           
   
      
      n1 = n1.getNextSibling();
      
      
     } while (n1 != null && n1.getNodeName() != "DataStructureAGSML");
   
    return n1;
   
 } catch (Exception e) {
     m_log.log(Level.SEVERE, e.getMessage());
     return n1;
 }
}
protected boolean IsHoleInList(String hole_id) {
  return IsHoleInList(hole_id,m_holeList);  
}
protected String removeSpecialCharacters(String s1) {
 
    Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");

    Matcher matcher = pattern.matcher(s1);
    
        if (!matcher.matches()) {
            String s2 = matcher.replaceAll("");
                return s2;
        } else { 
                return s1;
        }
  }
protected boolean ContainsSpecialCharacters(String s1) {
  
     Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");

      Matcher matcher = pattern.matcher(s1);
 
      if (!matcher.matches()) {
          m_log.log(Level.INFO, "string '" + s1 + "' contains special character");
           return true;
      } else {
            m_log.log(Level.INFO, "string '" + s1 + "' doesn't contains special character");
           return false;
      }
  }
protected boolean IsHoleInList(String hole_id, NodeList holeList) {
 try {
    if (m_onlyHoleId != null) {
        if (!m_onlyHoleId.isEmpty()) {
            if (m_onlyHoleId.equalsIgnoreCase(hole_id)) {
                return true; } 
            else {
                return false;
            }
        }
    }
    
    if (holeList == null) {
        return true;
    } else {
        if (holeList.getLength() == 0) {
            return true;
        } else {
            for (int i=0; i <= holeList.getLength(); i++) {
            Element el1  = (Element) holeList.item(i);
            String s1 = el1.getTextContent();
                if (s1.equalsIgnoreCase(hole_id)) {
                    return true;
                }
            }
        }
    return false;
    }
 }
 catch (Exception e) {
     return false;
 }
}

public String lookupTable2 (String t1name) {
    if (m_dic.table1_name().equals(t1name)!=true) {
       m_dic.find_table1(t1name);
    }
    return m_dic.table2_name();
}
public String lookupField2 (String t1name, String f1name) {
    if (m_dic.table1_name().equals(t1name)!=true) {
       m_dic.find_table1(t1name);
    }
    m_dic.find_field1(f1name);
    return m_dic.field2_name();
}
private boolean IsAGSTable(String tname, String lang){
    if (m_dic.find_table(tname, lang) == null){
        return false;
    } else {
      return true;
    }
}

protected Collection Header_XChange(Collection header1, String tname1) {
        List l;
        Iterator it;
        String fname1;
        String fname2;
        boolean TableFound = false;
        boolean FieldFound = false;
        
        l = new ArrayList<String>();
        it = header1.iterator();
        TableFound = (m_dic.find_table1(tname1) != null );
                
        do {
            fname1 = it.next().toString();
            if (TableFound) {
                FieldFound = (m_dic.find_field1(fname1)!=null);
                    if (FieldFound) {
                        fname2 = m_dic.field2_name();
                    } else { 
                        fname2 = m_dic.format_XMLTry(fname1, tname1);
                    }
            } else {
              fname2 = m_dic.format_XMLTry(fname1, tname1);  
            }
        l.add(fname2);
        } while(it.hasNext());

        return l;
}

protected int addTable(String xml_tname, String xml_fname, String fvalue) {
return -1;
}
protected Node addHoles(Node n1){
    return null;
}

}

