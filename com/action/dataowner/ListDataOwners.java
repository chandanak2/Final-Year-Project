/**
 * 
 */
package com.action.dataowner;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.AdminDAO;
import com.util.Utility;

/**
 * @author Priyanka
 */
public class ListDataOwners extends HttpServlet 
{
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		PrintWriter out=response.getWriter();
		ResultSet rs = null;
		RequestDispatcher rd=null;
		HttpSession session = null;
		
		try
		{
			String submit=request.getParameter("submit").trim();
			
			AdminDAO adminDAO = AdminDAO.getInstance();
			rs = adminDAO.getDataOwners();
			
			if(submit.equals("get"))
			{
				request.setAttribute("rs", rs);
				rd=request.getRequestDispatcher("/Resources/JSP/DataOwner/owners.jsp?no=1");
				rd.forward(request, response);
			}
			if(submit.equals("Add"))
			{
				if(Utility.parse1(request.getParameter("add1")).equals("YES"))
				{
					String username = request.getParameter("username");
					String password= request.getParameter("password");
					String name = request.getParameter("name");
					String address = request.getParameter("address");
					String email = request.getParameter("email");
					String phone = request.getParameter("phone");
					
					boolean result=adminDAO.checkOwner(username, password);
					if(!result)
					{
						session = request.getSession();
						
						 if( session.getAttribute( "waitPage" ) == null ) 
					     {  
					    	   session.setAttribute( "waitPage", Boolean.TRUE );  
					    	   out.println( "<html><head>" );  
					    	   out.println( "<title>Please Wait...</title>" );  
					       	   out.println( "<meta http-equiv=\"Refresh\" content=\"0\">" );  
					    	   out.println( "</head><body bgcolor=''>" );
					    	   out.println("<center>");
					    	   out.println("<font color='green' size='5'>");
					    	   out.println( "Wait Please,Processing Your Request......." );
					    	   out.println( "<br>" );
					    	   out.print( "<img src='Resources/Images/status_processing.gif'></img><br><br>");
					    	   out.println("<font color='red' size='5'>");
					    	   out.println( "Please Do not press Back or Refresh button.......<br>  " );
					    	   out.println( "Please wait....</h1>");  
					    	   out.println("</center>");
					    	   out.close();  
					     }
						 else
						 {
							 session.removeAttribute( "waitPage" ); 
							 result = adminDAO.addDataOwnerDetails(username, password, name, address, email, phone);
							 if(result)
							 {
								 rs = adminDAO.getDataOwners();
								 request.setAttribute("rs", rs);
								 rd=request.getRequestDispatcher("/Resources/JSP/DataOwner/owners.jsp?no=1&no1=1");
								 rd.forward(request, response);
							 }
							else
							{
								rd=request.getRequestDispatcher("/Resources/JSP/DataOwner/owners.jsp?no=2&no1=2");
								rd.forward(request, response);
							}
							 
						 }
						
					}
					else
					{
						rd=request.getRequestDispatcher("/Resources/JSP/DataOwner/owners.jsp?no=2&no1=1");
						rd.forward(request, response);
					}
				}
				else
				{
					rd=request.getRequestDispatcher("/Resources/JSP/DataOwner/owners.jsp?no=2");
					rd.forward(request, response);
				}
			}
			
			if(submit.equals("Edit"))
			{
				String []chk=request.getParameterValues("chk");
				if(Utility.parse1(request.getParameter("edit1")).equals("YES"))
				{
					String s[]=new String[5];
					s[0]=request.getParameter("id");
					s[1]=request.getParameter("name");
					s[2]=request.getParameter("address");
					s[3]=request.getParameter("phone");
					s[4]=request.getParameter("email");
					
					adminDAO.editDataOwnerDetails(s);
					
					rs = adminDAO.getDataOwners();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/Resources/JSP/DataOwner/owners.jsp?no=1&no1=2");
					rd.forward(request, response);
				}
				else if(chk==null)
				{
					rs = adminDAO.getDataOwners();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/Resources/JSP/DataOwner/owners.jsp?no=1&no1=3");
					rd.forward(request, response);
				}
				else if(chk.length!=1)
				{
					rs = adminDAO.getDataOwners();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/Resources/JSP/DataOwner/owners.jsp?no=1&no1=4");
					rd.forward(request, response);
				}
				else if(chk.length==1)
				{
					rs = adminDAO.getSpecificDataOwnerDetails(Integer.parseInt(chk[0]));
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/Resources/JSP/DataOwner/owners.jsp?no=3");
					rd.forward(request, response);
				}
			}
			
			if(submit.equals("Delete"))
			{
				String []chk=request.getParameterValues("chk");
				if(chk==null)
				{
					rs = adminDAO.getDataOwners();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/Resources/JSP/DataOwner/owners.jsp?no=1&no1=3");
					rd.forward(request, response);
				}
				else
				{
					for(int i=0;i<chk.length;i++)
					{
						adminDAO.deleteDataOwnerDetails(Integer.parseInt(chk[i]));
					}
					rs = adminDAO.getDataOwners();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/Resources/JSP/DataOwner/owners.jsp?no=1&no1=5");
					rd.forward(request, response);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Opps's Error is in Data Owner==>ListDataOwners Servlet : ");
			e.printStackTrace();
			out.println("Opps's Error is in Data Owner==>ListDataOwners Servlet......"+e);
		}
	}
}
