package kr.co.iei.guestbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.iei.guestbook.dto.GuestBook;
import kr.co.iei.guestbook.service.GuestBookService;


@Controller
@RequestMapping("/guest")
public class GuestBookController {
    @Autowired
    private GuestBookService guestBookService; 
    
    @GetMapping(value="/guestbookList")
	public String guestbookList(Model model) {
		return "guest/guestbookList";
	}
    @ResponseBody
    @PostMapping(value="/insertComment")
    public String insertComment(GuestBook gb, Model model) {
        int result = guestBookService.insertComment(gb);
        if(result > 0) {    
            model.addAttribute("title", "댓글 작성");
            model.addAttribute("msg", "댓글이 작성되었습니다.");
            model.addAttribute("icon", "success");
        } else {
            model.addAttribute("title", "댓글 작성 실패");
            model.addAttribute("msg", "댓글 작성 중 문제가 발생했습니다.");
            model.addAttribute("icon", "warning");
        }
        model.addAttribute("loc", "/guestbook");
        return "common/msg";
    }
    @ResponseBody
    @PostMapping(value="/updateComment")
    public String updateComment(GuestBook gb, Model model) {
        int result = guestBookService.updateComment(gb);
        if(result > 0) {
            model.addAttribute("title", "성공");
            model.addAttribute("msg", "댓글이 수정되었습니다.");
            model.addAttribute("icon", "success");
        } else {
            model.addAttribute("title", "실패");
            model.addAttribute("msg", "댓글 수정 중 문제가 발생했습니다.");
            model.addAttribute("icon", "warning");
        }
        model.addAttribute("loc", "/guestbook");
        return "common/msg";
    }
    @ResponseBody
    @GetMapping(value="/deleteComment")
    public String deleteComment(int guestCommentNo, Model model) {
        GuestBook gb = new GuestBook();
        gb.setGuestCommentNo(guestCommentNo);
        int result = guestBookService.deleteComment(gb);
        if(result > 0) {
            model.addAttribute("title", "성공");
            model.addAttribute("msg", "댓글이 삭제되었습니다.");
            model.addAttribute("icon", "success");
        } else {
            model.addAttribute("title", "실패");
            model.addAttribute("msg", "댓글 삭제 중 문제가 발생했습니다.");
            model.addAttribute("icon", "warning");
        }
        model.addAttribute("loc", "/guestbook");
        return "common/msg";
    }
    @GetMapping(value="/getComments")
    @ResponseBody
    public List<GuestBook> getComments() {
        return guestBookService.getAllComments();
    }

}