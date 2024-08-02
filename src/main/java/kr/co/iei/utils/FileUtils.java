package kr.co.iei.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtils {

	//파일의 저장경로랑 파일객체를 매개변수로 받음
		//해당 저장경로의 파일명이 중복되지 않도록 업로드하고, 업로드된 파일명을 리턴하는 메소드
		public String upload(String savepath, MultipartFile file) {
			//원본파일명 추출 => test.txt
			String filename = file.getOriginalFilename();
			//test 랑 .txt 분리
			//원본파일명의 시작부터 제일 뒤에 있는 .앞까지를 문자열로 가져옴 -> test
			String onlyFilename = filename.substring(0, filename.lastIndexOf("."));
															//.이 여러개 일 수도 있으므로 제일 뒤쪽에 있는 .앞까지 문자열로 가져옴
			String extension = filename.substring(filename.lastIndexOf("."));
			//원본 파일명의 제일 뒤에 있는 .부터 끝까지를 문자열로 가져옴 -> .txt
			
			//실제로 업로드할 파일명을 저장할 변수
			String filepath = null;
			//중복파일명이 있으면 숫자를 증가시키면서 뒤에 붙일 숫자 변수
			int count = 0;
			while(true) {
				if(count == 0) {
					//첫번째는 원본파일명 그대로 적용
					filepath = onlyFilename + extension;
				}//if
				else {
					//파일명에 숫자를 붙여서 처리
					filepath = onlyFilename+"_"+count+extension;
				}//else
				count++;
				//위에서 만든 파일명이 사용중인지 체크하는 로직
				File checkFile = new File(savepath + filepath);
				if(!checkFile.exists()) {
					break;
					//파일명이 겹치지 않으면 break
				}
			}//while
			//파일명 중복체크 끝 -> 내가 업로드할 파일명 결정 -> 파일업로드 진행
			try {
				//중복체크가 끝난 파일명으로 파일을 업로드 하는 로직
				file.transferTo(new File(savepath + filepath));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return filepath;
		}//upload
}
