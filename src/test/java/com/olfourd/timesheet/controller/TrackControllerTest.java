package com.olfourd.timesheet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olfourd.timesheet.controller.dto.track.AddTrackRequest;
import com.olfourd.timesheet.dao.ProjectRepository;
import com.olfourd.timesheet.dao.TrackRepository;
import com.olfourd.timesheet.dao.model.Project;
import com.olfourd.timesheet.dao.model.Track;
import com.olfourd.timesheet.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TrackController.class)
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrackRepository trackRepository;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void shouldCreateNewTrack() throws Exception {
        Project project = new Project("projectId", "projectName");

        given(projectRepository.findById(anyString())).willReturn(Optional.of(project));
        given(trackRepository.insert(any(Track.class))).willAnswer((invocation) -> invocation.getArgument(0));

        AddTrackRequest request = AddTrackRequest.builder()
                .projectId(project.getId())
                .start(LocalDateTime.of(2020, 9, 20, 15, 0, 15))
                .end(LocalDateTime.of(2020, 9, 20, 15, 45, 15))
                .description("track description")
                .build();

        this.mockMvc.perform(post("/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.project.id", is(project.getId())))
                .andExpect(jsonPath("$.project.name", is(project.getName())))
                .andExpect(jsonPath("$.trackTime.start", is(request.getStart().toString())))
                .andExpect(jsonPath("$.trackTime.end", is(request.getEnd().toString())))
                .andExpect(jsonPath("$.description", is(request.getDescription())));
    }

    @Test
    void getTrackTest() throws Exception {
        Track track = Track.builder().id("track id").build();
        List<Track> returnData = List.of(track);

        given(trackRepository.findAll()).willReturn(returnData);

        this.mockMvc.perform(get("/track"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(returnData.size())))
                .andExpect(jsonPath("$[0].id", is(track.getId())));
    }

    @Test
    void shouldUpdateTrack() throws Exception {
        Track track = Track.builder().id("unique Id").build();

        given(trackRepository.findByIdMandatory(track.getId())).willReturn(track);
        given(trackRepository.save(any(Track.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(put("/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(track)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(track.getId())));
    }

    @Test
    void shouldReturn404WhenUpdateNonExistingTrack() throws Exception {
        Track track = Track.builder().id("unique Id").build();
        given(trackRepository.findByIdMandatory(anyString())).willThrow(EntityNotFoundException.class);

        this.mockMvc.perform(put("/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(track)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistingTrack() throws Exception {
        given(trackRepository.findByIdMandatory(anyString())).willThrow(EntityNotFoundException.class);

        this.mockMvc.perform(delete("/track/{id}", "id"))
                .andExpect(status().isNotFound());
    }

}
