# 处理数据模型

SpringMVC提供了以下几种途径输出模型数据：

**ModelAndView**：处理方法返回值类型为ModelAndView时，方法体即可通过该对象添加模型数据

**Map及Model**：入参为org.springframework.ui.Model、org.springframework.ui.ModelMap、java.util.Map时，处理方法返回时，Map中的数据会自动添加到模型中

**@SessionAttributes**：将模型中的某个属性暂存到HttpSession中，以便多个请求之间可以共享这个属性

**@ModelAttribute**：方法入参标注该注解后，入参的对象就会放到数据模型中

## 1、ModelAndView

控制器处理方法的返回值如果为ModelAndView，则其既包含视图信息，也包含模型数据信息

添加模型数据

ModelAndView addObject(String attributeName,Object attributeValue)

ModelAndView addAllObject(Map<String,?> modelMap)

设置视图

void setView(View view)

void setViewName(String viewName)

```java
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
```

## 2、Map及Model

SpringMVC在内部使用了一个org.springframework.ui.Model接口存储模型数据

具体步骤：

​	SpringMVC在调用方法前会创建一个隐含的模型对象作为模型数据的存储容器

​	如果方法的入参为Map或Model类型，SpringMVC会将隐含模型的引用传递给这些入参，在方法体内，开发者可以通过这个入参对象访问到模型中的所有数据，也可以向模型中添加新的属性数据

```java
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
```

## 3、@SessionAttributes

**若希望在多个请求之间共用某个模型属性数据**，则可以在控制器类上标注一个@SessionAttributes，SpringMVC将在模型中对应的属性暂存到HttpSession中

@SessionAttributes除了可以通过属性名指定需要放到会话中的属性外，还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中

@SessionAttributes(types=User.class) 会将隐含模型中所有类型为User.class的属性添加到会话中

@SessionAttributes(value={"user1","user2"})

@SessionAttributes(types={User.class,Dept.class})

@SessionAttributes(value={"user1","user2"},types={Dept.class})

```java
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
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Success Page</h1>
    request user：${requestScope.user}
    <br><br>
    session user：${sessionScope.user}
    <br><br>
    request company：${sessionScope.company}
    <br><br>
    session company：${sessionScope.company}
</body>
</html>
```

显示 ：

```
Success Page

request user：User{username='Knight', password='123456', email='123@vip.com', age=18, address=Address{province='湖南', city='衡阳'}} 

session user：User{username='Knight', password='123456', email='123@vip.com', age=18, address=Address{province='湖南', city='衡阳'}} 

request company：ZhaoZhiCheng 

session company：ZhaoZhiCheng
```

## 4、@ModelAttribute

```java
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
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>处理数据模型</title>
</head>
<body>
@ModeAttribute <br>
<!--
    模拟修改操作
        1、原始数据为：1, knight, 123456, knight@vip.com, 18
        2、密码不能被修改
        3、表单回显，模拟操作直接在表单填写对应的属性值
-->
<form action="/springmvc/testModelAttribute" method="post">
    <input type="hidden" name="id" value="1"/>
    username: <input type="text" name="username" value="knight"> <br>
    email: <input type="text" name="email" value="knight@vip.com"> <br>
    age: <input type="text" name="age" value="18"> <br>
    <input type="submit" value="Test ModelAttribute">
</form>
</body>
</html>
```

```
ModelAttribute method
从数据中获取一个对象：Person{id=1, username='knight', password='123456', email='knight@vip.com', age=18}
修改： Person{id=1, username='knight1', password='123456', email='knight@vip.com', age=15}
```



