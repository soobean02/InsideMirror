package kr.co.iei.board.model.dto;

import java.util.List;

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
	private String boardDate;
	private int readCount;

	// 글 작성한 멤버 닉네임
	private String boardWriter;

	//로그인한 회원이 그 게시글에 좋아요를 눌렀는지 여부
	private int isLike;
	//로그인한 회원이 그 게시글에 즐겨찾기를 눌렀는지 여부
	private int isBookMark;

	private int boardLike;

	private List<BoardComment> commentList;

	private List<BoardComment> reCommentList;

}
