package com.exam.myapp.reply;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.exam.myapp.member.MemberVo;

@Controller
@RestController //현재 클래스의 모든 요청처리 메서드에 @ResponseBody를 적용
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	
	@GetMapping("/reply/list.do")
//	@ResponseBody
	public List<ReplyVo> list(int repBbsNo) {
		List<ReplyVo> repList = replyService.selectReplyList(repBbsNo);
		
		return repList;
	}
	
	@PostMapping("/reply/add.do") 
//	@ResponseBody //메서드의 반환값을 그대로 응답메시지 내용으로 전송
	public HashMap<String, Object> add(@SessionAttribute("loginUser") MemberVo mvo, ReplyVo vo) {

		vo.setRepWriter(mvo.getMemId());
		
		int num = replyService.insertReply(vo);
		
		MemberVo v = new MemberVo();
		v.setMemId("a001");
		v.setMemName("고길동");
		v.setMemPoint(10);
		
		//자바스크립트로 위의 v와 동일한 데이터를 저장한 객체를 정의	
		//작은 따옴표, 큰 따옴표 모두 사용 가능. memId, memName, memPoint에도 따옴표 사용가능 but 속성에 공백있을 때는 꼭 써줘야함.
		//var v = {memId: "a001", memName: "고길동", memPoint: 10}; //객체 : 중괄호	
		
		//JSON이 자바스크립트 객체 표현과 동일하지만 2가지 차이점 존재!
		//(1)문자열은 반드시 큰따옴표만 가능(작은 따옴표 사용 불가)
		//(2)객체의 속성이름은 반드시 문자열로 표현
		String jsonStr = "{\"memId\": \"a001\", \"memName\": \"고길동\", memPoint: 10}"; 
		
		//형태가 복잡하니까 자동으로 json과 자바를 상호변환해주는 라이브러리 -> Jackson(스프링 초창기부터!), Gson(스프링 최신버전 사용가능),... 등
		//<아래 num부분 표현하기 위해 jackson사용해보자!>
		
		HashMap<String, Object> map = new HashMap<String, Object>(); 
		map.put("ok", true);
		map.put("result", num);
		//num : 저장된 댓글 갯수
		
		//return "redirect:/bbs/edit.do?bbsNo="+vo.getRepBbsNo();
		//return "{\"ok\": \"true\", \"result\": "+num+"}";
		return map; // 그냥 이대로 쓰면"/WEB_INF/views/로 포워딩 시켜버림." -> @ResponseBody 사용하는 이유임.
		}
	
//	@GetMapping("/reply/del.do")
//	public String del(int repNo) {
//		replyService.deleteReply(repNo);
//		
//		return  "/bbs/list.do";
//	}
	
	@GetMapping("/reply/del.do") 
//	@ResponseBody //메서드의 반환값을 그대로 응답메시지 내용(json)으로 전송
	public HashMap<String, Object> del(@SessionAttribute("loginUser") MemberVo mvo, 
										ReplyVo vo) {

		vo.setRepWriter(mvo.getMemId());  //Mapper의 SQL문에 사용자 ID가 전달됨.
		
		int num = replyService.deleteReply(vo); //삭제되지 않은 경우(작성자와 사용자 아이디가 다른 경우) '0'으로 나옴.
		
		//잘 보내졌는지 확인하는 방법.
		HashMap<String, Object> map = new HashMap<String, Object>(); 
		map.put("num1", vo.getRepNo());
		map.put("result", num);

		return map; // 그냥 이대로 쓰면"/WEB_INF/views/로 포워딩 시켜버림." -> @ResponseBody 사용하는 이유임.
		}
	
}
