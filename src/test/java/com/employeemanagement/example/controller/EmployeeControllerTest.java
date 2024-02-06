package com.employeemanagement.example.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.employeemanagement.example.contract.request.EmployeeRequest;
import com.employeemanagement.example.contract.response.EmployeeResponse;
import com.employeemanagement.example.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private EmployeeService employeeService;

    @Test
    void testAddEmployee() throws Exception {

        EmployeeRequest request = new EmployeeRequest("name", "name@gmail.com", "dept");
        EmployeeResponse expectedResponse =
                new EmployeeResponse(1L, "name1", "email@gmail.com", "dept1");

        when(employeeService.addEmployee(any(EmployeeRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/employees/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Long id = 1L;
        EmployeeResponse expectedResponse = new EmployeeResponse();
        when(employeeService.getEmployeeById(id)).thenReturn(expectedResponse);
        mockMvc.perform(get("/employees/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testGetEmployeesByDepartment() throws Exception {
        String query = "department";
        List<EmployeeResponse> expectedResponse = new ArrayList<>();
        when(employeeService.getEmployeesByDepartment(query)).thenReturn(expectedResponse);
        mockMvc.perform(get("/employees/department?department=" + query))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
}
