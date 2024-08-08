package kr.co.iei.product.dto;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BuyInfoRowMapper implements RowMapper<SellBuyProduct>{

	@Override
	public SellBuyProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
		SellProduct sp = new SellProduct();
		sp.setProductUrl(rs.getString("product_url"));
		sp.setProductImg(rs.getString("product_img"));
		sp.setProductName(rs.getString("product_name"));
		sp.setProductPrice(rs.getInt("product_price"));
		sp.setProductListNo(rs.getInt("product_list_no"));
		sp.setProductNo(rs.getInt("product_no"));
		
		BuyProduct bp = new BuyProduct();
		bp.setBuyDate(rs.getDate("buy_date"));
		bp.setMemberNo(rs.getInt("member_no"));
		bp.setProductNo(rs.getInt("product_no"));
		bp.setBuyNo(rs.getInt("buy_no"));
		
		SellBuyProduct sb = new SellBuyProduct();
		sb.setSellProduct(sp);
		sb.setBuyProduct(bp);
		return sb;
	}

}
