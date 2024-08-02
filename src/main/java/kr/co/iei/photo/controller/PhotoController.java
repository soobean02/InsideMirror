package kr.co.iei.photo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.photo.model.service.PhotoService;

@Controller
@RequestMapping(value = "/photo")
public class PhotoController {
	@Autowired
	private PhotoService photoService;
}
