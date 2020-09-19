package com.olfourd.timesheet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olfourd.timesheet.dao.ProjectRepository;
import com.olfourd.timesheet.dao.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProjectController.class)
public class ProjectControllerTest {

    private final String PROJECT_CONTROLLER_PATH = "/project";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProjectRepository projectRepository;

    @Test
    void shouldReturn201WhenCreate() throws Exception {
        Project requestProject = Project.of("project name");
        Project savedProject = new Project("saved id", requestProject.getName());

        given(projectRepository.insert(any(Project.class))).willReturn(savedProject);

        this.mockMvc.perform(post(PROJECT_CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestProject)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(savedProject.getId())))
                .andExpect(jsonPath("$.name", is(requestProject.getName())));
    }

    @Test
    void shouldReturn400WhenCreate() throws Exception {
        Project project = new Project();

        this.mockMvc.perform(post(PROJECT_CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn200WhenGetAll() throws Exception {
        Project project = new Project("projectId", "ProjectName");

        given(projectRepository.findAll()).willReturn(List.of(project));

        this.mockMvc.perform(get(PROJECT_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(project.getId())));
    }

    @Test
    void shouldReturn200WhenUpdate() throws Exception {
        final String id = "project id";
        Project requestProject = new Project(id, "new name");
        Project savedProject = new Project(id, "name");

        given(projectRepository.findByIdMandatory(anyString())).willReturn(savedProject);
        given(projectRepository.save(any(Project.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(put(PROJECT_CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestProject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(requestProject.getId())))
                .andExpect(jsonPath("$.id", is(savedProject.getId())))
                .andExpect(jsonPath("$.name", is(requestProject.getName())));
    }

    @Test
    void shouldReturn404WhenUpdate() throws Exception {
        given(projectRepository.findById(anyString())).willReturn(Optional.empty());

        this.mockMvc.perform(put("/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenDelete() throws Exception {
        Project project = new Project("id", "name");

        given(projectRepository.findById(anyString())).willReturn(Optional.of(project));

        this.mockMvc.perform(delete(PROJECT_CONTROLLER_PATH + "/" + project.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenDelete() throws Exception {
        given(projectRepository.findById(anyString())).willReturn(Optional.empty());

        this.mockMvc.perform(delete("/track/someId"))
                .andExpect(status().isNotFound());
    }
}
