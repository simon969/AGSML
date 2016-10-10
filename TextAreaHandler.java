package agsml;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;
/**
 *
 * @author thomsonsj
 */
public class TextAreaHandler extends StreamHandler {
    JTextArea textArea = null;

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        flush();

        if (textArea != null) {
            textArea.append(getFormatter().format(record));
        }
    }
}
  
