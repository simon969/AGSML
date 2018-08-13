/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agsml;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.StringWriter;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.bind.DatatypeConverter;
import agsml.Base_Writer;
        /**
 *
 * @author thomsonsj
 */
class XML_Writer  extends Base_Writer {
    
    public static final String AGS_STYLESHEET = "stylesheet";
    public final static String AGS_HTMLPATH= "AGSMLPath";
    
    private BufferedWriter m_bw;
    private StringWriter m_sw;
    
    private StringBuilder m_sb;
    private StringBuilder m_attrib;
    
    private final String const_Open = "<";
    private final String const_Close =">";
    private final String const_OpenEnd="</";
    private final String const_CDATAstart="<![CDATA[";
    private final String const_CDATAend="]]>";
    private final String const_StylesheetAttribTag = "stylesheet";
    private final String const_XMLVersion = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private final String const_SPACE = " ";
    private final String const_EQUALS = "=";
    private final String const_QUOTE = "\"";
    
    private Pattern p;
    private Matcher m;
    private Stack<String> m_open;
    
    public XML_Writer()  {
        m_attrib = new StringBuilder();
        p = Pattern.compile("[&<>]");
        m_open = new Stack<String>();
        m_sw = new StringWriter();
        m_bw = new BufferedWriter(m_sw);
    }
    
    public XML_Writer(BufferedWriter bw )  {
        this();
        m_bw = bw;
    }

    public String stylesheet() {
        return getProperty(AGS_STYLESHEET);
    }
    
    public void setStyleSheet(String s1) {
        setProperty(AGS_STYLESHEET,s1);
    }
    protected int openFile(Node n1) {
        String s1 = n1.getTextContent();
        setOutputFile(s1);
        setStyleSheet(n1);
        return openFile(true);
     }
//    protected int openFile(String fout, Node DataStructureNode) {
//       setOutputFile(fout);
//       setAGSDataStructureNode(DataStructureNode);
//       return openFile(true);
//    }
    public void Process() {
   
    try {
        
      Node n1 =  m_ds.m_root;
      Process(n1);
     
    } catch (Exception e) {
        
    }
    }
    protected int openFile(boolean overwrite) {
        
    try {
        
        this.closeFile();
        
        File file = new File(fileOut());
      
        if (file.isDirectory()) {
            if (!file.exists()) {
                file.mkdir();    
            }
        }  else {
             if (file.exists() && overwrite == true) {
                    file.delete();
                }
           if (newFile()>0) {
             AddVersionAndStyleSheet();
             m_log.info("File " + fileOut() + " opened");   
           }
       } 
        
        return 1;
    }
    catch (Exception e) { 
       return -1;
    }
   }
 
    Node getRootNodeFirstChild(Node n1) {
        try {
     Node n2;
     
     if (isRootNode(n1) == true) {
            n2 =  n1.getFirstChild();

            while (n2.getNodeType() != Node.ELEMENT_NODE) {
            n2 = n2.getNextSibling();
            }
          
        return n2;
     } else {
            return null;
      }
        } catch (Exception e) {
          return null;    
        }  
 }
  private boolean RootNodeName(Node n1) {
  boolean retvar = false;
  
  if (n1.getNodeType() == Node.ELEMENT_NODE) {
        String s1 = n1.getNodeName(); 
        if(s1.equals (m_ds.DataStructureAGSML))
            retvar = true;
        } else {
            retvar = false;
  }
   return retvar;
     
 }
 public boolean isRootNode(Node n1) {
 // boolean retvar = false;
  if (m_ds.RootNode().isEqualNode(n1)) {
      return true;
  } else {
      return false;
  }
 }
                                                                                                                                                                                                                                                                                
   public void Process(Node n1) {
       
   }
   
   private XML_Writer(String fout, boolean overwrite)  {
       this();
       setOutputFile(fout);
       openFile (overwrite);
   }
    public XML_Writer(String fout) {
        this();
        setOutputFile(fout);
        openFile (true);
    } 
    public XML_Writer(String fileout, String stylesheet) {
        this();
        setOutputFileFolder(fileout);
        setStyleSheet(stylesheet);
        openFile (true);
    } 
    protected int newFile()  {
       try {
           // Close file if alreday open'
           // Initialise and open new bufferwe writer and stringbuilder
            m_bw = new BufferedWriter(new FileWriter(fileOut()));
            
            if (fieldOut().length() > 0) {
                m_sb = new StringBuilder();
            } 
            
            return 1;
            }
        catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        m_log.log(Level.SEVERE, e.getMessage()); 
        m_log.exiting("XMLWriter", fileOut());
        return -1;
    }
            
   }
    private String getStyleSheetFromAGSMLDataStructure() {
        try {
            return setStyleSheet(m_ds.RootNode());
        }
        catch (Exception e) {
        m_log.log(Level.SEVERE, e.getMessage());
        return "";
        }
    }
    private String setStyleSheet(Node n1 ){
        Element el1 = (Element) n1;
        String s1 = el1.getAttribute(const_StylesheetAttribTag);
        setStyleSheet(s1);
        return stylesheet();
    }
    protected void AddVersionAndStyleSheet() {
    
     add (const_XMLVersion,false);
     
     if (stylesheet().length()>0) {
        add ("<?xml-stylesheet type=\"text/xsl\" href=\"" + stylesheet() + "\"?>", false);
     }
    }
    
    public String toString(){
        
        try {
             m_bw.flush();
             m_sw.flush();
             return m_sw.toString();
         }
        
        catch ( Exception e ) {
            // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace()  ;
        return null;
        }
   }
   
    public void newLine() {
        add("\n");
    }
    public void add(String s){
    add(s, true);
    }
    
    public void add(byte[] b1, boolean boolAddToString){
        try {
            
        String s = DatatypeConverter.printBase64Binary(b1);
        
        m_bw.append(s);
        
        if (m_sb != null && boolAddToString == true) {
        m_sb.append (s);
        }
        
        }
        
        catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        }
    }
    public void add(String s, boolean boolAddToString){
        try {
            
        m_bw.append(s);
        
        if (m_sb != null && boolAddToString == true) {
        m_sb.append (s);
        }
        
        }
        
        catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        }
    }
    public void addNodeAttrib(String elName, String elValue) {
        if (!elName.isEmpty() && !elValue.isEmpty()) {
        m_attrib.append(this.const_SPACE);
            m_attrib.append(elName);
                m_attrib.append(this.const_EQUALS);
                    m_attrib.append (this.const_QUOTE);
                        m_attrib.append(elValue);
                            m_attrib.append (this.const_QUOTE); 
        }
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
     public void add64bit(String s1, byte[] val) {
         addNodeAttrib ("types:dt","bin.base64");
         addNodeAttrib ("xmlns:types", "urn:schemas-microsoft-com:datatypes");
         openNode(s1);
         add(val,false);
         closeNode();
             
//         <serialized_binary types:dt="bin.base64" xmlns:types="urn:schemas-microsoft-com:datatypes">
//        JVBERi0xLjMKJaqrrK0KNCAwIG9iago8PCAvVHlwZSAvSW5mbw...(plus lots more)
//    </serialized_binary>
         
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
             
    public void closeFile()  {
        try {
            if (m_bw != null) {
            m_bw.close();
            m_log.info("File " + fileOut() + " closed");  
            }
        } catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        }
}
 
    
    
protected Node Process(Node n1, String hole_id){
 return null;
}
protected boolean IsHoleInList(String hole_id) {
  return IsHoleInList(hole_id,m_holeList);  
}
protected String removeSpecialCharacters(String s1) {
  
    Pattern pattern = Pattern.compile("<>:'");

    Matcher matcher = pattern.matcher(s1);
    
        if (matcher.matches()) {
            String s2 = matcher.replaceAll("");
                return s2;
        } else { 
                return s1;
        }
  }
protected boolean ContainsSpecialCharacters(String s1) {
  
     Pattern p = Pattern.compile("[_a-zA-Z0-9-().\\s]+");

      Matcher m = p.matcher(s1);
 
      if (!m.matches()) {
          m_log.log(Level.INFO, "string '" + s1 + "' contains special character");
           return true;
      } else {
            m_log.log(Level.INFO, "string '" + s1 + "' doesn't contains special character");
           return false;
      }
  }

protected boolean IsValidFileName(String s1) {
   try {
    Path p =Paths.get(s1);
    return true;
   }
    catch (Exception e) {
        return false;
    }  
}

protected String getHoleList(String delimiter) {
    
    StringBuilder sb1 = new StringBuilder();
    
    if (m_holeList == null) {
        return "";
    }
     for (int i=0; i < m_holeList.getLength(); i++) {
        Node n1 = m_holeList.item(i);
            if (n1.getNodeType() == Node.ELEMENT_NODE) {
               Element el1  = (Element) m_holeList.item(i);
               String s1 = "'" + el1.getTextContent() + "'";
               if (sb1.length()>0) {
                     sb1.append(delimiter + s1);
                } else {
                   sb1.append(s1); 
               }
            }
     }
    return sb1.toString();
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
            for (int i=0; i < holeList.getLength(); i++) {
            Node n1 = holeList.item(i);
            if (n1.getNodeType() == Node.ELEMENT_NODE) {
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
 }
 catch (Exception e) {
     return false;
 }
}

//public String lookupTable2 (String t1name) {
//    if (m_dic.table1_name().equals(t1name)!=true) {
//       m_dic.find_table1(t1name);
//    }
//    return m_dic.table2_name();
//}
//public String lookupField2 (String t1name, String f1name) {
//    if (m_dic.table1_name().equals(t1name)!=true) {
//       m_dic.find_table1(t1name);
//    }
//    m_dic.find_field1(f1name);
//    return m_dic.field2_name();
//}

  private boolean openIfNodeNamed(Node n1, String s1) {
  boolean retvar = false;
  
  if (n1.getNodeType() == Node.ELEMENT_NODE) {
        String s2 = n1.getNodeName(); 
        if(s1.equals (s2))
           retvar = true;
           openNode (s1);
        } else {
            retvar = false;
  }
   return retvar;
     
 }
  private boolean closeIfNodeNamed (Node n1, String s1) {
  boolean retvar = false;
  
  if (n1.getNodeType() == Node.ELEMENT_NODE) {
        String s2 = n1.getNodeName(); 
        if(s1.equals (s2))
           retvar = true;
           closeNode ();
        } else {
            retvar = false;
  }
   return retvar;
  }


protected boolean IsAGSTable(String xml_tname){
 return IsAGSTable(xml_tname,null);
 }
protected boolean IsAGSTable(String xml_tname, String IgnoreTables){
  try {
      if (IgnoreTables != null) {
          String[] tokens = IgnoreTables.split(";");
           for(String token: tokens){
                if (xml_tname == token) {
                    return false;
                }
            }    
       } 
      
       return (m_dic.find_tableXML(xml_tname) != null);
       
  }
  catch (Exception e) {
      return false;
  }
}

//protected boolean AddXMLHeader(Row r, String tname1, AGS_Dictionary.Lang lang) {
// try {
//     
//        List<String> l;
//        Iterator it;
//        String fname1;
//        String fname2;
//        boolean TableFound = false;
//        boolean FieldFound = false;
//        
//        l = new ArrayList<String>();
//        it = header1.iterator();
//        
//        TableFound = (m_dic.find_table(tname1, lang) != null );
//                
//        do {
//            fname1 = it.next().toString();
//            fname2 = fname1;
//            if (TableFound) {
//                FieldFound = (m_dic.find_field(fname1, lang) != null);
//                    if (FieldFound) {
//                        fname2 = m_dic.fieldXML_name();
//                    } else { 
//                        fname2 = m_dic.format_XMLTry(lang);
//                    }
//            }
//            
//        l.add(fname2);
//        } while(it.hasNext());
//
//        return l;
// }
// catch (Exception e) {
////     m_log(Level.SEVERE, e.getMessage());      
//     return null;
// }
//}

protected List<String> convertHeaderXML(Collection header1, String tname1, AGS_Dictionary.Lang lang) {
 try {
     
        List<String> l;
        Iterator it;
        String fname1;
        String fname2;
        boolean TableFound = false;
        boolean FieldFound = false;
        
        l = new ArrayList<String>();
        it = header1.iterator();
        
        TableFound = (m_dic.find_table(tname1, lang) != null );
                
        do {
            fname1 = it.next().toString();
            fname2 = fname1;
            if (TableFound) {
                FieldFound = (m_dic.find_field(fname1, lang) != null);
                    if (FieldFound) {
                        fname2 = m_dic.fieldXML_name();
                    } else { 
                        fname2 = m_dic.format_XMLTry(tname1, fname2);
                    }
            }
            
        l.add(fname2);
        } while(it.hasNext());

        return l;
 }
 catch (Exception e) {
//     m_log(Level.SEVERE, e.getMessage());      
     return null;
 }
}

protected int addTable(String xml_tname, String xml_fname, String fvalue) {
return -1;
}
protected int addHoles(Node n1){
    return -1;
}
protected int processQueryNode(Node n1) {
    return -1;
}
protected int processKMLNode( Node n1) {
    return -1;
}
}

