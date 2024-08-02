package kr.co.iei.customer.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerList {
	private int customerList; // 고객 분류
	private String customerListName; // 고객 분류 이름(환불/기타)
}
