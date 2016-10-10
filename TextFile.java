package agsml;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
/**
 *
 * @author Simon
 */
public class TextFile{
    private FileReader m_fr;
    private BufferedReader m_br;
    private StringBuilder m_sb;
    private String m_NL;

    public TextFile (String filename) {
        try {
          m_fr = new FileReader(filename);
          m_br = new BufferedReader(m_fr);
          m_sb = new StringBuilder();
          m_NL = System.getProperty("line.separator");
        }
        catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();

    }
    }
    public BufferedReader BufferedReader() {
        return m_br;
    }
    public String to_string() {
        String record = new String();
        int recCount = 0;
        try {
        while ((record = m_br.readLine()) != null) {
              recCount++;
              m_sb.append(record);
              m_sb.append(m_NL);
           }
           String str = m_sb.toString();
           return str;
        }
        catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
        return null;
        }

    }

    public int printOut_All() {

        String record = null;
        int recCount = 0;
        
        try {
          record = new String();
           while ((record = m_br.readLine()) != null) {
              recCount++;
              System.out.println(recCount + ": " + record);
           }

        } catch (IOException e) {
           // catch possible io errors from readLine()
           System.out.println("Uh oh, got an IOException error!");
           e.printStackTrace();
           return 0;
        }
        return 1;
    }

    public String readLine() {

        try {
          String record = new String();
          record = m_br.readLine();
          return record;
         } catch (IOException e) {
           // catch possible io errors from readLine()
           System.out.println("Uh oh, got an IOException error!");
           e.printStackTrace();
           return null;
        }
    }
}
