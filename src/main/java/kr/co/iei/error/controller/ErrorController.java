package kr.co.iei.error.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/error")
public class ErrorController {

	@RequestMapping(value="/notFound")
	public String notFound() {
		return "/error/error404";
	}
	
	@RequestMapping(value="/serverError")
	public String serverError() {
		return "/error/error505";
	}

	@RequestMapping(value="/methodNotAllowed")
	public String methodNotAllowed() {
		return "/error/error405";
	}
}
