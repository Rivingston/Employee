package com.example.employeedata.controller;

import com.example.employeedata.dto.CreateEmployeeDTO;
import com.example.employeedata.dto.EmployeeDTO;
import com.example.employeedata.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // 🔁 Create new employee (accepts password)
    @PostMapping
    public EmployeeDTO create(@RequestBody @Valid CreateEmployeeDTO dto) {
        return service.create(dto);
    }

    // 📋 Get all employees
    @GetMapping
    public List<EmployeeDTO> getAll() {
        return service.getAll();
    }

    // 🔍 Get employee by empId
    @GetMapping("/{empId}")
    public EmployeeDTO getByEmpId(@PathVariable String empId) {
        return service.getByEmpId(empId);
    }

    // ✏️ Update name/email (password not updated here)
    @PutMapping("/{empId}")
    public EmployeeDTO update(@PathVariable String empId, @RequestBody @Valid EmployeeDTO dto) {
        return service.update(empId, dto);
    }

    // ❌ Delete employee
    @DeleteMapping("/{empId}")
    public void delete(@PathVariable String empId) {
        service.delete(empId);
    }
}
