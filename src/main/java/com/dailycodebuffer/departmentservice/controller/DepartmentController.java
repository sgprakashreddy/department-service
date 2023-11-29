package com.dailycodebuffer.departmentservice.controller;

import com.dailycodebuffer.departmentservice.client.EmployeeClient;
import com.dailycodebuffer.departmentservice.model.Department;
import com.dailycodebuffer.departmentservice.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private EmployeeClient employeeClient;

    @PostMapping
    public Department add(@RequestBody Department department){
        LOGGER.info("******* Department add: {} ", department);
        return departmentRepository.addDepartment(department);
    }
    @GetMapping
    public List<Department> findAll(){
        LOGGER.info("******* Department findAll *******");
        return departmentRepository.findAll();
    }
    @GetMapping("/{id}")
    public Department findById(@PathVariable("id") Long id){
        LOGGER.info("******* Department findById : id={} *******",id);
        return departmentRepository.findById(id);
    }

    @GetMapping("/with-employees")
    public List<Department> findAllWithEmployees(){
        LOGGER.info("******* Department findAllWithEmployees *******");
        List<Department> departments= departmentRepository.findAll();
        LOGGER.info("******* Departments received from repository  ******* "+departments.size());
        departments.forEach(department -> department.setEmployees(employeeClient.findByDepartment(department.getId())));

        return departments;
        }
        
    }
