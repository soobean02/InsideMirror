package kr.co.iei.report.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.customer.dto.CustomerListData;
import kr.co.iei.report.model.dao.ReportDao;
import kr.co.iei.report.model.dto.ReportListData;

@Service
public class ReportService {
	@Autowired
	private ReportDao reportDao;

	public ReportListData selectReportList(int reqPage) {
	
		int numPerPage = 5;
		
		int end = reqPage * numPerPage; 
		int start = end - numPerPage + 1;

		List reportList = reportDao.selectReportList(start, end);

		int totalCount = reportDao.selectReportTotalCount();
		
		int totalPage = 0;

		if (totalCount % numPerPage == 0) {
			totalPage = totalCount / numPerPage;
		} else {
			totalPage = totalCount / numPerPage + 1;
		}//else
		
		int pageNaviSize = 5;

		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

		String pageNavi = "<ul class='pagination circle-style'>";

		if (pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/report/reportList?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}//if

		for (int i = 0; i < pageNaviSize; i++) {
			pageNavi += "<li>";
			if (pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/report/reportList?reqPage=" + pageNo + "'>";
			} else {
				pageNavi += "<a class='page-item' href='/report/reportList?reqPage=" + pageNo + "'>";
			}//else
			
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;

			if (pageNo > totalPage) {
				break;
			}//if
		}//for
		
		
		if (pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/report/reportList?reqPage=" + pageNo + "'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}//if

		pageNavi += "</ul>";
		
		ReportListData rld = new ReportListData(reportList, pageNavi);
		
		return rld;
	}//selectReportList
}
