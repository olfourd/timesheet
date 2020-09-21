package com.olfourd.timesheet.controller;

import com.olfourd.timesheet.controller.dto.track.AddTaskRequest;
import com.olfourd.timesheet.controller.dto.track.UpdateTaskRequest;
import com.olfourd.timesheet.dao.ProjectRepository;
import com.olfourd.timesheet.dao.TaskRepository;
import com.olfourd.timesheet.dao.model.Project;
import com.olfourd.timesheet.dao.model.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("Track api")
@RestController
@RequestMapping("task")
@RequiredArgsConstructor

public class TaskController {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @ApiOperation("Add new task")
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@Valid @RequestBody AddTaskRequest request) {
        Project project = projectRepository.findByIdMandatory(request.getProjectId());
        Task task = Task.builder()
                .project(project)
                .name(request.getName())
                .estimateHours(request.getEstimateHours())
                //todo: .assignees()
                .tracks(List.of())
                .done(false)
                .build();

        return taskRepository.save(task);
    }

    @ApiOperation("Read task by project id ")
    @GetMapping
    @ResponseBody
    public List<Task> readByProjectId(@RequestParam String projectId) {
        return taskRepository.findAllByProjectId(projectId);
    }

    @ApiOperation("Update task")
    @PutMapping
    @ResponseBody
    public Task updateTask(@Valid @RequestBody UpdateTaskRequest request) {
        Task task = taskRepository.findByIdMandatory(request.getId());

        task.setName(request.getName());
        task.setAssignees(request.getAssignees());
        task.setEstimateHours(request.getEstimateHours());
        task.setDone(request.getDone());

        return taskRepository.save(task);
    }
}