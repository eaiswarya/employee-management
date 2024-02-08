package com.example.employeemanagement.contract.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private String department;
}
