package com.example.demo.repository;


import com.example.demo.domain.Employee;
import com.mysql.cj.jdbc.MysqlParameterMetadata;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class EmployeeJdbcRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String UPDATE_EMPLOYEE = "UPDATE employee SET ";

    public Employee updateEmployee(Employee employee){
        Employee updatedEmployee = null;
        Integer employeeId = employee.getEmployee_id();
        String employeeName = employee.getName();
        String phoneNumber = employee.getPhone_number();
        String supervisor = employee.getSupervisors();

        StringBuilder sql = new StringBuilder(UPDATE_EMPLOYEE);
        MapSqlParameterSource source = new MapSqlParameterSource();
        boolean hasUpdates = false;

        if(employeeName != null && !employeeName.isEmpty() ){
            sql.append("name = :employeeName ");
            source.addValue("employeeName", employeeName);
            hasUpdates = true;
        }

        if(phoneNumber != null && !phoneNumber.isEmpty() ){
            if (hasUpdates) {
                sql.append(", ");
            }

            sql.append("phone_number = :phoneNumber ");
            source.addValue("phoneNumber", phoneNumber);
            hasUpdates = true;
        }

        if(supervisor != null && !supervisor.isEmpty() ){
            if (hasUpdates) {
                sql.append(", ");
            }

            sql.append("supervisors = :supervisor ");
            source.addValue("supervisor", supervisor);
            hasUpdates = true;
        }

        if (hasUpdates) {
            sql.append(" WHERE employee_id = :employeeId");
            source.addValue("employeeId", employeeId);

            try {
                jdbcTemplate.update(sql.toString(), source);

                updatedEmployee = findEmployeeById(employeeId);
                return updatedEmployee;

            } catch (DataAccessException e) {
                throw e;
            }
        }

        updatedEmployee = findEmployeeById(employeeId);
        return updatedEmployee;
    }

    public Employee deleteEmployee(Integer id){
        String sql =
                "DELETE FROM employee " +
                        "WHERE employee_id = :id";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id",id);

        try{

            Employee deletedEmployee = findEmployeeById(id);
            this.jdbcTemplate.update(sql,source);
            return deletedEmployee;

        }catch (DataAccessException e){
            throw e;
        }
    }

    public Employee saveEmployee(Employee employee){

        String sql =
                "INSERT INTO employee (employee_id,name,phone_number,supervisors)" +
                        "VALUES (:employee_id, :name, :phone_number,:supervisors);";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("employee_id", employee.getEmployee_id(), Types.INTEGER);
        source.addValue("name", employee.getName(), Types.VARCHAR);
        source.addValue("phone_number", employee.getPhone_number(), Types.VARCHAR);
        source.addValue("supervisors", employee.getSupervisors(), Types.VARCHAR);

        try {
            this.jdbcTemplate.update(sql,source);
            return employee;

        }catch (DataAccessException e){
            throw e;
        }
    }

    public Employee findEmployeeById(Integer employeeId){

        String sql  =
                "SELECT employee_id, name, phone_number, supervisors " +
                        "FROM employee " +
                        "WHERE employee_id = :employeeId";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("employeeId", employeeId);

        try{
            Employee retrievedEmployee = this.jdbcTemplate.queryForObject(sql, source, ((rs, rowNum) -> {

                Employee employee = new Employee();
                employee.setEmployee_id(rs.getInt("employee_id"));
                employee.setSupervisors(rs.getString("supervisors"));
                employee.setName(rs.getString("name"));
                employee.setPhone_number(rs.getString("phone_number"));
                return employee;
            }));
            return retrievedEmployee;
        }catch (DataAccessException e){
            throw e;
        }

    }
    public Employee findEmployeeByName(String username){

        String sql =
                "SELECT employee_id, name, phone_number, supervisors " +
                        "FROM employee " +
                        "WHERE name = :username";
        MapSqlParameterSource source = new MapSqlParameterSource();

        source.addValue("username",username);

        try{
            Employee retrievedEmployee = this.jdbcTemplate.queryForObject(sql,source,(rs, rowNum) -> {

                Employee employee = new Employee();
                employee.setEmployee_id(rs.getInt("employee_id"));
                employee.setName(rs.getString("name"));
                employee.setPhone_number(rs.getString("phone_number"));
                employee.setSupervisors(rs.getString("supervisors"));

                return employee;
            });

            return retrievedEmployee;

        }catch (DataAccessException e){
            throw e;
        }
    }

    public List<Employee> findAll(){
        String sql =
                "SELECT * FROM EMPLOYEE";

        MapSqlParameterSource source = new MapSqlParameterSource();
        /*
        hold named parameters for SQL query
         */

        try{
            List<Employee> employeeList = this.jdbcTemplate.query(sql, source, (rs, rowNum) ->{

                /*
                to map each row in the result set to an Employee object
                rs is the resultset object represent the current row from query results
                 */
                Employee employee = new Employee();
                employee.setEmployee_id(rs.getInt("employee_id"));
                employee.setName(rs.getString("name"));
                employee.setPhone_number(rs.getString("phone_number"));
                employee.setSupervisors(rs.getString("supervisors"));

                return employee;
            });

            return employeeList;

        }catch (DataAccessException e){
            throw e;
        }
    }
}
