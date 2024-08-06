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
        String query = "insert into guestbook (guestbook_comment_no, guest_writer_no, guest_comment_content, guest_comment_date) " +
        				"values (guestbook_seq.nextval, ?, ?, sysdate)";
        Object[] params = {gb.getGuestWriterNo(), gb.getGuestCommentContent() };
        return jdbc.update(query, params);
    }

    public int updateComment(GuestBook gb) {
        String query = "update guestbook set guest_comment_content = ? where guestbook_comment_no = ?";
        Object[] params = {gb.getGuestCommentContent(), gb.getGuestCommentNo() };
        return jdbc.update(query, params);
    }

    public int deleteComment(GuestBook gb) {
        String query = "delete from guestbook where guestbook_comment_no = ?";
        Object[] params = {gb.getGuestCommentNo() };
        return jdbc.update(query, params);
    }
} 