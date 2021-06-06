<%@page import="com.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.DAO.AdminDAO"%><html>
<head>
	<link href="<%=request.getContextPath() %>/Resources/CSS/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/Resources/CSS/message.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/Resources/CSS/button.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/Resources/CSS/login.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/Resources/JS/style.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/Resources/JS/pagination.js"></script>

</head>
<body onload="startTimer()">
<%
    AdminDAO adminDAO = null;
    adminDAO = AdminDAO.getInstance();
    int no=Utility.parse(request.getParameter("no"));
    ResultSet rs=(ResultSet)request.getAttribute("rs");
    ResultSet rs1 = null;
    ResultSet rs2 = null;
    int id=0,deptId=0,designId=0;
	String username="",userid="",password="",city="",mobile="",phone="",address="",email="";
	if(no==1)
	{
%>	

<form action="<%=request.getContextPath()%>/ListAuditor">
<div align="right" style="position:absolute;top:20px;left:450px">
	<input type="submit" name="submit" value="Add" class="gradientbuttons"/>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="submit" name="submit" value="Edit" class="gradientbuttons"/>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="submit" name="submit" value="Delete" class="gradientbuttons"/>&nbsp;&nbsp;&nbsp;&nbsp;
</div>

<div id="a3" align="right" style="position:absolute;top:60px;left:2px;width: 660px;height: 350px;">
			
<p>

	<table id="results" class="tab" border="1" cellpadding="5px" cellspacing="4px" width="660px">
		
		<tr>
			
			<td colspan="6" align="center">
			<font color="#000000" style="font-style: bold" size="5">Data Owner Details</font>
			</td>
									
		</tr>
							
		
		<tr>
			<th>Select</th>
			<th>Name</th>
			<th>User-ID</th>
			<th>Address</th>
			<th>E-Mail</th>
			<th>Contact No</th>
		</tr>

<% 
	
    if(rs != null)
    {
	    while(rs.next())
		{
			/* id=rs.getInt(1);
			username=rs.getString(2);
			userid=rs.getString(4);
			address=rs.getString(5);
			mobile=rs.getString(6);
			email = rs.getString(7);
			
			
			 */
			
			
			
			
			
			
			
			id=rs.getInt(1);
	    	/* password=rs.getString(4); */
			userid=rs.getString(2);
			username=rs.getString(4);
			address=rs.getString(5);
			phone=rs.getString(7);
			email = rs.getString(6);
			
		    
%>
			<tr align="center">
				<td><input name="chk" type="checkbox" value="<%=id%>"></td>
						<td><%=username%></td>
						<td><%=userid%></td>
						<td><%=address%></td>
						<td><%=phone%></td>
						<td width="50px"><%=email%></td>
						
			</tr>
<%
		}
    }

%>
  	 </table>
  	 </p>
   </div>
 </form>
   

<div id="pageNavPosition" style="position:absolute;top:420px;left:550px"></div>
<script type="text/javascript"><!--
        var pager = new Pager('results',5); 
        pager.init(); 
        pager.showPageNav('pager', 'pageNavPosition'); 
        pager.showPage(1);
    //--></script>

<%-- Adding The Server Details --%>

<%
	if(Utility.parse(request.getParameter("no1"))==1)
	{
		%>
			<div class="success" id="message" style="position:absolute;top:5px;left:50px">	
				<p>Auditor Details Added successfully ..!</p>
			</div>
		<%
	}
	if(Utility.parse(request.getParameter("no1"))==2)
	{
		%>
			<div class="success" id="message" style="position:absolute;top:5px;left:50px">	
				<p>Auditor Updated successfully ..!</p>
			</div>
		<%
	}
	if(Utility.parse(request.getParameter("no1"))==3)
	{
		%>
			<div class="error" id="message" style="position:absolute;top:5px;left:50px">	
				<p>Opps,Select atleast one checkbox !</p>
			</div>
		<%
	}
	if(Utility.parse(request.getParameter("no1"))==4)
	{
		%>
			<div class="error" id="message" style="position:absolute;top:5px;left:50px">	
				<p>Opps,Select only one checkbox to edit!</p>
			</div>
		<%
	}
	if(Utility.parse(request.getParameter("no1"))==5)
	{
		%>
			<div class="success" id="message" style="position:absolute;top:5px;left:50px">	
				<p>Auditor Details Deleted successfully ..!</p>
			</div>
		<%
	}
}
	if(no == 2)
	{
%>
		
		<div id="a3" align="right" style="position:absolute;top:11px;left:11px">
						
		 <p>
			<table id="login" align="center">
					
				<form action="<%=request.getContextPath()%>/ListAuditor">
				<input type="hidden" name="submit" value="Add"></input>
				<input type="hidden" name="add1" value="YES"></input>
					
					<tr>
							<td colspan="7" align="center">
								<font color="#000000" style="font-style: bold" size="5">Add Auditor Details</font>
							</td>
					</tr>
					
					<tr>
							<td colspan="7" align="center"><hr></hr></td>
					</tr>
					
					<tr>
							<td>&nbsp;</td>
					</tr>
					
					<tr align="center">
							<td width="175px">Username</td>
							<td width="75px">:</td>
							<td width="200px">
								<input class="field" type="text" name="username" required="yes"></input>
							</td>
							
							<td width="50px"></td>
							
							<td>Password</td>
							<td >:</td>
							<td> 
								<input class="field" type="password" name="password" required="yes"></input>
							</td>
					</tr>
					
					<tr align="center">
							<td>&nbsp;</td>
					</tr>
					
					<tr align="center">
							<td>Auditid</td>
							<td>:</td>
							<td> 
									<input class="field" type="text" name="name" required="yes"></input>
							</td>
							
							<td width="50px"></td>
							
							<td>Address</td>
							<td>:</td>
							<td> 
									<input class="field" type="text" name="address" required="yes"></input>
							</td>
							
					</tr>
					
				
					<tr align="center">
							<td>&nbsp;</td>
					</tr>
					
					<tr align="center">
					        
					        <td>Phone</td>
							<td>:</td>
							<td> 
									<input class="field" type="text" name="phone"required="yes"></input>
							</td>
					        
					        <td width="50px"></td>
					        
							<td>Email</td>
							<td>:</td>
							<td> 
								<input class="field" type="text" name="email" ></input>
							</td>
					</tr>
					
					<tr align="center">
							<td>&nbsp;</td>
					</tr>
					
					
					<tr align="center">
							<td colspan="7" align="center">
								<input class="button" type="submit"  value="Register"></input>
							</td>
					</tr>
			</form>
			</table>
		</p>
						
</div>
<%
		if(Utility.parse(request.getParameter("no1"))==1)
		{
			%>
				<div class="error" id="message" style="position:absolute;top:5px;left:50px">	
					<p>Sorry,This user is already exists  !</p>
				</div>
			<%
		}
		
		if(Utility.parse(request.getParameter("no1"))==2)
		{
			%>
				<div class="error" id="message" style="position:absolute;top:5px;left:50px">	
					<p>Sorry, Somthing went wrong try again!</p>
				</div>
			<%
		}


}

if(no == 3)
{
	/* Adding The User Details */
	
	String hostName="";
	
	
	 if(rs != null)
    {
	    while(rs.next())
		{
	    	id=rs.getInt(1);
	    	password=rs.getString(4);
			userid=rs.getString(3);
			username=rs.getString(2);
			address=rs.getString(5);
			phone=rs.getString(6);
			email = rs.getString(7);
			
		}
    }   
	
%>
		
		<div id="a2" align="right"  style="position:absolute;top:11px;left:11px">
						
		 <p>
			<table id="login" align="center">
					
				<form action="<%=request.getContextPath()%>/ListAuditor">
				<input type="hidden" name="submit" value="Edit"></input>
			    <input type="hidden" name="edit1" value="YES"></input>
			    <input type="hidden" name="id" value="<%=id %>"></input>
					
					<tr>
							<td colspan="7" align="center">
								<font color="#000000" style="font-style: bold" size="5">Edit Data Owner Details</font>
							</td>
					</tr>
					
					<tr>
							<td colspan="7" align="center"><hr></hr></td>
					</tr>
					
					<tr>
							<td>&nbsp;</td>
					</tr>
					
					<tr align="center">
							<td width="175px">Username</td>
							<td width="75px">:</td>
							<td width="200px">
								<input class="field" type="text" name="username" value="<%=username %>" required="yes" readonly="readonly"></input>
							</td>
							
							<td width="50px"></td>
							
							<td>Password</td>
							<td >:</td>
							<td> 
								<input class="field" type="text" name="password" value="<%=password%>" required="yes" readonly="readonly"></input>
							</td>
					</tr>
					
					<tr align="center">
							<td>&nbsp;</td>
					</tr>
					
					<tr align="center">
							<td>Name</td>
							<td>:</td>
							<td> 
									<input class="field" type="text" name="name" value="<%=username %>" required="yes"></input>
							</td>
							
							<td width="50px"></td>
							
							 <td>Address</td>
							<td>:</td>
							<td> 
									<input class="field" type="text" name="address" value="<%=address %>" required="yes"></input>
							</td>
							
					</tr>
					
					
					<tr align="center">
							<td>&nbsp;</td>
					</tr>
					
					<tr align="center">
					        
					       <td>Email</td>
							<td>:</td>
							<td> 
									<input class="field" type="text" name="email" value="<%=email%>" required="yes"></input>
							</td>
					        
					        <td width="50px"></td>
					        
							<td>Phone</td>
							<td>:</td>
							<td> 
								<input class="field" type="text" name="phone" value="<%=phone%>"></input>
							</td>
					</tr>
					
					<tr align="center">
							<td>&nbsp;</td>
					</tr>
					
					<tr align="center">
							<td colspan="7" align="center">
								<input class="button" type="submit"  value="Update"></input>
							</td>
					</tr>
			</form>
			</table>
		</p>
						
</div>
<%
		if(Utility.parse(request.getParameter("no1"))==1)
		{
			%>
				<div class="error" id="message" style="position:absolute;top:200px;left:200px">	
					<p>Sorry, Somthing went wrong try again!</p>
				</div>
			<%
		}
		
		if(Utility.parse(request.getParameter("no1"))==2)
		{
			%>
				<div class="error" id="message" style="position:absolute;top:5px;left:50px">	
					<p>Sorry, Somthing went wrong try again!</p>
				</div>
			<%
		}


}
%>
</body>
</html>


