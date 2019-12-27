package com.khrd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khrd.domain.SimpleVO;

@RestController //모든 메소드 데이터 ajax 리턴
public class SimpleController {
	private static final Logger logger = LoggerFactory.getLogger(SimpleController.class);
	
	//@ResponseBody //ajax로 데이터 돌려주기(브라우저x)
	@RequestMapping(value = "hello", method = RequestMethod.GET)
	public String hello() {
		return "hello spring";
	}
	
	@RequestMapping(value = "send1", method = RequestMethod.GET)
	public SimpleVO sendSimpleVO() {
		SimpleVO vo = new SimpleVO();
		vo.setNum(1);
		vo.setName("사람인");
		vo.setPassword("1234");
		
		return vo;
	}
	
	@RequestMapping(value = "sendList", method = RequestMethod.GET)
	public List<SimpleVO> sendList(){
		List<SimpleVO> list = new ArrayList<SimpleVO>();
		
		list.add(new SimpleVO(1, "우하하", "8888"));
		list.add(new SimpleVO(2, "뿌뿌뿌", "333"));
		list.add(new SimpleVO(3, "주댕이", "372"));
		
		return list;
	}
	
	@RequestMapping(value = "sendMap", method = RequestMethod.GET)
	public Map<String, SimpleVO> sendMap(){
		Map<String, SimpleVO> map = new HashMap<>();
		
		map.put("test1", new SimpleVO(1, "대이", "8888"));
		map.put("test2", new SimpleVO(2, "댕이", "0404"));
		map.put("test3", new SimpleVO(3, "쟁이", "3333"));
		
		return map;
	}
	
	@RequestMapping(value = "sendAuth", method = RequestMethod.GET)
	public ResponseEntity<Void> sendAuth(){
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST); //에러 강제 발생
	}
	
	@RequestMapping(value = "sendAuthList", method = RequestMethod.GET)
	public ResponseEntity<List<SimpleVO>> sendAuthList(){
		List<SimpleVO> list = new ArrayList<SimpleVO>();
		
		list.add(new SimpleVO(1, "대이", "8888"));
		list.add(new SimpleVO(2, "댕이", "0404"));
		list.add(new SimpleVO(3, "쟁이", "3372"));
		
		ResponseEntity<List<SimpleVO>> entity = new ResponseEntity<List<SimpleVO>>(list, HttpStatus.BAD_REQUEST); //void에 원하는 타입 선언 시 데이터, 에러 전송
		
		return entity;
	}
}
