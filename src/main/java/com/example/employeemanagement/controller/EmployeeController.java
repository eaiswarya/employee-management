package com.example.employeemanagement.controller;

import com.example.employeemanagement.contract.request.EmployeeRequest;
import com.example.employeemanagement.contract.response.EmployeeResponse;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add")
    public EmployeeResponse addEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(employeeRequest);
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/department")
    public List<EmployeeResponse> getEmployeesByDepartment(
            @RequestParam("department") String query) {
        return employeeService.getEmployeesByDepartment(query);
    }
}
