<%-- 
    Document   : pay
    Created on : 2015-6-15, 2:30:21
    Author     : Rui Hu
--%>

<%@page import="com.ruihu.easyshop.order.domain.Order"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>pay.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/order/pay.css'/>">
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.11.3.js'/>"></script>

<script type="text/javascript">
$(function() {
	$("img").click(function() {
		$("#" + $(this).attr("name")).attr("checked", true);
	});
});
</script>
  </head>
  
  <body>
<div class="divContent">
	<span class="spanPrice">Total Payment£º</span><span class="price_t">&yen;${order.total }</span>
	<span class="spanOid">OrderNumber£º${order.oid }</span>
</div>
<!--<form action="<c:url value='/OrderServlet'/>" method="post" id="form1" target="_top">
<input type="hidden" name="method" value="payment"/>
<input type="hidden" name="oid" value="${order.oid }"/>-->
<div class="divBank">
	<div class="divText">Please submit your Information</div>
	
 <%@ page import="net.authorize.sim.*" %>
<%
     String apiLoginId = "";//your id from https://developer.authorize.net/integration/fifteenminutes/java/
     String transactionKey = "";//your id from https://developer.authorize.net/integration/fifteenminutes/java/
     String relayResponseUrl = "http://localhost:8084/EasyShop/relay_response.jsp";
     Order s =(Order)request.getAttribute("order");
     String amount =s.getTotal()+"";
     
     Fingerprint fingerprint = Fingerprint.createFingerprint(
          apiLoginId,
          transactionKey,
          1234567890,  // Random sequence used for creating the fingerprint
          amount);

     long x_fp_sequence = fingerprint.getSequence();
     long x_fp_timestamp = fingerprint.getTimeStamp();
     String x_fp_hash = fingerprint.getFingerprintHash(); 
%>
<form name='secure_redirect_form' id='secure_redirect_form_id' 
action='https://test.authorize.net/gateway/transact.dll' method='post'>
     <label>CreditCardNumber</label>
     <input type='text' class='text' name='x_card_num' size='15' /></br>
     <label>Exp.   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
     <input type='text' class='text' name='x_exp_date' size='4' /></br>
     
     
     
     
     <input type='hidden' name='x_invoice_num' value='<%=System.currentTimeMillis()%>' />
     <input type='hidden' name='x_relay_url' value='<%=relayResponseUrl%>' />
     <input type='hidden' name='x_login' value='<%=apiLoginId%>' />
     <input type='hidden' name='x_fp_sequence' value='<%=x_fp_sequence%>' />
     <input type='hidden' name='x_fp_timestamp' value='<%=x_fp_timestamp%>' />
     <input type='hidden' name='x_fp_hash' value='<%=x_fp_hash%>' />
     <input type='hidden' name='x_version' value='3.1' />
     <input type='hidden' name='x_method' value='CC' />
     <input type='hidden' name='x_type' value='AUTH_CAPTURE' />
     <input type='hidden' name='x_amount' value='<%=amount%>' />
     <input type='hidden' name='x_test_request' value='FALSE' />
     <input type='hidden' name='notes' value='extra hot please' />
     <input type='submit' name='buy_button' value='BUY' />
</form>      
        
</div>
</form>
  </body>
</html>
