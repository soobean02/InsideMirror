package kr.co.iei.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardCommentRowMapper implements RowMapper<BoardComment>{

	@Override
	public BoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardComment comment = new BoardComment();
		comment.setBoardCommentDate(rs.getString("board_comment_date"));
		comment.setBoardCommentNo(rs.getInt("board_comment_no"));
		comment.setBoardCommentRef(rs.getInt("board_comment_ref"));
		comment.setBoardCommentContent(rs.getString("board_comment_content"));
		comment.setMemberNo(rs.getInt("member_no"));
		comment.setBoardNo(rs.getInt("board_no"));
		return comment;
	}

	
}
