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

    public List<Friend> findAllFriend() {
        String sql = "SELECT * FROM friend"; 
        return jdbcTemplate.query(sql, new FriendRowMapper()); 
    }
}



