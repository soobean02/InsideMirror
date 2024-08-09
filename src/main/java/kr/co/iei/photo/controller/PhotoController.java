package kr.co.iei.photo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.photo.model.service.PhotoService;
import kr.co.iei.utils.FileUtils;

@Controller
@RequestMapping(value = "/photo")
public class PhotoController {
	@Autowired
	private PhotoService photoService;
	@Value("${file.root}")
	private String root;
	@Autowired
	private FileUtils fileUtils;//파일 업로드용
}
