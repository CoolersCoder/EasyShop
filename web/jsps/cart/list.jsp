<%-- 
    Document   : list
    Created on : 2015-6-29, 09:30:21
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<c:url value='/jquery/jquery-1.11.3.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/list.css'/>">
<script type="text/javascript">
$(function() {
	showTotal();//calculate totally
	
	/*
	add click event to the selected all
	*/
	$("#selectAll").click(function() {
		
		var bool = $("#selectAll").attr("checked");
		/*
		2. make concurrency for the chekbox
		*/
		setItemCheckBox(bool);
		/*
		3. make total price do the same thing in the some time,when they check a box
		*/
		setJieSuan(bool);
		/*
		4. recalculate
		*/
		showTotal();
	});
	
	/*
	 add event for the checkbox
	*/
	$(":checkbox[name=checkboxBtn]").click(function() {
		var all = $(":checkbox[name=checkboxBtn]").length;//items number
		var select = $(":checkbox[name=checkboxBtn][checked=true]").length;//get item which is checked

		if(all == select) {//select all
			$("#selectAll").attr("checked", true);//set select all
			setJieSuan(true);
		} else if(select == 0) {
			$("#selectAll").attr("checked", false);//cancel select all
			setJieSuan(false);
		} else {
			$("#selectAll").attr("checked", false);//cancel select all
			setJieSuan(true);				
		}
		showTotal();//calculate total price
	});
	
	/*
	add event for the subtraction 
	*/
	$(".jian").click(function() {
		// get cartItemId
		var id = $(this).attr("id").substring(0, 32);
		// get number from quantity
		var quantity = $("#" + id + "Quantity").val();
		// if the value equal 1, ask a question!
		if(quantity == 1) {
			if(confirm("Do you really want to delete the item？")) {
				location = "/EasyShop/CartItemServlet?method=batchDelete&cartItemIds=" + id;
			}
		} else {
			sendUpdateQuantity(id, quantity-1);
		}
	});
	
	//add event for the plus
	$(".jia").click(function() {
		// get cartItemId
		var id = $(this).attr("id").substring(0, 32);
		// get number from quantity
		var quantity = $("#" + id + "Quantity").val();
		sendUpdateQuantity(id, Number(quantity)+1);
	});
});

// get request from the server
function sendUpdateQuantity(id, quantity) {
	$.ajax({
		async:false,
		cache:false,
		url:"/EasyShop/CartItemServlet",
		data:{method:"updateQuantity",cartItemId:id,quantity:quantity},
		type:"POST",
		dataType:"json",
		success:function(result) {
			//1. modfiy quantity
			$("#" + id + "Quantity").val(result.quantity);
			//2. modify subtotal
			$("#" + id + "Subtotal").text(result.subtotal);
			//3. recalculate total
			showTotal();
		}
	});
}

/*
 * calculate total price
 */
function showTotal() {
	var total = 0;
	/*
	1.check all value which is checked
	*/
	$(":checkbox[name=checkboxBtn]:checked").each(function() {
		//2. get value for this component
		var id = $(this).val();
		//3. get text
		var text = $("#" + id + "Subtotal").text();
		//4. add together
		total += Number(text);
	});
	// 5. set value into #total
	$("#total").text(round(total, 2));//using around function to modify result
}


function setItemCheckBox(bool) {
	$(":checkbox[name=checkboxBtn]").attr("checked", bool);
}

/*
 * set calculate style
 */
function setJieSuan(bool) {
	if(bool) {
		$("#jiesuan").removeClass("kill").addClass("jiesuan");
		$("#jiesuan").unbind("click");//cancel event for the click
	} else {
		$("#jiesuan").removeClass("jiesuan").addClass("kill");
		$("#jiesuan").click(function() {return false;});
	}
	
}

/*
 * batch delete
 */
function batchDelete() {
	var cartItemIdArray = new Array();
	$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
		cartItemIdArray.push($(this).val());
	});
	location = "/EasyShop/CartItemServlet?method=batchDelete&cartItemIds=" + cartItemIdArray;
}


function jiesuan() {
	// 1. get all item , and set them into array
	var cartItemIdArray = new Array();
	$(":checkbox[name=checkboxBtn]:checked").each(function() {
		cartItemIdArray.push($(this).val());//add value into array
	});	
	// 2. convert array into String, and then give the value into cartItemIds
	$("#cartItemIds").val(cartItemIdArray.toString());
	// save total value into #hiddenTotal
	$("#hiddenTotal").val($("#total").text());
	// 3. submit form
	$("#jieSuanForm").submit();
}
</script>
  </head>
  <body>
      <c:choose>
          <c:when test="${empty cartItemList}">
              <table width="95%" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                      <td align="right">
                          <img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
                      </td>
                      <td>
                          <span class="spanEmpty">Your cart is empty</span>
                      </td>
                  </tr>
              </table>  
          </c:when>
          <c:otherwise>

              <table width="95%" align="center" cellpadding="0" cellspacing="0">
                  <tr align="center" bgcolor="#efeae5">
                      <td align="left" width="50px">
                          <input type="checkbox" id="selectAll" checked="checked"/><label for="selectAll">SelectAll</label>
                      </td>
                      <td colspan="2">Item Name</td>
                      <td>price</td>
                      <td>Quantity</td>
                      <td>subtotal</td>
                      <td>Operate</td>
                  </tr>


                  <c:forEach items="${cartItemList}" var="cartItem">

                      <tr align="center">
                          <td align="left">
                              <input value="${cartItem.cartItemId}" type="checkbox" name="checkboxBtn" checked="checked"/>
                          </td>
                          <td align="left" width="70px">
                              <a class="linkImage" href="<c:url value='/jsps/book/desc.jsp'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.book.image_b}'/>"/></a>
                          </td>
                          <td align="left" width="400px">
                              <a href="<c:url value='/jsps/book/desc.jsp'/>"><span>${cartItem.book.bname}</span></a>
                          </td>
                          <td><span>&#36;<span class="currPrice">${cartItem.book.currPrice}</span></span></td>
                          <td>
                              <a class="jian" id="${cartItem.cartItemId}Jian"></a><input class="quantity" readonly="readonly" id="${cartItem.cartItemId}Quantity" type="text" value="${cartItem.quantity}"/><a class="jia" id="${cartItem.cartItemId}Jia"></a>
                          </td>
                          <td width="100px">
                              <span class="price_n">&#36;<span class="subTotal" id="${cartItem.cartItemId}Subtotal">${cartItem.subtotal}</span></span>
                          </td>
                          <td>
                              <a href="<c:url value='/CartItemServlet?method=batchDelete&cartItemIds=${cartItem.cartItemId}'/>">Delete</a>
                          </td>
                      </tr>

                  </c:forEach>

                  <tr>
                      <td colspan="4" class="tdBatchDelete">
                          <a href="javascript:batchDelete();">Remove ALL</a>
                      </td>
                      <td colspan="3" align="right" class="tdTotal">
                          <span>Total：</span><span class="price_t">&#36;<span id="total"></span></span>
                      </td>
                  </tr>
                  <tr>
                      <td colspan="7" align="right">
                         <a href="javascript:jiesuan();"  id="jiesuan" class="jiesuan"></a>
                      </td>
                  </tr>
              </table>
              <form id="jieSuanForm" action="<c:url value='/CartItemServlet'/>" method="post">
		<input type="hidden" name="cartItemIds" id="cartItemIds"/>
		<input type="hidden" name="total" id="hiddenTotal"/>
		<input type="hidden" name="method" value="loadCartItems"/>
              </form>
          </c:otherwise>
      </c:choose>
  </body>
</html>
