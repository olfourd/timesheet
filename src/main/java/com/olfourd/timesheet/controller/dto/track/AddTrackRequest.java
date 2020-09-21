package com.olfourd.timesheet.controller.dto.track;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel(description = "Запрос добавления трекинга к проекту")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddTrackRequest {

    @ApiModelProperty(notes = "Идентификатор проекта", required = true)
    @NotNull(message = "project is mandatory")
    private String projectId;

    @ApiModelProperty("Стапт трекинга")
    @NotNull(message = "start is mandatory")
    private LocalDateTime start;

    @ApiModelProperty("Окончание трекинга")
    @NotNull(message = "end is mandatory")
    private LocalDateTime end;

    @ApiModelProperty(notes = "Описание работы")
    private String description;
}