package kr.co.iei.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.dto.MemberListData;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.product.dto.SellProduct;
import kr.co.iei.product.service.ProductService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping(value = "/adminHome")
	public String adminHome(Model model) {
		return "admin/adminHome";
	}//adminHome
	
	@GetMapping(value = "/allMember")
	public String allMember(int reqPage, Model model) {
		MemberListData mld = memberService.selectAllMember(reqPage);
		model.addAttribute("member", mld.getMember());
		model.addAttribute("pageNavi", mld.getPageNavi());
		return "admin/allMember";
	}//allMember
	
	@GetMapping(value = "/memberView")
	public String memberView(Model model, Member member) {
		String memberId = member.getMemberId();
		Member m = memberService.selectOneMember(memberId);
		model.addAttribute("member", m);
		return "admin/memberView";
	}//memberView

	@GetMapping(value = "/adminProductList")
	public String adminProductList() {
		return "admin/adminProductList";
	}//adminProductList
	
	@GetMapping(value = "/productView")
	public String productView(SellProduct sp, Model model) {
		int result = productService.productView(sp);
		return "admin/productView";
	}//productView
}
