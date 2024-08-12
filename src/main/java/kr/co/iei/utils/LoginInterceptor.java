package kr.co.iei.utils;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.dto.Member;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		if(member != null) return true; //로그인이 되어있으므로 true리턴
		else {
			//페이지 이동
			response.sendRedirect("/member/login");
			return false;				//로그인이 안 되어있으므로 false리턴
		}
	}

	
}
