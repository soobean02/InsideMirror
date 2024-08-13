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
        String query = "SELECT * FROM friend WHERE member_no = ? ORDER BY friend_nickname";
        Object[] params = { f.getMemberNo() };
        return jdbc.query(query, friendRowMapper, params);
    }

    public int friendCancel(Friend f) {
        String query = "DELETE FROM friend WHERE friend_no = ?";
        Object[] params = { f.getFriendNo() };
        return jdbc.update(query, params);
    }

    public int friendRequest(Friend f) {
        String query = "INSERT INTO friend (friend_no, member_no, friend_nickname) VALUES (friend_seq.NEXTVAL, ?, ?)";
        Object[] params = { f.getMemberNo(), f.getFriendNickName() };
        return jdbc.update(query, params);
    }

    public List<Friend> selectList(Friend f, String keyword) {
        String query = "SELECT * FROM friend WHERE member_no = ? AND LOWER(friend_nickname) LIKE LOWER(?) ORDER BY friend_nickname ASC";
        String searchKeyword = "%" + keyword + "%";
        Object[] params = { f.getMemberNo(), searchKeyword };
        return jdbc.query(query, friendRowMapper, params);
    }
}