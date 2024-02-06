package com.employeemanagement.example.repository;

import com.employeemanagement.example.model.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(String query);

    boolean existsByName(String name);
}
