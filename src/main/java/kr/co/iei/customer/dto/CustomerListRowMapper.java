package kr.co.iei.customer.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerListRowMapper implements RowMapper<CustomerList>{

	@Override
	public CustomerList mapRow(ResultSet rs, int rowNum) throws SQLException {
		CustomerList cl = new CustomerList();
		cl.setCustomerListName(rs.getString("customer_name"));
		cl.setCustomerList(rs.getInt("customer_list"));
		return null;
	}

}
