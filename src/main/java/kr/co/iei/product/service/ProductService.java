package kr.co.iei.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.customer.dto.CustomerListData;
import kr.co.iei.product.dao.ProductDao;
import kr.co.iei.product.dto.ProductListData;
import kr.co.iei.product.dto.SellProduct;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Transactional
	public int updateAcorns(int acorns) {
		/*멤버 테이블에 도토리 넣어주기*/
		int result1 = productDao.updateAcorns(acorns);
		
		/*도토리 구매 이력 테이블에 도토리 정보 넣어주기*/
		int result2 = productDao.insertAcorns(acorns);
		
		if(result1>0 && result2>0) { // 도토리 insert 성공 1 반환
			return 1;
		}else { // 도토리 insert 실패 0 반환
			return 0;
		}
	}
	
	public List selectProductPhoto() {
		List product = productDao.selectProductPhoto();
		return product;
	}

	
	/*판매 상품 */
	public ProductListData selectProduct(int reqPage) {
		// 한 페이지당 10개의 글 조회
		int numPerPage = 10;
		// 시작번호
		int end = reqPage * numPerPage; // 1페이지면 10, 2페이지면 20 ...
		int start = end - numPerPage + 1; // 10 - 10 = 1번 부터..., 20 - 10 + 1 = 11 번 부터...
		
		List list = productDao.selectProductList(start, end);
		// 전체 페이지 수 계산
		int totalCount = productDao.selectProductTotalCount();
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
			pageNavi += "<a class='page-item' href='/product/productList?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a></li>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='page-item active-page' href='/product/productList?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='page-item' href='/product/productList?reqPage="+pageNo+"'>";				
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
			pageNavi += "<a class='page-item' href='/product/productList?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a></li>";
		}
		
		// ul 닫기
		pageNavi += "</ul>";
		ProductListData pld = new ProductListData(list, pageNavi);
//		Customer customer = customerDao.selectCustomerList(reqPage);
		return pld;
	}

	//상품 상세보기 후 수정, 삭제
	public int productView(SellProduct sp) {
		return 0;
	}//productView
}
