package kr.co.iei.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.board.model.dao.BoardDao;
import kr.co.iei.board.model.dto.Board;
import kr.co.iei.board.model.dto.BoardListData;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	public BoardListData selectBoardList(int reqPage) {
		int numPerPage = 10;

		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;

		List list = boardDao.selectBoardList(start, end);

		int totalCount = boardDao.selectBoardTotalCount();

		int totalPage = 0;
		if(totalCount % numPerPage == 0){
			totalPage = totalCount/numPerPage;
		}
		else{
			totalPage = totalCount/numPerPage + 1;
		}

		int pageNaviSize = 5;
		int pageNo = ((reqPage -1)/pageNaviSize) * pageNaviSize + 1;

		String pageNavi = "<ul class='page-wrap'>";

		if(pageNo != 1){
			pageNavi += "<li><a class='page-index' href='/board/list?reqPage="+(pageNo - 1)+"'><span> < </span></a></li>";
		}

		for(int i = 0; i < pageNaviSize; i++){
			pageNavi += "<li>";
			if(pageNo == reqPage){
				pageNavi += "<a class='page-index active-page' href='/board/list?reqPage="+pageNo+"'>";
			}
			else{
				pageNavi += "<a class='page-index' href='/board/list?reqPage="+pageNo+"'>";
			}
			
			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			
			if(pageNo > totalPage) break;
		}//for

		if(pageNo <= totalPage){
			pageNavi += "<li>";
			pageNavi += "<a class='page-index' href='/board/list?reqPage="+pageNo+"'>";
			pageNavi += "<span> > </span>";
			pageNavi += "</a></li>";
		}

		pageNavi += "</ul>";

		BoardListData bld = new BoardListData(list, pageNavi);

		return bld;
	}


	public Board selectOneBoard(int boardNo) {
		
		return null;
	}
}
