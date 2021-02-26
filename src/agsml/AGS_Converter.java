/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agsml;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JTextArea;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import static agsml.Base_Class.log;

/**
 *
 * @author thomsonsj
 */
public class AGS_Converter extends Thread {
   //private TextAreaHandler mTextAreaHandler;
   private Logger log;
   private PropertyBag m_pb;
   
   public enum AGS_DataSourceType {
       None, AGS, SQLServer, AccessDb
   }  
//   static {
//       
//        log = Logger.getLogger("agsmlConverter" );
//        log.setUseParentHandlers(false);
//        
//        ConsoleHandler handler = new ConsoleHandler();
//        handler.setFormatter(new SimpleFormatter() {
//        private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";
//
//        @Override
//        public synchronized String format(LogRecord lr) {
//              return String.format(format,
//                      new Date(lr.getMillis()),
//                      lr.getLevel().getLocalizedName(),
//                      lr.getMessage()
//              );
//          }
//      });
//     
//     log.addHandler(handler); 
//         
//    }
   
   public AGS_Converter(){
 //   mTextAreaHandler = new TextAreaHandler(); 
 //   mTextAreaHandler.setFormatter(new SimpleFormatter() {
 //       private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";
 //       @Override
 //       public synchronized String format(LogRecord lr) {
 //             return String.format(format,
  //                    new Date(lr.getMillis()),
 //                     lr.getLevel().getLocalizedName(),
 //                     lr.getMessage()
 //             );
 //         }
 //       }
 //       );
 //   log = Logger.getLogger("AGS_Converter");
 //   log.addHandler(mTextAreaHandler);
    m_pb = new PropertyBag();
   }
   
 //  public void set_LoggerTextArea(JTextArea textArea, Level level) {
 //      mTextAreaHandler.textArea = textArea;
 //      mTextAreaHandler.setLevel(level);
 //  }
 
   public void setLogger(Logger Log) {
       log = Log;
    }
   public static agsml.Constants.AGSVersion getAGSVersion(String ags_file) {
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
  

  public void AGS_to_AGSML(String ags_file, String xml_filefolder, String xml_dictionary){
        AGS_to_AGSML(ags_file, xml_filefolder, xml_dictionary,Constants.AGS_DATASTRUCTURESERIESID_DEFAULT,"");
    } 
  
  public void AGS_to_AGSML(String ags_file, String xml_filefolder, String xml_dictionary, String AGSMLDataStructureSeriesId) {
        AGS_to_AGSML (ags_file, xml_filefolder, xml_dictionary, AGSMLDataStructureSeriesId, "");
    }      
 
   public boolean AGS_to_AGSML(String ags_file, String xml_filefolder, String xml_dictionary, String AGSMLDataStructureSeriesId, String xml_hole_file) {
   
      try {    
           
           AGS_XML_Writer axw = new AGS_XML_Writer();
           axw.setLogger(log);
           if (!xml_filefolder.isEmpty()) {
                axw.setOutputFileFolder(xml_filefolder);
                 if (!ags_file.isEmpty()) {
                    axw.setAGSDataSource(ags_file);  
                      if (!xml_dictionary.isEmpty()) {
                        Constants.AGSVersion agsversion = getAGSVersion(ags_file);
                        axw.setAGSDictionary(xml_dictionary,agsversion.toId());
                        axw.getAGSDictionary();
                        
                        axw.setAGSDataStructureSeries(xml_dictionary, AGSMLDataStructureSeriesId); 
                        axw.getAGSDataStructure();
                        if (!xml_hole_file.isEmpty()) {
                            /* open hole section file and find holes node */
                            XML_DOM holes = new XML_DOM (xml_hole_file);
                            NodeList nl1 = holes.doc().getElementsByTagName(Constants.AGS_HOLEID);
                            axw.setHoleList(nl1);
                        }
                        
                    axw.getAGSReader();
                    axw.Process();
                  //  axw.closeFile();
                    return true;
                    }
                }
            }
        return false;
        }
       
        catch (Exception e) {
          log.log(Level.SEVERE, e.getMessage()); 
          return false;
               
        }
 }  

 public void SQL_to_AGSML(String source, String xml_outputfolder, String xml_dictionary) {
   SQL_to_AGSML (source, xml_outputfolder, xml_dictionary,"","") ;  
 } 
  
 public void SQL_to_AGSML(String source, String xml_outputfolder, String xml_dictionary, String AGSMLDataStructureSeriesId) {
   SQL_to_AGSML (source, xml_outputfolder, xml_dictionary, AGSMLDataStructureSeriesId,"");  
 }
  public boolean SQL_to_AGSML(String dbsource, String xml_filefolder, String xml_dictionary, String AGSMLDataStructureSeriesId, String xml_hole_file) {
   
      try {   
                              
                SQL_XML_Writer sxw = new SQL_XML_Writer();
                sxw.setLogger(log);
                 if (!xml_filefolder.isEmpty()) {
                    sxw.setOutputFileFolder(xml_filefolder);
                        if (!dbsource.isEmpty()) {
                            sxw.setAGSDataSource(dbsource);  
                            sxw.getAGSReader();  
                            Constants.AGSVersion agsversion = sxw.findAGSVersion();
                            if (!xml_dictionary.isEmpty()) {
                                sxw.setAGSDictionary(xml_dictionary,agsversion.toId());
                                sxw.getAGSDictionary();
                                    if (AGSMLDataStructureSeriesId.isEmpty()) {
                                        AGSMLDataStructureSeriesId = "Project"; 
                                    }
                                    sxw.setAGSDataStructureSeries(xml_dictionary, AGSMLDataStructureSeriesId); 
                                    sxw.getAGSDataStructure();

                                   if (!xml_hole_file.isEmpty()) {
                                      /* open hole section file and find holes node */
                                       XML_DOM holes = new XML_DOM (xml_hole_file);
                                       NodeList nl1 = holes.doc().getElementsByTagName("HoleId");
                                       sxw.setHoleList(nl1);
                                   } 
                                  
                            sxw.Process();
                            sxw.closeFile();
                            return true;
                            }  
                        }
                 }
                 return false;
      }         
        
    catch (Exception e) {
           log.log(Level.SEVERE, e.getMessage());            
           return false;    
   }  
  }
  
  public void AGS_to_AGSML(Node DataStructure) {
      
          
    try {   
            if (DataStructure.getNodeName().equalsIgnoreCase(Base_Writer.AGS_DATASTRUCTURE)) {
                
                AGS_XML_Writer axw = new AGS_XML_Writer (); 
                axw.setLogger(log);
                // Copy properties from Datastructure node to AGS_XML_Writer'
                NamedNodeMap nodeAttribs = DataStructure.getAttributes();
                axw.setProperties (nodeAttribs); 
                axw.getHoleList(DataStructure);
                axw.getAGSDictionary();
                axw.getAGSDataStructure();
                axw.getAGSReader();
                axw.Process();
                log.log(Level.SEVERE, DataStructure.getLocalName() + "Processed");
            }
                              
                               
        }
        catch (Exception e) {
           log.log(Level.SEVERE, e.getMessage());            
               
   }
  }
  public String getAGSML(String ags_data, Node AGSDictionary, Node AGSMLDataStructure) {
   try {   
                String s1 = "";
                AGS_XML_Writer axw = new AGS_XML_Writer();
                axw.setLogger(log);
                
               if (!ags_data.isEmpty()) {
                    axw.setAGSDataSource(ags_data);
                    axw.setOutputFileFolder("NONE");
                    axw.getAGSDictionary(AGSDictionary);
                    axw.getAGSDataStructure(AGSMLDataStructure);
                    axw.getAGSReader();
                    axw.Process();
                    s1 = axw.toString();
                    }
        return s1;
        }
       
        catch (Exception e) {
          log.log(Level.SEVERE, e.getMessage());       
          return null;     
        } 
 }  


 public void setProperties(Node node) {
    try {
        
        Element e1 = (Element) node;
        m_pb.setProperties(e1.getAttributes());
        
        NodeList nodeList = node.getChildNodes();
            
             for (int i = 0; i < nodeList.getLength(); i++)     {
                 Node n1 = nodeList.item(i);
                     if (n1.getNodeType() == Node.ELEMENT_NODE) {
                         String nodeName = n1.getNodeName();
                         String nodeValue = n1.getTextContent();

                         e1 = (Element) n1;
                        m_pb.setProperties(e1.getAttributes());
                         
                         if (nodeName.equals(Base_Writer.AGS_DATASOURCE)) {
                             m_pb.setProperty (Base_Writer.AGS_DATASOURCE,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_DATASTRUCTUREFILENAME)) {
                             m_pb.setProperty (Base_Writer.AGS_DATASTRUCTUREFILENAME,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_DATASTRUCTUREID)) {
                             m_pb.setProperty (Base_Writer.AGS_DATASTRUCTUREID,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_DATASTRUCTURESERIESID)) {
                             m_pb.setProperty (Base_Writer.AGS_DATASTRUCTURESERIESID,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_DICTIONARYFILENAME)) {
                             m_pb.setProperty (Base_Writer.AGS_DICTIONARYFILENAME,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_DICTIONARYID)) {
                             m_pb.setProperty (Base_Writer.AGS_DICTIONARYID,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_OUTPUTFIELD)) {
                             m_pb.setProperty (Base_Writer.AGS_DATASTRUCTURE,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_OUTPUTFILE)) {
                             m_pb.setProperty (Base_Writer.AGS_OUTPUTFILE,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_OUTPUTFILEBASEDONFIELD)) {
                             m_pb.setProperty (Base_Writer.AGS_OUTPUTFILEBASEDONFIELD,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_OUTPUTFOLDER)) {
                             m_pb.setProperty (Base_Writer.AGS_OUTPUTFOLDER,nodeValue);
                         }
                         if (nodeName.equals(Base_Writer.AGS_PROJECTID)) {
                             m_pb.setProperty (Base_Writer.AGS_PROJECTID,nodeValue);
                         }
                         if (nodeName.equals(XML_Writer.AGS_STYLESHEET)) {
                             m_pb.setProperty (XML_Writer.AGS_STYLESHEET,nodeValue);
                         }
                         if (nodeName.equals(XML_Writer.AGS_HTMLPATH)) {
                             m_pb.setProperty (XML_Writer.AGS_HTMLPATH,nodeValue);
                         }
                         if (nodeName.equals(XML_Writer.AGS_STRICTLYONLY)) {
                             m_pb.setProperty (XML_Writer.AGS_STRICTLYONLY,nodeValue);
                         }
                     }
             }  
    } catch (Exception e) {
                 log.log(Level.SEVERE, e.getMessage());           
    }
 }
 
 public boolean try_ProcessFile() {
     
     String xml_process_file = m_pb.getProperty(agsml.Constants.AGS_PROCESSFILE);
     
     if (!xml_process_file.isEmpty()) {
     return Process (xml_process_file);
     } else {
     return false;
     }
     
 }
 
 public boolean try_AGS_to_AGSML(){
     
     String ags_file = m_pb.getProperty(agsml.Constants.AGS_DATASOURCE); 
     String xml_filefolder = m_pb.getProperty(agsml.Constants.AGS_OUTPUTFILEFOLDER); 
     String xml_dictionary = m_pb.getProperty(agsml.Constants.AGS_DICTIONARYFILENAME); 
     String AGSMLDataStructureSeriesId = m_pb.getProperty(agsml.Constants.AGS_DATASTRUCTURESERIESID); 
     String xml_hole_file = m_pb.getProperty(agsml.Constants.AGS_HOLELISTFILE);
     
     AGS_DataSourceType dst = getDataSourceType(ags_file);
             
     if (dst != AGS_DataSourceType.AGS) {
         return false;
     }
     
     return AGS_to_AGSML (ags_file, 
                       xml_filefolder, 
                       xml_dictionary, 
                       AGSMLDataStructureSeriesId, 
                       xml_hole_file);
     
 }
 
 public boolean try_SQL_to_AGSML(){
     
     String datasource = m_pb.getProperty(agsml.Constants.AGS_DATASOURCE); 
     String xml_filefolder = m_pb.getProperty(agsml.Constants.AGS_OUTPUTFILEFOLDER); 
     String xml_dictionary = m_pb.getProperty(agsml.Constants.AGS_DICTIONARYFILENAME); 
     String AGSMLDataStructureSeriesId = m_pb.getProperty(agsml.Constants.AGS_DATASTRUCTURESERIESID); 
     String xml_hole_file = m_pb.getProperty(agsml.Constants.AGS_HOLELISTFILE);
     
     AGS_DataSourceType dst = getDataSourceType(datasource);
             
     if (dst != AGS_DataSourceType.AccessDb && dst != AGS_DataSourceType.SQLServer) {
         return false;
     }
     
     return SQL_to_AGSML (datasource, 
                       xml_filefolder, 
                       xml_dictionary, 
                       AGSMLDataStructureSeriesId, 
                       xml_hole_file);
  }
 
 public void setProperty(String name, String value) {
     m_pb.setProperty(name, value);
 }
 
 public String getProperty (String name) {
    return m_pb.getProperty(name);
 }
 public boolean Process(String xml_process_file) {
      
     if (xml_process_file.isEmpty()) {
         return false;
     }
            
    try {   
         
            AGS_Process ap =  new AGS_Process(xml_process_file);
            ap.setLogger(log);
            
            //seach root node for global properties
            setProperties (ap.RootNode());
            
            NodeList nodeList = ap.RootNode().getChildNodes();
            
             for (int i = 0; i < nodeList.getLength(); i++)     {
                 Node n1 = nodeList.item(i);
                     if (n1.getNodeType() == Node.ELEMENT_NODE) {
                         String nodeName = n1.getNodeName();
                         String nodeValue = n1.getTextContent();
                         Element e1 = (Element) n1;
                         
                        if (nodeName.equalsIgnoreCase(agsml.Constants.AGS_DATASTRUCTURE)) {
                               
                                 // overrite or add new any local node properties 
                                // from datrastructure node
                                setProperties (n1);
                                m_pb.passProperties(n1);
                                 // Add all properties found to node n1 
                                 // as attributes to be used by called function
                                String s1 = e1.getAttribute(agsml.Constants.AGS_DATASTRUCTURETYPE);
                                AGS_Process.DataStructureType ds1 = ap.getDataStructureType(s1);;
                                if (ds1.equals(AGS_Process.DataStructureType.NotKnown)) {
                                    String source = m_pb.getAttributeValue(n1, Base_Writer.AGS_DATASOURCE);
                                    String output = m_pb.getAttributeValue(n1,Base_Writer.AGS_OUTPUTFILE);
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
        return true;
    
        } 
        catch (Exception e) {
           log.log(Level.SEVERE, e.getMessage());            
           return false ;   
        }
   } 
  public void SQL_to_AGSML(Node DataStructure) {
      
     try {   
                               
            if (DataStructure.getNodeName().equalsIgnoreCase(Base_Writer.AGS_DATASTRUCTURE)) {
                    NamedNodeMap nodeAttribs = DataStructure.getAttributes();
                    m_pb.setProperties(nodeAttribs);
                    
                    SQL_XML_Writer sxw = new SQL_XML_Writer();
                    
                    sxw.setLogger(log);
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
           log.log(Level.SEVERE, e.getMessage());            
               
   }
 } 
  
 public void SQL_to_KML(Node DataStructure) {
      
     try {   
            SQL_KML_Writer skw = null;
            
            
            if (DataStructure.getNodeName().equalsIgnoreCase(agsml.Constants.AGS_DATASTRUCTURE)) {
                NamedNodeMap nodeAttribs = DataStructure.getAttributes();
                m_pb.setProperties (nodeAttribs);  
                
                String xml_dictionaryfile = m_pb.getProperty(Base_Writer.AGS_DICTIONARYFILENAME);
                String AGSDictionaryId = m_pb.getProperty(Base_Writer.AGS_DICTIONARYID);
                if (AGSDictionaryId == null) {
                    AGSDictionaryId="Default";
                }
                
                String xml_datastructurefile = m_pb.getProperty(Base_Writer.AGS_DATASTRUCTUREFILENAME);
                String AGSMLDataStructureId = m_pb.getProperty (Base_Writer.AGS_DATASTRUCTUREID);
                String stylesheet =  m_pb.getProperty (XML_Writer.AGS_STYLESHEET); 
                String source = m_pb.getProperty (Base_Writer.AGS_DATASOURCE);
                String xml_output = m_pb.getProperty(Base_Writer.AGS_OUTPUTFILE);
                
                if (xml_output == null) {
                    xml_output = m_pb.getProperty(Base_Writer.AGS_OUTPUTFOLDER);
                }
                
                if (source != null && xml_output != null && xml_dictionaryfile != null) { 
                                     
                    skw = new SQL_KML_Writer(xml_output,  stylesheet);
                    skw.setLogger(log);
                    
                    skw.setAGSDictionaryFile(xml_dictionaryfile, AGSDictionaryId);
                    
                    if (AGSMLDataStructureId != null && xml_datastructurefile != null) {
                    skw.setAGSDataStructure(xml_datastructurefile,AGSMLDataStructureId); 
                    skw.getAGSDataStructure();
                    } else {
                    skw.getAGSDataStructure(DataStructure);
                    }
                  
                    String xml_hole_file = m_pb.getProperty (Base_Writer.AGS_HOLELIST);
                
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
           log.log(Level.SEVERE, e.getMessage());            
               
   }
 } 
 public AGS_DataSourceType getDataSourceType (String source) {
     
     String s1 = source.toLowerCase();
     
     if (s1.contains(".gpj")){
         return AGS_DataSourceType.AccessDb;
     }
    
     if (s1.contains(".mdb") || s1.contains(".accdb")){
         return AGS_DataSourceType.AccessDb;
     }
     
     
     if (s1.contains(".ags")){
         return AGS_DataSourceType.AGS;
     }
     
     if (s1.contains("jdbc:")){
         return AGS_DataSourceType.SQLServer;
     }
     
     return AGS_DataSourceType.None;
  
 }
 
   @Override
 public void run() { 
        
        try { 
              try_ProcessFile();  
              try_AGS_to_AGSML();
              try_SQL_to_AGSML();
           
        } catch (Exception e) {
             e.printStackTrace();
        }
     }
}


