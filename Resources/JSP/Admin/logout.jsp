<html>
<head>
<script type="text/javascript">
window.history.forward();
function noBack() { window.history.forward(); }
</script>
</head>
<body>
	<%
		response.sendRedirect(request.getContextPath());
	%>
</body>
</html>