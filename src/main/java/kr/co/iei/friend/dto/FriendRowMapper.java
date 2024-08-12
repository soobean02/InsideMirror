package kr.co.iei.friend.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FriendRowMapper implements RowMapper<Friend>{

	@Override
	public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println("FriendRowMapper: mapRow called"); // 디버깅 로그 추가
		Friend f = new Friend();
		f.setFriendMemberNo(rs.getInt("friend_member_no"));
		f.setMemberNo(rs.getInt("member_no"));
		f.setFriendNo(rs.getInt("friend_no"));
		f.setFriendNickName(rs.getString("friend_nickname"));
		
		return f;
	}

}