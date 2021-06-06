<%@ page import="java.sql.*" %>
<%@ page import="com.util.*" %>
<html>
<%
	ResultSet rs=(ResultSet)Utility.parse2(request.getAttribute("rs"));
	String name=Utility.parse1(request.getParameter("name")); 
	int count=1;
%>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/Files/CSS/message.css" type="text/css"/>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/Files/CSS/trans.css" type="text/css"/>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/Files/CSS/login.css" type="text/css"/>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/Files/CSS/button.css" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath() %>/Files/JS/pagination.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/Files/JS/style.js"></script>
</head>
<body onload="startTimer()">
<form action="<%=request.getContextPath() %>/GetFiles">
<input type="hidden" name="name" value="<%=name %>"/>
<input type="submit" name="submit" value="Verify" class="button_example" id="a1"/>&nbsp;&nbsp;&nbsp;&nbsp;

	<table id="results" class="tab"  style="width: 500 ; color: black";>
	<tr>
		<th>Select</th>
		<th>FileName/No</th>
		<th>Date</th>
		<!-- <th>Day</th> -->
		<th>Time</th>
	</tr>
	<%
	if(rs!=null)
		while(rs.next())
		{%>
			<tr>
				
					<td><input name="chk" type="checkbox" value="<%=rs.getInt(1) %>"></td>
				<td><%=rs.getString(5) %></td>
				<td><%=rs.getString(2) %></td>
				<%-- <td><%=rs.getString(8) %></td> --%>
				<td><%=rs.getString(3) %></td>
			</tr>
		<%}
	%>
</table>
</form>
<div id="pageNavPosition" style="cursor:hand"></div>
<script type="text/javascript"><!--
        var pager = new Pager('results', 5); 
        pager.init(); 
        pager.showPageNav('pager', 'pageNavPosition'); 
        pager.showPage(1);
    //--></script>
<%
	if(Utility.parse(request.getParameter("no"))==2)
    {%>
    
    <script type="text/javascript">

   alert(' File has been verified Successfully and No Modification.....!');

   </script>
    	
    <%}
	if(Utility.parse(request.getParameter("no"))==3)
    {%>
    
        <script type="text/javascript">

      alert(' File has been verified Successfully and has been modified by someone....!');

           </script>
    	
    <%}
	if(Utility.parse(request.getParameter("no"))==4)
    {%>
    	<div class="success" id="message" style="position:absolute;top:1px;left:300px">	
    		<p>File Deleted Successfully.....!</p>
    	</div>			
    <%}
	if(Utility.parse(request.getParameter("no"))==5)
    {%>
    	<div class="success" id="message" style="position:absolute;top:1px;left:300px">	
    		<p>File Verify process successfull .....!</p>
    	</div>			
    <%}
	if(Utility.parse(request.getParameter("no"))==6)
    {%>
    	<div class="error" id="message" style="position:absolute;top:1px;left:300px">	
    		<p>File Verify process successfull........!</p>
    	</div>			
    <%}
	
	if(Utility.parse(request.getParameter("no"))==7)
    {%>
    	<div class="error" id="message" style="position:absolute;top:1px;left:300px">	
    		<p>Network problem.....!</p>
    	</div>			
    <%}
	
	if(Utility.parse(request.getParameter("no"))==8)
    {%>
    	<div class="error" id="message" style="position:absolute;top:1px;left:300px">	
    		<p>User not uploaded any file.....!</p>
    	</div>			
    <%}
%>		
</body>
</html>