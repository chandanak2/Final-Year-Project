
package com.action.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.invoke.empty.Empty;

import com.DAO.AdminDAO;
import com.DAO.UserDAO;
import com.util.FileDownload;
import com.util.RSA_File_EncryptionDecryption;
import com.util.SerializeToDatabase;
import com.util.Utility;

public class Download extends HttpServlet
{
	

	
	
	public void service(HttpServletRequest req,HttpServletResponse res)throws IOException
	{
		  ResultSet rs = null; 
		  String username = "";
		  int userId = 0;
		  int deptId = 0;
		  int designationId = 0;
		  int cloudId = 0;
		  String BlockId ="";
		  int fileid=0;
		  int totalNumberOfClouds = 0;
		  String fileName="";
		  String transactionStatus = "";
		  
		  String downloadDir = "";
		  
		  UserDAO userDAO = UserDAO.getInstance();
		  AdminDAO adminDAO = AdminDAO.getInstance();
		  
		  RequestDispatcher rd=null;
		  
		  HttpSession session = req.getSession();  
		  username = (String) session.getAttribute("username");
		  userId = userDAO.getID(username);
		  deptId = userDAO.getDepartmentID(username);
      	  designationId = userDAO.getDesignationID(username);
		
		  fileid = Integer.parseInt(req.getParameter("fileid").toString());
		
		  totalNumberOfClouds = adminDAO.getTotalNumberOfClouds();
		  
		  rs = adminDAO.getUploadFiles(fileid);
	     if(rs != null)
	     {
	    	try 
	    	{
	    		while(rs.next())
	    		{
	    			fileName = rs.getString(2);
	    			cloudId = rs.getInt(6);
	    			BlockId = rs.getString(10);
	    			System.out.println("BlockId>>>>>>>>>>"+BlockId);
	    		
	    		}
				
			} catch (Exception e) 
			{
				// TODO: handle exception
			}
	     }
		 
		  for(int i=1;i<=totalNumberOfClouds;i++)
	      {
	        	if(cloudId == i)
		        {
	        		downloadDir = "cloud"+i;
		        }
	      }
		  
		  /*//downloadDir = "cloud1";
		  downloadDir = "cloud1";*/
		 
	       if ( session.getAttribute( "waitPage" ) == null ) 
	       {  
	    	   session.setAttribute( "waitPage", Boolean.TRUE );  
	    	   PrintWriter out = res.getWriter();  
	    	   out.println( "<html><head>" );  
	    	   out.println( "<title>Please Wait...</title>" );  
	       	   out.println( "<meta http-equiv=\"Refresh\" content=\"0\">" );  
	    	   out.println( "</head><body bgcolor=''>" );  
	    	   out.println( "<br>" );
	    	   out.println( "<center>" );
	    	   out.print("<font color='red'>");
	    	   out.println( "Please Do not press Back or Refresh button.......<br>  " );
	    	   out.println("</font>");
	    	   out.print("<font color='green'>");
	    	   out.println( "Please,Wait..........<br>  " );
	    	   out.println( "Download Athentication In Process..." );
	    	   out.println( "<br>" );
	    	   out.println("</font>");
	    	   out.println( "<br>" );
	    	   out.print( "<img src='Resources/Images/status_processing.gif'></img><br><br>");
	    	   out.print("<font color='geen'>");
	    	   out.println( "Please Do not press Back or Refresh button.......<br>  " );
	    	   out.println( "<br>" );
	    	   out.println( "Downloading is in process..." );
	    	   out.println( "<br>" );
	    	   out.println( "The File is being decrypted...." );
	    	   out.println("</font>");
	    	   out.println( "<br>" );
	    	   out.println( "Please wait....</h1></center");  
	    	   out.close();  
	       }  
	       else 
	       { 
		    	session.removeAttribute( "waitPage" );  
				try
				{
					PrintWriter out=res.getWriter();
					res.setContentType("text/html");
					
					boolean flag = false;
					
					
					
					String ftpserver = "ftp.drivehq.com";
			        String ftpusername = "ehrblockchain";
			        String ftppassword = "*India123";
			      //  flag = FileDownload.download(ftpserver, ftpusername, ftppassword,downloadDir, fileName);
			        
			        flag = FileDownload.download(ftpserver, ftpusername, ftppassword,downloadDir, BlockId);
			        
		            if(flag)
					{
		            	/* Decrypting the downloaded File(Starts) */
		            	
		            	
		            	String src = "D:/Download/"+BlockId;
		            	
		            	
		            	
		            	
		            	
		            	
		            	
		            	
		            	
		            	
		            	 // String fileZip = "D:/EHR_Blocks/"+BlockId;
		       		  // System.out.println("fileZip>>>>>>>>>>>>>>"+fileZip);
		       	     
		       	   
		       	        byte[] buffer = new byte[1024];
		       	        ZipInputStream zis = new ZipInputStream(new FileInputStream(src));
		       	        System.out.println("zis>>>>>>>>>>>>>>>>>>>>>>"+zis);
		       	        ZipEntry zipEntry = zis.getNextEntry();
		       	        while(zipEntry != null){
		       	            String fileName1 = zipEntry.getName();
		       	            System.out.println("fileName1>>>>>>>>>>>>>>>>"+fileName1);
		       	          
		       	            File newFile = new File("D:/Download/" + fileName1);
		       	            System.out.println("newfile>>>>>>>>>>>>>>>>>>>"+newFile);
		       	            FileOutputStream fos = new FileOutputStream(newFile);
		       	            System.out.println("fos>>>>>>>>>>>>>>>>>>>>"+fos);
		       	            int len;
		       	            while ((len = zis.read(buffer)) > 0) 
		       	            {
		       	                fos.write(buffer, 0, len);
		       	            }
		       	            fos.close();
		       	            zipEntry = zis.getNextEntry();
		       	        }
		       	        zis.closeEntry();
		       	        zis.close();
		            	
		            	
		            	
		            	
		            	
		            	
		    	        String srcs = "D:/Download/enc_"+fileName;
		            	
		            	
		            	
		            	
		            	
		            	
		            	
		            	
		            	System.out.println("srcs file is>>>>>>>>>>>>>>"+srcs);
		            	
		            	
		            	
		            	
			            	
		            	String dest = req.getRealPath("") +"\\Files\\downloadsfile\\"+fileName;
		            	
			            	PrivateKey privateKey = (PrivateKey) SerializeToDatabase.getPrivateKey();
			            	RSA_File_EncryptionDecryption.decryptFile(srcs, dest, privateKey);
		            	/* Decrypting the downloaded File(Ends) */
			            	
			    /*        Ading Download Transaction To Database(Starts)
			            	File ofile1 = new File(dest);
					res.sendRedirect(req.getContextPath()+"/DownloadToClient?Des="+dest+"") ;*/
			            	transactionStatus = "Allowed";
			            	userDAO.addDownloadTransaction(Utility.getDate(),Utility.getTime(),userId,fileid,deptId,designationId, transactionStatus);
			            	
			            /* Adding Download Transaction To Database(Ends)*/	
						rd=req.getRequestDispatcher("/Resources/JSP/User/download_file.jsp?no=1&fname="+fileName+"&dfrom="+downloadDir+"");
						rd.forward(req, res);
					}
					else
					{
						rd=req.getRequestDispatcher("/Resources/JSP/User/download_file.jsp?no=3");
						rd.forward(req, res);
					}
							
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
	       }
}	
}
