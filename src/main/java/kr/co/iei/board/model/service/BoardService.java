package kr.co.iei.board.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.board.model.dao.BoardDao;
import kr.co.iei.board.model.dto.BoardListData;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	public BoardListData selectBoardList(int reqPage) {
		
		return null;
	}
}
