package com.olfourd.timesheet.dao.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@ApiModel(description = "Трекинг")
@Data
@Builder
public class Track {

    @ApiModelProperty(notes = "Идентификатор")
    @Id
    private String id;

    @ApiModelProperty(notes = "Провект")
    private Project project;

    @ApiModelProperty("Время трекинга")
    private TrackTime trackTime;

    @ApiModelProperty("Описание работ")
    private String description;

    @ApiModel(description = "Время трекинга")
    @Data
    @AllArgsConstructor(staticName = "of")
    public static class TrackTime {
        @ApiModelProperty("Стапт трекинга")
        LocalDateTime start;
        @ApiModelProperty("Окончание трекинга")
        LocalDateTime end;
    }
}
