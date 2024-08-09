package kr.co.iei.member.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.dto.MemberRowMapper;
import kr.co.iei.member.model.dto.Title;
import kr.co.iei.member.model.dto.TitleRowMapper;

@Repository
public class MemberDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private MemberRowMapper memberRowMapper;
	@Autowired
	private TitleRowMapper titleRowMapper;

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
		Object[] params = {m.getMemberId(),m.getMemberPw(),m.getMemberNickName(),m.getMemberName(),m.getMemberGender(),m.getMemberPhone(),m.getMemberAddr()};
		System.out.println(m);
		int result = jdbc.update(query, params);
		return result;
	}


	public Member selectOneMember(String checkNickname) {
		String query = "select * from member where member_nickname=?";
		Object[] params = {checkNickname};
		List member = jdbc.query(query, memberRowMapper, params);
		if(member.isEmpty()) {
			return null;			
		}else {
			return (Member)member.get(0);
		}
	}


	public int resetPassword(Member m) {
		String query = "update member set member_pw=? where member_id=?";
		Object[] params = {m.getMemberPw(),m.getMemberId()};
		int result = jdbc.update(query, params);
		return result;
	}


	public Member checkPassword(String memberPw) {
		String query = "select * from member where member_pw=?";
		Object[] params = {memberPw};
		List member = jdbc.query(query, memberRowMapper, params);
		if(member.isEmpty()) {
			return null;
		}else {
			return (Member)member.get(0);
		}
	}
	public List selectFiveMember() {
		String query = "select * from(select rownum as rnum, m.* from (select * from member order by 1 desc)m)where rnum between 1 and 5";
		List fiveMemberList = jdbc.query(query, memberRowMapper);
		return fiveMemberList;
	}//selectFiveMember


	public Member selectAdminOneMember(int memberNo) {
		String query = "select * from member where member_no=?";
		Object[] params = {memberNo};
		List member = jdbc.query(query, memberRowMapper, params);
		if(member.isEmpty()) {
			return null;			
		}else {
			return (Member)member.get(0);
		}//else
	}//selectAdminOneMember


	public List findMember(String findMember) {
		String query = "select * from member where member_nickname || member_name like ?";
		String searchKeyword = "%" + findMember+"%"; 
		Object[] params = {searchKeyword};
		List memberList = jdbc.query(query, memberRowMapper, params);
		System.out.println(memberList);
		if(memberList.isEmpty()) {
			return null;
		}else {
			return memberList;
		}
	}


	public List viewAllMember() {
		String query = "select * from member order by total_count";
		return null;
	}


	public List title(Member member) {
		String query = "select *"
				+ "from member m"
				+ "left join photo p using(member_no)"
				+ "left join board b using(member_no)"
				+ "where member_no =? and rownum <=2"
				+ "order by p.photo_date desc";
		Object[] params = {member.getMemberNo()};
		List title = jdbc.query(query, titleRowMapper, params);
		System.out.println(title);
		if(title.isEmpty()) {
			return null;			
		}else {
			return title;
		}
	}

}
