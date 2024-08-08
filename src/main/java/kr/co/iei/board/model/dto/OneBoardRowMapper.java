package kr.co.iei.board.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class OneBoardRowMapper implements RowMapper<Board>{

	@Override
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board board = new Board();
		board.setBoardContent(rs.getString("board_content"));
		board.setBoardTitle(rs.getString("board_title"));
		return board;
	}

}
