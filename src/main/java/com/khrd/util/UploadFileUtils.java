package com.khrd.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	//upload함수, 원본 업로드, 썸네일 업로드, 년/월/일 폴더 만들기
	//uploadPath = C:/zzz/upload
	//originalFileName = 파일명
	//data = 이미지 데이터
	//return 값 = 썸네일 파일명	
	public static String uploadFile(String uploadPath, String originalFilename, byte[] data) throws IOException {
		
		//원본 업로드 처리
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + "_" + originalFilename;
		String datePath = calculatorPath(uploadPath);
		File newFile = new File(uploadPath + datePath, savedName);
		FileCopyUtils.copy(data, newFile);
		
		//썸네일 업로드
		String thumbnailName = makeThumbnail(uploadPath + datePath, savedName);
		
		return datePath + thumbnailName; // => 년/월/일/썸네일이미지
	}
	
	//썸네일 파일 만들기
	private static String makeThumbnail(String uploadPath, String filename) throws IOException {
		//BufferedImage : 실제 이미지 X 메모리상의 이미지를 의미하는 객체
		
		//원본 이미지를 가져와 메모리 상에 넣어둠
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + "/" + filename));
		
		//메모리상의 이미지 리사이징 - 높이 100 기준으로 가로 자동 조정
		BufferedImage destImg = Scalr.resize(sourceImg, 
												Scalr.Method.AUTOMATIC, //자동
												Scalr.Mode.FIT_TO_HEIGHT, //높이 기준
												100); //높이 100
		
		//작은 이미지가 데이터가 들어갈 파일 만들기
		String thumbnailName = uploadPath + "/s_" + filename;		
		File newFile = new File(thumbnailName);
		
		//파일 확장자 지정
		String formatName = filename.substring(filename.lastIndexOf(".") + 1);
		
		//작은 이미지 데이터를 작은 이미지 파일에 복사 (파일 완성)
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		return thumbnailName.substring(uploadPath.length()); //썸네일 이미지 파일명 리턴
	}

	//날짜 폴더 만들기
	private static String calculatorPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();

		String yearPath = "/" + cal.get(Calendar.YEAR); // => /yyyy
		String monthPath = String.format("%s/%02d", yearPath, cal.get(Calendar.MONTH)+1); // => /yyyy/MM
		String datePath = String.format("%s/%02d", monthPath, cal.get(Calendar.DATE)); // => /yyyy/MM/dd
		
		makeDir(uploadPath, yearPath, monthPath, datePath); //폴더 생성(매개변수 순서 중요)
		
		return datePath;
	}
	
	//paths = {yearPath, monthPath, datePath}
	private static void makeDir(String uploadPath, String... paths) { // ... : 해당 타입의 변수 개수 지정X, 배열로 받음
		for(String path : paths) {
			File dir = new File(uploadPath + path); // => c:/zzz/upload/yyyy/MM/dd
			if(dir.exists() == false) {
				dir.mkdir();
			}
		}
	}
}
