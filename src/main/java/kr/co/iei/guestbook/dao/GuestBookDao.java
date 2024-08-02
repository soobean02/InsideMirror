package kr.co.iei.guestbook.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.co.iei.guestbook.dto.GuestBookRowMapper;
@Repository
public class GuestBookDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private GuestBookRowMapper guestBookRowMapper;
}
