package kr.co.iei.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		CustomerListData cld = customerService.selectCustomerList(reqPage);
		
		model.addAttribute("customerList", cld.getList());
		model.addAttribute("reportList", rld.getReportList());
		model.addAttribute("pageNavi", rld.getPageNavi());
		return "report/reportList";
	}//reportList
	
	@GetMapping(value = "/reportView")
	public String reportView() {
		return "report/reportView";
	}//reportView
}


