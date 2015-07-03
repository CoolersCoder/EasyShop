<%-- 
    Document   : pager
    Created on : 2015-6-3, 14:56:21
    Author     : Rui Hu
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	function _go() {
		var pc = $("#pageCode").val();
		if(!/^[1-9]\d*$/.test(pc)) {
			alert('Please input valid page！');
			return;
		}
		if(pc > ${pb.tp}) {
			alert('Please input valid page！');
			return;
		}
		location = "${pb.url}&pc=" + pc;
	}
</script>


<div class="divBody">
  <div class="divContent">
      <%--previous page implement--%>
      <c:choose>
          <c:when test="${pb.pc eq 1}"><span class="spanBtnDisabled">Previous</span>  </c:when>
          <c:otherwise><a href="${pb.url}&pc=${pb.pc-1}" class="aBtn bold">Previous</a></c:otherwise>     
      </c:choose>
  
          
          <c:choose>
              <c:when test="${pb.tp <= 6}">
                  <c:set var="begin" value="1"/>
                  <c:set var="end" value="${pb.tp }"/>                         
              </c:when>
              <c:otherwise>
                  <c:set var="begin" value="${pb.pc-2}"/>
                  <c:set var="end" value="${pb.pc + 3}"/>
                  <c:if test="${begin < 1}">
                      <c:set var="begin" value="1"/>
                      <c:set var="end" value="6"/>
                  </c:if>
                  <c:if test="${end > pb.tp}">
                      <c:set var="begin" value="${pb.tp-5}"/>
                      <c:set var="end" value="${pb.tp}" />
                  </c:if>
              </c:otherwise>
          </c:choose>

          
          
          
          <c:forEach begin="${begin }" end="${end }" var="i">
              <c:choose>
                  <c:when test="${i eq pb.pc }">
                       <span class="spanBtnSelect">${i}</span>
                  </c:when>
              
              <c:otherwise>
                        <a href="${pb.url }&pc=${i}" class="aBtn">${i}</a>       
               </c:otherwise>      
                </c:choose> 
          </c:forEach>
          
          
     <c:if test="${end<pb.tp}">        
          <span class="spanApostrophe">...</span>
     </c:if>

 
     

      <c:choose>
          <c:when test="${pb.pc eq pb.tp}"><span class="spanBtnDisabled">Nextpage</span></c:when>
          <c:otherwise><a href="${pb.url}&pc=${pb.pc+1}" class="aBtn bold">Nextpage</a> </c:otherwise>
      </c:choose>
  
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 
    <span>Totol ${pb.tp} page</span>
    <span>to</span>
    <input type="text" class="inputPageCode" id="pageCode" value="${pb.pc}"/>
    <span>page</span>
    <a href="javascript:_go();" class="aSubmit">Submit</a>
  </div>
</div>