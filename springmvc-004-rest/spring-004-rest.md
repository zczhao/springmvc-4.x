# REST

REST：即Representational State Transfer。**(资源)表现层状态转化，是目前最流行的一种互联网软件架构。**它结构清晰、符合标准、易于理解、扩展方便，所心正得到越来越多网站的采用

==资源(Resources)==：**网络上的一个实体，或者说是网络上的一个具体信息**。它可以是一段文本、一张图片、一首歌曲、一种服务、总之就是一个具体的存在。可以用一个URI(统一资源定位符)指向它，**每种资源对应一个特定的URI**。要获取这个资源，访问它的URI就可以，因此**URI即为每一个资源的独一无二的总会别符**

==表现层(Representational)==：**把资源具体呈现出来的形式。叫做它的表现层(Representational)**。比如，文本可以用txt格式表现，也可以用HTML格式、XML格式、JSON格式表现，甚至可以彩用二进制格式。

==状态转化(State Transfer)==：每发出一个请求，就代表了客户端和服务器的一次交互过程，HTTP协议，是一个无状态协议，即所有的状态都保存在服务器端，因此，如果客户想要操作服务器，必须通过某种手段，让服务器端发生"状态转化"(State Transfer)。而这种转化是建立在表现层之上的，所以就是"表现层状态转化"。具体说，就是**HTTP协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE。它们分别对应四种基本操作：GET用来获取资源，POST用来新建资源，PUT用来更新资源，DELETE用来删除资源**

示例：

/order/1 HTTP ==GET==：得到id=1的order

/order/1 HTTP ==DELETE==：删除id=1的order

/order/1 HTTP ==PUT==：更新id=1的order

/order HTTP ==POST==：新增order

**HiddenHttpMethodFilter**：浏览器form表单只支持GET与POST请求，而DELETE和PUT等method并不支持，Spring3.0添加了一个过滤器，可以将这些请求转换为标准的http请求，使得支持GET、POST、PUT与DELETE请求

```xml
<!-- 配置org.springframework.web.filter.HiddenHttpMethodFilter：可以把POST请求转为DELETE或PUT请求 -->
<filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>

<filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>

<servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring-mvc.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

```java
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
```

```jsp
HiddenHttpMethodFilter：可以把POST请求转为DELETE或PUT请求
<hr>

GET请求 <br>
<a href="/springmvc/testRest/1">Test Rest GET</a>

<hr>

POST请求 <br>
<form method="post" action="/springmvc/testRest">
    <input type="submit" value="Test Rest POST"/>
</form>

<hr>
PUT请求 <br>
<form method="post" action="/springmvc/testRest/1">
    <input type="hidden" name="_method" value="PUT"/>
    <input type="submit" value="Test Rest PUT"/>
</form>


<hr>
DELETE请求 <br>
<form method="post" action="/springmvc/testRest/1">
    <input type="hidden" name="_method" value="DELETE"/>
    <input type="submit" value="Test Rest DELETE"/>
</form>
```

