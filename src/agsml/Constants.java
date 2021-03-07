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
public final class Constants {

    public final static String AGS_HOLELIST = "HoleList";
    public final static String AGS_HOLELISTFILE = "HoleListFile";
    
    public final static String AGS_OUTPUTFOLDER = "AGSOutputAGSMLFolder";
    public final static String AGS_OUTPUTFILE = "AGSOutputAGSMLFile";
    public final static String AGS_OUTPUTFILEFOLDER = "AGSOutputFileFolder";
    
    public final static String AGS_OUTPUTFILEOVERWRITE = "AGSOuputAGSMLFileOverwrite";
    public final static String AGS_OUTPUTFILEDATETIMESTAMP = "AGSOuputAGSMLFileDateTimeStamp";
    
    public final static String AGS_OUTPUTFIELD = "AGSOutputAGSMLField";
    public final static String AGS_OUTPUT64BINARYFIELDS = "AGSOutput64BinaryFields";
    
    public final static String AGS_PROJECTID = "AGSProjectID";
    public final static String AGS_OUTPUTFILEBASEDONFIELD = "AGSOutputAGSMLFileFromField";
    
    public final static String AGS_DATASTRUCTURE = "DataStructureAGSML";
    
    public final static String AGS_DATASTRUCTUREFILENAME = "AGSDataStructureFile";
    public final static String AGS_DATASTRUCTUREID = "AGSDataStructureID";
    public final static String AGS_DATASTRUCTURESERIESID = "AGSDataStructureSeriesID";
    public final static String AGS_DATASTRUCTURESERIES = "DataStructureSeries";
    public final static String AGS_DATASTRUCTUREAGSVERSION = "agsversion";
     
    public final static String AGS_DATASTRUCTURE_NODETYPE = "type";
    public final static String AGS_TABLE_NAME = "AGSTableName";
    
    public final static String AGS_DEFAULT = "Default";
    public final static String AGS_ATTRIBUTE_ID = "id";
    public final static String AGS_ATTRIBUTE_AGSVERSION = "agsversion";  
    public final static String AGS_ATTRIBUTE_REMARKS = "remarks";  
    public final static String AGS_ATTRIBUTE_DESCRIPTION = "description";  
    public final static String AGS_ATTRIBUTE_SERIES = "series";  
    public final static String AGS_ATTRIBUTE_AGSTABLE = "agstable";  
    public final static String AGS_ATTRIBUTE_FOREACH = "foreach";  
    public final static String AGS_ATTRIBUTE_STYLESHEET = "stylesheet"; 
    
    public final static String AGS_DICTIONARYFILENAME = "AGSDictionaryFile";
    public final static String AGS_DICTIONARYID = "AGSDictionaryID";
    
    public final static String AGS_DATASOURCE = "AGSDataSource"; 
    public final static String AGS_STRICTLYONLY = "AGSFieldsOnly";

    public final static String AGS_DATASTRUCTURETYPE = "type";
    
    public final static String AGS_PROCESSFILE = "AGSProcessFile";
    
    public final static String AGS_DATAQUERY = "DataQueryAGSML";
    public final static String AGS_DATAQUERY_KML = "DataQueryKML";
    
    public final static String AGS_DATASOURCE_STRING = "AGS_STRING";
    public final static String AGS_DATASOURCE_FILE = "AGS_FILE";
    
    public final static String AGS_DICTIONARY_TAG = "DictionaryAGSML";
    public final static String AGS_VERSION_TAG = "version";
    public final static String AGS_TAG = "ags";
    public final static String AGS_DICTIONARYID_DEFAULT = "AGS31a";
    public final static String AGS_DATASTRUCTURESERIESID_DEFAULT = "AGSProjectByGeol";
    
//    private final String constGintRecID = "GintRecID";
//    private final String constGintProjectID = "gINTProjectID";
    public final static String GINT_NAME = "GINT";
    
    public final static String AGS_FORMAT_SQL = "sql";
    public final static String AGS_FORMAT_AGS = "ags";
    public final static String AGS_FORMAT_XML = "agsml";
    public final static String AGS_FORMAT_GINT = "gint";

    public final static String ATHENTICATE_USER ="ge_user";
    public final static String ATHENTICATE_PASSWORD ="ge_password";
    public final static String ATHENTICATE_PAGE ="ge_login_page";
    
    public final static String DICTIONARY_FILE = "DictionaryFile";
    public final static String DATASTRUCTURESERIES = "DataStructureSeries";
    public final static String LOCALPORT = "LocalPort";
    public final static String CONFIG_FILENAME = "config.properties";
    public final static String LOG_FOLDER = "LogFolder";
    public final static String LOG_FORMAT = "[%1$tF %1$tT] [%4$-7s] %5$s %n";
    public final static String LOG_DATEFORMAT = "yyyyMMddHHmm";
    public final static String LOG_SIMPLE_FORMAT_JAVA_UTIL = "java.util.logging.SimpleFormatter.format";
    public final static String USAGE = "Usage: agsmlConverter [-ags ags_inputfilename] [-dic dictionary_filename] [-ds datastructure_nodename] [-xml xml_outputfilename] [-start port] [-stop] [-process filename] [-gui] [-fileupload] [-server name port] [-help]";
    public final static String RESOURCE_FOLDER_REPLACE = "[RESOURCEFOLDER]";
    public final static String RESOURCE_FOLDER = "ResourceFolder";
    public final static String EMPTY = "";
    public final static String AGSML_ROOT = "agsml";
    
   
    //Hide the constructor
    private Constants(){}

       
    
    public static enum Lang {
            GEN (0)  {public int toInt() {return 0;}},
            AGS (1) {public int toInt() {return 1;}},
            SQL (2) {public int toInt() {return 2;}},
            XML (3) {public int toInt() {return 3;}},
            GINT (4) {public int toInt() {return 4;}};
        private int value;     
        public abstract int toInt();
        private Lang(int value) {
           this.value=value;
       }       
    }
    
    public static enum AGSVersion {
        NONE    (0) {   public int toInt() {return 0;}
                            public String toId() {return "NONE";}
                            public double toDouble() {return 0;}
                            public String toVersion() {return "";}
                            public String toMajorVersion() {return "";}
                        },
        AGS30 (300) {   public int toInt() {return 300;}
                            public String toId() {return "AGS3.0";}
                            public double toDouble() {return 3;}
                            public String toVersion() {return "3.0";}
                            public String toMajorVersion() {return "3.0";}
                        },
        AGS31 (310) {   public int toInt() {return 310;}
                            public String toId() {return "AGS3.1";}
                            public double toDouble() {return 3.1;}
                            public String toVersion() {return "3.1";}
                            public String toMajorVersion() {return "3.1";}
                        },
        AGS31a (311){   public int toInt() {return 311;}
                            public String toId() {return "AGS3.1a";}
                            public double toDouble() {return 3.11;}
                            public String toVersion() {return "3.11";}
                            public String toMajorVersion() {return "3.1";}
                        },
        AGS403 (403){   public int toInt() {return 403;}
                            public String toId() {return "AGS4.03";}
                            public double toDouble() {return 4.03;}
                            public String toVersion() {return "4.03";}
                            public String toMajorVersion() {return "4.0";}
                        },
        AGS404 (404){   public int toInt() {return 404;}
                            public String toId() {return "AGS4.04";}
                            public double toDouble() {return 4.04;}
                            public String toVersion() {return "4.04";}
                            public String toMajorVersion() {return "4.0";}
                        };
        private int value;     
        public abstract int toInt();
        public abstract String toId();
        public abstract double toDouble();
        public abstract String toVersion();
        public abstract String toMajorVersion();
        private AGSVersion(int value) {
           this.value=value;
       }
    } 
     public static String getAGSVersionId(agsml.Constants.AGSVersion ags) {
        switch (ags) {
            case NONE: 
                return agsml.Constants.AGSVersion.NONE.toId();
            case AGS30: 
                return agsml.Constants.AGSVersion.AGS30.toId();  
            case AGS31: 
                return agsml.Constants.AGSVersion.AGS31.toId();  
            case AGS31a: 
                return agsml.Constants.AGSVersion.AGS31a.toId(); 
            case AGS403: 
                return agsml.Constants.AGSVersion.AGS403.toId();   
            case AGS404: 
               return agsml.Constants.AGSVersion.AGS404.toId(); 
        }
        return "NONE";
    } 
    
    public static agsml.Constants.AGSVersion getAGSVersion(String s1) {
        
        String s2 = s1.replaceAll("a", "1");
           
        float f1 = Float.valueOf(s2) * 100;
        
        int i1 = (int) f1;
        
        switch (i1) {
            case 30: 
                return agsml.Constants.AGSVersion.AGS30;
            case 310: 
                return agsml.Constants.AGSVersion.AGS31;      
            case 311: 
                return agsml.Constants.AGSVersion.AGS31a;    
            case 403: 
               return agsml.Constants.AGSVersion.AGS403;   
            case 404: 
               return agsml.Constants.AGSVersion.AGS404;  
        }
        return agsml.Constants.AGSVersion.NONE;
    }

    public agsml.Constants.AGSVersion getAGSVersion(int value) {
        
        switch (value) {
            case 30: 
                return agsml.Constants.AGSVersion.AGS30;
            case 310: 
                return agsml.Constants.AGSVersion.AGS31;      
            case 311: 
                return agsml.Constants.AGSVersion.AGS31a;    
            case 403: 
               return agsml.Constants.AGSVersion.AGS403;   
            case 404: 
               return agsml.Constants.AGSVersion.AGS404;  
        }
        return agsml.Constants.AGSVersion.NONE;
    }
    
   public static enum AGS_SOURCETYPE {
        FILE {
            public String toString() {
            return "AGS_FILE";
            }
        },
        STRING {
            public String toString() {
            return "AGS_STRING";
            }
        }
}

    public final static String SQLXML_SELECT = "SELECT";
    public final static String  SQLXML_WHERE = "WHERE";
    public final static String SQLXML_FROM = "FROM";
    public final static String SQLXML_QUERY = "QUERY";
    public final static String SQLXML_SINGLESPACE = " ";
    public final static String SQLXML_END = ";";
    public final static String SQLXML_DICTIONARY_TABLE = "dictionarytable";
    public final static String SQLXML_TABLE_ID = "id";
    public final static String SQLXML_TABLE_TAG = "tabletag";
    public final static String SQLXML_ROW_TAG = "rowtag";
    public final static boolean SQLXML_ADDHOLEFILENAME = true;
    public final static String SQLXML_HOLEFILETAGNAME = "holeFilename";
    
    
    public final static String SQLXML_KMLEASTING_TAG = "kmlEasting";
    public final static String SQLXML_KMLNORTHING_TAG = "kmlNorthing";
    public final static String SQLXML_KMLLATITUDE_TAG = "kmlLatitude";
    public final static String SQLXML_KMLLONGITUDE_TAG = "kmlLongitude";
    public final static String SQLXML_KMLDIAMETER_TAG = "kmlDiameter";
    public final static String SQLXML_KMLRADIUS_TAG = "kmlRadius";  
    public final static String SQLXML_KMLNAME_TAG = "kmlName";
    public final static String SQLXML_KMLNAMESPACE = "http://earth.google.com/kml/2.2";
    public final static String SQLXML_KMLEXTENDEDDATA ="kmlExtendedData";
    public final static String SQLXML_KMLDESCRIPTION_TAG ="kmlDescription";
    public final static String SQLXML_KMLGEOMETRYTYPE_TAG = "kmlGeometryType";
    
    public static enum SQLXML_KMLInputCoordType {
        kmlNone,
        kmlOSNationalGrid,
        kmlLongitudeLatitude;
    }

    public static enum SQLXML_KMLGeometryType {
        kmlNone,
        kmlPoint,
        kmlLineString,
        kmlPolygon,
        kmlCircle
    }
    
    public final static String AGS_DATATSTRUCTURE_REPEAT_ATTR = "foreach";
    public final static String AGS_DATATSTRUCTURE_HOLE_NODE = "Hole";
    public final static String AGS_DATATSTRUCTURE_GEOL_NODE = "Geol";
    public final static String AGS_DATATSTRUCTURE_PROJ_NODE = "Proj";
    public final static String AGS_DATATSTRUCTURE_ABBR_NODE ="Abbr";
    public final static String AGS_DATATSTRUCTURE_UNIT_NODE ="Unit";
    public final static String AGS_DATATSTRUCTURE_CODE_NODE ="Code";
    public final static String AGS_HOLEID = "HoleId";
    
    
}

