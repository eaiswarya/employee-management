package com.employeemanagement.example.service;

import com.employeemanagement.example.contract.request.EmployeeRequest;
import com.employeemanagement.example.contract.response.EmployeeResponse;
import com.employeemanagement.example.exception.EntityAlreadyExistsException;
import com.employeemanagement.example.model.Employee;
import com.employeemanagement.example.repository.EmployeeRepository;
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
                        .orElseThrow(() -> new RuntimeException("employee not found"));
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public List<EmployeeResponse> getEmployeesByDepartment(String query) {
        List<Employee> employees = (List<Employee>) employeeRepository.findByDepartment(query);
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());
    }
}
