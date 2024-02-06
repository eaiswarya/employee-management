package com.employeemanagement.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.employeemanagement.example.contract.request.EmployeeRequest;
import com.employeemanagement.example.contract.response.EmployeeResponse;
import com.employeemanagement.example.exception.EntityAlreadyExistsException;
import com.employeemanagement.example.model.Employee;
import com.employeemanagement.example.repository.EmployeeRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class EmployeeServiceTest {
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        employeeRepository = mock(EmployeeRepository.class);
        modelMapper = mock(ModelMapper.class);
        employeeService = new EmployeeService(employeeRepository, modelMapper);
    }

    @Test
    void testAddEmployee() {
        EmployeeRequest employeeRequest =
                new EmployeeRequest("akshay", "akshay@gmail.com", "development");
        Employee employee = new Employee();
        EmployeeResponse expectedResponse =
                new EmployeeResponse(1L, "name", "name@gmail.com", "dept");
        when(employeeRepository.existsByName(employeeRequest.getName())).thenReturn(false);
        when(modelMapper.map(employeeRequest, Employee.class)).thenReturn(employee);
        when(employeeRepository.save(any())).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeResponse.class)).thenReturn(expectedResponse);

        EmployeeResponse actualResponse = employeeService.addEmployee(employeeRequest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetEmployeeById() {
        Long id = 1L;
        EmployeeRequest employeeRequest =
                new EmployeeRequest("name", "name@gmail.com", "department");
        EmployeeResponse expectedResponse =
                new EmployeeResponse(1L, "name", "name@gmail.com", "department");
        Employee employee = new Employee();
        when(modelMapper.map(employeeRequest, Employee.class)).thenReturn(employee);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(modelMapper.map(employee, EmployeeResponse.class)).thenReturn(expectedResponse);
        EmployeeResponse actualResponse = employeeService.getEmployeeById(id);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetEmployeesByDepartment() {
        String query = "development";
        Employee employee = new Employee(1L, "employee", "employee@gmail.com", "physics");
        Employee employee1 = new Employee(2L, "employee2", "employee2@gmail.com", "maths");
        List<Employee> employees = Arrays.asList(employee, employee1);
        List<EmployeeResponse> expectedResponse =
                employees.stream()
                        .map(
                                employeeRequest ->
                                        modelMapper.map(employeeRequest, EmployeeResponse.class))
                        .collect(Collectors.toList());
        when(employeeRepository.findByDepartment(query)).thenReturn(employees);
        List<EmployeeResponse> actualResponse = employeeService.getEmployeesByDepartment(query);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetAllEmployees() {
        Employee employee = new Employee(1L, "employee", "employee@gmail.com", "physics");
        Employee employee1 = new Employee(2L, "employee2", "employee2@gmail.com", "maths");
        List<Employee> employees = Arrays.asList(employee, employee1);
        List<EmployeeResponse> expectedResponse =
                employees.stream()
                        .map(emp -> modelMapper.map(emp, EmployeeResponse.class))
                        .collect(Collectors.toList());

        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeResponse> actualResponse = employeeService.getAllEmployees();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void addEmployee_ThrowsEntityAlreadyExistsException() {
        EmployeeRequest request = new EmployeeRequest("name", "name@gmail.com", "physics");
        when(employeeRepository.existsByName(request.getName())).thenReturn(true);
        assertThrows(
                EntityAlreadyExistsException.class, () -> employeeService.addEmployee(request));
    }
}
