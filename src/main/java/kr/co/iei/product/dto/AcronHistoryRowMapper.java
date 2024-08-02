package kr.co.iei.product.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AcronHistoryRowMapper implements RowMapper<AcornHistory>{

	@Override
	public AcornHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
		AcornHistory ah = new AcornHistory();
		ah.setBuyDate(rs.getDate("buy_date"));
		ah.setAcornsPrice(rs.getInt("arcons_price"));
		ah.setMemberNo(rs.getInt("member_no"));
		ah.setAcornsNo(rs.getInt("acorns_no"));
		return ah;
	}
}
