package com.example.demo.repository;

import com.example.demo.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    public Employee findEmployeeByName(String name);

    /*
    top 3 salary
    select name, salary from employee order by salary desc limit 3
     */

    /*
    select name, salary from employee
     */


}
