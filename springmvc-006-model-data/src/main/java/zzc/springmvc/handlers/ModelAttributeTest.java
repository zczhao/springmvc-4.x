package zzc.springmvc.handlers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import zzc.springmvc.entities.Person;

import java.util.Map;

@SessionAttributes(value = {"person"})
@Controller
@RequestMapping("/springmvc")
public class ModelAttributeTest {

	public static final String SUCCESS = "success";

	/**
	 * 1、有@ModelAttribute标记的方法，会在每个目标方法执行之前被SpringMVC调用
	 * 2、@ModelAttribute注解也可以来修饰目标方法POJO类型的入参，其value属性有如下的作用：
	 *  1、SpringMVC会使用value属性值在implicitModel中查找对应的对象，若存在则会直接传入到目标方法的入参中
	 *  2、SpringMVC会以value为key，POJO类型的对象为value，存入到request中
	 */
	@ModelAttribute
	public void getPerson(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		System.out.println("ModelAttribute method");
		if (null != id) {
			// 模拟从数据库中获取对象
			Person person = new Person(1, "knight", "123456", "knight@vip.com", 18);
			System.out.println("从数据中获取一个对象：" + person);

			map.put("person", person);
		}
	}

	/**
	 * 运行流程：
	 *  1、执行@ModelAttribute注解修饰的方法：从数据库中取出对象，把对象放入到Map中，键为：person
	 *  2、SpringMVC从Map中取出Person对象，并把表单的请求参数赋给该Person对象的对应属性
	 *  3、SpringMVC把上述对象传入目标方法的参数
	 *
	 *  注意：在@ModelAttribute注解修饰的方法中，放入到Map时的键需要和目标方法入参类型的第一字母小写的字符串一致！
	 *
	 * SpringMVC 确定目标方法POJO类型入参的过程
	 *  1、确定一个key
	 *      1、若目标方法的POJO类型的参数没有使用@ModelAttribute作为修饰，则key为POJO类名第一个字母的小写
	 *      2、若使用了@ModelAttribute来修饰，则key为@ModelAttribute注解的value属性值
	 *  2、在implicitModel中查找key对应的对象，或存在，则作为入参传入，
	 *      1、若在@ModelAttribute标记的方法中在Map中保存过，且key和1确定的key一致，则会获取到
	 *  3、若implicitModel中不存在key对应的对象，则检查当前的Handler是否使用了@SessionAttributes注解修饰，若使用了该注解，且@SessionAttributes注解的value属性值中包含了key，则会从HttpSession中来获取key所对应的value值，
	 *  若存在则直接传入到目标方法的入参中，若不存在则将抛出异常
	 *  4、若Handler没有标识@SessionAttributes注解@SessionAttributes注解的value值中不包含key，则会通过反射创建POJO类型的参数，传入为目标方法的参数
	 *  5、SpringMVC会把key和POJO类型的对象保存到implicitModel中，进而保存到request中
	 *
	 * 源代码分析的流程
	 *  1、调用@ModelAttribute注解修饰的方法，实际上把@ModelAttribute方法中Map中的数据放在了implicitModel中
	 *  2、解析请求处理器的目标参数，实际上该目标参数来自于WebDataBinder对象的target属性
	 *      1、创建WebDataBinder对象：
	 *          1、确定objectName属性：若传入的attrName属性值为""，则objectName为类名第一个字母小写
	 *              注意：attrName，若目标方法的POJO属性使用了@ModelAttribute来修饰，则attrName值即为@ModelAttribute的value属性值
	 *          2、确定target属性：
	 *              1、在implicitModel中找查attrName对应的属性值。
	 *                  若存在，OK
	 *                  若不存在，则验证当前Handler是否使用了@SessionAttributes进行修饰，若使用了，则尝试从Session中获取attrName所对应的属性值，若session中没有对应的属性值，则抛出异常
	 *                  若Handler没有使用@SessionAttributes进行修饰，或@SessionAttributes中没有使用value值指定的key和attrName相互匹配，则通过反射创建了POJO对象
	 *      2、SpringMVC把表单的请求参数赋给了WebDataBinder的target对应的属性
	 *      3、SpringMVC会把webDATaBinder的attrName和target给到implicitModel
	 *      4、把WebDataBinder的target作为参数传递给目标方法的入参
	 *
	 */
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(@ModelAttribute("person") Person person) {
		System.out.println("修改： " + person);
		return SUCCESS;
	}
}
