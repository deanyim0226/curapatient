package com.example.demo.controller;

import com.example.demo.domain.Employee;
import com.example.demo.model.request.PageableRequest;
import com.example.demo.model.response.PageableResponse;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.UserService;
import com.example.demo.util.Validate;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@CrossOrigin
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    Validate validate;

    @PostMapping("/getPagedEmployees")
    public ResponseEntity<?> getPagedEmployees(HttpServletRequest exchange, @RequestBody PageableRequest request){
        String apikey = exchange.getHeader(UserService.AUTH_APIKEY_HEADER_NAME);

        if(!validate.isValidKey(apikey)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Pageable pageable = null;
        pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by(request.getSortBy()));

        Page<Employee> pagedEmployees = employeeService.findEmployees(pageable);
        List<Employee> employeeList = pagedEmployees.getContent();

        PageableResponse response = new PageableResponse();
        response.setEmployeeList(employeeList);
        response.setTotalPages(pagedEmployees.getTotalPages());
        response.setPageSize(request.getPageSize());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getEmployeeByName")
    public ResponseEntity<?> getEmployeeByName(HttpServletRequest exchange, @RequestParam String name){
        String apikey = exchange.getHeader(UserService.AUTH_APIKEY_HEADER_NAME);

        if(!validate.isValidKey(apikey)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Employee retrievedEmployee = employeeService.findEmployeeByName(name);
        if(retrievedEmployee == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(retrievedEmployee);
    }

    @GetMapping("/getEmployeeById")
    public ResponseEntity<?> getEmployeeById(HttpServletRequest exchange, @RequestParam Integer id){

        String apikey = exchange.getHeader(UserService.AUTH_APIKEY_HEADER_NAME);

        if(!validate.isValidKey(apikey)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

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
    public ResponseEntity<?> getAllEmployees(HttpServletRequest exchange){
        String apikey = exchange.getHeader(UserService.AUTH_APIKEY_HEADER_NAME);

        if(!validate.isValidKey(apikey)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Employee> employeeList = employeeService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(employeeList);
    }

    @PostMapping("/saveEmployee")
    public ResponseEntity<?> saveEmployee(HttpServletRequest exchange, @RequestBody Employee employee){
        String apikey = exchange.getHeader(UserService.AUTH_APIKEY_HEADER_NAME);

        if(!validate.isValidKey(apikey)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Employee savedEmployee = employeeService.saveEmployee(employee);

        if(savedEmployee == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(savedEmployee);
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(HttpServletRequest exchange, @RequestBody Employee employee){
        String apikey = exchange.getHeader(UserService.AUTH_APIKEY_HEADER_NAME);

        if(!validate.isValidKey(apikey)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        if(!validate.isValidId(employee.getEmployee_id())){
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
    public ResponseEntity<?> deleteEmployee(HttpServletRequest exchange, @RequestParam Integer id){
        String apikey = exchange.getHeader(UserService.AUTH_APIKEY_HEADER_NAME);

        if(!validate.isValidKey(apikey)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

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
