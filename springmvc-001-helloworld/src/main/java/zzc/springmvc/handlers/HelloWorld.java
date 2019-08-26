package zzc.springmvc.handlers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {


	/**
	 * 1、使用 @RequestMapping 注解来映射请求的URL
	 * 2、反回值会通过视图解析器解析为实际的目的视图，对于InternalResourceViewResolver视图解析器，会做如下的解析：
	 *     通过prefix+returnVal+suffix 这样的方式得到实际的物理视图，然后做转发操作
	 * /views/success.jsp
	 *
	 * http://localhost:8080/helloWorld
	 */
	@RequestMapping("/helloWorld")
	public String hello() {
		System.out.println("hello world");
		return "success";
	}
}
