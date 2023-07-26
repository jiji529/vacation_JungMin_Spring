package com.exam.myapp.bbs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@Transactional //이 객체의 모든 메소드들을 각각 하나의 트랜잭션으로 정의
public class BbsServiceImpl implements BbsService{
	
	@Autowired
	private BbsDao bbsDao;
	
	@Autowired
	private AttachDao attachDao;
	
	@Value("${bbs.upload.path}") //지정한 값을 스프링이 변수에 주입(저장)
	private String uploadPath; //게시판 첨부파일 저장 위치.

	@PostConstruct // 스프링이 현재 객체의 초기화 작업이 완료된 후 실행
	public void init() {
		new File(uploadPath).mkdirs(); //uploadPath 디텍토리가 없으면 생성하라는 생성자 
	}
	
	
	@Override
	public List<BbsVo> selectBbsList(SearchVo vo) {
		return bbsDao.selectBbsList(vo);
	}
	@Override
	public int insertBbs(BbsVo vo) {
		int num = bbsDao.insertBbs(vo);
		
		for (MultipartFile f : vo.getBbsFile()) {
			if(f.getSize()<=0) continue; //파일크기가 0인 경우, 저장하지 않음. 
			System.out.println("파일명: " + f.getOriginalFilename());
			System.out.println("파일크기: " + f.getSize());
			
			
			String fname = null; 
			File saveFile = null; 
			do {
				fname = UUID.randomUUID().toString(); // UUID: 중복될 확률이 극도로 낮은 unique한 아이디를 만들어주는 클래스.
				saveFile = new File(uploadPath, fname); //중복될 확률이 극도로 낮은 랜덤 문자열 생성 -> 파일명은 고유한 이름으로 변경해서 저장해줘야함.
			} while (saveFile.exists()); //fname가 현재 하드디스크 상에서 중복되는 값이 있는지 확인 -> 없으면 파일 만들라고(do)
			
			try {
				f.transferTo(saveFile); //파일 f의 내용을 saveFile에 복사(저장)

				AttachVo attVo = new AttachVo();
				attVo.setAttBbsNo(vo.getBbsNo()); //첨부파일이 속한 게시글 번호
				attVo.setAttOrgName(f.getOriginalFilename());
				attVo.setAttNewName(fname);	//첨부파일저장이름
				
				attachDao.insertAttach(attVo);
				
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e); //첨부파일 저장 중 오류발생 시 트랜잭션이 롤백되도록 
			}
			
		}
		return num; 
	}

	@Override
	public int deleteBbs(BbsVo vo) {
		BbsVo bbsVo = bbsDao.selectBbs(vo.getBbsNo()); //게시글 정보 조회 -> bbsVo : 첨부파일 번호를 포함하고 있음.
		if(!bbsVo.getBbsWriter().equals(vo.getBbsWriter())) { //실제 글 작성자: bbsVo.getBbsWriter()
															  //현재 로그인한 사람(컨트롤러에서 vo에 담음): vo.getBbsWriter()
			return 0;
		}
		 
		for (AttachVo attVo : bbsVo.getAttachList()) { //해당 게시글의 첨부파일 정보를 하나씩 꺼내서~.
			new File(uploadPath, attVo.getAttNewName()).delete(); //하드디스크 상의 파일 삭제 / AttNewName()가 파일 상에 저장된 이름 -> 삭제 시 이 이름 사용
			attachDao.deleteAttach(attVo.getAttNo()); //테이블에서 첨부파일 삭제
		}
		
		return bbsDao.deleteBbs(vo.getBbsNo()); //게시글 삭제
	}

	@Override
	public BbsVo selectBbs(int memId) {
		return bbsDao.selectBbs(memId);
	}

	@Override
	public int updateBbs(BbsVo vo) {
		return bbsDao.updateBbs(vo);
	}

	@Override
	public AttachVo selectAttach(int attNo) {
		return attachDao.selectAttach(attNo);
	}

	@Override
	public File getAttachFile(AttachVo vo) {
		return new File(uploadPath, vo.getAttNewName()); //vo.getAttNewName() -> 실제 하드디스트 상에 저장된 이름.
	}


	@Override
	public int selectBbsCount(SearchVo vo) {

		return bbsDao.selectBbsCount(vo);
	}

}
