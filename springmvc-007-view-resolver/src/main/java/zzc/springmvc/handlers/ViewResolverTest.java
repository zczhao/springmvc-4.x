package zzc.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/springmvc")
public class ViewResolverTest {

	public static final String SUCCESS = "success";

	@RequestMapping("/testViewAndViewResolver")
	public String testViewAndViewResolver() {
		System.out.println("testViewAndViewResolver");
		return SUCCESS;
	}


	/**
	 * http://localhost:8080/springmvc/testView
	 */
	@RequestMapping("/testView")
	public String testView(){
		System.out.println("testView");
		return "helloView";
	}

	/**
	 * http://localhost:8080/springmvc/testRedirect
	 *
	 * 浏览器上显示的地址：http://localhost:8080/index.jsp
	 */
	@RequestMapping("/testRedirect")
	public String testRedirect(){
		System.out.println("testRedirect");
		return "redirect:/index.jsp";
	}

	/**
	 * http://localhost:8080/springmvc/testForward
	 *
	 * 浏览器上显示的地址：http://localhost:8080/springmvc/testForward
	 */
	@RequestMapping("/testForward")
	public String testForward(){
		System.out.println("testForward");
		return "forward:/index.jsp";
	}

}
