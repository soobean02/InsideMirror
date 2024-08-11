package kr.co.iei.photo.model.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class PhotoRowMapper implements RowMapper<Photo> {

	@Override
	public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Photo p = new Photo();
		p.setMemberNo(rs.getInt("member_no"));
		p.setPhotoContent(rs.getString("photo_content"));

		Date photoDate = rs.getDate("photo_date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String formattedDate = sdf.format(photoDate);
		p.setPhotoDate(formattedDate);



		p.setPhotoNo(rs.getInt("photo_no"));
		p.setPhotoTitle(rs.getString("photo_title"));

		try {
			p.setIsLike(rs.getInt("is_like"));
			p.setLikeCount(rs.getInt("like_count"));
			p.setIsBookmark(rs.getInt("is_bookmark"));
		} catch (Exception e) {
			p.setIsLike(0);
			p.setLikeCount(0);

			p.setIsBookmark(0);
		}

		return p;
	}

}
