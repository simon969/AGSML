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

import static agsml.AGS_Server.BUFF_SIZE;
import static agsml.AGS_Server.LISTENING_PORT;
import static agsml.AGS_Server.HOSTNAME;
import static agsml.AGS_Server.ClientStatus;
import java.io.FileReader;
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
    private BufferedInputStream mSocketReader; 
    private BufferedWriter mSocketWriter; 
    
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
    
    public final String AGS_START = "[ags_start]";
    public final String AGS_END = "[ags_end]";
    public final String XML_START = "[xml_start]";
    public final String XML_END = "[xml_end]";
    
    private ClientStatus status = agsml.AGS_Server.ClientStatus.EMPTY;
    
    public AGS_Client (String host, int port) { 

        SERVER_HOSTNAME = host; 
        SERVER_PORT = port; 
        
        try { 
            Socket socket = new Socket(SERVER_HOSTNAME, SERVER_PORT); 
            mSocketReader = new BufferedInputStream(socket.getInputStream()); 
            mSocketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")); 
          
            System.out.println("Connected to AGS Server " + SERVER_HOSTNAME + ":" + SERVER_PORT); 

        } catch (IOException ioe) { 
            System.err.println("Cannot connect to " + SERVER_HOSTNAME + ":" + SERVER_PORT); 
            ioe.printStackTrace(); 
            System.exit(-1); 
        } 
       
        
        
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
     public void setXMLFileSave(){ setXMLFileSave("");}
     
     public void setXMLFileSave(String fileName){
        if (fileName == null && mAGS_fileIN != null) {
          int ext = mAGS_fileIN.lastIndexOf("ags");
        mXML_fileOUT = mAGS_fileIN.substring(0, ext) + "xml";  
        } else {
        mXML_fileOUT = fileName;
        }
     } 
     public void setAGSDatabaseSave(String DbConnection, 
                                    String PStatement) {
         mDB_Connect = DbConnection;
         mDB_StatementXML = PStatement;
     }
     public void setAGSFileSave(String ags_fileOUT){
         mAGS_fileOUT = ags_fileOUT;
     }
     private void receiveXMLData() { 
         StringBuilder sb= new StringBuilder();
         byte buffer[] = new byte[BUFF_SIZE];
         int length;
         int total;
         try {       
                while((length = mSocketReader.read(buffer)) != -1) {
                String s1 = new String(buffer, 0, length);
                sb.append(s1); 
                total =+ length;
                    if (s1.indexOf(XML_END) > 0) {
                        System.out.println("XML data received (" + total + ")");
                        break;   
                    }
                    
                }
         
          String s1 = sb.toString();
          mXML_data =  s1.substring(s1.indexOf(XML_START)+ XML_START.length(), s1.indexOf(XML_END));
          status = agsml.AGS_Server.ClientStatus.READY_XML;
        
         } catch (Exception e) {
             e.printStackTrace(); 
             mXML_data =  sb.toString();
            status = agsml.AGS_Server.ClientStatus.FAIL_XML; 
        }
          
     }
     public void readAGSData(){
        if (!mAGS_fileIN.isEmpty()){
        setXMLFileSave();
        setAGSData(usingBufferedReader(mAGS_fileIN));
        System.out.println("AGS data file read (" + mAGS_fileIN + ")");
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
           
           status=agsml.AGS_Server.ClientStatus.SAVED_XML;
       }
       
       private void  saveXMLDataToDatabase () {
           
        if (!empty(mDB_Connect) || !empty(mXML_data) || !empty(mDB_StatementXML)) {
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
        
        if (!empty(mXML_fileOUT) || !empty(mXML_data)) {
            return;
        }
        
        BufferedWriter out = null;
            try  
                {
                FileWriter fstream = new FileWriter(mXML_fileOUT,false); //true tells to append data.
                out = new BufferedWriter(fstream);
                out.write(mXML_data);
                out.close();
                System.out.println("XML data file written (" + mXML_fileOUT + ")");
             
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
           
        if (!empty(mDB_Connect) || !empty(mDB_StatementAGS)|| !empty(mAGS_data)) {
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
                System.out.println("AGS data file written (" + mAGS_fileOUT + ")");
             
                }   
                catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                }
    } 
     public void run() {
        // Open server socket for listening
        //start listening on the server socket 
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
             e.printStackTrace();
        }
     }
     
    
     private void sendAGSData() {
 
        StringBuilder sb = new StringBuilder();
        sb.append(AGS_START);
        sb.append(System.lineSeparator());
        sb.append(mAGS_data);
        sb.append(System.lineSeparator());
        sb.append(AGS_END);
        sb.append(System.lineSeparator());
        String line = "";
        int linecount = 0;
        String s1 = sb.toString();
       
        BufferedReader br = new BufferedReader( new StringReader(s1) );
        
        try {
            
         //   while (!isInterrupted()) {   
                while ((line = br.readLine()) != null) { 
                        mSocketWriter.write(line);
                        mSocketWriter.newLine();
                        linecount++;
                }
            System.out.println("AGS data sent (" + linecount + ")");
            mSocketWriter.flush();
            status = agsml.AGS_Server.ClientStatus.SENT_AGS;
        //    }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static boolean empty( final String s ) {
  // Null-safe, short-circuit evaluation.
  return s == null || s.trim().isEmpty();
} 
}

        
        
