package kr.co.iei.product.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.dto.MemberRowMapper;
import kr.co.iei.product.dto.AcronHistoryRowMapper;
import kr.co.iei.product.dto.BuyInfoRowMapper;
import kr.co.iei.product.dto.BuyProduct;
import kr.co.iei.product.dto.BuyProductRowMapper;
import kr.co.iei.product.dto.ProductListRowMapper;
import kr.co.iei.product.dto.RefundRowMapper;
import kr.co.iei.product.dto.SellBuyProduct;
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
	@Autowired
	private BuyInfoRowMapper buyInfoRowMapper;
	@Autowired
	private RefundRowMapper refundRowMapper;
	
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
	
	/*판매 상품 리스트 출력 - 전체 출력*/
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
	
	/*판매 상품 리스트 출력 - 타입별 출력*/
	public List selectProductList(int start, int end, int type) {
		String query = "select * from (select rownum as rnum, n.* from (select * from sell_product where product_list_no=? order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {type,start,end};
		List list = jdbc.query(query,sellProductRowMapper, params);
		return list;
	}
	public int selectProductTotalCount(int type) {
		String query = "select count(*) from sell_product where product_list_no=?";
		Object[] params = {type};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}

	/*판매 상품 상세보기 정보 출력*/
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
	
	// 구매한 상품 테이블에 insert 상품 하기
	public int insertProductAdd(Member m, SellProduct sp) {
		String query = "INSERT INTO buy_product VALUES(buy_product_seq.nextval, ?, ?, TO_CHAR(SYSDATE,'yyyy-mm-dd'), '사용',null, 0)";
		Object[] params = {sp.getProductNo(),m.getMemberNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	// 구매한 상품 리스트 보기(전체) - buyInfoRowMapper
	public List selectBuyProductList(int start, int end, Member member) {
		String query = "select * from (select rownum as rnum, n.* from (select * from buy_product join sell_product using(product_no) where member_no=? order by 1 desc)n) join sell_product s using(product_no) where rnum between ? and ?";
		Object[] params = {member.getMemberNo(),start,end};
		List list = jdbc.query(query,buyInfoRowMapper, params);
		return list;
	}
	public int selectBuyProductTotalCount(Member member) {
		String query = "select count(*) from buy_product where member_no=?";
		Object[] params = {member.getMemberNo()};
		int totalCount = jdbc.queryForObject(query, Integer.class,params);
		return totalCount;
	}
	
	// 구매한 상품 리스트 보기 (타입별)
	public List selectBuyProductList(int start, int end, Member member, int type) {
		String query = "select * from (select rownum as rnum, n.* from (select * from buy_product join sell_product using(product_no) where member_no=? and product_list_no=? order by 1 desc)n) join sell_product s using(product_no) where rnum between ? and ?";
		Object[] params = {member.getMemberNo(),type,start,end};
		List list = jdbc.query(query,buyInfoRowMapper, params);
		return list;
	}
	public int selectBuyProductTotalCount(Member member, int type) {
		String query = "select count(*) from (select * from buy_product join sell_product using(product_no) where member_no=? and product_list_no=?)";
		Object[] params = {member.getMemberNo(),type};
		int totalCount = jdbc.queryForObject(query, Integer.class,params);
		return totalCount;
	}
	
	
	public int addProduct(SellProduct sp) {
		String query = "insert into sell_product values(sell_product_seq.nextval,?,?,?,?,'url','이미지 경로')";
		Object[] params = {sp.getProductListNo(), sp.getProductPrice(), sp.getProductName(), sp.getProductImg()};
		int result = jdbc.update(query, params);
		return result;
	}//addProduct
	
	// 구매한 상품 상세보기 정보 출력
	public SellBuyProduct selectBuyProductInfo(int buyNo) {
		String query = "select * from buy_product join sell_product using(product_no) where buy_no=?";
		Object[] params = {buyNo};
		List list = jdbc.query(query, buyInfoRowMapper, params);
		if(list.isEmpty()) { // 사용자가 구매하지 않음
			return null;
		}else {
			return (SellBuyProduct)list.get(0);
		}
	}
	
	
	public int productUpdate(SellProduct sp) {
		String query = "update sell_product set product_name=?, product_list_no=?, product_price=? where product_no=?";
		Object[] params = {sp.getProductName(), sp.getProductListNo(), sp.getProductPrice(), sp.getProductNo()};
		int result = jdbc.update(query, params);
		return result;
	}//productUpdate
	
	public int adminAddProduct(SellProduct sp) {
		String query = "insert into sell_product values(sell_product_seq.nextval,?,?,?,?,'url','이미지 경로')";
		Object[] params = {sp.getProductListNo(), sp.getProductPrice(), sp.getProductName(), sp.getProductImg()};
		int result = jdbc.update(query, params);
		return result;
	}//adminAddProduct
	
	public int productDelete(int productNo) {
		String query = "delete from sell_product where product_no=?";
		Object[] params = {productNo};
		int result = jdbc.update(query, params);
		return result;
	}//productDelete
	
	public List selectRefundList(int start, int end) {
		String query = "select * from(select rownum as rnum, re.* from (select b.product_no, s.product_name, m.member_name, m.member_phone, s.product_price, b.refund_status, b.refund_date from buy_product b, member m, sell_product s where b.member_no = m.member_no and s.product_no = b.product_no order by 1 desc)re)where rnum between ? and ?";
		Object[] params = {start, end};
		List refund = jdbc.query(query, refundRowMapper, params);
		return refund;
	}//selectRefundList
	
	public int selectRefundListTotalCount() {
		String query = "select count(*) from buy_product";
		int refundListTotalCount = jdbc.queryForObject(query, Integer.class);	
		return refundListTotalCount;
	}//selectRefundListTotalCount
	
	public List selectThreeProduct() {
		String query = "select * from (select rownum as rnum, n.* from (select * from sell_product order by 1 desc)n) where rnum between 1 and 3";
		List threeProductList = jdbc.query(query, sellProductRowMapper);
		return threeProductList;
	}//selectThreeProduct




	
	

}