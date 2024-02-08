package com.example.employeemanagement.contract.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmployeeRequest {
    @NotBlank(message = "name should not be blank")
    private String name;

    @Email
    @NotBlank(message = "email should not be empty")
    private String email;

    @NotBlank(message = "department should not be empty")
    private String department;
}
