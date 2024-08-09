package kr.co.iei.photo.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.photo.model.dto.Photo;
import kr.co.iei.photo.model.dto.PhotoRowMapper;

@Repository
public class PhotoDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private PhotoRowMapper photoRowMapper;

	public int insertPhoto(Photo p) {
		String query = "insert into photo values(photo_seq.nextval,?,?,?,sysdate)";
		Object[] params = {p.getMemberNo(), p.getPhotoTitle(), p.getPhotoContent()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int getTotalCount() {
		String query = "select count(*) from photo";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public List selectPhotoList(int start, int end, Member member) {
		String query = "select * from (select rownum as rnum, p.*\r\n" + //
						"from (select * from photo p2 where member_no = (select member_no from member where member_no = ?) order by 1 desc)p)\r\n" + //
						"where rnum between ? and ?";
		Object[] params = {member.getMemberNo(), start, end};
		List list = jdbc.query(query, photoRowMapper, params);
		return list;
	}
}
