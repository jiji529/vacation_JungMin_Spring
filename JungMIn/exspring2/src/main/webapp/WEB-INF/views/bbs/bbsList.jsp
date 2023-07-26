<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>                
<html>                         
<head>                         
<meta charset='UTF-8'>         
<title>게시판</title> 
<style type="text/css">
html, body {width: 100%; height: 100%; text-align: center;}
td {text-align: center;}
h1 {color: pink;}
table {margin: 0 auto; margin-top: 20px; border: 1px solid black; border-collapse: collapse;}
.menu{text-align: right; margin-right: 100px;}

button{margin: 10px; padding: 5px; font:bold; background-color : pink; color: white;}
button : hover{background-color : blue; color: white;}
a{text-decoration : none; color: black;} 

</style>                            
</head>                        
<body>
<%-- <jsp:include page="/WEB-INF/views/comm/menu.jsp"/>   --%>  
<h1>게시글 목록</h1>
<a href="${pageContext.request.contextPath}/bbs/add.do"><button type="button">글쓰기</button></a>

<table border="1">
	<thead>
		<tr align="center"><th>글번호</th><th>제목</th><th>작성자</th><th>등록일시</th></tr>
	</thead>
	<tbody>
	<c:forEach var="vo" items="${bbsList}">
		<tr>
			<td>${vo.bbsNo}</td>
			<td>
				<a href="${pageContext.request.contextPath}/bbs/edit.do?bbsNo=${vo.bbsNo}">
				<c:out value="${vo.bbsTitle}"/>
				</a>
			</td>
			<td><c:out value="${vo.bbsWriter}"/></td>
			<!-- 등록일시가 2023/06/29 13:17:48 형식으로 출력되도록 변경 : 태그 라이브러리(fmt) 추가 + formatDate 태그 입력.-->
			<td><fmt:formatDate value="${vo.bbsRegDate}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
		</tr> 
	</c:forEach>
	</tbody>
</table>

<form id="searchForm" action="${pageContext.request.contextPath}/bbs/list.do">
	<select name="searchType">
	<!--<option value="title" ${searchVo.searchType=='title'?'selected':''}>제목</option>	
		<option value="content" ${searchVo.searchType=='content'?'selected':''}>내용</option>	
		<option value="total" ${searchVo.searchType=='total'?'selected':''}>제목+내용</option>	 -->
		
		<option value="title">제목</option>	
		<option value="content">내용</option>	
		<option value="total">제목+내용</option>	
	</select>
	<script type="text/javascript">
	if('${searchVo.searchType}') {
		document.querySelector('[name="searchType"]').value = '${searchVo.searchType}';
//		$('[name="searchType"]').val(${searchVo.searchType});
	}
	</script>
	<input type="text" name="searchWord" value="${searchVo.searchWord}"/>
	<input type="hidden" name="currentPageNo" value="1"/>
	<input type="submit" value="검색" /> 
</form>

${searchVo.pageHtml}
<script type="text/javascript">
 /* 자바 스크립트로 페이지 이동하는 방법 */
 function goPage(n) {

 	document.querySelector('[name="currentPageNo"]').value = n;
 	//form 전송 메소드 : submit
 	document.querySelector('#searchForm').submit();
 	
}
 
 
 
</script>

</body>                        
</html>                    