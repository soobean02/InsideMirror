package kr.co.iei.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.member.model.dto.MemberListData;
import kr.co.iei.member.model.service.MemberService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value = "/allMember")
	public String allMember(int reqPage, Model model) {
		MemberListData mld = memberService.selectAllMember(reqPage);
		model.addAttribute("member", mld.getMember());
		model.addAttribute("pageNavi", mld.getPageNavi());
		return "admin/allMember";
	}//allMember

}
