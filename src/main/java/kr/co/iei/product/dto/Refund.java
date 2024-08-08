package kr.co.iei.product.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Refund {
	private int productNo;
	private String productName;
	private String memberName;
	private String memberPhone;
	private int productPrice;
	private String refundStatus;
	private Date refundDate;
}
