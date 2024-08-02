package kr.co.iei.member.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberRowMapper implements RowMapper<Member> {

	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		Member m = new Member();
		m.setAcorns(rs.getInt("acorns"));
		m.setEnrollDate(rs.getString("enroll_date"));
		m.setMemberAddr(rs.getString("member_addr"));
		m.setMemberGender(rs.getString("member_gender"));
		m.setMemberId(rs.getString("member_id"));
		m.setMemberLevel(rs.getInt("member_level"));
		m.setMemberName(rs.getString("member_name"));
		m.setMemberNickname(rs.getString("member_nickname"));
		m.setMemberNo(rs.getInt("member_no"));
		m.setMemberPhone(rs.getString("member_phone"));
		m.setMemberPw(rs.getString("member_pw"));
		m.setProfileMsg(rs.getString("profile_msg"));
		m.setProfilePhoto(rs.getString("profile_photo"));
		m.setTotalCount(rs.getInt("total_count"));
		return m;
	}

}
