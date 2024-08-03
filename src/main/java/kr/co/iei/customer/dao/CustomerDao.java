package kr.co.iei.customer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.customer.dto.CustomerListRowMapper;
import kr.co.iei.customer.dto.CustomerRowMapper;

@Repository
public class CustomerDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private CustomerListRowMapper customerListRowMapper; // 문의 분류(고객 분류, 고객 분류 이름(환불/기타))
	@Autowired
	private CustomerRowMapper customerRowMapper; // 문의 상세(문의 번호, 멤버 번호(참조), 고객 분류, 문의 제목, 상태(승인중 default),문의 작성일)

}
