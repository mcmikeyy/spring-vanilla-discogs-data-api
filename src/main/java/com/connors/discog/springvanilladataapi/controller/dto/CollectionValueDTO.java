package com.connors.discog.springvanilladataapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionValueDTO extends BaseDTO {
  private BigDecimal median;
  private BigDecimal minimum;
  private BigDecimal maximum;
}
