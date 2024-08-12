package kr.co.iei.utils;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.dto.Member;

public class AdminInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		if(member != null && member.getMemberLevel() == 1) {
			return true;
		}
		else {
			//관리자가 아닌경우
            //경로 지정 필요
			response.sendRedirect("/");
			return false;
		}
	}
	
	

}
