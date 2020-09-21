package com.olfourd.timesheet.controller.dto.task;

import com.olfourd.timesheet.dao.model.Assignee;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "Запрос на обновление сущности Задача")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTaskRequest {

    @ApiModelProperty("Идентификатор")
    private String id;
    @ApiModelProperty("Наименование задачи")
    private String name;
    @ApiModelProperty("Исполнители")
    private List<Assignee> assignees;
    @ApiModelProperty("Трудооценка (в часах)")
    private Integer estimateHours;
    @ApiModelProperty("Флаг завершённости задачи")
    private Boolean done;
}
