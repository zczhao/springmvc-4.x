<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>列表</title>
</head>
<body>
员工添加页：<a href="/emp">Add New Employee</a> <br><br>

    <c:if test="${empty requestScope.employees}">
        没有任何员工信息
    </c:if>
    <c:if test="${not empty requestScope.employees}">
        <table border="1" cellpadding="10" cellspacing="0">
            <tr>
                <th>ID</th>
                <th>LastName</th>
                <th>Email</th>
                <th>Gender</th>
                <th>Department</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${requestScope.employees}" var="emp">
                <tr>
                    <td>${emp.id}</td>
                    <td>${emp.lastName}</td>
                    <td>${emp.email}</td>
                    <td>${emp.gender == 0 ? "Female" : "Male" }</td>
                    <td>${emp.department.departmentName}</td>
                    <td><a href="/emp/${emp.id}">Edit</a></td>
                    <td><a data-href="/emp/${emp.id}" class="delete" href="javascript:void(0);">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <form action="" method="post" id="deleteForm">
        <input type="hidden" value="delete" name="_method">
    </form>
    <!--
        SpringMVC处理静态资源：
            1、静态资源访问不到的原因
                优雅的REST风格的资源URL不希望带.html或.do等后缀
                若将DispatcherServlet请求映射配置为/，则SpringMVC将捕获WEB容器的所有请求，包括静态资源的请求，SpringMVC会将他们当成一个普通请求处理，因找不到对应处理器将导致错误
            2、解决
                可以在SpringMVC的配置文件中配置<mvc:default-servlet-handler/>方式解决静态资源的问题
     -->
    <script type="text/javascript" src="/statics/js/jquery-2.2.4.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $(".delete").click(function () {
                var action = $(this).data("href");
                $("#deleteForm").attr("action", action).submit();
                return false;
            });
        });
    </script>
</body>
</html>
