package com.connors.discog.springvanilladataapi.utils;

import com.connors.discog.springvanilladataapi.controller.dto.CollectionValueDTO;
import com.connors.discog.springvanilladataapi.entity.CollectionValue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TestDataFactory {

    public static CollectionValueDTO getSingleCollectionValueDTO(Long id) {
        return CollectionValueDTO.builder()
                .maximum(BigDecimal.valueOf(100.00))
                .median(BigDecimal.valueOf(45.67))
                .minimum(BigDecimal.valueOf(22.34))
                .id(id)
                .creationDateTime(LocalDateTime.now())
                .build();
    }

    public static List<CollectionValueDTO> getCollectionValueDTOList(Long count) {
        return LongStream.rangeClosed(1, count)
                .mapToObj(TestDataFactory::getSingleCollectionValueDTO)
                .collect(Collectors.toList());
    }

    public static CollectionValue getSingleCollectionValue(Long id) {
        return CollectionValue.builder()
                .maximum(BigDecimal.valueOf(100.00))
                .median(BigDecimal.valueOf(45.67))
                .minimum(BigDecimal.valueOf(22.34))
                .id(id)
                .creationDateTime(LocalDateTime.now())
                .build();
    }

    public static List<CollectionValue> getCollectionValueList(Long count) {
        return LongStream.rangeClosed(1, count)
                .mapToObj(TestDataFactory::getSingleCollectionValue)
                .collect(Collectors.toList());
    }
}
