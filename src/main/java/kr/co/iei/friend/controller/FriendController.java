package kr.co.iei.friend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iei.friend.dto.Friend;
import kr.co.iei.friend.service.FriendService;
import kr.co.iei.guestbook.dto.GuestBook;
import kr.co.iei.guestbook.service.GuestBookService;
import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.service.MemberService;


@Controller
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;

    //일촌 목록
    @GetMapping(value="/friendList")
    public String friendList(@SessionAttribute Member member, HttpServletRequest request, Model model) {
        Friend f = new Friend();
        f.setMemberNo(member.getMemberNo());

        String keyword = request.getParameter("searchKeyword");
        
        List<Friend> friendList;
        if (keyword == null || keyword.trim().isEmpty()) {
            friendList = friendService.selectAllList(f);
        } else {
            friendList = friendService.selectList(f, keyword);
        }
        model.addAttribute("friendList", friendList);
        model.addAttribute("searchKeyword", keyword);

        return "friend/friendList";
    }

    //일촌 삭제
    @ResponseBody
    @GetMapping(value="/friendCancel")
    public String friendCancel(int friendNo, Model model) {
        Friend f = new Friend();
        f.setFriendNo(friendNo);
        int result = friendService.friendCancel(f);
        return result > 0 ? "success" : "error";
    }

    //일촌 맺기
    @GetMapping(value="/friendRequest")
    public String friendRequest(@SessionAttribute Member member, int friendMemberNo, String friendNickName, Model model) {
        Friend f = new Friend();
        f.setFriendNo(friendMemberNo);
        f.setFriendNickName(friendNickName);
        f.setMemberNo(member.getMemberNo());
        int result = friendService.friendRequest(f);
        return "redirect:/member/friendPage?memberNo=" + friendMemberNo;
    }
}