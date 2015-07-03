<%-- 
    Document   : desc
    Created on : 2015-5-28, 2:30:21
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Book detail</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
       <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
	<script src="<c:url value='/jquery/jquery-1.11.3.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/book/desc.css'/>">
	<script src="<c:url value='/jsps/js/book/desc.js'/>"></script>
  </head>
  
  <body>
  <div class="divBookName">${book.bname}</div>
  <div>
    <img align="top" src="<c:url value='/${book.image_w}'/>" class="img_image_w"/>
    <div class="divBookDesc">
	    <ul>
	    	<li>ISBN-13：${book.bid} </li>
	    	<li>EasyShop：<span class="price_n">&#36;${book.currPrice}</span></li>
	    	<li>Price：<span class="spanPrice">&#36;${book.price}</span>　Discount：<span style="color: #c30;">${book.discount}</span>OFF</li>
	    </ul>
		<hr class="hr1"/>
		<table>
			<tr>
				<td colspan="3">
					Author：${book.author}
				</td>
			</tr>
			<tr>
				<td colspan="3">
					Publisher: ${book.press}
				</td>
			</tr>
			<tr>
				<td colspan="3">Publish time：${book.publishtime}</td>
			</tr>
			<tr>
				<td>Edition：${book.edition}</td>
				<td>Paperback：${book.pageNum}</td>
				<td>Font：${book.wordNum}</td>
			</tr>
			<tr>
				<td width="180">Publisher time：${book.printtime}</td>
				<td>Starting page：${book.booksize}</td>
				<td>Paper condition：${book.paper}</td>
			</tr>
		</table>
		<div class="divForm">
			<form id="form1" action="<c:url value='/CartItemServlet'/>" method="post">
				<input type="hidden" name="method" value="add"/>
				<input type="hidden" name="bid" value="${book.bid}"/>
  				Buy：<input id="cnt" style="width: 40px;text-align: center;" type="text" name="quantity" value="1"/>
  			</form>
  			<a id="btn" href="javascript:$('#form1').submit();"></a>
  		</div>	
	</div>
  </div>
  </body>
</html>
