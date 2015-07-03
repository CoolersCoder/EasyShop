<%-- 
    Document   : list
    Created on : 2015-5-28, 15:30:21
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Book List</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/book/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
        <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.11.3.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/js/book/list.js'/>"></script>
  </head>
  
  <body>

<ul>
    <c:forEach items="${pb.beanList}" var ="book">
        <li>
            <div class="inner">
                <a class="pic" href="<c:url value='/BookServlet?method=load&bid=${book.bid}'/>"><img src="<c:url value='/${book.image_b}'/>" border="0"/></a>
                <p class="price">
                    <span class="price_n">&#36;${book.currPrice}</span>
                    <span class="price_r">&#36;${book.price}</span>
                    (<span class="price_s">30%${book.discount}</span>)
                </p>
                <p><a id="bookname" title="${book.bname}" href="<c:url value='/BookServlet?method=load&bid=${book.bid}'/>">${book.bname}</a></p>
                <p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='${book.author}'>${book.author}</a></p>
                <p class="publishing">
                    <span>${book.press}：</span><a href="<c:url value='/jsps/book/list.jsp'/>">${book.price}</a>
                </p>
                <p class="publishing_time"><span>Publish time：</span>${book.publishtime}</p>
            </div>
        </li>
    </c:forEach>










</ul>

<div style="float:left; width: 100%; text-align: center;">
	<hr/>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
</div>

  </body>
 
</html>

