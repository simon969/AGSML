/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agsml;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author thomsonsj
 */
public class AGS_Database extends Base_Class {
        
        String db_connect = "";

        String db_paramRowGuid = "@rowguid";
        String db_paramProjectid ="@Projectid";

        String ags_data = "";
        
        String xml_data = "";

        String db_uniqueguidAGS;
        String db_paramNameAGS = "@ags_data";
        String  db_updateAGS = "update ge_data set data_ags=@agsdata where rowguid=@rowguid;";
        String db_insertAGS = "@newguid=newid(); insert into ge_data (rowguid, projectid, data_ags) values(@newguid, @projectid), @ags_data); select id, projectid, rowguid from ge_data where rowguid=@newguid;";
        String db_selectAGS = "select id, projectid, data_ags, rowguid from ge_data where rowguid=@rowguid;";
        String db_uniqueguidXML;
        String db_paramNameXML = "@xml_data";
        String db_updateXML = "update ge_data set data_xml=@xml_data where rowguid=@rowguid;";
        String db_insertXML = "@newguid=newid(); insert into ge_data (rowguid, projectid, data_xml) values(@newguid,@projectid),@xml_data); select id, projectid, rowguid from ge_data where rowguid=@newguid;";
        String db_selectXML = "select id, projectid, data_xml, rowguid from ge_data where rowguid=@rowguid;";

        public AGS_Database(String connect) {
        setDbConnect (connect);
        }
        public void setDbConnect(String connect) {
          db_connect = connect;
        }
        public void setDbSelect_AGS(String select) {
            db_selectAGS = select;
        }

        public void setUniqueGUID_XML(String rowguid) {
            db_uniqueguidXML = rowguid;
        }
        public void setUniqueGUID_AGS(String rowguid) {
            db_uniqueguidAGS = rowguid;
        }
        public void setDbUpdate_AGS(String statement) {
            db_updateAGS = statement;
        }
        public void setDbInsert_AGS(String statement) {
            db_insertAGS = statement;
        }
        public void setDbSelect_XML(String statement) {
          db_selectXML = statement;
        }
        public void setDbInsert_XML(String statement) {
          db_insertXML = statement;
        }
        public void setDbUpdate_XML(String statement) {
          db_updateXML = statement;
     }
//        public String readAGS() {
//            if (db_connect.Length == 0 || db_selectAGS.Length==0) {
//                getLogger().info("db_connect:" + db_connect);
//                Console.WriteLine ();
//                Console.WriteLine ("db_selectAGS:" + db_selectAGS);
//                Console.WriteLine ("Insufficient parameters for readDatabaseAGS");
//            return null;
//            }    
//        
//            using (SqlConnection db = new SqlConnection(db_connect)) {
//                using (SqlCommand cmd = new SqlCommand(db_selectAGS, db)) {
//                    //SqlCommand s = new SqlCommand("select * from foo where a=@xml", db);
//                    //    cmd.Parameters.Add(db_paramNameXML, SqlDbType.Xml);
//                        cmd.Parameters.Add(db_paramRowGuid, SqlDbType.UniqueIdentifier);
//                    //    cmd.Parameters[db_paramNameXML].Value = xml_data;
//                    try {
//                        db.Open();
//                        cmd.ExecuteNonQuery();
//                        return "";
//                    } catch (SqlException e) {
//                        Console.WriteLine (e.Message);
//                        return null;
//                    }
//                }
//            }    
//        }
//        private void insertAGS() {
//
//        }
//        private void updateAGS() {
//
//        }
//        public void saveAGS() {
//
//            if (db_connect.Length == 0) {
//                if (db_uniqueguidAGS.ToString().Length > 0) {
//                    updateAGS();
//                } else {
//                    insertAGS();
//                }
//            } else {
//                Console.WriteLine ("Insufficient parameters for saveAGS");
//                return;
//                } 
//            }
//          
//        public void saveXML() {
//
//            if (db_connect.Length > 0) {
//                if (db_uniqueguidXML.ToString().Length > 0) {
//                    updateXML();
//                } else {
//                    insertXML();
//                }
//            } else {
//            Console.WriteLine ("Insufficient parameters for saveXML"); 
//            }
//        }
//          
//        private void insertXML() {
//            using (SqlConnection db = new SqlConnection(db_connect)) {
//                using (SqlCommand cmd = new SqlCommand(db_insertXML, db)) {
//                    cmd.Parameters.Add(db_paramNameXML, SqlDbType.VarChar,1024*4);
//                    cmd.Parameters[db_paramNameXML].Value = xml_data;  
//                        try {
//                            db.Open();
//                            cmd.ExecuteNonQuery();
//                        } catch (SqlException e) {
//                            Console.WriteLine (e.Message);
//                        } 
//                }
//            }
//        }
//        private void updateXML() {
//            using (SqlConnection db = new SqlConnection(db_connect)) {
//                using (SqlCommand cmd = new SqlCommand(db_updateXML, db)) {
//                    cmd.Parameters.Add(db_paramRowGuid, SqlDbType.UniqueIdentifier);   
//                    cmd.Parameters[db_paramRowGuid].Value = db_uniqueguidXML;
//                    cmd.Parameters.Add(db_paramNameXML, SqlDbType.VarChar,1024*4);
//                    cmd.Parameters[db_paramNameXML].Value = xml_data;  
//                        try {
//                            db.Open();
//                            cmd.ExecuteNonQuery();
//                        } catch (SqlException e) {
//                            Console.WriteLine (e.Message);
//                        } 
//                }
//            }
//        }
//    
//     public void readXML() {
//
//    }
    }


