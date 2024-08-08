package kr.co.iei.customer.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.customer.dto.Customer;
import kr.co.iei.customer.dto.CustomerListRowMapper;
import kr.co.iei.customer.dto.CustomerRowMapper;
import kr.co.iei.member.model.dto.Member;

@Repository
public class CustomerDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private CustomerListRowMapper customerListRowMapper; // 문의 분류(고객 분류, 고객 분류 이름(환불/기타))
	@Autowired
	private CustomerRowMapper customerRowMapper; // 문의 상세(문의 번호, 멤버 번호(참조), 고객 분류, 문의 제목, 상태(승인중 default),문의 작성일, 문의 내용)
	
	public List selectCustomerList(Member member, int start, int end) {
		String query = "select * from (select rownum as rnum, n.* from (select * from customer where member_no=? order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {member.getMemberNo(),start,end};
		List list = jdbc.query(query,customerRowMapper, params);
		return list;
	}

	public int selectCustomerTotalCount(Member member) {
		String query = "select count(*) from customer where member_no=?";
		Object[] params = {member.getMemberNo()};
		int totalCount = jdbc.queryForObject(query, Integer.class,params); // 이 쿼리문 실행해서 바로 Inter.class로 바로 꺼내줘
		return totalCount;
	}

	public int insertCustomerInq(Customer c, Member member) {
		String query = "insert into customer values(customer_seq.nextval, ?, ?, ?, default, TO_CHAR(SYSDATE,'yyyy-mm-dd'),?)";
		Object[] params = {member.getMemberNo(), c.getCustomerList(), c.getInqTitle(),c.getInqContent()};
		int result = jdbc.update(query, params);
		return result;
	}


	//customer status 상태 '완료' 업데이트 로직
	public int updateStatus(Customer c) {
		String query = "update customer set status='완료' where inq_no=?";
		Object[] params = {c.getInqNo()};
		int result = jdbc.update(query, params);
		return result;
	}//updateStatus

	public Customer selectCustomerContent(int i) {
		String query = "select * from customer where inq_no=?";
		Object[] params = {i};
		List list = jdbc.query(query, customerRowMapper, params);
		if(list.isEmpty()) {
			return null;
		}
		return (Customer)list.get(0);
	}

	public List selectFiveReport() {
		String query = "select * from (select rownum as rnum, n.* from (select * from customer order by 1 desc)n) where rnum between 1 and 5";
		List fiveReportList = jdbc.query(query, customerRowMapper);
		return fiveReportList;
	}//selectFiveReport

	public List selectAdminCustomerList(int start, int end) {
		String query = "select * from (select rownum as rnum, n.* from (select * from customer order by 1 desc)n) where rnum between ? and ?";
		Object[] params = {start,end};
		List list = jdbc.query(query,customerRowMapper, params);
		return list;
	}//selectAdminCustomerLis

	public int selectAdminCustomerTotalCount() {
		String query = "select count(*) from customer";
		int totalCount = jdbc.queryForObject(query, Integer.class); // 이 쿼리문 실행해서 바로 Inter.class로 바로 꺼내줘
		return totalCount;
	}//selectAdminCustomerTotalCount


}
