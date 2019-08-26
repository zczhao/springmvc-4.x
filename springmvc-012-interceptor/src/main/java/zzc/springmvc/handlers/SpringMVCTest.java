package zzc.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringMVCTest {

	private static final String SUCCESS = "success";

	@RequestMapping("/testFirstInterceptor")
	public String testFirstInterceptor(){
		System.out.println("SpringMVCTest.testFirstInterceptor");
		return SUCCESS;
	}


	@RequestMapping("/testSecondInterceptor")
	public String testSecondInterceptor(){
		System.out.println("SpringMVCTest.testSecondInterceptor");
		return SUCCESS;
	}

}
