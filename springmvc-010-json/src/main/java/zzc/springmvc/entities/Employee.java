package zzc.springmvc.entities;

import java.util.Date;


public class Employee {
	private Integer id;
	private String lastName;

	private String email;
	private int gender;

	// 不加@DateTimeFormat 日期默认格式为yyyy/MM/dd
	private Date birth;

	private Float salary;

	private Department department;

	public Employee() {
	}

	public Employee(Integer id, String lastName, String email, int gender, Department department) {
		this.id = id;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", gender=" + gender +
				", birth=" + birth +
				", salary=" + salary +
				", department=" + department +
				'}';
	}
}
