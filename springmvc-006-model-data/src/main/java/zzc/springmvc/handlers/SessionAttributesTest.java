package zzc.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import zzc.springmvc.entities.Address;
import zzc.springmvc.entities.User;

import java.util.Map;

@SessionAttributes(value = {"user"}, types = {String.class})
@Controller
@RequestMapping("/springmvc")
public class SessionAttributesTest {
	public static final String SUCCESS = "success";


	/**
	 * @SessionAttributes 除了可以通过属性名指定需要放到会话中的属性外(实际上使用的是value属性值)，还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中(实际上使用的是types 属性值)
	 *
	 * 注意：该注解只能放在类的上面，而不能修饰方法
	 * http://localhost:8080/springmvc/testSessionAttributes
	 */
	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map) {
		User user = new User("Knight", "123456", "123@vip.com", 18, new Address("湖南", "衡阳"));
		map.put("user", user);
		map.put("company", "ZhaoZhiCheng");
		return SUCCESS;
	}
}
