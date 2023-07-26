<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!-- 로그인이 된 경우, 로그인한 사용자 이름과 로그아웃 링크를 출력 -->

<c:if test="${loginUser!=null}"> 
	<div class="menu">
		지현이의 일기장에 오신 것을 환영합니다. ${loginUser.memName}님❤️
		<a href='${pageContext.request.contextPath}/member/logout.do'><button type="button">로그아웃</button></a> 
		<a href='${pageContext.request.contextPath}/member/list.do'><button type="button">회원관리</button></a>	
		<a href='${pageContext.request.contextPath}/bbs/list.do'><button type="button">게시판</button></a>
		<hr>		 
	</div>
</c:if>

<!-- 로그인이 되어있지 않은 경우, 로그인과 회원가입(추가) 링크를 출력 -->
<c:if test="${loginUser==null}">
	<div class="menu">
		지현이의 일기장에 ❤️로그인❤️해주세요
		<a href='${pageContext.request.contextPath}/member/login.do'><button type="button">로그인</button></a> 
		<a href='${pageContext.request.contextPath}/member/add.do'><button type="button">회원추가</button></a>  
		<a href='<c:url value="/member/add.do"/>'><button type="button">회원추가</button></a>
		<hr>
	</div>  
</c:if>  
    
    