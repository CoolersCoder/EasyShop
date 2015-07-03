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
	/*
	 * 1. change pic when hover submit
	 */
	$("#submit").hover(
		function() {
			$("#submit").attr("src", "/EasyShop/images/login2.jpg");
		},
		function() {
			$("#submit").attr("src", "/EasyShop/images/login1.jpg");
		}
	);
	
	/*
	 * 2. add a submit event on regist button
	 */
	$("#submit").submit(function(){
		$("#msg").text("");
		var bool = true;
		$(".input").each(function() {
			var inputName = $(this).attr("name");
			if(!invokeValidateFunction(inputName)) {
				bool = false;
			}
		});
		return bool;
	});
	
	/*
	 * 3.hide infor 
	 */
	$(".input").focus(function() {
		var inputName = $(this).attr("name");
		$("#" + inputName + "Error").css("display", "none");
	});
	
	 
	$(".input").blur(function() {
		var inputName = $(this).attr("name");
		invokeValidateFunction(inputName);
	})
});

/*
 * example: When you pass loginname,it will change to a validateLoginname()
 */
function invokeValidateFunction(inputName) {
	inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
	var functionName = "validate" + inputName;
	return eval(functionName + "()");	
}

/*
 * check username
 */
function validateLoginname() {
	var bool = true;
	$("#loginnameError").css("display", "none");
	var value = $("#loginname").val();
	if(!value) { 
		$("#loginnameError").css("display", "");
		$("#loginnameError").text("Username can not be empty！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) { 
		$("#loginnameError").css("display", "");
		$("#loginnameError").text("Username length must between 3 and 20");
		bool = false;
	}
	return bool;
}

/*
 * check password
 */
function validateLoginpass() {
	var bool = true;
	$("#loginpassError").css("display", "none");
	var value = $("#loginpass").val();
	if(!value) {
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("Password can not be empty！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) { 
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("password length must between 3 and 20");
		bool = false;
	}
	return bool;
}
/*
 * check verify code
 */
function validateVerifyCode() {
	var bool = true;
	$("#verifyCodeError").css("display", "none");
	var value = $("#verifyCode").val();
	if(!value) { 
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").text("verufy code can not be null！");
		bool = false;
	} else if(value.length != 4) { 
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").text("Wrong verify code！");
		bool = false;
	} 
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
	
	return bool;
}