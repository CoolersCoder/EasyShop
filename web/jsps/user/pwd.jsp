<%-- 
    Document   : pwd
    Created on : 2015-6-5, 08:10:21
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>pwd.jsp</title>

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">    
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->
        <link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>">
        <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/pwd.css'/>">
        <script type="text/javascript" src="<c:url value='/jquery/jquery-1.11.3.js'/>"></script>
        <script src="<c:url value='/js/common.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/jsps/js/user/pwd.js'/>"></script>
    </head>

    <body>
        <div class="div0">
            <span>ChangePassword</span>
        </div>

        <div class="div1">
            <form action="<c:url value='/UserServlet'/>" method="post" target="_top">
                <input type="hidden" name="method" value="updatePassword"/>
                <table>
                    <tr>
                        <td><label class="error">${msg }</label></td>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right">Current password:</td>
                        <td><input class="input" type="password" name="loginpass" id="loginpass" value="${user.loginpass}"/></td>
                        <td><label id="loginpassError" class="error"></label></td>
                    </tr>
                    <tr>
                        <td align="right">New password:</td>
                        <td><input class="input" type="password" name="newpass" id="newpass" value="${user.newloginpass}"/></td>
                        <td><label id="newpassError" class="error"></label></td>
                    </tr>
                    <tr>
                        <td align="right">Reenter new password:</td>
                        <td><input class="input" type="password" name="reloginpass" id="reloginpass" value="${user.reloginpass}"/></td>
                        <td><label id="reloginpassError" class="error"></label></td>
                    </tr>
                    <tr>
                        <td align="right"></td>
                        <td>
                            <img id="vCode" src="<c:url value='/VerifyCodeServlet'/>" border="1"/>
                            <a href="javascript:_change();">Change</a>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">VerifyCode:</td>
                        <td>
                            <input class="input" type="text" name="verifyCode" id="verifyCode" value=""/>
                        </td>
                        <td><label id="verifyCodeError" class="error"></label></td>
                    </tr>
                    <tr>
                        <td align="right"></td>
                        <td><input id="submit" type="submit" value="Change password"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
