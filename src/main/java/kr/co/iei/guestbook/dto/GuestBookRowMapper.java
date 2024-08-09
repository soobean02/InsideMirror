package kr.co.iei.guestbook.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class GuestBookRowMapper implements RowMapper<GuestBook>{

	@Override
	public GuestBook mapRow(ResultSet rs, int rowNum) throws SQLException {
		GuestBook gb = new GuestBook();
		gb.setGuestCommentContent(rs.getString("guest_comment_content"));
		gb.setGuestCommentDate(rs.getDate("guest_comment_date"));
		gb.setGuestCommentNo(rs.getInt("guest_comment_no"));
		gb.setGuestWriterNo(rs.getInt("guest_writer_no"));
		gb.setMemberNo(rs.getInt("member_no"));
		gb.setGuestBookType(rs.getInt("guest_book_type"));
		return gb;
	}

}
