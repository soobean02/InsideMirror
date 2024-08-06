package kr.co.iei.product.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.member.model.dto.MemberRowMapper;
import kr.co.iei.product.dto.AcronHistoryRowMapper;

import kr.co.iei.product.dto.BuyProductRowMapper;
import kr.co.iei.product.dto.ProductListRowMapper;
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
	public int updateAcorns(int acorns) {
		String query = "UPDATE MEMBER SET ACORNS = ACORNS + ? WHERE MEMBER_NO=?";
		Object[] params = {acorns,1}; // 여기 바꿔야함! MEMBER_NO 임시로 넣어둠
		int result = jdbc.update(query,params);
		return result;
	}

}
