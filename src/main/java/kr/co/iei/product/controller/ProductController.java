package kr.co.iei.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.product.dto.BuyProduct;
import kr.co.iei.product.dto.ProductListData;
import kr.co.iei.product.dto.SellBuyProduct;
import kr.co.iei.product.dto.SellProduct;
import kr.co.iei.product.service.ProductService;

@Controller
@RequestMapping(value="/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value="/acornProduct")
	public String acorn_product(Model model,  @SessionAttribute Member member) {
		List product = productService.selectProductPhoto();
		model.addAttribute("product",product);
		return"/product/acornProduct";
	}
	/*도토리 구매!*/
	@PostMapping(value="/acornCount")
	public String acornCount(Member m, @SessionAttribute Member member, Model model) { // acorns 도토리 받기 나중엔 세션도 받아야함 member쪽에서 회원가입 끝내면 세션 확인하고 도토리 update 해주기 [acorns = acorns+?]
		System.out.println("gg");
		int result = productService.updateAcorns(m);
		
		if(result > 0) {
			// 도토리 잘 들어감 - 경고창 정하면 model 사용해서 만들기
			System.out.println("성공");
			// 세션 업데이트 하기
			member.setAcorns(m.getAcorns()+member.getAcorns());
			model.addAttribute("title", "성공!");
			model.addAttribute("msg", "결제 성공");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/product/acornProduct");
			return "common/msg";
		}else {
			// 도토리 실패 - 경고창 정하면 model 사용해서 만들기
			model.addAttribute("title", "실패");
			model.addAttribute("msg", "결제 실패");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/product/acornProduct");
			return "common/msg";
		}
	}
	/*판매 상품 리스트*/
	@GetMapping(value="/productList")
	public String productList(Model model, int reqPage, int type) {
		ProductListData pld = productService.selectProduct(reqPage, type);
		model.addAttribute("list", pld.getList());
		model.addAttribute("pageNavi", pld.getNaviPage());
		model.addAttribute("ty", type);
		return "/product/productList";
	}
	/*판매 상세 페이지*/
	@GetMapping(value="/proudctPage")
	public String productPage(Model model, int productNo,  @SessionAttribute Member member) { // buyProductPage로 되어있었음...
		// 상품 정보
		SellProduct p = productService.selectProductInfo(productNo);
		model.addAttribute("p",p);
		// 세션 보내기
		model.addAttribute("member", member);

		// 최신 상품 3개
		List product = productService.selectProductPhoto();
		model.addAttribute("product",product);
		
		// 구매한 상품인지 확인 => 이미 구매한 상품이면 뷰단에서 구매하기 버튼이 보이지 않게 함(검사는 뷰에서 함!)
		BuyProduct bp = productService.selectOneProduct(member,productNo);
		if(bp == null) { // 구매하지 않은 상품
			model.addAttribute("bp",null);
		}else {
			model.addAttribute("bp",bp);
		}
		System.out.println(p);
		return "/product/productPage";
	}
	
	/*상품 구매하기*/
	@PostMapping(value="/userBuyProduct")
	public String userBuyProduct(@SessionAttribute Member member,SellProduct sp, Model model) { // 상품번호, 상품 가격, 상품 리스트 정보, 멤버 정보 받기
		// 로직 : 멤버 update를 사용해서 상품 가격 만큼 도토리 빼기 -> 성공하면 구매 상품에 추가 insert, 실패 시 알림창(알림창 우짬..)
		int result = productService.productAdd(member, sp);
		model.addAttribute("prdouctPrice", result);
		if(result > 0) {
			member.setAcorns(member.getAcorns() - sp.getProductPrice()); // 세션 갱신
		}
		return "redirect:/product/productList?reqPage=1&type=0";
	}
	
	/*구매 상품 리스트 - 타입 검사 추가*/
	@GetMapping(value="/buyProductList")
	public String buyProductList(Model model, int reqPage, @SessionAttribute Member member, int type, String product) {
		ProductListData pld = productService.selectBuyProduct(reqPage, member, type);
		model.addAttribute("product", product);
		model.addAttribute("list", pld.getList());
		model.addAttribute("pageNavi", pld.getNaviPage());
		model.addAttribute("ty", type);
		return "/product/buyProductList";
	}
	
	/*구매 상품 상세페이지*/
	@GetMapping(value="/buyProductPage")
	public String buyProductPage(Model model, int buyNo,  @SessionAttribute Member member) {
		// 구매한 상품 정보 출력
		System.out.println(buyNo);
		SellBuyProduct sp = productService.selectBuyProductInfo(buyNo);
		System.out.println(sp.getBuyProduct());
		System.out.println(sp.getSellProduct());
		model.addAttribute("sp",sp);
		// 세션 보내기
		List product = productService.selectProductPhoto();
		model.addAttribute("product",product);
		
		return "/product/buyProductPage";
	}
	
	@GetMapping(value="/appProductList")
	public String appProductList(Model model, String product) {
		model.addAttribute("product", product);
		return "/product/appProductList";
	}
}