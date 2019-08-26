# 自定义类型转换器

**ConversionService**是Spring类型转换体系的核心接口

可以利用**ConversionServiceFactoryBean**在Spring的IOC容器中定义一个ConversionService，**Spring将自动识别出IOC容器中的ConversionService，并在Bean属性配置及SpringMVC处理方法入参绑定等场合使用它进行数据的转换**

**可通过ConversionServiceFactoryBean的converters属性注册自定义的类型转换器**

```xml
<bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
    <property name="converters">
        <set>
            <ref bean="employeeConverter"/>
        </set>
    </property>
</bean>
```

## Spring支持的转换器

Spring定义了3种类型的转换器接口，实现任意一个转换器接口都可以作为自定义转换器注册到ConversionServiceFactoryBean中：

​	Converter<S,T>：将S类型对象转为T类型对象

​	ConverterFactory：将相同系列多个“同质”Converter封装在一起，如果希望奖一种类型的对象转换为另一种类型及其子类型的对象(例如将String转换为Number及Number子类(Integer、Long、Double等)对象)可使用该转换器工厂类

​	GenericConverter：会根据源类对象及目标类对象所在的宿主类中的上下文信息进行类型转换

### 自定义转换器示例

<mvc:annotation-driven conversion-service="conversionService"/>会自动将自定义的ConversionService注册到SpringMVC的上下文中

```java
@Component
public class EmployeeConverter implements Converter<String, Employee> {

	@Override
	public Employee convert(String s) {
		if (null != s) {
			//lastName-email-gender-department.id
			String[] vals = s.split("-");
			if (vals.length == 4) {
				Employee employee = new Employee();
				employee.setLastName(vals[0]);
				employee.setEmail(vals[1]);
				employee.setGender(Integer.parseInt(vals[2]));
				Department department = new Department();
				department.setId(Integer.parseInt(vals[3]));
				employee.setDepartment(department);
				System.out.println(s + "--convert --" + employee);
				return employee;
			}
		}
		return null;
	}
}
```

```xml
<mvc:annotation-driven conversion-service="conversionService"/>

<!-- 配置ConversionService -->
<bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
    <property name="converters">
        <set>
            <ref bean="employeeConverter"/>
        </set>
    </property>
</bean>
```

# mvc:annotation-driven

`<mvc:annotation-driven/>` 会自动注册RequestMappingHandlerMapping、RequestMappingHandlerAdapter、ExceptionHandlerExceptionResolver三个Bean

还将提供以下支持：

​	支持使用ConversionService实例对表单参数进行类型转换

​	支持使用@NumberFormatannotation、@DateTimeFormat注解完成数据类型的格式化

```java
// 不加@DateTimeFormat 日期默认格式为yyyy/MM/dd
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date birth;

@NumberFormat(pattern = "#,###,###.#")
private Float salary;
```

```xml
 <mvc:annotation-driven/>
```

​	支持使用@Valid注解对JavaBean实例进行JSR303验证

​	支持使用@RequestBody和@ResponseBody注解

# @InitBinder

由@InitBinder标识的方法，可以对WebDataBinder对象进行初始化。WebDataBinder是DataBinder的子类，用于完成由表单字段到JavaBean属性的绑定

@InitBinder方法不能有返回值，它必须声明为void

@InitBinder方法的参数通常是WebDataBinder

```java
// 不自动绑定对象的roleset属性，另行处理
@InitBinder
public void initBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("roleset");
}
```

# 数据格式化

FormattingConversionServiceFactoryBean内部已经注册好了

​	NumberFormatAnnotationFormatterFactory：支持数字类型的属性使用**@NumberFormat**注解

​	JodaDateTimeFormatAnnotationFormaterFactory：支持对日期类型的属性使用**@DateTimeFormat**注解

装配了FormattingConversionServiceFactoryBean后，就可以在SpringMVC入参绑定及模型数据输出时使用注解驱动了

**`<mvc:annotation-driven/>`默认创建的ConversionService实例即为FormattingConversionServiceFactoryBean**

## 日期格式化

@DateTimeFormat注解可对java.util.Date、java.util.Calender、java.long.Long时间类型进行标注

​	pattern属性：类型为字符串。指定解析/格式化字段数据的模式，如："yyyy-MM-dd HH:mm:ss"

​	iso属性：类型为DateTimeFormat.ISO。指定解析/格式化字段数据的ISO模式，包括四种：ISO.NONE(不使用)--默认、ISO.DATE(yyyy-MM-dd)、ISO.TIME(hh:mm:ss.SSSZ)、ISO.DATE_TIME(yyyy-MM-dd hh:mm:ss.SSSZ)

​	style属性：字符串类型，通过样式指定日期的格式，由两位字符组成，第一位表示日期的格式，第二位表示时间的格式，：S：短日期/时间格式、M：中日期/j时间格式、L：长日期/时间格式、F：完整日期/时间格式、-：忽略日期或时间格式

## 数值格式化

@NumberFormat可对类似数字类型的属性进行标注，它拥有两个互斥的属性：

​	style：类型为NumberFormat.Style，用于指定样式类型，包括三种：Style.NUMBER(正常数字类型)、Style.CURRENCY(货币类型)、Style.PERCENT(百分比类型)

​	pattern：类型为String，自定义样式，如：pattern="#,###.#"

## 数据格式化

对属性对象的输入/输出进行格式化，从其本质上讲依然属于"类型转换"的范畴

Spring在格式化模块中定义了一个实现ConversionService接口的FormattingConversionService实现类，该实现类扩展了GenericConversionService，因此它既具有类型转换的功能，又具有格式化的功能

FormattingConversionService拥有一个FormattingConversionServiceFactoryBean工厂类。后者用于在Spring上下文k中构造前者

# JSR303数据校验

JSR303是Java为Bean数据合法性校验提供的标准框架，它已经包含在JavaEE6.0中

JSR303通过过在Bean属性上标注类似于@NotNull、@Max等标准的注解指定校验规则，并通过标准的验证接口对Bean进行验证

 Bean Validation 中内置的 constraint

| **Constraint**                | **详细信息**                                             |
| ----------------------------- | -------------------------------------------------------- |
| `@Null`                       | 被注释的元素必须为 `null`                                |
| `@NotNull`                    | 被注释的元素必须不为 `null`                              |
| `@AssertTrue`                 | 被注释的元素必须为 `true`                                |
| `@AssertFalse`                | 被注释的元素必须为 `false`                               |
| `@Min(value)`                 | 被注释的元素必须是一个数字，其值必须大于等于指定的最小值 |
| `@Max(value)`                 | 被注释的元素必须是一个数字，其值必须小于等于指定的最大值 |
| `@DecimalMin(value)`          | 被注释的元素必须是一个数字，其值必须大于等于指定的最小值 |
| `@DecimalMax(value)`          | 被注释的元素必须是一个数字，其值必须小于等于指定的最大值 |
| `@Size(max, min)`             | 被注释的元素的大小必须在指定的范围内                     |
| `@Digits (integer, fraction)` | 被注释的元素必须是一个数字，其值必须在可接受的范围内     |
| `@Past`                       | 被注释的元素必须是一个过去的日期                         |
| `@Future`                     | 被注释的元素必须是一个将来的日期                         |
| `@Pattern(value)`             | 被注释的元素必须符合指定的正则表达式                     |

## Hibernate Validator扩展注解

Hibernate Validator是JSR303的一个参考实现，除支持所有标准的校验注解外，它还支持以下的扩展注解·

Hibernate Validator 附加的 constraint

| **Constraint** | **详细信息**                           |
| -------------- | -------------------------------------- |
| `@Email`       | 被注释的元素必须是电子邮箱地址         |
| `@Length`      | 被注释的字符串的大小必须在指定的范围内 |
| `@NotEmpty`    | 被注释的字符串的必须非空               |
| `@Range`       | 被注释的元素必须在合适的范围内         |

## SpringMVC数据校验

Spring4.0拥有自己独立的数据校验框架，同时支持JSR303标准的校验框架

Spring在进行数据绑定时，可同时调用校验框架完成数据校验工作，**在SpringMVC中，可直接通过注解驱动的方式进行数据校验**

Spring的LocalValidatorFactoryBean即实现了Spring的Validator接口，也实现了JSR303的Validator接口，只要在Spring容器中定义一个**LocalValidatorFactoryBean**，即可将其注入到需要数据校验的Bean中

Spring本身并没有提供JSR303的实现，所以**必须将JSR303的实现者jar包放到类路径下**

**`<mvc:annotaion-driven/>` 会默认装配好一个LocalValidatorFactoryBean**，通过在处理方法的入参上注标@Valid注解即可让SpringMVC在完成数据绑定后执行数据校验的工作

在已经标注了JSR303注解的表单/命令对象前标注一个@Valid，SpringMVC框架在将请求参数绑定到该入参对象后，就会调用校验框架根据注解声明的校验规则实施校验

SpringMVC是通过处理方法签名的规约来保存校验结果的：前一个表单/命令对象的校验结果保存到随后的入参中，这个保存校验结果的入参必须是**BindingResult**或**Errors**类型，这两个类都位于org.springframework.validation包中

**需校验的Bean对象和其绑定结果对象或错误对象成对出现时，它们之间不充许声明其他入参**

Errors接口提供了获取错误信息的方法，如果getErrorCount()或getFieldErrors(String field)

BindingResult扩展了Errors接口

## 校验消息的国际化

每个属性在数据绑定和数据校验发生错误时，都会生成一个对应的FieldError对象

**当一个属性校验失败后，校验框架会为该属性生成4个消息代码，这些代码以校验注解类名为**==前缀==，**结合modelAttribute、属性名及属性类型名生成多个对应的消息代码**，例如User类中的password属性标准了一个@Pattern注解，当该属性值不满足@Pattern所定义的规则时，就会产生以下4个错误代码：

Pattern.user.password

Pattern.password

Pattern.java.long.String

Pattern

当使用SpringMVC标签显示错误消息时，SpringMVC会查看WEB上下文是否装配了对应的国际化消息，如果没有，则显示默认的错误消息，否则使用国际化消息

若数据类型转换或数据格式转换时发生错误，或该有有参数不存在，或调用处理方法时发生错误，都会在隐含模型中创建错误消息。其错误代码前缀说明如下：

required：必要的参数不存在。如@RequiredParam("param1")标注了一个入参，但是该参数不存在

typeMismatch：在数据绑定时，发生数据类型不匹配的问题

methodInvocation：SpringMVC在调用处理方法时发生了错误

```xml
<!-- 配置国际化资源文件 -->
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
    <property name="basename" value="i18n"/>
    <property name="defaultEncoding" value="UTF-8"/>
</bean>
```

