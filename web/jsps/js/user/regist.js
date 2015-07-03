/* 
 * Copyright (C) 2015 Rui Hu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
$(function() {
	$(".errorClass").each(function() {
		showError($(this));//traverse every element and call showError function
	});
	/*
	 * 2. change regist pic
	 */
	$("#submitBtn").hover(
		function() {
			$("#submitBtn").attr("src", "/EasyShop/images/regist2.jpg");
		},
		function() {
			$("#submitBtn").attr("src", "/EasyShop/images/regist1.jpg");
		}
	);
 
	$(".inputClass").focus(function() {
		var labelId = $(this).attr("id") + "Error"; 
		$("#" + labelId).text(""); 
		showError($("#" + labelId)); 
	});
 
	$(".inputClass").blur(function() {
		var id = $(this).attr("id"); 
		var funName = "validate" + id.substring(0,1).toUpperCase() + id.substring(1) + "()"; 
		eval(funName); 
	});
	
 
	$("#registForm").submit(function() {
		var bool = true; 
		if(!validateLoginname()) {
			bool = false;
		}
		if(!validateLoginpass()) {
			bool = false;
		}
		if(!validateReloginpass()) {
			bool = false;
		}
		if(!validateEmail()) {
			bool = false;
		}
		if(!validateVerifyCode()) {
			bool = false;
		}
		
		return bool;
	});
});

/*
 * check username
 */
function validateLoginname() {
	var id = "loginname";
	var value = $("#" + id).val(); 
	if(!value) {
		$("#" + id + "Error").text("Username can not empty！");
		showError($("#" + id + "Error"));
		return false;
	}
	if(value.length < 3 || value.length > 20) {
		$("#" + id + "Error").text("username length should between 3 and 20");
		showError($("#" + id + "Error"));
		false;
	}
      
	/*
	 * 3. check user status
	 */
	$.ajax({
		url:"/EasyShop/UserServlet",//getrequest from UserServlet
		data:{method:"ajaxValidateLoginname", loginname:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(result) {                  
			if(!result) {
				$("#" + id + "Error").text("username has been registed!!");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});
	return true;
}

/*
 * check password
 */
function validateLoginpass() {
	var id = "loginpass";
	var value = $("#" + id).val(); 
	if(!value) {
		$("#" + id + "Error").text("Password can not empty！");
		showError($("#" + id + "Error"));
		return false;
	}
	if(value.length < 3 || value.length > 20) {
		$("#" + id + "Error").text("password length should between 3 and 20");
		showError($("#" + id + "Error"));
		false;
	}
	return true;	
}

function validateReloginpass() {
	var id = "reloginpass";
	var value = $("#" + id).val();
	if(!value) {
		$("#" + id + "Error").text("confirm password can not empty！");
		showError($("#" + id + "Error"));
		return false;
	}
	if(value != $("#loginpass").val()) {
		$("#" + id + "Error").text("Two password not match！");
		showError($("#" + id + "Error"));
		false;
	}
	return true;	
}

/*
 * Check email
 */
function validateEmail() {
	var id = "email";
	var value = $("#" + id).val(); 
	if(!value) {
		$("#" + id + "Error").text("Email can not empty！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 2.check email format
	 */
	if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)) {
		$("#" + id + "Error").text("Error Email faomat");
		showError($("#" + id + "Error"));
		false;
	}
 
	$.ajax({
		url:"/EasyShop/UserServlet", 
		data:{method:"ajaxValidateEmail", email:value}, 
		type:"POST",
		dataType:"json",
		async:false, 
		cache:false,
		success:function(result) {
			if(!result) { 
				$("#" + id + "Error").text("Email has been registered");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});
	return true;		
}

/*
 * check verifycode
 */
function validateVerifyCode() {
	var id = "verifyCode";
	var value = $("#" + id).val();
	if(!value) {
		$("#" + id + "Error").text("Verifycode can not empty！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 2. Check length
	 */
	if(value.length != 4) {
		$("#" + id + "Error").text("Error verifycode！");
		showError($("#" + id + "Error"));
		false;
	}
	/*
	 * 3. using ajax check verifycode
	 */
	$.ajax({
		url:"/EasyShop/UserServlet",//request servlet
		data:{method:"ajaxValidateVerifyCode", verifyCode:value},//send parameter to server
		type:"POST",
		dataType:"json",
		async:false, 
		cache:false,
		success:function(result) {
			if(!result) { 
				$("#" + id + "Error").text("Error VerifyCode！");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});
	return true;		
}

 
function showError(e) {
	var text = e.text(); 
	if(!text) { 
		e.css("display", "none"); 
	} else { 
		e.css("display", "");
	}
}

/*
 Change Verify Code
 */
function _hyz() {
	/*
	 * 1. get<img>element
	 * 2. reset src attribute
	 */
	$("#imgVerifyCode").attr("src", "/EasyShop/VerifyCodeServlet?a=" + new Date().getTime());
}
