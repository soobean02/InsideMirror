package kr.co.iei.product.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BuyProduct {
	private int buyNo; // 구매한 상품 번호
	private int productNO; // 판매 상품 번호(참조)
	private int memberNo; // 멤버 번호(참조)
	private String buyDate; // 구매 날짜
	
	/*admin에서 사용하는 거*/
	private String refundStatus; // 환불 상태 (환불/대기/사용) - admin에서 쓰는거
	private String refundDate; // 환불 날짜 - admin에서 사용하는거
}
