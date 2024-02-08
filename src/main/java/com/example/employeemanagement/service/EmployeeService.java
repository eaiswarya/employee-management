package com.example.employeemanagement.service;

import com.example.employeemanagement.contract.request.EmployeeRequest;
import com.example.employeemanagement.contract.response.EmployeeResponse;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.exception.EntityAlreadyExistsException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRepository.existsByName(employeeRequest.getName())) {
            throw new EntityAlreadyExistsException(employeeRequest.getName());
        }
        Employee employee = modelMapper.map(employeeRequest, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeResponse.class);
    }

    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee =
                employeeRepository
                        .findById(id)
                        .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public List<EmployeeResponse> getEmployeesByDepartment(String query) {
        List<Employee> employees = (List<Employee>) employeeRepository.findByDepartment(query);
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found for department: " + query);
        }
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());
    }
}
