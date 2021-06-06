
<%@page import="com.DAO.AdminDAO"%>
<%@page import="com.util.Utility"%><html>
<head>
<title>Generate Key</title>

	<link href="<%=request.getContextPath() %>/Resources/CSS/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/Resources/CSS/message.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/Resources/CSS/button.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/Resources/CSS/login.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/Resources/JS/style.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/Resources/JS/pagination.js"></script>
</head>

<body onload="startTimer()">

<%
	AdminDAO adminDAO = AdminDAO.getInstance();
    String date = adminDAO.getLastKeyUpdatedDate();
%>

<div id="a3" align="right" style="position:absolute;top:50px;left:50px">
						
		 <p>
			
					
				<form action="<%=request.getContextPath()%>/GenerateKey" method="post">
					
					<table id="login" align="center">
					<tr>
							<td colspan="7" align="center">
								<font color="#000000" style="font-style: bold" size="5">Generate Key</font>
							</td>
					</tr>
					
					<tr>
							<td colspan="3" align="center"><hr></hr></td>
					</tr>
					
					<tr>
							<td>&nbsp;</td>
					</tr>
					
					<tr align="center">
							<td width="200px">Key Updated On </td>
							<td width="75px">:</td>
							<td width="200px">
								<%=date %>
							</td>
							
					</tr>
					
					<tr align="center">
							<td>&nbsp;</td>
					</tr>
					
					<tr align="center">
							<td colspan="7" align="center">
								<input class="button" type="submit"  value="Update Key"></input>
							</td>
					</tr>
					
					</table>
			</form>
			
		</p>
						
</div>

<%
	if(Utility.parse(request.getParameter("no"))==1)
	{
		%>
			<div class="success" id="message" style="position:absolute;top:5px;left:50px">	
				<p>Key Updated Successfully....</p>
			</div>
		<%
	}

	if(Utility.parse(request.getParameter("no"))==2)
	{
		%>
			<div class="error" id="message" style="position:absolute;top:5px;left:50px">	
				<p>Opps,Something Went Wrong,Try It Again...</p>
			</div>
		<%
	}
%>

</body>
</html>