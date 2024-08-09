package kr.co.iei.member.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.dto.Member;
import kr.co.iei.member.model.dto.Title;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.utils.EmailSender;
import kr.co.iei.utils.FileUtils;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private EmailSender emailSender;
	
	@Value("${file.root}")
	private String root;
	@Autowired
	private FileUtils fileUtils;//파일 업로드용
	
	@GetMapping(value="/login")
	public String login() {
		return "member/login";
	}
	@PostMapping(value="/loginin")
	public String login(Member m, HttpSession session) {
		Member member = memberService.selectOneMember(m);
		if(member == null) {
			return "member/login";
		}else {
			if(member.getMemberLevel() == 2) {
				session.setAttribute("member", member);
				return "common/minihomepage";
			}else if(member.getMemberLevel() == 1) {
				session.setAttribute("member", member);
				return "redirect:/admin/adminHome";
			}else {
				session.setAttribute("member", member);
				return "redirect:/";
			}
		}
	}
	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	@GetMapping(value="/homelist")
	public String homelist(Model model) {
		List member = memberService.viewAllMember();
		model.addAttribute("member", member);
		return "common/homelist";
	}
	@GetMapping(value="/search")
	public String search(String findFriend, Model model) {
		System.out.println(findFriend);
		if(findFriend.equals("")) {
			System.out.println(findFriend);
			
			model.addAttribute("title","검색을 입력해주세요");
			model.addAttribute("icon", "error");
		}else {
			List memberList = memberService.findMember(findFriend);
			model.addAttribute("memberList", memberList);
			return "/common/searchlist";				
		}
		model.addAttribute("loc","/");
		return "common/msg";
		
		
			
	}
	@GetMapping(value="/minihomepage")
		public String minihomepage() {
			return "common/minihomepage";
		}
	@GetMapping(value="/joinFrm")
	public String joinFrm() {
		return "/member/joinFrm";
	}
	@PostMapping(value="/join")
	public String join(Member m,String memberId2, String[]phone) {
		String memberId = m.getMemberId()+"@"+memberId2;
		String memberPhone = phone[0]+"-"+phone[1]+"-"+phone[2];
		m.setMemberId(memberId);
		m.setMemberPhone(memberPhone);
		int result = memberService.insertMember(m);
		if(result>0) {
			return "redirect:/";
		}else {
			return "redirect:/member/joinFrm";
		}
	}
	@GetMapping(value="/joinFinal")
	public String joinFinal() {
		return "/member/joinFinal";
	}
	@GetMapping(value="/findPassword")
	public String findPassword() {
		return "/member/findPassword";
	}
	
	@PostMapping(value="/doubleCheckPassword")
	public String doubleCheckPassword(String id,Model model) {
		
		model.addAttribute("id", id);
		return "/member/doubleCheckPassword";
	}
	@PostMapping(value="/resetPassword")
	public String resetPassword(Member m) {
		int result = memberService.resetPassword(m);
		if(result>0) {
			m.setMemberId(m.getMemberId());
			m.setMemberPw(m.getMemberPw());
			return "redirect:/member/login";
		}else {
			return "redirect:/";			
		}
	}
	@GetMapping(value="/memberPage")
	public String memberPage(@SessionAttribute(required=false) Member member,Model model) {
		Member m = member;
		List getTitle = memberService.getTitle(member);
		model.addAttribute("member",m);
		model.addAttribute("getTitle",getTitle);
		return "member/memberPage";
	}
	
	@ResponseBody
	@GetMapping(value="/ajaxCheckNickname")
	public int ajaxCheckNickname(String memberNickName) {
		Member member = memberService.selectOneMember(memberNickName);
		if(member == null) {
			return 0;
		}else {
			return 1;
		}
	}
	
	@ResponseBody
	@PostMapping(value="/sendCode")
	public String sendCode(String receiver) {
		String emailTitle = "InsideMirror 인증메일 입니다.";
		System.out.println(receiver);
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<6;i++) {
			int flag = r.nextInt(3);
			if(flag == 0) {
				int randomCode = r.nextInt(10); //0~9 : r.nextInt(10); 숫자
				sb.append(randomCode);
			}else if(flag == 1) {
				char randomCode = (char)(r.nextInt(26)+65); //A~Z : r.nextInt(26)+65; 대문자 
				sb.append(randomCode);
			}else if(flag == 2) {
				char randomCode = (char)(r.nextInt(26)+97); //0~9 : r.nextInt(26)+97; 소문자
				sb.append(randomCode);
			}
		}
		String emailContent = "<h1>InsideMirror 인증코드를 확인하여 주세요.</h1>"
							+"<h3>인증번호는 [<span style='color:red; font-size:bold;'>"
							+sb.toString()
							+"</span>]입니다.</h3>";
		emailSender.sendMail(emailTitle, receiver, emailContent);
		return sb.toString();
	}
}
