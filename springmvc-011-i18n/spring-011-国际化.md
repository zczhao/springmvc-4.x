# 国际化概述

默认情况下，SpringMVC根据**Accept-Language**参数判断客户端的本地化类型

当接受请求时，SpringMVC会在上下文中查找一个本地化解析器(**LocalResolver**)，找到后使用它获取请示所对应的的本地化类型信息

SpringMVC还充许装配一个**动态更改本地化类型的拦截器**，这样通过指定一个请求参数就可以控制单个请求的本地化类型

# 本地化解析器和本地化拦截器

AcceptHeaderLocaleResolver：根据HTTP请求头的Accept-language参数确定本地化类型，如果没有显示定义本地化解析器，SpringMVC使用该解析器

CookieLocaleResolver：根据指定的Cookie值确定本地化类型

SessionLocaleResolver：根据Session中特定的属性确定本地化类型

LocaleChangeInterceptor：从请求参数中获取本次请求对应的本地化类型

## SessionLocaleResolver&LocaleChangeInterceptor工作原理：

1､获取name=locale的请求参数

2､把第一步的Locale请求参数解析为Locale对象

3､获取LocaleResolver对象

4､把Locale对象设置为Session属性

5､从Session中获取Locale对象

6､使用该Locale对象

# 根据参数切换国际化

```properties
<!-- 配置SessionLocaleResolver -->
<bean id="localeResolver" class="org.springframework.web.servlet.i18n.SessionLocaleResolver"/>

<!-- 配置LocaleChangeInterceptor -->
<mvc:interceptors>
<bean class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor"/>
</mvc:interceptors>
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>i18n</title>
</head>
<body>
    <fmt:message key="i18n.user"/>

    <br><br>
    <a href="i18n2">i18n2 Page</a>

    <br><br>
    <a href="i18n?locale=zh_CH">中文</a>

    <br><br>
    <a href="i18n?locale=en_US">English</a>
</body>
</html
```

```java
@Controller
public class SpringMVCTest {

	@Autowired
	private ResourceBundleMessageSource messageSource;

	@RequestMapping("/i18n")
	public String testI18n(Locale locale){
		String val = messageSource.getMessage("i18n.user", null, locale);
		System.out.println("val = " + val);
		return "i18n";
	}


	@RequestMapping("/i18n2")
	public String testI18n2(){
		return "i18n2";
	}

}
```

