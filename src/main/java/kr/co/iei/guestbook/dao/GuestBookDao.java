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
        String query = "INSERT INTO guestbook (guestbook_comment_no, guest_writer_no, guest_comment_content, guest_comment_date) " +
                       "VALUES (guestbook_seq.nextval, ?, ?, SYSDATE)";
        Object[] params = { gb.getGuestWriterNo(), gb.getGuestCommentContent() };
        return jdbc.update(query, params);
    }

    public int updateComment(GuestBook gb) {
        String query = "UPDATE guestbook SET guest_comment_content = ? WHERE guestbook_comment_no = ?";
        Object[] params = { gb.getGuestCommentContent(), gb.getGuestCommentNo() };
        return jdbc.update(query, params);
    }

    public int deleteComment(GuestBook gb) {
        String query = "DELETE FROM guestbook WHERE guestbook_comment_no = ?";
        Object[] params = { gb.getGuestCommentNo() };
        return jdbc.update(query, params);
    }
} 