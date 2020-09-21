package com.olfourd.timesheet.controller.dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddTaskRequest {

    @NotBlank
    private String projectId;
    @NotBlank
    private String name;
    private Integer estimateHours;

}
