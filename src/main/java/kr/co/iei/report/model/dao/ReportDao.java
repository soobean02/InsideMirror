package kr.co.iei.report.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.report.model.dto.ReportRowMapper;

@Repository
public class ReportDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private ReportRowMapper reportRowMapper;
}
