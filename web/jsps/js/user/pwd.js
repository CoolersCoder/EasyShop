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
	 *  
         * bind a event to verify form
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
	 * 3.when .input blur start to verify
	 */
	$(".input").blur(function() {
		var inputName = $(this).attr("name");
		invokeValidateFunction(inputName);
	});
});

 
function invokeValidateFunction(inputName) {
	inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
	var functionName = "validate" + inputName;
	return eval(functionName + "()");	
}

/*
 * verify pass
 */
function validateLoginpass() {
	var bool = true;
	$("#loginpassError").css("display", "none");
	var value = $("#loginpass").val();
	if(!value) { 
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("password can not be null！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) { 
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("The new code should between 3 and 20");
		bool = false;
	}
	return bool;
}

// verify new passwrod
function validateNewpass() {
	var bool = true;
	$("#newpassError").css("display", "none");
	var value = $("#newpass").val();
	if(!value) { 
		$("#newpassError").css("display", "");
		$("#newpassError").text("new password can not be null ！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {
		$("#newpassError").css("display", "");
		$("#newpassError").text("The new code should between 3 and 20");
		bool = false;
	}
	return bool;
}
 
function validateReloginpass() {
	var bool = true;
	$("#reloginpassError").css("display", "none");
	var value = $("#reloginpass").val();
	if(!value) { 
		$("#reloginpassError").css("display", "");
		$("#reloginpassError").text("reloginpasssword can not be null！");
		bool = false;
	} else if(value != $("#newpass").val()) { 
		$("#reloginpassError").css("display", "");
		$("#reloginpassError").text("Two password not match！");
		bool = false;
	}
	return bool;	
}

/*
 * verify verifyCode
 */
function validateVerifyCode() {
	var bool = true;
	$("#verifyCodeError").css("display", "none");
	var value = $("#verifyCode").val();
	if(!value) { 
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").text("VerifyCode can not be null！");
		bool = false;
	} else if(value.length != 4) { 
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").text("Wrong verifyCode！");
		bool = false;
	} else { //useing ajax to call servlete ajaxValidateVerifyCode
		$.ajax({
			cache: false,
			async: false,
			type: "POST",
			dataType: "json",
			data: {method: "ajaxValidateVerifyCode", verifyCode: value},
			url: "/goods/UserServlet",
			success: function(flag) {
				if(!flag) {
					$("#verifyCodeError").css("display", "");
					$("#verifyCodeError").text("Wrong verifyCode！");
					bool = false;					
				}
			}
		});
	}
	return bool;
}
