/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agsml;
// import sun.jdbc.odbc.JdbcOdbc;
import java.io.BufferedWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection ;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.lang.StringBuilder;
import java.lang.Math;

/**
 *
 * @author thomsonsj
 */
class SQL_KML_Writer extends  SQL_XML_Writer {

    protected SQL_Base m_db;
    
   
    protected final String SQLXML_KMLEASTING_TAG = "kmlEasting";
    protected final String SQLXML_KMLNORTHING_TAG = "kmlNorthing";
    protected final String SQLXML_KMLLATITUDE_TAG = "kmlLatitude";
    protected final String SQLXML_KMLLONGITUDE_TAG = "kmlLongitude";
    protected final String SQLXML_KMLDIAMETER_TAG = "kmlDiameter";
    protected final String SQLXML_KMLRADIUS_TAG = "kmlRadius";  
    protected final String SQLXML_KMLNAME_TAG = "kmlName";
    protected final String SQLXML_KMLNAMESPACE = "http://earth.google.com/kml/2.2";
    protected final String SQLXML_KMLEXTENDEDDATA ="kmlExtendedData";
    protected final String SQLXML_KMLDESCRIPTION_TAG ="kmlDescription";
    protected final String SQLXML_KMLGEOMETRYTYPE_TAG = "kmlGeometryType";
    
    protected enum SQLXML_KMLInputCoordType {
        kmlNone,
        kmlOSNationalGrid,
        kmlLongitudeLatitude;
    }

    protected enum SQLXML_KMLGeometryType {
        kmlNone,
        kmlPoint,
        kmlLineString,
        kmlPolygon,
        kmlCircle
    }
    
    public SQL_KML_Writer() {
    super();
    }
    
    public SQL_KML_Writer(String fout, String stylesheet) {
    super(fout, stylesheet);    
    }
    
    public SQL_KML_Writer(String fout) {
    super(fout);
    }

    public SQL_KML_Writer (BufferedWriter bw ) {
    super (bw);
    try {
  
    }
    catch (Exception e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an Exception error!");
        e.printStackTrace();
    }

}
 public void setAGSDictionaryFile(String xmlDictFile, String s1) {
 
     super.setAGSDictionary(xmlDictFile, s1);  
     super.getAGSDictionary();
 
 }
 public void setAGSSQLReader(SQL_Base sr ) {
     m_db = sr; 
 }
 
 public void Process() {
      Node n1 =  m_ds.RootNode();
      Process ( n1);
 }

 public void Process(Node n1) {
 try {
     
     if (folderOut().isEmpty()) {
     
         Process(n1,null);
     
     } else {
     
     String sql_tname = m_db.AGSSQL_PointTableName;
     String sql_fname = m_db.AGSSQL_PointIdFieldName;
     
     String PointId;
     String holename;
     
     ResultSet rs = m_db.get_table(sql_tname);
     
     rs.first();
     
     do {
     
     PointId = getData(rs,  sql_fname);    
     
     holename = removeSpecialCharacters(PointId); 
     
     if (IsHoleInList(PointId) && ContainsSpecialCharacters(holename) == false) {
         
         m_onlyHoleId = PointId;
            
            setOutputFile(folderOut() + "\\" + holename + ".xml");
            
            openFile(true);
            
            Process (n1, null);
            
            closeFile();
      
         m_onlyHoleId = "";
      
     }
     } while (rs.next());
     
     rs.close();
     
     }   
  }
 
 catch (SQLException e) {
     m_log.log(Level.SEVERE, e.getMessage());
 }   
}
protected Node Process(Node n1, String hole_id){
 try {
    String s1 = null;
    String ntype = null;

    boolean IsAGSMLTable;
 
    do {
           if (n1.getNodeType() == Node.ELEMENT_NODE) {
               
                s1 = n1.getNodeName();
                
                ntype = getAttributeValue( n1, "type");
                
                IsAGSMLTable = IsAGSTable(s1);
                
                m_log.log(Level.INFO ,"["+ hole_id + "][" + n1.getNodeName() +"]") ;
                
                if (IsAGSMLTable == false) {
                    openNode (s1);
                }
                 if (s1.equals("Proj")) {
                    addTable (s1, null, null);
                }
                 
                if (s1.equals("holes") && hole_id == null) {
                    addHoles(n1);
                    
                }
                
                if (ntype.equals("DataQueryAGSML")) {
                    processQueryNode(n1);
                }                                    
                
                if (s1.equals("DataQueryKML")) {
                    processKMLNode(n1);
                }  
                if (IsAGSMLTable == true && hole_id != null) {
                    addTable (s1, "HoleId", hole_id);
                }
                
                if (n1 != null) {
                        if (n1.hasChildNodes() == true) {
                            NodeList list = n1.getChildNodes();
                            Node n2 = list.item(0); 
                            Process(n2, hole_id);
                }} 
                
                if (IsAGSMLTable == false) {
                    closeNode();
                }

        }
           
   
      
      n1 = n1.getNextSibling();
      
      
     } while (n1 != null && n1.getNodeName() != "DataStructureAGSML");
   
    return n1;
   
 } catch (Exception e) {
     m_log.log(Level.SEVERE, e.getMessage());
     return n1;
 }
}
 
 
 
 protected int addTable(String xml_tname, String xml_fname, String fvalue){
     
 try {
  
    int row_count = 0;
   
    String sql_tname = "";
    String sql_fname = "";
  
       
    if (xml_tname.length() > 0) {
        if (m_dic.find_tableXML(xml_tname)!=null) {
            sql_tname = m_dic.tableGINT_name();
                if (sql_tname == null) {
                    return 0;
                } 
        }
    } else return 0;
         
      
    if (xml_fname != null) {
        if (xml_fname.length() > 0) {
            if (m_dic.find_fieldXML(xml_fname)!= null)  {
            sql_fname = m_dic.fieldGINT_name();
            }
        }
    }
    
    if (m_db.find_tablename(sql_tname) >= 1) {
    
        ResultSet rs = m_db.get_table(sql_tname, sql_fname, fvalue );
        Header r =  convertSQLHeader(rs, sql_tname, AGS_Dictionary.Lang.GINT); 
        
        if (rs !=null) {
            if (rs.first()) {
                // openNode (xml_tname);
                    do {
                        openNode (xml_tname);
                            addRow(r, rs, true);
                        closeNode();
                        row_count++;
                    } while (rs.next());
                // closeNode();
            } 
            
         rs.close();
        }
        
    }
    
    if (row_count > 0) {
    m_log.log(Level.INFO,"["+ fvalue + "]["+ xml_tname+ "][" + String.valueOf(row_count) +"]") ;
    }
    
   
            
    return row_count;
    
 }
 
 catch (SQLException e) {
    m_log.log(Level.SEVERE, e.getMessage());
     return -1;
 }
 }
 protected String getData(ResultSet rs, String field) {
 try {
      String attName;
      String attValue;
      if (rs != null) {
      ResultSetMetaData rsmd = rs.getMetaData();
      
      for (int i = 1; i < rsmd.getColumnCount(); i++) {
                         attName = rsmd.getColumnName(i);
                         attValue = rs.getString(i);
                         if (field.equalsIgnoreCase(attName)) {
                             return attValue;
                             }
      }
      }
      return null;
    }
 catch (SQLException e) {
     return null;
 }
 }
 protected int addRow(Collection header, ResultSet rs, boolean AddEmptyData) {
      
    try {
           Iterator h_it;
           String h1;
           String d1;
           int colid=0;
           h_it = header.iterator();
  
           do {
               colid++; 
               h1 = h_it.next().toString();
               d1 = rs.getString(colid);
               if (d1 == null) d1= "";
                if (!(d1.length() == 0 && AddEmptyData == false)){
                    openNode(h1);
                        addValid (d1);
                    closeNode();
                    if (h1.equals(OutputFileNameFromField()) && SQLXML_ADDHOLEFILENAME == true) {
                         String holefileName = removeSpecialCharacters(d1) + ".xml";
                         openNode (SQLXML_HOLEFILETAGNAME);
                         addValid (holefileName);
                         closeNode();
                    }
                }
            } while(h_it.hasNext());
          
    return 1;        
    }
    catch (SQLException e) {
    return -1;
    }
 }
 
 
 protected int addHole(Node n1, String hole_id) {
     
     return -1;
  }
protected int addHoles(Node n1) {

   try   {
         
    int result;
    int count = 0;
    boolean holeInList;
    Collection sql_header = null;
    Collection xml_header = null;

    String hole_id = null;
    Node n2 = null;

  
    result = m_db.find_tablename(m_db.AGSSQL_PointTableName);

    if (result <= 0) {
    return -1;
    }

    ResultSet  rs = m_db.get_table(m_db.AGSSQL_PointTableName);
    Header r = convertSQLHeader (rs,m_db.AGSSQL_PointTableName, AGS_Dictionary.Lang.GINT );
    
    if (n1.hasChildNodes() == true)  { 
        NodeList list = n1.getChildNodes();
        n2 = list.item(1);
    } else {
        n2 = n1;
    }

    if (rs.first()) {
       do {
            hole_id = getData(rs, m_db.AGSSQL_PointIdFieldName);  
            holeInList = IsHoleInList(hole_id);
                if (holeInList) {
                    openNode ("Hole");
                    addRow (r, rs, false);
                    count++;
                        if (n2 != null) {
                           Process (n2, hole_id);
                        }
                    closeNode();
                m_log.info("Found " + hole_id + " in list ");
              }
           } while (rs.next());
    }
    
    rs.close();
    
return count;  
}

catch (SQLException e) {
   m_log.log(Level.SEVERE, e.getMessage()); 
   return -1;    
}
}

  protected String getKMLDescriptionField(Node n1) {
    return getSubNodeValue(n1,SQLXML_KMLDESCRIPTION_TAG);
} 
  protected String getKMLDiameterField(Node n1) {
    return getSubNodeValue(n1,SQLXML_KMLDIAMETER_TAG);
}
  protected String getKMLRadiusField(Node n1) {
    return getSubNodeValue(n1,SQLXML_KMLRADIUS_TAG);
}
  protected String getKMLNameField(Node n1) {
    return getSubNodeValue(n1,SQLXML_KMLNAME_TAG);
}
  protected String getKMLLongitudeField(Node n1) {
    return getSubNodeValue(n1,SQLXML_KMLLONGITUDE_TAG);
}   
protected String getKMLLatitudeField(Node n1) {
    return getSubNodeValue(n1,SQLXML_KMLLATITUDE_TAG);
}     
  protected String getKMLEastingField(Node n1) {
    return getSubNodeValue(n1,SQLXML_KMLEASTING_TAG);
}   
protected String getKMLNorthingField(Node n1) {
    return getSubNodeValue(n1,SQLXML_KMLNORTHING_TAG);
} 
protected SQLXML_KMLGeometryType getKMLGeometryType(Node n1) {
    String s1 = getSubNodeValue(n1,SQLXML_KMLGEOMETRYTYPE_TAG);
    
    if (s1.length()==0 || s1.equalsIgnoreCase("Point")) {
        return SQLXML_KMLGeometryType.kmlPoint;
    }
    if (s1.equalsIgnoreCase("LineString")) {
        return SQLXML_KMLGeometryType.kmlPoint;
    }
    if (s1.equalsIgnoreCase("Circle")) {
        return SQLXML_KMLGeometryType.kmlCircle;
    }
    return SQLXML_KMLGeometryType.kmlPoint;
}  

protected String getDictionaryTable(Node n1) {
    return getSubNodeValue(n1, SQLXML_DICTIONARY_TABLE);
}
protected String getOutputTableName(Node n1) {
    return getSubNodeValue(n1,SQLXML_TABLE_TAG);
}
protected String getOutputRowName(Node n1) {
    String s1 = getSubNodeValue(n1,SQLXML_ROW_TAG);
    if (s1.isEmpty()) {
               s1 = getAttributeValue(n1, SQLXML_ROW_TAG);
    }
    if (s1.isEmpty()) {
        s1 = "row";
    }
    return s1;
}
protected String getOutputTableID(Node n1) {
    String s1 = getAttributeValue(n1,SQLXML_TABLE_ID);
    if (s1.isEmpty()) {
        s1 = getSubNodeValue(n1,SQLXML_TABLE_ID); 
        } 
    if (s1.isEmpty()) {
        s1 = getAttributeValue(n1,"id"); 
        } 
    return s1;
}
protected String getSelect(Node n1) {
     return getSubNodeValue(n1,SQLXML_SELECT);
}
protected String getFrom(Node n1) {
     return getSubNodeValue(n1,SQLXML_FROM);
}
protected String getWhere(Node n1) {
     return getSubNodeValue(n1,SQLXML_WHERE);
}
protected String getExplicitQuery(Node n1) {
     return getSubNodeValue(n1,SQLXML_QUERY);
}
protected String getQuery (Node n1) {
try {

    StringBuilder  sb1 = new StringBuilder();

    String select = getSelect (n1);
    String from = getFrom(n1);
    String where = getWhere(n1);
    String query = getExplicitQuery(n1);
    String query1 =  n1.getTextContent();
    
if (select.isEmpty()!=true) {
    sb1.append(SQLXML_SELECT);
        sb1.append(SQLXML_SINGLESPACE);
            sb1.append(select);
                sb1.append(SQLXML_SINGLESPACE);
    sb1.append(SQLXML_FROM);
    sb1.append(SQLXML_SINGLESPACE);
        sb1.append(from);
        
    if (!where.isEmpty()) {       
            sb1.append(SQLXML_SINGLESPACE);
    sb1.append(SQLXML_WHERE);
        sb1.append(SQLXML_SINGLESPACE);
            sb1.append(where);
                sb1.append(SQLXML_END); 
    } else {
        sb1.append(SQLXML_END); 
    }
return sb1.toString();
}

if (query.isEmpty()) {
    if (sb1.length()==0) {
        return query1;
    }
    else { 
        return sb1.toString();
    }
} else {
    return query;  
  }
} 
catch (Exception e) {
    m_log.log(Level.SEVERE,e.getMessage());
  return null;  
}    
}
private String getCoordinatePair(ResultSet rs, String fLatitude, String fLongitude){
   try {
       return rs.getString(fLatitude) + "," +  rs.getString(fLongitude);
                
} catch (Exception e) {
        return "";
}
}
private String getCoordinateConvertPair(ResultSet rs, String fEasting, String fNorthing){
   try {
       double _easting = rs.getDouble(fEasting);
       double _northing = rs.getDouble(fNorthing);
       return getCoordinateConvertPair(_easting, _northing);
                
} catch (Exception e) {
        return "";
}
}
private String getCircleConvertCoordinates(ResultSet rs, String fEasting, String fNorthing, String fRadius, double ScaleFactor) {
 try {  
     
     double _originX = rs.getDouble(fEasting);
     double _originY = rs.getDouble(fNorthing);
     double _radius = rs.getDouble(fRadius) * ScaleFactor;
     
     return getCircleConvertCoordinates (_originX,_originY, _radius);
     
 } catch (Exception e) {
     
  return "";
 } 
}

private String getCircleCoordinates(ResultSet rs, String fLatitude, String fLongitude, String fRadius, double ScaleFactor) {
 try {  
     
        OSNationalGridConversion OSNatGrid = new OSNationalGridConversion();
         
        OSNatGrid.Latitude=  rs.getDouble(fLatitude);
        OSNatGrid.Longitude = rs.getDouble(fLongitude);
        OSNatGrid.dbLatLongToEastingNorthingPair(); 
     
        double _originX = OSNatGrid.Easting;
        double _originY = OSNatGrid.Northing;
        double _radius = rs.getDouble(fRadius) * ScaleFactor;
     
        return getCircleConvertCoordinates ( _originX,_originY, _radius);
     
 } catch (Exception e) {
     
        return "";
 }    
     
}
private String getCircleConvertCoordinates( Double originX, Double originY, Double Radius) {
 try  
 {
        return "";
 }
 catch (Exception e) {
     return "";
 }
    
}
private String getCoordinateConvertPair(Double Easting, Double Northing){
   try {
       
        OSNationalGridConversion OSNatGrid = new OSNationalGridConversion();
         
        OSNatGrid.Easting =  Easting;
        OSNatGrid.Northing = Northing;
        
        return OSNatGrid.dbOSGridToLongitudeLatitudePair(); 
        
} catch (Exception e) {
        return "";
}
}

private String getCoordinatesConvert(ResultSet rs, String fEasting, String fNorthing) {
 try {
      
    String coordinate;
    String _space = " ";
    StringBuilder sb1 = new StringBuilder();
    
    if (rs.first()) {
        do {
         coordinate = getCoordinateConvertPair(rs, fEasting, fNorthing) + ",0.0";
         sb1.append(_space);
         sb1.append(coordinate);
        } while (rs.next());                       
    }
    
    return sb1.toString();
    
  } catch (Exception e) {
      return "";
  }   
}
public String getCoordinates(ResultSet rs, String fLatitude, String fLongitude){
  try {
      
    String coordinate;
    String _space = "    ";
    StringBuilder sb1 = new StringBuilder();
    
    if (rs.first()) {
        do {
         coordinate = rs.getString(fLongitude) + "," +  rs.getString(fLatitude);
         sb1.append(_space);
         sb1.append(coordinate);
        } while (rs.next());                       
    }
    
    return sb1.toString();
    
  } catch (Exception e) {
      return "";
  }
}

public int processKMLNode( Node n1) {
    
//    super.processKMLNode(n1);
            
    try {
        
        int row_count = 0;
        
        String query = getQuery (n1) ;
        
        String fLatitude = getKMLLatitudeField(n1);
        String fLongitude = getKMLLongitudeField(n1);
        String fEasting = getKMLEastingField(n1);
        String fNorthing = getKMLNorthingField(n1);        
        String fRadius = getKMLRadiusField(n1);
        String fDiameter = getKMLDiameterField(n1);
        double ScaleFactor = 1.0;
        
        if (fRadius.isEmpty() == true && fDiameter.isEmpty() != true) {
        fRadius = fDiameter;
        ScaleFactor = 0.5;
        }
        
        SQLXML_KMLInputCoordType coordType;
        
        if (fEasting.isEmpty() && fNorthing.isEmpty()) {
         coordType= SQLXML_KMLInputCoordType.kmlLongitudeLatitude;
        } else {
        coordType= SQLXML_KMLInputCoordType.kmlOSNationalGrid;
        }
        
        String fName = getKMLNameField(n1);
        String fDescription = getKMLDescriptionField(n1);
        SQLXML_KMLGeometryType geometryType = getKMLGeometryType(n1);
        
        String coordinate = "";
        String name = "";
        String description = "";
               
        Node nodeExtendedData = getKMLExtendedData(n1);
        
        ResultSet rs = m_db.get_query(query);
        
        if (rs !=null) {
            addNodeAttrib("xmlns",this.SQLXML_KMLNAMESPACE);
            openNode ("kml");
                openNode ("Document");
                    openNode ("name");
                        addValid(fileOut());
                    closeNode();
                    openNode("Folder");
                        if (rs.first()) {
                            do {
                                
                                name = rs.getString(fName);
                               
                                if (fDescription.length()>0) {
                                description = rs.getString(fDescription);
                                } 
                                
                                openNode ("Placemark");
                                    openNode ("name");
                                        addValid(name);
                                    closeNode();
                                        openNode ("description");
                                            addValid(description);
                                        closeNode();
                                   
                                    addKMLExtendedData (rs, nodeExtendedData);
                                   
                                    
                                    if (geometryType == SQLXML_KMLGeometryType.kmlPoint) {
                                            
                                            if (coordType == SQLXML_KMLInputCoordType.kmlLongitudeLatitude) {
                                            coordinate = getCoordinatePair(rs, fLatitude, fLongitude );
                                            }
                                            
                                            if (coordType == SQLXML_KMLInputCoordType.kmlOSNationalGrid) {
                                            coordinate = getCoordinateConvertPair(rs, fEasting,fNorthing);
                                            }
                                                openNode ("Point");
                                                    openNode("coordinates");
                                                        add(coordinate);
                                                    closeNode();
                                                closeNode(); 
                                             row_count++;
                                    }
                                    
                                    if (geometryType == SQLXML_KMLGeometryType.kmlCircle) {
                                            
                                            if (coordType == SQLXML_KMLInputCoordType.kmlLongitudeLatitude) {
                                            coordinate = getCircleCoordinates(rs, fLatitude,fLongitude,  fRadius, ScaleFactor);
                                            }
                                            
                                            if (coordType == SQLXML_KMLInputCoordType.kmlOSNationalGrid) {
                                            coordinate = getCircleConvertCoordinates(rs, fEasting, fNorthing, fRadius, ScaleFactor);
                                            }
                                            openNode("Polygon");
                                                openNode("outerBoundaryIs");
                                                    openNode ("LinearRing");
                                                        openNode("coordinates");
                                                            add(coordinate);
                                                        closeNode();
                                                    closeNode();
                                                closeNode();
                                            closeNode();
                                            row_count++;
                                    }
                                    
                                    
                                    
                                    
                                    
                                    if (geometryType == SQLXML_KMLGeometryType.kmlLineString) {
                                            if (coordType == SQLXML_KMLInputCoordType.kmlLongitudeLatitude) {
                                            coordinate = getCoordinates(rs, fLongitude, fLatitude);
                                            }
                                            if (coordType == SQLXML_KMLInputCoordType.kmlOSNationalGrid) {
                                            coordinate = getCoordinatesConvert(rs, fNorthing, fEasting);
                                            }
                                            rs.afterLast();
                                                openNode ("LineString");
                                                    openNode("coordinates");
                                                        add(coordinate);
                                                    closeNode();
                                                closeNode(); 
                                             row_count++;
                                    }
                                closeNode();
                                } while (rs.next());
                        }
                    closeNode();
                closeNode();
            closeNode();
        rs.close();
        }
        
        m_log.log(Level.INFO,"KMLNode Processed " + row_count + "rows added");
        return row_count;
         
     } catch (Exception e) {
        m_log.log(Level.SEVERE,e.getMessage());
      return -1;
    }
    
    
}
private void addKMLExtendedData(ResultSet rs, Node n1) {
 
try {
    
    if (n1 == null) {
        return; 
    }
    
    String fName;
    String nName;
    String fValue;
    
    String hole;
    String holeFilename;
    
    NodeList nodeList = n1.getChildNodes();
    if (nodeList != null) {
    
        openNode("ExtendedData");    
  
                for (int i = 0; i < nodeList.getLength(); i++) {
                     Node currentNode = nodeList.item(i);
                        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                            if (currentNode.getNodeName().equals("Field")) {
                                fName= currentNode.getTextContent();
                                fValue = rs.getString(fName);
                                addNodeAttrib("name",fName);
                                openNode ("Data");
                                    openNode("value");
                                        add (fValue);
                                    closeNode();
                                closeNode();
                            }
                        }
                }
                if (folderOut().length()>0) {
                    hole = rs.getString(OutputFileNameFromField()) ; 
                    holeFilename = folderOut() + "/" + removeSpecialCharacters(hole) + ".xml";
                    fValue = "<a href='" + holeFilename + "'>" + hole + " Borehole Data</a>";
                    addNodeAttrib("name","BoreholeLog");
                    openNode ("Data");
                        openNode("value");
                            addValid(holeFilename);
                        closeNode();
                    closeNode();     
                }
        
        closeNode();
    }   
 }   catch (Exception e) {
      m_log.log(Level.SEVERE,e.getMessage());
      return ;
 }
}
private Node getKMLExtendedData(Node n1) {
    return getSubNode(n1,SQLXML_KMLEXTENDEDDATA);
}
public int processQueryNode(Node n1) {
    try {
        
        Collection sql_header = null;
        Collection xml_header = null;
        int row_count = 0;
        
        String query = getQuery (n1) ;
        String dict_table = getDictionaryTable(n1);
        String row_name = getOutputRowName(n1);
        
        ResultSet rs = m_db.get_query(query);
        
        if (rs !=null) {
       
        Header h = convertXMLHeader(rs, dict_table, AGS_Dictionary.Lang.GINT);
              
            if (rs.first()) {
                   do {
                        openNode (row_name);
                            addRow(h, rs, true);
                        closeNode();
                        row_count++;
                    } while (rs.next());
               } 
            
         rs.close();
        }
    
        return row_count;

    } catch (Exception e) {
        m_log.log(Level.SEVERE,e.getMessage());
      return -1;
    }
}
}
