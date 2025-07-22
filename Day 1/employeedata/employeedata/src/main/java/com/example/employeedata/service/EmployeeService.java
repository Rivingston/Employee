package com.example.employeedata.service;

import com.example.employeedata.dto.CreateEmployeeDTO;
import com.example.employeedata.dto.EmployeeDTO;
import com.example.employeedata.dto.GetEmployeeDTO;
import com.example.employeedata.dto.ProfileDTO;
import com.example.employeedata.model.Department;
import com.example.employeedata.model.Employee;
import com.example.employeedata.model.Project;
import com.example.employeedata.repository.DepartmentRepository;
import com.example.employeedata.repository.EmployeeRepository;
import com.example.employeedata.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmpId(employee.getEmpId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPassword(employee.getPassword());
        dto.setDepartmentId(employee.getDepartment().getId());
        return dto;
    }

    private Employee mapToEntity(CreateEmployeeDTO dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Invalid Department ID: " + dto.getDepartmentId()));

        Employee employee = new Employee();
        employee.setEmpId(dto.getEmpId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());
        employee.setDepartment(department);
        return employee;
    }

    private GetEmployeeDTO mapToGetDTO(Employee employee) {
        GetEmployeeDTO dto = new GetEmployeeDTO();
        dto.setEmpId(employee.getEmpId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPassword(employee.getPassword());
        dto.setDepartmentId(employee.getDepartment().getId());
        dto.setDepartmentName(employee.getDepartment().getName());

        if (employee.getProfile() != null) {
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setEmpId(employee.getProfile().getEmpId());
            profileDTO.setPhoneNo(employee.getProfile().getPhoneNo());
            profileDTO.setAddress(employee.getProfile().getAddress());
            dto.setProfile(profileDTO);
        }

        // Include project names
        Set<String> projectNames = employee.getProjects()
                .stream()
                .map(Project::getProjectName)
                .collect(Collectors.toSet());
        dto.setProjects(projectNames);

        return dto;
    }

    public EmployeeDTO create(CreateEmployeeDTO dto) {
        Employee employee = mapToEntity(dto);
        Employee saved = employeeRepository.save(employee);
        return mapToDTO(saved);
    }

    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public GetEmployeeDTO getByEmpId(String empId) {
        Employee employee = employeeRepository.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + empId));
        return mapToGetDTO(employee);
    }

    public EmployeeDTO update(String empId, EmployeeDTO dto) {
        Employee employee = employeeRepository.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + empId));

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Invalid Department ID: " + dto.getDepartmentId()));
            employee.setDepartment(department);
        }

        Employee updated = employeeRepository.save(employee);
        return mapToDTO(updated);
    }

    public void delete(String empId) {
        Employee employee = employeeRepository.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + empId));
        employeeRepository.delete(employee);
    }

    public void assignProject(String empId, Long projectId) {
        Employee employee = employeeRepository.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + empId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));

        employee.getProjects().add(project);
        employeeRepository.save(employee);
    }

    public void removeProject(String empId, Long projectId) {
        Employee employee = employeeRepository.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + empId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));

        employee.getProjects().remove(project);
        employeeRepository.save(employee);
    }
}
