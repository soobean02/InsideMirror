package kr.co.iei.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.member.model.service.MemberService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private MemberService memberService;

}
