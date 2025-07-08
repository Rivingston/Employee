package com.example.employeedata.service;
import com.example.employeedata.dto.CreateEmployeeDTO;
import com.example.employeedata.dto.EmployeeDTO;
import com.example.employeedata.model.Employee;
import com.example.employeedata.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmpId(employee.getEmpId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        return dto;
    }

    private Employee mapToEntity(CreateEmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setEmpId(dto.getEmpId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());
        return employee;
    }

    // 🔧 Create
    public EmployeeDTO create(CreateEmployeeDTO dto) {
        Employee employee = mapToEntity(dto);
        Employee saved = repo.save(employee);
        return mapToDTO(saved);
    }

    // 📋 Read All
    public List<EmployeeDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔍 Read by empId
    public EmployeeDTO getByEmpId(String empId) {
        Employee employee = repo.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + empId));
        return mapToDTO(employee);
    }

    // ✏️ Update name/email
    public EmployeeDTO update(String empId, EmployeeDTO dto) {
        Employee employee = repo.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + empId));
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        Employee updated = repo.save(employee);
        return mapToDTO(updated);
    }

    // ❌ Delete
    public void delete(String empId) {
        Employee employee = repo.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + empId));
        repo.delete(employee);
    }
}
