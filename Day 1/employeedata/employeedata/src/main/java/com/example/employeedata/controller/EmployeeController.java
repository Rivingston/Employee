package com.example.employeedata.controller;

import com.example.employeedata.dto.CreateEmployeeDTO;
import com.example.employeedata.dto.EmployeeDTO;
import com.example.employeedata.dto.GetEmployeeDTO;
import com.example.employeedata.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public EmployeeDTO create(@RequestBody @Valid CreateEmployeeDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<EmployeeDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{empId}")
    public ResponseEntity<GetEmployeeDTO> getByEmpId(@PathVariable String empId) {
        GetEmployeeDTO dto = service.getByEmpId(empId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{empId}")
    public EmployeeDTO update(@PathVariable String empId, @RequestBody @Valid EmployeeDTO dto) {
        return service.update(empId, dto);
    }

    @DeleteMapping("/{empId}")
    public void delete(@PathVariable String empId) {
        service.delete(empId);
    }

    @PostMapping("/{empId}/projects/{projectId}")
    public ResponseEntity<String> assignProjectToEmployee(
            @PathVariable String empId,
            @PathVariable Long projectId) {
        service.assignProject(empId, projectId);
        return ResponseEntity.ok("Project assigned successfully.");
    }

    @DeleteMapping("/{empId}/projects/{projectId}")
    public ResponseEntity<String> removeProjectFromEmployee(
            @PathVariable String empId,
            @PathVariable Long projectId) {
        service.removeProject(empId, projectId);
        return ResponseEntity.ok("Project removed successfully.");
    }
}
