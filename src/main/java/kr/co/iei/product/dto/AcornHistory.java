package kr.co.iei.product.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AcornHistory {
	private int acornsNo; // 도토리 번호
	private int memberNo; // 회원번호(참조)
	private int acornsPrice; // 도토리 가격
	private Date buyDate; // 도토리 구매일
}
