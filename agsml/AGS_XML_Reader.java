/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agsml;
import java.io.FileReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

class AGS_XML_Reader {

    private XMLReader xr;
    private AGS_XML_EventHandler handler;
    
            
    public AGS_XML_Reader ()
    {
	try {
        xr = XMLReaderFactory.createXMLReader();
	handler = new  AGS_XML_EventHandler();
	xr.setContentHandler(handler);
	xr.setErrorHandler(handler);
        } catch (SAXException e) {

        }
    }            
            
            
    public void main (String args[])
	throws Exception
    {
			// Parse each file provided on the
				// command line.
	for (int i = 0; i < args.length; i++) {
	    FileReader r = new FileReader(args[i]);
	    xr.parse(new InputSource(r));
	}
    }
}