package com.olfourd.timesheet.controller;

import com.olfourd.timesheet.controller.dto.AddTrackRequest;
import com.olfourd.timesheet.dao.ProjectRepository;
import com.olfourd.timesheet.dao.TrackRepository;
import com.olfourd.timesheet.dao.model.Project;
import com.olfourd.timesheet.dao.model.Track;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel("Track api")
@RestController
@RequestMapping("track")
@RequiredArgsConstructor
public class TrackController {

    private final TrackRepository trackRepository;
    private final ProjectRepository projectRepository;

    @ApiOperation("Add new track")
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Track createTrack(@Valid @RequestBody AddTrackRequest request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found by ID: " + request.getProjectId()));

        Track track = Track.builder()
                .project(project)
                .trackTime(Track.TrackTime.of(request.getStart(), request.getEnd()))
                .description(request.getDescription())
                .build();

        return trackRepository.insert(track);
    }

    @ApiOperation("Get all tracks")
    @GetMapping
    @ResponseBody
    public List<Track> getTracks() {
        return trackRepository.findAll();
    }

    @ApiOperation("Update track")
    @PutMapping
    @ResponseBody
    public Track updateTrack(@Valid @RequestBody Track request) {
        Track track = trackRepository.findByIdMandatory(request.getId());

        track.setProject(request.getProject());
        track.setTrackTime(request.getTrackTime());
        track.setDescription(request.getDescription());

        return trackRepository.save(request);
    }

    @ApiOperation("Delete track")
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteTrack(@NotBlank @PathVariable("id") String id) {
        Track track = trackRepository.findByIdMandatory(id);
        trackRepository.delete(track);
    }

}
