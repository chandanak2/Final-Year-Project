package com.action.auditor;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.CommonDAO;



public class AuditRequest extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException
			{
		HttpSession session = request.getSession();
		String name=request.getParameter("name");
		System.out.println("user code>>>>>>>>>>>>>>>>>>>>"+name);
		ResultSet rs=CommonDAO.getFilesByGrpName(name);
		try 
		{
			if(rs.next())
			{
				
				rs=CommonDAO.getFilesByGrpName(name);
				request.setAttribute("rs", rs);
				request.setAttribute("name", name);
				session.setAttribute("name", name);
			    RequestDispatcher rd=request.getRequestDispatcher("/Resources/JSP/Auditor/auditrequest.jsp");
				try 
				{
					rd.forward(request, response);
				} 
				catch (ServletException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
