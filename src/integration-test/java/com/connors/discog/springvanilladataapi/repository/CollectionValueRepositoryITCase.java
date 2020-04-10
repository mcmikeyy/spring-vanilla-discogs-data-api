package com.connors.discog.springvanilladataapi.repository;

import com.connors.discog.springvanilladataapi.entity.CollectionValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    @Transactional
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

    @Test
    @DisplayName("Save a CollectionValue")
    @Transactional
    public void givenSingleCollectionValue_whenSave_thenCollectionValueIsSaved() {
        //given
        CollectionValue collectionValue = singleCollectionValue(1L);

        //when
        collectionValueRepository.save(collectionValue);

        //then
        CollectionValue savedCollectionValue = getCollectionValuesResultListSavedInDb().get(0);
        assertNotNull(savedCollectionValue.getId());
        assertAll("check all properties",
                () -> assertThat(savedCollectionValue.getMedian()).isEqualByComparingTo("43.33"),
                () -> assertThat(savedCollectionValue.getMinimum()).isEqualByComparingTo("24.33"),
                () -> assertThat(savedCollectionValue.getMaximum()).isEqualByComparingTo("101.33")
                );
    }

    @Test
    @DisplayName("Delete CollectionValue by Id")
    @Transactional
    public void given2SavedCollectionValue_whenDeleteById_thenOnlyOneCollectionValueInDatabase() {
        //given
        entityManager.persist(singleCollectionValue(1L));
        entityManager.persist(singleCollectionValue(2L));
        entityManager.flush();

        CollectionValue collectionValueToBeDeleted = getCollectionValuesResultListSavedInDb().get(0);

        //when
        collectionValueRepository.deleteById(collectionValueToBeDeleted.getId());

        //then
        List<CollectionValue> collectionValueList = getCollectionValuesResultListSavedInDb();

        assertEquals(1, collectionValueList.size());
        assertFalse(collectionValueToBeDeleted.getId().equals(collectionValueList.get(0).getId()));
    }

    @Test
    @DisplayName("Get a CollectionValue by Id when 2 CollectionValue are in database")
    @Transactional
    public void given2CollectionValues_whenFindById_thenGetSingleCollectionValue() {
        //given
        entityManager.persist(singleCollectionValue(1L));
        entityManager.persist(singleCollectionValue(2L));
        entityManager.flush();

        CollectionValue savedCollectionValue = getCollectionValuesResultListSavedInDb().get(0);

        //when
        Optional<CollectionValue> optNotice = collectionValueRepository.findById(savedCollectionValue.getId());

        //then
        assertTrue(optNotice.isPresent());
        assertNotNull(optNotice.get().getId());
    }

    @Test
    @DisplayName("Get a list with 3 Notices")
    public void given3CollectionValues_whenFindAll_thenGetCollectionValues() {
        //given
        entityManager.persist(singleCollectionValue(1L));
        entityManager.persist(singleCollectionValue(2L));
        entityManager.persist(singleCollectionValue(3L));
        entityManager.flush();

        //when
        Iterable<CollectionValue> noticeIterable = collectionValueRepository.findAll();

        //then
        List<CollectionValue> noticeList =
                StreamSupport.stream(noticeIterable.spliterator(), false)
                        .collect(Collectors.toList());

        assertFalse(noticeList.isEmpty());
        assertEquals(3, noticeList.size());
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
