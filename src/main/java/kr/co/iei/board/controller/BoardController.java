package kr.co.iei.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.board.model.dto.Board;
import kr.co.iei.board.model.dto.BoardComment;
import kr.co.iei.board.model.dto.BoardFile;
import kr.co.iei.board.model.dto.BoardListData;
import kr.co.iei.board.model.service.BoardService;
import kr.co.iei.member.model.dto.Member;
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
		System.out.println("writewrite");
		return "/board/writeFrm";
		//파일 올릴 수 있게 해야함
	}//글 작성

	@GetMapping(value="/view")
	public String view(int boardNo, Model model, @SessionAttribute(required = false) Member member){
		// int memberNo = 0;
		// if(member != null){
		// 	memberNo = member.getMemberNo();
		// }
		Board board = boardService.selectOneBoard(boardNo);
		if(board == null){
			return "redirect:/board/list?reqPage=1";
		}
		model.addAttribute("board", board);
		System.out.println(member);
		return "/board/view";
	}//게시글 상세보기

	@PostMapping(value="/write")
	public String write(Board board, Model model){
		List<BoardFile> fileList = new ArrayList<BoardFile>();
		// if(!upfile[0].isEmpty()){
		// 	String savepath = root+"/board/";
		// 	for(MultipartFile file : upfile){
		// 		String filepath = fileUtils.upload(savepath, file);
		// 		BoardFile boardFile = new BoardFile();
		// 		boardFile.setFilepath(filepath);
		// 		fileList.add(boardFile);
		// 	}
		// }
		int result = boardService.insertBoard(board, fileList);
		if(result > 0){
			//작성 성공로직
		}

		return "redirect:/board/list?reqPage=1";
	}//게시글 작성

	@GetMapping(value="/delete")
	public String deleteBoard(int boardNo,Model model){
		int result = boardService.deleteBoard(boardNo);
		model.addAttribute("title", "삭제");
		model.addAttribute("msg", "게시글을 삭제했습니다");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/board/list?reqPage=1");
		return "common/msg";
	}

	@ResponseBody
	@PostMapping(value="/editorImage",produces = "plain/text;charset=utf-8")
	public String editorImage(MultipartFile upfile, @SessionAttribute(required = false) Member member){
		if(member == null){
			return "redirect:/board/list?reqPage=1";
		}
		String savepath = root+"/board/";
		String filepath = fileUtils.upload(savepath, upfile);
		return "/board/"+filepath;
	}//파일업로드(summernote로 글 작성할때 파일 업로드하면 바로보일 수 있게)


	@ResponseBody
	@PostMapping(value="/comment")
	public Map<String, Object> comment(BoardComment comment, Model model, @SessionAttribute Member member){
		int result = boardService.insertBoardComment(comment);
		BoardComment oneComment = boardService.selectOneComment(comment);
		Board board = new Board();
		board.setBoardNo(comment.getBoardNo());
		if(result > 0){
			//댓글 작성 성공시
			Map<String, Object> info = new HashMap<>();
			info.put("session", member);
			info.put("comment", oneComment);
			info.put("board", comment);
			return info;
		}
		else{
			return null;
		}
	}//댓글작성

	@ResponseBody
	@PostMapping(value="/updateComment")
	public int updateComment(String commentContent, String boardCommentNo, String boardNo){
		int result = boardService.updateBoardComment(commentContent, boardCommentNo, boardNo);
		return result;
	}//댓글 수정

	@ResponseBody
	@PostMapping(value = "/removeComment")
	public int removeComment(String boardCommentNo){
		System.out.println("controller");
		int result = boardService.removeBoardComment(boardCommentNo);
		return result;
	}

}
