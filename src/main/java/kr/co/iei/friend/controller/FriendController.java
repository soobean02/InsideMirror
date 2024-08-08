package kr.co.iei.friend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.friend.service.FriendService;

@Controller
@RequestMapping(value="friend")
public class FriendController {
	@Autowired
	private FriendService friendService; 
	
	@GetMapping(value="/friendList")
	public String friendList(Model model) {
		return "friend/friendList";
	}
	
	@GetMapping(value="friendrequest")
	public String friendrequest(Model model) {
		return "friend/friendrequest";
	}
	
	@GetMapping(value="neighborrequest")
	public String neighborrequest(Model model) {
		return "friend/neighborrequest";
	}
}



