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
import kr.co.iei.product.dto.ProductListData;
import kr.co.iei.product.dto.SellProduct;
import kr.co.iei.product.service.ProductService;

@Controller
@RequestMapping(value="/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping(value="/acornProduct")
	public String acorn_product(Model model) {
		List product = productService.selectProductPhoto();
		model.addAttribute("product",product);
		return"/product/acornProduct";
	}
	/*도토리 구매!*/
	@PostMapping(value="/acornCount")
	public String acornCount(Member m, @SessionAttribute Member member) { // acorns 도토리 받기 나중엔 세션도 받아야함 member쪽에서 회원가입 끝내면 세션 확인하고 도토리 update 해주기 [acorns = acorns+?]
		System.out.println("gg");
		int result = productService.updateAcorns(m);
		
		if(result > 0) {
			// 도토리 잘 들어감 - 경고창 정하면 model 사용해서 만들기
			System.out.println("성공");
			// 세션 업데이트 하기
			member.setAcorns(m.getAcorns());
		}else {
			// 도토리 실패 - 경고창 정하면 model 사용해서 만들기
			System.out.println("실패");
		}
		return "redirect:/product/acornProduct";
	}
	/*판매 상품 리스트*/
	@GetMapping(value="/productList")
	public String productList(Model model, int reqPage) {
		ProductListData pld = productService.selectProduct(reqPage);
		model.addAttribute("list", pld.getList());
		model.addAttribute("pageNavi", pld.getNaviPage());
		return "/product/productList";
	}
	/*판매 상세 페이지*/
	@GetMapping(value="/proudctPage")
	public String buyProductPage(Model model, int productNo,  @SessionAttribute Member member) {
		// 상품 정보
		SellProduct p = productService.selectProductInfo(productNo);
		model.addAttribute("p",p);
		
		model.addAttribute("member", member);

		// 최신 상품 3개
		List product = productService.selectProductPhoto();
		model.addAttribute("product",product);
		
		System.out.println(p);
		return "/product/productPage";
	}
	
	/*상품 구매하기*/
	@GetMapping(value="/userBuyProduct")
	public String userBuyProduct(@SessionAttribute Member member,SellProduct sp) { // 멤버 정보와 판매 상품 정보 받음
		// 로직 : 멤버 update를 사용해서 상품 가격 만큼 도토리 빼기 -> 성공하면 구매 상품에 추가 insert, 실패 시 알림창
		return "/product/productList";
	}
}