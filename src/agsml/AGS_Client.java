/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agsml;

import java.io.BufferedOutputStream;
import java.net.*;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter; 
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import agsml.AGS_Package;
import static agsml.AGS_Server.MAX_BUFFER_SIZE;
import static agsml.AGS_Server.LISTENING_PORT;
import static agsml.AGS_Server.HOSTNAME;
import static agsml.AGS_Server.ClientStatus;
import java.io.ByteArrayInputStream;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author Simon
 */

public class AGS_Client extends Thread {

    private String SERVER_HOSTNAME =  agsml.AGS_Server.HOSTNAME;
    private int SERVER_PORT = agsml.AGS_Server.LISTENING_PORT;
    private Socket socket;   
  //  private BufferedInputStream mSocketReader; 
  //  private BufferedWriter mSocketWriter; 
    
    private String mAGS_fileIN;
    private String mAGS_fileOUT;

    private String mXML_fileOUT;    

    private String mDB_Connect;
    private String mDB_StatementAGS;
    private final int mDB_ParamIdAGS = 1;  
    private String mDB_StatementXML;
    private final int mDB_ParamIdXML = 1;
    
    private String mAGS_data;
    private String mXML_data;
    
    private String mDictionaryFile;
    private String mDataStructureSeries;
    
    private Logger log;
    
    private ClientStatus status = agsml.AGS_Server.ClientStatus.EMPTY;
    
    public AGS_Client (String host, int port) throws IOException { 

        SERVER_HOSTNAME = host; 
        SERVER_PORT = port; 
        
        socket = new Socket(SERVER_HOSTNAME, SERVER_PORT); 
           // mSocketReader = new BufferedInputStream(socket.getInputStream()); 
           // mSocketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")); 
          
     } 
    
    public void setLogger(Logger Log) {
        log = Log;
    }
    
    public int ValidateInputData() {
    
        int retval = 0;
        
      // Minimum input data requirements'
         if (mAGS_fileIN.isEmpty()) retval=+ -1;
         if (mDictionaryFile.isEmpty()) retval=+ -1;
         if (mDataStructureSeries.isEmpty()) retval=+-1;
         return retval;
         
    }
    
    public void setDictionaryFile(String s1){
        mDictionaryFile  = s1;
    }
    public void setDataStructureSeries(String s1){
        mDataStructureSeries  = s1;
    }
    public void setAGSFileIN(String ags_fileIN) {
        mAGS_fileIN = ags_fileIN;
    }
     
    public void setAGSData(String ags_data) {
        mAGS_data = ags_data;
        status=agsml.AGS_Server.ClientStatus.READY_AGS;
     }
     public String getXMLData() {
        return mXML_data;
     }
     public void setXMLDatabaseSave(String DbConnection, 
                                    String PStatement) {
         mDB_Connect = DbConnection;
         mDB_StatementXML = PStatement;
     }
    
    public void setXMLFileSaveFromAGS(boolean Overwrite, boolean IncludeDateStamp) {
        if (mAGS_fileIN != null) {
        AGS_Data f = new AGS_Data(mAGS_fileIN);
        f.changeExtension(".xml");
        if (!Overwrite) {
            f.addUniqueNumber();
        }
        if (IncludeDateStamp) {
            f.addDateStamp();
        }
        mXML_fileOUT = f.File().getAbsolutePath();
        
      //  int ext = mAGS_fileIN.lastIndexOf("ags");
      //  String filename = mAGS_fileIN.substring(0, ext) + "xml";  
      //  mXML_fileOUT = getFileName(filename, Overwrite, IncludeDateStamp);
        } 
    }    
    
    public void setXMLFileSave(String fileName, boolean Overwrite, boolean IncludeDateStamp){
        if (fileName != null) {
        AGS_Data f = new AGS_Data(fileName);
            if (!f.File().isAbsolute()) {
                f.getFullOutputPath();
            }
            if (!Overwrite) {
                f.addUniqueNumber();
            }
            if (IncludeDateStamp) {
                f.addDateStamp();
            }
        mXML_fileOUT = f.File().getAbsolutePath();
      }
    }
         
    public void setAGSDatabaseSave(String DbConnection, 
                                    String PStatement) {
         mDB_Connect = DbConnection;
         mDB_StatementXML = PStatement;
    }
    public void setAGSFileSave(String fileName, boolean Overwrite, boolean IncludeDateStamp){
        if (fileName != null) {
            AGS_Data f = new AGS_Data(fileName);
            if (!Overwrite) {
                f.addUniqueNumber();
            }
            if (IncludeDateStamp) {
                f.addDateStamp();
            }
            
            mAGS_fileOUT = f.File().getAbsolutePath();
      //   mAGS_fileOUT = getFileName (filename, Overwrite, IncludeDateStamp);
        }
     }
     
     private void receiveXMLData() { 
         
         try { 
         
         BufferedInputStream mSocketReader = new BufferedInputStream(socket.getInputStream()); 
         
         StringBuilder sb = new StringBuilder();
         byte buffer[] = new byte[AGS_Server.MAX_BUFFER_SIZE];
         int length;
         int total;
               
                while((length = mSocketReader.read(buffer)) != -1) {
                String s1 = new String(buffer, 0, length);
                sb.append(s1); 
                total =+ length;
                    if (s1.indexOf(AGS_Package.FILE_END) > 0) {
                        log.info("XML data received (" + total + ")");
                        break;   
                    }
                    
                }
          AGS_Package ap = new AGS_Package (AGS_Package.ContentType.XML, sb.toString());
          if (ap.HasXMLData()) {
          mXML_data = ap.data_xml;
          status = agsml.AGS_Server.ClientStatus.READY_XML;
          } else {
           throw new Exception();       
          }
          
        
         } catch (Exception e) {
             e.printStackTrace(); 
            // mXML_data =  sb.toString();
            status = agsml.AGS_Server.ClientStatus.FAIL_XML; 
        }
          
     }
     public void readAGSData(){
        if (!mAGS_fileIN.isEmpty()){
        setAGSData(usingBufferedReader(mAGS_fileIN));
        
        log.info("AGS data file read (" + mAGS_fileIN + ")");
      }
     }
        private static String usingBufferedReader(String filePath) 
     // https://howtodoinjava.com/core-java/io/java-read-file-to-string-examples/       
    {
	StringBuilder contentBuilder = new StringBuilder();
	try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
	{

		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) 
		{
			contentBuilder.append(sCurrentLine).append("\n");
		}
	} 
	catch (IOException e) 
	{
		e.printStackTrace();
	}
	return contentBuilder.toString();
    }
       private void saveXMLData(){
           
           saveXMLDataToFile();
           saveXMLDataToDatabase();
           
           status = agsml.AGS_Server.ClientStatus.SAVED_XML;
       }
       
       private void  saveXMLDataToDatabase () {
           
        if (IsEmpty(mDB_Connect) || IsEmpty(mXML_data) || IsEmpty(mDB_StatementXML)) {
            return;
        }
        
       try { 
           
           Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           Connection m_Conn = DriverManager.getConnection(mDB_Connect);
                    
           PreparedStatement ps = m_Conn.prepareStatement(mDB_StatementXML);
           
            // set the preparedstatement parameters
            ps.setString(mDB_ParamIdXML,mXML_data);
            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
           
       }
 
       catch (Exception ex) {
         System.out.println(ex.getMessage());
        ex.printStackTrace();
       }
//       catch (FileNotFoundException e) {
//           
//       }
    }
        public void saveXMLDataToFile() {
        
        if (IsEmpty(mXML_fileOUT) || IsEmpty(mXML_data)) {
            return;
        }
        
        BufferedWriter out = null;
            try  
                {
                FileWriter fstream = new FileWriter(mXML_fileOUT,false); //true tells to append data.
                out = new BufferedWriter(fstream);
                out.write(mXML_data);
                out.close();
                log.info("XML data file written (" + mXML_fileOUT + ")");
             
                }   
                catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                }
    }
    private void saveAGSData(){
           
           saveAGSDataToFile();
           saveAGSDataToDatabase();
           
           status=agsml.AGS_Server.ClientStatus.SAVED_AGS;
       }
       
       private void  saveAGSDataToDatabase () {
           
        if (IsEmpty(mDB_Connect) || IsEmpty(mDB_StatementAGS)|| IsEmpty(mAGS_data)) {
            return;
        }
       try { 
           
           Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           Connection m_Conn = DriverManager.getConnection(mDB_Connect);
                    
           PreparedStatement ps = m_Conn.prepareStatement(mDB_StatementAGS);
           
            // set the preparedstatement parameters
            ps.setString(mDB_ParamIdAGS,mAGS_data);
            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
           
       }
 
       catch (Exception ex) {
         System.out.println(ex.getMessage());
        ex.printStackTrace();
       }
//       catch (FileNotFoundException e) {
//           
//       }
    }
        public void saveAGSDataToFile() {
        
        if (mAGS_fileOUT == null) {
            return;
        }
        
        BufferedWriter out = null;
            try  
                {
                FileWriter fstream = new FileWriter(mAGS_fileOUT,false); //true tells to append data.
                out = new BufferedWriter(fstream);
                out.write(mAGS_data);
                out.close();
                log.info("AGS data file written (" + mAGS_fileOUT + ")");
             
                }   
                catch (IOException e) {
                log.severe (e.getMessage());
                }
    } 
    @Override
      public void run() {
                
        log.info("AGS_Client started on AGS Server " + SERVER_HOSTNAME + ":" + SERVER_PORT); 
        
        try { 
            while (!isInterrupted()) {  
            
            if (status ==agsml.AGS_Server.ClientStatus.EMPTY) {
                 readAGSData();
            }    
            
            if (status == agsml.AGS_Server.ClientStatus.READY_AGS) {
                 saveAGSData();
            }
            
            if (status == agsml.AGS_Server.ClientStatus.SAVED_AGS) {
                 sendAGSData();
            }
            
            if (status == agsml.AGS_Server.ClientStatus.SENT_AGS) {
                 receiveXMLData();
            }
            
            if (status == agsml.AGS_Server.ClientStatus.READY_XML) {
                 saveXMLData();
            }
            
            if (status == agsml.AGS_Server.ClientStatus.SAVED_XML) {
                break;
            }
            }
        } catch (Exception e) {
             log.severe (e.getMessage());
        }
     }
     
    private void sendAGSData(){
        sendAGSDataByLine();
    }
    
    private void sendAGSDataByStream(){
       {
        try {
            
            BufferedOutputStream mSocketWriter = new BufferedOutputStream(socket.getOutputStream()); 
            
            AGS_Package ap = new AGS_Package (mAGS_data,mDictionaryFile, mDataStructureSeries);
            String s1 = ap.getContentsAGS();
        
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
            String msg = "AGS data sent (" + total + ")";
            //System.out.println(msg);
          // server.getLogger().info(msg);
           status = AGS_Server.ClientStatus.SENT_AGS;
        
        } catch (Exception e) {
            log.severe (e.getMessage());
        }

    } 
    }
    
     private void sendAGSDataByLine() {
 
       try {       
        String line = "";
        int linecount = 0;
        
        AGS_Package ap = new AGS_Package (mAGS_data,mDictionaryFile, mDataStructureSeries);
        
        String s1 = ap.getContentsAGS();
       
        BufferedReader br = new BufferedReader( new StringReader(s1) );
        
        
        BufferedWriter mSocketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")); 
        
        //   while (!isInterrupted()) {   
                while ((line = br.readLine()) != null) { 
                        mSocketWriter.write(line);
                        mSocketWriter.newLine();
                        linecount++;
                }
           log.info("AGS data sent (" + linecount + ")");
            mSocketWriter.flush();
            status = agsml.AGS_Server.ClientStatus.SENT_AGS;
        //    }
            
        } catch (Exception e) {
           log.severe (e.getMessage());
        }

    }
    private static boolean IsEmpty( final String s ) {
  // Null-safe, short-circuit evaluation.
  return s == null || s.trim().isEmpty();
} 
}

        
        
