<%-- 
    Document   : list
    Created on : 2015-6-1, 09:20:21
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>订单列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">

	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/order/list.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
    <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
  </head>
  
  <body>
<div class="divMain">
	<div class="divTitle">
		<span style="margin-left: 150px;margin-right: 280px;">Item Info</span>
		<span style="margin-left: 40px;margin-right: 38px;">Price</span>
		<span style="margin-left: 50px;margin-right: 40px;">Status</span>
		<span style="margin-left: 50px;margin-right: 0px;">Operating</span>
	</div>
	<br/>
	<table align="center" border="0" width="100%" cellpadding="0" cellspacing="0">

<c:forEach items="${pb.beanList }" var="order">

		<tr class="tt">
			<td width="320px">Order No：<a  href="<c:url value='/OrderServlet?method=load&oid=${order.oid }'/>">${order.oid }</a></td>
			<td width="200px">Order Time：${order.ordertime }</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">


  <c:forEach items="${order.orderItemList }" var="orderItem">
	<a class="link2" href="<c:url value='/BookServlet?method=load&bid=${orderItem.book.bid }'/>">
	    <img border="0" width="70" src="<c:url value='/${orderItem.book.image_b }'/>"/>
	</a>
  </c:forEach>
	



			</td>
			<td width="115px">
				<span class="price_t">&yen;${order.total }</span>
			</td>
			<td width="142px">
<c:choose>
	<c:when test="${order.status eq 1 }">(Wating Pay)</c:when>
	<c:when test="${order.status eq 2 }">(Prepare deliver)</c:when>
	<c:when test="${order.status eq 3 }">(Prepare comfirm)</c:when>
	<c:when test="${order.status eq 4 }">(Trading Sucess)</c:when>
	<c:when test="${order.status eq 5 }">(Cancel)</c:when>
</c:choose>			

			</td>
			<td>
			<a href="<c:url value='/OrderServlet?method=load&oid=${order.oid }'/>">Checking</a><br/>
<c:if test="${order.status eq 1 }">
				<a href="<c:url value='/OrderServlet?method=paymentPre&oid=${order.oid }'/>">Pay</a><br/>
				<a href="<c:url value='/OrderServlet?method=load&oid=${order.oid }&btn=cancel'/>">Cancel</a><br/>						
</c:if>
<c:if test="${order.status eq 3 }">
				<a href="<c:url value='/OrderServlet?method=load&oid=${order.oid }&btn=confirm'/>">Comfirm item</a><br/>
</c:if>
			</td>
		</tr>
</c:forEach>



	</table>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
</div>
  </body>
</html>
