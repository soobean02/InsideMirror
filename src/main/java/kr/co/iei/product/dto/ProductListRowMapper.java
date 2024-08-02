package kr.co.iei.product.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductListRowMapper implements RowMapper<ProductList>{

	@Override
	public ProductList mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductList pl = new ProductList();
		pl.setProductListName(rs.getString("product_list_name"));
		pl.setProductListNo(rs.getInt("product_list_no"));
		return pl;
	}
		
}
