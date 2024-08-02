package kr.co.iei.report.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.report.model.dao.ReportDao;

@Service
public class ReportService {
	@Autowired
	private ReportDao reportDao;
}
