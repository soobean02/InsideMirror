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
	}//사진첩 업로드

	public int getTotalCount(Member member) {
		String query = "select count(*) from photo where member_no = ?";
		Object[] params = {member.getMemberNo()};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}//사진첩 개수 조회

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
	}//사진첩 리스트

	public List PhotoSortPopular(int start, int end, Member member) {
		String query = "select * from\r\n" + //
						"    (select rownum as rnum, p.*,\r\n" + //
						"        (select count(*) from photo_like where photo_no = p.photo_no and member_no = ?) as is_like,\r\n" + //
						"        (select count(*) from photo_like where photo_no = p.photo_no) as like_count,\r\n" + //
						"        (select count(*) from book_mark where photo_no = p.photo_no and member_no = ?) as is_bookmark\r\n" + //
						"    from\r\n" + //
						"        (select *\r\n" + //
						"            from photo p2\r\n" + //
						"                where member_no = (select member_no from member where member_no = ?)\r\n" + //
						"                order by (select count(*) from photo_like where photo_no = p2.photo_no) desc)p)\r\n" + //
						"where rnum between ? and ?";
		Object[] params = {member.getMemberNo(), member.getMemberNo(), member.getMemberNo(), start, end};
		List list = jdbc.query(query, photoRowMapper, params);
		return list;
	}//인기순 정렬

	public int pushLike(int photoNo, Member member) {
		String query = "insert into photo_like values(photo_like_seq.nextval,?,?)";
		Object[] params = {member.getMemberNo(), photoNo};

		int result = jdbc.update(query, params);
		return result;
	}//좋아요

	public int deleteLike(int photoNo, Member member) {
		String query = "delete from photo_like where member_no = ? and photo_no = ?";
		Object[] params = {member.getMemberNo(), photoNo};
		int result = jdbc.update(query, params);
		return result;
	}//좋아요 취소

	public int pushBookmark(int photoNo, String boardNo, Member member) {
		String query = "insert into book_mark values(photo_like_seq.nextval,?,?,?)";
		Object[] params = {boardNo, member.getMemberNo(), photoNo};
		int result = jdbc.update(query, params);
		return result;
	}//즐겨찾기 추가

	public int deleteBookmark(int photoNo, Member member) {
		String query = "delete from book_mark where photo_no = ? and member_no = ?";
		Object[] params = {photoNo, member.getMemberNo()};
		int result = jdbc.update(query, params);
		return result;
	}//즐겨찾기 삭제

	public int removePhoto(int memberNo, int photoNo) {
		String query = "delete from photo where member_no = ? and photo_no = ?";
		Object[] params = {memberNo, photoNo};
		int result = jdbc.update(query, params);
		return result;
	}//사진첩 삭제

	public Photo selectOnePhoto(int photoNo) {
		String query = "select * from photo where photo_no = ?";
		Object[] params= {photoNo};
		List list = jdbc.query(query, photoRowMapper, params);
		if(list.isEmpty()){
			return null;
		}
		return (Photo)list.get(0);
	}//사진첩 하나 조회(파일 삭제를 위해)

	public int getBookmarkTotalCount(Member member) {
		String query = "select count(*) from book_mark where member_no = ? and photo_no is not null";
		Object[] params = {member.getMemberNo()};
		int totalCount = jdbc.queryForObject(query, Integer.class, params);
		return totalCount;
	}//사진첩 즐겨찾기 개수 조회

	public List selectBookmarkPhotoList(int start, int end, Member member) {
		String query = "select * from \r\n" + //
						"    (select rownum as rnum, p.*,\r\n" + //
						"        (select count(*) from photo_like where photo_no = p.photo_no and member_no = ?) as is_like,\r\n" + //
						"        (select count(*) from photo_like where photo_no = p.photo_no) as like_count,\r\n" + //
						"        (select count(*) from book_mark where photo_no = p.photo_no and member_no = ?) as is_bookmark\r\n" + //
						"    from \r\n" + //
						"        (select * \r\n" + //
						"            from photo p2 \r\n" + //
						"                where\r\n" + //
						"                photo_no in (select photo_no from book_mark where member_no = ?)\r\n" + //
						"                order by 1 desc)p)\r\n" + //
						"where rnum between ? and ?";
		Object[] params = {member.getMemberNo(), member.getMemberNo(), member.getMemberNo(), start, end};
		List list = jdbc.query(query, photoRowMapper, params);
		return list;
	}//사진첩 즐겨찾기 리스트 조회

	public List bookmarkPhotoSortPopular(int start, int end, Member member) {
		String query = "select * from\r\n" + //
						"    (select rownum as rnum, p.*,\r\n" + //
						"        (select count(*) from photo_like where photo_no = p.photo_no and member_no = ?) as is_like,\r\n" + //
						"        (select count(*) from photo_like where photo_no = p.photo_no) as like_count,\r\n" + //
						"        (select count(*) from book_mark where photo_no = p.photo_no and member_no = ?) as is_bookmark\r\n" + //
						"    from\r\n" + //
						"        (select *\r\n" + //
						"            from photo p2\r\n" + //
						"                where\r\n" + //
						"                photo_no in (select photo_no from book_mark where member_no = ?)\r\n" + //
						"                order by (select count(*) from photo_like where photo_no = p2.photo_no) desc)p)\r\n" + //
						"where rnum between ? and ?";
		Object[] params = {member.getMemberNo(), member.getMemberNo(), member.getMemberNo(),start, end};
		List list = jdbc.query(query, photoRowMapper, params);
		return list;
	}//사진첩 즐겨찾기 인기순 정렬 리스트 조회

	

	

	
}
