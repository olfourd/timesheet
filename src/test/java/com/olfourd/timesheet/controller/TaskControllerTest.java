package com.olfourd.timesheet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olfourd.timesheet.controller.dto.track.AddTaskRequest;
import com.olfourd.timesheet.controller.dto.track.UpdateTaskRequest;
import com.olfourd.timesheet.dao.ProjectRepository;
import com.olfourd.timesheet.dao.TaskRepository;
import com.olfourd.timesheet.dao.model.Assignee;
import com.olfourd.timesheet.dao.model.Project;
import com.olfourd.timesheet.dao.model.Task;
import com.olfourd.timesheet.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {

    private final String TASK_CONTROLLER_PATH = "/task";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void shouldReturn200WhenCreateTask() throws Exception {
        AddTaskRequest request = AddTaskRequest.builder()
                .projectId("project id")
                .name("task name")
                .estimateHours(4)
                .build();
        Project project = new Project(request.getProjectId(), "name");

        given(projectRepository.findByIdMandatory(anyString())).willReturn(project);
        given(taskRepository.save(any(Task.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(post(TASK_CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.project.id", is(project.getId())))
                .andExpect(jsonPath("$.project.name", is(project.getName())))
                .andExpect(jsonPath("$.name", is(request.getName())))
                .andExpect(jsonPath("$.estimateHours", is(request.getEstimateHours())))
                .andExpect(jsonPath("$.done", is(false)));
    }

    @Test
    void shouldReturn404WhenCreateTaskIfProjectNotExist() throws Exception {
        AddTaskRequest request = AddTaskRequest.builder()
                .projectId("not exist project id")
                .name("task name")
                .build();

        given(projectRepository.findByIdMandatory(anyString())).willThrow(EntityNotFoundException.class);

        this.mockMvc.perform(post(TASK_CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenReadByProjectId() throws Exception {
        Task task = Task.builder()
                .id("taskId")
                .project(new Project("projectId", "project name"))
                .build();

        given(taskRepository.findAllByProjectId(anyString())).willReturn(List.of(task));

        this.mockMvc.perform(get(TASK_CONTROLLER_PATH + "?projectId=" + task.getProject().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(task.getId())))
                .andExpect(jsonPath("$[0].project.id", is(task.getProject().getId())))
                .andExpect(jsonPath("$[0].project.name", is(task.getProject().getName())));
    }

    @Test
    void shouldReturn200WhenUpdateTask() throws Exception {
        String taskId = "task id";
        UpdateTaskRequest request = UpdateTaskRequest.builder()
                .id(taskId)
                .name("task name")
                .assignees(List.of(Assignee.builder()
                        .id("assignee id")
                        .name("assignee name")
                        .build()))
                .estimateHours(4)
                .done(true)
                .build();
        Task savedTask = Task.builder()
                .id(taskId)
                .project(new Project("projectId", "projectName"))
                .done(false)
                .build();

        given(taskRepository.findByIdMandatory(taskId)).willReturn(savedTask);
        given(taskRepository.save(any(Task.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(put(TASK_CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(request.getId())))
                .andExpect(jsonPath("$.name", is(request.getName())))
                .andExpect(jsonPath("$.estimateHours", is(request.getEstimateHours())))
                .andExpect(jsonPath("$.done", is(request.getDone())));
    }

    @Test
    void shouldReturn404WhenUpdateTaskIfTaskNotExist() throws Exception {
        UpdateTaskRequest request = UpdateTaskRequest.builder()
                .id("task id")
                .name("task name")
                .assignees(List.of(Assignee.builder()
                        .id("assignee id")
                        .name("assignee name")
                        .build()))
                .estimateHours(4)
                .done(true)
                .build();

        given(taskRepository.findByIdMandatory(request.getId())).willThrow(EntityNotFoundException.class);

        this.mockMvc.perform(put(TASK_CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
