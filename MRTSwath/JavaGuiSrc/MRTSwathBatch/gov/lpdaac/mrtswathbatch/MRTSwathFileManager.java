/*
 * MRTSwathFileManager.java
 *
 * Created on July 16, 2010, 12:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.lpdaac.mrtswathbatch;

import java.util.ArrayList;


/**
 *
 * @author mmerritt
 */
public class MRTSwathFileManager {

   private ArrayList<MRTSwathShortName> names;

   /** Creates a new instance of MRTSwathFileManager */
   public MRTSwathFileManager() {
      names = new ArrayList<MRTSwathShortName>();
   }
   
   public MRTSwathFileManager(String shortname, String date, String path, String file) {
      names = new ArrayList<MRTSwathShortName>();
   }
   
   public MRTSwathShortName findShortName( String shortname ) {
      MRTSwathShortName s = null;
      for( int i = 0; i < names.size(); ++i ) {
         s = names.get(i);
         if( s.getNShortName().equals(MRTSwathShortName.normalizeShortName(shortname)) ) {
            return s;
         }
      }
      return null;
   }
   
   public int addItem(String shortname, String date, String path, String file) {
      MRTSwathShortName name = findShortName( shortname );
      if( name == null ) {
         names.add(new MRTSwathShortName(shortname,date,path,file));
         return 1;
      } else {
         return name.addDatedFile(date, path, file);
      }
   }
   
   public ArrayList<MRTSwathShortName> getShortNames() {
      return names;
   }
   
   public void displayTree(Boolean normalized) {
      for( MRTSwathShortName s : names ) {
         System.out.println( "ShortName: " + (normalized ? s.getNShortName() : s.getShortName()) );
         ArrayList<MRTSwathDate> dates = s.getList();
         for( MRTSwathDate d : dates ) {
            System.out.println( "  Date: " + (normalized ? d.getNDate() : d.getDate()));
            ArrayList<MRTSwathFiles> paths = d.getList();
            for( MRTSwathFiles p : paths ) {
               System.out.println( "    Path: " + (normalized ? p.getNPath() : p.getPath()));
               ArrayList<String> files = p.getList();
               for( String f : files ) {
                  System.out.println( "      File: " + f.toString() );
               }
            }
         }
      }
   }
   
   public Boolean isEmpty() {
      return names.isEmpty();
   }
   
}
