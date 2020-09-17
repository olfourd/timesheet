package com.olfourd.timesheet;

import com.olfourd.timesheet.dao.AssigneeRepository;
import com.olfourd.timesheet.dao.ProjectRepository;
import com.olfourd.timesheet.dao.TaskRepository;
import com.olfourd.timesheet.dao.TrackRepository;
import com.olfourd.timesheet.dao.model.Assignee;
import com.olfourd.timesheet.dao.model.Project;
import com.olfourd.timesheet.dao.model.Task;
import com.olfourd.timesheet.dao.model.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ModelTest {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private AssigneeRepository assigneeRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void projectModelTest() {
        final String projectName = "project name";

        Project project = projectRepository.save(Project.of(projectName));
        Project foundedProject = projectRepository.findById(project.getId())
                .orElseThrow(() -> new RuntimeException("project not found by id:" + project.getId()));

        Assertions.assertEquals(project, foundedProject);
        Assertions.assertEquals(foundedProject.getName(), projectName);

        projectRepository.delete(project);
    }

    @Test
    void asigneeModelTest() {
        final String name = "name";
        final String email = "name@gmail.com";

        Assignee assignee = assigneeRepository.save(Assignee.builder()
                .name(name)
                .email(email)
                .build());
        Assignee foundedAssignee = assigneeRepository.findById(assignee.getId())
                .orElseThrow(() -> new RuntimeException("assignee not found by id:" + assignee.getId()));

        Assertions.assertEquals(foundedAssignee, assignee);
        Assertions.assertEquals(foundedAssignee.getName(), name);
        Assertions.assertEquals(foundedAssignee.getEmail(), email);

        assigneeRepository.delete(assignee);
    }

    @Test
    void trackModelTest() {
        final String description = "описание работ";
        LocalDateTime start = LocalDateTime.of(2020, 9, 9, 12, 15);
        LocalDateTime end = LocalDateTime.of(2020, 9, 9, 13, 0);

        Track track = trackRepository.save(Track.builder()
                .trackTime(Track.TrackTime.of(start, end))
                .description(description)
                .build());
        Track foundedTrack = trackRepository.findById(track.getId())
                .orElseThrow(() -> new RuntimeException("track not found by id:" + track.getId()));

        Assertions.assertEquals(track, foundedTrack);
        Assertions.assertEquals(track.getTrackTime(), foundedTrack.getTrackTime());
        Assertions.assertEquals(foundedTrack.getDescription(), description);

        trackRepository.delete(track);
    }

    @Test
    void taskModelTest() {
        Project project = projectRepository.save(Project.of("project name"));
        Assignee assignee = assigneeRepository.save(Assignee.builder().name("assigneeName").email("assigneeEmail").build());
        Track track = trackRepository.save(Track.builder()
                .trackTime(Track.TrackTime.of(
                        LocalDateTime.of(2020, 9, 9, 12, 15),
                        LocalDateTime.of(2020, 9, 9, 13, 0)
                ))
                .description("description")
                .build()
        );

        Task task = taskRepository.save(Task.builder()
                .project(project)
                .name("task name")
                .estimateHours(12)
                .assignees(List.of(assignee))
                .tracks(List.of(track))
                .build()
        );

        projectRepository.delete(project);
        assigneeRepository.delete(assignee);
        trackRepository.delete(track);
        taskRepository.delete(task);
    }
}