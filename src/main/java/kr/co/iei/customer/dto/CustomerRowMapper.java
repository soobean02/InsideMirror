package kr.co.iei.customer.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerRowMapper implements RowMapper<Customer>{

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer ct = new Customer();
		ct.setInqDate(rs.getDate("inq_date"));
		ct.setStatus(rs.getString("status"));
		ct.setInqTitle(rs.getString("inq_title"));
		ct.setCustomerList(rs.getInt("customer_list"));
		ct.setMemberNo(rs.getInt("member_no"));
		ct.setInqNo(rs.getInt("inq_no"));
		return ct;
	}
	
}
