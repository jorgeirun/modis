/*
 * MRTSwathShortName.java
 *
 * Created on July 16, 2010, 12:15 PM
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
public class MRTSwathShortName {
   
   private String shortName = "";
   private String nShortName = "";
   private ArrayList<MRTSwathDate> dates;
   
   /** Creates a new instance of MRTSwathShortName */
   public MRTSwathShortName() {
      dates = new ArrayList<MRTSwathDate>();
   }
   public MRTSwathShortName(String shortname) {
      dates = new ArrayList<MRTSwathDate>();
      setShortName(shortname);
   }

   public MRTSwathShortName(String shortname, String date, String path, String file) {
      dates = new ArrayList<MRTSwathDate>();
      setShortName(shortname);
      addDatedFile(date, path, file);
   }

   public String getShortName() {
      return shortName;
   }

   public void setShortName(String shortname) {
      this.shortName = shortname;
      this.nShortName = normalizeShortName(shortname);
   }

   public String getNShortName() {
      return nShortName;
   }
   
   public MRTSwathDate findDate( String date ) {
      MRTSwathDate d = null;
      for( int i = 0; i < dates.size(); ++i ) {
         d = dates.get(i);
         if( d.getNDate().equals(MRTSwathDate.normalizeDate(date)) ) {
            return d;
         }
      }
      return null;
   }

   public int addDatedFile( String date, String path, String file ) {
      MRTSwathDate dlist = findDate(date);
      if( dlist == null ) {
         dates.add(new MRTSwathDate(date, path, file));
         return 1;
      } else {
         return dlist.addFile(path, file);
      }
   }
   
   public ArrayList<MRTSwathDate> getList() {
      return dates;
   }
   
   public static String normalizeShortName( String shortName ) {
      return shortName.toUpperCase();
   }
   
}
