/**
 * 
 */
package com.action.auditor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class Verification extends HttpServlet 
{
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		PrintWriter out = response.getWriter();
		try
		{
			String task=request.getParameter("task");
			System.out.println("Auditors task is  "+task);
			//DAOFactory factory=new DAOFactory();
			//DAO dao=factory.getInstance("Auditor");
			//ResultSet rs=null;
			if(task.equals("get"))
			{
				/*rs = dao.getUsers();
				int count=0;
				while(rs.next())
					count++;
				rs = dao.getUsers();
				request.setAttribute("rs", rs);*/
				RequestDispatcher rd=null;
			
				rd=request.getRequestDispatcher("/Resources/JSP/Auditor/verify.jsp");
				rd.forward(request, response);
			}
		}
		catch(Exception e)
		{
			System.out.println("Opps's Error is in User Verification Servlet......"+e);
			out.println("Opps's Error is in User Verification Servlet......"+e);
		}
	}
}
