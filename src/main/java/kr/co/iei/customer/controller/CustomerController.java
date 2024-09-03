package kr.co.iei.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.iei.customer.dto.Customer;
import kr.co.iei.customer.dto.CustomerListData;
import kr.co.iei.customer.service.CustomerService;
import kr.co.iei.member.model.dto.Member;

@Controller
@RequestMapping(value="/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	/*고객센터 목록*/
	@GetMapping(value="/customerList")
	public String customerList(Model model,int reqPage, @SessionAttribute Member member) {
		CustomerListData cld = customerService.selectCustomerList(reqPage, member); // 목록 조회
		model.addAttribute("list", cld.getList()); // 리스트
		model.addAttribute("pageNavi", cld.getPageNavi()); // 페이징
		return "customer/customerList";
	}
	/*고객센터 문의 폼으로 이동*/
	@GetMapping(value="/customerFrm")
	public String customerFrm() {
		return "customer/customerFrm";
	}
	/*고객센터 문의 폼*/
	@PostMapping(value="/customerWrite")
	public String customerWrite(Customer c,@SessionAttribute("member") Member member, Model model) {

		int result = customerService.insertCustomerInq(c,member); // 고객이 작성한 문의내용 insert
		if(result > 0) {
			model.addAttribute("title", "문의");
			model.addAttribute("msg", "5 ~ 10일 정도 걸려요~");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/customer/customerList?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("title", "실패");
			model.addAttribute("msg", "문의 실패");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/customer/customerList?reqPage=1");
			return "common/msg";
		}
	}
	/*문의 상세 페이지*/
	@GetMapping(value="/customerPage")
	public String customerView(Model model, int inqNo) {
		Customer customer = customerService.selectCustomerContent(inqNo); 
		model.addAttribute("customer",customer);
		return "/customer/customerPage";
	}
	
}
