package kr.co.iei;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebError implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>{

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		// ErrorPage org.springframework.boot.web.server.ErrorPage로 임포트!
		// 404 에러
		ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND,"/error/notFound"); // 404에러가 뜨면 notFound로 보내기

		// 505에러
		ErrorPage error505 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error/serverError");
		
		ErrorPage error405 = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/methodNotAllowed");

		factory.addErrorPages(error404, error505, error405);
		
	}

}