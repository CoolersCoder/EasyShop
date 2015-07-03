<%-- 
    Document   : left
    Created on : 2015-6-2, 08:38:21
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>left</title>
    <base target="body"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.11.3.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/menu/mymenu.css'/>" type="text/css" media="all">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/left.css'/>">
<script language="javascript">
    /**
     * object must the same with first parameter
     * the second parameter is a title of left bar
     * @type Q6MenuBar
     */
var bar = new Q6MenuBar("bar", "EasyShop");
$(function() {
	bar.colorStyle = 4;
	bar.config.imgDir = "<c:url value='/menu/img/'/>";
	bar.config.radioButton=true;

	
<c:forEach items="${parents}" var="parent">
  <c:forEach items="${parent.children}" var="child">
	bar.add("${parent.cname}", "${child.cname}", "/EasyShop/BookServlet?method=findByCategory&cid=${child.cid}", "body");
  </c:forEach>
</c:forEach>
      
	$("#menu").html(bar.toString());
});
</script>
</head>
  
<body>  
  <div id="menu"></div>
</body>
</html>
