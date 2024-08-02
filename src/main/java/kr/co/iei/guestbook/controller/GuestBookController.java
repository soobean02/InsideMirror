package kr.co.iei.guestbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.guestbook.service.GuestBookService;
import kr.co.iei.utils.FileUtils;

@Controller
@RequestMapping(value="/GuestBook")
public class GuestBookController {
	@Autowired
	private GuestBookService guestBookService; 
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtils fileUtils;

}
