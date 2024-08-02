package kr.co.iei.report.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ReportRowMapper implements RowMapper<Report> {

	@Override
	public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
		Report r = new Report();
		r.setBoardNo(rs.getInt("board_no"));
		r.setMemberNo(rs.getInt("member_no"));
		r.setPhotoNo(rs.getInt("photo_no"));
		r.setReportcontent(rs.getString("report_content"));
		r.setReportDate(rs.getString("report_date"));
		r.setReportNo(rs.getInt("report_no"));
		return r;
	}

}
