# 1、使用@RequestMappng映射请求

SpringMVC使用**@RequestMapping**注解为控制器指定可以处理哪些URL请求

在控制器的**类定义及方法定义处**都可标注

@RequestMapping

​	**类定义处**：提供初步r的请求映射信息。相对于WEB应用的根目录

​	**方法处**：提供进一步的细分映射信息。相对于类定义处的URL。若类定义处未标注@RequestMapping，则方法处标记的URL相对于WEB应用的根目录

DispatcherServlet截获请求后，就通过控制器上@RequestMapping提供的映射信息确定请求所对应的处理方法

```java
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
}
```

# 2、请求方式

```java
@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {

	public static final String SUCCESS = "success";

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

}
```

# 3、映射请求参数、请求方法或请求头

@RequestMapping除了可以使用请求URL映射请求外，还可以使用请求方法，请求参数及请求头映射请求

@RequestMapping的value、method、params及headers分别表示请求URL、请求方法、请求参数及请求头的映射条件，他们之间的关系是与的关系，联合使用多个条件可让请求映射更加精确化

params和headers支持简单的表达式：

param1：表示请求必须包含名为param1的请求参数

!param1：表示请求不能包含名为param1的请求参数

param1!=value1：表示请求包含名为param1的请求参数，但其值不能为value1

{"param1=value1","param2"}：请求必须包含名为param1和param2的两个请求参数，且param1参数的值必须为value1

```java
@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {

	public static final String SUCCESS = "success";

	/**
	 * 了解：可以使用params 和 headers来更加精确的映射请求。params 和 headers支持简单的表达式
	 * http://localhost:8080/springmvc/testParamsAndHeaders?username=ZhaoZhiCheng&age=11
	 */
	@RequestMapping(value = "/testParamsAndHeaders", params = {"username", "age!=10"}, headers = {"Accept-Language=zh-CN,zh;q=0.9"})
	public String testParamsAndHeaders() {
		System.out.println("testParamsAndHeaders");
		return SUCCESS;
	}
}
```

# 4、@RequestMapping资源地址

Ant风格资源地址支持3种匹配符：

?：匹配文件名中的一个字符

*：匹配文件名中的任意字符

**：匹配多层路径

@RequestMapping还支持Ant风格的URL：

/user/*/createUser：匹配/user/==aaa==/createUser、/user/==bbb==/createUser、/user/==ccc==/createUser等URL

/user/**/createUser：匹配/user/==aaa/bbb==/createUser等URL

/user/createUser??：匹配/user/createUser==aa==、/user/createUser==bb==等URL

```java
@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {

	public static final String SUCCESS = "success";
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
```

