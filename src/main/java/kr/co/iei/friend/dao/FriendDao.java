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
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private FriendRowMapper friendRowMapper;

	public List selectAllList() {
		String query = "select friend_member_no, member_no from friend join member on (friend_member_no = member_no) where friend_member_name = ?";
		
		return null;
	}

  
  
}



