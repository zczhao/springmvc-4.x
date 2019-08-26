# 处理JSON

## 1､添加依赖

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```

## 2､编写目标方法，使用其返回JSON对应的对象或集合

```java
@RequestMapping("/testJson")
public Collection<Employee> testJson(){
    return employeeDao.getEmployees();
}
```

## 3､在方法上添加@ResponseBody注解

```java
@ResponseBody
@RequestMapping("/testJson")
public Collection<Employee> testJson(){
    return employeeDao.getEmployees();
}
```

# HttpMessageConverter<T>

HttpMessageConverter<T>是Spring3.0新添加的一个拉口，负责将请求信息转换为一个对象(类型为T)，将对象(类型为T)输出为响应信息

HttpMessageConverter<T>接口定义的方法：

boolean canRead(Class<?> clazz, MediaType mediaType); 指定转换器可以读取的对象类型，即转换器是否可将请求信息转换为clazz类型的对象，同时指定支持MIME类型(text/html、application/json等)

boolean canWrite(Class<?> clazz, MediaType mediaType);指定转换器是否可将clazz类型的对象写到响应流中，响应流支持的媒体类型在MediaType中定义

List<MediaType> getSupportedMediaTypes();该转换器支持的媒体类型

T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
​			throws IOException, HttpMessageNotReadableException;将请求信息流转换为T类型的对象

void write(T t, MediaType contentType, HttpOutputMessage outputMessage)
​			throws IOException, HttpMessageNotWritableException;将T类型的对象写到响应流中，同时指定相应的媒体类型为contentType

使用HttpMessageConverter<T>将请示信息转化并绑定到处理方法的入参中，或将响应结果转为对应类型的响应信息，Spring提供了两种途径：

​	使用**@RequestBody/@ResponseBody**对处理方法进行标注

​	使用**HttpEntity<T>/ResponseEntity<T>**作为处理方法的入参或返回值

当控制器处理方法用到@RequestBody/@ResponseBody或HttpEntity<T>/ResponseEntity<T>时，Spring首先根据请示头或响应头的Accept属性选择匹配的HttpMessageConverter，进而根据参数类型或泛型类型的过滤得到匹配的HttpMessageConverter,若找不到可用的HttpMessageConverter将报错

@RequestBody和@ResponseBody不需要成对出现