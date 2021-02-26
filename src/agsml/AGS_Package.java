/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agsml;

/**
 *
 * @author thomsonsj
 */
public class AGS_Package {
        
    public String data_series;
    public String data_dictionary;
    public String data_ags;
    public String data_xml;
    public ContentType data_type; 
    public final static String FILE_START = "[file_start]";
    public final static String FILE_END = "[file_end]";
    public final static String AGS_START = "[ags_start]";
    public final static String AGS_END = "[ags_end]";
    public final static String XML_START = "[xml_start]";
    public final static String XML_END = "[xml_end]";
    public final static String DICTIONARY_START = "[dictionary_start]";
    public final static String DICTIONARY_END = "[dictionary_end]";
    public final static String DATASTRUCTURE_START = "[datastructure_start]";
    public final static String DATASTRUCTURE_END = "[datastructure_end]";
    
    public enum ContentType {
        AGS,
        XML
    }
   
    public AGS_Package(ContentType type, String contents) {
        
        data_type = type;
        
        if (data_type == AGS_Package.ContentType.AGS) {
            getAGSData(contents);
            getAGSDataStructureSeries(contents);
            getAGSDictionary(contents);
        }
        
        if (data_type == AGS_Package.ContentType.XML) {
             getXMLData(contents);
        }
    }
    public AGS_Package (String ags, String dictionary, String datastructure) {
        data_type = AGS_Package.ContentType.AGS;
        data_ags = ags;
        data_dictionary = dictionary;
        data_series = datastructure; 
                
    }   
     public AGS_Package (String xml) {
        data_type = AGS_Package.ContentType.XML;
        data_xml = xml;
    } 
    public String getContents () {
        try {
            if (data_type==AGS_Package.ContentType.AGS) {
                return getContentsAGS();
            }
        
            if (data_type==AGS_Package.ContentType.XML) {
                return getContentsXML();
            }
        
            throw new Exception("AGS Package content type not given");
        
        } catch (Exception e){
            return Constants.EMPTY;
        } 
    }
    public String getContentsAGS(){
            
        StringBuilder sb = new StringBuilder();
            
            sb.append(AGS_Package.FILE_START);
            sb.append(System.lineSeparator());
            sb.append(AGS_Package.DICTIONARY_START);
                      sb.append(data_dictionary);
            sb.append(AGS_Package.DICTIONARY_END);
            sb.append(System.lineSeparator());
            sb.append(AGS_Package.DATASTRUCTURE_START);
                     sb.append(data_series);
            sb.append(AGS_Package.DATASTRUCTURE_END);
            sb.append(System.lineSeparator());
            sb.append(AGS_Package.AGS_START);
                     sb.append(data_ags);
            sb.append(AGS_Package.AGS_END);
            sb.append(System.lineSeparator());
            sb.append(AGS_Package.FILE_END);
            sb.append(System.lineSeparator());
            
        return sb.toString();
            
    }
     public String getContentsXML(){
            
        StringBuilder sb = new StringBuilder();
            
            sb.append(AGS_Package.FILE_START);
            sb.append(System.lineSeparator());
            sb.append(AGS_Package.XML_START);
                    sb.append(data_xml);
            sb.append(AGS_Package.XML_END);
            sb.append(System.lineSeparator());
            sb.append(AGS_Package.FILE_END);
            sb.append(System.lineSeparator());
            
        return sb.toString();
            
    }
    public int CheckContents(ContentType type) {
        int retvar = 0;
        
        if (type== ContentType.AGS) {
        if (data_ags.isEmpty()) retvar =+ -1;
        if (data_dictionary.isEmpty()) retvar =+ -1;
        if (data_series.isEmpty()) retvar =+ -1;
        return retvar;
        }
        
        if (type== ContentType.XML) {
        if (data_xml.isEmpty()) retvar =+ -1;
        return retvar;
        }
        
        return retvar;
    }
    public boolean HasAGSData() {
            return !data_ags.isEmpty();
    }
    public boolean HasDataStructureSeries() {
            return !data_series.isEmpty();
    }
    public boolean HasDataDictionary() {
            return !data_dictionary.isEmpty();
    }
    public boolean HasXMLData() {
            return !data_xml.isEmpty();
    }
    private boolean getXMLData(String contents) {
        int from = contents.indexOf(XML_START,0)+ XML_START.length();
        int to = contents.indexOf(XML_END,0);
        data_xml = contents.substring(from, to);
        return !data_xml.isEmpty();
    }
    private boolean getAGSData(String contents) {
        int from = contents.indexOf(AGS_START,0)+ AGS_START.length();
        int to = contents.indexOf(AGS_END,0);
        data_ags = contents.substring(from, to);
        return !data_ags.isEmpty();
    }
        private boolean getAGSDataStructureSeries(String contents) {
        int from = contents.indexOf(DATASTRUCTURE_START,0)+ DATASTRUCTURE_START.length();
        int to = contents.indexOf(DATASTRUCTURE_END,0);
        data_series= contents.substring(from, to);
        return !data_series.isEmpty();
    }
    private boolean getAGSDictionary(String contents) {
        int from = contents.indexOf(DICTIONARY_START,0)+ DICTIONARY_START.length();
        int to = contents.indexOf(DICTIONARY_END,0);
        data_dictionary = contents.substring(from, to);
        return !data_dictionary.isEmpty();
    } 
   }  
