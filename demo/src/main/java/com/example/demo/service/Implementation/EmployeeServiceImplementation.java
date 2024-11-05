package com.example.demo.service.Implementation;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeJdbcRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImplementation implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeJdbcRepository employeeJdbcRepository;


    ////////////////////////////////////JPA/////////////////////////////////////////
    @Override
    public Employee findEmployeeByName(String name) {

        Employee retrievedEmployee = employeeRepository.findEmployeeByName(name);
        return retrievedEmployee;
    }

    @Override
    public Employee findEmployeeById(Integer id) {
        Employee retrievedEmployee = employeeRepository.findById(id).orElse(null);
        return retrievedEmployee;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> retrievedEmployees = employeeRepository.findAll();
        return retrievedEmployees;
    }

    @Override
    public Employee updateEmployee(Employee employee) {

        Employee retrievedEmployee = employeeRepository.findById(employee.getEmployee_id()).orElse(null);

        if(retrievedEmployee != null){
            String employeeName = employee.getName();
            String phoneNumber = employee.getPhone_number();
            String supervisor = employee.getSupervisors();

            if(employeeName != null && !employeeName.isEmpty() ){
                retrievedEmployee.setName(employeeName);
            }

            if(phoneNumber != null && !phoneNumber.isEmpty() ){
                retrievedEmployee.setPhone_number(phoneNumber);
            }

            if(supervisor != null && !supervisor.isEmpty() ){
                retrievedEmployee.setSupervisors(supervisor);
            }

            Employee updatedEmployee = employeeRepository.save(retrievedEmployee);
            return updatedEmployee;
        }

        return null;
    }

    @Override
    public Employee deleteEmployee(Integer id) {
        Employee retrievedEmployee = employeeRepository.findById(id).orElse(null);
        if(retrievedEmployee != null){
            employeeRepository.delete(retrievedEmployee);
            return retrievedEmployee;
        }
        return null;
    }

    @Override
    public Employee saveEmployee(Employee newEmployee) {
        Integer id = newEmployee.getEmployee_id();

        Employee retrievedEmployee = employeeRepository.findById(id).orElse(null);
        if(retrievedEmployee != null){
            return null;
        }

        Employee employee = employeeRepository.save(newEmployee);
        return employee;
    }

    @Override
    public Page<Employee> findEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    ////////////////////////////////////JDBC TEMPLATE/////////////////////////////////////////
    @Override
    public Employee findEmployeeByNameUsingJdbc(String name) {
        Employee retrievedEmployee = employeeJdbcRepository.findEmployeeByName(name);
        return retrievedEmployee;
    }

    @Override
    public Employee findEmployeeByIdUsingJdbc(Integer id) {
        Employee retrievedEmployee = employeeJdbcRepository.findEmployeeById(id);
        return retrievedEmployee;
    }

    @Override
    public List<Employee> getAllUsingJdbc() {
        List<Employee> employeeList = employeeJdbcRepository.findAll();
        return employeeList;
    }

    @Override
    public Employee updateEmployeeUsingJdbc(Employee employee) {

        return employeeJdbcRepository.updateEmployee(employee);
    }

    @Override
    public Employee deleteEmployeeUsingJdbc(Integer id) {
        return employeeJdbcRepository.deleteEmployee(id);
    }

    @Override
    public Employee saveEmployeeUsingJdbc(Employee newEmployee) {
        return employeeJdbcRepository.saveEmployee(newEmployee);
    }
}
