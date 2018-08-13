/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
Refernces

https://www.nakov.com/inetjava/lectures/part-1-sockets/InetJava-1.10-Chat-client-server.html

*/
package agsml;

/**
 *
 * @author Simon
 */

import java.net.*;
import java.io.*;
import java.util.Vector;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;


public class AGS_Server extends Thread {
    private TextAreaHandler textAreaHandler;
    private Logger mlog; 
  
    public static String HOSTNAME = "localhost"; 
    public static int LISTENING_PORT = 20402; 
    public static int BUFF_SIZE = 4062; 
    
    private ServerSocket mServerSocket ; 
    private AGS_ServerClients mServerClients ;
    private AGS_XML_Writer mDefaultWriter ;
    private String WorkingFolder ;
   
    public enum ClientStatus {
    EMPTY {
        public String toString() {
          return "EMPTY";
        }
    },    
    RECEIVING_AGS {
        public String toString() {
          return "RECEIVING AGS";
        }
    },
    READY_AGS {
        public String toString() {
          return "READY_AGS";
        }
    },
    SENT_AGS {
        public String toString() {
          return "SENT_AGS";
        }
    },
    FAIL_AGS {
        public String toString() {
          return "FAIL_AGS";
        }
    },
    PROCESSING_AGS {
        public String toString() {
          return "PROCESSING_AGS";
        }
    },
    READY_XML {
        public String toString() {
          return "READY_XML";
        }
    }, 
    SENT_XML {
        public String toString() {
          return "SENT_XML";
        }
    },
    SAVED_XML {
        public String toString() {
          return "SAVED_XML";
        }
    },
    FAIL_XML {
        public String toString() {
          return "FAIL_XML";
        }
    }   
    }  
public AGS_Server (int port) {
     Object obj = this;
     LISTENING_PORT = port;
     mlog = Logger.getLogger( obj.getClass().getName());
     mlog.setLevel(Level.ALL);
     
     textAreaHandler = new TextAreaHandler();   
     
     mlog.addHandler(textAreaHandler);  
     mDefaultWriter = new  AGS_XML_Writer() ;
    
}

public void setLoggerTextArea(JTextArea textArea, Level level) {
       textAreaHandler.textArea = textArea;
       textAreaHandler.setLevel(level);
   }
Logger getLogger() {
       return mlog;
   }
public void setAGSDictionary(String xml_file, String xml_node) {
    mDefaultWriter.setAGSDictionary(xml_file, xml_node);
    mDefaultWriter.getAGSDictionary();
}
public void setAGSDataStructure(String xml_file, String xml_node){
    mDefaultWriter.setAGSDataStructure(xml_file, xml_node);
    mDefaultWriter.getAGSDataStructure();
}        
 public void run() {
        // Open server socket for listening
        //start listening on the server socket 
        bindServerSocket(); 
      
        // Start the ServerDispatcher thread 
        mServerClients = new AGS_ServerClients(); 
               
        // Infinitely accept and handle client connections 
        handleClientConnections();   
 }   

public void shutdown(){
     try {
        interrupt();
        mServerSocket.close();
        mServerSocket = null;
        String msg = "AGS Server stopped";
        System.out.println ( msg);
        mlog.info(msg);
        } 
        catch (Exception e) {
            
        }
 }     
private void bindServerSocket () {  
    
        try {
            mServerSocket = new ServerSocket (LISTENING_PORT);
            System.out.println ( "AGS Server started on port " + LISTENING_PORT);
        } catch (IOException ioe) {
            System.err.println ( "AGS Server cannot start listening on port " + LISTENING_PORT);
            ioe.printStackTrace (); 
          //  System.exit (- 1 );
        } 
} 
 
private void handleClientConnections ()  {
        while ( !isInterrupted()) {
            try {
                Socket socket = mServerSocket.accept (); 
                if (socket != null) {
                    AGS_ServerClient client = new AGS_ServerClient (this, socket);
                     mServerClients.addClient (client); 
                     client.mAGS_DataStructure =  mDefaultWriter.m_ds;
                     client.mAGS_Dictionary =  mDefaultWriter.m_dic;
                     client.start();
                }
                
//            }    
//            catch (InterruptedException e) {
//                e.printStackTrace (); 
//                running = false;
            }   catch (SocketException se) {
               System.out.println ( "AGS Server closed on port " + LISTENING_PORT);
             } 
            catch (IOException ioe) {
                ioe.printStackTrace ();
            } 

         
        } 
        
  } 
} 

class AGS_ServerClients {
    
        private Vector<AGS_ServerClient> mClients = new Vector<AGS_ServerClient>();

     /** 
     * Adds given client to the server's client list. 
     */ 

    public synchronized void addClient(AGS_ServerClient aClient) { 
        mClients.add(aClient); 
    } 

    /** 
     * Deletes given client from the server's client list if 
     * the client is in the list. 
     */ 

    public synchronized void deleteClient(AGS_ServerClient aClient) { 
        int clientIndex = mClients.indexOf(aClient); 
        if (clientIndex != -1) 
            mClients.removeElementAt(clientIndex); 
    } 
}


class AGS_ServerClient extends Thread {
    private AGS_Server server;
    private BufferedReader mSocketReader = null; 
    private BufferedOutputStream mSocketWriter = null; 
    private String mAGS_data = null;
    private String mXML_data = null;
    public AGS_Dictionary mAGS_Dictionary = null;
    public AGS_DataStructure mAGS_DataStructure = null;
    private AGS_Server.ClientStatus status = AGS_Server.ClientStatus.EMPTY;
    private int BUFF_SIZE = AGS_Server.BUFF_SIZE;
    private final String AGS_START = "[ags_start]";
    private final String AGS_END = "[ags_end]";
    private final String XML_START = "[xml_start]";
    private final String XML_END = "[xml_end]";
    
    public AGS_ServerClient(AGS_Server ags_server, Socket socket) {
    
        try {
            server = ags_server;
            mSocketReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8")); 
            mSocketWriter = new BufferedOutputStream(socket.getOutputStream()); 
            
        } catch (IOException iox) {
        
        }
    }
    
    public void processAGS() {
        
        try {
            if (mAGS_data != null && 
                    mAGS_Dictionary != null &&
                        mAGS_DataStructure != null) {
          
            AGS_ReaderLine alr = new AGS_ReaderLine40 (mAGS_data);
            AGS_XML_Writer axw = new AGS_XML_Writer();
            axw.setLogger(server.getLogger());
            axw.setAGSLineReader(alr);
            axw.setAGSDictionary(mAGS_Dictionary);
            axw.setAGSDataStructure(mAGS_DataStructure);
            axw.Process();
            
            mXML_data = axw.toString();
            String msg = "AGS data processed Ok";
            server.getLogger().info(msg);
            status = AGS_Server.ClientStatus.READY_XML;
           
        } else {
            status = AGS_Server.ClientStatus.FAIL_XML;    
            
            } 
        }
        
        catch (Exception e) {
           status =  AGS_Server.ClientStatus.FAIL_XML;
        }
        
    }
    private void sendLine(String line) {
        try {
           //  mSocketWriter.write(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private void sendXML()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(XML_START);
        sb.append(System.lineSeparator());
        sb.append(mXML_data);
        sb.append(System.lineSeparator());
        sb.append(XML_END);
        sb.append(System.lineSeparator());
        
        String s1 = sb.toString();
        byte buffer[] = new byte[BUFF_SIZE];
        int length = 0;
        int total =0 ;
        
        // https://www.programcreek.com/java-api-examples/java.io.BufferedInputStream
         
        BufferedInputStream bis = new BufferedInputStream( new ByteArrayInputStream(s1.getBytes(StandardCharsets.UTF_8)));
        try {
            while((length = bis.read(buffer)) != -1) {
                mSocketWriter.write(buffer, 0, length);
                total =+ length;
            }
            
            mSocketWriter.flush();
            String msg = "XML data sent (" + total + ")";
            //System.out.println(msg);
           server.getLogger().info(msg);
           status = AGS_Server.ClientStatus.SENT_XML;
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
      
private void receiveAGS()
      {
    // status = AGS_Server.ClientStatus.RECEIVING_AGS;
       StringBuilder sb = new StringBuilder();
       int linecount = 0 ;
       String line;
       
       boolean read = false;
       
       try {  
        
           while ((line = mSocketReader.readLine()) != null) {
            
            if (line.equals(AGS_END)) {
                read = false;
               
                mAGS_data = sb.toString();
                String msg ="AGS data received (" + linecount + ")";
                
                System.out.println(msg);
                server.getLogger().info(msg);
                status = AGS_Server.ClientStatus.READY_AGS;
                return;
                
            }             
            
            if (read == true) {
                sb.append(line);
                sb.append(System.lineSeparator());
                linecount++;
            //    System.out.println("received " + line); 
            }    
            
            if (line.equals(AGS_START)) {
                System.out.println ("Receiving AGS data...");
                read = true;
            }
          }
          
 
         
      } catch (Exception e) {
            e.printStackTrace();
      }
}


@Override
    public void run() { 
        
        try { 
            while (!isInterrupted()) { 
                
                if (status == AGS_Server.ClientStatus.EMPTY) {
                  receiveAGS();
                }             
                
                if (status == AGS_Server.ClientStatus.READY_AGS) {
                   processAGS();
                }
                
                if (status == AGS_Server.ClientStatus.READY_XML) {
                   sendXML();
                }
                
                if (status == AGS_Server.ClientStatus.FAIL_XML) {
                   sendLine("XML conversion failed");
                }
            }

        }  catch (Exception ex) { 
            
       // Problem reading from socket (broken connection) 
       // Communication is broken. Interrupt both listener and 
       // sender threads 
        }
    }
    
}



