package zzc.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {

	public static final String SUCCESS = "success";


	/**
	 * 1、@RequestMapping 除了修饰方法，还可以修饰类
	 *      1、类定义处：提供初步r的请求映射信息。相对于WEB应用的根目录
	 *      2、方法处：提供进一步的细分映射信息。相对于类定义处的URL。若类定义处未标注@RequestMapping，则方法处标记的URL相对于WEB应用的根目录
	 * http://localhost:8080/springmvc/testRequestMapping
	 */
	@RequestMapping("/testRequestMapping")
	public String testRequestMappng() {
		System.out.println("testRequestMappng");
		return SUCCESS;
	}

	/**
	 * 常用：使用method属性来指定请求方式
	 *
	 * http://localhost:8080/springmvc/testMethod
	 */
	@RequestMapping(value = "/testMethod", method = RequestMethod.POST)
	public String testMethod() {
		System.out.println("testMethod");
		return SUCCESS;
	}

	/**
	 * 了解：可以使用params 和 headers来更加精确的映射请求。params 和 headers支持简单的表达式
	 * http://localhost:8080/springmvc/testParamsAndHeaders?username=ZhaoZhiCheng&age=11
	 */
	@RequestMapping(value = "/testParamsAndHeaders", params = {"username", "age!=10"}, headers = {"Accept-Language=zh-CN,zh;q=0.9"})
	public String testParamsAndHeaders() {
		System.out.println("testParamsAndHeaders");
		return SUCCESS;
	}

	/**
	 *  /abc之前可以随便写
	 * http://localhost:8080/springmvc/testAntPath/888/abc
	 */
	@RequestMapping("/testAntPath/*/abc")
	public String testAntPath(){
		System.out.println("testAntPath");
		return SUCCESS;
	}
}
