<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>                 
<html>                          
<head>                          
<meta charset='UTF-8'>          
<title>회원관리</title> 
<style type="text/css">
.menu{text-align: right; margin-right: 100px;} 

button{margin: 10px; padding: 5px; font:bold; background-color : pink; color: white;}
button : hover{background-color : blue; color: white;}
a{text-decoration : none; color: black;}          
</style> 
</head>                         
<body>    
<%-- <jsp:include page="/WEB-INF/views/comm/menu.jsp"/> 1234 --%>                  
<h1>로그인</h1>                
<form action='${pageContext.request.contextPath}/member/login.do' method='post'>
	아이디: <input type="text" name="memId" value=""> <br>
	비밀번호: <input type="password" name="memPass" value=""> <br>
	<input type="submit" value="로그인"/>
</form>
</body>                         
</html>                         
		
