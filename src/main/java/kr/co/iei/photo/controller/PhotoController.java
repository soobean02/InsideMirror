package kr.co.iei.photo.controller;

import java.util.List;

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

import kr.co.iei.member.model.dto.Member;
import kr.co.iei.photo.model.dto.Photo;
import kr.co.iei.photo.model.service.PhotoService;
import kr.co.iei.utils.FileUtils;

@Controller
@RequestMapping(value = "/photo")
public class PhotoController {
	@Autowired
	private PhotoService photoService;
	@Value("${file.root}")
	private String root;
	@Autowired
	private FileUtils fileUtils;//파일 업로드용

	@GetMapping(value="/list")
	public String list(Model model){
		int totalCount = photoService.getTotalCount();
		model.addAttribute("totalCount", totalCount);
		return "/photo/photoList";
	}

	@GetMapping(value="/writeFrm")
	public String writeFrm(){
		return "/photo/writeFrm";
	}

	@PostMapping(value = "/write")
	public String write(Photo p, MultipartFile upfile, Model model){
		if(p.getPhotoTitle().equals("")){
			p.setPhotoTitle("untitle");
		}
		String savepath = root+"/photo/";
		String filepath = fileUtils.upload(savepath, upfile);
		p.setPhotoContent(filepath);
		int result = photoService.insertPhoto(p);
		if(result > 0){
			model.addAttribute("title", "사진첩");
			model.addAttribute("msg", "사진첩 업로드완료!");
			model.addAttribute("icon", "success");
		}
		else{
			model.addAttribute("title", "사진첩");
			model.addAttribute("msg", "사진첩 업로드 실패");
			model.addAttribute("icon", "error");
		}
		model.addAttribute("loc", "/photo/list");
		return "common/msg";
	}//사진첩 업로드


	@ResponseBody
	@GetMapping(value="/more")
	public List photoMore(int start, int amount, @SessionAttribute(required = false) Member member){
		List photoList = photoService.selectPhotoList(start, amount, member);
		return photoList;
	}

	@ResponseBody
	@PostMapping(value="/like")
	public int pushLike(int isLike, int photoNo, @SessionAttribute(required = false) Member member, Model model){
		if(member == null){
			return -1;
		}

		int result = photoService.pushLike(isLike, photoNo, member);
		return result;
	}

	@ResponseBody
	@PostMapping(value="/bookmark")
	public int pushBookmark(int isBookmark, int photoNo, @SessionAttribute(required = false) Member member, Model model) {
		if(member == null){
			return -1;
		}
		System.out.println(isBookmark);
		int result = photoService.pushBookmark(isBookmark, photoNo, member);
		return result;
	}
	
	
	@GetMapping(value="/bookmark/list")
	public String bookmark(@SessionAttribute(required=false) Member member, Model model){
		int totalCount = photoService.getTotalCount();
		model.addAttribute("totalCount", totalCount);
		return "/photo/bookmarkPhoto";
	}
	
	@ResponseBody
	@GetMapping(value="/bookmark/more")
	public List bookmarkMore(int start, int amount, @SessionAttribute(required = false) Member member){
		List photoList = photoService.selectPhotoList(start, amount, member);
		return photoList;
	}
	
	
}
