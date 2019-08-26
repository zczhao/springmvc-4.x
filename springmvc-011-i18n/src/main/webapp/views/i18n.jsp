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
</html>
