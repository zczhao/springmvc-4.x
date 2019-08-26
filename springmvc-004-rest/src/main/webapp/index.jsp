<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HiddenHttpMethodFilter</title>
</head>
<body>
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

</body>
</html>
