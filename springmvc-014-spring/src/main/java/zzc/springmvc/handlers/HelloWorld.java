package zzc.springmvc.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import zzc.springmvc.service.UserService;

@Controller
public class HelloWorld {

	@Autowired
	private UserService userService;

	public HelloWorld() {
		System.out.println("HelloWorld Constructor...");
	}

	@RequestMapping("/hello")
	public String hello() {
		System.out.println("success");
		return "success";
	}

}
