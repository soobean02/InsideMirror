package kr.co.iei.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.customer.dto.Customer;
import kr.co.iei.customer.dto.CustomerListData;
import kr.co.iei.customer.service.CustomerService;

@Controller
@RequestMapping(value="/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping(value="/customerList")
	public String customerList(Model model,int reqPage) {
		CustomerListData cld = customerService.selectCustomerList(reqPage);
		model.addAttribute("list", cld.getList());
		model.addAttribute("pageNavi", cld.getPageNavi());
		return "customer/customerList";
	}
	@GetMapping(value="/customerFrm")
	public String customerFrm() {
		return "customer/customerFrm";
	}
	@PostMapping(value="/customerWrite")
	public String customerWrite(Customer c) {
		int result = customerService.insertCustomerInq(c);
		return "redirect:/customer/customerList?reqPage=1";
	}
}
