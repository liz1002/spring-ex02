package com.khrd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.khrd.domain.Criteria;
import com.khrd.domain.PageMaker;
import com.khrd.domain.ReplyVO;
import com.khrd.service.ReplyService;

@RequestMapping("/replies/*")
@RestController
public class ReplyController {
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	private ReplyService service;
	
	//주소에 있는 변수를 매개변수로 받기
	@RequestMapping(value = "all/{bno}", method = RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> listAll(@PathVariable("bno") int bno){
		ResponseEntity<List<ReplyVO>> entity = null;
		try {
			List<ReplyVO> list = service.listAll(bno);
			entity = new ResponseEntity<List<ReplyVO>>(list, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<ReplyVO>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity; 
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResponseEntity<String> registAdd(ReplyVO vo){
		ResponseEntity<String> entity = null;
		
		logger.info("~~~~~~~~~~ regist Add ~~~~~~~~~~");
		logger.info(vo.toString());
		
		try {
			service.regist(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}//임시
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> regist(@RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		
		logger.info("~~~~~~~~~~ regist ~~~~~~~~~~");
		logger.info(vo.toString());
		
		try {
			service.regist(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}//찐사용
	
	@RequestMapping(value = "{rno}", method = RequestMethod.PUT) //put = post
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") int rno){
		ResponseEntity<String> entity = null;
		
		logger.info("~~~~~~~~~~ modify ~~~~~~~~~~");
		logger.info(vo.toString() + ", " + rno);
		
		try {
			vo.setRno(rno);
			service.modify(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "{rno}", method = RequestMethod.DELETE) //delete = get 
	public ResponseEntity<String> remove(@PathVariable("rno") int rno){
		ResponseEntity<String> entity = null;
		
		logger.info("~~~~~~~~~~ remove ~~~~~~~~~~");
		logger.info("rno = " + rno);
		
		try {
			service.remove(rno);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value = "{bno}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listCri(@PathVariable("bno") int bno, @PathVariable("page") int page){
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			Criteria cri = new Criteria(page, 5); //한 번에 보일 댓글 수 객체화
			List<ReplyVO> list = service.listCri(bno, cri);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.listCriTotalCount(bno));
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity; 
	}
}
