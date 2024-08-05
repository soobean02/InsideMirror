package kr.co.iei.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.member.model.dto.MemberRowMapper;

@Repository
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private MemberRowMapper memberRowMapper;

	public List selectAllMember(int start, int end) {
		String query = "select * from(select rownum as rnum, m.* from (select * from member order by 1 desc)m)where rnum between ? and ?";
		Object[] params = {start, end};
		List member = jdbc.query(query, memberRowMapper,params);
		return member;
	}//selectAllMember

	
	public int selectAllMemberTotalCount() {
		String query = "select count(*) from member";
		int allMemberTotalCount = jdbc.queryForObject(query, Integer.class);	
		return allMemberTotalCount;
	}//selectAllMemberTotalCount
}
