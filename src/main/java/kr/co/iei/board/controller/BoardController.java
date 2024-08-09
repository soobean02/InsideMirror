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
		return "/board/writeFrm";
		//파일 올릴 수 있게 해야함
	}//글 작성

	@GetMapping(value="/view")
	public String view(int boardNo, String check, Model model, @SessionAttribute(required = false) Member member){
		int memberNo = 0;
		if(member != null){
			memberNo = member.getMemberNo();
		}

		Board board = boardService.selectOneBoard(boardNo, check, memberNo);
		if(board == null){
			return "redirect:/board/list?reqPage=1";
		}
		model.addAttribute("board", board);
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
			model.addAttribute("title", "작성");
			model.addAttribute("msg", "게시글을 작성했습니다!");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/board/list?reqPage=1");
		}

		return "common/msg";
	}//게시글 작성

	@GetMapping(value="/editFrm")
	public String editBoardFrm(int boardNo, Model model){
		Board board = boardService.getOneBoard(boardNo);
		board.setBoardNo(boardNo);
		model.addAttribute("board", board);
		return "/board/editFrm";
	}//게시글 수정 양식으로

	@PostMapping(value="/edit")
	public String editBoard(Board board, Model model){
		
		int result = boardService.editBoard(board);
		if(result > 0){
			//수정 성공시
			model.addAttribute("title", "수정");
			model.addAttribute("msg", "게시글을 수정했습니다!");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/board/view?boardNo="+board.getBoardNo()+"&check=1");
		}
		else{
			model.addAttribute("title", "수정");
			model.addAttribute("msg", "수정실패 다시 시도해주세요.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/board/view?boardNo="+board.getBoardNo()+"&check=1");
		}
		return "common/msg";
	}//게시글 수정

	@GetMapping(value="/delete")
	public String deleteBoard(int boardNo,Model model){
		int result = boardService.deleteBoard(boardNo);
		if(result > 0){

			model.addAttribute("title", "삭제");
			model.addAttribute("msg", "게시글을 삭제했습니다");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/board/list?reqPage=1");
		}
		else{
			model.addAttribute("title", "삭제");
			model.addAttribute("msg", "삭제에 실패했습니다");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/board/list?reqPage=1");
		}
		return "common/msg";
	}//게시글 삭제

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

	//게시글 좋아요 / 북마크

	@ResponseBody
	@PostMapping(value="/like")
	public int pushLike(int isLike, int boardNo, @SessionAttribute(required = false) Member member, Model model){
		if(member == null){
			return -1;
		}
		int result = boardService.pushLike(isLike, boardNo, member);
		return result;
	}//좋아요버튼 누르면

	@ResponseBody
	@PostMapping(value = "/bookmark")
	public int pushBookMark(int isBookMark, int boardNo, @SessionAttribute(required = false) Member member, Model model){
		if(member == null){
			return -1;
		}
		int result = boardService.pushBookMark(isBookMark, boardNo, member);
		return result;
	}

	@ResponseBody
	@PostMapping(value="/comment")
	public Map<String, Object> comment(BoardComment comment, Model model, 	@SessionAttribute Member member){
		int result = boardService.insertBoardComment(comment);
		BoardComment oneComment = boardService.selectOneComment(comment);
		Board board = new Board();
		board.setBoardNo(comment.getBoardNo());
		if(result > 0){
			//댓글 작성 성공시
			Map<String, Object> info = new HashMap<>();
			info.put("comment", oneComment);
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
		int result = boardService.removeBoardComment(boardCommentNo);
		return result;
	}//댓글 삭제

	////게시글 검색
	@GetMapping(value = "/search")
	public String search(String type, String keyword, int reqPage, Model model){
		if(keyword.equals("")) return "redirect:/board/list?reqPage=1";

		BoardListData bld = boardService.selectSearchList(type,keyword,reqPage);
		model.addAttribute("list", bld.getList());
		model.addAttribute("pageNavi", bld.getPageNavi());
		model.addAttribute("keyword", keyword);
		model.addAttribute("type", type);
		return "board/boardList";
	}//게시글 검색

	//게시글 정렬
	@GetMapping(value="/order")
	public String order(String type, String keyword, int reqPage, String orderDate, String orderFriend, Model model, @SessionAttribute(required = false) Member member){
		BoardListData bld = boardService.selectOrderList(type, keyword, reqPage, orderDate, orderFriend, member);
		model.addAttribute("list", bld.getList());
		model.addAttribute("pageNavi", bld.getPageNavi());
		model.addAttribute("keyword", keyword);
		model.addAttribute("type", type);
		model.addAttribute("orderDate", orderDate);
		model.addAttribute("orderFriend", orderFriend);
		return "/board/boardList";
	}

}
