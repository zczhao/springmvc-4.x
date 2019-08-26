# SpringMVC概述

**Spring为展显层提供的基于MVC设计理念**的优秀的Web框架，是目前主流的MVC框架之一

Spring3.0后全面超越Struts2,成为**最优秀的MVC框架**

SpringMVC通过一套MVC注解，让POJO成为处理请求的控制器，而无须实现任何接口

**支持REST风格的URL请求**

彩用了松耦合可插拔组件结构，比其他MVC框架更具扩展性和灵活性

# helloworld步骤

## 1､依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
</dependency>
```

## 2､在web.xml中配置DispatcherServlet

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.5" xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
	http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">

    <!-- 配置DispatcherServlet -->
    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 配置DispatcherServlet的一个初始化参数：配置SpringMVC配置文件的位置和名称 -->
        <!--
            实际上也可以不通过contextConfigLocation 来配置SpringMVC的配置文件，而使用默认的
            默认的配置文件为：/WEB-INF/<servlet-name>-servlet.xml
            例：
                当前<servlet-name>为dispatcherServlet
                那应在/WEB-INF文件夹下创建一个名为dispatcherServlet-servlet.xml
        -->
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

</web-app>
```

## 3､加入SpringMVC的配置文件

```xml
<!-- 配置自动扫描的包 -->
<context:component-scan base-package="zzc.springmvc"/>

<!-- 配置视图解析器 -->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <!-- 需要在webapp下新建views文件夹-->
    <property name="prefix" value="/views/"/>
    <property name="suffix" value=".jsp"/>
</bean>
```

## 4､编写处理请求的处理器，并标识为处理器

```java
@Controller
public class HelloWorld {


	/**
	 * 1、使用 @RequestMapping 注解来映射请求的URL
	 * 2、反回值会通过视图解析器解析为实际的目的视图，对于InternalResourceViewResolver视图解析器，会做如下的解析：
	 *     通过prefix+returnVal+suffix 这样的方式得到实际的物理视图，然后做转发操作
	 * /views/success.jsp
	 * http://localhost:8080/helloWorld
	 */
	@RequestMapping("/helloWorld")
	public String hello() {
		System.out.println("hello world");
		return "success";
	}
}
```

## 5､编写视图

在webapp/views文件夹下新建success.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Success Page</h1>
</body>
</html>
```

