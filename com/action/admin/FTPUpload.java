
package com.action.admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.zip.ZipOutputStream;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.AdminDAO;
import com.DAO.UserDAO;
import com.drw.util.RSA_Algorithm;
import com.sun.org.apache.xml.internal.serializer.SerializerTrace;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.util.*;



public class FTPUpload extends HttpServlet
{
	private static String username="";
	private static int userid=0;
	private static String selectedFileType="";
	private static String dirToUploadFile="";


	private static String CLOUD1="ftp://ftp.drivehq.com/cloud1";
	private static String CLOUD2="ftp://ftp.drivehq.com/cloud2";
	private static String CLOUD3="ftp://ftp.drivehq.com/cloud3";
	private static String CLOUD4="ftp://ftp.drivehq.com/cloud4";
	private static String cloud1Status="";
	private static String cloud2Status="";
	private static String cloud3Status="";
	private static String cloud4Status="";

	
	public void service(HttpServletRequest req,HttpServletResponse res)throws IOException
	{
		   RequestDispatcher rd = null;
		   String uploadSubject = "";
		   int cloudId =0,ownerId=0;
		   int totalNumberOfClouds = 0;
		   String hashtag="";
		   String srcFilePath = "";
		   String destFilePath = "";
		   
		   
		   UserDAO userDAO = UserDAO.getInstance();
		   AdminDAO adminDAO = AdminDAO.getInstance();
		   
		   HttpSession session = req.getSession();  
		   
		   if(session != null)
			{
			  username = (String) session.getAttribute("username");
			  uploadSubject = session.getAttribute("subject").toString();
			}
		   
		   
	       if ( session.getAttribute( "waitPage" ) == null ) 
	       {  
	    	   session.setAttribute( "waitPage", Boolean.TRUE );  
	    	   PrintWriter out = res.getWriter();  
	    	   out.println( "<html><head>" );  
	    	   out.println( "<title>Please Wait...</title>" );  
	       	   out.println( "<meta http-equiv=\"Refresh\" content=\"0\">" );  
	    	   out.println( "</head><body bgcolor=''>" );   
	    	   out.println( "<br><br><br>" );
	    	   out.print( "<center><img src='Resources/Images/status_processing.gif'></img><br><br>");
	    	   out.println( "Please Do not press Back or Refresh button.......<br>  " );
	    	   out.println("<font color='white' size='5'>");
	    	   out.println( "File Uploading is in Process......  " );
	    	   out.println("</font><br>");
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
					
					
					String path=(String)session.getAttribute("filepath");
					
					System.out.println("Source ::> "+path);
				
					String filename=(String)session.getAttribute("fname");
					System.out.println("File Name ::> "+filename);
					
					String localFilePath = req.getRealPath("") +"\\Files\\Upload\\"+filename;
				  
			        
			        /* Encrypting the file (Starts) */
			        	srcFilePath = localFilePath;
			        	//destFilePath = req.getRealPath("") +"\\Files\\Upload\\enc_"+filename;
			        	
			        	
			        	destFilePath = req.getRealPath("") +"\\Files\\Upload\\enc_"+filename;
			        	System.out.println("destFilePath enc file>>>>>>>>>>>>>>>>>>>"+destFilePath);
			        	
			        	
			        	PublicKey pubKey = (PublicKey) SerializeToDatabase.getPublicKey();
			        	RSA_File_EncryptionDecryption.encryptFile(srcFilePath, destFilePath,pubKey);
			        /* Encrypting the file (Ends) */
			       
			              File file=new File(destFilePath);
			        
    				
    				
	                   //Hash Function Start//
				        
				        HashingTechnique g1 = new HashingTechnique();
				        hashtag = g1.MD5(localFilePath);
						   
					        //End//
				        
				        
			        
			           //Random Function for nonce //
				        Random r = new Random();
					    String randomNumber = String.format("%04d", (Object) Integer.valueOf(r.nextInt(1001)));
					    System.out.println("randomNumber>>>>>>>>>>>>>>>>>>>>>>>>"+randomNumber);
					    //End
			        
					  //  FileUpload.upload(filename,file,destFilePath);
				        System.out.println("uploadded success>>>>>>>>>>>>>>>");
			        
			         
					
					/* Adding the upload Transaction details (Start)*/
					
					boolean flag = false;
					int dotPos = filename.lastIndexOf(".");
				    String extension = filename.substring(dotPos);
				    String fileType=extension;
					
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
					SimpleDateFormat formatter1=new SimpleDateFormat("HH:mm:ss");
					String date = formatter.format(currentDate.getTime());
					String time = formatter1.format(currentDate.getTime());
					
					date = date + "  " + time;
					System.out.println("date is>>>>>>>>>>>>>>>>"+date);
					ownerId = adminDAO.getDataOwnerId(username);
					
					System.out.println("owner id is>>>>>>>>>>>>>>>>>>>>>>>"+ownerId);
					
					String previousblk=adminDAO.previousblkid();
					System.out.println("genesisblk id is>>>>>>>>>>>>>>>>>>"+previousblk);
					
					
					String previousblkvale=adminDAO.previousblk( previousblk);
					
		
				    int id  =  adminDAO.getTransID();
				    int fileid=id+1;
					
					
				        
				        //Confidential Key Creation Start//
			
				        
				        String value=""+previousblkvale+"~"+date+"~"+""+randomNumber+"~"+hashtag;
						   
						   System.out.println("Input :"+value);
						   
						 
				
						   String ConfidentialFilePath = req.getRealPath("")+"\\ConfidentialKeys\\"+"C_"+fileid+".txt";
						   
						  
						   System.out.println("File Path :"+ConfidentialFilePath);
						   FileWriter fstream = new FileWriter(ConfidentialFilePath);
							
							BufferedWriter out1 = new BufferedWriter(fstream);

					        out1.write(value);
					        out1.close();
					        
					        File f = new File(ConfidentialFilePath);
					        
					        String fnm = f.getName();
					  
					   	 System.out.println("short filename>>>>>>>>>>>"+f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("\\")+1));
					        
					        //End//
					        
					     String shortpath=f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("\\")+1);
					 
					        String zipblkame="B"+fileid+".zip";
					        
					        //Zip File Code//
					    	
					        
					        
					     //   File newFile = new File("D:\\EHR_Blocks\\"+zipblkame);
					        
					        File newFile = new File(zipblkame);
				            System.out.println("newfile>>>>>>>>>>>>>>>>>>>"+newFile);
				            FileOutputStream fos = new FileOutputStream(newFile);
				            System.out.println("fos>>>>>>>>>>>>>>>>>>>>"+fos);
				            
				            
				            
				            
					       // FileOutputStream fos = new FileOutputStream("D:\\EHR_Blocks\\"+zipblkame);
							ZipOutputStream zos = new ZipOutputStream(fos);

							String file1Name = ConfidentialFilePath;
							System.out.println("file1Name>>one>>>>>>>>>>>>>>>"+file1Name);
							String file2Name = destFilePath;
							System.out.println("file1Name>>>>>two>>>>>>>>>>>>"+file2Name);

							FileUpload.addToZipFile(file1Name, zos);
							FileUpload.addToZipFile(file2Name, zos);
							
							zos.close();
							fos.close();
					
						
							

				           
				            
				            
							
							/* Uploading File On Cloud (Starts)*/
							String ftpserver = "ftp.drivehq.com";
					        String ftpusername = "ehrblockchain";
					        String ftppassword = "*India123";
					        totalNumberOfClouds = adminDAO.getTotalNumberOfClouds();
					        
					        /* Getting Random Number Between 1 to n (Starts) */
					        	int START = 1;
					        	int END = totalNumberOfClouds;
					        	ArrayList list = GenerateRandomNubberBetween1ton.gererateRandomNumberBetween1_to_n(START, END);
					        	System.out.println("**************** Random Number Info ****************");
					        	System.out.println(list);
					        	System.out.println("**************** Random Number Info ****************");
					        /* Getting Random Number Between 1 to n (Ends) */
					        	
					        cloudId = Integer.parseInt(list.get(totalNumberOfClouds-1).toString());
					        StringBuffer sb = new StringBuffer("File Uploaded On : ");
					        
					        for(int i=1;i<=totalNumberOfClouds;i++)
					        {
					        	if(cloudId == i)
						        {
						        	dirToUploadFile = "cloud"+i;
			        				sb.append(dirToUploadFile);
						        }
					        }
					        
					        
		    				//FileUpload.upload(ftpserver,ftpusername,ftppassword,filename,file1,dirToUploadFile);
		    				
		    				FileUpload.upload(ftpserver,ftpusername,ftppassword,filename,newFile,dirToUploadFile);
		    				
		    				
		    				
		    				
			        	
							/* Uploading File On Cloud (Ends)*/
			        		String s= sb.toString();
			        		String a[] =s.split(":");
			        		String uploadedClouds = a[1];
			        		String clouds[] = uploadedClouds.split(",");
			        		
			        		System.out.println("7777777777777777777777777777777");
			        		System.out.println("Uploaded Clouds :");
			        		System.out.println(Arrays.toString(clouds));
			        		
			        		//String url="ftp://dhsinformatics.com/";
			        		
			        		String url = "ftp.drivehq.com";
							/* Adding the upload Transaction details (Start)*/
							
							
							
							
							date = date + "  " + time;
							ownerId = adminDAO.getDataOwnerId(username);
							
							
							
					
					flag = adminDAO.addUploadTransaction(fileid,filename,fileType,date,uploadSubject,cloudId,ownerId,previousblkvale,hashtag,zipblkame,randomNumber);
					
					
					flag = true;
				
					
					/* Displaying Success Message  */
					if(flag)
					{
						//rd=req.getRequestDispatcher("/Resources/JSP/Admin/select_file.jsp?no=1&file_name="+filename+"&cloud="+sb.toString()+"");
						rd=req.getRequestDispatcher("/Resources/JSP/Admin/select_file.jsp?no=1&file_name="+filename+"&cloud="+sb.toString()+"");
						rd.forward(req,res);
					}
					else
					{
						//rd=req.getRequestDispatcher("/Resources/JSP/User/uploadSupport_file.jsp?no=3");
						rd=req.getRequestDispatcher("/Resources/JSP/Admin/select_file.jsp?no=0&no1=1");
						rd.forward(req,res);
					}
					
				}
				catch(Exception e)
				{
					System.out.println("\n ******** Exception In FTPUpload Servlet : \n");
					e.printStackTrace();
				}
		
	}}
}
