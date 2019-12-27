package com.khrd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.khrd.util.UploadFileUtils;

@RequestMapping("/upload/*")
@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Resource(name = "uploadPath") //bean id로 주입 받기(= DI : Dependency Injection)
	private String uploadPath; //파일 저장 경로(c:/zzz/upload)
	
	@RequestMapping(value = "innerUpload", method = RequestMethod.GET)
	public String innerUploadGet() {
		logger.info("********** [innerUpload GET] **********");
		return "/upload/innerUploadForm";
	}
	
	@RequestMapping(value = "innerUpload", method = RequestMethod.POST)
	public String innerUploadPost(String test, MultipartFile file, HttpServletRequest request, Model model) throws IOException {
		logger.info("********** [innerUpload POST] **********");
		logger.info("* test ::: " + test);
		logger.info("* file size ::: " + file.getSize() + " | file name ::: " + file.getOriginalFilename());
		
		String root_path = request.getSession().getServletContext().getRealPath("/"); //서버 저장 주소
		File dirPath = new File(root_path + "/resources/upload"); //저장 경로
		if(dirPath.exists() == false) {
			dirPath.mkdir(); //젖장
		}
		
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + "_" + file.getOriginalFilename(); //랜덤 값 + 원본 파일명
		File newFile = new File(root_path + "/resources/upload/" + savedName);
		System.out.println("root path ================= " + root_path);
		FileCopyUtils.copy(file.getBytes(), newFile);
		
		model.addAttribute("test", test);
		model.addAttribute("file", savedName);
		
		return "/upload/innerUploadResult";
	}
	
	@RequestMapping(value = "innerMulti", method = RequestMethod.GET)
	public String innerMultiGet() {
		logger.info("********** [innerMulti GET] **********");
		
		return "/upload/innerMultiForm";
	}
	
	@RequestMapping(value = "innerMulti", method = RequestMethod.POST)
	public String innerMultiPost(String test, List<MultipartFile> files, HttpServletRequest request, Model model) throws IOException {
		logger.info("********** [innerMulti POST] **********");
		logger.info("* test ::: " + test);
		
		String root_path = request.getSession().getServletContext().getRealPath("/");
		
		List<String> fileNames = new ArrayList<>(); //파일명 리스트
		
		for(MultipartFile file : files) {
			logger.info("* file size ::: " + file.getSize() + " | file name ::: " + file.getOriginalFilename());
			
			UUID uid = UUID.randomUUID();
			String savedName = uid.toString() + "_" + file.getOriginalFilename();
			File newFile = new File(root_path + "/resources/upload/" + savedName);
			FileCopyUtils.copy(file.getBytes(), newFile);
			
			fileNames.add(savedName);
		}
		model.addAttribute("test", test);
		model.addAttribute("list", fileNames);
		
		return "/upload/innerMultiResult";
	}
	
	@RequestMapping(value = "outUpload", method = RequestMethod.GET)
	public String outUploadGet() {
		logger.info("********** [outUpload GET] **********");
		logger.info("* uploadPath ::: " + uploadPath);	
		return "/upload/outUploadForm";
	}
	
	@RequestMapping(value = "outUpload", method = RequestMethod.POST)
	public String outUploadPost(String test, MultipartFile file, Model model) throws IOException {
		logger.info("********** [outUpload POST] **********");
		logger.info("* test ::: " + test);	
		logger.info("* file size ::: " + file.getSize() + " | file name ::: " + file.getOriginalFilename());
		
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + "_" + file.getOriginalFilename();
		File newFile = new File(uploadPath + "/" + savedName);
		FileCopyUtils.copy(file.getBytes(), newFile);
		
		model.addAttribute("test", test);
		model.addAttribute("file", savedName);
		
		return "/upload/outUploadResult";
	}
	
	@RequestMapping(value = "outMulti", method = RequestMethod.GET)
	public String outMultiGet() {
		logger.info("********** [outMulti GET] **********");
		logger.info("* uploadPath ::: " + uploadPath);	

		return "/upload/outMultiForm";
	}
	
	@RequestMapping(value = "outMulti", method = RequestMethod.POST)
	public String outMultiPost(String test, List<MultipartFile> files, Model model) throws IOException {
		logger.info("********** [outMulti POST] **********");
		logger.info("* test ::: " + test);	
		
		List<String> fileNames = new ArrayList<>();
		
		for(MultipartFile file : files) {
			logger.info("* file size ::: " + file.getSize() + " | file name ::: " + file.getOriginalFilename());
		
			UUID uid = UUID.randomUUID();
			String savedName = uid.toString() + "_" + file.getOriginalFilename();
			File newFile = new File(uploadPath + "/" + savedName);
			FileCopyUtils.copy(file.getBytes(), newFile);
			
			fileNames.add(savedName);
		}
		
		model.addAttribute("test", test);
		model.addAttribute("list", fileNames);
		
		return "/upload/outMultiResult";
	}
	
	@RequestMapping(value = "displayFile", method = RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String filename){
		ResponseEntity<byte[]> entity = null;
		logger.info("filename ::: " + filename);
		
		try {
			String formatName = filename.substring(filename.lastIndexOf(".") + 1);
			MediaType type = null;
			if(formatName.equalsIgnoreCase("jpg")) {
				type = MediaType.IMAGE_JPEG;
			}else if(formatName.equalsIgnoreCase("png")) {
				type = MediaType.IMAGE_PNG;
			}else if(formatName.equalsIgnoreCase("gif")) {
				type = MediaType.IMAGE_GIF;
			}
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(type);
			
			InputStream in = new FileInputStream(uploadPath + "/" + filename);
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
			in.close(); //파일 삭제 등 안 먹히면 반드시 close
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@RequestMapping(value = "drag", method = RequestMethod.GET)
	public String dragGet() {
		logger.info("********** [drag GET] **********");
		return "/upload/dragForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "drag", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> dragPost(String writer, List<MultipartFile> files) {
		logger.info("********** [drag POST] **********");
		logger.info("* writer ::: " + writer);

		ResponseEntity<Map<String, Object>> entity = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> fileNames = new ArrayList<>();
		
		try {
			for(MultipartFile file : files) {
				logger.info("* file size ::: " + file.getSize() + " | file name ::: " + file.getOriginalFilename());
				
				/*
				 * UUID uid = UUID.randomUUID(); String savedName = uid.toString() + "_" +
				 * file.getOriginalFilename(); File newFile = new File(uploadPath + "/" +
				 * savedName); FileCopyUtils.copy(file.getBytes(), newFile);
				 */
				
				String thumb = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
				fileNames.add(thumb);
			}
			
			map.put("result", "success");
			map.put("list", fileNames);
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();

			map.put("result", "fail");
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "deleteFile", method = RequestMethod.GET)
	public ResponseEntity<String> deleteFile(String filename){
		logger.info("********** [deleteFile GET] **********");
		
		ResponseEntity<String> entity = null;
		
		try {
			//썸네일 삭제
			File file = new File(uploadPath + "/" + filename);
			file.delete();
			
			//원본 삭제
			String datePath = filename.substring(0, 12); // "년/월/일/" 자르기
			String originFilename = filename.substring(14); // "년/월/일/s_" 뒤부터
			File originFile = new File(uploadPath + datePath + originFilename);

			System.out.println("원본 경로 ::: " + originFile);
			originFile.delete();
			
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}
