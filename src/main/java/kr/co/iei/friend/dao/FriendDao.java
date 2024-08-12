package kr.co.iei.friend.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.friend.dto.Friend;
import kr.co.iei.friend.dto.FriendRowMapper;


@Repository
public class FriendDao {
	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private FriendRowMapper friendRowMapper;

	public List<Friend> selectAllList(Friend f) {

		String query = "SELECT * FROM friend WHERE member_no = ? ORDER by friend_nickname ";
	    Object[] params = { f.getMemberNo() };
	    List<Friend> list = jdbc.query(query, friendRowMapper, params);
		return list;

	}

	public int friendCancel(Friend f) {
		String query = "delete from friend where friend_No = ?";
		Object[] params = { f.getFriendNo()};
		return jdbc.update(query, params);
	}

	public int friendRequest(Friend f) {
		String query = "insert into friend values (friend_seq.nextval,?,?,?)";
		Object[] params = {f.getMemberNo(),f.getFriendNo(),f.getFriendNickName()};
		int result = jdbc.update(query,params);
		return result;
	}
}