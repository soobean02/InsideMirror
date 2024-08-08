package kr.co.iei.product.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BuyProductRowMapper implements RowMapper<BuyProduct>{

	@Override
	public BuyProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
		BuyProduct bp = new BuyProduct();
		bp.setRefundDate(rs.getDate("refund_date"));
		bp.setRefundStatus(rs.getString("refund_status"));
		bp.setBuyDate(rs.getDate("buy_date"));
		bp.setMemberNo(rs.getInt("member_no"));
		bp.setProductNo(rs.getInt("product_no"));
		bp.setBuyNo(rs.getInt("buy_no"));
		return bp;
	}

}
