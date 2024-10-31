package com.example.demo.service;

import com.example.demo.domain.Employee;

import java.util.List;

public interface EmployeeService {

    public Employee findEmployeeByName(String name);
    public Employee findEmployeeById(Integer id);
    public List<Employee> getAll();
    public Employee updateEmployee(Employee employee);
    public Employee deleteEmployee(Integer id);
    public Employee saveEmployee(Employee newEmployee);


    public Employee findEmployeeByNameUsingJdbc(String name);
    public Employee findEmployeeByIdUsingJdbc(Integer id);
    public List<Employee> getAllUsingJdbc();
    public void updateEmployeeUsingJdbc(Employee employee);
    public void deleteEmployeeUsingJdbc(Integer id);
    public void saveEmployeeUsingJdbc(Employee newEmployee);
}
