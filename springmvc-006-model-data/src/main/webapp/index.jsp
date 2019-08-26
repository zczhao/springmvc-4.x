<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>处理数据模型</title>
</head>
<body>

ModelAndView <br>
    <a href="springmvc/testMoodelAndView">Test ModelAndView</a>
<hr>

Map <br>
    <a href="/springmvc/testMap">Test Map</a>
<hr>

@SessionAttributes <br>
    <a href="/springmvc/testSessionAttributes">Test Map</a>
<hr>

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
