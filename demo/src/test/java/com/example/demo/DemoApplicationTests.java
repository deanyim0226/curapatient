package com.example.demo;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeJdbcRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.util.Validate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@MockBean
	private Validate validate;

	private static final String API_KEY = "hardcodedsecurityapikey";
	private static final String INVALID_API_KEY = "hardcodedsecurityinvalidapikey";
	private static final String HEADER = "x-api-key";

	private Employee employee;

	@BeforeEach
	void setUp(){
		employee = new Employee(1, "Daniel", "310-824-3365", "Sam");
		when(validate.isValidKey(INVALID_API_KEY)).thenReturn(false);
		when(validate.isValidKey(API_KEY)).thenReturn(true);
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void getEmployeeByName() throws Exception {

		when(employeeService.findEmployeeByName("Daniel")).thenReturn(employee);

		mockMvc.perform(get("/getEmployeeByName")
						.header(HEADER, API_KEY)
						.queryParam("name", "Daniel")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employee_id").value(1))
				.andExpect(jsonPath("$.phone_number").value("310-824-3365"))
				.andExpect(jsonPath("$.supervisors").value("Sam"));
	}

	@Test
	public void getEmployeeByName_InvalidId_NotFound() throws Exception {

		when(employeeService.findEmployeeByName("Daniel")).thenReturn(null);

		mockMvc.perform(get("/getEmployeeByName")
						.header(HEADER, API_KEY)
						.queryParam("name", "Daniel")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
	@Test
	public void getEmployeeByName_InvalidKey_Unauthorized() throws Exception {

		mockMvc.perform(get("/getEmployeeByName")
						.header(HEADER, INVALID_API_KEY)
						.queryParam("name", "Daniel")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isUnauthorized());
	}
	@Test
	public void getEmployeeById() throws Exception{

		when(employeeService.findEmployeeById(1)).thenReturn(employee);
		when(validate.isValidId(1)).thenReturn(true);

		mockMvc.perform(get("/getEmployeeById")
						.header(HEADER, API_KEY)
						.queryParam("id", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Daniel"))
				.andExpect(jsonPath("$.phone_number").value("310-824-3365"))
				.andExpect(jsonPath("$.supervisors").value("Sam"));
	}

	@Test
	public void getEmployeeById_InvalidKey_Unauthorized() throws Exception {

		mockMvc.perform(get("/getEmployeeById")
						.header(HEADER, INVALID_API_KEY)
						.queryParam("id", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isUnauthorized());

	}
	@Test
	public void getEmployeeById_InvalidId_BadRequest() throws Exception{
		when(validate.isValidId(-1)).thenReturn(false);

		mockMvc.perform(get("/getEmployeeById")
						.header(HEADER, API_KEY)
						.queryParam("id", "-1")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest());

	}
	@Test
	public void getEmployeeById_InvalidId_NotFound() throws Exception{

		when(employeeService.findEmployeeByName("Daniel")).thenReturn(null);
		when(validate.isValidId(1)).thenReturn(true);

		mockMvc.perform(get("/getEmployeeById")
						.header(HEADER, API_KEY)
						.queryParam("id", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());

	}

	@Test
	public void getAllEmployees() throws Exception{

		List<Employee> employeeList = Arrays.asList(employee, new Employee(2,"Donghyeon Yim","213-999-6350","Sam"), new Employee(3,"Dean","999-911-1234","CEO"));
		when(employeeService.getAll()).thenReturn(employeeList);

		mockMvc.perform(get("/getAllemployees")
						.header(HEADER, API_KEY)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3))

				.andExpect(jsonPath("$[1].employee_id").value(2))
		    	.andExpect(jsonPath("$[1].name").value("Donghyeon Yim"))
				.andExpect(jsonPath("$[1].phone_number").value("213-999-6350"))
				.andExpect(jsonPath("$[1].supervisors").value("Sam"))

				.andExpect(jsonPath("$[2].employee_id").value(3))
				.andExpect(jsonPath("$[2].name").value("Dean"))
				.andExpect(jsonPath("$[2].phone_number").value("999-911-1234"))
				.andExpect(jsonPath("$[2].supervisors").value("CEO"));
	}

	@Test
	public void getAllEmployees_InvalidKey_Unauthorized() throws Exception{

		List<Employee> employeeList = Arrays.asList(employee, new Employee(2,"Donghyeon Yim","213-999-6350","Sam"), new Employee(3,"Dean","999-911-1234","CEO"));
		when(employeeService.getAll()).thenReturn(employeeList);

		mockMvc.perform(get("/getAllemployees")
						.header(HEADER, INVALID_API_KEY)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void saveEmployee() throws Exception{
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);
		mockMvc.perform(post("/saveEmployee")
						.header(HEADER, API_KEY)
						.content("{\"employee_id\":\"1\",\"name\":\"Daniel\",\"phone_number\":\"310-824-3365\",\"supervisors\":\"Sam\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.name").value("Daniel"))
				.andExpect(jsonPath("$.phone_number").value("310-824-3365"))
				.andExpect(jsonPath("$.supervisors").value("Sam"));
	}

	@Test
	public void saveEmployee_InvalidKey_Unauthorized() throws Exception{
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);
		mockMvc.perform(post("/saveEmployee")
						.header(HEADER, INVALID_API_KEY)
						.content("{\"employee_id\":\"1\",\"name\":\"Daniel\",\"phone_number\":\"310-824-3365\",\"supervisors\":\"Sam\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void saveEmployee_InvalidId_Conflict() throws Exception{
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(null);

		mockMvc.perform(post("/saveEmployee")
						.header(HEADER, API_KEY)
						.content("{\"employee_id\":\"1\",\"name\":\"Daniel\",\"phone_number\":\"310-824-3365\",\"supervisors\":\"Sam\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}

	@Test
	public void updateEmployee() throws Exception{
		when(employeeService.updateEmployee(any(Employee.class))).thenReturn(new Employee(1,"Daniel","213-999-6350","Sam"));
		when(validate.isValidId(1)).thenReturn(true);

		mockMvc.perform(put("/updateEmployee")
						.header(HEADER, API_KEY)
						.content("{\"employee_id\":\"1\",\"name\":\"Daniel\",\"phone_number\":\"213-999-6350\",\"supervisors\":\"Sam\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.name").value("Daniel"))
				.andExpect(jsonPath("$.phone_number").value("213-999-6350"))
				.andExpect(jsonPath("$.supervisors").value("Sam"));
	}

	@Test
	public void updateEmployee_InvalidKey_Unauthorized() throws Exception{
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(null);
		mockMvc.perform(put("/updateEmployee")
						.header(HEADER, INVALID_API_KEY)
						.content("{\"employee_id\":\"1\",\"name\":\"Daniel\",\"phone_number\":\"213-999-6350\",\"supervisors\":\"Sam\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void updateEmployee_InvalidId_BadRequest() throws Exception{
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(null);
		mockMvc.perform(put("/updateEmployee")
						.header(HEADER, API_KEY)
						.content("{\"employee_id\":\"-1\",\"name\":\"Daniel\",\"phone_number\":\"213-999-6350\",\"supervisors\":\"Sam\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updateEmployee_InvalidId_NotFound() throws Exception{
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(null);
		when(validate.isValidId(1)).thenReturn(true);
		mockMvc.perform(put("/updateEmployee")
						.header(HEADER, API_KEY)
						.content("{\"employee_id\":\"1\",\"name\":\"Daniel\",\"phone_number\":\"213-999-6350\",\"supervisors\":\"Sam\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void deleteEmployee() throws Exception{

		when(employeeService.deleteEmployee(1)).thenReturn(employee);
		when(validate.isValidId(1)).thenReturn(true);

		mockMvc.perform(delete("/deleteEmployeeById")
						.header(HEADER, API_KEY)
						.queryParam("id", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employee_id").value(1));
	}

	@Test
	public void deleteEmployee_InvalidKey_Unauthorized() throws Exception{
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(null);
		mockMvc.perform(delete("/deleteEmployeeById")
						.header(HEADER, INVALID_API_KEY)
						.queryParam("id", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void deleteEmployee_InvalidId_BadRequest() throws Exception{
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(null);
		when(validate.isValidId(-1)).thenReturn(false);

		mockMvc.perform(delete("/deleteEmployeeById")
						.header(HEADER, API_KEY)
						.queryParam("id", "-1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteEmployee_InvalidId_NotFound() throws Exception{
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(null);
		when(validate.isValidId(2)).thenReturn(true);

		mockMvc.perform(delete("/deleteEmployeeById")
						.header(HEADER, API_KEY)
						.queryParam("id", "2")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	/*
	///////////////////////TEST CASE USING JDBC TEMPLATE/////////////////////////////
	In order for these test cases to work
	make sure to change to use JDBC for every api in employee controller

	@Test
	public void getEmployeeByNameUsingJdbc() throws Exception {

		when(employeeService.findEmployeeByNameUsingJdbc("Daniel")).thenReturn(employee);

		mockMvc.perform(get("/getEmployeeByName")
						.header(HEADER, API_KEY)
						.queryParam("name", "Daniel")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employee_id").value(1))
				.andExpect(jsonPath("$.phone_number").value("310-824-3365"))
				.andExpect(jsonPath("$.supervisors").value("Sam"));
	}

	@Test
	public void getEmployeeByIdUsingJdbc() throws Exception{

		when(employeeService.findEmployeeByIdUsingJdbc(1)).thenReturn(employee);
		when(validate.isValidId(1)).thenReturn(true);

		mockMvc.perform(get("/getEmployeeById")
						.header(HEADER, API_KEY)
						.queryParam("id", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Daniel"))
				.andExpect(jsonPath("$.phone_number").value("310-824-3365"))
				.andExpect(jsonPath("$.supervisors").value("Sam"));
	}

	@Test
	public void getAllEmployeesUsingJdbc() throws Exception{

		List<Employee> employeeList = Arrays.asList(employee, new Employee(2,"Donghyeon Yim","213-999-6350","Sam"), new Employee(3,"Dean","999-911-1234","CEO"));
		when(employeeService.getAllUsingJdbc()).thenReturn(employeeList);

		mockMvc.perform(get("/getAllemployees")
						.header(HEADER, API_KEY)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3))

				.andExpect(jsonPath("$[1].employee_id").value(2))
				.andExpect(jsonPath("$[1].name").value("Donghyeon Yim"))
				.andExpect(jsonPath("$[1].phone_number").value("213-999-6350"))
				.andExpect(jsonPath("$[1].supervisors").value("Sam"))

				.andExpect(jsonPath("$[2].employee_id").value(3))
				.andExpect(jsonPath("$[2].name").value("Dean"))
				.andExpect(jsonPath("$[2].phone_number").value("999-911-1234"))
				.andExpect(jsonPath("$[2].supervisors").value("CEO"));
	}



	@Test
	public void saveEmployeeUsingJdbc() throws Exception{
		when(employeeService.saveEmployeeUsingJdbc(any(Employee.class))).thenReturn(employee);
		mockMvc.perform(post("/saveEmployee")
						.header(HEADER, API_KEY)
						.content("{\"employee_id\":\"1\",\"name\":\"Daniel\",\"phone_number\":\"310-824-3365\",\"supervisors\":\"Sam\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.name").value("Daniel"))
				.andExpect(jsonPath("$.phone_number").value("310-824-3365"))
				.andExpect(jsonPath("$.supervisors").value("Sam"));
	}

	@Test
	public void updateEmployeeUsingJdbc() throws Exception{
		when(employeeService.updateEmployeeUsingJdbc(any(Employee.class))).thenReturn(new Employee(1,"Daniel","213-999-6350","Sam"));
		when(validate.isValidId(1)).thenReturn(true);

		mockMvc.perform(put("/updateEmployee")
						.header(HEADER, API_KEY)
						.content("{\"employee_id\":\"1\",\"name\":\"Daniel\",\"phone_number\":\"213-999-6350\",\"supervisors\":\"Sam\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.name").value("Daniel"))
				.andExpect(jsonPath("$.phone_number").value("213-999-6350"))
				.andExpect(jsonPath("$.supervisors").value("Sam"));
	}

	@Test
	public void deleteEmployeeUsingJdbc() throws Exception{

		when(employeeService.deleteEmployeeUsingJdbc(1)).thenReturn(employee);
		when(validate.isValidId(1)).thenReturn(true);

		mockMvc.perform(delete("/deleteEmployeeById")
						.header(HEADER, API_KEY)
						.queryParam("id", "1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employee_id").value(1));
	}
	*/


}
