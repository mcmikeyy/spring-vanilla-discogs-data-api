package com.connors.discog.springvanilladataapi.service;

import com.connors.discog.springvanilladataapi.controller.dto.CollectionValueDTO;
import com.connors.discog.springvanilladataapi.entity.CollectionValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Integration Tests of CollectionValueService with H2 Database")

public class CollectionValueServiceITCase {

    @Autowired
    private CollectionValueService collectionValueService;

    @Autowired
    private EntityManager entityManager;

    @AfterEach
    public void cleanUp() {

    }

    @Test
    @Transactional
    @DisplayName("get a list with 3 Collection values")
    public void given3CollectionValuesInDb_whenFindAll_thenGet3CollectionValues() {
        //given
        entityManager.persist(singleCollectionValue(1L));
        entityManager.persist(singleCollectionValue(2L));
        entityManager.persist(singleCollectionValue(3L));
        entityManager.flush();

        getCollectionValuesResultListSavedInDb();

        //when
        final List<CollectionValueDTO> collectionValueDtos = collectionValueService.findAll();

        //then
        assertAll("collection results",
                () -> assertThat(collectionValueDtos.size()).isEqualTo(3),
                () -> assertThat(collectionValueDtos.get(0).getMedian()).isEqualByComparingTo("43.33")
        );
    }

    @Test
    @Transactional
    @DisplayName("get a list with single collection value")
    public void given3CollectionValuesInDB_whenFindBYId_thenGetSingleCollectionValue() {
        //given
        entityManager.persist(singleCollectionValue(1L));
        entityManager.persist(singleCollectionValue(2L));
        entityManager.persist(singleCollectionValue(3L));
        entityManager.flush();


        final List<CollectionValue> collectionValueResultListSavedInDb = getCollectionValuesResultListSavedInDb();
        final CollectionValue collectionValue = collectionValueResultListSavedInDb.get(0);

        // when
        final Optional<CollectionValueDTO> collectionValuebyId = collectionValueService.findById(collectionValue.getId());

        assertAll("test all properties of the collectionValue",
                () -> assertThat(collectionValuebyId.isPresent()).isTrue(),
                () -> assertThat(collectionValuebyId.get().getMinimum()).isEqualByComparingTo("24.33"),
                () -> assertThat(collectionValuebyId.get().getMaximum()).isEqualByComparingTo("101.33"),
                () -> assertThat(collectionValuebyId.get().getMedian()).isEqualByComparingTo("43.33")
        );
    }

    @Test
    @Transactional
    @DisplayName("get a collection by id and return empty results")
    public void given3CollectionValuesInDB_whenFindById_thenGetEmptyOptional() {
        //given
        entityManager.persist(singleCollectionValue(1L));
        entityManager.persist(singleCollectionValue(2L));
        entityManager.persist(singleCollectionValue(3L));
        entityManager.flush();

        // when
        final Optional<CollectionValueDTO> collectionValuebyId = collectionValueService.findById(5000L);

        assertAll("test all properties of the collectionValue",
                () -> assertThat(collectionValuebyId.isPresent()).isFalse()
        );
    }

    @Test
    @Transactional
    @DisplayName("Save a Collection")
    public void givenCollectionValue_whenSave_thenGetSavedCollectionValue() {
        //given
        CollectionValueDTO collectionValueDTO = singleCollectionValueDTO();

        //when
        CollectionValueDTO savedCollectionValueDTO = collectionValueService.save(collectionValueDTO);

        //then
        assertAll("properties on the collection value",
                () -> assertThat(savedCollectionValueDTO).isNotNull(),
                () -> assertThat(savedCollectionValueDTO.getMedian()).isEqualByComparingTo("42.33"),
                () -> assertThat(savedCollectionValueDTO.getMaximum()).isEqualByComparingTo("100.33"),
                () -> assertThat(savedCollectionValueDTO.getMinimum()).isEqualByComparingTo("23.33"),
                () -> assertThat(savedCollectionValueDTO.getCreationDateTime()).isAfterOrEqualTo(LocalDateTime.now().minusHours(5))
        );
    }

    @Test
    @Transactional
    @DisplayName("Delete CollectionValue by Id")
    public void given3CollectionValueInDatabase_whenDeleteById_thenGet2CollectionValues() {
        //given
        entityManager.persist(singleCollectionValue(1L));
        entityManager.persist(singleCollectionValue(2L));
        entityManager.persist(singleCollectionValue(3L));
        entityManager.flush();
        final CollectionValue collectionValue = getCollectionValuesResultListSavedInDb().get(0);

        //when
        collectionValueService.delete(collectionValue.getId());

        //then
        List<CollectionValue> collectionValueList = getCollectionValuesResultListSavedInDb();
        assertAll("the db results contain correct data",
                () -> assertThat(collectionValueList.size()).isEqualTo(2)
        );

        final List<CollectionValueDTO> all = collectionValueService.findAll();

        assertAll("the service findall results contain correct data",
                () -> assertThat(all.size()).isEqualTo(2)
        );
    }

    @Test
    @Transactional
    @DisplayName("Update a CollectionValue")
    public void givenSavedCollectionValueInDatabase_whenUpdate_thenCollectionValueIsUpdated() {
        //given
        entityManager.persist(singleCollectionValue(1L));
        entityManager.flush();
        final CollectionValue collectionValue = getCollectionValuesResultListSavedInDb().get(0);

        final CollectionValueDTO updateCollectionValueDTO = singleCollectionValueDTO();

        //when
        final CollectionValueDTO updated = collectionValueService.update(collectionValue.getId(), updateCollectionValueDTO);

        //then
        assertThat(updateCollectionValueDTO.getMinimum()).isEqualByComparingTo(updated.getMinimum());
        assertThat(updateCollectionValueDTO.getMedian()).isEqualByComparingTo(updated.getMedian());
        assertThat(updateCollectionValueDTO.getMaximum()).isEqualByComparingTo(updated.getMaximum());
        assertThat(collectionValue.getId()).isEqualByComparingTo(updated.getId());
        assertThat(collectionValue.getCreationDateTime()).isEqualToIgnoringNanos(updated.getCreationDateTime());
    }

    private List<CollectionValue> getCollectionValuesResultListSavedInDb() {

        return entityManager
                .createNativeQuery("SELECT collection_value.* FROM collection_value", CollectionValue.class)
                .getResultList();
    }

    private CollectionValue singleCollectionValue(long l) {
        return CollectionValue.builder()
                .minimum(BigDecimal.valueOf(23.33D + l))
                .median(BigDecimal.valueOf(42.33D + l))
                .maximum(BigDecimal.valueOf(100.33D + l))
                .build();
    }

    private CollectionValueDTO singleCollectionValueDTO() {
        return CollectionValueDTO.builder()
                .minimum(BigDecimal.valueOf(23.33D))
                .median(BigDecimal.valueOf(42.33D))
                .maximum(BigDecimal.valueOf(100.33D))
                .build();
    }

}
