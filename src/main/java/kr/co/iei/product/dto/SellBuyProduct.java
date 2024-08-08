package kr.co.iei.product.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellBuyProduct {
	private BuyProduct buyProduct;
	private SellProduct sellProduct;
}
