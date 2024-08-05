package kr.co.iei.customer.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
	private int inqNo;	// 문의 번호
	private int memberNo;	// 멤버 번호
	private int customerList;	// 고객 분류
	private String inqTitle;	// 문의 제목
	private String status;	// 상태 (default로 '승인중'되어있음)
	private Date inqDate;	// 문의 작성일
	private String inqContent; //문의 내용
}
