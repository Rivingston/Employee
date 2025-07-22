package com.example.employeedata.dto;

import java.util.Set;

public class GetEmployeeDTO {
    private String empId;
    private String name;
    private String email;
    private String password;

    private Long departmentId;
    private String departmentName;

    private ProfileDTO profile;

    private Set<String> projects;

    // Getters and Setters

    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = "********"; // Hide actual password
    }

    public Long getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public ProfileDTO getProfile() {
        return profile;
    }
    public void setProfile(ProfileDTO profile) {
        this.profile = profile;
    }

    public Set<String> getProjects() {
        return projects;
    }
    public void setProjects(Set<String> projects) {
        this.projects = projects;
    }
}
