<%-- 
    Document   : regist
    Created on : 2015-6-8, 15:32:22
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Register</title>

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">    
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->
        <link rel="stylesheet" href="<c:url value='/css/regist/reset.css'/>"> 
        <link rel="stylesheet" href="<c:url value='/jsps/css/user/regist.css'/>">     
    
        <link rel="stylesheet" href="<c:url value='/css/regist/supersized.css'/>">
        <link rel="stylesheet" href="<c:url value='/css/regist/style.css'/>">
     
        
       
    </head>

    <body oncontextmenu="return false">
        <div id="body">
            <div id="divMain">
              
                <div id="divBody">
                    <form action="<c:url value='/UserServlet'/>" method="post" id="registForm">
                        <input type="hidden" name="method" value="regist"/>  
                        <table id="tableForm">
                            <tr>
                                <td class="tdText">Username：</td>
                                <td class="tdInput">
                                    <input class="inputClass" type="text" name="loginname" id="loginname" value="${form.loginname }"/>
                                </td>
                                <td class="tdError">
                                    <label class="errorClass" id="loginnameError">${errors.loginname }</label>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdText">Password：</td>
                                <td>
                                    <input class="inputClass" type="password" name="loginpass" id="loginpass" value="${form.loginpass }"/>
                                </td>
                                <td>
                                    <label class="errorClass" id="loginpassError">${errors.loginpass }</label>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdText">Confirm Password：</td>
                                <td>
                                    <input class="inputClass" type="password" name="reloginpass" id="reloginpass" value="${form.reloginpass }"/>
                                </td>
                                <td>
                                    <label class="errorClass" id="reloginpassError">${errors.reloginpass}</label>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdText">Email：</td>
                                <td>
                                    <input class="inputClass" type="text" name="email" id="email" value="${form.email }"/>
                                </td>
                                <td>
                                    <label class="errorClass" id="emailError">${errors.email}</label>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdText">VerifyCode：</td>
                                <td>
                                    <input class="inputClass" type="text" name="verifyCode" id="verifyCode" value="${form.verifyCode }"/>
                                </td>
                                <td>
                                    <label class="errorClass" id="verifyCodeError">${errors.verifyCode}</label>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <div id="divVerifyCode"><img id="imgVerifyCode" src="<c:url value='/VerifyCodeServlet'/>"/></div>
                                </td>
                                <td>
                                    <label><a href="javascript:_hyz()">Change</a></label>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <input type="image" src="<c:url value='/images/regist1.jpg'/>" id="submitBtn"/>
                                </td>
                                <td>
                                    <label></label>
                                </td>
                            </tr>
                        </table>
                    </form>    
                </div>
            </div>
        </div>
     
        
                
         <script src="<c:url value='/jquery/jquery-1.11.3.js'/>"></script> 
         <script src="<c:url value='/jsps/js/user/regist.js'/>"></script>
        <script src="<c:url value='/js/regist/supersized.3.2.7.min.js'/>"></script>
        <script src="<c:url value='/js/regist/supersized-init.js'/>"></script> 
        <script language="javascript">
		$(".btn").click(function(){
			is_hide();
		});
		var u = $("input[name=username]");
		var p = $("input[name=password]");
		$("#submit").
		$("#submit").live('click',function(){
			if(u.val() == '' || p.val() =='')
			{
				$("#ts").html("Username and password  not  null~");
				is_show();
				return false;
			}
			else{
				var reg = /^[0-9A-Za-z]+$/;
				if(!reg.exec(u.val()))
				{
					$("#ts").html("Username error!");
					is_show();
					return false;
				}
			}
		});
		window.onload = function()
		{
			$(".connect p").eq(0).animate({"left":"0%"}, 600);
			$(".connect p").eq(1).animate({"left":"0%"}, 1000);
		};
		function is_hide(){ $(".alert").animate({"top":"-40%"}, 300) };
		function is_show(){ $(".alert").show().animate({"top":"45%"}, 200) };


			function s(){

			}
	 </script>
    </body>
</html>
