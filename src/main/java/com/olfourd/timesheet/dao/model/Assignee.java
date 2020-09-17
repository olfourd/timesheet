package com.olfourd.timesheet.dao.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Исполнитель задачи
 */
@Data
@Builder
public class Assignee {
    /**
     * Идентификатор
     */
    @Id
    private String id;
    /**
     * Наименование
     */
    private String name;
    /**
     * Электронная почта
     */
    private String email;
}
