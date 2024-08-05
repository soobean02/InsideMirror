package kr.co.iei.guestbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.guestbook.dto.GuestBook;
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
	
	
@PostMapping(value="/insertComment")
public String insertComment(GuestBook gb, Model model) {
	System.out.println(111);
	int result = guestBookService.insertComment(gb);
	if(result > 0) {	
	model.addAttribute("title", "댓글작성");
	model.addAttribute("msg", "댓글이 작성되었습니다");
	model.addAttribute("icon", "success");
}else {
	model.addAttribute("title", "댓글작성 실패");
	model.addAttribute("msg", "댓글이 작성중 문제가 생겼습니다");
	model.addAttribute("icon", "warning");
}
	model.addAttribute("loc", "/guest/guestBook?memberNo");
	return "common/msg";
}
@PostMapping(value="/updateComment")
public String updateComment(GuestBook gb ,Model model) {
	int result = guestBookService.updateComment(gb);
	if(result > 0) {
		model.addAttribute("title", "성공");
		model.addAttribute("msg", "댓글이 수정되었습니다.");
		model.addAttribute("icon", "success");
	}else {
		model.addAttribute("title", "실패");
		model.addAttribute("msg", "잠시후 다시 시도해주세요.");
		model.addAttribute("icon", "warning");
	}
	model.addAttribute("loc", "/guest/guestBook?check=1&memberNo");
	return "common/msg";
}
@GetMapping(value="/deleteComment")
public String deleteComment(GuestBook gb ,Model model) {
	int result = guestBookService.deleteComment(gb);
	if(result > 0) {
		model.addAttribute("title", "성공");
		model.addAttribute("msg", "댓글이 삭제되었습니다.");
		model.addAttribute("icon", "success");
	}else {
		model.addAttribute("title", "실패");
		model.addAttribute("msg", "잠시후 다시 시도해주세요.");
		model.addAttribute("icon", "warning");
	}
	model.addAttribute("loc", "/guest/guestBook?check=1&memberNo=");
	return "common/msg";
}

}
