<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>@RequestMapping</title>
</head>
<body>
使用@RequestMapping映射请求<br>
    <a href="springmvc/testRequestMapping">Test RequestMapping</a>
    <hr>
@RequestMapping请求方式<br>
    <a href="springmvc/testMethod">Test Method GET</a>
    <br><br>
    <form action="springmvc/testMethod" method="post">
        <input type="submit" value="Test Method POST">
    </form>
    <hr>
@RequestMapping映射请求参数、请求方法或请求头<br>
    <a href="/springmvc/testParamsAndHeaders?username=ZhaoZhiCheng&age=11">Test ParamsAndHeaders</a>
    <hr>
@RequestMapping Ant风格的URL<br>
    <a href="/springmvc/testAntPath/888/abc">Test AntPath</a>
</body>
</html>
