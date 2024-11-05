package com.example.demo.service;

import com.example.demo.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    ////////////////JPA/////////////////

    public Employee findEmployeeByName(String name);
    public Employee findEmployeeById(Integer id);
    public List<Employee> getAll();
    public Employee updateEmployee(Employee employee);
    public Employee deleteEmployee(Integer id);
    public Employee saveEmployee(Employee newEmployee);

    public Page<Employee> findEmployees(Pageable pageable);

    //////////////JDBC//////////////

    public Employee findEmployeeByNameUsingJdbc(String name);
    public Employee findEmployeeByIdUsingJdbc(Integer id);
    public List<Employee> getAllUsingJdbc();
    public Employee updateEmployeeUsingJdbc(Employee employee);
    public Employee deleteEmployeeUsingJdbc(Integer id);
    public Employee saveEmployeeUsingJdbc(Employee newEmployee);
}
