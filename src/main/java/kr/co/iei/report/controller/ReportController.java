package kr.co.iei.report.controller;

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
import kr.co.iei.report.model.dto.ReportListData;
import kr.co.iei.report.model.service.ReportService;

@Controller
@RequestMapping(value = "/report")
public class ReportController {
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping(value = "/reportList")
	public String reportList(Model model, int reqPage) {
		ReportListData rld = reportService.selectReportList(reqPage);
		CustomerListData cld = customerService.selectAdminCustomerList(reqPage);
		
		model.addAttribute("customerList", cld.getList());
		model.addAttribute("pageNavi", rld.getPageNavi());
		return "report/reportList";
	}//reportList
	
	@GetMapping(value = "/reportView")
	public String reportView(Model model, int inqNo) {
		Customer customer = customerService.selectCustomerContent(inqNo);
		model.addAttribute("customer", customer);
		return "report/reportView";
	}//reportView
	
	@PostMapping(value = "/updateStatus")
	public String updateStatus(Customer c, Model model) {
		int result = customerService.updateStatus(c);
		if(result>0) {
			model.addAttribute("title", "처리 완료");
			model.addAttribute("msg", "요청이 처리되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/report/reportList?reqPage=1");
			return "common/msg";
		}else {
			return "redirect:/";
		}//else
	}//updateStatus
	
	@GetMapping(value = "/updateStatusReject")
	public String updateStatusReject(Customer c, Model model) {
		int result = customerService.updateStatusReject(c);
		if(result>0) {
			model.addAttribute("title", "처리 완료");
			model.addAttribute("msg", "요청이 반려되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/report/reportList?reqPage=1");
			return "common/msg";
		}else {
			return "redirect:/";
		}//else
	}//updateStatusReject
}


