package zzc.springmvc.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(value = {ArithmeticException.class})
	public ModelAndView handleArithmeticException(Exception ex) {
		System.out.println("[ArithmeticException] 出异常了: " + ex);
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("exception", ex);
		return modelAndView;
	}
}
