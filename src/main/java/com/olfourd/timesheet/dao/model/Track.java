package com.olfourd.timesheet.dao.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
public class Track {

    /**
     * Идентификатор
     */
    @Id
    private String id;
    /**
     * Время трекинга
     */
    private TrackTime trackTime;
    /**
     * Описание работ
     */
    private String description;

    /**
     * Время трекинга
     */
    @Data
    @AllArgsConstructor(staticName = "of")
    public static class TrackTime {
        /**
         * Статр трекинга
         */
        LocalDateTime start;
        /**
         * Окончание трекинга
         */
        LocalDateTime end;
    }
}
