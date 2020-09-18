package com.olfourd.timesheet.controller;

import com.olfourd.timesheet.dao.ProjectRepository;
import com.olfourd.timesheet.dao.model.Project;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//todo: controller test
@ApiModel("Project api")
@RestController
@RequestMapping("project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;

    @ApiOperation("Add new project")
    @PostMapping
    @ResponseBody
    public Project createProject(@Valid @RequestBody Project project) {
        return projectRepository.insert(project);
    }

    @ApiOperation("Update project")
    @PutMapping
    @ResponseBody
    public Project updateProject(@Valid @RequestBody Project project) {
        return projectRepository.save(project);
    }

    @ApiOperation("Get all project")
    @GetMapping
    @ResponseBody
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @ApiOperation("Delete project")
    @DeleteMapping
    @ResponseBody
    //todo by id
    public void deleteProject(@Valid @RequestBody Project project) {
        projectRepository.delete(project);
    }
}
