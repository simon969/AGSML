/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agsmlGenerator;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.LogManager;
import agsml.AGS_Client;
import agsml.AGS_Converter;
import agsml.AGS_Server;
import agsml.AGS_Data;
import java.util.Date;
import java.util.logging.LogRecord;
import agsml.Constants;

/**
 *
 * @author thomsonsj
 */
public class Main {
        
 //   }
      /**
     * @param args the command line arguments
     */
 public static AGS_Server as = null;
 public static Logger log = null;
 public static Properties config = null;
 private static WindowHandler wh = null; 
 
 public final static String ARG_GUI = "-gui";
 public final static String ARG_FILEUPLOAD = "-fileupload";
 public final static String ARG_PROCESS = "-process";
 public final static String ARG_AGS = "-ags";
 public final static String ARG_XML = "-xml";
 public final static String ARG_DICTIONARY = "-dic";
 public final static String ARG_DATASTRUCTURESERIES= "-ds";
 public final static String ARG_SERVER = "-server";
 public final static String ARG_START= "-start";
 public final static String ARG_STOP = "-stop";
 public final static String ARG_HELP = "-help";
 
 public static void main(String[] args) {   
        int i = 0;
        String arg;
        boolean vflag = false;
        String process_filename = "";
        String ags_filename = "";
        String dic_filename = "";
        String data_structure = "";
        String server_name = "";
        String xml_filename = "";
        int port = 0;
        
        config = loadProperties(Constants.CONFIG_FILENAME);
        initLogger();
        
        agsml.Main.config = config;
        agsml.Main.log = log;
        
        while (i < args.length) {
            
            arg = args[i++];

    // use this type of check for "wordy" arguments
            if (arg.equals(ARG_GUI)) {
                log.info("displaying GUI");
                vflag=true;
                showAGSMLGeneratorGUI(args);
            }
            
            if (arg.equals(ARG_FILEUPLOAD)) {
                log.info("displaying FileUpload");
                showFileUpload();
            }
 
            if (arg.equals(ARG_PROCESS)) {
                if (i < args.length)
                    process_filename = args[i++];
                else
                    log.info("-process requires a filename");
                log.info("processing file: " + process_filename);
                processFile (process_filename);
                return;
            }
            
            if (arg.equals(ARG_AGS)) {
                if (i < args.length)
                    ags_filename = args[i++];
                else
                    log.info("-ags requires a filename");
            log.info("ags_filename: " + ags_filename);
            }
            
            if (arg.equals(ARG_DICTIONARY)) {
                if (i < args.length)
                    dic_filename = args[i++];
                else
                    log.info("-dic requires a filename");
            log.info("dic_filename: " + dic_filename);
            }
            
            if (arg.equals(ARG_DATASTRUCTURESERIES)) {
                if (i < args.length)
                    data_structure = args[i++];
                else
                    log.info("-ds requires a datastructurename");
            log.info("data_structure: " + data_structure);
            }
             
            if (arg.equals(ARG_XML)) {
                if (i < args.length)
                    xml_filename = args[i++];
                else
                    log.info("-xml requires a filename");
            log.info("xml_filename: " + xml_filename);
            }
            
            if (arg.equals(ARG_SERVER)) {
                if (i < args.length) {
                    server_name = args[i++];
                    port = Integer.valueOf(args[i++]);
                } else
                    log.info("-server requires a name and port");
            log.info("server port: " + server_name + port);
            }
            
            if (arg.equals(ARG_START)) {
                log.info("starting server..");
                vflag =true;
                startServer(args);
            }
            if (arg.equals(ARG_STOP)) {
                log.info("stopping server..");
                vflag = true;
                stopServer(args);
            }
            if (arg.equals(ARG_HELP)) {
                 vflag = true;
                log.info(Constants.USAGE);
            }
        }
        
        if (!ags_filename.isEmpty()) {
            vflag = convertFile(ags_filename, dic_filename,data_structure, xml_filename, server_name, port);
        }
        
        if (vflag == false)
            log.info(Constants.USAGE);
        else
            log.info("Finished");
    } 

    
 
public static void showAGSMLGeneratorGUI(String[] args) {
        //Create and set up the window.
//          AGSMLGeneratorGUI gui1 = new AGSMLGeneratorGUI();
//          gui1.setVisible( true);
          GeneratorGUI gui = new GeneratorGUI(args, config, log);
          
          wh = WindowHandler.getInstance();
          wh.setGeneratorGUI(gui);
          SimpleFormatter sf = new SimpleFormatter();
          wh.setFormatter(sf);
          log.addHandler(wh);
          gui.setVisible( true);             
    }
public static void initLogger(){
    initLogger("","");
}
public static void initLogger(String log_folder, String format){
 
//   https://stackoverflow.com/questions/15758685/how-to-write-logs-in-text-file-when-using-java-util-logging-logger
    if (config != null) {
        if (format=="") {
        format = config.getProperty("java.util.logging.SimpleFormatter.format");
    }
        if (log_folder=="") {
        log_folder = config.getProperty(Constants.LOG_FOLDER);
        }
        
    }
        
    if (format=="") {
        format = Constants.LOG_FORMAT;
    }
    
    System.setProperty("java.util.logging.SimpleFormatter.format", format);
    
    log = Logger.getLogger("agsmlGenerator");
    
   
      
    try {
        if (log_folder != "") {   
            FileHandler fh;      
            AGS_Data f = new AGS_Data(log_folder + "\\log_.log");
            f.addDateStamp(Constants.LOG_DATEFORMAT);
            String log_file = f.File().getAbsolutePath();
            // This block configure the logger with handler and formatter  
            fh = new FileHandler(log_file);  
            log.addHandler(fh);
        }
        
        // the following statement is used to log any messages  
       
    } catch (SecurityException e) {  
        e.printStackTrace();
    } catch (IOException e) {  
        e.printStackTrace();
    }
    
    
     log.info("Logger initialised");
}

public static String processFile(String filename) {
       
    AGS_Converter ac = new AGS_Converter();  
    ac.setLogger(log);
    log.info("AGS_Converter started");
    ac.Process (filename);
    log.info("AGS_Converter finished");
    return "";
    
}

public static boolean convertFile (String ags_file, String dic_file, String data_structure, String xml_file, String server_name, int port ) {
    if (ags_file.isEmpty()){
        log.info("convertFile needs ags_file");
        return false;
    }
    
    if (config != null) {
        if (dic_file.isEmpty())
            dic_file = config.getProperty(Constants.DICTIONARY_FILE);
        if (data_structure.isEmpty())
            data_structure = config.getProperty(Constants.DATASTRUCTURESERIES);
    }
    
    if (dic_file.isEmpty() || data_structure.isEmpty()) {
        log.info ("convertFile needs a dictionary file and a data structure");
        return false;
    }
    
    
    try {
        if (server_name.isEmpty()){
            AGS_Converter ac = new AGS_Converter();
            ac.setLogger(log);
            
            // Add resouce folder to dic_file if necessary
            AGS_Data f = new AGS_Data (dic_file);
            f.setResourceFolder(config.getProperty(Constants.RESOURCE_FOLDER));
            String full_dic_file = f.getFullResourcePath();
            
            if (full_dic_file.isEmpty()) {
                log.info ("Cannot find dictionary file:" + dic_file + " resource folder:" + config.getProperty(Constants.RESOURCE_FOLDER));   
                return false;
            } 
            
            ac.AGS_to_AGSML(ags_file, full_dic_file, data_structure,xml_file);
            return true;
        } else {
            if (port==0) {
                log.info("convertFile needs both server_name and port");
                return false;
            }

            AGS_Client ac = new AGS_Client(server_name, port);
            ac.setDictionaryFile(dic_file);
            ac.setDataStructureSeries(data_structure);
            ac.setAGSFileIN(ags_file);
            ac.setXMLFileSave(xml_file, true, false);
            ac.setLogger(log);
            ac.start();
            return true;
        }
    } catch (Exception e) {
        log.severe(e.toString());
        return false;
    }
 }
private static Properties FindAndReplace(Properties props, String find, String replace) {
    
    Set<String> keys = props.stringPropertyNames();
    Properties newprops  = new Properties();
    for (String key : keys) {
      String value =  props.getProperty(key);
      String newValue = value.replace(find, replace);
      newprops.setProperty(key, newValue);
    }
    return newprops;        
}
 public static Properties  loadProperties(String configFile) {
     
    // try (InputStream input = Generator.class.getClassLoader().getResourceAsStream("config.properties")) {
    try  {
        Properties props = new Properties();
        String jarName = "agsmlConverter.jar";
        String path_resources = "";
        
        String pathjar = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        
        if (pathjar.contains(jarName)) {
            path_resources = pathjar.substring(0, pathjar.indexOf(jarName)) + "/resources";
        }
        
        File configPathFile = new File (path_resources + "/" + configFile);
        
        if (configPathFile.exists()) {
            FileInputStream inputF = new FileInputStream(configPathFile.getAbsolutePath());
                if (inputF !=null) {
                    props.load(inputF);
                    props = FindAndReplace(props, Constants.RESOURCE_FOLDER_REPLACE,path_resources);
                    System.out.println("Properties loaded from" + configPathFile + "(" + new java.util.Date() + ")");
                    return props;
                }
        }       
        
        InputStream input = Main.class.getClassLoader().getResourceAsStream(configFile);
        
        if (input !=null) {
                System.out.println("Properties loaded from getResourceAsStream " + configFile + " ("  + new java.util.Date() + ")");
                props.load(input);
                return props;
         }
           
           
         System.out.println("Unable to find file " + configFile + " (" + new java.util.Date() + ")");
         return null;

        } catch (IOException ex) {
            System.out.println (ex.getMessage());
            return null;
        }

    }
public static void startServer(String [] args) {
    int i = 0;
    String arg;
    int port = 0 ;
    String dic_filename = "";
    String data_structure = "";

     while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];
          
            if (arg.equals("-dic")) {
                if (i < args.length)
                    dic_filename = args[i++];
                else
                    log.severe("-dic requires a filename");
                log.info("dic file: " + dic_filename);
            }
            
            if (arg.equals("-ds")) {
                if (i < args.length)
                    data_structure = args[i++];
                else
                    log.severe("-ds requires a string value");
                log.info("data structure: " + data_structure);
            }
            if (arg.equals("-start")) {
                if (i < args.length)
                    port = Integer.parseInt(args[i++]);
                else
                    log.severe("-start requires a port");
                log.info("port: " + port);
            }
     }
     
    startServer(port, dic_filename,data_structure);
    
}
public static void startServer(int port, String dic_file, String data_structure){

    String retMessage;
    
    try {
              
        if (config !=null) {
            if (port <= 0) {
                port = Integer.parseInt(config.getProperty(Constants.LOCALPORT)); 
            }
            if (dic_file.isEmpty()) {
                dic_file = config.getProperty(Constants.DICTIONARY_FILE);
            }
            if (data_structure.isEmpty()) {
                data_structure = config.getProperty(Constants.DATASTRUCTURESERIES);
            }
        }
         // Add resouce folder to dic_file if necessary
            AGS_Data f = new AGS_Data (dic_file);
            f.setResourceFolder(config.getProperty(Constants.RESOURCE_FOLDER));
            String full_dic_file = f.getFullResourcePath();
            
            if (full_dic_file.isEmpty()) {
                log.info ("Cannot find dictionary file:" + dic_file + " resource folder:" + config.getProperty(Constants.RESOURCE_FOLDER));   
            }
            
        if (port > 0) {
            as = new AGS_Server(port, full_dic_file, data_structure);
            } else {
            as = new AGS_Server();   
        }
       
        as.setLogger(log);
        as.start();

        retMessage = "AGS Server started (" + new java.util.Date() + ") on port:" + as.getPort();  
    } catch (Exception e) {
        retMessage = "AGS Server failed to start (" + new java.util.Date() + ")" + e.getMessage();  
    }
    log.info(retMessage);
}

public static void stopServer(String [] args){
    String retMessage;
    
    try {
        as.interrupt();
        retMessage = "AGS Server interrupted: " + as.getPort(); 
    } catch (Exception e) {
       retMessage = "AGS Server failed to interrupt thread";  
    }
     log.info(retMessage);
 } 

public static void showFileUpload() {
        //Create and set up the window.
//          AGSMLGeneratorGUI gui1 = new AGSMLGeneratorGUI();
//          gui1.setVisible( true);
          FileUploadGUI fu = new FileUploadGUI();
          fu.setVisible( true);             
    }
 }