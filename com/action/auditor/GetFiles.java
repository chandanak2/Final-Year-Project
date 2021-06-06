/**
 * 
 */
package com.action.auditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.sql.ResultSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.CommonDAO;
import com.util.FileDownload;
import com.util.HashingTechnique;
import com.util.RSA_File_EncryptionDecryption;
import com.util.SendMailAttachment;
import com.util.SerializeToDatabase;
import com.util.Utility;

import com.DAO.AdminDAO;

public class GetFiles extends HttpServlet 
{
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		PrintWriter out = response.getWriter();
		try
		{
			
		
			boolean flag=false;
		    String fileName1="";
		    int totalNumberOfClouds = 0;
			String submit=request.getParameter("submit");
			String name=request.getParameter("name");
			System.out.println("name is>>>>>>>>>>>>>>>>>>>>>"+name);
			  int cloudId = 0;
			  String downloadDir = "";
			String msg="";
			String fname="";
			String oldmd="";
			//boolean result=false;
			int rs3;
			ResultSet rs=CommonDAO.getFiles1(name);
			RequestDispatcher rd=null;
			if(submit.equals("Ok"))
			{
				if(rs.next())
				{
					rs=CommonDAO.getFiles1(name);
					request.setAttribute("rs", rs);
					request.setAttribute("name", name);
					
					rd=request.getRequestDispatcher("/Resources/JSP/Auditor/files.jsp");
					rd.forward(request, response);
				}
				else
				{
					rd=request.getRequestDispatcher("/Resources/JSP/Auditor/files.jsp?no=8");
					rd.forward(request, response);
				}
	
			
			}
			
			else if(submit.equals("Verify"))
			{
				String []chk=request.getParameterValues("chk");
				request.setAttribute("name", name);
				if(chk==null || chk.length!=1)
				{
					rs=CommonDAO.getFiles1(name);
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/Resources/JSP/Auditor/files.jsp?no=2");
					rd.forward(request,response);
				}
				else
				{
					boolean verify_process=false;
					for(int i=0;i<chk.length;i++)
					{
						System.out.println("check select box id>>>>>>>>>>>>>>>>>>>>>>>>"+chk[i]);
						
						fname=CommonDAO.getFileNo(chk[i]);
						int fid=Integer.parseInt(fname);
						System.out.println("fname is>>>>>>>>>>>>>>>>>>>>"+fname);
						String BlockId=CommonDAO.getFileNoname(fname);
						System.out.println("BlockId is>>>>>>>>>>>>>>>>>>>>"+BlockId);
						
						  HttpSession session= request.getSession();
						String fileName=CommonDAO.getFileNoname1(fname);
						System.out.println("filename is>>>>>>>>>>>>>>>>>>>>"+fileName);
						
						
						
						  String emailid=CommonDAO.getMailID1(name);
					      System.out.println("User emailid is>>>>>>>>>>>>>>>>> "+emailid);	
					
					      
					      
					      
					      AdminDAO adminDAO = AdminDAO.getInstance();
						  totalNumberOfClouds = adminDAO.getTotalNumberOfClouds();
						  
						  rs = adminDAO.getUploadFiles(fid);
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
								
							} 
					    	catch (Exception e) 
							{
								// TODO: handle exception
							}
					     }
						 
						  for(int j=1;j<=totalNumberOfClouds;j++)
					      {
					        	if(cloudId == j)
						        {
					        		downloadDir = "cloud"+j;
						        }
					      }
						  
						  //downloadDir = "cloud1";
						//  downloadDir = "cloud1";
						 
					     /*  if ( session.getAttribute( "waitPage" ) == null ) 
					       {  
					    	   session.setAttribute( "waitPage", Boolean.TRUE );  
					    	   PrintWriter out1 = response.getWriter();  
					    	   out1.println( "<html><head>" );  
					    	   out1.println( "<title>Please Wait...</title>" );  
					       	   out1.println( "<meta http-equiv=\"Refresh\" content=\"0\">" );  
					    	   out1.println( "</head><body bgcolor=''>" );  
					    	   out1.println( "<br>" );
					    	   out1.println( "<center>" );
					    	   out1.print("<font color='red'>");
					    	   out1.println( "Please Do not press Back or Refresh button.......<br>  " );
					    	   out1.println("</font>");
					    	   out1.print("<font color='green'>");
					    	   out1.println( "Please,Wait..........<br>  " );
					    	   out1.println( "Download Athentication In Process..." );
					    	   out1.println( "<br>" );
					    	   out1.println("</font>");
					    	   out1.println( "<br>" );
					    	   out1.print( "<img src='Resources/Images/status_processing.gif'></img><br><br>");
					    	   out1.print("<font color='geen'>");
					    	   out1.println( "Please Do not press Back or Refresh button.......<br>  " );
					    	   out1.println( "<br>" );
					    	   out1.println( "Downloading is in process..." );
					    	   out1.println( "<br>" );
					    	   out1.println( "The File is being decrypted...." );
					    	   out1.println("</font>");
					    	   out1.println( "<br>" );
					    	   out1.println( "Please wait....</h1></center");  
					    	   out1.close();  
					       }  
					       
					       else 
					       { 
						    	session.removeAttribute( "waitPage" );  
								try
								{
									PrintWriter out1=response.getWriter();
									response.setContentType("text/html");
									
									boolean flag1 = false;
									
								
									
									String ftpserver = "ftp.drivehq.com";
							        String ftpusername = "priyalitty";
							        String ftppassword = "priyalitty";
							      //  flag = FileDownload.download(ftpserver, ftpusername, ftppassword,downloadDir, fileName);
							        
							        flag = FileDownload.download(ftpserver, ftpusername, ftppassword,downloadDir, BlockId);
							        
						            if(flag)
									{
						            
						            	
						            	String src = "D:/Download/"+BlockId;
						            	
						            	
						            	
						            	
						       	        byte[] buffer = new byte[1024];
						       	        ZipInputStream zis = new ZipInputStream(new FileInputStream(src));
						       	        System.out.println("zis>>>>>>>>>>>>>>>>>>>>>>"+zis);
						       	        ZipEntry zipEntry = zis.getNextEntry();
						       	        while(zipEntry != null){
						       	            String fileName2 = zipEntry.getName();
						       	            System.out.println("fileName2>>>>>>>>>>>>>>>>"+fileName2);
						       	          
						       	            File newFile = new File("D:/Download/" + fileName2);
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
						            	
						            	
						            	
						            	
						            
					            	    String dest = request.getRealPath("") +"\\Files\\DownloadVerify\\"+fileName;
					            	
						            	PrivateKey privateKey = (PrivateKey) SerializeToDatabase.getPrivateKey();
						            	RSA_File_EncryptionDecryption.decryptFile(src, dest, privateKey);
						
						
						            	
						            	
						            	
						            	
						
						            	String localFilePath = request.getRealPath("") +"\\Files\\DownloadVerify\\"+fileName;
						            	//  File file=new File(localFilePath);
						            	String localFilePath1 = request.getRealPath("") +"\\Files\\Upload\\"+fileName;
						            	
						                //Hash Function Start//
								        
								        HashingTechnique g1 = new HashingTechnique();
								        String  hashtag = g1.MD5(localFilePath1);
								     
								        System.out.println("hashtag is>>>>>>>>>>>>>>>>>>>>>>>>>>>"+hashtag);
										   
									        //End//
						            	
						            	
						            
								        String previousblkno=CommonDAO.previousblkid(fname);
										System.out.println("genesisblk id is>>>>>>>>>>>>>>>>>>"+previousblkno);
										
										
										String previousblkvale=CommonDAO.previousblk(previousblkno);
										
										System.out.println("previousblkvale>>>>>>>>>>>>>>>>>>>>>>>>>>>"+previousblkvale);
										
										
										
										 flag=hashtag.equals(previousblkvale);
										System.out.println("flag value is >>>>>>>>>>>>>"+flag);
						*/
						  
						  
						  
							String ftpserver = "ftp.drivehq.com";
					        String ftpusername = "ehrblockchain";
					        String ftppassword = "*India123";
					      //  flag = FileDownload.download(ftpserver, ftpusername, ftppassword,downloadDir, fileName);
					        
					        flag = FileDownload.download(ftpserver, ftpusername, ftppassword,downloadDir, BlockId);
					        
				            if(flag)
							{
				            
				            	
				            	String src = "D:/Download/"+BlockId;
				            	
				            	
				            	
				            	
				       	        byte[] buffer = new byte[1024];
				       	        ZipInputStream zis = new ZipInputStream(new FileInputStream(src));
				       	        System.out.println("zis>>>>>>>>>>>>>>>>>>>>>>"+zis);
				       	        ZipEntry zipEntry = zis.getNextEntry();
				       	        while(zipEntry != null){
				       	            String fileName2 = zipEntry.getName();
				       	            System.out.println("fileName2>>>>>>>>>>>>>>>>"+fileName2);
				       	          
				       	            File newFile = new File("D:/Download/" + fileName2);
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
				            	
				            	
				            	
				            	
				            
			            	    String dest = request.getRealPath("") +"\\Files\\DownloadVerify\\"+fileName;
			            	
				            	PrivateKey privateKey = (PrivateKey) SerializeToDatabase.getPrivateKey();
				            	RSA_File_EncryptionDecryption.decryptFile(srcs, dest, privateKey);
				
				
				            	
				            	
				            	
				            	
				
				            	String localFilePath = request.getRealPath("") +"\\Files\\DownloadVerify\\"+fileName;
				            	//  File file=new File(localFilePath);
				            	String localFilePath1 = request.getRealPath("") +"\\Files\\Upload\\"+fileName;
				            	
				                //Hash Function Start//
						        
						        HashingTechnique g1 = new HashingTechnique();
						        String  hashtag = g1.MD5(localFilePath1);
						     
						        System.out.println("hashtag is>>>>>>>>>>>>>>>>>>>>>>>>>>>"+hashtag);
								   
							        //End//
				            	
				            	
				            
						        String previousblkno=CommonDAO.previousblkid(fname);
								System.out.println("genesisblk id is>>>>>>>>>>>>>>>>>>"+previousblkno);
								
								
								String previousblkvale=CommonDAO.previousblk(previousblkno);
								
								System.out.println("previousblkvale>>>>>>>>>>>>>>>>>>>>>>>>>>>"+previousblkvale);
								
								
								
								 flag=hashtag.equals(previousblkvale);
								System.out.println("flag value is >>>>>>>>>>>>>"+flag);
								
								
								
								
								
								
								
								
								
								
								
						if(flag)
						{
							
							System.out.println("its came inside the if condition>>>>>>>>>>>>>>>>>>>>>>>");
							msg="Dear "+name.toUpperCase()+"...... \n\n " +	"        The File '"+fileName+"'  has been Verified successfully, no file modification happened " +"\n\n\n\n\n\n\n\nThank You\n\n";
							SendMailAttachment.sendPersonalizedMail(emailid,"From Block Chain Service", msg,"no");
							
							
							rd=request.getRequestDispatcher("/Resources/JSP/Auditor/files.jsp?no=2");
							rd.forward(request,response);
						
							
						}
									}
						else
						{
							
							
							msg="Dear "+name.toUpperCase()+"...... \n\n " +	"        The File '"+fileName+"'  has been modified by someone so please upload fresh  " +"\n\n\n\n\n\n\n\nThank You\n\n";
							SendMailAttachment.sendPersonalizedMail(emailid,"From Block Chain Service", msg,"no");
							
							
							rd=request.getRequestDispatcher("/Resources/JSP/Auditor/files.jsp?no=3");
							rd.forward(request,response);
							
							
						
						
						
		        }
						 
         }
						
						
			
						}
			
		}
			
			
		
			
	
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	
}
}



