package com.employeemanagement.example.contract.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmployeeRequest {
    @NotBlank(message = "name must include three characters")
    private String name;

    @Email(message = "email should not be empty")
    private String email;

    private String department;
}
