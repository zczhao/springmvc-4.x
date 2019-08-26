package zzc.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/springmvc")
public class SpringMVCTest {

	public static final String SUCCESS = "success";


	/**
	 * Rest风格的URL
	 *
	 * 以CRUD为例：
	 * 新增：/order POST
	 * 修改：/order/1 PUT update?id=1
	 * 获取：/order/1 GET get?id=1
	 * 删除：/order/1 DELETE delete?id=1
	 *
	 * 如何发送PUT和DELETE请求
	 *  1、需要配置HiddenHttpMethodFilter
	 *  2、需要发送POST请求
	 *  3、需要在发送POST请求时携带一个name="_method"的隐藏域，值为 DELETE 或 PUT
	 *
	 * 在SpringMVC的目标方法中如何得到id
	 *  使用@PathVariable注解
	 */
	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.GET)
	public String testRestGet(@PathVariable("id") Integer id) {
		System.out.println("testRestGet: " + id);
		return SUCCESS;
	}

	@RequestMapping(value = "/testRest", method = RequestMethod.POST)
	public String testRestPost() {
		System.out.println("testRestPost");
		return SUCCESS;
	}

	@ResponseBody
	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.PUT)
	public String testRestPUT(@PathVariable("id") Integer id) {
		System.out.println("testRestPUT: " + id);
		return SUCCESS;
	}

	@ResponseBody
	@RequestMapping(value = "/testRest/{id}", method = RequestMethod.DELETE)
	public String testRestDelete(@PathVariable("id") Integer id) {
		System.out.println("testRestDelete: " + id);
		return SUCCESS;
	}

}
