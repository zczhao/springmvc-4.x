# 自定义拦截器

SpringMVC也可以使用拦截器对请求进行拦截处理，用户可以自定义拦截器来实现特定的功能，自定义的拦截器必须实现HandlerInterceptor接口：

​	preHandle()：这个方法在业务处理器处理请求之前被调用，在该方法中对用户请求request进行处理。如果程序员决定该拦截器对请求进行拦截处理后还要调用其他的拦截器，或者是业务处理器去进行处理，则返回true，如果程序员决定不需要再调用其他的组件去处理请求，则返回false

​	postHandle()：这个方法在业务处理器处理完请示后，但是DispatcherServlet向客户端返回响应前被调用，在该方法中对用户请求request进行处理

​	afterCompletion()：这个方法在DispatcherServlet完全处理完请求后被调用，可以在该方法中进行一些资源清理的操作

```java
package zzc.springmvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FirstInterceptor implements HandlerInterceptor {

	/**
	 * 该方法在目标方法之前被调用
	 * 若返回值为ture，则继续调用后续的拦截器和目标方法
	 * 若返回值为false，则不会再调用后续的拦截器和目标方法
	 *
	 * 可以考虑做权限、日志、事务等处理
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		System.out.println("FirstInterceptor.preHandle");
		return true;
	}

	/**
	 * 调用目标方法之后，但渲染视图之前
	 * 可以对请求域中的属性或视图做出修改
	 *
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param o
	 * @param modelAndView
	 * @throws Exception
	 */
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
		System.out.println("FirstInterceptor.postHandle");
	}

	/**
	 * 渲染视图之后被调用，释放资源
	 *
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param o
	 * @param e
	 * @throws Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
		System.out.println("FirstInterceptor.afterCompletion");
	}
}

```

```xml
<mvc:interceptors>
    <!-- 配置自定义拦截器 -->
    <bean class="zzc.springmvc.interceptor.FirstInterceptor"/>
</mvc:interceptors>
```

执行顺序：

1､FirstInterceptor#preHandle

2､HandlerAdapter#handle

3､FirstInterceptor#postHandle

4､DispatcherServlet#render

5､FirstInterceptor#afterCompletion

# 拦截器配置

```xml
<mvc:interceptors>
    <!-- 配置自定义拦截器 -->
    <bean class="zzc.springmvc.interceptor.FirstInterceptor"/>
    <!-- 配置拦截器(不)作用的路径-->
    <mvc:interceptor>
        <mvc:mapping path="/testSecondInterceptor"/>
        <mvc:exclude-mapping path="/testFirstInterceptor"/>
        <bean class="zzc.springmvc.interceptor.SecondInterceptor"/>
    </mvc:interceptor>
</mvc:interceptors>
```

```java
package zzc.springmvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecondInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		System.out.println("SecondInterceptor.preHandle");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
		System.out.println("SecondInterceptor.postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
		System.out.println("SecondInterceptor.afterCompletion");
	}
}
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Interceptor</title>
</head>
<body>
全局拦截器：
    <a href="/testFirstInterceptor">Test Interceptor</a>
<hr>
指定拦截器：
    <a href="/testSecondInterceptor">Test Interceptor</a>
</body>
</html>
```

