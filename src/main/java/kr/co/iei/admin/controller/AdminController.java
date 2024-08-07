package kr.co.iei.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.customer.service.CustomerService;
import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.dto.MemberListData;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.product.dto.SellProduct;
import kr.co.iei.product.service.ProductService;
import kr.co.iei.report.model.service.ReportService;
import kr.co.iei.utils.FileUtils;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private CustomerService customerService;
	
	@Value("${file.root}")
	private String root;

	@Autowired
	private FileUtils fileUtils;
	
	
	@GetMapping(value = "/adminHome")
	public String adminHome(Model model) {
		//회원 5명 조회
		List fiveMemberList = memberService.selectFiveMember();
		model.addAttribute("fiveMemberList", fiveMemberList);
		
		//고객센터 글 5개 조회
		List fiveReportList = customerService.selectFiveReport();
		model.addAttribute("fiveReportList", fiveReportList);
		
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
	public String memberView(Model model, int memberNo) {
		Member m = memberService.selectAdminOneMember(memberNo);
		model.addAttribute("m", m);
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
	
	@GetMapping(value = "/adminProductAddFrm")
	public String adminProductAddFrm() {
		return "admin/adminProductAddFrm";
	}//adminProductAddFrm
	
	@PostMapping(value = "/adminProductAdd")
	public String adminProductAdd(SellProduct sp, Model model, MultipartFile[] upfile) {
		
		List<SellProduct> productImgFileList = new ArrayList<SellProduct>();
		if(!upfile[0].isEmpty()) {
			String savepath = root+"/product/";
			for(MultipartFile file : upfile) {
				String filepath = fileUtils.upload(savepath, file);
				SellProduct productImgFile = new SellProduct();
				productImgFile.setProductImg(filepath);
				productImgFileList.add(productImgFile);
			}//for
		}//if
		
		int result = productService.addProduct(sp);
		if(result>0) {
			return "admin/adminProductList";
		}else {
			return "redirect:/";
		}//else
	}//adminProductAdd
}
