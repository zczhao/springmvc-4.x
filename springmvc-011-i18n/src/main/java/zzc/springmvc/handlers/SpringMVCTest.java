package zzc.springmvc.handlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;


@Controller
public class SpringMVCTest {

	@Autowired
	private ResourceBundleMessageSource messageSource;

	@RequestMapping("/i18n")
	public String testI18n(Locale locale){
		String val = messageSource.getMessage("i18n.user", null, locale);
		System.out.println("val = " + val);
		return "i18n";
	}


	@RequestMapping("/i18n2")
	public String testI18n2(){
		return "i18n2";
	}

}
