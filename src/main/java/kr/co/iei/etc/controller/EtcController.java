package kr.co.iei.etc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/etc")
public class EtcController {
	@GetMapping(value="/companyInfo")
	public String companyInfo() {
		return"/etc/companyInfo";
	}
	@GetMapping(value="proposalGuide")
	public String proposalGuide() {
		return "/etc/proposalGuide";
	}
	@GetMapping(value="/policy")
	public String policy() {
		return "/etc/policy";
	}
	@GetMapping(value="privacy")
	public String privacy() {
		return "etc/privacy";
	}
	@GetMapping(value="youthPolicy")
	public String youthPolicy() {
		return "etc/youthPolicy";
	}
	@GetMapping(value="marketingPrivacy")
	public String marketingPrivacy() {
		return "etc/marketingPrivacy";
	}
	@GetMapping(value="adPrivacy")
	public String adPrivacy() {
		return "etc/adPrivacy";
	}
}
