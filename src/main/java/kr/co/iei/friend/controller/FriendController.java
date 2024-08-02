package kr.co.iei.friend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.friend.service.FriendService;
import kr.co.iei.utils.FileUtils;

@Controller
@RequestMapping(value="friend")
public class FriendController {
	@Autowired
	private FriendService friendService; 
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtils fileUtils;
}
