package kr.co.iei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



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

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// 로그인 처리가 꼭 필요한 애들을 갖다 넣기
//		// .addPathPatterns()	=> 로그인 처리가 필요한 애들
//		// .excludePathPatterns() => 예외인 애들 (/notice/** 처럼 한꺼번에 적었을때 그 중에서 로그인이 필요없어도 보여주고 싶은 애들만)
//		//						  => but 에디터가 들어간 애들도 예외처리 해주어야함 파일 안 보임..
//		registry.addInterceptor(new LoginInterceptor())
//				.addPathPatterns("/member/loginout","/member/mypage1","/member/update1","/member/mypage2","/member/update2","/member/delete","/notice/**")
//				.excludePathPatterns("/notice/list","/notice/view","/notice/filedown","/notice/editor/**","/admin/**");
////		WebMvcConfigurer.super.addInterceptors(registry);
//		
//		registry.addInterceptor(new AdminInterceptor())
//				.addPathPatterns("/admin/**");
//	}
	
	
	
}
