package kr.co.iei.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	@GetMapping(value="/login")
	public String login() {
		return "member/login";
	}
	@PostMapping(value="/login")
	public String login(Member m, HttpSession session) {
		Member member = memberService.selectOneMember(m);
		if(member == null) {
			return "member/login";
		}else {
			if(member.getMemberLevel() == 2) {
				session.setAttribute("member", member);
				return "common/minihomepage";
			}else {
				session.setAttribute("member", member);
				return "redirect:/";
			}
		}
	}
	@GetMapping(value="/minihomepage")
		public String minihomepage() {
			return "common/minihomepage";
		}
	@GetMapping(value="/joinFrm")
	public String joinFrm() {
		return "/member/joinFrm";
	}
	@PostMapping(value="/join")
	public String join(Member m,String memberId2) {
		String memberId = m.getMemberId()+"@"+memberId2;
		m.setMemberId(memberId);
		int result = memberService.insertMember(m);
		if(result>0) {
			return "redirect:/";
		}else {
			return "redirect:/member/joinFrm";
		}
	}
	@PostMapping(value="/checkNickname")
	public String checkPw(String checkNickname, Model model) {
		Member member = memberService.selectOneMember(checkNickname);
		if(member == null) {
			model.addAttribute("result",0);
		}else {
			model.addAttribute("result",1);
		}
		model.addAttribute("memberNickname",checkNickname);
		return "member/checkNickname";
	}
	@GetMapping(value="/joinFinal")
	public String joinFinal() {
		return "/member/joinFinal";
	}
	@GetMapping(value="/findPassword")
	public String findPassword() {
		return "/member/findPassword";
	}
	@GetMapping(value="/doubleCheckPassword")
	public String doubleCheckPassword() {
		return "/member/doubleCheckPassword";
	}
	
	@ResponseBody
	@GetMapping(value="/ajaxCheckNickname")
	public int ajaxCheckPw(String memberNickname) {
		Member member = memberService.selectOneMember(memberNickname);
		if(member == null) {
			return 0;
		}else {
			return 1;
		}
	}
}
