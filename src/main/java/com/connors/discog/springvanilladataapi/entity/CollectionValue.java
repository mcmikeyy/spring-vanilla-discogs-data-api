package com.connors.discog.springvanilladataapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.math.BigDecimal;

@Entity(name="collection_value")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class CollectionValue extends BaseEntity {

    @Column(name="minimum")
    private BigDecimal minimum;

    @Column(name="median")
    private BigDecimal median;

    @Column(name="maximum")
    private BigDecimal maximum;

}
