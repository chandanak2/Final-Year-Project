
<%@page import="com.DAO.AdminDAO"%>
<%@ page import="java.sql.*"%>
<html>
<%
	//DAOFactory factory=new DAOFactory();
	//DAO dao=factory.getInstance("Auditor");
		AdminDAO adminDAO = AdminDAO.getInstance();
	ResultSet rs=adminDAO.getUsers1();
	
%>
<head>

<link rel="stylesheet"
	href="<%=request.getContextPath() %>/Files/CSS/button.css"
	type="text/css" />
</head>
<body>
<form action="<%=request.getContextPath()%>/GetFiles" target="v2">
		<select name="name" style="width:80;height: 30;color: #663300; background-color: #BCDCAB ">
		<option>Select</option>
			<%
				while (rs.next()) {
			%>
			
			<option value="<%=rs.getString(3)%>"><%=rs.getString(3)%> </option>
			<%
				}
			%>
		</select>

<input type="submit" name="submit" value="Ok" class="button_example">

	</form>	
</body>
</html>