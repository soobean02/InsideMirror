package kr.co.iei.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardComment {

	private int BoardCommentNo;
	private int MemberNo;
	private int BoardNo;
	private String BoardCommentContent;
	private String BoardCommentDate;
	private int BoardCommentRef;
}
