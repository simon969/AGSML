package agsml;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  - Neither the name of Sun Microsystems nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * This class assumes that the string used to initialize fullPath has a
 * directory path, filename, and extension. The methods won't work if it
 * doesn't.
 */
public class AGS_Data {
  private File f;
  private URL u;
  private URL login;
  private String source;
  private String resource_folder;
  private String output_folder;
  private final String _extensionSeparator = ".";
  
  private final String PATH_SEPARATOR_LINUX = "/";
  private final String PATH_SEPARATOR_WINDOWS = "\\";
  
  private String _pathSeparator = PATH_SEPARATOR_WINDOWS;
  private final String ASPNET_returnpage = "ReturnPage";
  public enum OpenType {
      AppendTodaysDate,
      OverriteExisting,
      UniqueNumber,
  }
   public enum DataType {
     NotKnown,
     AGS,
     XML,
     KML,
     AccessDb,
     ServerDb,
  }
  public AGS_Data(String str) {
      try {
        
        source = str;
        setPathSeparator(source);
        
        f = new File (source);
        u = new URL(source);
      } catch (Exception e) {
          
      }
    
  }
   
  public File File() {
      return f;
  }
  public AGS_Data(String str, char sep, char ext) {
       this (str + sep + ext);
  }
  public boolean verifyFile() {
     try {
       if (!f.isDirectory())
         f = f.getAbsoluteFile();
         return true;
       } catch (Exception e) {
        return false;
      } 
  }
  public boolean verifyURL() {
      try {
        u = new URL (source);
        return true;
       
      } catch (Exception e) {
        return false;
      } 
  } 
  public void setLoginPage(String s1){
      try {
      login = new URL(s1);
      } catch (Exception e) {
        
      }
  }
  public String getLoginWithReturnPage(){
   return login.getPath() + ASPNET_returnpage + u.getPath();
  }
    public String Source() {
      return source;
  }
    public void setResourceFolder(String s1) {
        resource_folder = s1;
        setPathSeparator(resource_folder);
    }
    
    private void setPathSeparator(String s1) {
        
        if (s1.contains(PATH_SEPARATOR_LINUX)) {
            _pathSeparator = PATH_SEPARATOR_LINUX;
            return;
        }
        
        _pathSeparator = PATH_SEPARATOR_WINDOWS;
    }
    
    public String getResourceFolder() {
        if (resource_folder==null){
            resource_folder = Path();
        }
        return resource_folder;
    }
    public void setOutputFolder(String s1) {
        output_folder = s1;
        setPathSeparator(output_folder);
    }
  public String FileExtension() {
      if (source==null)
          return null;
    f = new File(source);
    int from = f.getAbsolutePath().lastIndexOf(_extensionSeparator) + 1;
    int to = f.getAbsolutePath().length();
    return f.getAbsolutePath().substring(from, to);
  }
  public String FileName(){
     int from = f.getAbsolutePath().lastIndexOf(_pathSeparator) + 1; 
     int to = f.getAbsolutePath().length();
     return f.getAbsolutePath().substring(from, to); 
  }
  public String FileNameNoPath(){
     return f.getName();
  }
  public String FileNameNoExtension() { // gets filename without extension
    int dot = f.getAbsolutePath().lastIndexOf(_extensionSeparator);
    int sep = f.getAbsolutePath().lastIndexOf(_pathSeparator) + 1;
    return f.getAbsolutePath().substring(sep, dot);
  }

  public String Path() {
    int sep = f.getAbsolutePath().lastIndexOf(_pathSeparator);
    return f.getAbsolutePath().substring(0, sep);
  }
  public String getFullOutputPath(){
       if (!output_folder.isEmpty()) {
            if (output_folder.endsWith(_pathSeparator)) {
               File f1 = new File (output_folder + source);
               return f1.getAbsolutePath();
            }
               File f1 = new File (output_folder + _pathSeparator + source);
               return f1.getAbsolutePath();
        }
      return Constants.EMPTY;
  }
  
  public String getFullResourcePath(){
      if (f.exists()){
          return f.getAbsolutePath();
      }
      if (resource_folder!=null) {
            if (resource_folder.endsWith(_pathSeparator)) {
               File f1 = new File (resource_folder + source);
               return f1.getAbsolutePath(); 
            }  
               File f1 = new File (resource_folder + _pathSeparator + source);
               return f1.getAbsolutePath();
      }
      return Constants.EMPTY;
  }
  public int FileExist(){
        try {
          if(f.canRead()) { 
             return 1;
         } else {
             return -1;
         }}
        
        catch (Exception e) {
         return -1;
        }
    }
    public int FolderExist() {
        try {
         if(f.isDirectory()) 
             return 1;
         else
             return -1;
         }
        catch (Exception e) {
         return -1;
        }
    }
    public boolean changeExtension(String newExtension) {
       try {
     
           String body = FileNameNoExtension();
                
            File f2 = new File (body + newExtension);
                
            f = f2.getAbsoluteFile();
            
            return true; 
        
        } catch (Exception e) { 
            System.err.println(e.getMessage()); 
          return false;   
        }
      }   
       
    public boolean addUniqueNumber() {
        
        try {
        
            int counter = 1;       
            String ext = FileExtension();
            String path = Path();
            String body = FileNameNoExtension();
                
            File f2 = new File (path + _pathSeparator + body + _extensionSeparator + ext);

            while (f2.exists()) {
                 f2 = new File (path + _pathSeparator + body + String.format("%d",counter) +  _extensionSeparator + ext);
                 counter += 1; 
            }
            
            f = f2.getAbsoluteFile();
            
            return true; 
        
        } catch (Exception e) { 
            System.err.println(e.getMessage()); 
          return false;   
        }
      } 
    
    public boolean addDateStamp(){
        return addDateStamp("");
    }
    public boolean addDateStamp(String pattern) {
        
        try {
        
            String ext = FileExtension();
            String body = FileNameNoExtension();
            String folder = f.getParent();
            
            if (pattern.isEmpty()) {
                pattern = "yyMMdd-hhmmss-SSS";
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            body += sdf.format(new Date());   
            String new_filename = folder + _pathSeparator + body + _extensionSeparator + ext;
            
            File f2 = new File (new_filename);

            f = f2.getAbsoluteFile();
            
            return true; 
        
        } catch (Exception e) { 
            System.err.println(e.getMessage()); 
          return false;   
        }
      }
    
    DataType getDataType () {
       
         if (f.isFile()) {
             
             String ext = FileExtension();
             
                if (ext.equals("mdb") || ext.equals("gpj") || ext.equals("accdb")) {
                    return DataType.AccessDb;
                }
                if (ext.equals("xml")) {
                    return DataType.XML;
                }
                if (ext.equals("ags")) {
                    return DataType.AGS;
                }
                if (ext.equals("kml")) {
                    return DataType.XML;
                }
         } else {
                if (source.contains(";")) {
                    return DataType.ServerDb;
                }
                if (source.contains (".ags")) {
                    return DataType.AGS;
                }
                if (source.contains(".xml")) {
                    return DataType.XML;
                }
                if (source.contains(".kml")) {
                    return DataType.KML;
                }
        }
         
         return DataType.NotKnown;
   }
public boolean IsHTTPPath() {
    try {
        u = new URL(source);
        return true;
    } catch (MalformedURLException e) {
        return false;
    }
}
public boolean IsXMLFile() {
    try {
        return (getDataType() == AGS_Data.DataType.XML);
    } catch (Exception e) {
        return false;
    }
}
 public boolean IsAGSFile() {
    try {
        return (getDataType() == AGS_Data.DataType.AGS);
    } catch (Exception e) {
        return false;
    }   
} 
}
