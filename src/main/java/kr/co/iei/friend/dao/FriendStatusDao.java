package kr.co.iei.friend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import kr.co.iei.friend.dto.FriendStatusRowMapper;

@Repository
public class FriendStatusDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private FriendStatusRowMapper friendStatusRowMapper;
}
