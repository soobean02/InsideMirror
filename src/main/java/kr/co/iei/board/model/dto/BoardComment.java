package kr.co.iei.board.model.dto;

import java.sql.Date;

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
	private Date BoardCommentDate;
	private int BoardCommentRef;
}
