<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.DAO.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
window.history.forward();
function noBack() { window.history.forward(); }
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Home</title>

     <script type="text/javascript" src="<%=request.getContextPath() %>/Resources/JS/jquery-1.2.1.min.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath() %>/Resources/JS/menu.js"></script>
     <link href="<%=request.getContextPath() %>/Resources/CSS/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/Resources/CSS/message.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/Resources/CSS/button.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/Resources/CSS/login.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath() %>/Resources/CSS/style1.css" rel="stylesheet" type="text/css" />
 <style type="text/css">
   li a {display:inline-block;}
   li a {display:block;}
   </style>
</head>
<body>

<%!
       HttpSession session = null;
       String username = "";
       int id = 0;
       UserDAO userDAO = null;
       
%>
<%
      session = request.getSession();

       if(session != null)
       {
    	   username = session.getAttribute("username").toString();
       }
       
      userDAO = UserDAO.getInstance();
      
      id = userDAO.getID(username);
       
%>

<img alt="" src="<%=request.getContextPath()%>/img/ehrtitle.png">
</br></br>
<div style="">
      <table width="100%" height="100%">				
			<tr valign="top" >
				<td align="left" width="20%">
				<ul id="menu">
		<li>
			<a href="#">Profile</a>
			<ul>
				<li><a href="<%=request.getContextPath()%>/Profile" target="myIframe">View Profile</a></li>
				<li><a href="<%=request.getContextPath() %>/EditProfile?username=<%=username %>&no=1" target="myIframe">Edit Profile</a></li>
				<li><a href="<%=request.getContextPath() %>/ChangePass?username=<%=username %>&no=1&id=<%=id %>" target="myIframe" target="target">Change Password</a></li>
				
			</ul>
		</li>
		
		
		<%-- <li>
			<a href="#">Cloud Server</a>
			<ul>
				<li><a href="<%=request.getContextPath()%>/ServerList?submit=get" target="myIframe">View Server Details</a></li>
				<li><a href="<%=request.getContextPath()%>/ServerList?submit=Add" target="myIframe">Add Server Details</a></li>
				<li><a href="<%=request.getContextPath()%>/ServerList?submit=Edit" target="myIframe">Update Server Details</a></li>
				
			</ul>
		</li> --%>
		
		
		
		<li>
			<a href="#">Auditor List</a>
			<ul>
				<li><a href="<%=request.getContextPath()%>/ListAuditor?submit=get" target="myIframe">View Auditor Details</a></li>
				<li><a href="<%=request.getContextPath()%>/ListAuditor?submit=Add" target="myIframe">Add Auditor Details</a></li>
				<li><a href="<%=request.getContextPath()%>/ListAuditor?submit=Edit" target="myIframe">Update Auditor Details</a></li>
			</ul>
		</li>
		
		<li>
			<a href="#">Doctor</a>
			<ul>
				<li><a href="<%=request.getContextPath()%>/ListDataOwners?submit=get" target="myIframe">View Doctor Details</a></li>
				<li><a href="<%=request.getContextPath()%>/ListDataOwners?submit=Add" target="myIframe">Add Doctor Details</a></li>
				<li><a href="<%=request.getContextPath()%>/ListDataOwners?submit=Edit" target="myIframe">Update Doctor Details</a></li>
			</ul>
		</li>
		
		
		
		
	<%-- <li>
			<a href="#">Key Generation</a>
			<ul>
				<li><a href="<%=request.getContextPath()%>/Resources/JSP/Admin/generate_key.jsp" target="myIframe">Generation Key</a></li>
		   </ul>
		</li>--%>
		
		
		<li>
			<a href="#">Department</a>
			<ul>
				<li><a href="<%=request.getContextPath()%>/DepartmentList?submit=get" target="myIframe">View Department</a></li>
			</ul>
		</li>
		
		<li>
			<a href="#">Designation</a>
			<ul>
				<li><a href="<%=request.getContextPath()%>/DesignationList?submit=get" target="myIframe">View Designation</a></li>
			</ul>
		</li>
		
		
		<li>
			<a href="#">Logout</a>
			<ul>
				<li><a href="<%=request.getContextPath()%>/Logout">Sign Out</a></li>
			</ul>
		</li>
		
	</ul>
				</td>
			</tr>
		</table>
</div>

<div style="position:absolute;top:120px;left:230px;">
	<iframe align="middle" frameborder="0" scrolling="auto" name="myIframe" height="600" width="900"></iframe>
</div>	
</body>
</html>