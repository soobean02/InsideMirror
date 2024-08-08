package kr.co.iei.board.model.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardCommentRowMapper implements RowMapper<BoardComment>{

	@Override
	public BoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardComment comment = new BoardComment();

		Date commentDate = rs.getDate("board_comment_date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String formattedDate = sdf.format(commentDate);
		comment.setBoardCommentDate(formattedDate);
		
		comment.setBoardCommentNo(rs.getInt("board_comment_no"));
		comment.setBoardCommentRef(rs.getInt("board_comment_ref"));
		comment.setBoardCommentContent(rs.getString("board_comment_content"));
		comment.setMemberNo(rs.getInt("member_no"));
		comment.setBoardNo(rs.getInt("board_no"));
		comment.setBoardEdit(rs.getInt("board_edit"));
		
		try {
			comment.setBoardCommentWriter(rs.getString("member_nickname"));
			comment.setReCommentCount(rs.getInt("recomment_count"));
		} catch (Exception e) {
			comment.setReCommentCount(0);
		}
		return comment;
	}

	
}
