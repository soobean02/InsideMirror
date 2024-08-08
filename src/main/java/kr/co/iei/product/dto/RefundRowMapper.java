package kr.co.iei.product.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class RefundRowMapper implements RowMapper<Refund> {

	@Override
	public Refund mapRow(ResultSet rs, int rowNum) throws SQLException {
		Refund rf = new Refund();
		rf.setMemberName(rs.getString("member_name"));
		rf.setProductName(rs.getString("product_name"));
		rf.setMemberPhone(rs.getString("member_phone"));
		rf.setProductNo(rs.getInt("product_no"));
		rf.setProductPrice(rs.getInt("product_price"));
		rf.setRefundDate(rs.getDate("refund_date"));
		rf.setRefundStatus(rs.getString("refund_status"));
		return rf;
	}
}
