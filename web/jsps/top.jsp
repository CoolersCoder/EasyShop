<%-- 
    Document   : top
    Created on : 2015-6-2, 21:10:21
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>top</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
        <link rel="stylesheet" type="text/css" href="<c:url value='/css/animate.css'/>">
        <link  href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
        <link  href="<c:url value='/js/bootstrap.min.js'/>" rel="stylesheet">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {
		background: #15B69A;
		margin: 0px;
		color: #ffffff;
	}
	a {
		text-decoration:none;
		color: #ffffff;
		font-weight: 900;
                text-decoration-color: #15B69A;
	} 
	a:hover {
	 
	        background-color: yellow;
		font-weight: 800;
 
	}
        h1{
            margin: 0 ;
            padding:0 ;
            text-align: center;
        }
        
        
        .nav>li>a:hover{
            background-color: #d0e9c6;
        }
        
</style>
  </head>
  
<body>
<h1 class="animated bounceInDown">EasyShop Online</h1>
<div >
    <ul class="nav nav-tabs">
        <%-- check user session --%>
        <c:choose>
            <c:when test="${empty sessionScope.sessionUser }">
                <li> <a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">Login </a> </li>
                <li> <a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">Regist</a></li>	
                </c:when>
                <c:otherwise>
                <li> <a >EasyShop Member：${sessionScope.sessionUser.loginname }</a></li>
                <li> <a href="<c:url value='/CartItemServlet?method=myCart'/>" target="body">My Cart</a></li>
                <li> <a href="<c:url value='/OrderServlet?method=myOrders'/>" target="body">My Order</a></li>
                <li> <a href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">Modify Password</a></li>
                <li> <a href="<c:url value='/UserServlet?method=quit'/>" target="_parent">Log out</a></li>
                <li> <a href="http://www.hrhphotos.com/blog" target="_blank">Contact</a></li>	
                </c:otherwise>
            </c:choose>
    </ul>                 
</div>
 
<!--For the bootstrap necessary-->
<script src="<c:url value='/js/jquery-1.11.3.min.js'/>"></script>
<script src="<c:url value='/js/bootstrap.min.js'/>"></script>
  </body>
</html>
