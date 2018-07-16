/*
 * MRTSwathFiles.java
 *
 * Created on July 16, 2010, 12:21 PM
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
public class MRTSwathFiles {
   
   private String path = "";
   private String npath = "";
   private ArrayList<String> files;
   
   /** Creates a new instance of MRTSwathFiles */
   public MRTSwathFiles() {
      files = new ArrayList<String>();
   }
   
      public MRTSwathFiles(String path) {
      files = new ArrayList<String>();
      setPath(path);
   }

   public MRTSwathFiles(String path, String file) {
      files = new ArrayList<String>();
      setPath(path);
      addFile(file);
   }
      
   public String getPath() {
      return path;
   }

   public void setPath(String path) {
      this.path = path;
      this.npath = normalizePath(path);
   }

   public String getNPath() {
      return npath;
   }

   public String findFile(String file) {
      String f = "";
      for( int i = 0; i < files.size(); ++i ) {
         f = files.get(i);
         if( f.toUpperCase().equals(file.toUpperCase()) ) {
            return f;
         }
      }
      return "";
   }
   
   public int getNumberOfFiles() {
      return files.size();
   }
   
   public int addFile( String file ) {
      String f = findFile(file);
      if( f.length() == 0 ) {
         files.add(file);
         return 1;
      }
      return 0;
   }
   
   public ArrayList<String> getList() {
      return files;
   }

   static public String normalizePath(String path) {
      return MRTSwathUtil.createOSPath(path.toUpperCase());
   }
   
}
