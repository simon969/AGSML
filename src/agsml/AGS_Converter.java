/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agsml;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

/**
 *
 * @author thomsonsj
 */
public class AGS_Converter extends Base_Class {
    private TextAreaHandler textAreaHandler;
     
   public AGS_Converter(){
    TextAreaHandler textAreaHandler = new TextAreaHandler();   
    m_log.addHandler(textAreaHandler);
   }
   public void set_LoggerTextArea(JTextArea textArea, Level level) {
       textAreaHandler.textArea = textArea;
       textAreaHandler.setLevel(level);
   }
   
   public static AGS_Dictionary.AGSVersion getAGSVersion(String ags_file) {
       AGS_ReaderText art = new AGS_ReaderText (ags_file);
       return art.AGSVersion();
   }
   public static void AGS_XML_filelist (String flist_file, String home_folder) {
        String ags_fname;
        String xml_fname;

        TextFile tf1 = new TextFile(flist_file);

        do {
            ags_fname = tf1.readLine();

                if (ags_fname !=null) {
                int iloc =  ags_fname.lastIndexOf("\\")  ;
                 xml_fname = ags_fname.substring(ags_fname.lastIndexOf("\\")+1, ags_fname.length()-4);
                 xml_fname = home_folder + xml_fname + ".xml";

                 System.out.print("\"" + ags_fname + "\" to \"" + xml_fname + "\"");
                 AGS_to_XML(ags_fname, xml_fname);
                 System.out.print(" OK \n\r");

                 };
          } while (ags_fname != null);
   }
  public static void AGS_XMLNest_filelist (String flist_file, String home_folder) {
        String ags_fname;
        String xml_fname;

        TextFile tf1 = new TextFile(flist_file);

        do {
            ags_fname = tf1.readLine();

                if (ags_fname !=null) {
                int iloc =  ags_fname.lastIndexOf("\\")  ;
                 xml_fname = ags_fname.substring(ags_fname.lastIndexOf("\\")+1, ags_fname.length()-4);
                 xml_fname = home_folder + xml_fname + "_nest.xml";

                 System.out.print("\"" + ags_fname + "\" to \"" + xml_fname + "\"");
                 AGS_to_XML_Nest(ags_fname, xml_fname);
                 System.out.print(" OK \n\r");

                 };
          } while (ags_fname != null);
   }
  public static void AGSLineReader_file(String fname) {
          TextFile tf = new TextFile(fname);
          AGS_ReaderLine lr = new AGS_ReaderLine31(tf.BufferedReader());
          printAGSLineReader(lr);
 }
  public static void printAGSLineReader(AGS_ReaderLine lr) {
       AGS_ReaderLine.LineType result;
       do {
              result = lr.ReadLine();
              if (result == AGS_ReaderLine.LineType.tableName) {
                 System.out.print("\"" + lr.get_tablename() + "\"");
                  System.out.print("\n\r");
              }
              if (result == AGS_ReaderLine.LineType.tableHeader) {
                 print_collection(lr.get_headers());
                  System.out.print("\n\r");
              }

              if (result == AGS_ReaderLine.LineType.tableUnits) {
                  print_collection(lr.get_units());
                   System.out.print("\n\r");
              }
              if (result == AGS_ReaderLine.LineType.rowData) {
                  print_collection(lr.get_data());
                   System.out.print("\n\r");
              }
           } while (result != AGS_ReaderLine.LineType.Error);
   }
  private static void print_collection (Collection c) {
        Iterator itr = c.iterator();
        print_itr (itr);
  }
  private static void print_itr (Iterator itr) {
                    while (itr.hasNext()){
                         System.out.print("\"" + itr.next().toString() + "\"");
                         if (itr.hasNext()){System.out.print(",");}
                      }

  }
  public static void testAGSTextReader() {
        // TODO code application logic here
          String file_name = "g:/Netbeans/AGS_Files/bhdisk2.ags";
          String AGS_str=null;
          TextFile tf = new TextFile(file_name);
          AGS_str = tf.to_string();
          AGS_ReaderText tr = new AGS_ReaderText(AGS_str);
          tr.first_table();
          do {
                    System.out.print("\n\r" + tr.table_name() + "\n\r");
                    Collection c = tr.get_headers();
                    Iterator itr = c.iterator();
                        print_itr (itr);
                           System.out.print("\n");
                        List l = tr.first_data();
                        do {
                           itr = l.iterator();
                                print_itr (itr);
                           l = tr.next_data();
                           System.out.print("\n");
                        } while (tr.data_end() == false);
          }
          while (tr.next_table());
  }
  public static void AGS_to_XML(String ags_file, String xml_file){
      AGS_ReaderLine lr = new AGS_ReaderLine31(ags_file);
      AGS_XML_Writer axw = new AGS_XML_Writer(xml_file);
      axw.openRoot();
      axw.AGS_to_XML(lr);
      axw.closeRoot();
      axw.closeFile();

   }
  public static void AGS_to_XML_Nest(String ags_file, String xml_file){
      AGS_ReaderLine lr = new AGS_ReaderLine31(ags_file);
      AGS_XML_Writer axw = new AGS_XML_Writer(xml_file);
      axw.openRoot();
      axw.AGS_to_XML_Nest(lr);
      axw.closeRoot();
      axw.closeFile();

   }
  public static void AGS_to_XML_Nest(String ags_file, String xml_file, String xml_dictionary){
      AGS_ReaderLine lr = new AGS_ReaderLine31(ags_file);
      AGS_XML_Writer axw = new AGS_XML_Writer(xml_file);
      axw.setAGSDictionary(xml_dictionary);
      axw.openRoot();
      axw.AGS_to_XML_Nest(lr);
      axw.closeRoot();
      axw.closeFile();

   }

  public static void AGSLineReader_filelist (String flist_file) {
        String fname;
        TextFile tf1 = new TextFile(flist_file);
        do {
            fname = tf1.readLine();
                if (fname !=null) {
                System.out.print("\"" + fname + "\"");
                System.out.print("\n\r");
              AGSLineReader_file(fname);
                }
         } while (fname != null);
   }
  

  public void AGS_to_AGSML(String ags_file, String xml_file, String xml_dictionary){
        AGS_to_AGSML(ags_file, xml_file, xml_dictionary,"","","");
    } 
   public void AGS_to_AGSML(String ags_file, String xml_folder, String xml_dictionary, String xml_hole_file) {
        AGS_to_AGSML (ags_file, xml_folder,xml_dictionary, "", "", xml_hole_file);
 }
  
  public void AGS_to_AGSML(String ags_file, String xml_filefolder, String xml_dictionary, String AGSDictionaryId, String AGSMLDataStructureId) {
        AGS_to_AGSML (ags_file, xml_filefolder, xml_dictionary, AGSDictionaryId, AGSMLDataStructureId, "");
    }      
 
  public void AGS_to_AGSML(String ags_file, String xml_filefolder, String xml_dictionary, String AGSDictionaryId, String AGSMLDataStructureId,String xml_hole_file) {
   
      try {   
           if (!xml_filefolder.isEmpty()) {
               
                AGS_XML_Writer axw = new AGS_XML_Writer();
                             
                axw.setAGSDataSource(ags_file);
                axw.setOutputFileFolder(xml_filefolder);
                
                 if (!ags_file.isEmpty()) {
                    if (!xml_dictionary.isEmpty()) {
                        if (AGSDictionaryId.isEmpty()) {
                            AGSDictionaryId = "Default";
                        }
                        axw.setAGSDictionary(xml_dictionary,AGSDictionaryId);
                        axw.getAGSDictionary();

                        if (AGSMLDataStructureId.isEmpty()) {
                            AGSMLDataStructureId = "Project"; 
                        }
                        axw.setAGSDataStructure(xml_dictionary, AGSMLDataStructureId); 
                        axw.getAGSDataStructure();

                        if (!xml_hole_file.isEmpty()) {
                        /* open hole section file and find holes node */
                        XML_DOM holes = new XML_DOM (xml_hole_file);
                        NodeList nl1 = holes.doc().getElementsByTagName("HoleId");
                        axw.setHoleList(nl1);
                        }
                        
                    axw.getAGSReader();
                    axw.Process();
                    axw.closeFile(); 
                    }
                }
            }
        }
       
        catch (Exception e) {
          m_log.log(Level.SEVERE, e.getMessage());       
               
        }
 }  

 public void SQL_to_AGSML(String source, String xml_outputfolder, String xml_dictionary) {
   SQL_to_AGSML (source, xml_outputfolder, xml_dictionary,"","","") ;  
 } 
  
 public void SQL_to_AGSML(String source, String xml_outputfolder, String xml_dictionary,String xml_hole_file) {
   SQL_to_AGSML (source, xml_outputfolder, xml_dictionary,"","",xml_hole_file) ;  
 }
 public void SQL_to_AGSML(String source, String xml_outputfolder, String xml_dictionary, String AGSDictionaryId, String AGSMLDataStructureId) {
   SQL_to_AGSML (source, xml_outputfolder, xml_dictionary,AGSDictionaryId,AGSMLDataStructureId,"");  
 }
  public void SQL_to_AGSML(String source, String xml_outputfilefolder, String xml_dictionary, String AGSDictionaryId, String AGSMLDataStructureId, String xml_hole_file) {
   
      try {   
            if (!source.isEmpty() && !xml_outputfilefolder.isEmpty() && !xml_dictionary.isEmpty()) {  
                
                 SQL_XML_Writer sxw = new SQL_XML_Writer();
                               
                    if (AGSDictionaryId.isEmpty()) {
                        AGSDictionaryId="Default";
                    }
        
                    if (AGSMLDataStructureId.isEmpty()) {
                        AGSMLDataStructureId ="Project" ;
                    }
       
                sxw.setAGSDictionary(xml_dictionary, AGSDictionaryId);
                sxw.getAGSDictionary();
                
                sxw.setAGSDataStructure(xml_dictionary,AGSMLDataStructureId); 
                sxw.getAGSDataStructure();
                
                sxw.setOutputFileFolder(xml_outputfilefolder);
                
                sxw.setAGSDataSource(source);
                
                    if (!xml_hole_file.isEmpty()) {
                             /* open hole section file and find holes node */
                        XML_DOM holes = new XML_DOM (xml_hole_file);
                        NodeList nl1 = holes.doc().getElementsByTagName("HoleId");
                        sxw.setHoleList(nl1);
                    } 
            
            sxw.getAGSReader();     
            sxw.Process();
            sxw.closeFile();
            }
       }  
   
        
    catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());            
               
   }  
  }
  
  public void AGS_to_AGSML(Node DataStructure) {
      
          
    try {   
            if (DataStructure.getNodeName().equalsIgnoreCase(Base_Writer.AGS_DATASTRUCTURE)) {
                
                AGS_XML_Writer axw = new AGS_XML_Writer (); 
                       
                // Copy properties from Datastructure node to AGS_XML_Writer'
                NamedNodeMap nodeAttribs = DataStructure.getAttributes();
                axw.setProperties (nodeAttribs); 
                axw.getHoleList(DataStructure);
                axw.getAGSDictionary();
                axw.getAGSDataStructure();
                axw.getAGSReader();
                axw.Process();
                m_log.log(Level.SEVERE, DataStructure.getLocalName() + "Processed");
            }
                              
                               
        }
        catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());            
               
   }
  }
  public String getAGSML(String ags_data, Node AGSDictionary, Node AGSMLDataStructure) {
   try {   
                String s1 = "";
                AGS_XML_Writer axw = new AGS_XML_Writer();
                axw.setLogger(m_log);
                
               if (!ags_data.isEmpty()) {
                    axw.setAGSDataSource(ags_data);
                    axw.setOutputFileFolder("NONE");
                    axw.getAGSDictionary(AGSDictionary);
                    axw.getAGSDataStructure(AGSMLDataStructure);
                    axw.getAGSReader();
                    axw.Process();
                    s1 = axw.toString();
                    axw.closeFile(); 
                    }
        return s1;
        }
       
        catch (Exception e) {
          m_log.log(Level.SEVERE, e.getMessage());       
          return null;     
        } 
 }    
 public void setProperties(Node node) {
    try {
        
        Element e1 = (Element) node;
        setProperties(e1.getAttributes());
        
        NodeList nodeList = node.getChildNodes();
            
             for (int i = 0; i < nodeList.getLength(); i++)     {
                 Node n1 = nodeList.item(i);
                     if (n1.getNodeType() == Node.ELEMENT_NODE) {
                         String nodeName = n1.getNodeName();
                         String nodeValue = n1.getTextContent();

                         e1 = (Element) n1;
                        setProperties(e1.getAttributes());
                         
                         if (nodeName.equals(Base_Writer.AGS_DATASOURCE)) {
                             setProperty (Base_Writer.AGS_DATASOURCE,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_DATASTRUCTUREFILENAME)) {
                             setProperty (Base_Writer.AGS_DATASTRUCTUREFILENAME,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_DATASTRUCTUREID)) {
                             setProperty (Base_Writer.AGS_DATASTRUCTUREID,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_DICTIONARYFILENAME)) {
                             setProperty (Base_Writer.AGS_DICTIONARYFILENAME,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_DICTIONARYID)) {
                             setProperty (Base_Writer.AGS_DICTIONARYID,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_OUTPUTFIELD)) {
                             setProperty (Base_Writer.AGS_DATASTRUCTURE,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_OUTPUTFILE)) {
                             setProperty (Base_Writer.AGS_OUTPUTFILE,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_OUTPUTFILEBASEDONFIELD)) {
                             setProperty (Base_Writer.AGS_OUTPUTFILEBASEDONFIELD,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_OUTPUTFOLDER)) {
                             setProperty (Base_Writer.AGS_OUTPUTFOLDER,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_PROJECTID)) {
                             setProperty (Base_Writer.AGS_PROJECTID,nodeValue);
                         }
                         if (nodeName.equals(XML_Writer.AGS_STYLESHEET)) {
                             setProperty (XML_Writer.AGS_STYLESHEET,nodeValue);
                         }
                         if (nodeName.equals(XML_Writer.AGS_HTMLPATH)) {
                             setProperty (XML_Writer.AGS_HTMLPATH,nodeValue);
                         }
                         if (nodeName.equals(XML_Writer.AGS_STRICTLYONLY)) {
                             setProperty (XML_Writer.AGS_STRICTLYONLY,nodeValue);
                         }
                     }
             }  
    } catch (Exception e) {
                 m_log.log(Level.SEVERE, e.getMessage());           
    }
 }
 public void Process(String xml_process_file) {
      
     if (xml_process_file.isEmpty()) {
         return;
     }
            
    try {   
         
            AGS_Process ap =  new AGS_Process(xml_process_file);
            ap.setLogger(m_log);
            
            //seach root node for global properties
            setProperties (ap.RootNode());
            
            NodeList nodeList = ap.RootNode().getChildNodes();
            
             for (int i = 0; i < nodeList.getLength(); i++)     {
                 Node n1 = nodeList.item(i);
                     if (n1.getNodeType() == Node.ELEMENT_NODE) {
                         String nodeName = n1.getNodeName();
                         String nodeValue = n1.getTextContent();
                         Element e1 = (Element) n1;
                         
                        if (nodeName.equalsIgnoreCase(AGS_Process.AGS_DATASTRUCTURE)) {
                               
                                 // overrite or add new any local node properties 
                                // from datrastructure node
                                setProperties (n1);
                                passProperties(n1);
                                 // Add all properties found to node n1 
                                 // as attributes to be used by called function
                                String s1 = e1.getAttribute(AGS_Process.AGS_DATASTRUCTURETYPE);
                                AGS_Process.DataStructureType ds1 = ap.getDataStructureType(s1);;
                                if (ds1.equals(AGS_Process.DataStructureType.NotKnown)) {
                                    String source = getAttributeValue(n1, Base_Writer.AGS_DATASOURCE);
                                    String output = getAttributeValue(n1,Base_Writer.AGS_OUTPUTFILE);
                                    //If the outputfile is empty it is most likely to be xml output
                                    //based on the borehole name. All other valid outputs would require a file name        
                                    if (output.isEmpty()) {
                                        output= "*.xml";
                                    }
                                    ds1= ap.getDataStructureType(source, output);
                                }
                                        
                                if (ds1.equals(AGS_Process.DataStructureType.SQLtoXML)) {
                                    SQL_to_AGSML(n1);
                                }
                                if (ds1.equals(AGS_Process.DataStructureType.SQLtoKML) ) {
                                    SQL_to_KML(n1);
                                }
                                if (ds1.equals(AGS_Process.DataStructureType.AGStoSQL)) {
                                 /* Not Implemented Yet
                                     AGS_to_SQL(n1, ap); */
                                } 
                                if (ds1.equals(AGS_Process.DataStructureType.AGStoXML)) {
                                    AGS_to_AGSML(n1);
                                } 
                        }
                    }
           
             }      
    
        } 
        catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());            
               
        }
   } 
  public void SQL_to_AGSML(Node DataStructure) {
      
     try {   
                               
            if (DataStructure.getNodeName().equalsIgnoreCase(Base_Writer.AGS_DATASTRUCTURE)) {
                    NamedNodeMap nodeAttribs = DataStructure.getAttributes();
                    setProperties(nodeAttribs);
                    
                    SQL_XML_Writer sxw = new SQL_XML_Writer();
                    
                    sxw.setLogger(m_log);
                    sxw.setProperties(nodeAttribs);
                    sxw.getAGSDictionary();
                    sxw.getHoleList(DataStructure);
                    sxw.getAGSDataStructure();
                    sxw.getAGSReader();
                    sxw.Process();
                    sxw.closeFile();
                }
            }
       catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());            
               
   }
 } 
  
 public void SQL_to_KML(Node DataStructure) {
      
     try {   
            SQL_KML_Writer skw = null;
            
            
            if (DataStructure.getNodeName().equalsIgnoreCase(XML_Writer.AGS_DATASTRUCTURE)) {
                NamedNodeMap nodeAttribs = DataStructure.getAttributes();
                setProperties (nodeAttribs);  
                
                String xml_dictionaryfile = getProperty(Base_Writer.AGS_DICTIONARYFILENAME);
                String AGSDictionaryId = getProperty(Base_Writer.AGS_DICTIONARYID);
                if (AGSDictionaryId == null) {
                    AGSDictionaryId="Default";
                }
                
                String xml_datastructurefile = getProperty(Base_Writer.AGS_DATASTRUCTUREFILENAME);
                String AGSMLDataStructureId = getProperty (Base_Writer.AGS_DATASTRUCTUREID);
                String stylesheet =  getProperty (XML_Writer.AGS_STYLESHEET); 
                String source = getProperty (Base_Writer.AGS_DATASOURCE);
                String xml_output = getProperty(Base_Writer.AGS_OUTPUTFILE);
                
                if (xml_output == null) {
                    xml_output = getProperty(Base_Writer.AGS_OUTPUTFOLDER);
                }
                
                if (source != null && xml_output != null && xml_dictionaryfile != null) { 
                                     
                    skw = new SQL_KML_Writer(xml_output,  stylesheet);
                    skw.setLogger(m_log);
                    
                    skw.setAGSDictionaryFile(xml_dictionaryfile, AGSDictionaryId);
                    
                    if (AGSMLDataStructureId != null && xml_datastructurefile != null) {
                    skw.setAGSDataStructure(xml_datastructurefile,AGSMLDataStructureId); 
                    skw.getAGSDataStructure();
                    } else {
                    skw.getAGSDataStructure(DataStructure);
                    }
                  
                    String xml_hole_file = getProperty (Base_Writer.AGS_HOLELIST);
                
                    if (xml_hole_file != null) {
                             /* open hole section file and find holes node */
                        XML_DOM holes = new XML_DOM (xml_hole_file);
                        NodeList nl1 = holes.doc().getElementsByTagName("HoleId");
                        skw.setHoleList(nl1);
                    } 

                    if (DataStructure.hasChildNodes()){
                      NodeList nodeList = DataStructure.getChildNodes();
                            for (int j = 0; j < nodeList.getLength(); j++) {
                                Node n1 = nodeList.item(j);
                                if (n1.getNodeType() == Node.ELEMENT_NODE) {
                                    String nodeName = n1.getNodeName();
                                    String nodeValue = n1.getTextContent();

                                    if (nodeName.equalsIgnoreCase(skw.AGS_DATASTRUCTUREID)) {
                                    skw.setAGSDataStructureId(nodeValue);
                                    }

                                    if (nodeName.equalsIgnoreCase(skw.AGS_HOLELIST)) {
                                    NodeList nl1 = n1.getChildNodes();
                                    skw.setHoleList(nl1);
                                    }
                                }
                            }
                    }
                    
                    skw.getAGSReader();
                    skw.Process();
                    skw.closeFile();
                }
            }
        }
      catch (Exception e) {
           m_log.log(Level.SEVERE, e.getMessage());            
               
   }
 }  
      
  
  }


