package com.spaneos.ems.restapi;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spaneos.ems.domain.Employee;
import com.spaneos.ems.service.EmployeeService;

@Component
@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmsRestService {
	@Autowired
	private EmployeeService employeeService;

	@POST
	@Path("register")
	public Employee registerEmployee(Employee employee) {
		Employee emp = employeeService.registerEmployee(employee);
		return emp;
	}

	@GET
	@Path("all")
	public List<Employee> findAll(@PathParam("email") String email) {
		return employeeService.findAll();
	}

	@GET
	@Path("email/{email}")
	public Employee findByEmail(@PathParam("email") String email) {
		return employeeService.findByEmail(email);

	}

	@GET
	@Path("mobile/{mobienumber}")
	public Employee findByMobile(@QueryParam("mobileNumber") String mobileNumber) {
		return employeeService.findByMobile(mobileNumber);
	}

	@GET
	@Path("empno/{empno}")
	public Employee findByEmpno(@PathParam("empno") Long empno) {
		return employeeService.findByEmpno(empno);
	}

	@GET
	@Path("birthdays")
	public List<Employee> findByBirthDate() {
		return employeeService.findByBirthDate();
	}

	@PUT
	@Path("update")
	public Employee updateEmployee(Employee employee) {
		return employeeService.updateEmployee(employee);

	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

}
