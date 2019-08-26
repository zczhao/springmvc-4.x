# @PathVariable映射URL绑定的占位符

带占位符的URL是Spring3.0新增的功能，该功能能在SpringMVC向REST目标挺进发展过程中具有里程碑的意义

通过@PathVariable可以将URL中占位符参数绑定到控制器处理方法的入参中：URL中的{xxx}占位符可以通过@PathVariable("xxx")绑定到操作方法的入参中

```java
@RequestMapping("/delete/{id}")
public String delete(@PathVariable("id")Integer id){
    userService.delete(id);
    return "redirect:/user/list";
}
```

```java
@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {

	public static final String SUCCESS = "success";

	/**
	 * @PathVariable 可以来映射URL中的占位符到目标方法的参数中
	 *
	 * http://localhost:8080/springmvc/testPathVariable/1
	 */
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") Integer id) {
		System.out.println("testPathVariable: " + id);
		return SUCCESS;
	}
}
```

