package kr.co.iei.photo.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Photo {
	private int photoNo;
	private int memberNo;
	private String photoTitle;
	private String photoContent;
	private Date photoDate;

	private String photoWriter;
}
