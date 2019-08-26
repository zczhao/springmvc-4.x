package zzc.springmvc.handlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zzc.springmvc.dao.EmployeeDao;
import zzc.springmvc.entities.Employee;
import zzc.springmvc.entities.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {


	@Autowired
	private EmployeeDao employeeDao;


	@ResponseBody
	@RequestMapping("/testJson")
	public Collection<Employee> testJson() {
		return employeeDao.getEmployees();
	}


	@ResponseBody
	@RequestMapping(value = "/testHttpMessageConverter", method = RequestMethod.POST)
	public String testHttpMessageConverter(@RequestBody String body) {
		System.out.println(body);
		return "hello World! " + new Date();
	}

	/**
	 * 下载文件
	 */
	@RequestMapping("/testResponseEntity")
	public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
		byte[] body = null;
		ServletContext servletContext = session.getServletContext();
		InputStream is = servletContext.getResourceAsStream("/files/abc.txt");
		body = new byte[is.available()];
		is.read(body);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=abc.txt");

		HttpStatus status = HttpStatus.OK;
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, status);
		return response;
	}

	/**
	 * http://localhost:8080/springmvc/user/1：返回user页面
	 * http://localhost:8080/springmvc/user/1.json 返回json数据
	 * http://localhost:8080/springmvc/user/1.xml 返回xml数据
	 *
	 */
	@RequestMapping(value = "/user/{id}", method=RequestMethod.GET)
	public String getUserPage(@PathVariable("id") String id, Model model) {
		User user = new User("zczhao","123456","zczhao@vip.com",22);
		model.addAttribute(user);
		return "user";
	}
}
