package zzc.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {

	public static final String SUCCESS = "success";

	/**
	 * @PathVariable 可以来映射URL中的占位符到目标方法的参数中
	 *
	 * http://localhost:8080/springmvc/testPathVariable/1
	 */
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") Integer id) {
		System.out.println("testPathVariable: " + id);
		return SUCCESS;
	}
}
