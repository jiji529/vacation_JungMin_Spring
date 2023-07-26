<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%-- <!DOCTYPE html>                 
<html>                          
<head>                          
<meta charset='UTF-8'>          
<title>회원관리</title> 
<style type="text/css">
.menu{text-align: right; margin-right: 100px;}
</style>           
</head>                         
<body>   
<jsp:include page="/WEB-INF/views/comm/menu.jsp"/>    --%>                    
<h1> 회원추가</h1>                
<form action='${pageContext.request.contextPath}/member/add.do' method='post'>
	아이디: <input type="text" name="memId" value=""> <br>
	비밀번호: <input type="password" name="memPass" value=""> <br>
	이름: <input type="text" name="memName" value=""> <br>
	포인트: <input type="number" name="memPoint" value="0"> <br>
	<input type="submit" value="입력"/>
</form>
<!-- </body>                         
</html>    -->                      
		
