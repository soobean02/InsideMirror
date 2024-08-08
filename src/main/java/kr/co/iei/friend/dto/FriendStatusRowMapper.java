package kr.co.iei.friend.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FriendStatusRowMapper implements RowMapper<FriendStatus>{

	@Override
	public FriendStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		FriendStatus fs = new FriendStatus();
		fs.setFriendmemberNo(rs.getInt("friend_member_no"));
		fs.setMemberNo(rs.getInt("member_no"));
		return fs;
	}

}
