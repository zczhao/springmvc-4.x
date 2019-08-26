package zzc.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zzc.springmvc.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Controller
@RequestMapping("/springmvc")
public class SpringMVCTest {

	public static final String SUCCESS = "success";


	/**
	 * @RequestParam 来映射请求参数
	 * value 值为请求参数的参数名
	 * required 该参数是否必须
	 * defaultValue 请求参数的默认值
	 * <p>
	 * http://localhost:8080/springmvc/testRequestParam?username=knight&age=11
	 */
	@RequestMapping("/testRequestParam")
	public String testRequestParam(@RequestParam(value = "username") String un, @RequestParam(value = "age", required = false, defaultValue = "0") int age) {
		System.out.println("testRequestParam, username: " + un + ", age: " + age);
		return SUCCESS;
	}


	/**
	 * 映射请示头信息
	 * 用法同@RequestParam注解
	 * http://localhost:8080/springmvc/testRequestHeader
	 */
	@RequestMapping("/testRequestHeader")
	public String testRequestHeader(@RequestHeader(value = "Accept-Language") String al) {
		System.out.println("testRequestHeader, Accept-Language: " + al);
		return SUCCESS;
	}


	/**
	 * @CookieValue：映射一个Cookie值，属性值同@RequestParam
	 *
	 * http://localhost:8080/springmvc/testCookieValue
	 */
	@RequestMapping("/testCookieValue")
	public String testCookieValue(@CookieValue("JSESSIONID") String jsessionId) {
		System.out.println("testCookieValue, JSESSIONID: " + jsessionId);
		return SUCCESS;
	}


	/**
	 * SpringMVC安按请求参数名和POJO属性名进行自动匹配，自动为该对象填充属性值，支持级联属性
	 *
	 * http://localhost:8080/springmvc/testPojo
	 */
	@RequestMapping("/testPojo")
	public String testPojo(User user) {
		System.out.println("testPojo: " + user);
		return SUCCESS;
	}


	/**
	 *
	 * http://localhost:8080/springmvc/testServletAPI
	 */
	@RequestMapping("/testServletAPI")
	public void testServletAPI(HttpServletRequest request, HttpServletResponse response, Writer out) throws IOException {
		System.out.println("testServletAPI, " + request + ", " + response);
		out.write("Hello SpringMVC");
		//return SUCCESS;
	}
}
