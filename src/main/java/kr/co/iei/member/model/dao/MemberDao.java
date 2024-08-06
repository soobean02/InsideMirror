package kr.co.iei.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.member.model.dto.Member;
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


	public Member selectOneMember(Member m) {
		String query = "select * from member where member_id=? and member_pw=?";
		Object[] params = {m.getMemberId(),m.getMemberPw()};
		List member = jdbc.query(query, memberRowMapper ,params);
		if(member.isEmpty()) {
			return null;			
		}else {
			return (Member)member.get(0);
		}//else
	}//selectOneMember


	public int insertMember(Member m) {
		String query = "insert into member values(member_seq.nextval,?,?,?,?,?,?,?,2,to_char(sysdate,'yyyy-mm-dd'),'InsideMirror에 메세지를 적어보세요','이미지첨부',0,0)";
		Object[] params = {m.getMemberId(),m.getMemberPw(),m.getMemberNickname(),m.getMemberName(),m.getMemberGender(),m.getMemberPhone(),m.getMemberAddr()};
		System.out.println(m);
		int result = jdbc.update(query, params);
		return result;
	}
}
