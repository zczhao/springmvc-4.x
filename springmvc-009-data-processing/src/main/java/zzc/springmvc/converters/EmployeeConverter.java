package zzc.springmvc.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import zzc.springmvc.entities.Department;
import zzc.springmvc.entities.Employee;

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
