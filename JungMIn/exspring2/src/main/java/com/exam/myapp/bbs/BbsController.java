package com.exam.myapp.bbs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.exam.myapp.member.MemberVo;

@Controller
@RequestMapping("/bbs/") //현재 컨트롤러 클래스 내부의 모든 메서드들의 공통 경로 설정 -> 각 메소드에서 생략 가능.
public class BbsController {

	//싱글톤 패턴 구현한 내용
	//private MemberService bbsService = MemberServiceImpl.getInstance();

	@Autowired
	private BbsService bbsService;
	
	@GetMapping("list.do") // @RequestMapping(value="list.do", method = RequestMethod.GET) 
	public String list(Model model, SearchVo vo) {
		int cnt = bbsService.selectBbsCount(vo);
		vo.setTotalRecordCount(cnt);
		vo.makePageHtml(); //페이지 처리에 필요한 필요한 값들 계산
		
		List<BbsVo> list = bbsService.selectBbsList(vo);
		
		//jsp에서 쓰기 위해 model에 넣기.
		model.addAttribute("bbsList", list);
		
		return "bbs/bbsList";
		}
	
	@GetMapping("add.do") //@RequestMapping(value="add.do", method = RequestMethod.GET)
	public String addform() {
		return "bbs/bbsAdd";
	}
		
	@PostMapping("add.do") //@RequestMapping(value="add.do", method = RequestMethod.POST)
	public String add(BbsVo vo
			, HttpSession session
			, @SessionAttribute("loginUser") MemberVo mvo //지정한 세션속성값("loginUser")을 이 변수에 주입(전달) <- MemberVo mvo = (MemberVo)session.getAttribute("loginUser");
		) {
		
		//vo : 접속자가 작성한 글(bbs 테이블에서 하나의 column)
		vo.setBbsWriter(mvo.getMemId());
		
		int n = bbsService.insertBbs(vo);
		System.out.println(n + "개의 글 추가");
		return "redirect:/bbs/list.do"; 	
		}
	
	@GetMapping("edit.do") //@RequestMapping(value="edit.do", method = RequestMethod.GET)
	public String editform(int bbsNo, Model model) {
		BbsVo vo = bbsService.selectBbs(bbsNo);
		model.addAttribute("bbsVo", vo);
		return "bbs/bbsEdit";
	}
	
	@PostMapping("edit.do") //@RequestMapping(value="edit.do", method = RequestMethod.POST)
	public String edit(BbsVo vo, @SessionAttribute("loginUser") MemberVo mvo
					   //, Model model
					  ) {
		
		//과제로 스스로 한 부분. -> 컨트롤러에서만으로도 실행되도록 함.
		/*
		 * String UserId = mvo.getMemId(); //접속자 아이디.
		 * 
		 * BbsVo bvo = bbsService.selectBbs(vo.getBbsNo()); 
		 * String WriterId =bvo.getBbsWriter(); //글 작성자 아이디.
		 * 
		 * if(!WriterId.equals(UserId)){ 
		 * model.addAttribute("alertMsg", "해당 글 작성자만 수정 및 삭제할 수 있습니다."); 
		 * return "redirect:/bbs/edit.do?bbsNo=vo.getBbsNo()";
		 * 
		 * } else if(WriterId.equals(UserId)) {
		 * 
		 * bbsService.updateBbs(vo); }
		 */
		
		vo.setBbsWriter( mvo.getMemId());
		bbsService.updateBbs(vo);
		
		return  "redirect:/bbs/list.do";	
		}
	
	@GetMapping("del.do")  //@RequestMapping(value="del.do", method = RequestMethod.GET)
	public String delform(BbsVo vo, @SessionAttribute("loginUser") MemberVo mvo
						  //, Model model
						 ) {
		
		//과제로 스스로 한 부분. -> 컨트롤러에서만으로도 실행되도록 함.
		/*String UserId = mvo.getMemId(); 		//접속자 아이디.
		
		BbsVo bvo = bbsService.selectBbs(bbsNo);
		String WriterId = bvo.getBbsWriter();	//글 작성자 아이디.
		
		if(!WriterId.equals(UserId)){
			model.addAttribute("alertMsg", "해당 글 작성자만 수정 및 삭제할 수 있습니다.");
			return  "redirect:/bbs/edit.do?bbsNo=" + bbsNo;
			
		} else if(WriterId.equals(UserId)) {

			bbsService.deleteBbs(bbsNo);
		} */
		
		vo.setBbsWriter( mvo.getMemId());
		bbsService.deleteBbs(vo);
		
		return  "redirect:/bbs/list.do";	
		}
	
	
	//컨트롤러 메서드가 인자로 HttpServletResponse -> OutputStream, Writer를 받고,
	//반환타입이 void이면,
	//직접 응답을 처리(전송)했다고 판단하여 스프링은 뷰에 대한 처리(return)를 하지 않는다.
	@GetMapping("down.do")                               
	public void down(int attNo, HttpServletResponse resp) { //?뒤의 파라미터 값이름 같은 파라미터 이름 적기.
		
		AttachVo vo = bbsService.selectAttach(attNo); //DB에서 다운로드할 첨부파일 정보 조회
		
		// 디스크 상에서 첨부파일 가져오기 / Service(impl)에 있는 saveFile 주소 활용해야함. / 실제 파일위치를 담고 있는 f 객체.
		File f = bbsService.getAttachFile(vo);
		
		resp.setContentLength((int) f.length()); //응답메시지 본문(파일)의 크기 설정
		
		resp.setContentType("application/octet-stream");
		//resp.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		
		//다운로드 파일을 저장할 때 사용한 디폴트 파일명 설정
		//지원하는 브라우저에 따라서 다른 처리가 필요할 수도 있다.
		/*
		 * try 
		 * { 
		 * String fname = URLEncoder.encode(vo.getAttOrgName(),"UTF-8").replace("+", "%20"); 
		 * resp.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fname); } 
		 * catch(UnsupportedEncodingException e1) { 
		 * e1.printStackTrace(); 
		 * }
		 */
		
		String cdv = ContentDisposition.attachment().filename(vo.getAttOrgName(), StandardCharsets.UTF_8).build().toString();
		resp.setHeader(HttpHeaders.CONTENT_DISPOSITION, cdv);
		

		try {
			//파일 f의 내용을 응답객체(의 출력 스트림)에 복사(전송)
			FileCopyUtils.copy(new FileInputStream(f), resp.getOutputStream()); //-> 응답객체에 입력 -> 브라우저 전송
		} catch (IOException e) {
			e.printStackTrace();
		} 
			
		}
	
}






