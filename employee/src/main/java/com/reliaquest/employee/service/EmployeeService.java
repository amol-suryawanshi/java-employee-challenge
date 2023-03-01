package com.reliaquest.employee.service;

import com.reliaquest.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface EmployeeService {

    List<Employee> getAllEmployees();

    List<Employee> getEmployeesByNameSearch(String name);

    Employee getEmployeeById(Long id);

    Long getHighestSalaryOfEmployees();

    List<String> getTop10HighestEarningEmployeeNames();

    Employee createEmployee(Employee employee);

    String deleteEmployee(Long id);
}