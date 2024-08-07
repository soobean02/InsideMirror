package kr.co.iei.board.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.board.model.dto.Board;
import kr.co.iei.board.model.dto.BoardComment;
import kr.co.iei.board.model.dto.BoardCommentRowMapper;
import kr.co.iei.board.model.dto.BoardFile;
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
				"from (select rownum as rnum, b.*,\r\n" + //
				"(select member_nickname from member where member_no = b.member_no) as board_writer_nickname\r\n" + //
				"from (select * from board order by 1 desc)b)b_tbl where rnum between ? and ?";
		Object[] params = { start, end };
		List list = jdbc.query(query, boardRowMapper, params);
		return list;
	}// 자유 게시글 10개 조회

	public int selectBoardTotalCount() {
		String query = "select count(*) from board";

		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}// 자유 게시글 총 개수 조회

	public Board selectOneBoard(int boardNo) {
		String query = "select b.*,\r\n" + //
						"(select count(*) from board_like where board_no = b.board_no) as board_like,\r\n" + //
						"(select member_nickname from member where member_no = b.member_no) as board_writer_nickname\r\n" + //
						"from board b where board_no = ?";

		Object[] params = {boardNo};
		List list = jdbc.query(query, boardRowMapper, params);
		if(list.isEmpty()){
			return null;
		}
		return (Board)list.get(0);
	}//게시글 상세보기

	public int updateReadCount(int boardNo) {
		String query = "update board set read_count = read_count + 1 where board_no = ?";
		Object[] params = {boardNo};
		int result = jdbc.update(query, params);
		return result;
	}//게시글 상세보기시 조회수 증가

	public int insertBoard(Board board) {
		String query = "insert into board values(board_seq.nextval,?,?,?,sysdate,0)";
		Object[] params = {board.getMemberNo(),board.getBoardTitle(),board.getBoardContent()};
		int result = jdbc.update(query, params);
		return result;
	}//board테이블에 insert 게시글 작성

	public int selectBoardNo() {
		String qeury = "select max(board_no) from board";
		int result = jdbc.queryForObject(qeury, Integer.class);
		return result;
	}//게시글 번호 하나 조회 (이걸 조회해서 밑에 파일 넣는 작업할때 boardNo를 넣어 줄 수 있음)

	public int insertBoardFile(BoardFile boardFile) {
		String query = "insert into board_file values(board_file_seq.nextval,?,?)";
		Object[] params = {boardFile.getBoardNo(),boardFile.getFilepath()};
		int result = jdbc.update(query, params);
		return result;
	}//파일 insert

	public int insertBoardComment(BoardComment comment) {
		String query = "insert into board_comment values(board_comment_seq.nextval,?,?,?,sysdate,?,0)";
		String boardCommentRef = comment.getBoardCommentRef() == 0 ? null : String.valueOf(comment.getBoardCommentRef());
		Object[] params = {comment.getMemberNo(),comment.getBoardNo(),comment.getBoardCommentContent(),boardCommentRef};
		int result = jdbc.update(query, params);
		return result;
	}//댓글 대댓글 insert

	public List<BoardComment> selectComment(int boardNo) {
		String query = "select bc.*,\r\n" + //
						"(select count(board_comment_ref) from board_comment where board_comment_ref = bc.board_comment_no) as recomment_count\r\n" + //
						"from board_comment bc where board_no = ? and board_comment_ref is null order by 1 desc";
		Object[] params = {boardNo};
		List list = jdbc.query(query, boardCommentRowMapper, params);
		return list;
	}//댓글 조회

	public List selectReCommentList(int boardNo) {
		String query = "select * from board_comment where board_no = ? and board_comment_ref is not null order by 1 desc";
		Object[] params = {boardNo};
		List list = jdbc.query(query, boardCommentRowMapper, params);
		return list;
	}//대댓글 조회

	public int updateBoardComment(String commentContent, String boardCommentNo, String boardNo) {
		String query = "update board_comment set board_comment_content = ?, board_edit = 1 where board_comment_no = ? and board_no = ?";
		Object[] params = {commentContent, boardCommentNo, boardNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int removeBoardComment(String boardCommentNo) {
		String query = "delete from board_comment where board_comment_no = ?";
		Object[] params = {boardCommentNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public BoardComment selectOneComment(BoardComment comment) {
		String query = "select * from board_comment where board_no = ? and member_no = ? order by 1 desc";
		Object[] params = {comment.getBoardNo(), comment.getMemberNo()};

		List list = jdbc.query(query, boardCommentRowMapper, params);
		if(!list.isEmpty()){
			return (BoardComment)list.get(0);
		}
		return null;
	}//댓글 하나 조회


}
