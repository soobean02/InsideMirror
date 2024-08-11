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

	public int getTotalCount(Member member) {
		String query = "select count(*) from photo where member_no = ?";
		Object[] params = {member.getMemberNo()};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}

	public List selectPhotoList(int start, int end, Member member) {
		String query = "select * from (select rownum as rnum, p.*,\r\n" + //
						"(select count(*) from photo_like where photo_no = p.photo_no and member_no = ?) as is_like,\r\n" + //
						"(select count(*) from photo_like where photo_no = p.photo_no) as like_count,\r\n" + //
						"(select count(*) from book_mark where photo_no = p.photo_no and member_no = ?) as is_bookmark\r\n" + //
						"from (select * from photo p2 where member_no = (select member_no from member where member_no = ?) order by 1 desc)p)\r\n" + //
						"where rnum between ? and ?";
		Object[] params = {member.getMemberNo(), member.getMemberNo(), member.getMemberNo(), start, end};
		List list = jdbc.query(query, photoRowMapper, params);
		return list;
	}

	public int pushLike(int photoNo, Member member) {
		String query = "insert into photo_like values(photo_like_seq.nextval,?,?)";
		Object[] params = {member.getMemberNo(), photoNo};

		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteLike(int photoNo, Member member) {
		String query = "delete from photo_like where member_no = ? and photo_no = ?";
		Object[] params = {member.getMemberNo(), photoNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int pushBookmark(int photoNo, String boardNo, Member member) {
		String query = "insert into book_mark values(photo_like_seq.nextval,?,?,?)";
		Object[] params = {boardNo, member.getMemberNo(), photoNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteBookmark(int photoNo, Member member) {
		String query = "delete from book_mark where photo_no = ? and member_no = ?";
		Object[] params = {photoNo, member.getMemberNo()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int getBookmarkTotalCount(Member member) {
		String query = "select count(*) from photo p1 where member_no = ? and photo_no in (select photo_no from book_mark where photo_no = p1.photo_no)";
		Object[] params = {member.getMemberNo()};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}

	public List selectBookmarkPhotoList(int start, int end, Member member) {
		String query = "select * from \r\n" + //
						"    (select rownum as rnum, p.*,\r\n" + //
						"        (select count(*) from photo_like where photo_no = p.photo_no and member_no = ?) as is_like,\r\n" + //
						"        (select count(*) from photo_like where photo_no = p.photo_no) as like_count,\r\n" + //
						"        (select count(*) from book_mark where photo_no = p.photo_no and member_no = ?) as is_bookmark\r\n" + //
						"    from \r\n" + //
						"        (select * \r\n" + //
						"            from photo p2 \r\n" + //
						"                where member_no = (select member_no from member where member_no = ?) and\r\n" + //
						"                photo_no = (select photo_no from book_mark where photo_no = p2.photo_no)\r\n" + //
						"                order by 1 desc)p)\r\n" + //
						"where rnum between ? and ?";
		Object[] params = {member.getMemberNo(), member.getMemberNo(), member.getMemberNo(), start, end};
		List list = jdbc.query(query, photoRowMapper, params);
		return list;
	}

	public List bookmarkPhotoSortPopular(int start, int end, Member member) {
		String query = "select * from\r\n" + //
						"    (select rownum as rnum, p.*,\r\n" + //
						"        (select count(*) from photo_like where photo_no = p.photo_no and member_no = ?) as is_like,\r\n" + //
						"        (select count(*) from photo_like where photo_no = p.photo_no) as like_count,\r\n" + //
						"        (select count(*) from book_mark where photo_no = p.photo_no and member_no = ?) as is_bookmark\r\n" + //
						"    from\r\n" + //
						"        (select *\r\n" + //
						"            from photo p2\r\n" + //
						"                where member_no = (select member_no from member where member_no = ?) and\r\n" + //
						"                photo_no = (select photo_no from book_mark where photo_no = p2.photo_no)\r\n" + //
						"                order by (select count(*) from photo_like where photo_no = p2.photo_no) desc)p)\r\n" + //
						"where rnum between ? and ?";
		Object[] params = {member.getMemberNo(), member.getMemberNo(), member.getMemberNo(), start, end};
		List list = jdbc.query(query, photoRowMapper, params);
		return list;
	}
}
