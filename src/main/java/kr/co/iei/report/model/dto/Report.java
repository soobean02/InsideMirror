package kr.co.iei.report.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Report {
	private int reportNo;			//신고 고유 번호
	private String reportcontent;	//신고 사유
	private int memberNo;			//신고 당한 작성자 멤버 번호
	private int boardNo;			//신고 당한 게시글 번호
	private int photoNo;			//신고 당한 사진첩 번호
	private String reportDate;		//신고 일자
}
