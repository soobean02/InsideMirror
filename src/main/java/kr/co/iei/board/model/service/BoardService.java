package kr.co.iei.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.board.model.dao.BoardDao;
import kr.co.iei.board.model.dto.Board;
import kr.co.iei.board.model.dto.BoardComment;
import kr.co.iei.board.model.dto.BoardFile;
import kr.co.iei.board.model.dto.BoardListData;
import kr.co.iei.member.model.dto.Member;

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
	}//리스트보기

	@Transactional
	public Board selectOneBoard(int boardNo, String check, int memberNo) {
		Board board = boardDao.selectOneBoard(boardNo, memberNo);
		if(board != null){

			if(check == null){
				int result = boardDao.updateReadCount(boardNo);
			}
			//게시물 조회수 증가
			
			//댓글 조회
			List<BoardComment> commentList = boardDao.selectComment(boardNo);
			board.setCommentList(commentList);
			//답글 조회
			List reCommentList = boardDao.selectReCommentList(boardNo);
			board.setReCommentList(reCommentList);
			//답글 개수 조회
			
		}

		return board;
	}//상세보기

	public Board getOneBoard(int boardNo) {
		Board board = boardDao.getOneBoard(boardNo);
		return board;
	}//수정하기 위해서 데이터를 가져오는 작업 (게시글 수정)

	@Transactional
	public int insertBoard(Board board, List<BoardFile> fileList) {
		int result = boardDao.insertBoard(board);

		if(result > 0){
			int boardNo = boardDao.selectBoardNo();

			for(BoardFile boardFile : fileList){
				boardFile.setBoardNo(boardNo);
				result += boardDao.insertBoardFile(boardFile);
			}
		}
		return result;
	}//게시판 insert

	@Transactional
	public int editBoard(Board board) {
		int result = boardDao.editBoard(board);
		return result;
	}//게시글 수정

	@Transactional
	public int deleteBoard(int boardNo) {
		int result = boardDao.deleteBoard(boardNo);
		return result;
	}//게시글 삭제

	@Transactional
	public int pushLike(int isLike, int boardNo, Member member) {
		int result = 0;
		if(isLike == 0){
			//좋아요 누른 경우
			result = boardDao.insertLike(boardNo, member);
		}
		else{
			//좋아요 취소인경우
			result = boardDao.deleteLike(boardNo, member);
		}
		return result;
	}//게시글 좋아요

	@Transactional
	public int pushBookMark(int isBookMark, int boardNo, Member member) {
		int result = 0;
		String photoNo = null;
		if(isBookMark == 0){
			//북마크 한 경우
			result = boardDao.insertBookMark(boardNo, photoNo, member);
		}
		else{
			//북마크 취소한경우
			result = boardDao.deleteBookMark(boardNo, member);
		}
		return result;
	}//게시글 북마크(즐겨찾기)

	@Transactional
	public int insertBoardComment(BoardComment comment) {
		int result = boardDao.insertBoardComment(comment);
		return result;
	}//댓글 대댓글 insert

	@Transactional
	public int updateBoardComment(String commentContent, String boardCommentNo, String boardNo) {
		int result = boardDao.updateBoardComment(commentContent, boardCommentNo, boardNo);
		return result;
	}//댓글 대댓 수정

	@Transactional
	public int removeBoardComment(String boardCommentNo) {
		int result = boardDao.removeBoardComment(boardCommentNo);
		return result;
	}//댓글 대댓 삭제

	public BoardComment selectOneComment(BoardComment comment) {
		BoardComment oneComment = boardDao.selectOneComment(comment);
		return oneComment;
	}//댓글 하나 조회

	public BoardListData selectSearchList(String type, String keyword, int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List list = null;
		int totalCount = 0;
		if(type.equals("title")){
			list = boardDao.selectBoardSearchTitleList(keyword,start,end);
			totalCount = boardDao.selectBoardSearchTitleTotalCount(keyword);
		}
		else if(type.equals("writer")){
			list = boardDao.selectBoardSearchWriterList(keyword,start,end);
			totalCount = boardDao.selectBoardSearchWriterTotalCount(keyword);
		}

		int totalPage = 0;
		if(totalCount % numPerPage == 0){
			totalPage = totalCount / numPerPage;
		}
		else{
			totalPage = totalCount / numPerPage + 1;
		}
		int pageNaviSize = 5;

		int pageNo = ((reqPage - 1)/pageNaviSize) * pageNaviSize + 1;
		String pageNavi = "<ul class='page-wrap'>";

		if(pageNo != 1){
			pageNavi += "<li><a class='page-index' href='/board/search?type="+type+"&keyword="+keyword+"&reqPage="+(pageNo - 1)+"'><span> < </span></a></li>";
		}

		for(int i = 0; i < pageNaviSize; i++){
			pageNavi += "<li>";
			if(pageNo == reqPage){
				pageNavi += "<a class='page-index active-page' href='/board/search?type="+type+"&keyword="+keyword+"&reqPage="+pageNo+"'>";
			}
			else{
				pageNavi += "<a class='page-index' href='/board/search?type="+type+"&keyword="+keyword+"&reqPage="+pageNo+"'>";
			}

			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			if(pageNo > totalPage) break;
		}//for

		if(pageNo <= totalPage){
			pageNavi += "<li><a class='page-index' href='/board/search?type="+type+"&keyword="+keyword+"&reqPage="+pageNo+"'><span> > </span></a></li>";
		}
		pageNavi += "</ul>";

		BoardListData bld = new BoardListData(list, pageNavi);

		return bld;
	}//게시글 검색

	public BoardListData selectOrderList(String type, String keyword, int reqPage, String orderDate, String orderFriend, Member member) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List list = null;
		int totalCount = 0;

		if(type.equals("title")){
			//제목으로 검색한거면
			//그중에서
			if(orderDate.equals("newest") && orderFriend.equals("all")){
				//최신글 / 전체
				list = boardDao.titleDateNewFriendAll(keyword, start, end);
			}
			else if(orderDate.equals("newest") && orderFriend.equals("friend")){
				//최신글 / 일촌only
				list = boardDao.titleDateNewFriendFriend(keyword, start, end, member);
			}
			else if(orderDate.equals("popular") && orderFriend.equals("all")){
				//인기글 / 전체
				list = boardDao.titleDatePopularFriendAll(keyword, start, end);
			}
			else if(orderDate.equals("popular") && orderFriend.equals("friend")){
				//인기글 / 일촌only
				list = boardDao.titleDatePopularFriendFriend(keyword, start, end, member);
			}

			totalCount = boardDao.selectBoardSearchTitleTotalCount(keyword);
			//어차피 총 개수는 똑같기 때문에 중첩 if문 나와서 한 번에 전체 개수 조회
		}
		else if(type.equals("writer")){
			//작성자로 검색한거면
			//그중에서
			if(orderDate.equals("newest") && orderFriend.equals("all")){
				//최신글 / 전체
				list = boardDao.writerDateNewFriendAll(keyword, start, end);
			}
			else if(orderDate.equals("newest") && orderFriend.equals("friend")){
				//최신글 / 일촌only
				list = boardDao.writerDateNewFriendFriend(keyword, start, end, member);
			}
			else if(orderDate.equals("popular") && orderFriend.equals("all")){
				//인기글 / 전체
				list = boardDao.writerDatePopularFriendAll(keyword, start, end);
			}
			else if(orderDate.equals("popular") && orderFriend.equals("friend")){
				//인기글 / 일촌only
				list = boardDao.writerDatePopularFriendFriend(keyword, start, end, member);
			}
			totalCount = boardDao.selectBoardSearchWriterTotalCount(keyword);
			//어차피 총 개수는 똑같기 때문에 중첩 if문 나와서 한 번에 전체 개수 조회
		}

		int totalPage = 0;
		if(totalCount % numPerPage == 0){
			totalPage = totalCount / numPerPage;
		}
		else{
			totalPage = totalCount / numPerPage + 1;
		}
		int pageNaviSize = 5;

		int pageNo = ((reqPage - 1)/pageNaviSize) * pageNaviSize + 1;
		String pageNavi = "<ul class='page-wrap'>";


		



		if(pageNo != 1){
			pageNavi += "<li><a class='page-index' href='/board/search?type="+type+"&keyword="+keyword+"&reqPage="+(pageNo - 1)+"'><span> < </span></a></li>";
		}

		for(int i = 0; i < pageNaviSize; i++){
			pageNavi += "<li>";
			if(pageNo == reqPage){
				pageNavi += "<a class='page-index active-page' href='/board/search?type="+type+"&keyword="+keyword+"&reqPage="+pageNo+"'>";
			}
			else{
				pageNavi += "<a class='page-index' href='/board/search?type="+type+"&keyword="+keyword+"&reqPage="+pageNo+"'>";
			}

			pageNavi += pageNo;
			pageNavi += "</a></li>";
			pageNo++;
			if(pageNo > totalPage) break;
		}//for

		if(pageNo <= totalPage){
			pageNavi += "<li><a class='page-index' href='/board/search?type="+type+"&keyword="+keyword+"&reqPage="+pageNo+"'><span> > </span></a></li>";
		}
		pageNavi += "</ul>";

		BoardListData bld = new BoardListData(list, pageNavi);

		return bld;






	}

	

	

	

	

	
}
