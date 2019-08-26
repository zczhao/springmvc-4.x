<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Success Page</h1>
    <br><br>
    time：${requestScope.time}
    <br><br>
    request user：${requestScope.user}
    <br><br>
    session user：${sessionScope.user}
    <br><br>
    request company：${sessionScope.company}
    <br><br>
    session company：${sessionScope.company}
</body>
</html>
