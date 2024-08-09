package kr.co.iei.guestbook.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.guestbook.dto.GuestBook;
import kr.co.iei.guestbook.dto.GuestBookRowMapper;

@Repository
public class GuestBookDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GuestBookRowMapper guestBookRowMapper;

    public int insertComment(GuestBook gb) {
        String query = "INSERT INTO guest_book (GUEST_COMMENT_NO, MEMBER_NO, GUEST_WRITER_NO, GUEST_COMMENT_CONTENT, GUEST_COMMENT_DATE) VALUES (GUEST_BOOK_SEQ.NEXTVAL, ?, ?, ?, SYSDATE)";		
        Object[] params = { gb.getMemberNo(), gb.getGuestWriterNo(), gb.getGuestCommentContent() };
        return jdbcTemplate.update(query, params);
    }

    public int updateComment(GuestBook gb) {
        String query = "UPDATE guest_book SET GUEST_COMMENT_CONTENT = ? WHERE GUEST_COMMENT_NO = ?";
        Object[] params = { gb.getGuestCommentContent(), gb.getGuestCommentNo() };
        return jdbcTemplate.update(query, params);
    }

    public int deleteComment(GuestBook gb) {
        String query = "DELETE FROM guest_book WHERE GUEST_COMMENT_NO = ?";
        Object[] params = {gb.getGuestCommentNo()};
        return jdbcTemplate.update(query, params);
    }

    public List<GuestBook> getAllComments() {
        String query = "SELECT * FROM guest_book ORDER BY GUEST_COMMENT_DATE DESC";
        return jdbcTemplate.query(query, guestBookRowMapper);
    }
}