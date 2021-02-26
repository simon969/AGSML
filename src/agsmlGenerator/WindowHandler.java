/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agsmlGenerator;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
/**
 *
 * @author thomsonsj
 */
class WindowHandler extends Handler {
  private GeneratorGUI window = null;

  private Formatter formatter = null;

  private Level level = null;

  private static WindowHandler handler = null;

  private WindowHandler() {
 //   LogManager manager = LogManager.getLogManager();
 //   String className = this.getClass().getName();
 //   String level = manager.getProperty(className + ".level");
 //   setLevel(level != null ? Level.parse(level) : Level.INFO);
     // if (window == null)
   //   window = new LogWindow();
   }
 public void setGeneratorGUI(GeneratorGUI gg) {
     window = gg;
 }
  public static synchronized WindowHandler getInstance() {
    if (handler == null) {
      handler = new WindowHandler();
    }
    return handler;
  }

  public synchronized void publish(LogRecord record) {
    String message = null;
    if (!isLoggable(record))
      return;
    message = getFormatter().format(record);
    window.showInfo(message);
  }

  public void close() {
  }

  public void flush() {
  }
}

