package kr.co.iei.photo.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class PhotoRowMapper implements RowMapper<Photo> {

	@Override
	public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Photo p = new Photo();
		p.setMemberNo(rs.getInt("member_no"));
		p.setPhotoContent(rs.getString("photo_content"));
		p.setPhotoDate(rs.getDate("photo_date"));
		p.setPhotoNo(rs.getInt("photo_no"));
		p.setPhotoTitle(rs.getString("photo_title"));


		return p;
	}

}
