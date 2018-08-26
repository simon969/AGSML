/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agsmlGenerator;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;
import agsml.AGS_Converter;
import agsml.AGS_Server;


/**
 *
 * @author thomsonsj
 */
public class AGSMLGenerator {

// public static void  AGSMLGenerator() {
        
 //   }
      /**
     * @param args the command line arguments
     */
public static void main(String[] args) {
        // TODO code application logic here
      
        int i = 0, j;
        String arg;
        char flag;
        boolean vflag = false;
        String filename = "";
        int port = 0;
        
        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];
                // use this type of check for "wordy" arguments
            if (arg.equals("-gui")) {
                System.out.println("displaying GUI");
                showAGSMLGeneratorGUI();
                vflag = true;
                return;
            }
            
            if (arg.equals("-fileupload")) {
                System.out.println("displaying FileUpload");
                showFileUpload();
                vflag = true;
                return;
            }
    // use this type of check for arguments that require arguments
            if (arg.equals("-process")) {
                if (i < args.length)
                    filename = args[i++];
                else
                    System.err.println("-process requires a filename");
            vflag = true;
            System.out.println("processing file: " + filename);
            System.out.println( processFile (filename));
            
            }
            
            // use this type of check for arguments that require arguments
            if (arg.equals("-server")) {
                if (i < args.length)
                    port = Integer.parseInt(args[i++]);
                else
                    System.err.println("-server requires a port");
            vflag = true;
            
            System.out.println("Server starting on localhost port:" + port + "...");
            System.out.println(startServer (port));
            
            }
    // use this type of check for a series of flag arguments
            if (vflag == false) {
                
                for (j = 1; j < arg.length(); j++) {
                    flag = arg.charAt(j);
                    switch (flag) {
                    case 'x':
                        if (vflag) System.out.println("Option x");
                        break;
                    case 'n':
                        if (vflag) System.out.println("Option n");
                        break;
                    default:
                        System.err.println("ParseCmdLine: illegal option " + flag);
                        break;
                    }
                }
            }
        }
        
        if (i == args.length)
            System.err.println("Usage: ParseCmdLine [-process filename] [-gui]");
        else
            System.out.println("Success!");
    } 

private static void showAGSMLGeneratorGUI() {
        //Create and set up the window.
//          AGSMLGeneratorGUI gui1 = new AGSMLGeneratorGUI();
//          gui1.setVisible( true);
          AGSMLGeneratorGUI gui = new AGSMLGeneratorGUI();
          gui.setVisible( true);             
    }
private static String processFile(String filename) {
    String retMessage;
    Logger log = Logger.getLogger("AGSMLGenerator");
    
    AGS_Converter ac = new AGS_Converter();  
    ac.setLogger(log);
    
    ac.Process (filename);
    
    retMessage = log.toString();
  
   
    return retMessage;
    
}
private static String startServer(int port) {
    String retMessage;
 //   Logger log = Logger.getLogger("AGS Server");
    try {
        AGS_Server as = new AGS_Server(port);
       as.run();
       retMessage = "AGS Server started on port:" + port; 
    } catch (Exception e) {
       retMessage = "AGS Server failed to start on port:" + port;  
    }

    //as.setLogger(log);
    
   // retMessage = log.toString();
    
   return retMessage;
    
            
}
private static void showFileUpload() {
        //Create and set up the window.
//          AGSMLGeneratorGUI gui1 = new AGSMLGeneratorGUI();
//          gui1.setVisible( true);
          FileUpload fu = new FileUpload();
          fu.setVisible( true);             
    }
}