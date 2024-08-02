package kr.co.iei.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.report.model.service.ReportService;

@Controller
@RequestMapping(value = "/report")
public class ReportController {
	@Autowired
	private ReportService reportService;
}
