package kr.co.iei.customer.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.customer.dto.Customer;
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
	
	public List selectCustomerList(int start, int end) {
		String query = "select * from (select rownum as rnum, n.* from (select * from customer order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query,customerRowMapper, params);
		return list;
	}

	public int selectCustomerTotalCount() {
		String query = "select count(*) from customer";
		int totalCount = jdbc.queryForObject(query, Integer.class); // 이 쿼리문 실행해서 바로 Inter.class로 바로 꺼내줘
		return totalCount;
	}

}
