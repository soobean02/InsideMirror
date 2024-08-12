package kr.co.iei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.iei.utils.AdminInterceptor;
import kr.co.iei.utils.LoginInterceptor;



// 스프링 부트 설정파일
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	@Value("${file.root}")
	private String root;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/* 우선순위 1 */
		registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/","classpath:/static/");
		// 별(*)을 두개 찍으면 모든 파일 의미 // classpath:/templates/ 로 가거나 classpath:/static/ 로 감
		
		/* 우선순위 2 - but /photo라고 들어오면 여기서 찾음 */
		registry.addResourceHandler("/product/**").addResourceLocations("file:///"+root+"/product/");
		
		registry.addResourceHandler("/product/editor/**").addResourceLocations("file:///"+root+"/product/editor/");

		registry.addResourceHandler("/board/**").addResourceLocations("file:///"+root+"/board/");
		
		registry.addResourceHandler("/member/**").addResourceLocations("file:///"+root+"/member/");
		
		registry.addResourceHandler("/photo/**").addResourceLocations("file:///"+root+"/photo/");
	}

	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
			.addPathPatterns("/member/logout","/board/**","/photo/**","/guest/**","/product/**","/customer/**")
			.excludePathPatterns("");
		
		registry.addInterceptor(new AdminInterceptor())
			.addPathPatterns("/admin/**");
	}
	
	
}
