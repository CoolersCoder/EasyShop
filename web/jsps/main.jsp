<%-- 
    Document   : newjsp
    Created on : 2015-6-2, 10:08:22
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <link rel="shortcut icon" href="<c:url value='/images/favicon.ico'/>" type="image/x-icon" />
        <link  href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/main.css'/>">
         <link rel="stylesheet" type="text/css" href="<c:url value='/css/animate.css'/>">
        

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">    
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <meta http-equiv="content-type" content="text/html;charset=utf-8">
        <!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->


    </head>
        <title style="text-align: left" class="animated zoomInLeft" >EasyShop book gloable</title>
        <a>
        <table class="table" align="center">
            <tr class="trTop">

                <td colspan="2" class="tdTop">
                    <iframe frameborder="0" src="<c:url value='/jsps/top.jsp'/>" name="top"></iframe>
                </td>

            </tr>
            <tr>

                <td class="tdLeft" rowspan="2">
                    <iframe frameborder="0" src="<c:url value='/CategoryServlet?method=findAll'/>" name="left"></iframe>
                </td>


                <td class="tdSearch" style="border-bottom-width: 0px;">
                    <iframe frameborder="0" src="<c:url value='/jsps/search.jsp'/>" name="search"></iframe>
                </td>

            </tr>
            <tr>

                <td class="tdbody">
                    <iframe frameborder="0" src="<c:url value='/jsps/body.html'/>" name="body"></iframe>
                </td>

            </tr>
        </table>
   
	  
<!--Below part is footer-->
        <div class="container" id="footer">
            <div class="row">
                <!--for copyright-->
                <div class="col-lg-2 col-md-2 ">
                    <h6 class="h6_color">Copyright &copy; 2015 </h6>
                </div>
                <!--for myself-->
                <div class="col-lg-4 col-md-4">
                    <h6 class="h6_color ">About me</h6>
                    <p>Student from UNH, and this is a simple project for my class</p>
                </div>

                <div class="col-lg-2 col-md-2 col-sm-2">
                    <h6 class="h6_color">Navigation</h6>
                    <ul class="list-unstyled">
                        <li><a href="#">Home</a></li>
                        <li><a href="#">Services</a></li>
                        <li><a href="#">Links</a></li>
                        <li><a href="#">Contact</a></li>
                    </ul>
                </div>

                <div class="col-lg-2 col-md-2">
                    <h6 class="h6_color">Follow me</h6>
                    <ul class="list-unstyled">
                        <li><a href="#">Twiter</a></li>
                        <li><a href="#">Facebook</a></li>
                        <li><a href="#">Linked In</a></li>
                    </ul>


                </div>

                <div class="col-lg-2 col-md-2">
                    <h6 class="h6_color">Coded with <span class="glyphicon glyphicon-heart"></span>by Rui Hu</h6>
                </div>


            </div>

        </div>
    </div>
</div>

<!--important file for bootstrap -->
<script src="<c:url value='/js/jquery-1.11.3.min.js'/>"></script>
<script src="<c:url value='/js/bootstrap.min.js'/>"></script>
<!-- for my google-analytics -->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-64302937-1', 'auto');
  ga('send', 'pageview');

</script>

</body>
</html>
