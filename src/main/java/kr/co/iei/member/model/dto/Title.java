package kr.co.iei.member.model.dto;

import kr.co.iei.board.model.dto.Board;
import kr.co.iei.photo.model.dto.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Title {
	private Photo photo;
	private Board board;
}
