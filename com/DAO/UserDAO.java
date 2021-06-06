
package com.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.DAO.*;

public class UserDAO 
{
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static UserDAO userDAO=null;
	private UserDAO()
	{
		
	}
	public static UserDAO getInstance()
	{
		if(userDAO == null)
		{
			userDAO= new UserDAO();
		}
		return userDAO;
	}
	public boolean checkUser(String username,String password)
	{
		boolean flag=false;
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_user where username='"+username+"' and password='"+password+"'");
			while(resultSet.next())
			{
				flag=true;
			}
			System.out.println("User Login Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->checkUser(String userid,String pass): "+ e);
		}
		return flag;
	}
	
	/* Get Profile Details (Starts) */
	
	public ResultSet getProfile(String username)
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_user where username='"+username+"'");
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getProfile()....."+e);
		}
		return resultSet;
	}
	
	/* Get Profile Details (Ends) */
	
	public int getID(String username)
	{
		int i=0;
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select u_code from m_user where username='"+username+"'");
			while(resultSet.next())
			{
				i=resultSet.getInt(1);
			}
			System.out.println("User ID is : "+i);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getID(String userid): ");
			e.printStackTrace();
		}
		return i;
	}
	
	
	public int getDepartmentID(String username)
	{
		int i=0;
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select dept_code from m_user where username='"+username+"'");
			while(resultSet.next())
			{
				i=resultSet.getInt(1);
			}
			System.out.println("User ID is : "+i);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getDepartmentID(String username): ");
			e.printStackTrace();
		}
		return i;
	}
	
	
	public int getDesignationID(String username)
	{
		int i=0;
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select design_code from m_user where username='"+username+"'");
			while(resultSet.next())
			{
				i=resultSet.getInt(1);
			}
			System.out.println("User ID is : "+i);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getDesignationID(String username): ");
			e.printStackTrace();
		}
		return i;
	}
	
	
	public String getUsername(String userName)
	{
		String username = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select u_name from m_user where username='"+userName+"'");
			while(resultSet.next())
			{
				username=resultSet.getString(1);
			}
			System.out.println("User Name : "+username);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getUser(int userId): ");
			e.printStackTrace();
		}
		return username;
	}
	
	
	public boolean editProfile(String [] s) 
	{
		boolean flag=false;
		String sql = "";
		try
		{   
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "update m_user set u_name='"+s[1]+"',u_email='"+s[2]+"',u_phone='"+s[3]+"',u_address='"+s[4]+"' where u_code='"+s[0]+"'";
			System.out.println("******* Edit User Profile Info *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("User Edit Profile Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-editProfile() :");
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean chnagePass(int id,String cpass) 
	{
		boolean flag=false;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "update m_user set password='"+cpass+"' where u_code='"+id+"'";
			System.out.println("******* Change User Password Info *********\n");
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("User Change Pass Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-chnagePass() : ");
			e.printStackTrace();
		}
		return flag;
	}
	
	

	
/* Add Download Transaction (Starts) */
	
	public boolean addDownloadTransaction(String date,String time,int userId,int fileId,int deptId,int designationId,String transactionStatus) 
	{
		boolean flag=false;
		String sql="";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql="insert into m_transaction(t_date,t_time,u_code,f_code,dept_code,design_code,t_status) values('"+date+"','"+time+"','"+userId+"','"+fileId+"','"+deptId+"','"+designationId+"','"+transactionStatus+"')";
			System.out.println(sql);
			int i=statement.executeUpdate(sql);
			if(i!=0)
			{
				flag=true;
			}
			System.out.println("Download Transaction Status : "+flag);
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-addDownloadTransaction(): ");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Add Download Transaction (Ends) */
	
/* Get Download Transaction(Starts) */

	public ResultSet getDownloadTransaction(int userId)
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_transaction where u_code='"+userId+"'");
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getDownloadTransaction() : ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Download Transaction(Ends) */
	
	
	public String getFileName(int fileId)
	{
		String fileName = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select f_name from m_file_Upload where f_code='"+fileId+"'");
			while(resultSet.next())
			{
				fileName=resultSet.getString(1);
			}
			System.out.println("File Name : "+fileName);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getFileName(int fileId): ");
			e.printStackTrace();
		}
		return fileName;
	}
	
/* Getting Number of clouds required(Starts) */
	public int getCloud()
	{
		int i=0;
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select f_no_of_clouds from m_fault_tolerance");
			while(resultSet.next())
			{
				i=resultSet.getInt(1);
			}
			System.out.println("Number of Clouds Required : "+i);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getCloud(): ");
			e.printStackTrace();
		}
		return i;
	}
/* Getting Number of clouds required(Ends) */
	
/* Getting Cloud Status(Starts) */
	public String getCloudStatus(String cloudUrl)
	{
		String status = "Inactive";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			System.out.println("******* Cloud Status Information *******");
			resultSet = statement.executeQuery("select c_status from m_cloud where c_url='"+cloudUrl+"'");
			while(resultSet.next())
			{
				status=resultSet.getString(1);
			}
			System.out.println("Cloud "+cloudUrl +" Status : "+status);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getCloudStatus(String cloudUrl): ");
			e.printStackTrace();
		}
		return status;
	}
	/* Getting Cloud Status(Starts) */

/* Get Files To Download (Starts) */

	public ResultSet getFilesToDownload(int userId)
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_upload where m_id='"+userId+"'");
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getFiles() : ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Files To Download (Ends) */
	
/* Get Download Transactions (Starts) */

	public ResultSet getUserDownloadTransaction(int userId)
	{
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_download where m_id='"+userId+"'");
		}
		catch(Exception e)
		{
			System.out.println("Opp's Error is in UserDAO-getUserDownloadTransaction(int userId) : ");
			e.printStackTrace();
		}
		return resultSet;
	}
	
/* Get Download Transactions (Starts) */
	
	
/* Update Download Message Digest (Starts)*/
	
	public boolean updateDownloadMessageDigest(String fileName,String dMsgDigest)
	{
		boolean flag = false;
		int i = 0;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql="update m_download set d_messagedigest='"+dMsgDigest+"' where d_filename='"+fileName+"'";
			i = statement.executeUpdate(sql);
			
			if(i>0)
			{
				flag = true;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->updateDownloadMessageDigest(): ");
			e.printStackTrace();
		}
		return flag;
	}

/* Update Download Message Digest (Ends)*/
	
/* Get Upload Message Digest (Starts)*/
	public String getUploadMsgDigest(String fileName)
	{
		String msgDigest = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select m_messagedigest from m_upload where u_filename='"+fileName+"'");
			while(resultSet.next())
			{
				msgDigest=resultSet.getString(1);
			}
			System.out.println("Upload Msg Digest : "+msgDigest);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getUploadMsgDigest(String fileName): ");
			e.printStackTrace();
		}
		return msgDigest;
	}
	
/* Get Upload Message Digest (Ends)*/
	
/* Get Download Message Digest (Starts)*/
	public String getDownloadMsgDigest(String fileName)
	{
		String msgDigest = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select d_messagedigest from m_download where d_filename='"+fileName+"'");
			while(resultSet.next())
			{
				msgDigest=resultSet.getString(1);
			}
			System.out.println("Download Msg Digest : "+msgDigest);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getDownloadMsgDigest(String fileName): ");
			e.printStackTrace();
		}
		return msgDigest;
	}
	
/* Get Download Message Digest (Ends)*/

/* Update Download Integrity Status (Starts)*/
	
	public boolean updateDownloadIntegrityStatus(String fileName,String integrityConstraint)
	{
		boolean flag = false;
		int i = 0;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql="update m_download set integrity_status='"+integrityConstraint+"' where d_filename='"+fileName+"'";
			i = statement.executeUpdate(sql);
			
			if(i>0)
			{
				flag = true;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->updateDownloadIntegrityStatus(): ");
			e.printStackTrace();
		}
		return flag;
	}
	
/* Update Download Integrity Status (Ends)*/
	
/* Add Upload Transactions (Starts)*/
	
	public boolean addUploadConstraints(int uploadId,int cloudId)
	{
		boolean flag = false;
		int i = 0;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql="insert into m_transactions (u_id,c_id) values('"+uploadId+"','"+cloudId+"')";
			i = statement.executeUpdate(sql);
			
			if(i>0)
			{
				flag = true;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->addUploadTransactions(int uploadId,int cloudId): ");
			e.printStackTrace();
		}
		return flag;
	}
/* Add Upload Transactions (Ends)*/	
	
/* Get Max Upload ID (Starts)*/	
	
	public int getMaxUploadID()
	{
		int i=0;
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select max(u_id) from m_upload");
			while(resultSet.next())
			{
				i=resultSet.getInt(1);
			}
			System.out.println("Max Upload ID is : "+i);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO--> getMaxUploadID(): ");
			e.printStackTrace();
		}
		return i;
	}
	
/* Get Max Upload ID (Ends)*/	
	
/* Get Cloud ID (Starts)*/	
	
	public int getCloudID(String url)
	{
		int i=0;
		String sql = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "select c_id from m_cloud where c_url='"+url+"'";
			System.out.println("******************** Get Cloud Ids Info ********************");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				i=resultSet.getInt(1);
			}
			System.out.println("Cloud ID is : "+i);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO--> getCloudID(String url): ");
			e.printStackTrace();
		}
		return i;
	}
	
/* Get Cloud  ID (Ends)*/	
	
/* Get Cloud ID using UploadId (Starts)*/	
	
	public ArrayList getCloudIDByUploadId(int uploadId)
	{
		int i=0;
		String sql = "";
		ArrayList list = new ArrayList();
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			sql = "select c_id from m_transactions where u_id='"+uploadId+"'";
			System.out.println("******************** Get Cloud Ids By Using Upload ID ********************");
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				i=resultSet.getInt(1);
				list.add(i);
			}
			System.out.println("Cloud IDs are : "+list);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO--> getCloudIDByUploadId(String uploadId): ");
			e.printStackTrace();
		}
		return list;
	}
	
/* Get Cloud ID using UploadId (Ends)*/		
	
/* Getting Clouds Status By Id (Starts)*/	
	
	public String getCloudStatusById(int cloudId)
	{
		String status = "";
		try
		{
			DAO dao=DAO.getInstance();
			connection=dao.connector();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select c_status from m_cloud where c_id='"+cloudId+"'");
			while(resultSet.next())
			{
				status=resultSet.getString(1);
			}
			System.out.println("Cloud Status : "+status);
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserDAO-->getCloudStatusById(int cloudId): ");
			e.printStackTrace();
		}
		return status;
	}
/* Getting Clouds Status By Id (Ends)*/	
	
}
