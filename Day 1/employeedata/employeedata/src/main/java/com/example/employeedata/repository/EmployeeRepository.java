package com.example.employeedata.repository;

import com.example.employeedata.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmpId(String empId);
    void deleteByEmpId(String empId);
}
