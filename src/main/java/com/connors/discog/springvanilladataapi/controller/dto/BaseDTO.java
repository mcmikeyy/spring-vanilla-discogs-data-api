package com.connors.discog.springvanilladataapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {
  @JsonProperty( value = "id", access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @JsonProperty( value = "creationDateTime", access = JsonProperty.Access.READ_ONLY)
  @JsonSerialize(using = ToStringSerializer.class)
  private LocalDateTime creationDateTime;

  @JsonProperty( value = "modifiedDateTime", access = JsonProperty.Access.READ_ONLY)
  @JsonSerialize(using = ToStringSerializer.class)
  private LocalDateTime modifiedDateTime;

  @JsonProperty( value = "modifiedBy", access = JsonProperty.Access.READ_ONLY)
  private String modifiedBy;

  @JsonProperty( value = "createdBy", access = JsonProperty.Access.READ_ONLY)
  private String createdBy;
}
