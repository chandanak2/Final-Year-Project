
package com.action.user;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.AdminDAO;
import com.DAO.UserDAO;
import com.util.DesFileEncrypter_Decrypter;
import com.util.ReadLastLineOfFile;
import com.util.ReadnRemoveASpecificLineOfAFile;
import com.util.RemoveLineFromFile;
import com.util.SerializeToDatabase;
import com.util.Utility;

public class VerifyKey extends HttpServlet
{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		
		
		System.out.println("Its Came inside the servlet");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = null;
		ResultSet rs = null;
		String srcFilePath = "",destFilePath="";
		int fileId=0;
		int userId=0,deptCode1=0,deptCode2=0,designCode1=0,designCode2=0;
		String keyInfo = "";
		String username = "";
		
		  int deptId = 0;
		  int designationId = 0;
		  String transactionStatus = "";
		
		AdminDAO adminDAO = AdminDAO.getInstance();
		UserDAO userDAO = UserDAO.getInstance();
		
		HttpSession session = request.getSession();  
	    username = (String) session.getAttribute("username");
	    System.out.println("username >>>>>>>>>>>>>>>>>>>>>>>>>"+username);
		userId = userDAO.getID(username);
		System.out.println("userid>>>>>>>>>>>>>>>>"+userId);
		deptId = userDAO.getDepartmentID(username);
		System.out.println("deptId>>>>>>>>>>>>>>>>>"+deptId);
    	designationId = userDAO.getDesignationID(username);
    	System.out.println("designationId>>>>>>>>>>>>>>>>>"+designationId);
		
		boolean flag = false,flag1 = false;
		
		try 
		{
			//fileId = Integer.parseInt(request.getParameter("fileid").toString());
			
			fileId=(Integer)session.getAttribute("fileId");
			System.out.println("fileId is>>>>>>>>>>"+fileId);
			srcFilePath = request.getRealPath("") +"\\Files\\ReadKey\\privateKey.txt";
			System.out.println(">>>>>>srcFilePath>>>>>>>>>>>>>>>>>>>>>>>>"+srcFilePath);
        	destFilePath = request.getRealPath("") +"\\Files\\ReadKey\\d_privateKey.txt";
        	System.out.println(">destFilePath>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+destFilePath);
        	
        	/* Decrypting The Key Information(Starts) */
				SecretKey key = (SecretKey) SerializeToDatabase.getDESsecreteKey();
				System.out.println("key is>>>>>>>>>>>>>>"+key);
			    DesFileEncrypter_Decrypter encrypter = new DesFileEncrypter_Decrypter(key);
			    System.out.println("encrypter>>>>>>>>> "+encrypter);
				encrypter.decrypt(new FileInputStream(srcFilePath), new FileOutputStream(destFilePath));
			/* Decrypting The Key Information(Ends) */
			
		    //keyInfo = ReadLastLineOfFile.readLastLineOfAFile(destFilePath);
		    keyInfo = ReadnRemoveASpecificLineOfAFile.readSpecificLine(destFilePath);
		    
		   
		    System.out.println("Key Info >>>>>>>>>>>>>>>>>>>>: " + keyInfo );
		    System.out.println();
	        //secreteKeyInfo = userId+"-"+deptCode+"-"+designCode;
		    
		    String[] info = keyInfo.split("-");
		    
		    if(info.length == 5)
		    {
		    	userId = Integer.parseInt(info[0]);
		    	deptCode1 = Integer.parseInt(info[1]);
		    	deptCode2 = Integer.parseInt(info[2]);
		    	designCode1 = Integer.parseInt(info[3]);
		    	designCode2 = Integer.parseInt(info[4]);
		    }
		    
		    System.out.println("******* Veryfication Info **************");
		    System.out.println("       User Id : " + userId);
		    System.out.println("       Dept Id1 : " + deptCode1);
		    System.out.println("       Dept Id2 : " + deptCode2);
		    System.out.println("Designation Id1 : " + designCode1);
		    System.out.println("Designation Id2 : " + designCode2);
		    System.out.println("File Id : " + fileId);
		    
		    flag = adminDAO.downloadAuthentication1(fileId, deptCode1, designCode1);
		    flag1 = adminDAO.downloadAuthentication1(fileId, deptCode2, designCode2);
		    
		    if(flag || flag1)
		    {
		    	  response.sendRedirect(request.getContextPath()+"/Download?fileid="+fileId+"");
		    }
		    else
		    {
		    	rs = adminDAO.getUploadFiles();
		    	
		    	/* Adding Download Transaction To Database(Starts)*/
            	
            	transactionStatus = "Denied";
            	userDAO.addDownloadTransaction(Utility.getDate(),Utility.getTime(),userId,fileId,deptId,designationId, transactionStatus);
        	
               /* Adding Download Transaction To Database(Ends)*/	
				
				if(rs != null)
				{
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/Resources/JSP/User/download_file.jsp?no=0&no1=1");
					rd.forward(request,response);
				}
		    }
		    
	       
		}
		catch (Exception e) 
		{
			System.out.println("Opps,Exception In VerifyKey :");
			e.printStackTrace();
		}
		
		
		
	}
}
