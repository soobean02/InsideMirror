package kr.co.iei.guestbook.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.guestbook.dto.GuestBook;

import kr.co.iei.guestbook.dto.GuestBookRowMapper;
@Repository
public class GuestBookDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private GuestBookRowMapper guestBookRowMapper;
	
	public int insertComment(GuestBook gb) {
		String query = "insert into guestbook values(guestbook_seq.nextval,?,to char(sysdate,'yyyy-mm-dd)";
		Object[] params = {gb.getGuestWriterNo()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int updateComment(GuestBook gb) {
		String query = "update guestbook set guestbook_comment_content=? where guestbook_comment_no=?";
		Object[] params = {gb.getGuestCommentContent(),gb.getGuestCommentNo()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteComment(GuestBook gb) {
		String query = "delete from guestbook where guestbook_comment_no=?";
		Object[] params = {gb.getGuestCommentNo()};
		int result = jdbc.update(query, params);
		return result;
	}
}
