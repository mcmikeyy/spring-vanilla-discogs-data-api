package com.connors.discog.springvanilladataapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
/**
 * //TODO add currency
 */
public class CollectionValue extends BaseEntity {

    @Column(name="minimum")
    private BigDecimal minimum;

    @Column(name="median")
    private BigDecimal median;

    @Column(name="maximum")
    private BigDecimal maximum;

}
