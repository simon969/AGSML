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
import java.util.Iterator;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.Properties;
import agsml.AGS_Package;

public class AGS_Server extends Thread {
  //  private TextAreaHandler mTextAreaHandler;
    public Logger log; 
  //  private JTree tree;
    public static String HOSTNAME = "localhost"; 
    public static int LISTENING_PORT = 20402; 
    public static int MAX_BUFFER_SIZE = 4062; 
    private ServerSocket mServerSocket ; 
    private AGS_ServerClients mServerClients ;
    private static String DICTIONARY_SOURCE = "";
    private static String DATA_STRUCTURE = "";
    private AGS_Dictionary mAGSDictionary = null; 
    private AGS_DataStructure mAGSDataStructure = null;
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
    SAVED_AGS {
        public String toString() {
          return "SAVED_AGS";
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
 public AGS_Server () {
   }
  public AGS_Server (int port) {
  
      LISTENING_PORT = port;
  
  }
  
  public AGS_Server (int port, String xml_dictionary_source, String xml_datastructure) {
    
    LISTENING_PORT = port;
    DICTIONARY_SOURCE = xml_dictionary_source;
    DATA_STRUCTURE = xml_datastructure;
  }
  

public void setLogger( Logger Log) {
       log = Log;
   }
public void setPort(int port) {
    LISTENING_PORT = port;
}
public int getPort(){
    return LISTENING_PORT;
}
public void setDictionarySource(String xml_source) {
    DICTIONARY_SOURCE = xml_source;
}
public void setDataStructureSeries(String id){
   DATA_STRUCTURE = id;
}        
 public void run() {
        // Open server socket for listening
        //start listening on the server socket 
        loadDictionary();
        bindServerSocket(); 
        // Start the ServerDispatcher thread 
        mServerClients = new AGS_ServerClients(); 
        mServerClients.setLogger(log);
        // Infinitely accept and handle client connections 
        handleClientConnections();   
 }   

public void shutdown(){
     try {
        interrupt();
        mServerSocket.close();
        String msg = "AGS Server closed (" + mServerSocket.getInetAddress() + ":"  + mServerSocket.getLocalPort() + ")";
        mServerSocket = null;
        log.info(msg);
        } 
        catch (Exception e) {
        log.severe (e.getMessage()) ;   
        }
 }     
private void bindServerSocket () {  
    
        try {
            mServerSocket = new ServerSocket (LISTENING_PORT);
            log.info ( "AGS Server started (" + mServerSocket.getInetAddress() + ":"  + mServerSocket.getLocalPort() + ")");
        } catch (IOException ioe) {
            log.severe (ioe.getMessage());
        } 
} 
private void loadDictionary() {
    try {
 
        if (!DICTIONARY_SOURCE.isEmpty()) {
        mAGSDictionary = new AGS_Dictionary(DICTIONARY_SOURCE);
        }
        if (!DATA_STRUCTURE.isEmpty()) {
        mAGSDataStructure =  new AGS_DataStructure (mAGSDictionary); 
        mAGSDataStructure.setDataStructureSeries(DATA_STRUCTURE);
        }
            
        
    } catch (Exception e) {
        log.severe (e.getMessage());
    }
    
}

//private void pollActiveClients() {
//    if (tree != null) {
//        
//        mServerClients.purgeClients();
//               
//        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
//        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
//        root.removeAllChildren();
//     //   DefaultMutableTreeNode top =  new DefaultMutableTreeNode(HOSTNAME + "AGS SERVER:" + LISTENING_PORT);
//        while (mServerClients.hasNext()) {
//        AGS_ServerClient ac = mServerClients.Next();
//        root.add(new DefaultMutableTreeNode(ac.Details() + ac.ConnectionDuration() + ac.ProgressStatus()));
//        System.out.println(ac.Details() + ac.ConnectionDuration() + ac.ProgressStatus());
//        }
//        
//        model.reload(root);
//    }
//} 


private void handleClientConnections ()  {
        while ( !isInterrupted()) {
            try {
                Socket socket = mServerSocket.accept (); 
                if (socket != null) {
                    AGS_ServerClient client = new AGS_ServerClient (socket);
                    mServerClients.addClient (client); 
                    client.setDictionary(mAGSDictionary);
                    client.setDataStructure(mAGSDataStructure);
                    client.setLogger(log);
                    client.start();
                }
                
           // pollActiveClients();
            
            }   catch (SocketException se) {
               log.severe ( "AGS Server closed on port " + LISTENING_PORT);
             } 
            catch (IOException ioe) {
               log.severe(ioe.getMessage());
            } 

         
        }
        
        this.shutdown();
        
  } 
} 

class AGS_ServerClients {
    
    private Vector<AGS_ServerClient> mClients = new Vector<AGS_ServerClient>();
    private Iterator<AGS_ServerClient> iter = null;
    private Logger log;
    public AGS_ServerClient Next() {return iter.next();}
    
    public Boolean hasNext() {
        
        if (iter == null) {
           iter = mClients.iterator();
        }
        
        if (iter.hasNext()) {
          return true;
        } else {
          iter = null;
          return false;
        }
    }
    
     /* Adds given client to the server's client list. 
     */ 
    public void setLogger (Logger Log){
        log = Log;
    }
    public synchronized void addClient(AGS_ServerClient aClient) { 
        mClients.add(aClient); 
        log.info ( "AGS Client connected " + aClient.Details());
    } 

    /** 
     * Deletes given client from the server's client list if 
     * the client is in the list. 
     */

    public synchronized void purgeClients() {
        for (int i=0; i < mClients.size(); i++) {
        AGS_ServerClient ac = mClients.get(i);
             
            if (ac.IsClosed() || 
                ac.getProgressStatus()==AGS_Server.ClientStatus.FAIL_XML ||
                ac.getProgressStatus()==AGS_Server.ClientStatus.SENT_XML ) {
                deleteClient(ac);
            }
        }
    }
    
    public synchronized void deleteClient(AGS_ServerClient aClient) { 
        int clientIndex = mClients.indexOf(aClient); 
        if (clientIndex != -1) 
            mClients.removeElementAt(clientIndex); 
    }
}


class AGS_ServerClient extends Thread {
    private Socket socket;
    private InetAddress addr;
    private static final LocalDateTime timeStamp = LocalDateTime.now();
    private String mAGS_data = null;
    private String mXML_data = null;
    
    private String  mDictionarySource = null;
    private AGS_Dictionary mAGSDictionary = null;   
    private String  mDataStructureSeries = null;
    private AGS_DataStructure mAGSDataStructure = null;
    
    private AGS_Server.ClientStatus status = AGS_Server.ClientStatus.EMPTY;
    private Logger log;  
      
    public AGS_ServerClient(Socket s) {
    
        try {
            socket = s;
            addr = socket.getInetAddress();
            } catch (Exception ex) {
              log.severe(ex.getMessage());
        }
    }
    public void  setLogger(Logger Log) {
        log = Log;
    }
    public String Details() {
        String details;
        details = addr.getHostName() + "(" + addr.getHostAddress() + ")";
        details += "Started:" + timeStamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);  
        return details;
    }
    
    public void setDictionary(AGS_Dictionary ags_dic) {
        mAGSDictionary = ags_dic;
      }
    public void setDataStructure(AGS_DataStructure ags_ds) {
        mAGSDataStructure = ags_ds;
    }
    public AGS_DataStructure getDataStructure(String fileName,String datastructureseries,Constants.AGSVersion ags) {
        try {
            if (mAGSDataStructure == null) {
                    mAGSDataStructure = new AGS_DataStructure(fileName, datastructureseries, ags); 
               return mAGSDataStructure;
               
            } 
            AGS_Data ad1 = new AGS_Data(mAGSDataStructure.source);
            String s1 =  ad1.FileNameNoPath();   
            
            AGS_Data ad2 = new AGS_Data(fileName);
            ad2.setResourceFolder(ad1.getResourceFolder());
            String s2 =  ad2.FileNameNoPath();
                
            if (!s1.equalsIgnoreCase(s2)) {
                  String s3 = ad2.getFullResourcePath();
                   mAGSDataStructure = new AGS_DataStructure(s3, datastructureseries, ags); 
                } else {
                    if (!mAGSDataStructure.getDataStructureSeriesName().equals(datastructureseries)) {
                       mAGSDataStructure.setDataStructureSeries(datastructureseries);
                    }
                    if (!mAGSDataStructure.getAGSVersion().equals(ags)) {
                       mAGSDataStructure.setAGSVersion(ags); 
                    }   
                }
                        
            return mAGSDataStructure;
        } catch (Exception e) {
            log.severe(e.getMessage());
            return null;
        }
    }
   
   public AGS_Dictionary getDictionary(String fileName,  Constants.AGSVersion ags) {
       
        try {
            
            if (mAGSDictionary == null) {
                mAGSDictionary =  new AGS_Dictionary(fileName, ags.toString());
                return mAGSDictionary;
            }
                       
            
            AGS_Data ad1 = new AGS_Data(mAGSDictionary.source);
            String s1 =  ad1.FileNameNoPath();   
                
            AGS_Data ad2 = new AGS_Data(fileName);
            ad2.setResourceFolder(ad1.getResourceFolder());
            String s2 =  ad2.FileNameNoPath();
                
            if (!s1.equalsIgnoreCase(s2)) {
                String s3 = ad2.getFullResourcePath();
                log.info ("Using resource folder:"  + ad2.getResourceFolder());
                log.info ("Setting new dictionary:" + s3 + " ags:"  + ags.toString());
                mAGSDictionary =  new AGS_Dictionary(s3, ags.toString());
               
            }
            if (mAGSDictionary.getAGSVersion() != ags) {
                mAGSDictionary.setAGSVersion (ags);
            }
            
            return mAGSDictionary;
            
       } catch (Exception e) {
           return null;
           
       }
   }
       
    public Long ConnectionDuration() {
        return Duration.between(LocalDateTime.now(), timeStamp).getSeconds();
    }
    
    public String ProgressStatus() {
        return status.toString();
    }
    public boolean IsClosed() {
        return socket.isClosed();
    }
    public AGS_Server.ClientStatus getProgressStatus() {
        return status;
    }
    
    public void processAGS() {
        
        try {
                       
            if (mDictionarySource.isEmpty()) {
               log.info("AGS_DictionaryFile not provided default dictionary will be used" ); 
            }
            
            if (mDataStructureSeries.isEmpty()) {
               log.info("AGS_DataStructure not provided default datastructure will be used" ); 
            } 
            
            if (mAGS_data.isEmpty() ) {
                throw new Exception ("AGS_Data not provided");
            }    
            
                AGS_ReaderText art = new AGS_ReaderText (mAGS_data);
                AGS_Dictionary dic = null;
                AGS_DataStructure ds = null;
                AGS_ReaderLine alr = null;

                if (art.ags_version.toInt() > Constants.AGSVersion.AGS31a.toInt()){
                alr = new AGS_ReaderLine40 (mAGS_data);    
                dic = getDictionary(mDictionarySource,art.ags_version);
                ds = getDataStructure(mDictionarySource, mDataStructureSeries, Constants.AGSVersion.AGS404);
                } 
                if (Constants.AGSVersion.NONE.toInt() < Constants.AGSVersion.AGS31a.toInt() && art.ags_version.toInt() <= Constants.AGSVersion.AGS31a.toInt() ){
                alr = new AGS_ReaderLine31 (mAGS_data);
                dic = getDictionary(mDictionarySource,art.ags_version);
                ds = getDataStructure(mDictionarySource, mDataStructureSeries, Constants.AGSVersion.AGS31a);
                }

                log.info("        ags lines:" + art.AGSLines());
                log.info("   identified ags:" + art.AGSVersion().toString());
                log.info("datastructure set:" + mDataStructureSeries);

                if (alr == null ) {
                 throw new Exception ("AGS_Reader not identified:" + art.ags_version.toId());    
                }
                if (dic == null) {
                   
                    throw new Exception("AGS_Dictionary not identified:" + mDictionarySource);
                }
                if (ds == null) {
                    throw new Exception("AGS_DataStructure not identified:" + mDataStructureSeries);
                }

                AGS_XML_Writer axw = new AGS_XML_Writer();
                axw.setLogger(log);
                axw.setAGSLineReader(alr);
                axw.setAGSDictionary(dic);
                axw.setAGSDataStructure(ds);
                axw.Process();
                mXML_data = axw.toString();
                log.info("AGS data processed Ok");
                status = AGS_Server.ClientStatus.READY_XML;
                
            
        }
        
        catch (Exception e) {
           status =  AGS_Server.ClientStatus.FAIL_XML;
           log.info(e.getMessage());
        }
        
    }
    private void sendLine(String line) {
        try {
             BufferedWriter mSocketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")); 
             mSocketWriter.write(line);
           //  log.info("AGS data sent (" + line + ")");
             mSocketWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private void sendXML()
    {
        try {
            
            BufferedOutputStream mSocketWriter = new BufferedOutputStream(socket.getOutputStream()); 
            
            AGS_Package ap = new AGS_Package(mXML_data);
            
            String s1 = ap.getContentsXML();
            
            byte buffer[] = new byte[AGS_Server.MAX_BUFFER_SIZE];
            int length = 0;
            int total =0 ;


        
// https://www.programcreek.com/java-api-examples/java.io.BufferedInputStream
         
        BufferedInputStream bis = new BufferedInputStream( new ByteArrayInputStream(s1.getBytes(StandardCharsets.UTF_8)));
 
            while((length = bis.read(buffer)) != -1) {
                mSocketWriter.write(buffer, 0, length);
                total =+ length;
            }
            
            mSocketWriter.flush();
            String msg = "XML data sent (" + total + ")";
            //System.out.println(msg);
            log.info(msg);
            status = AGS_Server.ClientStatus.SENT_XML;
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
private void receiveAGS() {
    receiveAGSByStream();
}
private void receiveAGSByLine()
      {
      try {     
               
       BufferedReader mSocketReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8")); 
          
    // status = AGS_Server.ClientStatus.RECEIVING_AGS;
       StringBuilder sb = new StringBuilder();
       int linecount = 0 ;
       String line;
       
       boolean read = false;
       
 
        
           while ((line = mSocketReader.readLine()) != null) {
            
            if (line.equals(AGS_Package.FILE_END)) {
                read = false;
                AGS_Package ap = new AGS_Package(AGS_Package.ContentType.AGS, sb.toString());
                
                if (ap.HasAGSData()) {
                mAGS_data = ap.data_ags;
                status = AGS_Server.ClientStatus.READY_AGS;
                }
                
                if (ap.HasDataDictionary()) {
                mDictionarySource = ap.data_dictionary;
                }
                
                if (ap.HasDataStructureSeries()) {
                mDataStructureSeries  = ap.data_series;
                }
                
                String msg ="AGS data package received (" + linecount + ")";
                log.info(msg);
                return;
                
            }             
            
            if (read == true) {
                sb.append(line);
                sb.append(System.lineSeparator());
                linecount++;
            }    
            
            if (line.equals(AGS_Package.FILE_START)) {
                log.info ("Receiving AGS data package...");
                read = true;
            }
          }
          
 
         
      } catch (Exception e) {
           log.severe (e.getMessage());
      }
}

private void receiveAGSByStream()
      {
            // https://stackoverflow.com/questions/5867227/convert-streamreader-to-byte

            try {
                
                InputStream networkStream = socket.getInputStream();
                BufferedInputStream s_in = new BufferedInputStream(networkStream);
                Boolean ReadData = false;
                int read;
                byte[] buffer = new byte[AGS_Server.MAX_BUFFER_SIZE];

                // MemoryStream ms = new MemoryStream();
                StringBuilder sb =  null; 
                while ((read = s_in.read(buffer, 0, AGS_Server.MAX_BUFFER_SIZE)) > 0) {
                        // ms.Write(buffer, 0, read);
                        String s = new String(buffer, 0, read);
                          if (ReadData==true) {
                            if (s.indexOf(AGS_Package.FILE_END)>0) {
                              sb.append(s);
                              break; 
                            } 
                            else {
                              sb.append(s);  
                            }
                        }
                        if (ReadData==false) {
                            if (s.indexOf(AGS_Package.FILE_START)==0) {
                                log.info ("AGS data package receiving.......");
                                ReadData=true;
                                sb =  new StringBuilder();
                                sb.append(s);
                            } else {
                                throw new Exception ("No recognised file start..'" + s.substring(0, 16) + "'");
                            }
                        }
                      
                                               
                    }

                //    xml_data = Encoding.UTF8.GetString(ms.ToArray());
                AGS_Package ap = new AGS_Package(AGS_Package.ContentType.AGS, sb.toString());
                
                if (ap.HasAGSData()) {
                mAGS_data = ap.data_ags;
                status = AGS_Server.ClientStatus.READY_AGS;
                } else { 
                throw new Exception("AGS Package has no identifieable AGS data");
                }
                    
                if (ap.HasDataDictionary()) {
                mDictionarySource =  ap.data_dictionary;
                }
                if (ap.HasDataStructureSeries()) {
                mDataStructureSeries =  ap.data_series;
                }
                log.info ("AGS data package received (" + mAGS_data.length() + ")");
                               
            } catch (Exception e) {
                log.severe(e.getMessage());
                status = AGS_Server.ClientStatus.FAIL_AGS;
            }
}

@Override
    public void run() { 
        
       log.info("AGS_ServerClient started: " + this.addr);
        
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
                    this.socket.close();
                    break;
                }
                if (status == AGS_Server.ClientStatus.FAIL_AGS) {
                    sendLine("AGS file receive failed");
                    this.socket.close();
                    break;
                } 
                if (status == AGS_Server.ClientStatus.SENT_XML) {
                    break;
                }
            }

        }  catch (Exception ex) {
           log.severe(ex.getMessage());
      
       // Problem reading from socket (broken connection) 
       // Communication is broken. Interrupt both listener and 
       // sender threads 
        } 
        log.info("AGS_ServerClient finished: " + this.addr);
    }
}




