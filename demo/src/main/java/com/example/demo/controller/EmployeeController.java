package com.example.demo.controller;

import com.example.demo.domain.Employee;
import com.example.demo.service.EmployeeService;
import com.example.demo.util.Validate;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private static final String APIKEY = "securityapikey";
    @Autowired
    EmployeeService employeeService;
    @Autowired
    Validate validate;


    @GetMapping("/getEmployeeByName")
    public ResponseEntity<?> getEmployeeByName(@RequestParam String name){
        Employee retrievedEmployee = employeeService.findEmployeeByName(name);

        if(retrievedEmployee == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(retrievedEmployee);
    }

    @GetMapping("/getEmployeeById")
    public ResponseEntity<?> getEmployeeById(@RequestParam Integer id){

        if(!validate.isValidId(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Employee retrievedEmployee = employeeService.findEmployeeById(id);

        if(retrievedEmployee == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(retrievedEmployee);
    }

    @GetMapping("/getAllemployees")
    public ResponseEntity<?> getAllEmployees(){

        List<Employee> employeeList = employeeService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(employeeList);
    }

    @PostMapping("/saveEmployee")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee){

        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.OK).body(savedEmployee);
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee){

        if(!validate.isValidId(employee.getEmployee_Id())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        employeeService.updateEmployee(employee);

        Employee updatedEmployee = employeeService.updateEmployee(employee);

        if(updatedEmployee == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @DeleteMapping("/deleteEmployeeById")
    public ResponseEntity<?> deleteEmployee(@RequestParam Integer id){

        if(!validate.isValidId(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Employee deletedEmployee = employeeService.deleteEmployee(id);

        if(deletedEmployee == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(deletedEmployee);
    }
}
