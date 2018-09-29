/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agsml;
import java.io.PrintWriter;
import org.w3c.dom.NodeList;
/**
 *
 * @author Simon
 */
class Main {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {
    testOSNationalGridConversion();
     
      }

/* private void parse(){
   // http://it.toolbox.com/blogs/enterprise-web-solutions/parsing-an-xsd-schema-in-java-32565
/*
   try {
        XSOMParser parser = new XSOMParser();
        parser.parse(file);
        this.schemaSet = parser.getResult();
        this.xsSchema = this.schemaSet.getSchema(1);
    }
    catch (Exception exp) {
        exp.printStackTrace(System.out);
    }
 *

    }*/
   
   
   public static void testOSNationalGridConversion() {
   
       OSNationalGridConversion osg =  new OSNationalGridConversion();
       osg.Easting = 544523;
       osg.Northing = 181960;
       System.out.print (osg.dbOSGridToLatitudeLongitudePair());
   }
   public static void test_domecho(){
      PrintWriter pw = new PrintWriter(System.out,true);

      DOMEcho de= new DOMEcho(pw);
      String s1[] = new String[1];
      s1[0] ="F:/Netbeans/AGS_Files/schemas/AGSMLv1Schemas/agsml.xsd";
      // s1[1] ="abbrs.xml";
      try {
      DOMEcho.main(s1);
      }
      catch (java.lang.Exception e ) {
      }

   }

   public static void test5(){
         String ags_name = "f:/Netbeans/AGS_Files/con_test.ags";
         String xml_name = "f:/Netbeans/AGS_Files/con_test.xml";
         AGS_Converter ac = new AGS_Converter();
        ac.AGS_to_XML(ags_name, xml_name);
        
    }
   public static void test4(){
         String ags_name = "f:/projects/Netbeans/AGS_Files/bhdisk2.ags";
         String xml_name = "f:/projects/Netbeans/AGS_Files/bhdisk21.xml";
          AGS_Converter ac = new AGS_Converter();
        ac.AGS_to_XML(ags_name, xml_name);
    }
   public static void test6(){
         String ags_name = "f:/Netbeans/AGS_Files/alldata.ags";
         String xml_name = "f:/Netbeans/AGS_Files/alldata.xml";
         AGS_Converter ac = new AGS_Converter();
        ac.AGS_to_XML(ags_name, xml_name);
    }
   public static void test2(){
        String fname = "g:/Netbeans/AGS_Files/bhdisk2.ags";
         AGS_Converter ac = new AGS_Converter();
       ac.AGSLineReader_file(fname);
   }
   public static void test3(){
        String flist = "h:/home/thomsonsj/m7-mg_ags.txt";
        AGS_Converter ac = new AGS_Converter();
       ac.AGSLineReader_filelist(flist);
   }
      public static void test7(){
        String flist = "h:/home/thomsonsj/m7-mg_ags.txt";
        String home_folder = "h:/home/thomsonsj/ags_xml/";
        AGS_Converter ac = new AGS_Converter();
       ac.AGS_XML_filelist(flist, home_folder);
   }
       public static void test8(){
         String ags_name = "g:/Netbeans/AGS_Files/alldata.ags";
         String xml_name = "g:/Netbeans/AGS_Files/alldata_nest.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_XML_Nest(ags_name, xml_name);
    }
       public static void test9(){
         String ags_name = "g:/Netbeans/AGS_Files/SI_Data_004.ags";
         String xml_name = "g:/Netbeans/AGS_Files/SI_Data_004_nest.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_XML_Nest(ags_name, xml_name);
    }
        public static void test10(){
        String flist = "h:/home/thomsonsj/m7-mg_ags.txt";
        String home_folder = "h:/home/thomsonsj/ags_xml/nest/";
        AGS_Converter ac = new AGS_Converter();
       ac.AGS_XMLNest_filelist(flist, home_folder);
   }
       public static void test11(){
         String ags_name = "g:/projects/Netbeans/AGS_Files/bhdisk2.ags";
         String xml_name = "g:/projects/Netbeans/AGS_Files/bhdisk2_nest.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_XML_Nest(ags_name, xml_name);
    }
           public static void test11a(){
         String ags_name = "g:/projects/Netbeans/AGS_Files/bhdisk2.ags";
         String xml_name = "g:/projects/Netbeans/AGS_Files/bhdisk2_nest.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_XML_Nest(ags_name, xml_name, "G:/Projects/AGS_XML/DictionaryAgsml.xml");
    }
          public static void test11b(){
         String ags_name = "g:/projects/Netbeans/AGS_Files/bhdisk2.ags";
         String xml_name = "g:/projects/Netbeans/AGS_Files/bhdisk2_agsml.xml";
         String xml_dictionary ="g:/Projects/AGS_XML/DictionaryAgsml.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_AGSML(ags_name, xml_name,xml_dictionary);
    }     
        public static void test_a14_2014_all(){
        String ags_name = "c:/pwworking/jacobs_uk_highways_ss4/ex-thomsos/dms02966/a14_2014_all.ags";
        String xml_name = "c:/pwworking/jacobs_uk_highways_ss4/ex-thomsos/dms02966/a14_2014_all.xml";
        String xml_dictionary ="e:/Projects/AGS_XML/DictionaryAgsml.xml";
        AGS_Converter ac = new AGS_Converter();
        ac.AGS_to_AGSML(ags_name, xml_name,xml_dictionary);
    }   
public static void test12(){
    try {
    String fname;
    String tname;
    AGS_Dictionary agsd =  new AGS_Dictionary("G:/Projects/AGS_XML/DictionaryAgsml.xml");
  // agsd.echo_root();
 //   agsd.find_table1("**PROJ");
  //  agsd.find_field1("*PROJ_ID");
  //  tname = agsd.find_table2Value("name");
  //  fname = agsd.find_field2Value("name");
//    System.out.print(tname + "\n\r");
//    System.out.print(fname + "\n\r");
    }
    catch (Exception e) {
         System.out.print(e.getMessage());
      }
}

public static void test14(){
    try {
    AGS_Dictionary agsd =  new AGS_Dictionary("G:/Projects/AGS_XML/DictionaryAgsml.xml");
    agsd.first_table();
    do {
//      System.out.print("\n\rTable:" + agsd.find_table2Value("name") + "\n\r");
      agsd.first_field();
        do {
 //        System.out.print(agsd.find_field2Value("name") + ",");
        } while (agsd.next_field() != null ) ;
       System.out.print("\n\r");
       agsd.first_field();

       do {
//         System.out.print(agsd.find_field2Value("type") + ",");
        } while (agsd.next_field() != null ) ;
     } while (agsd.next_table() != null );
   }
    catch (Exception e) {
         System.out.print(e.getMessage());
      } 
}
public static void test13(){
    try {
    AGS_Dictionary agsd =  new AGS_Dictionary("f:/Projects/AGS_XML/DictionaryAgsml.xml");
     agsd.first_table();
    do {
//      System.out.print("\n\rTable:" + agsd.find_tableValue("name") + "\n\r");
      agsd.first_field();
        do {
   //      System.out.print(agsd.find_fieldValue("name") + ",");
        } while (agsd.next_field() != null ) ;
       System.out.print("\n\r");
       agsd.first_field();
     } while (agsd.next_table() != null );
    }
    catch (Exception e) {
         System.out.print(e.getMessage());
      }
}
public static void test15(){
    try {
    String ags_file = "f:/projects/netbeans/ags_files/bhdisk2.ags";
   String xml_file = "f:/projects/netbeans/ags_files/bhdisk2.xml";
   String xml_dictionary ="e:/Projects/AGS_XML/DictionaryAgsml.xml";
  AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_AGSML(ags_file, xml_file, xml_dictionary);
  }
    catch (Exception e) {
         System.out.print(e.getMessage());
      }     
}

public static void test16(){
    try {
   AGS_Dictionary agsd =  new AGS_Dictionary("f:/Projects/AGS_XML/DictionaryAgsml.xml");
   if (agsd.find_tableAGS("**CLSS")!=null) {
       if (agsd.find_fieldAGS("*CLSS_425") != null) {
           String s1 = agsd.fieldGINT_name();
           System.out.print(s1);
       }
   }
   
   
   }
    catch (Exception e) {
         System.out.print(e.getMessage());
      }
}
public static void test17(){
         String ags_name = "C:/Documents and Settings/thomsonsj/My Documents/2011/Forth Replacement/ALL/AGS/FORTH02.ags";
         String xml_name = "C:/Documents and Settings/thomsonsj/My Documents/2011/Forth Replacement/ALL/AGS/FORTH02.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_XML(ags_name, xml_name);
   }
   public static void test18(){
         String ags_name = "f:/Projects/Netbeans/AGS_Files/6851.ags";
         String xml_name = "f:/Projects/Netbeans/AGS_Files/6851.xml";
         String xml_dictionary ="f:/Projects/AGS_XML/DictionaryAgsml.xml";
         AGS_Converter ac = new AGS_Converter();
      ac.AGS_to_AGSML(ags_name, xml_name, xml_dictionary);
    }
public static void test19(){
         String ags_name = "E:/Projects/AGS_XML/FORTH02.ags";
         String xml_name = "E:/Projects/AGS_XMLFORTH02.xml";
         String xml_dictionary ="e:/Projects/AGS_XML/DictionaryAgsml.xml";
         AGS_Converter ac = new AGS_Converter();
      ac.AGS_to_AGSML(ags_name, xml_name, xml_dictionary);
   }
public static void test20(){
         String ags_name = "C:/Users/thomsonsj/Documents/2014/Tideway Tunnel East Lot/Combined_28-01-2014.ags";
         String xml_name = "C:/Users/thomsonsj/Documents/201+4/Tideway Tunnel East Lot/Combined_28-01-2014.xml";
         String xml_dictionary ="e:/Projects/AGS_XML/DictionaryAgsml.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_AGSML(ags_name, xml_name, xml_dictionary);
   }
public static void test21(){
         String ags_name = "C:/Users/thomsonsj/Documents/2014/Tideway/Combined_28-01-2014.ags";
         String xml_name = "C:/Users/thomsonsj/Documents/2014/Tideway/Combined_09-04-2014.xml";
         String xml_dictionary ="e:/Projects/AGS_XML/DictionaryAgsml_0.0.2.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_AGSML(ags_name, xml_name, xml_dictionary);
   }
public static void test22(){
         String ags_name = "C:/Users/thomsonsj/Documents/2014/PortoNovi/gINT/porto novi_cry rev4.ags";
         String xml_name = "C:/Users/thomsonsj/Documents/2014/PortoNovi/gINT/porto novi_cry rev4.xml";
         String xml_dictionary ="e:/Projects/AGS_XML/DictionaryAgsml_0.0.2.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_AGSML(ags_name, xml_name, xml_dictionary);
   }
public static void test23(){
         String ags_name = "C:/Users/thomsonsj/Documents/2014/PortoNovi/gINT/porto_novi_STCN_r01.ags";
         String xml_name = "C:/Users/thomsonsj/Documents/2014/PortoNovi/gINT/porto_novi_STCN_r01.xml";
         String xml_dictionary ="e:/Projects/AGS_XML/DictionaryAgsml_0.0.2.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_AGSML(ags_name, xml_name, xml_dictionary);
   }

public static void test1103(){
    String file_in = "C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/ArcGIS/A14_C2H_J2A_10.xml";
    String file_out ="C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/ArcGIS/A14_C2H_J2A_101.xml";
    XML_DOM xml_in = new XML_DOM (file_in);
    NodeList nl = xml_in.doc().getElementsByTagName("Hole");
    AGS_XML_Query q = new AGS_XML_Query();
    NodeList n2 = q.Where(nl,"HoleType","CPT");
    q.SaveFile(file_out);
    
}
public static void test1104(){
    String file_in = "C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/ArcGIS/A14_C2H_J2A_10.xml";
    String file_out ="C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/ArcGIS/A14_C2H_J2A_102.xml";
    XML_DOM xml_in = new XML_DOM (file_in);
    NodeList nl = xml_in.doc().getElementsByTagName("Hole");
    AGS_XML_Query q = new AGS_XML_Query();
    NodeList n2 = q.WhereNOT(nl,"HoleType","CPT");
    q.SaveFile(file_out);
    
}
public static void a14_2014_all_finalp2_nostcn(){
         String ags_name = "C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/Final/a14_2014_all_finalp2_nostcn.ags";
         String xml_name = "C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/Final/a14_2014_all_finalp2_nostcn.xml";
         String xml_dictionary ="i:/Projects/AGS_XML/DictionaryAgsml_0.0.2.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_AGSML(ags_name, xml_name, xml_dictionary);

}

public static void a14_2014_all_finalp2_cnmt(){
         String ags_name = "C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/Final/a14_2014_all_finalp2_cnmt.ags";
         String xml_name = "C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/Final/a14_2014_all_finalp2_cnmt.xml";
         String xml_dictionary ="C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/Final/DictionaryAgsml_0.0.2_CNMT_Only.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_AGSML(ags_name, xml_name, xml_dictionary);
}
public static void a14_2014_fugro(){
         String ags_name = "C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/Final/a14_2014_fugro.ags";
         String xml_name = "C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/Final/a14_2014_fugro.xml";
         String xml_dictionary ="i:/Projects/AGS_XML/DictionaryAgsml_0.0.2.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_AGSML(ags_name, xml_name, xml_dictionary);

}
public static void a14_2014_fugro_part2(){
         String ags_name = "C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/Final/a14_2014_fugro_part2.ags";
         String xml_name = "C:/Users/thomsonsj/Documents/2014/A14 Cambridge to Huntingdon/Final/a14_2014_fugro_part2.xml";
         String xml_dictionary ="i:/Projects/AGS_XML/DictionaryAgsml_0.0.2.xml";
         AGS_Converter ac = new AGS_Converter();
       ac.AGS_to_AGSML(ags_name, xml_name, xml_dictionary);

}
}



