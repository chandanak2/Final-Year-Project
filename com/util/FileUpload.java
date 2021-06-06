package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class FileUpload
{

 
	
	
	public static void upload( String ftpServer, String user, String password,
	         String fileName, File source, String dirToUploadFile) throws MalformedURLException,
	         IOException
	   {
		System.out.println("source file is>>>>>>>>>>>>>>"+source);
	      if (ftpServer != null && fileName != null && source != null)
	      {
	         StringBuffer sb = new StringBuffer( "ftp://" ); 
	         if (user != null && password != null)
	         {
	            sb.append( user );
	            sb.append( ':' );
	            sb.append( password );
	            sb.append( '@' );
	         }
	         sb.append( ftpServer );
	         sb.append( '/' );
	         sb.append( dirToUploadFile );
	         sb.append( '/' );
	         sb.append( source );
	         /*
	          * type ==> a=ASCII mode, i=image (binary) mode, d= file directory
	          * listing
	          */
	         sb.append( ";type=i" );
	         System.out.println("URL   :"+sb);
	         BufferedInputStream bis = null;
	         BufferedOutputStream bos = null;
	         try
	         {
	            URL url = new URL( sb.toString() );
	            URLConnection urlc = url.openConnection();
	            System.out.println("-----"+urlc);
	            bos = new BufferedOutputStream( urlc.getOutputStream() );
	            bis = new BufferedInputStream( new FileInputStream( source ) );
	            String s="";
	            int i;
	            // read byte by byte until end of stream
	            while ((i = bis.read()) != -1)
	            {
	            	 bos.write((byte)(char)i);
	            }
	           
	         }
	         finally
	         {
	            if (bis != null)
	               try
	               {
	                  bis.close();
	               }
	               catch (IOException ioe)
	               {
	                  ioe.printStackTrace();
	               }
	            if (bos != null)
	               try
	               {
	                  bos.close();
	               }
	               catch (IOException ioe)
	               {
	                  ioe.printStackTrace();
	               }
	         }
	      }
	      else
	      {
	         System.out.println( "Input not available." );
	      }
	   }
	
	
	
  /* public static void upload( String fileName, File source, String dirToUploadFile) throws MalformedURLException,
         IOException
   {
	   
	   System.out.println("its came inside the FileUpload>>>>>>>>>>>>>>>>>>>");
	   System.out.println(">>>>>>>>>>fileName>>>>>>>>>>>>>>>"+fileName);
	   System.out.println(">>>>>>>>>>>>source>>>>>>>>>>>>>"+source);
	   System.out.println(">>>>>>>>>>>>>>dirToUploadFile>>>>>>>>>>>"+dirToUploadFile);
      if ( fileName != null && source != null)
      {
        StringBuffer sb = new StringBuffer( "/" ); //ftp://dhsinf17:nikisujai@dhsinformatics.com
       // check for authentication else assume its anonymous access.
       if (user != null && password != null)
         {
            sb.append( user );
            sb.append( ':' );
            sb.append( password );
            sb.append( '@' );
         }
         sb.append( ftpServer );
         sb.append( '/' );
       sb.append( dirToUploadFile );
      sb.append( '/' );
         sb.append( fileName );
         
          * type ==> a=ASCII mode, i=image (binary) mode, d= file directory
          * listing
          
        sb.append( ";type=i" );
         System.out.println("URL   :"+sb);
         BufferedInputStream bis = null;
         BufferedOutputStream bos = null;
         try
         {
        URL url = new URL( sb.toString() );
        URLConnection urlc = url.openConnection();
         System.out.println("-----"+urlc);
            bos = new BufferedOutputStream( new FileOutputStream(dirToUploadFile));
            bis = new BufferedInputStream( new FileInputStream( source ) );
            String s="";
            int i;
            // read byte by byte until end of stream
            while ((i = bis.read()) != -1)
            {
            	 bos.write((byte)(char)i);
            }
           
         }
         finally
         {
            if (bis != null)
               try
               {
                  bis.close();
               }
               catch (IOException ioe)
               {
                  ioe.printStackTrace();
               }
            if (bos != null)
               try
               {
                  bos.close();
               }
               catch (IOException ioe)
               {
                  ioe.printStackTrace();
               }
         }
      }
      else
      {
         System.out.println( "Input not available." );
      }
   }*/
   /**
    * Download a file from a FTP server. A FTP URL is generated with the
    * following syntax:
    * ftp://user:password@host:port/filePath;type=i.
    * 
    * @param ftpServer , FTP server address (optional port ':portNumber').
    * @param user , Optional user name to login.
    * @param password , Optional password for user.
    * @param fileName , Name of file to download (with optional preceeding
    *            relative path, e.g. one/two/three.txt).
    * @param destination , Destination file to save.
    * @throws MalformedURLException, IOException on error.
    */
   
   
   public static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {

		System.out.println("fileName>>>>>>>>>>>>>>>>>"+fileName);

		File file = new File(fileName);
		System.out.println("files is>>>>>>>>>>>>>>"+file);
		FileInputStream fis = new FileInputStream(file);
		System.out.println("fis>>>>>>>>>>>>>>>>"+fis);
		
		
		
		
		
		
		 //File f = new File(ConfidentialFilePath);
	        
	        String fnm = file.getName();
	  
	   	 System.out.println("short filename>>>>>>>>>>>"+file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\")+1));
	        
	        //End//
	        
	  String shortpath=file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\")+1);
		
		
		
		
		
		
		
		ZipEntry zipEntry = new ZipEntry(shortpath);
		System.out.println("zipEntry>>>>>>>>>>>>>>>"+zipEntry);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

}
							
