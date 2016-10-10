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
import org.w3c.dom.NodeList;
/**
 *
 * @author thomsonsj
 */
public class AGSMLConverter {
     private static final Logger log = Logger.getLogger( AGSMLConverter.class.getName() );
     public TextAreaHandler textAreaHandler = new TextAreaHandler();
     
   public AGSMLConverter(){
    log.addHandler(textAreaHandler);
    }
   public void set_LoggerTextArea(JTextArea textArea) {
       textAreaHandler.textArea = textArea;
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
          AGSLineReader lr = new AGSLineReader(tf.BufferedReader());
          printAGSLineReader(lr);
 }
  public static void printAGSLineReader(AGSLineReader lr) {
       int result;
       do {
              result = lr.ReadLine();
              if (result == lr.intTableName()) {
                 System.out.print("\"" + lr.get_tablename() + "\"");
                  System.out.print("\n\r");
              }
              if (result == lr.intHeader()) {
                 print_collection(lr.get_headers());
                  System.out.print("\n\r");
              }

              if (result == lr.intUnits()) {
                  print_collection(lr.get_units());
                   System.out.print("\n\r");
              }
              if (result == lr.intData()) {
                  print_collection(lr.get_data());
                   System.out.print("\n\r");
              }
           } while (result != lr.intError());
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
          AGSTextReader tr = new AGSTextReader(AGS_str);
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
      AGSLineReader lr = new AGSLineReader(ags_file);
      AGS_XML_Writer axw = new AGS_XML_Writer(xml_file);
      axw.openRoot();
      axw.AGS_to_XML(lr);
      axw.closeRoot();
      axw.close();

   }
  public static void AGS_to_XML_Nest(String ags_file, String xml_file){
      AGSLineReader lr = new AGSLineReader(ags_file);
      AGS_XML_Writer axw = new AGS_XML_Writer(xml_file);
      axw.openRoot();
      axw.AGS_to_XML_Nest(lr);
      axw.closeRoot();
      axw.close();

   }
  public static void AGS_to_XML_Nest(String ags_file, String xml_file, String xml_dictionary){
      AGSLineReader lr = new AGSLineReader(ags_file);
      AGS_XML_Writer axw = new AGS_XML_Writer(xml_file);
      axw.setAGSDictionary(xml_dictionary);
      axw.openRoot();
      axw.AGS_to_XML_Nest(lr);
      axw.closeRoot();
      axw.close();

   }
  public static void AGS_to_AGSML(String ags_file, String xml_file, String xml_dictionary){
      AGS_to_AGSML(ags_file, xml_file, xml_dictionary,"");
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
  
  public static void SQL_to_AGSML (String db_file, String xml_file, String xml_dictionary, String xml_hole_file) {
   try {
      if (!db_file.isEmpty()) {
          
         AGSSQLReader sr = new AGSSQLReader(db_file);
     
      if (!xml_file.isEmpty()) {
         SQL_XML_Writer sxw = new SQL_XML_Writer(xml_file);
         
            if (!xml_dictionary.isEmpty()) {
                sxw.setAGSDictionaryFile(xml_dictionary, "Default"); 
                sxw.setAGSDataStructureFile(xml_dictionary,"Project");
            }
      
            if (!xml_hole_file.isEmpty()) {
                /* open hole section file and find holes node */
                XML_DOM holes = new XML_DOM (xml_hole_file);
                NodeList nl1 = holes.doc().getElementsByTagName("HoleId");
                sxw.setHoleList(nl1);
                NodeList nl2 = holes.doc().getElementsByTagName("DataStructureAGSMLId");
                Node n1 = nl2.item(0);
                String s1= n1.getTextContent();
                sxw.setAGSDataStructure(s1);
            }
       
      sxw.SQL_AGSML(sr);
      sxw.close();
      }
      }
   } catch (Exception e) {
   log.log(Level.SEVERE, e.getMessage());
   }
   }    
    
  public static void AGS_to_AGSML(String ags_file, String xml_file, String xml_dictionary, String xml_hole_file){
        
      if (!ags_file.isEmpty()) {
        AGSLineReader lr = new AGSLineReader(ags_file);
           
        if (!xml_file.isEmpty()) {
          AGS_XML_Writer axw = new AGS_XML_Writer(xml_file);

              if (!xml_dictionary.isEmpty()) {
                  axw.setAGSDictionaryFile(xml_dictionary, "Default"); 
                  axw.setAGSDataStructureFile(xml_dictionary,"Project");
             }

              if (!xml_hole_file.isEmpty()) {
                  /* open hole section file and find holes node */
                  XML_DOM holes = new XML_DOM (xml_hole_file);
                  NodeList nl1 = holes.doc().getElementsByTagName("HoleId");
                  axw.setHoleList(nl1);
                  NodeList nl2 = holes.doc().getElementsByTagName("DataStructureAGSMLId");
                  Node n1 = nl2.item(0);
                  String s1= n1.getTextContent();
                  axw.setAGSDataStructure(s1);
              }
     
        axw.AGS_to_AGSML(lr);
        axw.close();
        }
      }
    }
  
  
 public static void SQL_to_AGSMLFiles(String source, String xml_folder, String xml_dictionary,String xml_hole_file) {
  try {   
       if (!source.isEmpty()) {
            AGSSQLReader sr = new AGSSQLReader(source);
            sr.setLogger(log);
         
            if (!xml_folder.isEmpty()) {
                 SQL_XML_Writer sxw = new SQL_XML_Writer(xml_folder);
                 sxw.setLogger(log);


                 if (!xml_dictionary.isEmpty()) {
                     sxw.setAGSDictionaryFile(xml_dictionary, "Default"); 
                     sxw.setAGSDataStructureFile(xml_dictionary,"Project");
                 }

                 if (!xml_hole_file.isEmpty()) {
                     /* open hole section file and find holes node */
                     XML_DOM holes = new XML_DOM (xml_hole_file);
                     NodeList nl1 = holes.doc().getElementsByTagName("HoleId");
                     sxw.setHoleList(nl1);
                     NodeList nl2 = holes.doc().getElementsByTagName("DataStructureAGSMLId");
                     Node n1 = nl2.item(0);
                     String s1= n1.getTextContent();
                     sxw.setAGSDataStructure(s1);
                 }
          
                 
                 
            sxw.SQL_AGSML(sr);
            sxw.close();
            }
       }  
    }
        
    catch (Exception e) {
           log.log(Level.SEVERE, e.getMessage());            
               
   }
 }
  public static void SQL_to_AGSMLFiles(String source, String xml_folder, String xml_process_file) {
  try {   
       if (!source.isEmpty()) {
            AGSSQLReader sr = new AGSSQLReader(source);
            sr.setLogger(log);
         
       if (!xml_folder.isEmpty()) {
            SQL_XML_Writer sxw = new SQL_XML_Writer(xml_folder);
            sxw.setLogger(log);
        
        if (!xml_process_file.isEmpty()) {
            AGS_Process ap =  new AGS_Process(xml_process_file);
                 
            Node n1= ap.m_root.getFirstChild();
            String s1;
            
            do {
                s1 = n1.getNodeName();  
                if (s1.equals(ap.AGS_Process_DATAQUERY)) {
                    
                
                sxw.processQueryNode(n1);
                }
                if (s1.equals(ap.AGS_Process_DATASTRUCTURE)) {
                
                    
                sxw.processAGSML(n1);
                sxw.SQL_AGSML(sr);    
                }                    
                n1 = n1.getNextSibling();
            } while (n1!=null);
            
            
             
            
            
        }    
        
       
      
             
        
        
        
        
        
        
        sxw.close();
      }
    }
  }
       
    catch (Exception e) {
           log.log(Level.SEVERE, e.getMessage());            
               
   }
 }
  public static void AGS_to_AGSMLFiles(String source, String xml_folder, String xml_dictionary, String xml_hole_file) {
  try {   
       if (!source.isEmpty()) {
         AGSLineReader alr = new AGSLineReader(source);
         alr.setLogger(log);
         
       if (!xml_folder.isEmpty()) {
            AGS_XML_Writer axw = new AGS_XML_Writer(xml_folder);
            axw.setLogger(log);
         
            axw.setAGSDictionary(xml_dictionary);
             
            if (!xml_hole_file.isEmpty()) {
            /* open hole section file and find holes node */
            XML_DOM holes = new XML_DOM (xml_hole_file);
            NodeList nl1 =holes.doc().getElementsByTagName("HoleId");
            axw.setHoleList(nl1);
            }
            
                      
                         
            axw.AGS_to_AGSML(alr);
      
            axw.close();
        }
        }
  }
       
  catch (Exception e) {
          log.log(Level.SEVERE, e.getMessage());       
               
   }
 }
 }

