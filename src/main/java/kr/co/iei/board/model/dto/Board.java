package kr.co.iei.board.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {

	private int boardNo;
	private int memberNo;
	private String boardTitle;
	private String boardContent;
	private Date boardDate;
	private int readCount;

	// 글 작성한 멤버 닉네임
	private String boardWriter;

	private int boardLike;

}
