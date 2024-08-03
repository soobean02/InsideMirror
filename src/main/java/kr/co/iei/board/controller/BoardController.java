package kr.co.iei.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.board.model.dto.Board;
import kr.co.iei.board.model.dto.BoardListData;
import kr.co.iei.board.model.service.BoardService;
import kr.co.iei.utils.FileUtils;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Value("${file.root}")
	private String root;

	@Autowired
	private FileUtils fileUtils;

	@GetMapping(value = "/list")
	public String boardList(int reqPage, Model model) {
		BoardListData bld = boardService.selectBoardList(reqPage);
		model.addAttribute("list", bld.getList());
		model.addAttribute("pageNavi", bld.getPageNavi());
		return "/board/boardList";
	}//자유게시판 보기 조회는 10개씩

	@GetMapping(value="/writeFrm")
	public String writeFrm(){
		return "/board/write";
	}//글 작성

	@GetMapping(value="/view")
	public String view(int boardNo){
		Board board = boardService.selectOneBoard(boardNo);

		return "/board/view";
	}

}
