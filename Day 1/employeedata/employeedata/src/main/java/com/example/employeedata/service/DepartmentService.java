package com.example.employeedata.service;

import com.example.employeedata.dto.DepartmentDTO;
import com.example.employeedata.model.Department;
import com.example.employeedata.model.Employee;
import com.example.employeedata.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department create(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Department getById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    public Department update(Long id, Department updatedDepartment) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        existing.setName(updatedDepartment.getName());
        return departmentRepository.save(existing);
    }

    public void delete(Long id) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        departmentRepository.delete(existing);
    }

    public DepartmentDTO getDepartmentDTOById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        List<String> employeeIds = department.getEmployees().stream()
                .map(Employee::getEmpId)
                .collect(Collectors.toList());

        return new DepartmentDTO(department.getId(), department.getName(), employeeIds);
    }
}
