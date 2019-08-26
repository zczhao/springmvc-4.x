# RESTful SpringMVC CRUD需求

## 1､显示所有员工信息

URI：/emps

请求方式：GET

## 2､添加员工信息

**显示添加页面**：

URI: /emp

请求方式：GET

**添加员工信息**：

URI: /emp

请求方式：POST

显示效果：完成添加，重定向到list页面

## 3､删除操作

URI：/emp/{id}

请求方式：DELETE

删除后效果：对应记录从数据表中删除

## 4､修改操作

**lastName不可修改**

**显示修改页面**

URI：/emp/{id}

请求方式：GET

**修改员工信息**

URI: /emp

请求方式：PUT

显示效果：完成修改，重定向到list页面

# 使用Spring的表单标签

通过SpringMVC的表单标签可以实现将模型数据中的属性和HTML表单元素相绑定，以实现表单数据更便捷编辑和表单的回显

## form标签

一般情况下，通过GET请求获取表单页面，而通过POST请求提交表单页面，因此获取表单页面和提交表单页面的URL是相同的，只要满足最佳条件的契约，`<form:form>`标签就无需通过action属性指定表单提交的URL

可以通过modelAttribute属性指定绑定的模型属性，若没有指定该属性，则默认从request域对象中读取command的表单bean，如果该属性值也不存在，则会发生错误

## 表单标签

form:input、form:password、form:hidden、form:textarea：对应HTML表单的text、password、hidden、texture标签

form:radiobutton：单选框组件标签，当表单bean对应的属性值和value值相等时，单选按钮被选中

form:radiobuttons：单选框组标签，用于构造多个单选框

​	items：可以是一个List、String[] 或Map

​	itemValue：指定radio的value值，可以是集合中bean的一个属性值

​	itemLabel：指定radio的label值

​	delimiter：多个单选按钮可以通过delimiter指定分割符

form:checkbox：复选框组件。用于构造单个复选框

form:checkboxs：用于构造多个复选框，使用方式同form:radiobuttons标签

orm:select：用于构造下拉框组件，使用方式同form:radiobuttons标签

form:option：下拉框选项组件标签，使用方式同form:radiobuttons标签

form:errors：显示表单组件或数据校验所对应的错误

​	<form:errors path="*"/>：显示表单所有的错误

​	<form:errors path="user*"/>：显示所有以user为前缀的属性对应的错误	

​	<form:errors path="username">：显示特定表单对像属性的错误

# 处理静态资源

优雅的REST风格的资源URL不希望带.html或.do等后缀

若将DispatcherServlet请求映射配置为/，则SpringMVC将捕获WEB容器的所有请求，包括静态资源的请求，SpringMVC会将他们当成一个普通请求处理，因找不到对应处理器将导致错误

可以在SpringMVC的配置文件中配置`<mvc:default-servlet-handler/>`方式解决静态资源的问题：

​	`<mvc:default-servlet-handler/>`将在SpringMVC上下文中定义一个DefaultServletHttpRequestHandler，它会对进入DispatchServlet的请求进行筛查，如果发现是没有经过映射的请求，就将该请求将由WEB应用服务器默认的Servlet处理，如果不是静态资源的请求，才由DispatchServlet继续处理

​	一般WEB应用服务器默认的Servlet的名称都是default。若所使用的WEB服务器的默认Servlet名称不是default，则需要通过default-servlet-name属性显示指定

# 数据绑定流程

1、SpringMVC主框架将ServletRequest对象及目标方法的入参实例传递给WebDataBinderFactory实例，以创建**DataBinder**实例对象

2、DataBinder调用装配在SpringMVC上下文中的**ConversionService**组件进行**数据类型转换，数据格式化**工作。将Servlet中的请求信息填充到入参对象中

3、调用**Validator**组件对已经绑定了请求消息的入参对象进行数据合法校验，并最终生成数据绑定结果**BindingData**对象

4、SpringMVC抽取**BindingResult**中的入参对象和校验错误对象，将它们赋给处理方法的响应入参

# 文件上传

SpringMVC为文件上传提供了直接的支持，这种支持是通过即插即用的MultipartResolver实现的。Spring用**Jakarta Commons Fileupload**技术实现了一个MultipartResolver实现类：**CommonsMultipartResolver**

SpringMVC上下文中没有装配MultipartResolver，因此默认情况下不能处理文件上传工作，如果想使用Spring的文件上传工作，需在上下文中配置MultipartResolver

## 配置MultipartResolver

defaultEncoding：必须和用户JSP的pageEncoding属性一致，以便正确解析表单的内容

为了让CommsMultipartResolver正确工作，必须先将Jakarta Commons Fileupload及Jakarta Commons IO的类包添加到类路径下

```xml
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
</dependency>
```

```xml
  <!-- 配置MultipartResolver -->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <property name="defaultEncoding" value="UTF-8"/>
    <property name="maxUploadSize" value="1024000"/>
</bean>
```

```java
/**
 * 通过流的方式上传文件
 *
 * @param desc
 * @param file
 * @return
 */
@RequestMapping("/fileUpload1")
public String fileUpload1(@RequestParam("desc") String desc, @RequestParam("file") MultipartFile file) throws IOException {
    System.out.println("desc:" + desc);
    System.out.println("OriginalFilename: " + file.getOriginalFilename());

    //用来检测程序运行时间
    long startTime = System.currentTimeMillis();

    try {
        //获取输出流
        OutputStream os = new FileOutputStream("/Users/zczhao/Documents/logs/" + new Date().getTime() + file.getOriginalFilename());
        //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
        InputStream is = file.getInputStream();
        int temp;
        //一个一个字节的读取并写入
        while ((temp = is.read()) != (-1)) {
            os.write(temp);
        }
        os.flush();
        os.close();
        is.close();

    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    System.out.println("方法一的运行时间：" + String.valueOf(endTime - startTime) + "ms");

    return "redirect:/emps";
}

/**
 * 采用file.Transto 来保存上传的文件
 *
 * @param desc
 * @param file
 * @return
 */
@RequestMapping("/fileUpload2")
public String fileUpload2(@RequestParam("desc") String desc, @RequestParam("file") MultipartFile file) throws IOException {
    System.out.println("desc:" + desc);
    System.out.println("OriginalFilename: " + file.getOriginalFilename());
    long startTime = System.currentTimeMillis();
    String path = "/Users/zczhao/Documents/logs/" + new Date().getTime() + file.getOriginalFilename();

    File newFile = new File(path);
    //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
    file.transferTo(newFile);
    long endTime = System.currentTimeMillis();
    System.out.println("方法二的运行时间：" + String.valueOf(endTime - startTime) + "ms");
    return "redirect:/emps";
}

/**
 * 采用spring提供的上传文件的方法
 *
 * @param request
 * @return
 * @throws IOException
 */
@RequestMapping("/fileUpload3")
public String fileUpload3(HttpServletRequest request) throws IOException {
    long startTime = System.currentTimeMillis();
    //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
        request.getSession().getServletContext());
    //检查form中是否有enctype="multipart/form-data"
    if (multipartResolver.isMultipart(request)) {
        //将request变成多部分request
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        //获取multiRequest 中所有的文件名
        Iterator iter = multiRequest.getFileNames();

        while (iter.hasNext()) {
            //一次遍历所有文件
            MultipartFile file = multiRequest.getFile(iter.next().toString());
            if (file != null) {
                String path = "/Users/zczhao/Documents/logs/" + file.getOriginalFilename();
                //上传
                file.transferTo(new File(path));
            }

        }

    }
    long endTime = System.currentTimeMillis();
    System.out.println("方法三的运行时间：" + String.valueOf(endTime - startTime) + "ms");
    return "redirect:/emps";
}
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CRUD</title>
</head>
<body>
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
```

