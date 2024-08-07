package kr.co.iei.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellProduct {
	private int productNo;			// 상품 번호
	private int productListNo;		// 상품 리스트 번호 (배경: 1, 커서: 2, 폰트: 3)
	private int productPrice;		// 상품 도토리 수
	private String productName;		// 상품 이름
	private String productImg;		// 상품 이미지
	private String productUrl;		// 상품 url (ex. background-color: skyblue;에서 'skyblue'만)
	private String productPath;		// 상품 이미지 경로
}
