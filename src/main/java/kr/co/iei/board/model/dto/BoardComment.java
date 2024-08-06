package kr.co.iei.board.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardComment {

	private int boardCommentNo;
	private int memberNo;
	private int boardNo;
	private String boardCommentContent;
	private String boardCommentDate;
	private int boardEdit;

	private int boardCommentRef;

	//답글 개수
	private int reCommentCount;
}
