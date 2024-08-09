package kr.co.iei.member.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.co.iei.board.model.dto.Board;
import kr.co.iei.photo.model.dto.Photo;
@Component
public class TitleRowMapper implements RowMapper<Title>{

	@Override
	public Title mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Photo p = new Photo();
		p.setMemberNo(rs.getInt("member_no"));
		p.setPhotoContent(rs.getString("photo_content"));
		p.setPhotoDate(rs.getDate("photo_Date"));
		p.setPhotoNo(rs.getInt("photo_no"));
		p.setPhotoTitle(rs.getString("photo_title"));
		
		Board b = new Board();
		b.setBoardContent(rs.getString("board_content"));
		b.setBoardDate(rs.getString("board_date"));
		b.setBoardTitle(rs.getString("board_title"));
		b.setBoardNo(rs.getInt("board_no"));
		b.setMemberNo(rs.getInt("member_no"));
				
		Title title = new Title();
//		title.setBoard(b);
//		title.setPhoto(p);
		//useless class 임
		return title;
	}
}
