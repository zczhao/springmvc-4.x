package zzc.springmvc.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zzc.springmvc.dao.EmployeeDao;
import zzc.springmvc.entities.Employee;

@Controller
public class SpringMVCTest {

	@Autowired
	private EmployeeDao employeeDao;

	@RequestMapping("/testConversionServiceConverter")
	public String testConverter(@RequestParam("employee") Employee employee) {
		System.out.println("save: " + employee);
		employeeDao.save(employee);
		return "redirect:/emps";
	}
}
