package zzc.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/springmvc")
public class SpringMVCTest {

	public static final String SUCCESS = "success";


	/**
	 * 目标方法的返回值可以是ModelAndView类型
	 * 其中可以包含视图和模型信息
	 *
	 * SpringMVC 会把ModelAndView的model中的数据放入到request 域对象中
	 * http://localhost:8080/springmvc/testMoodelAndView
	 */
	@RequestMapping("/testMoodelAndView")
	public ModelAndView testMoodelAndView() {
		String viewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		// 添加模型数据到ModelAndView中
		modelAndView.addObject("time", new Date());
		return modelAndView;
	}

	/**
	 * 目标方法可以添加Map(实际上也可以是Model、ModelMap类型)类型的参数
	 *
	 * http://localhost:8080/springmvc/testMap
	 */
	@RequestMapping("/testMap")
	public String testMap(Map<String,Object> map){
		System.out.println(map.getClass().getName());
		map.put("time", new Date());
		return SUCCESS;
	}
}
