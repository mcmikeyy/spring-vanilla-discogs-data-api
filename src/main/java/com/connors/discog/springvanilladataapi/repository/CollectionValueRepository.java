package com.connors.discog.springvanilladataapi.repository;

import com.connors.discog.springvanilladataapi.entity.CollectionValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionValueRepository extends CrudRepository<CollectionValue, Long> {
}
