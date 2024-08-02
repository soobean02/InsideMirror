package kr.co.iei.board.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
	
	private int BoardNo;
	private int MemberNo;
	private String BoardTitle;
	private String BoardContent;
	private String BoardDate;
	private int ReadCount;
}
