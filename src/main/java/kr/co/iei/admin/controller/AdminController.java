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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.customer.service.CustomerService;
import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.dto.MemberListData;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.product.dto.BuyProductListData;
import kr.co.iei.product.dto.ProductListData;
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
		
		//상품 3개 조회
		List threeProductList = productService.selectThreeProduct();
		model.addAttribute("threeProductList", threeProductList);
		
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
	public String adminProductList(Model model, int reqPage, int type) {
		ProductListData pld = productService.selectAdminProduct(reqPage, type);
		model.addAttribute("list", pld.getList());
		model.addAttribute("pageNavi", pld.getNaviPage());
		model.addAttribute("ty", type);
		return "admin/adminProductList";
	}//adminProductList
	
	@GetMapping(value = "/adminProductView")
	public String adminProductView(Model model, int productNo) {
		SellProduct sp = productService.selectProductInfo(productNo);
		model.addAttribute("sp", sp);
		return "admin/adminProductView";
	}//productView
	
	@PostMapping(value = "/productUpdate")
	public String productUpdate(SellProduct sp, Model model) {
		int result = productService.productUpdate(sp);
		if(result>0) {
			model.addAttribute("title", "수정 완료");
			model.addAttribute("msg", "상품 정보가 수정되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/admin/adminProductList?reqPage=1&type=0");
			return "common/msg";
		}else {
			return "redirect:/";
		}//else
	}//productUpdate
	
	@GetMapping(value = "/adminProductAddFrm")
	public String adminProductAddFrm() {
		return "admin/adminProductAddFrm";
	}//adminProductAddFrm
	
	@PostMapping(value = "/adminProductAdd")
	public String adminProductAdd(SellProduct sp, Model model, @RequestParam("addProductImg") MultipartFile addProductImg) {
		String savepath = root+"/product/editor/";
		String filepath = fileUtils.upload(savepath, addProductImg);
		sp.setProductImg(filepath);
		int result = productService.adminAddProduct(sp);
		if(result>0) {
			return "redirect:/admin/adminProductList?reqPage=1&type=0";
		}else {
			return "redirect:/";
		}//else
	}//adminProductAdd
	
	@GetMapping(value = "/productDelete")
	public String productDelete(SellProduct sp, Model model) {
		int productNo = sp.getProductNo();
		int result = productService.productDelete(productNo);
		if(result>0) {
			model.addAttribute("title", "삭제 완료");
			model.addAttribute("msg", "상품이 삭제되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/admin/adminProductList?reqPage=1&type=0");
			return "common/msg";
		}else {
			return "redirect:/";
		}//else
	}//productDelete
	
	@GetMapping(value = "/adminRefundList")
	public String adminRefundList(int reqPage, Model model) {
		BuyProductListData bld = productService.selectRefundList(reqPage);
		model.addAttribute("list", bld.getList());
		model.addAttribute("pageNavi", bld.getPageNavi());
		return "admin/adminRefundList";
	}//adminRefundList
}
