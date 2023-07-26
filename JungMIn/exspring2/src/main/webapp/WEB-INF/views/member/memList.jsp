
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- <!DOCTYPE html>                
<html>                         
<head>                         
<meta charset='UTF-8'>         
<title>회원관리</title>   -->         
<style>
.menu{text-align: right; margin-right: 100px;}

button{margin: 10px; padding: 5px; font:bold; background-color : pink; color: white;}
button : hover{background-color : blue; color: white;}
a{text-decoration : none; color: black;} 
</style>                       
<%-- </head>                        
<body> 
<!-- 화면 상단에 로그인 여부에 따른 상태변화 창 표현 -->
<jsp:include page="/WEB-INF/views/comm/menu.jsp"/>  --%>
<h1>회원목록</h1>
<c:forEach var="vo" items="${memberList}">
	<p>
	<%-- ${vo.memId} : ${vo.memPass} : ${vo.memName} : ${vo.memPoint} --%>
	<%-- EL 대신 안전하게 출력하기 위함. ${vo.memPoint}는 int부분이라서 <>씌워주지 않음 --%>
	<a href='${pageContext.request.contextPath}/member/edit.do?memId=${vo.memId}'><c:out value="${vo.memId}"/></a> :
	<c:out value="${vo.memName}"/> :
	${vo.memPoint}
	<%--<a href='${pageContext.request.contextPath}/member/del.do?memId=<c:out value="${vo.memId}"/>'> <button type="button">삭제</button></a>--%>
	<!-- JSTL 태그의 scope와 var 속성을 사용하면,
	JSTL 태그 실행 결과를 현재 위치에 출력하지 않고,
	지정한 scope에 지정한 이름(var)의 속성을 저장한 후,
	EL에서 읽어서 사용 가능. -> 자동완성 눌렀을 때 scope, var가 있으면 모두 사용 가능. scope 생략하면 pageContext에 저장되는 것. -->
	<c:url value="/member/del.do" var="delUrl">
	<!-- c:url뿐만 아니라 다른 주소를 사용하는 곳에서도 param 이용하여 변수(var)지정하고 사용가능. -->
	<c:param name="memId">${vo.memId}</c:param>
	</c:url>
	<a href='${delUrl}'><button type="button">삭제</button></a>
	</p> 
</c:forEach>

<!-- </body>                        
	</html>  -->                       