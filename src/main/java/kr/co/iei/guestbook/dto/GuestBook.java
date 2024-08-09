package kr.co.iei.guestbook.dto;

import java.sql.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuestBook {
	private int guestCommentNo;
	private int guestWriterNo;
	private String guestCommentContent;
	private Date guestCommentDate;
	private int memberNo;
	private int guestBookType;
}
