package com.aloha.spring.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.spring.dao.PersonDTO;
import com.aloha.spring.dto.Board;
import com.aloha.spring.dto.Person;
import com.aloha.spring.dto.User;

@Controller					//Controller 지정하고 빈 등록
@RequestMapping("/request")	// [클래스 레벨 요청경로 매핑]"/request" 요청 경로로 매핑		
public class RequestController {
	
	// log 객체
	private static final Logger logger
						= LoggerFactory.getLogger(RequestController.class);
	
	
	// 컨트롤러 메소드
	
	/**
	 * @RequestMapping : 요청 경로 매핑
	 * - 요청 	: /request/board
	 * - 응답		: /request/board.jsp
	 * @return
	 */
//	@RequestMapping( value = "/request/board", method = RequestMethod.GET)
//	@RequestMapping( value = "/board", method = RequestMethod.GET)
	@RequestMapping("/board")
	public String request() {
		logger.info("[GET] - /request/board");
		return "request/board";
	}
	/**
	 * 경로 패턴 매핑
	 * @param no
	 * @return
	 */
	@RequestMapping(value = "/board/{no}", method = RequestMethod.GET)
	public String requestPath(@PathVariable("no") int no) {
		logger.info("[GET] - /request/board/{no}");
		logger.info("no : " + no);
		return "request/board";
	}

	@ResponseBody			// 반환 값을, 응답 본문에 직접 지정 
	@RequestMapping(value = "/board", method = RequestMethod.POST)
	public String requestPost(@RequestParam("no") int no)	{
		logger.info("[POST} - /request/board");
		logger.info("no : " + no);
		
		return "SUCCESS - no : " + no;
	}
	
	/**
	 * 파라미터 매핑
	 * 
	 * params 속성으로 요청 파라미너가 id 인 경우 매핑한다.
	 * /request/board?id=aloha
	 *	/request/board?id=aloha&age=20
	 *  
	 */
//	@RequestMapping(value = "/board", method = RequestMethod.GET, params = "id")
	@RequestMapping(value = "/board", method = RequestMethod.GET, params = {"id", "age"})
//	public String requetsParams(@RequestParam("id") String id) {
	public String requetsParams(@RequestParam("id") String id, @RequestParam("age") String age) {
//		logger.info("[GET] - /request/board?id=" + id);
		logger.info("[GET] - /request/board?id=" + id + "&age=" + age);
		logger.info("id : " + id);
		logger.info("age : " + age);
		return "reqeut/board";
	}
	/**
	 *  헤더 매핑
	 * @return
	 * * headers = "헤더명=값" 으로 지정하여 헤더를 매핑 조건으로 지정한다.
	 */
	@ResponseBody
	@RequestMapping(value = {"/board", "/board2"}, method = RequestMethod.POST
								, headers = "Content-Type=application/json")
//								, headers = "Content-Type=application/json", "헤더2", "헤더3")
	public String requestHeader() {
		logger.info("[POST} - /request/board");
		logger.info("헤더 매핑...");
		return "SUCCESS";
	}
	
	// PUT 매핑
	
	/**
	 * PUT 매핑
	 * @return
	 * 
	 * * @ResponseBody O : return "데이터" --> 응답 메시지(본문 : 데이터)
	 * * @ResponseBody X : return "뷰이름" --> 뷰 리졸버가 jsp 선택 -> 렌더링 -> html응답
	 */
	@RequestMapping(value = "/board", method = RequestMethod.PUT)
	public String requestPut() {
		logger.info("[PUT] - request/board");
		// 메인화면으로 리다이렉트
		return "redirect:/";
	}
	
	// 컨텐츠 타앱 매핑
	@ResponseBody
	@RequestMapping(value = {"/board"}, method = RequestMethod.POST
			, consumes = "application/xml")
	public String requestContentType() {
		logger.info("[POST} - /request/board");
		logger.info("컨텐츠 타입 매핑...");
		return "SUCCESS - xml";
	}
	
	// Accept 매핑
	/**
	 * Accept 매핑
	 * @return
	 * - Accept 헤더의 값으로 매핑
	 * - Accept 헤더 ?
	 *   : 응답 받을 컨텐츠 타입을 서버에게 알려주는 헤더
	 * - produces = "컨텐츠 타입"
	 */
	@ResponseBody
	@RequestMapping(value = {"/board"}, method = RequestMethod.POST
	, produces = "application/json")
	public Map<?,?> requestAccept() {
		logger.info("[POST} - /request/board");
		logger.info("Accept 매핑...");
		Map<String, String> map = new HashMap<>();
		map.put("result", "SUCCESS");
		return map;
	}
	
    @ResponseBody
    @RequestMapping(value = "/check/person", method = RequestMethod.POST)
    public String requestCheckPerson(Person person) {
        logger.info("[POST] - /reuqest/check");
        
        logger.info("::::: person :::::");
        logger.info(person.toString());
        
        //String[] hobbies = person.getHobby();
        List<String> hobbies = person.getHobby();
        for (String hobby : hobbies) {
            logger.info("hobby : " + hobby);
        }
        
        return "SUCCESS";
    }
    
    @ResponseBody
    @RequestMapping(value = "/check/personDTO", method = RequestMethod.POST)
    public String requestCheckPerson(PersonDTO personDTO) {
        logger.info("[POST] - /reuqest/check");
        
        logger.info("::::: person :::::");
        logger.info(personDTO.toString());
        
        //String[] hobbies = person.getHobby();
        List<String> hobbies = personDTO.getPerson().getHobby();
        for (String hobby : hobbies) {
            logger.info("hobby : " + hobby);
        }
        
        return "SUCCESS";
    }
    
    /**
     * Map 컬렉션 여러 요청 파라미터 가져오기
     * 요청 경로 : /request/map?name=김조은&age=20
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/map")
    public String requestMap(@RequestParam Map<String, String> map) {
        String name = map.get("name");
        String age = map.get("age");
        
        logger.info("name : " + name);
        logger.info("age : " + age);
        return "SUCCESS";
    }
    
    /**
     * date 형식, 여러 요청 정보 가져오기
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST)
//  public String requestUser(String birth) {
//	public String requestUser(@DateTimeFormat(pattern="yyyy-MM-dd") Date birth) {
    public String requestUser(User user) {
        logger.info("[POST] - /reuqest/user");
        
//         logger.info("birth : " + birth);   // 2024-11-07
        logger.info("user : " + user);
        
        return "SUCCESS";
    }
    
    // 업로드 경로
    @Autowired
    @Qualifier("uploadPath")
    private String uploadPath;
    
    /**
     * 파일 업로드
     * @param file
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String fileUpload(MultipartFile file) throws Exception {
        logger.info("/request/file");
        logger.info("uploadPath : " + uploadPath);
        
        if( file == null ) return "FAIL";
        
        logger.info("originalFileName : " + file.getOriginalFilename());
        logger.info("size : " + file.getSize());
        logger.info("contentType : " + file.getContentType());
        
        // 파일 데이터 
        byte[] fileData = file.getBytes();
        
        // 파일 업로드
        String filePath = uploadPath;
        String fileName = file.getOriginalFilename();
        File uploadFile = new File(filePath, fileName);
        
        FileCopyUtils.copy(fileData, uploadFile);     // 파일 업로드 
        // FileCopyUtils.copy(파일 데이터, 파일 객체);
        // : 내부적으로는 InputStrea, OutputStream 을 이용하여 입력받은 파일을 출력한다.
        
        return "SUCCESS - uploadPath : " + uploadPath;
    }
    
 // 다중 파일 업로드
    @ResponseBody
    @RequestMapping(value = "/file/multi", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("file") MultipartFile[] fileList) throws Exception {
        logger.info("/request/file/multi");
        logger.info("uploadPath : " + uploadPath);
        
        if( fileList == null ) return "FAIL";
        
        if( fileList.length > 0 ) {
            for (MultipartFile file : fileList) {
                logger.info("originalFileName : " + file.getOriginalFilename());
                logger.info("size : " + file.getSize());
                logger.info("contentType : " + file.getContentType());
                
                // 파일 데이터 
                byte[] fileData = file.getBytes();
                
                // 파일 업로드
                String filePath = uploadPath;
                String fileName = file.getOriginalFilename();
                File uploadFile = new File(filePath, fileName);
                
                FileCopyUtils.copy(fileData, uploadFile);       // 파일 업로드 
                // FileCopyUtils.copy(파일 데이터, 파일 객체);
                // : 내부적으로는 InputStrea, OutputStream 을 이용하여 입력받은 파일을 출력한다.
            }
        }
        return "SUCCESS - uploadPath : " + uploadPath;
    }
    
 // 데이터 등록 + 파일 업로드
    @ResponseBody
    @RequestMapping(value = "/file/board", method = RequestMethod.POST)
    public String fileUpload(Board board) throws Exception {
        logger.info("/request/file/board");
        logger.info("uploadPath : " + uploadPath);
        logger.info("board : " + board);
        
        // MultipartFile[] fileList = board.getFileList();
        List<MultipartFile> fileList = board.getFileList();
        if( fileList == null ) return "FAIL";
        
//      if( fileList.length > 0 ) {
        if( !fileList.isEmpty() ) {
            for (MultipartFile file : fileList) {
                logger.info("originalFileName : " + file.getOriginalFilename());
                logger.info("size : " + file.getSize());
                logger.info("contentType : " + file.getContentType());
                
                // 파일 데이터 
                byte[] fileData = file.getBytes();
                
                // 파일 업로드
                String filePath = uploadPath;
                String fileName = file.getOriginalFilename();
                File uploadFile = new File(filePath, fileName);
                
                FileCopyUtils.copy(fileData, uploadFile);       // 파일 업로드 
                // FileCopyUtils.copy(파일 데이터, 파일 객체);
                // : 내부적으로는 InputStrea, OutputStream 을 이용하여 입력받은 파일을 출력한다.
            }
        }
        return "SUCCESS";
    }
    
    // ajax 비동기 파일 업로드
    @RequestMapping("/ajax")
    public String ajax() {
    	return "request/ajax";
    }
    
    @ResponseBody
    @RequestMapping(value = "/ajax", method = RequestMethod.POST)
    public String fileUploadAjax(Board board) throws Exception {
        logger.info("/request/ajax");
        logger.info("uploadPath : " + uploadPath);
        logger.info("board : " + board);
        
        // MultipartFile[] fileList = board.getFileList();
        List<MultipartFile> fileList = board.getFileList();
        if( fileList == null ) return "FAIL";
        
//      if( fileList.length > 0 ) {
        if( !fileList.isEmpty() ) {
            for (MultipartFile file : fileList) {
                logger.info("originalFileName : " + file.getOriginalFilename());
                logger.info("size : " + file.getSize());
                logger.info("contentType : " + file.getContentType());
                
                // 파일 데이터 
                byte[] fileData = file.getBytes();
                
                // 파일 업로드
                String filePath = uploadPath;
                String fileName = file.getOriginalFilename();
                File uploadFile = new File(filePath, fileName);
                
                FileCopyUtils.copy(fileData, uploadFile);       // 파일 업로드 
                // FileCopyUtils.copy(파일 데이터, 파일 객체);
                // : 내부적으로는 InputStrea, OutputStream 을 이용하여 입력받은 파일을 출력한다.
            }
        }
        return "SUCCESS";
    }
    
    
    
}
