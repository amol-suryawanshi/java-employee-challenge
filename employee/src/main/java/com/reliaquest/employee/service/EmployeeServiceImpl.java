package com.reliaquest.employee.service;

import com.reliaquest.employee.exception.ResourceNotFoundException;
import com.reliaquest.employee.model.Employee;
import com.reliaquest.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByNameSearch(String name) {
        return employeeRepository.findByName(name);
    }

    public Employee getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return employee;
    }

    public Long getHighestSalaryOfEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        Optional<Employee> employee = employees.stream().max(Comparator.comparingLong(Employee::getSalary));
        return employee.map(Employee::getSalary).orElseThrow(() -> new ResourceNotFoundException("Employee with highest salary not found"));
    }

    public List<String> getTop10HighestEarningEmployeeNames() {
        List<Employee> employees = employeeRepository.findAll();
        Map<Long, String> empMap = employees.stream()
                .collect(Collectors.groupingBy(Employee::getSalary,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)), value -> value.get().getName())));
        return new ArrayList<>(new TreeMap<>(empMap).descendingMap().values()).subList(0, 10);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public String deleteEmployee(Long id) {

        employeeRepository.deleteById(id);
        return "Employee deleted successfully!.";
    }
}