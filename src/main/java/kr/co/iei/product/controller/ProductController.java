package kr.co.iei.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.product.service.ProductService;

@Controller
@RequestMapping(value="/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping(value="/acornProduct")
	public String acorn_product() {
		return"/product/acornProduct";
	}
	/*도토리 구매!*/
	@PostMapping(value="/acornCount")
	public String acornCount(int acorns) { // acorns 도토리 받기 나중엔 세션도 받아야함 member쪽에서 회원가입 끝내면 세션 확인하고 도토리 update 해주기 [acorns = acorns+?]
		System.out.println("gg");
		int result = productService.updateAcorns(acorns);
		if(result > 0) {
			// 도토리 잘 들어감 - 경고창 정하면 model 사용해서 만들기
			System.out.println("성공");
		}else {
			// 도토리 실패 - 경고창 정하면 model 사용해서 만들기
			System.out.println("실패");
		}
		return "/product/acornProduct";
	}
}