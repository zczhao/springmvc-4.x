# 请求方法签名

SpringMVC**通过分析处理方法的签名，将HTTP请求信息绑定到处理方法的相应入参中**

SpirngMVC对控制器处理方法签名的限制是很宽松的，几乎可以按喜欢的任何方式对方法进行签名

必要时可以对方法及方法入参标注相应的注解(**@PathVariable、@RequestParam、@RequestHeader**等)，SpringMVC框架会将HTTP请求的信息绑定到相应的方法入参中，并根据方法的返回值类型做出相应的后续处理

## 1､使用@RequestParam绑定请求参数值

在处理方法入参处理使用@RequestParams可以把请求参数传递给请求方法

​	value：参数名

​	required：是否必须，默认为true，表示请求参数中必须包含对应的参数，若不存在，将抛出异常

​	default：默认值

```java
@Controller
@RequestMapping("/springmvc")
public class SpringMVCTest {

	public static final String SUCCESS = "success";


	/**
	 * @RequestParam 来映射请求参数
	 *  value 值为请求参数的参数名
	 *  required 该参数是否必须
	 *  defaultValue 请求参数的默认值
	 *  
	 *  http://localhost:8080/springmvc/testRequestParam?username=knight&age=11
	 */
	@RequestMapping("/testRequestParam")
	public String testRequestParam(@RequestParam(value = "username") String un, @RequestParam(value = "age",required = false,defaultValue = "0") int age) {
		System.out.println("testRequestParam, username: " + un + ", age: " + age);
		return SUCCESS;
	}
}
```



## 2､使用@RequestHeader绑定请求报头的属性值

请求头包含了若干个属性，服务器可据此获知客户端的信息，**通过@RequestHeader即可将请求头中的属性值绑定到处理方法的入参中**

```java
/**
 * 映射请示头信息
 * 用法同@RequestParam注解
 * http://localhost:8080/springmvc/testRequestHeader
 */
@RequestMapping("/testRequestHeader")
public String testRequestHeader(@RequestHeader(value = "Accept-Language") String al) {
    System.out.println("testRequestHeader, Accept-Language: " + al);
    return SUCCESS;
}
```

## 3､使用CookieValue绑定请求域中的Cookie值

@CookieValue可让处理方法入参绑定某个Cookie值

```java
/**
 * @CookieValue：映射一个Cookie值，属性值同@RequestParam
 *
 */
@RequestMapping("/testCookieValue")
public String testCookieValue(@CookieValue("JSESSIONID") String jsessionId) {
	System.out.println("testCookieValue, JSESSIONID: " + jsessionId);
	return SUCCESS;
}
```

## 4､使用POJO对象绑定请求参数值

SpringMVC安按请求参数名和POJO属性名进行自动匹配，自动为该对象填充属性值，支持级联属性

```java
/**
 *SpringMVC安按请求参数名和POJO属性名进行自动匹配，自动为该对象填充属性值，支持级联属性
 */
@RequestMapping("/testPojo")
public String testPojo(User user){
	System.out.println("testPojo: " + user);
	return SUCCESS;
}
```

## 5､使用Servlet API作为入参

SpringMVC的Handler方法可以接受哪些ServletAPI类型的参数

HttpServletRequest、HttpServletResponse、HttpSession、java.security.Principal、Locale、InputStream、OutputStream、Reader、Writer

org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter

```java
@Override
protected Object resolveStandardArgument(Class<?> parameterType, NativeWebRequest webRequest) throws Exception {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

    if (ServletRequest.class.isAssignableFrom(parameterType) ||
        MultipartRequest.class.isAssignableFrom(parameterType)) {
        Object nativeRequest = webRequest.getNativeRequest(parameterType);
        if (nativeRequest == null) {
            throw new IllegalStateException(
                "Current request is not of type [" + parameterType.getName() + "]: " + request);
        }
        return nativeRequest;
    }
    else if (ServletResponse.class.isAssignableFrom(parameterType)) {
        this.responseArgumentUsed = true;
        Object nativeResponse = webRequest.getNativeResponse(parameterType);
        if (nativeResponse == null) {
            throw new IllegalStateException(
                "Current response is not of type [" + parameterType.getName() + "]: " + response);
        }
        return nativeResponse;
    }
    else if (HttpSession.class.isAssignableFrom(parameterType)) {
        return request.getSession();
    }
    else if (Principal.class.isAssignableFrom(parameterType)) {
        return request.getUserPrincipal();
    }
    else if (Locale.class == parameterType) {
        return RequestContextUtils.getLocale(request);
    }
    else if (InputStream.class.isAssignableFrom(parameterType)) {
        return request.getInputStream();
    }
    else if (Reader.class.isAssignableFrom(parameterType)) {
        return request.getReader();
    }
    else if (OutputStream.class.isAssignableFrom(parameterType)) {
        this.responseArgumentUsed = true;
        return response.getOutputStream();
    }
    else if (Writer.class.isAssignableFrom(parameterType)) {
        this.responseArgumentUsed = true;
        return response.getWriter();
    }
    return super.resolveStandardArgument(parameterType, webRequest);
}
```

```java
/**
 * 
 * http://localhost:8080/springmvc/testServletAPI
 */
@RequestMapping("/testServletAPI")
public String testServletAPI(HttpServletRequest request, HttpServletResponse response) {
	System.out.println("testServletAPI, " + request + ", " + response);
	return SUCCESS;
}
```

```java
/**
 *
 * http://localhost:8080/springmvc/testServletAPI
 */
@RequestMapping("/testServletAPI")
public void testServletAPI(HttpServletRequest request, HttpServletResponse response, Writer out) throws IOException {
    System.out.println("testServletAPI, " + request + ", " + response);
    out.write("Hello SpringMVC");
}
```

