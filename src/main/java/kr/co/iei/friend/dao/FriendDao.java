package kr.co.iei.friend.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.friend.dto.Friend;
import kr.co.iei.friend.dto.FriendRowMapper;
import kr.co.iei.guestbook.dto.GuestBook;


@Repository
public class FriendDao {
	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private FriendRowMapper friendRowMapper;

	public List<Friend> selectAllList(Friend f) {

		String query = "SELECT * FROM friend WHERE member_no = ? ORDER by friend_nickname ASC";
	    Object[] params = { f.getMemberNo() };
	 // 파라미터 디버깅
	    System.out.println("Executing query: " + query);
	    System.out.println("With parameters: ");
	    for (int i = 0; i < params.length; i++) {
	        System.out.println("Parameter " + (i + 1) + ": " + params[i]);
	    }

	    // 쿼리 실행
	    List<Friend> list = jdbc.query(query, friendRowMapper, params);

	    // 결과 디버깅
	    System.out.println("Query result: " + list);
		return list;

	}

	public int deleteComment(Friend f) {
		String query = "DELETE FROM friend WHERE member_No = ?";
		Object[] params = { f.getMemberNo() };
		return jdbc.update(query, params);
	}
}