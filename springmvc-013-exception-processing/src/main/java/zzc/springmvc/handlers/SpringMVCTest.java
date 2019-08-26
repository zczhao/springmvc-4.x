package zzc.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpringMVCTest {

	@RequestMapping("/testExceptionHandlerExceptionResolver")
	public String testExceptionHandlerExceptionResolver(@RequestParam("i") Integer i) {
		System.out.println("result: " + (10 / i));
		return "success";
	}

	/**
	 * 1、在@ExceptionHandler方法的入参中可以加入Exception类型的参数，该参数即对应发生的异常对象
	 * 2、@ExceptionHandler方法的入参中不能传入Map，若希望把异常信息传到页面上，需要使用ModelAndView作为返回值
	 * 3、@ExceptionHandler方法标记的异常有优先级的问题
	 * 4、@ControllerAdvice：如果在当前Handler中找不到@ExceptionHandler方法来处理当前方法的异常，则将去@ControllerAdvice标记的类中查找@ExceptionHandler标记的方法来处理异常
	 */

	/*@ExceptionHandler(value = {ArithmeticException.class})
	public ModelAndView handleArithmeticException(Exception ex) {
		System.out.println("[ArithmeticException] 出异常了: " + ex);
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("exception", ex);
		return modelAndView;
	}

	@ExceptionHandler(value = {RuntimeException.class})
	public ModelAndView handleRuntimeException(Exception ex) {
		System.out.println("[RuntimeException] 出异常了: " + ex);
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("exception", ex);
		return modelAndView;
	}
	*/
	//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "测试")
	@RequestMapping("/testResponseStatusExceptionResolver")
	public String testResponseStatusExceptionResolver(@RequestParam("i") Integer i) {
		if (i.intValue() == 13) {
			throw new UserNameNotMatchPasswordException();
		}
		System.out.println("SpringMVCTest.testResponseStatusExceptionResolver");
		return "success";
	}

	@RequestMapping(value = "/testDefaultHandlerExceptionResolver", method = RequestMethod.POST)
	public String testDefaultHandlerExceptionResolver() {
		System.out.println("SpringMVCTest.testDefaultHandlerExceptionResolver");
		return "success";
	}

	/**
	 * http://localhost:8080/testSimpleMappingExceptionResolver?i=11
	 */
	@RequestMapping("/testSimpleMappingExceptionResolver")
	public String testSimpleMappingExceptionResolver(@RequestParam("i") Integer i){
		String[] vals = new String[10];
		System.out.println(vals[i]);
		return "success";
	}
}
