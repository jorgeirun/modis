/*
 * MRTSwathDate.java
 *
 * Created on July 16, 2010, 12:18 PM
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
public class MRTSwathDate {
   
   private String date = "";
   private String nDate = "";
   private ArrayList<MRTSwathFiles> files;
   
   /** Creates a new instance of MRTSwathDate */
   public MRTSwathDate() {
      files = new ArrayList<MRTSwathFiles>();
   }
   
   public MRTSwathDate(String date) {
      files = new ArrayList<MRTSwathFiles>();
      setDate(date);
   }

   public MRTSwathDate(String date, String path, String file) {
      files = new ArrayList<MRTSwathFiles>();
      setDate(date);
      addFile(path, file);
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
      this.nDate = normalizeDate(date);
   }

   public String getNDate() {
      return nDate;
   }
   
   public MRTSwathFiles findPath( String path ) {
      MRTSwathFiles p = null;
      for( int i = 0; i < files.size(); ++i ) {
         p = files.get(i);
         if( p.getNPath().equals(MRTSwathFiles.normalizePath(path)) ) {
            return p;
         }
      }
      return null;
   }
   
   public String findFile( String path, String file ) {
      MRTSwathFiles p = findPath(path);
      if( p != null )
         return p.findFile(file);
      return "";
   }

   public int addFile( String path, String file ) {
      MRTSwathFiles flist = findPath(path);
      if( flist == null ) {
         files.add(new MRTSwathFiles(path, file));
         return 1;
      } else {
         return flist.addFile(file);
      }
   }
   
   public ArrayList<MRTSwathFiles> getList() {
      return files;
   }
   
   public static String normalizeDate( String date ) {
      return date.toUpperCase();
   }
   
}
