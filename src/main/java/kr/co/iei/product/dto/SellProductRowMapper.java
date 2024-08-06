package kr.co.iei.product.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class SellProductRowMapper implements RowMapper<SellProduct>{

	@Override
	public SellProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
		SellProduct sp = new SellProduct();
		sp.setProductPath(rs.getString("product_path"));
		sp.setProductUrl(rs.getString("product_url"));
		sp.setProductImg(rs.getString("prodcut_img"));
		sp.setProductName(rs.getString("product_name"));
		sp.setProductPrice(rs.getInt("product_price"));
		sp.setProductListNo(rs.getInt("product_list_no"));
		sp.setProductNo(rs.getInt("product_no"));
		return sp;
	}
	
}
