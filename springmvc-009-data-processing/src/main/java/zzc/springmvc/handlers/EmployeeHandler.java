package zzc.springmvc.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import zzc.springmvc.dao.DepartmentDao;
import zzc.springmvc.dao.EmployeeDao;
import zzc.springmvc.entities.Employee;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class EmployeeHandler {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private DepartmentDao departmentDao;


	@ModelAttribute
	public void getEmployee(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
		if (null != id) {
			map.put("employee", employeeDao.get(id));
		}
	}

	/**
	 * 列表页
	 */
	@RequestMapping(value = "/emps", method = RequestMethod.GET)
	public String list(Map<String, Object> map) {
		map.put("employees", employeeDao.getEmployees());
		return "list";
	}

	/**
	 * 跳转到添加页
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("departments", departmentDao.getDepartments());
		map.put("employee", new Employee());
		return "input";
	}

	/**
	 * 新增员工信息
	 * <p>
	 * BindingResult 类型转换失败的信息
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public String save(@Valid Employee employee, BindingResult result,Map<String, Object> map) {
		if (result.getErrorCount() > 0) {
			System.out.println("出错了！");

			for (FieldError error : result.getFieldErrors()) {
				System.out.println(error.getField() + ":" + error.getDefaultMessage());
			}

			// 若验证出错，则转向定制的页面
			map.put("departments", departmentDao.getDepartments());
			return "input";
		}
		System.out.println("save: " + employee);
		employeeDao.save(employee);
		return "redirect:/emps";
	}

	/**
	 * 删除员工
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id) {
		employeeDao.delete(id);
		return "redirect:/emps";
	}

	/**
	 * 跳转到编辑页
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		map.put("employee", employeeDao.get(id));
		map.put("departments", departmentDao.getDepartments());
		return "input";
	}

	/**
	 * 修改员工信息
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.PUT)
	public String update(@ModelAttribute("employee") Employee employee) {
		employeeDao.save(employee);
		return "redirect:/emps";
	}

	/**
	 * 不自动绑定对象的lastName属性，另行处理
	 */
	/*
	@InitBinder
	public void initBinder(WebDataBinder dataBinder){
		dataBinder.setDisallowedFields("lastName");
	}
	*/
}
