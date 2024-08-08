package kr.co.iei.board.model.dto;

import lombok.Data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardListData {

	private List list;
	private String pageNavi;

}
