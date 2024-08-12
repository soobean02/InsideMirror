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
    private JdbcTemplate jdbc;

    @Autowired
    private GuestBookRowMapper guestBookRowMapper;

    public int insertComment(GuestBook gb) {
        String query = "INSERT INTO guest_book VALUES (GUEST_BOOK_SEQ.NEXTVAL, ?, ?, ?, SYSDATE,?, ?)";		
        Object[] params = { gb.getMemberNo(), gb.getGuestWriterNo(), gb.getGuestCommentContent(),gb.getGuestBookType(), gb.getGuestNickName()};
        int result = jdbc.update(query, params);
        return result;
    }
    public boolean doesMemberExist(int memberNo) {
        String query = "SELECT COUNT(*) FROM member WHERE member_no = ?";
        Integer count = jdbc.queryForObject(query, new Object[]{memberNo}, Integer.class);
        return count != null && count > 0;
    }

    public boolean doesGuestWriterExist(int guestWriterNo) {
        String query = "SELECT COUNT(*) FROM guest_writer WHERE guest_writer_no = ?";
        Integer count = jdbc.queryForObject(query, new Object[]{guestWriterNo}, Integer.class);
        return count != null && count > 0;
    }
    

    public int updateComment(GuestBook gb) {
        String query = "UPDATE guest_book SET GUEST_COMMENT_CONTENT = ? WHERE GUEST_COMMENT_NO = ?";
        Object[] params = { gb.getGuestCommentContent(), gb.getGuestCommentNo() };
        return jdbc.update(query, params);       
    }

    public int deleteComment(GuestBook gb) {
        String query = "DELETE FROM guest_book WHERE GUEST_COMMENT_NO = ?";
        Object[] params = {gb.getGuestCommentNo()};
        return jdbc.update(query,params);       
    }

    public List<GuestBook> getAllComments(GuestBook gb) {
        String query = "SELECT * FROM guest_book WHERE member_no = ? ORDER BY GUEST_COMMENT_DATE ASC";
        Object[] params = {gb.getMemberNo()};
        List list = jdbc.query(query, guestBookRowMapper, params);
        return list;
        
    }
	public List<GuestBook> selectGuestBookList() {
		String sql = "SELECT * FROM guest_book ORDER BY GUEST_COMMENT_NO ASC";
        return jdbc.query(sql, (rs, rowNum) -> {
            GuestBook guestBook = new GuestBook();
            guestBook.setGuestCommentNo(rs.getInt("guest_Comment_No"));
            guestBook.setGuestWriterNo(rs.getInt("guest_Writer_No"));
            guestBook.setGuestCommentContent(rs.getString("guest_Comment_Content"));
            guestBook.setGuestCommentDate(rs.getDate("guest_Comment_Date"));
            guestBook.setMemberNo(rs.getInt("member_No"));
            guestBook.setGuestBookType(rs.getInt("guest_Book_Type"));
            return guestBook;
        });
    }
	public GuestBook getCommentByNo(int guestCommentNo) {
	    String query = "SELECT * FROM guest_book WHERE GUEST_COMMENT_NO = ?";
	    Object[] params = { guestCommentNo };
	    return jdbc.queryForObject(query, guestBookRowMapper, params);
	}
		
}