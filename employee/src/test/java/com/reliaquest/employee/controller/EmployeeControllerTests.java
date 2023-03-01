package com.reliaquest.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.employee.exception.ResourceNotFoundException;
import com.reliaquest.employee.model.Employee;

import com.reliaquest.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{
        Employee employee = new Employee("abc", 28, 50000);
        given(employeeService.createEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(employee.getName())))
                .andExpect(jsonPath("$.age",
                        is(employee.getAge())))
                .andExpect(jsonPath("$.salary",
                        is(employee.getSalary().intValue())));

    }

    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(new Employee("abc", 28, 30000));
        listOfEmployees.add(new Employee("xyz", 30, 20000));
        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        ResultActions response = mockMvc.perform(get("/api/v1/employees"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
        long employeeId = 1L;
        Employee employee = new Employee("abc", 28, 30000);
        given(employeeService.getEmployeeById(employeeId)).willReturn(employee);

        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", employeeId));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(employee.getName())))
                .andExpect(jsonPath("$.age", is(employee.getAge())))
                .andExpect(jsonPath("$.salary", is(employee.getSalary().intValue())));

    }

    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        long employeeId = 1L;
        given(employeeService.getEmployeeById(employeeId)).willThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", employeeId));
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_andEmployeeNotExists_thenReturn404() throws Exception{
        long employeeId = 1L;
        given(employeeService.deleteEmployee(employeeId)).willThrow(EmptyResultDataAccessException.class);

        ResultActions response = mockMvc.perform(delete("/api/v1/employees/{id}", employeeId));
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
        long employeeId = 1L;
        given(employeeService.deleteEmployee(employeeId)).willReturn("Employee deleted successfully!.");

        ResultActions response = mockMvc.perform(delete("/api/v1/employees/{id}", employeeId));
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenEmployeeName_thenReturnEmployeeObject() throws Exception{
        List<Employee> listOfEmployees = new ArrayList<>();
        Employee employee = new Employee("abc", 28, 30000);
        listOfEmployees.add(employee);
        given(employeeService.getEmployeesByNameSearch("abc")).willReturn(listOfEmployees);

        ResultActions response = mockMvc.perform(get("/api/v1/employee/search/{name}", employee.getName()));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }

    @Test
    public void givenEmployeeName_ifEmployeeNotPresent_thenReturn404() throws Exception{
        List<Employee> listOfEmployees = new ArrayList<>();
        Employee employee = new Employee("abc", 28, 30000);
        listOfEmployees.add(employee);
        given(employeeService.getEmployeesByNameSearch("abc")).willThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/api/v1/employee/search/{name}", employee.getName()));
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void get_highestSalary() throws Exception{
        given(employeeService.getHighestSalaryOfEmployees()).willReturn(50000L);

        ResultActions response = mockMvc.perform(get("/api/v1/employee/highestSalary"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is(50000)));

    }

    @Test
    public void get_topTenHighestEarningEmployeeNames() throws Exception{
        List<String> listOfEmployees = new ArrayList<>();
        listOfEmployees.add("emp1");
        listOfEmployees.add("emp2");
        listOfEmployees.add("emp3");
        listOfEmployees.add("emp4");
        listOfEmployees.add("emp5");
        listOfEmployees.add("emp6");
        listOfEmployees.add("emp7");
        listOfEmployees.add("emp8");
        listOfEmployees.add("emp9");
        listOfEmployees.add("emp10");
        given(employeeService.getTop10HighestEarningEmployeeNames()).willReturn(listOfEmployees);

        ResultActions response = mockMvc.perform(get("/api/v1/employee/topTenHighestEarningEmployeeNames"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }
}
