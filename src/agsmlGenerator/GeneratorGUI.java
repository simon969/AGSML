package agsmlGenerator;

import java.io.*;
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import agsml.AGS_Converter;
import agsml.AGS_Server;
import agsml.AGS_Client;
import agsml.AGS_Dictionary;
import agsml.AGS_DataStructure;
import agsml.AGS_Data;
import agsml.Constants;
import static agsmlGenerator.Main.config;
import java.awt.event.WindowEvent;
import java.util.logging.StreamHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;
import java.util.ArrayList;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomsonsj
 */
public class GeneratorGUI extends javax.swing.JFrame {
   
    private JFileChooser fc;
    static private final String NEW_LINE = "\n";
    static private final String EMPTY = "";
    private AGS_Server ags_server;
    private ArrayList<AGS_Client> ags_clients = new ArrayList<AGS_Client>();
    private Properties props = null;
    static private final String EMPTY_INPUTSOURCE = "...InputSource" ;
    static private final String EMPTY_OUTPUTFILEFOLDER = "...OutputFileFolder" ;
    static private final String EMPTY_PROCESSFILE = "...ProcessFile" ;
    static private final String EMPTY_DICTIONARYFILE ="...DictionaryFile";
    static private final String EMPTY_LOCALPORT = "...port";
    static private final String EMPTY_SERVER = "...server";
    static private final String EMPTY_PORT = "...port";
    static private final String EMPTY_DBCONNECTION = "...DbConnection";
    static private final String EMPTY_PSTATEMENT = "...PStatement";
    static private final String AGS_EXT = "ags";
    static private final String XML_EXT = "xml";
    static private final String GINT_EXT = "gpj";
    static private final String ACCESS_EXT = "mdb;accdb";
    static private final int MAX_TEXTAREA =  Integer.MAX_VALUE / 2;
    
    public Logger log;
    /** 
     * Creates new form jfMainForm
     */
    public GeneratorGUI(String[] args, Properties Props, Logger Log) {
      initComponents();
      
      props = Props;
      log = Log;
      
      assignProperties(true);
      
      // assign command line arguments;        
      String dic_file = getargvalue(args,Main.ARG_DICTIONARY);
      if (!dic_file.isEmpty()) {
            AGS_Data f = new AGS_Data (dic_file);
            f.setResourceFolder(props.getProperty(Constants.RESOURCE_FOLDER));
            String full_dic_file = f.getFullResourcePath();
       assignValue(jTextDictionaryFile,full_dic_file,EMPTY_DICTIONARYFILE, true);
      }
      
       String server = getargvalue(args, Main.ARG_SERVER);
       String port = getargvalue(args, server);
       String port_start = getargvalue(args,Main.ARG_START); 
       
       if (server.isEmpty() && !port_start.isEmpty()) {
            server = "localhost";
            port = port_start;      
       }
       
       assignValue(jTextServerPort,port,EMPTY_PORT,true);
       assignValue(jTextServer,server,EMPTY_SERVER,true);
       assignValue(jTextLocalPort,port_start,EMPTY_LOCALPORT,true);
        
      updateComboBoxAGSDataStructureId();
    }
    
    public void setLogger(Logger Log) {
     log =  Log;
    }
    void assignProperties(boolean Overwrite) {
       
        if (props !=null) {
            assignValue(jTextInputSource,props.getProperty("InputSource"),EMPTY_INPUTSOURCE, Overwrite);
            assignValue(jTextOutputFileFolder,props.getProperty("OutputFolder"), EMPTY_OUTPUTFILEFOLDER, Overwrite);
        
            String dic_file = props.getProperty("DictionaryFile");
            AGS_Data f = new AGS_Data (dic_file);
            f.setResourceFolder(props.getProperty(Constants.RESOURCE_FOLDER));
            String full_dic_file = f.getFullResourcePath();
      
            assignValue(jTextDictionaryFile,full_dic_file,EMPTY_DICTIONARYFILE, Overwrite);
            assignValue(jTextLocalPort,props.getProperty("LocalPort"),EMPTY_LOCALPORT,Overwrite);
            assignValue(jTextServerPort,props.getProperty("ServerPort"),EMPTY_PORT,Overwrite);
            assignValue(jTextServer,props.getProperty("Server"),EMPTY_SERVER,Overwrite);
            assignValue(jTextDbConnection,props.getProperty("DBConnection"),EMPTY_DBCONNECTION,Overwrite);
            assignValue(jTextPStatement,props.getProperty("PStatement"),EMPTY_PSTATEMENT,Overwrite);
    
        }
        
    }
    private void assignValue(JTextField tf, String newvalue, String defaultvalue, Boolean Overwrite) {
        if (newvalue != null && !newvalue.isEmpty()) {
            String currentvalue = tf.getText();
                if (currentvalue.equals(defaultvalue) || currentvalue.equals(Constants.EMPTY)) {
                    tf.setText(newvalue);
                } else if (Overwrite==true) {
                    tf.setText(newvalue);
                }
        } else {
            tf.setText(defaultvalue);
        }
            
    }
    private String getargvalue(String[] args, String arg) {
        
       for (int i = 0; i < args.length-1; i++) {
           if (args[i].equals(arg)) {
               return args[i+1];
           }
       }
       return Constants.EMPTY; 
        
    }
   // String getJarFolder() {
   //     try {
   //   return new File(AGSMLConverter.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
   //     } catch (Exception e) {
   //         return "";
   //         
   //     }
   // }
   
    public void setProperties(Properties p) {
         props = p;
    }
    private void initCustomOutputStream() {
    
     //       PrintStream printStream = new PrintStream(new JTextAreaOutputStream(jTextArea1));
       //     SimpleFormatter sf = new SimpleFormatter();
       //     StreamHandler sh = new StreamHandler(printStream,sf);  
       //     Main.log.addHandler(sh);
           // System.setOut(printStream);
           // System.setErr(printStream);        
    }
    public void showInfo(String s1) {
      if(jTextArea1.getText().length() + s1.length() > MAX_TEXTAREA) {
          jTextArea1.setText(EMPTY);
      return;
      } 
      jTextArea1.append(s1);
      this.validate();
  }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTextInputSource = new javax.swing.JTextField();
        jButtonInputData = new javax.swing.JButton();
        jButtonOutputFileFolder = new javax.swing.JButton();
        jTextOutputFileFolder = new javax.swing.JTextField();
        jButtonDictionaryFile = new javax.swing.JButton();
        jComboBoxAGSMLDataStructureSeries = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTextDictionaryFile = new javax.swing.JTextField();
        jTextProcessFile = new javax.swing.JTextField();
        jButtonIProcessFile = new javax.swing.JButton();
        jbuttonRun = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jTextServer = new javax.swing.JTextField();
        jTextServerPort = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextDbConnection = new javax.swing.JTextField();
        jTextPStatement = new javax.swing.JTextField();
        jCheckBoxOverwrite = new javax.swing.JCheckBox();
        jCheckBoxDateStamp = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jTextLocalPort = new javax.swing.JTextField();
        jButtonStartServer = new javax.swing.JButton();
        jButtonStopServer = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AGS to XML Conversion Tool");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTextInputSource.setText("...InputSource");
        jTextInputSource.setToolTipText("");
        jTextInputSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextInputSourceActionPerformed(evt);
            }
        });

        jButtonInputData.setText("...");
        jButtonInputData.setToolTipText("");
        jButtonInputData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInputDataActionPerformed(evt);
            }
        });

        jButtonOutputFileFolder.setText("...");
        jButtonOutputFileFolder.setToolTipText("");
        jButtonOutputFileFolder.setName(""); // NOI18N
        jButtonOutputFileFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOutputFileFolderActionPerformed(evt);
            }
        });

        jTextOutputFileFolder.setText("...OutputFileFolder");
        jTextOutputFileFolder.setToolTipText("");

        jButtonDictionaryFile.setText("...");
        jButtonDictionaryFile.setToolTipText("");
        jButtonDictionaryFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDictionaryFileActionPerformed(evt);
            }
        });

        jComboBoxAGSMLDataStructureSeries.setEditable(true);
        jComboBoxAGSMLDataStructureSeries.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("DataStructureSeries");

        jTextDictionaryFile.setText("...DictionaryFile");
        jTextDictionaryFile.setToolTipText("");

        jTextProcessFile.setText("...ProcessFile");
        jTextProcessFile.setToolTipText("");
        jTextProcessFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextProcessFileActionPerformed(evt);
            }
        });

        jButtonIProcessFile.setText("...");
        jButtonIProcessFile.setToolTipText("");
        jButtonIProcessFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIProcessFileActionPerformed(evt);
            }
        });

        jbuttonRun.setText("Run");
        jbuttonRun.setToolTipText("");
        jbuttonRun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbuttonRunMouseClicked(evt);
            }
        });
        jbuttonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbuttonRunActionPerformed(evt);
            }
        });

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jTextServer.setText("...server");

        jTextServerPort.setText("...port");

        jLabel4.setText("InputSource");

        jLabel5.setText("OutputFileFolder");

        jLabel6.setText("ProcessFile");

        jLabel7.setText("Port");

        jLabel8.setText("DictionaryFile");

        jLabel9.setText("Server");

        jLabel10.setText("DbConnection");

        jLabel11.setText("PStatement");

        jTextDbConnection.setText("...DbConnection");
        jTextDbConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDbConnectionActionPerformed(evt);
            }
        });

        jTextPStatement.setText("...PStatement");
        jTextPStatement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPStatementActionPerformed(evt);
            }
        });

        jCheckBoxOverwrite.setText("Overwrite");

        jCheckBoxDateStamp.setText("DateStamp");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextServer, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                                .addComponent(jTextServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextDbConnection)
                                    .addComponent(jTextPStatement)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbuttonRun, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBoxAGSMLDataStructureSeries, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(49, 49, 49)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextOutputFileFolder)
                                    .addComponent(jTextDictionaryFile)
                                    .addComponent(jTextInputSource)
                                    .addComponent(jTextProcessFile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jCheckBoxOverwrite)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBoxDateStamp)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonDictionaryFile)
                            .addComponent(jButtonIProcessFile)
                            .addComponent(jButtonOutputFileFolder)
                            .addComponent(jButtonInputData))))
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonInputData)
                    .addComponent(jTextInputSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextOutputFileFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonOutputFileFolder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxOverwrite)
                    .addComponent(jCheckBoxDateStamp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDictionaryFile)
                    .addComponent(jTextDictionaryFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxAGSMLDataStructureSeries, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 52, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextProcessFile, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonIProcessFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextDbConnection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextPStatement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jbuttonRun))
                .addContainerGap())
        );

        jTabbedPane1.addTab("AGS to AGSML", jPanel2);

        jTextLocalPort.setText("...port");

        jButtonStartServer.setText("Start");
        jButtonStartServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartServerActionPerformed(evt);
            }
        });

        jButtonStopServer.setText("Stop");
        jButtonStopServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopServerActionPerformed(evt);
            }
        });

        jLabel3.setText("Localhost:");

        jScrollPane2.setViewportView(jTree1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextLocalPort, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButtonStartServer)
                    .addComponent(jButtonStopServer))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextLocalPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonStartServer)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonStopServer)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Server", jPanel3);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbuttonRunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbuttonRunMouseClicked

           
    }//GEN-LAST:event_jbuttonRunMouseClicked
    private int initFileSetTest101() {
        
        this.jTextOutputFileFolder.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\kmlShaftPoints\\Shafts2.xml");
        this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\kmlShaftPoints\\Process_File_ShaftPoints.xml");
        this.jTextInputSource.setText("jdbc:sqlserver://UKLON3SQ033;instanceName=CORPDB_2012;databaseName=tideway_c410_all;integratedSecurity=true");

      // this.InputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
      // this.InputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.gpj");
      
       return 1;
    }
    private int initFileSetTest102() {
        
        this.jTextOutputFileFolder.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\Monitoring Points KML\\kml_test1.kml");
        this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\Monitoring Points KML\\process_file.xml");
        this.jTextInputSource.setText("jdbc:sqlserver://UKLON3SQ033;instanceName=CORPDB_2012;databaseName=tideway_Groundwater;integratedSecurity=true");
     //  this.InputSource.setText("jdbc:sqlserver://UKLON3SQ033;instanceName=CORPDB_2012;databaseName=tideway_c410_all;integratedSecurity=true");

      // this.InputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
      // this.InputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.gpj");
      
       return 1;
    } 
    private int initFileSetTest103() {
        
        this.jTextOutputFileFolder.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\xmlBoreholesByShaft\\agsml_test2\\test2");
        this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\xmlBoreholesByShaft\\agsml_test2\\Process_File.xml");
        this.jTextInputSource.setText("jdbc:sqlserver://UKLON3SQ033;instanceName=CORPDB_2012;databaseName=tideway_c410_all;integratedSecurity=true");
        this.jTextDictionaryFile.setText("C:\\Users\\thomsonsj\\Documents\\NetBeansProjects\\AGSML\\schemas\\DictionaryAgsml_0.0.6.xml");
        updateComboBoxAGSDataStructureId();
      // this.InputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
      // this.InputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.gpj");
      
       return 1;
    } 
    private int initFileSetTest104() {
        
        this.jTextOutputFileFolder.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\xmlBoreholesByShaft\\agsml_test2\\test2.xml");
        this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\xmlBoreholesByShaft\\agsml_test2\\Process_File.xml");
        this.jTextInputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.mdb");
        this.jTextDictionaryFile.setText("C:\\Users\\thomsonsj\\Documents\\NetBeansProjects\\AGSML\\schemas\\DictionaryAgsml_0.0.6.xml");
        updateComboBoxAGSDataStructureId();
      // this.InputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
      // this.InputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.gpj");
      
       return 1;
    } 
    private int initFileSetTest105() {
        
        this.jTextOutputFileFolder.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\xmlBoreholesByShaft\\agsml_test2\\test2");
        this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\xmlBoreholesByShaft\\agsml_test2\\Process_File.xml");
        this.jTextInputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
        this.jTextDictionaryFile.setText("C:\\Users\\thomsonsj\\Documents\\NetBeansProjects\\AGSML\\schemas\\DictionaryAgsml_0.0.6.xml");
        updateComboBoxAGSDataStructureId();
      // this.InputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
      // this.InputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.gpj");
      
       return 1;
    } 

    private int initFileSetTest106() {
        
        this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\xmlBoreholesByShaft\\agsml_test3\\Process_File101.xml");
       updateComboBoxAGSDataStructureId();
      // this.InputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
      // this.InputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.gpj");
      
       return 1;
    } 
    private int initFileSetTest107() {
        
        this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\kmlShaftPoints\\Process_File_ShaftPoints2.xml");
        updateComboBoxAGSDataStructureId();
      // this.InputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
      // this.InputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.gpj");
      
       return 1;
    } 
    private int initFileSetSilvertownTunnel() {
        
        this.jTextOutputFileFolder.setText("C:\\Users\\ThomsonSJ\\Documents\\2017\\Silvertown Tunnel\\100 GI Data\\gINT-XML3");
        this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2017\\Silvertown Tunnel\\100 GI Data\\gINT-XML3\\Process_File_ByProject.xml");
        this.jTextInputSource.setText("C:\\Users\\ThomsonSJ\\Documents\\2017\\Silvertown Tunnel\\100 GI Data\\gINT\\silvertown_ags4.gpj");
        this.jTextDictionaryFile.setText("C:\\Users\\thomsonsj\\Documents\\NetBeansProjects\\AGSML\\schemas\\DictionaryAgsml_0.0.10.xml");
        updateComboBoxAGSDataStructureId();
      // this.InputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
      // this.InputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.gpj");
      
       return 1;
    } 
    private int initFileSetTest108() {
        
        this.jTextOutputFileFolder.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\RW\\gINT\\xmlBoreholes\\2017-05-19");
        this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\RW\\gINT\\xmlBoreholes\\2017-05-23\\Process_File.xml");
      //  this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2017\\Silvertown Tunnel\\100 GI Data\\gINT-XML2\\Process_File3.xml");
        this.jTextInputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\\\TTT Central C410\\RW\\gINT\\xmlBoreholes\\2017-05-19");
        this.jTextDictionaryFile.setText("C:\\Users\\thomsonsj\\Documents\\NetBeansProjects\\AGSML\\schemas\\DictionaryAgsml_0.0.10.xml");
         updateComboBoxAGSDataStructureId();
      // this.InputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
      // this.InputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.gpj");
      
       return 1;
    }  
        private int initFileSetTest109() {
        
        this.jTextOutputFileFolder.setText("");
        this.jTextProcessFile.setText("");
      //  this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2017\\Silvertown Tunnel\\100 GI Data\\gINT-XML2\\Process_File3.xml");
        this.jTextInputSource.setText("C:\\Users\\ThomsonSJ\\Downloads\\107971\\E7037A_Burton SWWM_Final Factual Report AGS4.ags");
        this.jTextDictionaryFile.setText("C:\\Users\\thomsonsj\\Documents\\NetBeansProjects\\AGSML\\schemas\\DictionaryAgsml_0.0.11.xml");
       this.jTextLocalPort.setText("1045");
           updateComboBoxAGSDataStructureId();
      // this.InputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
      // this.InputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.gpj");
      
       return 1;
    } 
        
    private int initFileSetTest110() {
        
        this.jTextOutputFileFolder.setText("");
        this.jTextProcessFile.setText("");
      //  this.jTextProcessFile.setText("C:\\Users\\thomsonsj\\Documents\\2017\\Silvertown Tunnel\\100 GI Data\\gINT-XML2\\Process_File3.xml");
        this.jTextInputSource.setText("C:\\Users\\simon.DORSETRECLAIM\\Downloads\\107971\\E7037A_Burton SWWM_Final Factual Report AGS4.ags");
        this.jTextDictionaryFile.setText("C:\\Users\\simon.DORSETRECLAIM\\Documents\\NetBeansProjects\\AGSML\\schemas\\DictionaryAgsml_0.0.11.xml");
              updateComboBoxAGSDataStructureId();
      // this.InputSource.setText("jdbc:sqlserver://UKCRD1LT40672;instanceName=SQLEXPRESS11;databaseName=tideway_c410_all;integratedSecurity=true");
      // this.InputSource.setText("C:\\Users\\thomsonsj\\Documents\\2016\\TTT Central C410\\gINT\\tideway_c410_all.gpj");
      
       return 1;
    }     
    JFileChooser init_FileChooser(String initFolder) {
        if (fc==null) {
            fc = new JFileChooser("./");
        }
        File file = new File (initFolder);
        fc.setCurrentDirectory(file);
        fc.removeChoosableFileFilter(fc.getFileFilter());
     
        return fc;
    }
            
    private int addAttributesToComboBox (NodeList nl, String AttributeName, JComboBox<String> jcb1, String selectItem ) {
    
    try {
       int selectId = 0;
       
       for (int i=0; i <= nl.getLength();i++){
           Element el = (Element) nl.item(i);
           if (el != null ){
            String s1 = el.getAttribute(AttributeName);
                if (s1.equalsIgnoreCase(selectItem)){
                    selectId=i;
                }
                if (s1.isEmpty()!=true) {
                    jcb1.addItem(s1);
               }
           }
       }
     
       jcb1.setSelectedIndex(selectId);
     
     return nl.getLength();    
    } 
    catch (Exception ex) {
     log.severe(ex.getMessage());
     return -1;
    } 
   }
    
    private int FileExist(String FileName){
       AGS_Data f = new AGS_Data(FileName);
       return f.FileExist();
    }
    private int FolderExist(String FolderName) {
       AGS_Data f = new AGS_Data(FolderName);
       return f.FolderExist();
    }
    private String FileExtension(String FileName){
       AGS_Data f = new AGS_Data(FileName);
       return f.FileExtension();
    }
    private String usingBufferedReader(String filePath) 
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
		showInfo (e.getMessage() +  NEW_LINE);
	}
	return contentBuilder.toString();
    }
    
    private boolean tryAGSConverter () {
        
       try {
           
       
        AGS_Converter ac = new AGS_Converter(); 
        ac.setLogger(log);
       
        if (!this.jTextProcessFile.getText().isEmpty() && !jTextProcessFile.getText().equalsIgnoreCase(EMPTY_PROCESSFILE)) {
            ac.setProperty(agsml.Constants.AGS_PROCESSFILE, NEW_LINE);
            ac.Process (jTextProcessFile.getText());
            return true;
        } 
        
        if (!jTextInputSource.getText().isEmpty() && !jTextInputSource.getText().equalsIgnoreCase(EMPTY_INPUTSOURCE)) {
           if (FileExist(jTextInputSource.getText()) == 1) { 
               String ext = FileExtension(jTextInputSource.getText()).toLowerCase();
               if (ext.equalsIgnoreCase(AGS_EXT)) {
               ac.AGS_to_AGSML(jTextInputSource.getText(), 
                       jTextOutputFileFolder.getText(), 
                       jTextDictionaryFile.getText(), 
                       jComboBoxAGSMLDataStructureSeries.getSelectedItem().toString());     
               return true;
               }

               if (ACCESS_EXT.contains(ext) || ext.equalsIgnoreCase(GINT_EXT)) { 
                ac.SQL_to_AGSML(jTextInputSource.getText(), 
                        jTextOutputFileFolder.getText(), 
                        jTextDictionaryFile.getText(), 
                        jComboBoxAGSMLDataStructureSeries.getSelectedItem().toString());
               return true;
               }

           } else
              // try server string
               ac.SQL_to_AGSML(jTextInputSource.getText(), 
                       jTextOutputFileFolder.getText(), 
                       jTextDictionaryFile.getText(), 
                       "", 
                       jComboBoxAGSMLDataStructureSeries.getSelectedItem().toString());
                return true;
        }
        } catch (Exception e) {
            log.severe(e.getMessage());
            return false;
        }
    return false;
    
    }
    private boolean tryAGSConverterThread () {
        
      try {
        
        AGS_Converter ac = new AGS_Converter(); 
        ac.setLogger(log);
       
        if (!this.jTextProcessFile.getText().isEmpty() && !jTextProcessFile.getText().equalsIgnoreCase(EMPTY_PROCESSFILE)) {
            ac.setProperty(agsml.Constants.AGS_PROCESSFILE, NEW_LINE);
        } 
        
        if (!jTextInputSource.getText().isEmpty() && !jTextInputSource.getText().equalsIgnoreCase(EMPTY_INPUTSOURCE)) {
           if (FileExist(jTextInputSource.getText()) == 1) { 
               String ext = FileExtension(jTextInputSource.getText()).toLowerCase();
               if (ext.equalsIgnoreCase(AGS_EXT)) {
                    ac.setProperty(agsml.Constants.AGS_DATASOURCE,jTextInputSource.getText() );
                    ac.setProperty(agsml.Constants.AGS_OUTPUTFILEFOLDER, jTextOutputFileFolder.getText() );
                    ac.setProperty(agsml.Constants.AGS_DICTIONARYFILENAME, jTextDictionaryFile.getText() );
                    ac.setProperty(agsml.Constants.AGS_DATASTRUCTURESERIESID,jComboBoxAGSMLDataStructureSeries.getSelectedItem().toString());
               }

               if (ACCESS_EXT.contains(ext) || ext.equalsIgnoreCase(GINT_EXT)) { 
                    ac.setProperty(agsml.Constants.AGS_DATASOURCE,jTextInputSource.getText() );
                    ac.setProperty(agsml.Constants.AGS_OUTPUTFILEFOLDER, jTextOutputFileFolder.getText() );
                    ac.setProperty(agsml.Constants.AGS_DICTIONARYFILENAME, jTextDictionaryFile.getText() );
                    ac.setProperty(agsml.Constants.AGS_DATASTRUCTURESERIESID,jComboBoxAGSMLDataStructureSeries.getSelectedItem().toString());
                }

           } 
        }
        
        ac.start();
        return true;
        
      } catch (Exception e) {
          log.severe(e.getMessage());
          return false;
      }
      
    }
    
    private boolean tryAGSServer() {
        
                      
        if (!jTextServer.getText().isEmpty() && !jTextServerPort.getText().isEmpty()) {
            if (!jTextInputSource.getText().isEmpty() && !jTextInputSource.getText().equalsIgnoreCase(EMPTY_INPUTSOURCE)) {
                if (FileExist(jTextInputSource.getText()) == 1) { 
                   String ext = FileExtension(jTextInputSource.getText().toLowerCase());
                   if (ext.equalsIgnoreCase(AGS_EXT)) {
                      // String ags_data = usingBufferedReader (jTextInputSource.getText());
                       // if (ags_data.length()> 0) {
                            try {
                                int port = Integer.parseInt(jTextServerPort.getText());
                                String server =  jTextServer.getText();
                                AGS_Client ac =  new AGS_Client (server, port);
                                
                                ac.setLogger(Main.log);
                                ac.setAGSFileIN(jTextInputSource.getText());
                                ac.setXMLFileSave(jTextOutputFileFolder.getText(),jCheckBoxOverwrite.isSelected(),jCheckBoxDateStamp.isSelected());
                                ac.setDictionaryFile(jTextDictionaryFile.getText());
                                ac.setDataStructureSeries(jComboBoxAGSMLDataStructureSeries.getSelectedItem().toString());
                                
                                if (!jTextDbConnection.getText().equalsIgnoreCase(EMPTY_DBCONNECTION)) {
                                ac.setXMLDatabaseSave(jTextDbConnection.getText(),jTextPStatement.getText());
                                }
                                
                                if (ac.ValidateInputData() == 0) {
                                    ags_clients.add(ac); 
                                    ac.start();
                                    return true;
                                } else {
                                    throw new Exception("Invalid AGS_Client Input");
                                }
                               
                            } catch (Exception e) {
                               log.severe(e.getMessage());
                               return false;
                            }
                            
                        //}
                   }
               }
            }
        }
        return false;
    }
    
    
    
    private void jbuttonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbuttonRunActionPerformed
       boolean ActionStarted;
       ActionStarted = false;
       
       if (ActionStarted==false) {
            log.info("Trying AGSServer..");
            ActionStarted = tryAGSServer();
       }
       
       if (ActionStarted==false) {
           log.info("Trying AGSConverter..");
           ActionStarted = tryAGSConverterThread();
       }
       

        
       if (ActionStarted==false) {
           log.info("...unable to complete run action");
       } else {
           log.info("...run action started");
       }
      
    }//GEN-LAST:event_jbuttonRunActionPerformed
    

         
    private void updateComboBoxAGSDataStructureId (){
        try {
                  if (!jTextDictionaryFile.getText().isEmpty() && !jTextDictionaryFile.getText().equalsIgnoreCase(EMPTY_DICTIONARYFILE)) { 
                  AGS_DataStructure agsds = new AGS_DataStructure(jTextDictionaryFile.getText());
                  this.jComboBoxAGSMLDataStructureSeries.removeAllItems();
                  String s1 = props.getProperty("DataStructure");
                  addAttributesToComboBox(agsds.allAGSDataStructureSeriesNodes(), "id", jComboBoxAGSMLDataStructureSeries, s1);
              }
        }
        catch (Exception e) {
          log.severe(e.getMessage());
        }    
    }          
               
    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jTextProcessFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextProcessFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextProcessFileActionPerformed

    private void jButtonIProcessFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIProcessFileActionPerformed

        try {
            init_FileChooser(this.jTextProcessFile.getText());

            fc.setMultiSelectionEnabled(false);
            fc.setDialogTitle("Select Process file");
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setFileFilter(new FileNameExtensionFilter("AGSML Process(*.agsml;*.xml)", "agsml","xml"));

            if (!this.jTextProcessFile.getText().isEmpty()) {
                File file = new File (this.jTextProcessFile.getText());
                fc.setSelectedFile(file);
            }

            int result =fc.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                this.jTextProcessFile.setText(file.getAbsolutePath());
                //This is where a real application would open the file.
                log.info("Selected Process file: " + file.getName() + ".");
            }
        }
        catch (Exception ex) {
            log.severe(ex.getMessage());
        }
    }//GEN-LAST:event_jButtonIProcessFileActionPerformed

    private void jButtonStopServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopServerActionPerformed
        // TODO add your handling code here:
        ags_server.shutdown();
        ags_server= null;
    }//GEN-LAST:event_jButtonStopServerActionPerformed

    private void jButtonStartServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartServerActionPerformed
        // TODO add your handling code here:
        
        try {
            int port = Integer.parseInt(jTextLocalPort.getText());
            if (ags_server != null ) {
                ags_server.shutdown();
                ags_server= null;
            }

            ags_server = new AGS_Server ();
            ags_server.setPort(port);
            ags_server.setDictionarySource(jTextDictionaryFile.getText());
            ags_server.setDataStructureSeries(jComboBoxAGSMLDataStructureSeries.getSelectedItem().toString());

            ags_server.start();
        } 
                
        catch (NumberFormatException e) {
        log.severe("Cannot start ags_server invalid port integer (jTextLocalPort=" + jTextLocalPort.getText() + ")");
        }
        
        catch (Exception e) {
        log.severe(e.getMessage());
        }

    }//GEN-LAST:event_jButtonStartServerActionPerformed

    private void jButtonDictionaryFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDictionaryFileActionPerformed

        try {
            init_FileChooser(jTextDictionaryFile.getText());

            fc.setMultiSelectionEnabled(false);
            fc.setDialogTitle("Select AGS Dictionary file");
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setFileFilter(new FileNameExtensionFilter("AGS Dictionary(*.xml)", "xml"));
            int result =fc.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                this.jTextDictionaryFile.setText(file.getAbsolutePath());
                log.info("Selected Dictionary File: " + file.getName());
                updateComboBoxAGSDataStructureId();
            } 
        }
        catch (Exception ex) {
            log.severe(ex.getMessage());
        }
    }//GEN-LAST:event_jButtonDictionaryFileActionPerformed

    private void jButtonOutputFileFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOutputFileFolderActionPerformed
        try {

            init_FileChooser(this.jTextOutputFileFolder.getText());
            File file1 = new File (this.jTextOutputFileFolder.toString());
                
            fc.setCurrentDirectory(file1);
            fc.setMultiSelectionEnabled(false);
            fc.setDialogTitle("Select Output File or Folder");
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int result = fc.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                this.jTextOutputFileFolder.setText(file.getAbsolutePath());
                log.info ("Selected Output File/Folder: " + file.getName());
            } 
        }
        catch (Exception ex) {
            log.severe(ex.getMessage());
        }
    }//GEN-LAST:event_jButtonOutputFileFolderActionPerformed

    private void jButtonInputDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInputDataActionPerformed

        try {

            // if (e.getSource() == openButton) {
                init_FileChooser(this.jTextInputSource.getText()) ;
                
                fc.setMultiSelectionEnabled(false);
                fc.setDialogTitle("Select AGS Data Source");
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setFileFilter(new FileNameExtensionFilter("AGS Data Source(*.ags;*.gpj;*.mdb;*.acdb)", "ags","gpj","mdb","acdb"));
                
                int result =fc.showOpenDialog(this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file2 = fc.getSelectedFile();
                    this.jTextInputSource.setText(file2.getAbsolutePath());
                    log.info("Selected AGS Data source: " + file2.getName());
                }             
            }
            catch (Exception ex) {
                log.severe(ex.getMessage());
            }

            //Handle save button action.
            // } else if (e.getSource() == saveButton)
    }//GEN-LAST:event_jButtonInputDataActionPerformed

    private void jTextInputSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextInputSourceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextInputSourceActionPerformed

    private void jTextDbConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDbConnectionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDbConnectionActionPerformed

    private void jTextPStatementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPStatementActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPStatementActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_formWindowClosed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        log.info("opened agsmlGeneratorGUI");
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        log.info("closed agsmlGeneratorGUI");
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GeneratorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
   
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GeneratorGUI(args, null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonDictionaryFile;
    private javax.swing.JButton jButtonIProcessFile;
    private javax.swing.JButton jButtonInputData;
    private javax.swing.JButton jButtonOutputFileFolder;
    private javax.swing.JButton jButtonStartServer;
    private javax.swing.JButton jButtonStopServer;
    private javax.swing.JCheckBox jCheckBoxDateStamp;
    private javax.swing.JCheckBox jCheckBoxOverwrite;
    private javax.swing.JComboBox<String> jComboBoxAGSMLDataStructureSeries;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextDbConnection;
    private javax.swing.JTextField jTextDictionaryFile;
    private javax.swing.JTextField jTextInputSource;
    private javax.swing.JTextField jTextLocalPort;
    private javax.swing.JTextField jTextOutputFileFolder;
    private javax.swing.JTextField jTextPStatement;
    private javax.swing.JTextField jTextProcessFile;
    private javax.swing.JTextField jTextServer;
    private javax.swing.JTextField jTextServerPort;
    private javax.swing.JTree jTree1;
    private javax.swing.JButton jbuttonRun;
    // End of variables declaration//GEN-END:variables
}
