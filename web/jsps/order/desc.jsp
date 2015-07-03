<%-- 
    Document   : desc
    Created on : 2015-6-1, 08:30:21
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Order Detail</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/order/desc.css'/>">
  </head>
  
<body>
	<div class="divOrder">
		<span>Order No：${order.oid }
<c:choose>
	<c:when test="${order.status eq 1 }">(Wait Pay)</c:when>
	<c:when test="${order.status eq 2 }">(Prep Shiping)</c:when>
	<c:when test="${order.status eq 3 }">(Wait Commit)</c:when>
	<c:when test="${order.status eq 4 }">(Trading Success)</c:when>
	<c:when test="${order.status eq 5 }">(Cancel)</c:when>
</c:choose>	
		　　　Order Time：${order.ordertime }</span>
	</div>
	<div class="divContent">
		<div class="div2">
			<dl>
				<dt>Recive Info</dt>
				<dd>${order.address }</dd>
			</dl>
		</div>
		<div class="div2">
			<dl>
				<dt>Item List</dt>
				<dd>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th class="tt">Item Name</th>
							<th class="tt" align="left">Price</th>
							<th class="tt" align="left">Quantity</th>
							<th class="tt" align="left">subtotal</th>
						</tr>


<c:forEach items="${order.orderItemList }" var="item">
						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="400px">
								<div class="bookname">
								  <img align="middle" width="70" src="<c:url value='/${item.book.image_b }'/>"/>
								  <a href="<c:url value='/BookServlet?method=load&bid=${item.book.bid }'/>">${item.book.bname }</a>
								</div>
							</td>
							<td class="td" >
								<span>&yen;${item.book.currPrice }</span>
							</td>
							<td class="td">
								<span>${item.quantity }</span>
							</td>
							<td class="td">
								<span>&yen;${item.subtotal }</span>
							</td>			
						</tr>
</c:forEach>


					</table>
				</dd>
			</dl>
		</div>
		<div style="margin: 10px 10px 10px 550px;">
			<span style="font-weight: 900; font-size: 15px;">Total：</span>
			<span class="price_t">&yen;${order.total }</span><br/>
<c:if test="${order.status eq 1 }">
	<a href="<c:url value='/OrderServlet?method=paymentPre&oid=${order.oid }'/>" class="pay"></a><br/>
</c:if>
<c:if test="${order.status eq 1 and btn eq 'cancel'}">
    <a id="cancel" href="<c:url value='/OrderServlet?method=cancel&oid=${order.oid }'/>">Cancel Order</a><br/>
</c:if>
<c:if test="${order.status eq 3 and btn eq 'confirm'}">
	<a id="confirm" href="<c:url value='/OrderServlet?method=confirm&oid=${order.oid }'/>">comfirm order</a><br/>
</c:if>	
		</div>
	</div>
</body>
</html>

