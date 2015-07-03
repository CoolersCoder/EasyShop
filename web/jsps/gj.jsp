<%-- 
    Document   : gj
    Created on : 2015-6-1, 08:50:22
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Combin search</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	table {
		color: #404040;
		font-size: 10pt;
	}
</style>
  </head>
  
  <body>
  <form action="<c:url value='/BookServlet'/>" method="get">
  	<input type="hidden" name="method" value="findByCombination"/>
<table align="center">
	<tr>
		<td>BookName：</td>
		<td><input type="text" name="bname"/></td>
	</tr>
	<tr>
		<td>Author：</td>
		<td><input type="text" name="author"/></td>
	</tr>
	<tr>
		<td>Publish：</td>
		<td><input type="text" name="press"/></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			<input type="submit" value="Search"/>
			<input type="reset" value="Rewrite"/>
		</td>
	</tr>
</table>
	</form>
  </body>
</html>
