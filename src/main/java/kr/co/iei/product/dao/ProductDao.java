package kr.co.iei.product.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.dto.MemberRowMapper;
import kr.co.iei.product.dto.AcronHistoryRowMapper;
import kr.co.iei.product.dto.BuyProduct;
import kr.co.iei.product.dto.BuyProductRowMapper;
import kr.co.iei.product.dto.ProductListRowMapper;
import kr.co.iei.product.dto.SellProduct;
import kr.co.iei.product.dto.SellProductRowMapper;

@Repository
public class ProductDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private ProductListRowMapper ProductListRowMapper; // 상품 목록(목록 번호, 목록 이름)
	@Autowired
	private SellProductRowMapper sellProductRowMapper; // 판매상품(번호,목록번호(참조), 가격, 이름, 이미지, url)
	@Autowired
	private BuyProductRowMapper buyProductRowMapper; // 구매한 상품(상품 번호, 판매 상품 번호(참조), 멤버 번호(참조), 구매날짜, 환불 상태, 환불 날짜)
	@Autowired
	private AcronHistoryRowMapper acorAcronHistoryRowMapper; // 도토리 구매이력 (도토리 번호, 회원 번호, 도토리 가격, 도토리 구매일)
	@Autowired
	private MemberRowMapper memberRowMapper;
	
	/* 멤버 테이블 도토리 구매 - update */
	public int updateAcorns(Member m) {
		String query = "UPDATE MEMBER SET ACORNS = ACORNS + ? WHERE MEMBER_NO=?";
		Object[] params = {m.getAcorns(),m.getMemberNo()}; // 여기 바꿔야함! MEMBER_NO 임시로 넣어둠
		int result = jdbc.update(query,params);
		return result;
	}
	/*도토리 구매 이력 - insert*/
	public int insertAcorns(Member m) {
		String query = "insert into acorns_purchase_history values(ACORNS_PURCHASE_HISTORY_SEQ.NEXTVAL,?,?,TO_CHAR(SYSDATE,'yyyy-mm-dd'))";
		System.out.println(m);
		Object[] params = {m.getMemberNo(),(m.getAcorns()*100)};// 여기 바꿔야함! MEMBER_NO 임시로 넣어둠
		int result = jdbc.update(query,params);
		return result;
	}
	
	/*구매 상품 출력 - 3개 짜리*/
	public List selectProductPhoto() {
		String query = "SELECT * FROM sell_product WHERE ROWNUM <= 3";
		List list = jdbc.query(query, sellProductRowMapper);
		if(list.isEmpty()) {
			return null;
		}
		return list;
	}

	public List selectProductList(int start, int end) {
		String query = "select * from (select rownum as rnum, n.* from (select * from sell_product order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query,sellProductRowMapper, params);
		return list;
	}
	
	public int selectProductTotalCount() {
		String query = "select count(*) from sell_product";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public SellProduct selectProductInfo(int productNo) {
		String query = "select * from sell_product where product_no = ?";
		Object[] params = {productNo};
		List list = jdbc.query(query, sellProductRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}else {
			return (SellProduct)list.get(0);
		}
	}
	// 상품 구매 시 회원 지갑에서 도토리 빼가기
	public int updateAcornMinus(Member m, SellProduct sp) {
		String query = "UPDATE MEMBER SET ACORNS = ACORNS - ? WHERE MEMBER_NO=?";
		Object[] params = {sp.getProductPrice(),m.getMemberNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	// 구매한 상품 정보 출력
	public BuyProduct selectOneProduct(Member member, int productNo) {
		String query = "SELECT * FROM buy_product WHERE MEMBER_NO=? and product_no = ?";
		Object[] params = {member.getMemberNo(), productNo};
		List list = jdbc.query(query, buyProductRowMapper, params);
		if(list.isEmpty()) { // 사용자가 구매하지 않음
			return null;
		}else {
			return (BuyProduct)list.get(0); // 사용자가 구매한 상품
		}
	}
	
	// 구매한 상품 테이블에 insert 상품 하기 // 환불 날짜... 환불 상태는 사용으로 하면 되나 not null로 되어있음 ... 홀리 몰리 과카몰리
	public int insertProductAdd(Member m, SellProduct sp) {
		String query = "INSERT INTO buy_product VALUES(buy_product_seq.nextval, ?, ?, TO_CHAR(SYSDATE,'yyyy-mm-dd'), '사용',null)";
		Object[] params = {sp.getProductNo(),m.getMemberNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	
	public int addProduct(SellProduct sp) {
		String query = "insert into sell_product values(sell_product_seq.nextval,?,?,?,?,?,?)";
		Object[] params = {sp.getProductListNo(), sp.getProductPrice(), sp.getProductName(), sp.getProductImg(), sp.getProductUrl(), sp.getProductPath()};
		int result = jdbc.update(query, params);
		return result;
	}//addProduct
	
	
	public int productUpdate(SellProduct sp) {
		String query = "update sell_product set product_name=?, product_list_no=?, product_price=? where product_no=?";
		Object[] params = {sp.getProductName(), sp.getProductListNo(), sp.getProductPrice(), sp.getProductNo()};
		int result = jdbc.update(query, params);
		return result;
	}//productUpdate
	
	

}