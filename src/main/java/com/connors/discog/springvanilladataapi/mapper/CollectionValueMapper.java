package com.connors.discog.springvanilladataapi.mapper;

import com.connors.discog.springvanilladataapi.controller.dto.CollectionValueDTO;
import com.connors.discog.springvanilladataapi.entity.CollectionValue;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollectionValueMapper {
  CollectionValueMapper INSTANCE = Mappers.getMapper(CollectionValueMapper.class);
  CollectionValueDTO collectionValueToDto(CollectionValue collectionValue);
  CollectionValue dtoToCollectionValue(CollectionValueDTO collectionValueDTO);
}
