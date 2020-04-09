package com.connors.discog.springvanilladataapi.repository;

import com.connors.discog.springvanilladataapi.entity.CollectionValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Integration Tests of CollectionValueRepository with H2 Database")
public class CollectionValueRepositoryITCase {

    @Autowired
    private CollectionValueRepository collectionValueRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Get a CollectionValue by Id")
    public void givenSingleCollectionValue_whenFindById_thenGetCollectionValue() {
        //given

        entityManager.persist(singleCollectionValue(11L));
        entityManager.flush();
        CollectionValue savedCollectionValue = getCollectionValuesResultListSavedInDb().get(0);

        //when
        Optional<CollectionValue> optionalCollectionValue = collectionValueRepository.findById(savedCollectionValue.getId());

        //then
        assertAll("properties of the result",
                () -> assertThat(optionalCollectionValue.isPresent()).isTrue(),
                () -> assertThat(optionalCollectionValue.get().getMaximum()).isEqualByComparingTo("111.33"),
                () -> assertThat(optionalCollectionValue.get().getMinimum()).isEqualByComparingTo("34.33"),
                () -> assertThat(optionalCollectionValue.get().getMedian()).isEqualByComparingTo("53.33")
                );

    }

    private CollectionValue singleCollectionValue(long l) {
        return CollectionValue.builder()
                .minimum(BigDecimal.valueOf(23.33D + l))
                .median(BigDecimal.valueOf(42.33D + l))
                .maximum(BigDecimal.valueOf(100.33D + l))
                .build();
    }

    private List<CollectionValue> getCollectionValuesResultListSavedInDb() {

        return entityManager
                .createNativeQuery("SELECT collection_value.* FROM collection_value", CollectionValue.class)
                .getResultList();
    }
}
