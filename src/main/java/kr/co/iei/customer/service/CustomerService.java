package kr.co.iei.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.customer.dao.CustomerDao;
import kr.co.iei.customer.dto.Customer;
import kr.co.iei.customer.dto.CustomerListData;

@Service
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;

	public CustomerListData selectCustomerList(int reqPage) {
		// 한 페이지당 10개의 글 조회
		int numPerPage = 5;
		// 시작번호
		int end = reqPage * numPerPage; // 1페이지면 10, 2페이지면 20 ...
		int start = end - numPerPage + 1; // 10 - 10 = 1번 부터..., 20 - 10 + 1 = 11 번 부터...
		
		List list = customerDao.selectCustomerList(start, end);
		// 전체 페이지 수 계산
		int totalCount = customerDao.selectCustomerTotalCount();
		// 전체 페이지 수 계산
		int totalPage = 0;
		
		if(totalCount%numPerPage == 0) { // 나머지가 없다면
			totalPage = totalCount/numPerPage;
		}else { // 나머지가 있다면
			totalPage = totalCount/numPerPage+1;
		} // 올림연산, 삼항 연산도 많이 씀
		// 페이지네비 사이즈 조정
		int pageNaviSize = 5;
		
		// 페이지네비 시작번호를 지정
		// reqPage 1 ~ 5 : 1 2 3 4 5
		// reqPage 6 ~ 10 : 6 7 8 9 10
		// reqPage 11 ~ 15 : 11 12 13 14 15
		
		int pageNo  = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		
		// html코드 작성(페이지 네비게이션 작성)
		String pageNavi = "<ul class='pagination circle-style'>";
		
		//이전 버튼(1페이지로 시작하지 않으면)
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/customer/customerList?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/customer/customerList?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/customer/customerList?reqPage="+pageNo+"'>";				
			}
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			
			// 페이지를 만들다가 최종페이지를 출력했으면 더 이상 반복하지 않고 종료
			if(pageNo > totalPage) {
				break;
			}
		}
		// 다음 버튼(최종 페이지를 출력하지 않았으면)
		if(pageNo<=totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/customer/customerList?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		
		// ul 닫기
		pageNavi += "</ul>";
		CustomerListData cld = new CustomerListData(list, pageNavi);
//		Customer customer = customerDao.selectCustomerList(reqPage);
		return cld;
	}
	
	@Transactional
	public int insertCustomerInq(Customer c) {
		int result = customerDao.insertCustomerInq(c);
		return 0;
	}


	
	//customer status 상태 '승인 완료' 업데이트 로직
	@Transactional
	public int updateStatus(Customer c) {
		int result = customerDao.updateStatus(c);
		return result;
	}//updateStatus

	
	/*회원 문의 페이지 내용 보기*/
	public Customer selectCustomerContent(int inqNo) {
		Customer customer = customerDao.selectCustomerContent(inqNo);
		return customer;
	}

	//관리자 메인 홈피에서 고객센터 글 5개만 출력
	public List selectFiveReport() {
		List fiveReportList = customerDao.selectFiveReport();
		return fiveReportList;
	}//selectFiveReport


}
