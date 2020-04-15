package com.connors.discog.springvanilladataapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="CollectionValueDTO", description="The value of the discogs collection")
public class CollectionValueDTO extends BaseDTO {

  @JsonProperty( value = "median", access = JsonProperty.Access.READ_WRITE)
  private BigDecimal median;

  @JsonProperty( value = "minimum", access = JsonProperty.Access.READ_WRITE)
  private BigDecimal minimum;

  @JsonProperty( value = "maximum", access = JsonProperty.Access.READ_WRITE)
  private BigDecimal maximum;


}
