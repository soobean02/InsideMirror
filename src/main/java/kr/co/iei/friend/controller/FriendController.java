package kr.co.iei.friend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.iei.friend.dto.Friend;
import kr.co.iei.friend.service.FriendService;

@Controller
@RequestMapping(value="friend")
public class FriendController {
	@Autowired
	private FriendService friendService; 
	
	@ResponseBody
	@GetMapping(value="/friendList")
	public List<Friend> getFriendListData() {
	    return friendService.getAllFriend(); 
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



