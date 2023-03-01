package com.reliaquest.employee.controller;

import com.reliaquest.employee.model.Employee;
import com.reliaquest.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

//@CrossOrigin(origins = "https://dummy.restapiexample.com")
@RestController
@RequestMapping("/api/v1")
public class IEmployeeController {

    private final EmployeeService employeeService;

    public IEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    ResponseEntity<List<Employee>> getAllEmployees() throws IOException{
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/employee/search/{searchString}")
    ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString){
        return ResponseEntity.ok(employeeService.getEmployeesByNameSearch(searchString));
    }

    @GetMapping("/employees/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/employee/highestSalary")
    ResponseEntity<Long> getHighestSalaryOfEmployees() {
        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
    }

    @GetMapping("/employee/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames()  {
        return ResponseEntity.ok(employeeService.getTop10HighestEarningEmployeeNames());
    }

    @PostMapping("/create")
    ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}
