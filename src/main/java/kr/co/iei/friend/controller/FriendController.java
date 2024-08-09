package kr.co.iei.friend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iei.friend.dto.Friend;
import kr.co.iei.friend.service.FriendService;
import kr.co.iei.friend.service.FriendStatusService;
import kr.co.iei.member.model.dto.Member;

@Controller
@RequestMapping(value="friend")
public class FriendController {
	@Autowired
	private FriendService friendService; 	
	
	@GetMapping(value="/friendRequest")
	public String friendrequest(Model model, @SessionAttribute Member member) {
		int memberNo = member.getMemberNo();
		List list = friendService.selectAllList();
		
		
		return "friend/friendrequest";
	

	}
}



