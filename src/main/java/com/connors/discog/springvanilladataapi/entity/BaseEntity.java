package com.connors.discog.springvanilladataapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @CreatedDate
    @Column(name = "creation_datetime", columnDefinition = "TIMESTAMP", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @LastModifiedDate
    @Column(name = "modified_datetime", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime modifiedDateTime;

    @LastModifiedBy
    @Column(nullable = false)
    private String modifiedBy;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;
}
