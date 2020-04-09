package com.connors.discog.springvanilladataapi.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {
  @ApiModelProperty(value = "The id of the object")
  private Long id;

  @ApiModelProperty(value = "The date time when the object was created")
  @JsonSerialize(using = ToStringSerializer.class)
  private LocalDateTime creationDateTime;
}
