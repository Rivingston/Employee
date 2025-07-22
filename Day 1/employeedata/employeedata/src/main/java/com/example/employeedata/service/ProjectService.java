package com.example.employeedata.service;

import com.example.employeedata.dto.ProjectDTO;
import com.example.employeedata.model.Employee;
import com.example.employeedata.model.Project;
import com.example.employeedata.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public Project getById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
    }

    public ProjectDTO getProjectDTOById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));

        List<String> employeeIds = project.getEmployees().stream()
                .map(Employee::getEmpId)
                .collect(Collectors.toList());

        return new ProjectDTO(project.getProjectId(), project.getProjectName(), employeeIds);
    }

    public Project update(Long id, Project updatedProject) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));

        existing.setProjectName(updatedProject.getProjectName());
        existing.setDescription(updatedProject.getDescription());

        return projectRepository.save(existing);
    }

    public void delete(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));

        projectRepository.delete(project);
    }
}
