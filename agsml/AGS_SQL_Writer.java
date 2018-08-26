package agsml;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author thomsonsj
 */
class fField {

    public String fName;
    public String fType;
    public String defaultValue;
};

class AGS_SQL_Writer extends SQL_Writer implements Converter {

    private AGS_ReaderLine m_lr;
  
    AGS_SQL_Writer(String source) {
        super(source);
    }

    public void setDictionary(String xmlDictFile) {
    try {    
        super.setAGSDictionary(xmlDictFile);
    }
    catch (Exception e) {
        
    }
    }

    public void setDataStructure(String xmlDataStructureFile) {
        super.setAGSDataStructureFile(m_onlyHoleId);
    }
    
    public void setAGSLineReader(AGS_ReaderLine lr1) {
        m_lr = lr1;
    }

    public int setReader(Object lr1) {
        if (lr1.getClass().isInstance(AGS_ReaderLine.class)) {
            m_lr = (AGS_ReaderLine) lr1;
            return 1;
        }
        return 0;

    }
   public int getAGSReader() {
    try {
        
        String s1 =  getProperty(AGS_DATASOURCE);
        AGS_Dictionary.AGSVersion ags1 = m_dic.getAGSVersion();

        switch (ags1) {
            case AGS30: case AGS31: case AGS31a:
            m_lr = new AGS_ReaderLine31 (s1);
            case AGS403: case AGS404:
            m_lr = new AGS_ReaderLine40 (s1);
        }
        return ags1.toInt();
    }
    catch (Exception e) {
        return -1;
    }
      
}
    public String CreateTable(String strTableCreateCommand) {
        String s1 = "";
        return s1;

    }

    public void openFile(String fileName) {
        
    }

    public void closeFile() {
    }

    private fField getField(String fName, String tName) {
        return null;
    }
public void setAGSDictionaryNode(Node n1) {
}
    public String getSQLCreateTable(String tname) {

//   Node n1 = m_dic.find_table(tname,null);
//   if (n1 != null) {
//      String s1 = "create table " + tname + " (";  
//        NodeList nl1 = n1.getChildNodes();
//            for(int i=1; i<nl1.getLength(); i++){
//                Node n2 = nl1.item(i);
//                String s2 = getSQLCreateField(n2);
//                if (i==1) {
//                s1 = s1 + s2;
//                } else {
//                    s1 = s1 + "," + s2;
//                }
//            
//            }
//       s1= s1 + ")";
//    return s1;  
//   }
        return null;
    }

    public String getSQLCreateField(Node n1) {
        String s1 = "";
        if (n1 != null) {
            //    String s1 = m_dic.find_fieldValue("name");    
            NodeList nl1 = n1.getChildNodes();
            for (int i = 1; i < nl1.getLength(); i++) {

            }

            return s1;
        }
        return null;
    }

    public void Process() {
        Node n1 = m_ds.RootNode();
        Process(n1);
    }
         
   
        public void Process(Node n1) {

        try {
            Collection<String> data = null;
            Collection<String> ags_header = null;
            Header sql_header = null;

            boolean tableOkay = false;
            AGS_ReaderLine lr = m_lr.Copy();

            AGS_ReaderLine.LineType result;

            do {
                result = lr.ReadLine();

                if (result == AGS_ReaderLine.LineType.tableHeader) {
                    String table_name = lr.get_tablename();
//                ags_header = addDictionaryTypes(lr.get_headers());
//                    sql_header = convertHeaderSQL(ags_header, table_name,AGS_Dictionary.Lang.AGS);
                    tableOkay = ensureTable(table_name, sql_header, true);
                    if (tableOkay) {
//                        String insert1 =  getTableInsert(table_name, sql_header);
                    }
                }

                if (result == AGS_ReaderLine.LineType.rowData && tableOkay) {
                    data = lr.get_data();
//                List ldata =  (List)data;
//                String insert2 = getTableInsertData();

                }

            } while (result == AGS_ReaderLine.LineType.rowData || result == AGS_ReaderLine.LineType.tableHeader || result == AGS_ReaderLine.LineType.tableUnits);
        } catch (Exception e) {
            m_log.log(Level.SEVERE, e.getMessage());
        }
    }
}
