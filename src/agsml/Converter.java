package agsml;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author thomsonsj
 */
interface Converter {
    public void Process();
    public void setProperty (String name, String value);
    public String getProperty (String name);
    public void setAGSDataSource(String datasource);
    public void setOutputFolder(String outfolder);
    public int getAGSDataStructure();
    public int getAGSDictionary();
    public void setOutputFile(String outfile);
    public int getAGSReader();
   
}
