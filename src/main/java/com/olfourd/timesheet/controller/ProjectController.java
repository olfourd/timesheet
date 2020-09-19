package com.olfourd.timesheet.controller;

import com.olfourd.timesheet.dao.ProjectRepository;
import com.olfourd.timesheet.dao.model.Project;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel("Project api")
@RestController
@RequestMapping("project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;

    @ApiOperation("Add new project")
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@Valid @RequestBody Project project) {
        return projectRepository.insert(project);
    }

    @ApiOperation("Update project")
    @PutMapping
    @ResponseBody
    public Project updateProject(@Valid @RequestBody Project request) {
        Project project = projectRepository.findByIdMandatory(request.getId());
        project.setName(request.getName());
        return projectRepository.save(project);
    }

    @ApiOperation("Get all project")
    @GetMapping
    @ResponseBody
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @ApiOperation("Delete project")
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteProject(@NotBlank @PathVariable("id") String id) {
        Project project = projectRepository.findByIdMandatory(id);
        projectRepository.delete(project);
    }
}
