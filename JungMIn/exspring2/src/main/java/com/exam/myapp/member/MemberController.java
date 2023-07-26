package com.exam.myapp.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class MemberController {

	//싱글톤 패턴 구현한 내용
	//private MemberService memberService = MemberServiceImpl.getInstance();

	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/member/list.do", method = RequestMethod.GET)
	public String list(Model model) {
		
		List<MemberVo> list = memberService.selectMemberList();
		
		model.addAttribute("memberList", list);
		
		return "member/memList";
		}
	
	@RequestMapping(value="/member/add.do", method = RequestMethod.GET)
	public String addform() {
		return "member/memAdd";
	}
		
	@RequestMapping(value="/member/add.do", method = RequestMethod.POST)
	public String add(MemberVo vo) {
		memberService.insertMember(vo);
		return "redirect:/member/list.do"; 	
		}
	
	@RequestMapping(value="/member/edit.do", method = RequestMethod.GET)
	public String editform(String memId, Model model) {
		MemberVo vo = memberService.selectMember(memId);
		model.addAttribute("mvo", vo);
		return "member/memEdit";
	}
	
	@RequestMapping(value="/member/edit.do", method = RequestMethod.POST)
	public String edit(MemberVo vo) {
		memberService.updateMember(vo);
		return "redirect:/member/list.do";
		}
	
	@RequestMapping(value="/member/del.do", method = RequestMethod.GET)
	public String delform(String memId) {
		
		int n = memberService.deleteMember(memId);
		System.out.println(n + "명의 회원 삭제");
		
		return "redirect:/member/list.do";		
		}
	
	//로그인 동작이 수행되도록 아래 메서드들을 변경
	@RequestMapping(value="/member/login.do", method = RequestMethod.GET)
	public String loginform() {

		return "member/login"; //절대경로
	}
		
	@RequestMapping(value="/member/login.do", method = RequestMethod.POST)
	public String login(MemberVo vo, HttpSession session) {

		//id, pw 조회 기능. 
		MemberVo mvo = memberService.selectLogin(vo);
				
		if(mvo==null) { //로그인 실패시, 로그인 화면으로 이동
			System.out.println("로그인 안 됐습니다.");
			
			return "redirect:/member/login.do";
			
		}else { //-> 로그인 성공 시 회원정보(mvo)랑 로그인 여부를 session에 저장! -> model은 한 번 쓰고 사라짐.
			
			session.setAttribute("loginUser", mvo);
			
			System.out.println("로그인 됐습니다.");
			
			return "redirect:/member/list.do"; //회원목록화면으로 이동
		}
	}
	
	@RequestMapping(value="/member/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {

		session.invalidate(); //세선객체를 제거(후 다시 생성), 모든 속성들도 함께 삭제
		System.out.println("로그아웃 됐습니다.");
		
		return "redirect:/member/login.do";
			
		}
}






