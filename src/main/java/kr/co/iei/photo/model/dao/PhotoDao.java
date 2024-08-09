package kr.co.iei.photo.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

	public List selectPhotoList(int start, int end) {
		String query = "select rownum as rnum, p.*, \r\n" + //
						"(select member_nickname from member where member_no = p.member_no) as photo_writer\r\n" + //
						"from (select * from photo order by 1 desc)p";
		Object[] params = {start, end};
		List list = jdbc.query(query, photoRowMapper, params);
		return list;
	}
}
