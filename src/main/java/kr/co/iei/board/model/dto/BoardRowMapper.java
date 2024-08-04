package kr.co.iei.board.model.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardRowMapper implements RowMapper<Board> {

	@Override
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board b = new Board();
		b.setBoardNo(rs.getInt("board_no"));
		b.setBoardTitle(rs.getString("board_title"));
		b.setMemberNo(rs.getInt("member_no"));
		b.setReadCount(rs.getInt("read_count"));
		Date boardDate = rs.getDate("board_date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String formattedDate = sdf.format(boardDate);
		b.setBoardDate(formattedDate);
		

		b.setBoardContent(rs.getString("board_content"));

		b.setBoardLike(rs.getInt("board_like"));

		b.setBoardWriter(rs.getString("board_writer_nickname"));
		return b;
	}

}
