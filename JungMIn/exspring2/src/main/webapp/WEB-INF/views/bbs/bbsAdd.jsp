<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!-- <!DOCTYPE html>                 
<html>                          
<head>                          
<meta charset='UTF-8'>          
<title>게시판</title>  --> 
<style type="text/css">
.menu{text-align: right; margin-right: 100px;}

button{margin: 10px; padding: 5px; font:bold; background-color : pink; color: white;}
button : hover{background-color : blue; color: white;}
a{text-decoration : none; color: black;} 
</style>
<!-- </head>                         
<body>  -->  
<%-- <jsp:include page="/WEB-INF/views/comm/menu.jsp"/>   --%>  

<!-- 글쓰기 화면에 첨부파일을 입력할 수 있도록 추가하고, -->
<!-- Bbs 클래스에 첨부파일을 받을 수 있는 변수(필드)를 추가 -->

<!-- 파일(첨부파일)이 포함된 경우 아래와 같은 방식(post)으로 인코딩할수 없음. -->
<!-- bbsTitle=입력한제목&bbsContent=입력한내용&bbsFile=선택한파일내용 -->
                       
<h1> 게시글 추가 </h1>   
<!-- 파일을 포함하여 전송하는 form 엘리먼트는 enctype="multipart/form-data"으로 설정 -->             
<form action='${pageContext.request.contextPath}/bbs/add.do' method='post' enctype="multipart/form-data">
	제목: <input type="text" name="bbsTitle" value=""> <br>
	내용: <textarea id="bbsContent" name="bbsContent" rows="5" cols="30"></textarea><br>
	첨부파일1: <input type="file" name="bbsFile"/><br>
	첨부파일2: <input type="file" name="bbsFile"/><br>	
	<input type="submit"/>
</form>
<!-- </body>                         
</html>   -->                       
		
