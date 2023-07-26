<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- <!DOCTYPE html>                 
<html>                          
<head>                          
<meta charset='UTF-8'>          
<title>게시판</title>   -->  
<style type="text/css">
.menu{text-align: right; margin-right: 100px;}

</style>        
<!-- </head>                         
<body> -->  
<%-- <jsp:include page="/WEB-INF/views/comm/menu.jsp"/>   --%>  

                      
<h1> 게시글정보변경</h1>                
<form action='${pageContext.request.contextPath}/bbs/edit.do' method='post'>
	<!-- 폼태그 -> submit통해서 글번호 값을 보내주기 위해 input 태그에 name을 객체 속성값(Vo)과 똑같이 만들어서 넣어줘야 함. 대신 글번호는 감추기. -->
	<input type="hidden" name="bbsNo" value='${bbsVo.bbsNo}' /><br>
	제목: <input <c:if test="${bbsVo.bbsWriter!=sessionScope.loginUser.memId}">readonly</c:if> 
		  type="text" name="bbsTitle" value='<c:out value="${bbsVo.bbsTitle}"/>'/><br>
	내용: <textarea ${bbsVo.bbsWriter!=loginUser.memId?'readonly':''} name="bbsContent" rows="5" cols="30"><c:out value="${bbsVo.bbsContent}"/></textarea><br>
	작성자: <c:out value="${bbsVo.bbsWriter}"/><br>
	등록일: <fmt:formatDate value="${bbsVo.bbsRegDate}" pattern="yyyy/MM/dd HH:mm:ss"/><br> 
<!-- 첨부파일들이 출력되도록 구현. -->
	<c:forEach items="${bbsVo.attachList}" var="vo">
		첨부파일 : <a href="${pageContext.request.contextPath}/bbs/down.do?attNo=${vo.attNo}"><c:out value="${vo.attOrgName}" /></a> <br> 
	</c:forEach>
	
<!--1. 로그인한 사용자가 작성한 글인 경우에만 저장 버튼과 삭제 버튼을 출력 -->
<!--2. 삭제 버튼을 클릭하면, 삭제여부를 확인하고, 삭제하겠다고 응답한 경우에만 삭제 -->
	
	<!-- 자신이 작성한 글이 아닌 경우, 제목과 내용을 키보드로 입력할 수 없도록 구현 -->
	<c:if test="${bbsVo.bbsWriter==sessionScope.loginUser.memId}"> <!-- sessionScope 생략해도 알아서 검색함. -->
	
	<input type="submit" value="저장"/>

	<a id="delLink" href='${pageContext.request.contextPath}/bbs/del.do?bbsNo=${bbsVo.bbsNo}'>
	<button type="button">삭제</button>
	</a>
	
	</c:if>
	
	<!--<c:url value="/bbs/del.do" var="delUrl">
	<c:param name="bbsNo">${bbsVo.bbsNo}</c:param>
	</c:url>
	<a href='${delUrl}'><button type="button">삭제2</button></a> -->
	

</form>

<!-- 댓글 내용을 입력하고 submit 버튼을 클릭하면, reply 테이블에 댓글내용이 저장(insert)되도록 구현 -->
<hr>
<form id="replyform" action="${pageContext.request.contextPath}/reply/add.do" method="post" >
	<input type="hidden" name="repBbsNo" value="${bbsVo.bbsNo}"/>
	<textarea name="repContent" rows="3" cols="30"></textarea>
	<input id="repSaveBtn" type="button" value="저장"/>
</form>

	<div id="replyList"></div>
	
	<template id="replyTemp">
		<div class="repContent"></div>
		<div class="repWriter"></div>
		<div class="repRegDate"></div>
		<input type="button" value="삭제" class="delBtn" data-no=""/>
		<hr>
	</template>


<script>
		//버튼을 눌렀을 때 X, alertMsg 파라미터값이 있을 때 경고창 띄우기.
		var msg = '${param.alertMsg}';
		 if(msg !== '') {
			alert(msg); 
		 }

	$('#delLink').on('click', function(
									   //ev -> 이벤트에 대한 모든 정보를 갖고 있는 이벤트 객체.
									   ){ 
		var ok = confirm('정말 삭제하시겠습니까?');
		if(!ok) { //true라면 삭제 기능 여전히 되니까~ false일 때 삭제 기능 못하도록 막아야 함.
			//ev.preventDefault(); -> 이벤트에 대한 브라우저의 기본동작을 취소.
			return false; // 이벤트 전파를 중단하고, 이벤트에 대한 브라우저의 기본동작을 취소
		}
	})	 
		 
		 
	//${pageContext.request.contextPath}/reply/list.do"로 GET방식 AJAX 요청을 전송하여
	//현재 글에 대한 댓글들을 받아오도록 구현.
	//댓글을 추가하면, 곧바로 댓글목록에 출력되도록 구현
	//각 댓글 아래에 삭제버튼 출력
	
	//댓글 저장 버튼 눌렀을 때도 호출하기 위해 함수로 감싸기.
	
	
	//<template> 엘리먼트의 내용은 content 속성을 사용하여 접근 
	// -> 제이쿼리 객체로 담아서 변수(제이쿼리 객체 담을 땐 보통 앞에 $붙임)에 넣기
	var $repTemp = $(document.querySelector('#replyTemp').content);
	
	function refreshReplyList(){
//	1. 로그인한 사용자가 작성한 댓글에만 삭제버튼 출력
//	2. 삭제버튼 클릭 시, 삭제여부를 묻는 창을 띄우고, 삭제하겠다고 선택한 경우에만 삭제
//	3. 댓글 저장 성공시, 댓글 입력란의 내용 초기화(기존의 내용 없애주기)
	$.ajax({
		  url: "${pageContext.request.contextPath}/reply/list.do", //요청주소
		  method: "GET",	 //요청방식
		  data: { repBbsNo : ${bbsVo.bbsNo} },  //요청파라미터!
		  dataType: "json" 	 //받는 응답데이터타입
		}).done(function( data) { 				//요청 전송 후 성공적으로 응답을 받았을 때 실행
		 	console.log(data);
		
		 	//댓글내용을 html 문자열 형태로 표현 -> 고치기 어렵고, 실수하기 쉬움. 
			/* var listHtml = '';
			for(var i=0; i<data.length; i++) {
				var repVo = data[i];
				console.log(repVo.repContent, repVo.repWriter, repVo.repRegDate);
				listHtml += '<div>'+repVo.repContent +'</div>';
				listHtml += '<div>'+repVo.repWriter +'</div>';
				listHtml += '<div>'+repVo.repRegDate +'</div>';
				if(repVo.repWriter == '${loginUser.memId}') {
				listHtml += '<input data-no="'+repVo.repNo+'" type="button" class="delBtn" value="삭제"/>';
				}
				listHtml += '<hr>';

			}
			console.log(listHtml);
			//listHtml 값을 id="replyList"인 div 엘리먼트의 내용으로 출력
			$("#replyList").html(listHtml); */
			
			var listHtml = [];
			for(var i=0; i<data.length; i++) {
					var repVo = data[i];
			  /*console.log(repVo.repContent, repVo.repWriter, repVo.repRegDate); 
				listHtml.push( $('<div>').text(repVo.repContent)); // -> <div>repVo.repContent</div>를 배열 listHtml에 넣기.
				listHtml.push( $('<div>').text(repVo.repWriter));
				listHtml.push( $('<div>').text(repVo.repRegDate));
				if(repVo.repWriter == '${loginUser.memId}') {
				//listHtml += '<input data-no="'+repVo.repNo+'" type="button" class="delBtn" value="삭제"/>';
				listHtml.push( $('<input>').attr({'data-no':repVo.repNo, 'type':"button"}) 
											// .attr('data-no', repVo.repNo).attr('type', "button")
										   .addClass('delBtn')//.attr('class', delBtn)
										   .val('삭제'));       //.attr('value', "삭제"));
				}
				listHtml.push($('<hr>')); */
				
				var $newRep = $repTemp.clone();
				
				//$('div', $newRep) or
				$newRep.find('.repContent').text(repVo.repContent); 
				//템플릿 안에 있는 div 한 줄씩 가져오는 메소드: .find()
				//가져온 특정 div 태그에 내용 붙이기 메소드 : .text() 
				$newRep.find('.repWriter').text(repVo.repWriter);
				$newRep.find('.repRegDate').text(repVo.repRegDate);
				
				if(repVo.repWriter == '${loginUser.memId}') {
					$newRep.find('.delBtn').attr('data-no', repVo.repNo);
				} else {
					$newRep.find('.delBtn').remove(); //해당 댓글 작성자가 아니라면 댓글리스트에서 삭제 버튼 지우기
				}
				
				listHtml.push($newRep);

			}
			console.log(listHtml);
			//listHtml 값을 id="replyList"인 div 엘리먼트의 내용으로 출력
			//$("#replyList").html(listHtml);
			$("#replyList").empty().append(listHtml);
			
		}).fail(function( jqXHR, textStatus ) { //요청 처리에 오류가 발생했을 때 실행.
		  alert( "Request failed: " + textStatus );
		});		
	}
	//삭제버튼을 클릭하면 해당 댓글이 삭제되도록,
	//ReplyController.java,ReplyService.java,ReplyServiceImpl.java,ReplyDao.java
	//,ReplyMapper.xml 파일을 변경.
	$('#replyList').on('click', '.delBtn', function(){
//		alert('삭제!' + $(this).attr('data-no'));
		var ok = confirm('정말 삭제하시겠습니까?');
		if(!ok) return; //return : 함수 실행을 종료하고 시작부분으로 돌아감 
			
		$.ajax({
			  url: "${pageContext.request.contextPath}/reply/del.do", //요청주소
			  method: "GET",	 //요청방식
			  data: { repNo : $(this).attr('data-no') },  //요청파라미터
			  dataType: "json" 	 //응답데이터타입
			  //"json"으로 설정하면, 응답으로 받은 JSON 문자열을 자바스크립트 객체로 변환하여
			  //응답처리함수(done())에게 인자로 전달
			}).done(function( msg ) { 				//요청 전송 후 성공적으로 응답을 받았을 때 실행
				
				if(msg.result == 0) {
					alert("해당 댓글 작성자만 삭제할 수 있습니다.");   
					
				} else{
					alert(msg.num1 + "번 댓글 삭제");
				}   
				
				refreshReplyList(); 				//삭제 후에 댓글 목록을 다시 받아오도록 하는 부분.
				
			}).fail(function( jqXHR, textStatus ) { //요청 처리에 오류가 발생했을 때 실행.
			  alert( "Request failed: " + textStatus );
			});	
	});
	
	refreshReplyList();
		 

	//저장버튼을 클릭했을 때, AJAX로 댓글 저장 요청을 전송
	//AJAX
	//(1) XmlHttpRequest 객체 사용
	//(2) fetch() 함수 사용
	//(3) $.ajax() 메서드 사용 <- JQuery 사용하는 방법
	$("#repSaveBtn").click(function() {
		
		$.ajax({
		  url: "${pageContext.request.contextPath}/reply/add.do", //요청주소
		  method: "POST",	 //요청방식
		  data: { repBbsNo : ${bbsVo.bbsNo}, repContent : $('[name="repContent"]').val() },  //객체타입 요청파라미터, 제이쿼리는 value 대신 val사용.
		  //data: 'repBbsNo=${bbsVo.bbsNo}&repContent='+$('[name="repContent"]').val()       //문자열 요청파라미터
		  //data: $('#replyform').serialize();       -> 파라미터 형식으로 바꿔주는 메소드 사용. 
		  										   //-> form안에 입력요소가 많다면 이 방법이 제일 좋음.
		  dataType: "json" 	 //응답데이터타입
		  //"json"으로 설정하면, 응답으로 받은 JSON 문자열을 자바스크립트 객체로 변환하여
		  //응답처리함수(done())에게 인자로 전달
		}).done(function( msg ) { 				//요청 전송 후 성공적으로 응답을 받았을 때 실행
			
			refreshReplyList();
			
			//지금 alert창에 뜨는 msg == '{"result": 1, "ok": "true"}'; -> 작은 따옴표 씌워진 문자열 형태
			//var data = JSON.parse(msg); //JSON(문자열)을 자바스크립트 객체로 변환
			//data == {result: 1, ok: true}; -> 객체 타입! msg.result로 key값 나타낼 수 있음.
		  alert(msg.result + "개의 댓글 저장"); //"(result=)1개의 댓글 저장"이라고 출력되도록
		  $('[name="repContent"]').val('') // -> textarea의 내용을 불러오는 방법 -> ''로 내용을 없애기.
		}).fail(function( jqXHR, textStatus ) { //요청 처리에 오류가 발생했을 때 실행.
		  alert( "Request failed: " + textStatus );
		});	
	})
</script>	



<!-- </body>                         
</html>  -->                        
		
<!-- 1. 회원정보변경 화면에서 이름과 포인트를 변경하고 submit 버튼을 클릭하면,
	MemEditServlet 클래스의 doPost 메서드가 실행되도록 memEdit.jsp 파일을 변경하세요.
2. 회원정보변경 화면에서 아이디는 키보드로 값을 입력(변경)할 수 없도록 memeEdit.jsp 파일을 변경하세요.	
3. MemEditServlet 클래스의 doPost 메서드에서 사용자가 입력한 정보에 따라서
	데이터베이스의 회원 정보(이름, 포인터)가 변경되도록 
	MemEditServelt.java, MemberDao.java, MemberDaoBatis.java, MemberMapper.xml 파일을 변경하세요.  -->