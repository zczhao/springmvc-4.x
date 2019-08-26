<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>国际化</title>
</head>
<body>
<!--
    关于国际化：
    1、在页面上能够根据浏览器语言设置的情况对文本(不是内容)，时间、数值进行本地化处理
    2、可以在Bean中获取国际化资源文件Locale对应的消息
    3、可以通过超链接切换Locale，而不再依赖于浏览器的语言设置情况

    解决：
    1、使用JSTL的fmt标签
    2、在bean中注入ResourceBundleMessageSource实例，使用其对应的getMessage方法即可
    3、配置LocalResolver和LocaleChangeInterceptor
-->

<br><br>
<a href="i18n">i18n Page</a>

</body>
</html>
