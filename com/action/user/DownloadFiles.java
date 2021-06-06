/**
 @Author : Munna Kumar Singh
 Date : Aug 25, 2012
 File : DownloadFiles.java
 Package : com.action.admin
*/
package com.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.AdminDAO;
import com.DAO.UserDAO;

public class DownloadFiles extends HttpServlet 
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		PrintWriter out=resp.getWriter();
		ResultSet rs = null;
		RequestDispatcher rd=null;
		
		UserDAO userDAO = UserDAO.getInstance();
		AdminDAO adminDAO = AdminDAO.getInstance();
		
		String username = "";
		int userid = 0;
		HttpSession session = null;
	
		session = req.getSession();

	    if(session != null)
	    {
	    	username = session.getAttribute("username").toString();
	    }
		
	    userid = userDAO.getID(username);
		
		try 
		{
			rs = adminDAO.getUploadFiles();
			
			if(rs != null)
			{
				req.setAttribute("rs", rs);
				rd=req.getRequestDispatcher("/Resources/JSP/User/download_file.jsp?no=0");
				rd.forward(req, resp);
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Opps,Error in DownloadFiles Servlet : ");
			e.printStackTrace();
		}
		
		
		
	}
}
