<%@page import="java.sql.ResultSet"%>
<%@ page import="com.util.Utility"%>
<%@ page import="com.DAO.AdminDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>audit</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/Files/CSS/message.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath() %>/Files/CSS/audit.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/Files/JS/style.js"></script>
</head>
<%
	ResultSet rs = (ResultSet) Utility.parse2(request
			.getAttribute("rs"));
String filename ="";
%>
<body>
<form action="<%=request.getContextPath()%>/AuditAction" method="post">
	<table id="results" class="tab" style="width: 500; color: FFFFCC" cellspacing="5">
		<tr>
			<th>Select</th>
			<th>File Code</th>

		</tr>

		<%
			if (rs != null)
				while (rs.next()) {
		%>
		<tr>
			<td><input name="chk" type="checkbox" value="<%=rs.getInt(1)%>"></td>
			<td><%=rs.getString(1)%></td>
		</tr>
		<%
			}
		%>
		
		
		
		
	</table>
	<input id="send" type="image" src="<%=request.getContextPath()%>/Files/Images/send.png" />
</form>

<%
	if(Utility.parse(request.getParameter("no"))==1)
    {%>
    		
    	   <script type="text/javascript">

   alert(' Request Sent Successfully.....!');

   </script>
    		
    <%}
	if(Utility.parse(request.getParameter("no"))== 2)
	{%>
		<div class="error" id="message" style="height: 50;width: 250;">	
    		<p>OOPS!! Something went wrong try again later</p>
    	</div>			
	<%} if(Utility.parse(request.getParameter("no"))== 3)
	{%>
		<div class="error" id="message" style="height: 50;width: 250;">	
    		<p>Request already exists</p>
    	</div>			
	<%} %>

</body>
</html>