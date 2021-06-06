package com.action.auditor;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.DAO.AdminDAO;
import com.mysql.jdbc.Connection;

public class AuditAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String name, url;
	String filename="";
	Connection con=null;
	public void doPost(HttpServletRequest req, HttpServletResponse res) {

		request = req;
		response = res;
		HttpSession session = request.getSession();
		String userId = session.getAttribute("userid").toString();
		System.out.println("userid>>>>>>>>>>>>>>>>>>>>>>"+userId);
		name = session.getAttribute("name").toString();
		System.out.println("name is >>>>>>>>>>>>>>>>>>>"+name);
		String[] chosenFileId = request.getParameterValues("chk");
		System.out.println(">>>>>>>>>>chosenFileId>>>>>>>>>>>>>"+chosenFileId);
		
		boolean exists = AdminDAO.checkRequest(chosenFileId, userId);
		if (!exists) {
			/*DAOFactory factory = new DAOFactory();*/
			//DAO dao = factory.getInstance("User");
			try
			{
			Class.forName("com.mysql.jdbc.Driver");
			 con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db_blockchain_ehr_test","root","admin");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from m_transaction where u_code='"+userId+"'");
			while(rs.next())
			{
				filename=rs.getString(5);
			}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("-------,,,,<<<<>>>><<>>>><<>>"+filename);
			
			boolean updated = AdminDAO.updateRequest(chosenFileId, userId, filename);
			System.out.println("REquest Update status :"+updated);
			if (updated) 
			{
				boolean flag=AdminDAO.updatestatus(chosenFileId);
				 System.out.println("m_trans table status aftyer request==========>:"+flag);
			//	url = "/Files/JSP/Auditor/auditrequest.jsp?no=1";
				 RequestDispatcher rd=req.getRequestDispatcher("/Resources/JSP/Auditor/auditrequest.jsp?no=1");
				 try {
					rd.forward(req, res);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				url = "/Files/JSP/Auditor/auditrequest.jsp?no=2";
			}
		}
		else
		{
			url = "/Files/JSP/Auditor/auditrequest.jsp?no=3";
		}
		/*try {
			redirect();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	/*private void redirect() throws IOException {
		ResultSet rs = CommonDAO.getFilesByGrpName(CommonDAO.getGroupName(name), "Upload");
		try {
			if (rs.next()) {

				rs = CommonDAO.getFilesByGrpName(CommonDAO.getGroupName(name),
						"Upload");
				request.setAttribute("rs", rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher(url);
		try {
			rd.forward(request, response);
		}catch(ServletException e){ e.printStackTrace();} 
	}*/

}
