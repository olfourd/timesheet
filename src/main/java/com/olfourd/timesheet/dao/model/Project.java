package com.olfourd.timesheet.dao.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;


@ApiModel(description = "Проект")
@Data
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @ApiModelProperty(notes = "Идентификатор")
    private String id;

    @ApiModelProperty(notes = "Наименование проекта", required = true)
    @NonNull
    @NotBlank(message = "Project name is mandatory")
    private String name;
}
