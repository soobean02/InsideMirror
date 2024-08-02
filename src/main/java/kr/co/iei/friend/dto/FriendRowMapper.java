package kr.co.iei.friend.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FriendRowMapper implements RowMapper<Friend>{

	@Override
	public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
		Friend f = new Friend();
		f.setFriendMemberNo(rs.getInt("friend_member_no"));
		f.setMemberNo(rs.getInt("member_no"));
		f.setFriendNo(rs.getInt("friend_no"));
		
		return f;
	}

}
