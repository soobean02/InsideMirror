package kr.co.iei.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellProduct {
	private int productNo;
	private int productListNo;
	private int productPrice;
	private String productName;
	private String productImg;
	private String productUrl;
	private String productPath;
}
