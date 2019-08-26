<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>@RequestParam</title>
</head>
<body>
@RequestParam <br>
    <a href="/springmvc/testRequestParam?username=knight&age=11">Test RequestParam</a>
<hr>

@RequestHeader <br>
<a href="/springmvc/testRequestHeader">Test RequestHeader</a>
<hr>

@CookieValue <br>
<a href="/springmvc/testCookieValue">Test CookieValue</a>
<hr>

使用POJO对象绑定请求参数值 <br>
<form action="/springmvc/testPojo" method="post">
    username：<input type="text" name="username"> <br>
    password：<input type="password" name="password"> <br>
    email：<input type="text" name="email"> <br>
    age：<input type="text" name="age"><br>
    province：<input type="text" name="address.province"><br>
    city：<input type="text" name="address.city"><br>
    <input type="submit" value="Test POJO">
</form>
<hr>

使用Servlet API作为入参 <br>
<a href="/springmvc/testServletAPI">Test ServletAPI</a>

</body>
</html>
