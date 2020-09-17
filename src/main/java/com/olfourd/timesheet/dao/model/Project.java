package com.olfourd.timesheet.dao.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Проект
 */
@Data
@RequiredArgsConstructor(staticName = "of")
public class Project {

    /**
     * Идентификатор
     */
    @Id
    private String id;
    /**
     * Наименование
     */
    @NonNull
    private String name;
}
