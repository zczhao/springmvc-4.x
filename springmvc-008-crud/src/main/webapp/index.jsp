<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CRUD</title>
</head>
<body>
员工列表页：<a href="/emps">List All Employees</a>
<hr>

员工添加页：<a href="/emp">Add New Employee</a>
<hr>

文件上传：
<form action="/fileUpload1" method="post" enctype="multipart/form-data">
    <h1>采用流的方式上传文件</h1>
    File：<input type="file" name="file"> <br>
    Desc: <input type="text" name="desc"> <br>
    <input type="submit" value="Submit">
</form>

<br>
<form action="/fileUpload2" method="post" enctype="multipart/form-data">
    <h1>采用multipart提供的file.transfer方法上传文件</h1>
    File：<input type="file" name="file"> <br>
    Desc: <input type="text" name="desc"> <br>
    <input type="submit" value="Submit">
</form>

<br>
<form action="/fileUpload3" method="post" enctype="multipart/form-data">
    <h1>使用spring mvc提供的类的方法上传文件</h1>
    File：<input type="file" name="file"> <br>
    Desc: <input type="text" name="desc"> <br>
    <input type="submit" value="Submit">
</form>


</body>
</html>
