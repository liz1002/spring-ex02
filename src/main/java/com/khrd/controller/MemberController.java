package com.khrd.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khrd.domain.MemberVO;
import com.khrd.service.MemberService;

@RequestMapping("/member/*")
@RestController
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	private MemberService service;
	
	/* 리스트 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ResponseEntity<List<MemberVO>> listAll(){
		ResponseEntity<List<MemberVO>> entity = null;
		
		try {
			List<MemberVO> list = service.listAll();
			entity = new ResponseEntity<List<MemberVO>>(list, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<MemberVO>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity; 
	} //listAll
	
	/* 1개 정보 */
	@RequestMapping(value = "{userid}", method = RequestMethod.GET)
	public ResponseEntity<MemberVO> read(@PathVariable("userid") String userid){
		ResponseEntity<MemberVO> entity = null;
		
		logger.info("[1개 정보] 아이디 확인 ::: " + userid);
		
		try {
			MemberVO vo = service.read(userid);
			entity = new ResponseEntity<MemberVO>(vo, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<MemberVO>(HttpStatus.BAD_REQUEST);
		}
		
		return entity; 
	} //listAll
	
	/* 추가 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> regist(@RequestBody MemberVO vo){
		ResponseEntity<String> entity = null;
		
		logger.info("[추가] 멤버 vo 확인 ::: " + vo);
		
		try {
			service.regist(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity; 
	} //regist
	
	/* 수정 */
	@RequestMapping(value = "{userid}", method = RequestMethod.PUT)
	public ResponseEntity<String> modify(@RequestBody MemberVO vo){
		ResponseEntity<String> entity = null;
		
		logger.info("[수정] 멤버 vo 확인 ::: " + vo);
		
		try {
			service.modify(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity; 
	} //modify
	
	/* 삭제 */
	@RequestMapping(value = "{userid}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("userid") String userid){
		ResponseEntity<String> entity = null;
		
		logger.info("[삭제] 아이디 확인 ::: " + userid);
		
		try {
			service.remove(userid);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity; 
	} //remove
}
