package kr.co.iei.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.board.model.dto.BoardCommentRowMapper;
import kr.co.iei.board.model.dto.BoardFileRowMapper;
import kr.co.iei.board.model.dto.BoardRowMapper;

@Repository
public class BoardDao {

	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private BoardRowMapper boardRowMapper;
	@Autowired
	private BoardFileRowMapper boardFileRowMapper;
	@Autowired
	private BoardCommentRowMapper boardCommentRowMapper;
	
	
	public List selectBoardList(int start, int end) {
		String query = "select b_tbl.*,\r\n" + //
						"(select count(*) from board_like where board_no = b_tbl.board_no) as board_like\r\n" + //
						"from (select rownum as rnum, b.* from (select * from board order by 1 desc)b)b_tbl where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query, boardRowMapper, params);
		return list;
	}//자유 게시글 10개 조회


	public int selectBoardTotalCount() {
		String query = "select count(*) from board";

		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}//자유 게시글 총 개수 조회



}
