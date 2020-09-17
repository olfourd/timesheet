package com.olfourd.timesheet.dao.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Задача
 */
@Data
@Builder
public class Task {

    /**
     * Идентификатор
     */
    @Id
    private String id;
    /**
     * Проект
     */
    private Project project;
    /**
     * Наименование задачи
     */
    private String name;
    /**
     * Трудооценка (в часах)
     */
    private Integer estimateHours;
    /**
     * Исполнители
     */
    private List<Assignee> assignees;
    /**
     * Трекинг
     */
    private List<Track> tracks;
}
