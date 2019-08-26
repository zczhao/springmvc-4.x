<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>input</title>
</head>
<body>
    <!--
        1、使用form标签
            可以更快速的开发出表单页面，而且可以更方便的进行表单值回填
        2、注意
        可以通过modelAttribute属性指定绑定的模型属性，若没有指定该属性，则默认从request域对象中读取command的表单bean，如果该属性值也不存在，则会发生错误
    -->
    <form:form action="${pageContext.request.contextPath}/emp" method="post" modelAttribute="employee">
        <!-- path属性对应html表单标签的name属性值 -->
        <c:if test="${empty employee.id}">
            LastName: <form:input path="lastName" /> <br>
        </c:if>
        <c:if test="${not empty employee.id}">
            <form:hidden path="id"/>
            <!-- 对于_method不能使用form:hidden 标签，因为modelAttribute对应的bean中没有_method这个属性 -->
            <input type="hidden" name="_method" value="put"/>
        </c:if>
        Email: <form:input path="email"/> <br>
        <%
            Map<String, String> genders = new HashMap<String, String>();
            genders.put("1", "Male");
            genders.put("0", "Female");

            request.setAttribute("genders", genders);
        %>
        Gender: <br>
        <form:radiobuttons path="gender" items="${genders}" delimiter="<br>"/> <br>
        Department: <form:select path="department.id" items="${departments}" itemLabel="departmentName" itemValue="id"/>
        <br>
        <input type="submit" value="Submit">
    </form:form>
</body>
</html>
