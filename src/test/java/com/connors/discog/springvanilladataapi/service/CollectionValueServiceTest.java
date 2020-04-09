package com.connors.discog.springvanilladataapi.service;

import com.connors.discog.springvanilladataapi.controller.dto.CollectionValueDTO;
import com.connors.discog.springvanilladataapi.entity.CollectionValue;
import com.connors.discog.springvanilladataapi.repository.CollectionValueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.connors.discog.springvanilladataapi.utils.TestDataFactory.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for the collectionvalue service class")
public class CollectionValueServiceTest {

    @Mock
    private CollectionValueRepository collectionValueRepository;

    @InjectMocks
    private CollectionValueService collectionValueService;

    @Test
    @DisplayName("get empty list of collection values")
    public void givenNoCollectionValues_whenFindAll_thenGetEmptyList() {
        // given
        when(collectionValueRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        final List<CollectionValueDTO> allCollectionValues = collectionValueService.findAll();

        // then
        assertThat("", allCollectionValues.size(), is(0));
    }

    @Test
    @DisplayName("get a list with single collection value")
    public void givenSingleCollectionValues_whenFindAll_thenSingleCollectionValueList() {
        // given
        when(collectionValueRepository.findAll()).thenReturn(getCollectionValueList(1L));

        // when
        final List<CollectionValueDTO> allCollectionValues = collectionValueService.findAll();

        // then
        assertThat("size ", allCollectionValues.size(), is(1));
        assertThat("median", allCollectionValues.get(0).getMedian(), is(BigDecimal.valueOf(45.67D)));
        assertThat("maximum", allCollectionValues.get(0).getMaximum(), is(BigDecimal.valueOf(100.0D)));
        assertThat("minimum", allCollectionValues.get(0).getMinimum(), is(BigDecimal.valueOf(22.34D)));
    }

    @Test
    @DisplayName("get a list with 243 collection value")
    public void given243CollectionValues_whenFindAll_then243CollectionValueList() {
        // given
        when(collectionValueRepository.findAll()).thenReturn(getCollectionValueList(243L));

        // when
        final List<CollectionValueDTO> allCollectionValues = collectionValueService.findAll();

        // then
        assertThat("size ", allCollectionValues.size(), is(243));
    }

    @Test
    @DisplayName("get an collection list by id")
    public void givensinglecollectionvalue_whenfindbyid_thenGetSingleCollectionValue() {
        // given
        when(collectionValueRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleCollectionValue(1L)));
        //when
        final Optional<CollectionValueDTO> collectionOptional = collectionValueService.findById(1L);
        //then
        assertThat(collectionOptional.isPresent()).isTrue();
        assertThat(collectionOptional.get().getId()).isNotNull();
        assertThat(collectionOptional.get().getMaximum()).isEqualByComparingTo(BigDecimal.valueOf(100.0));
        assertThat(collectionOptional.get().getMinimum()).isEqualByComparingTo(BigDecimal.valueOf(22.34));
        assertThat(collectionOptional.get().getMedian()).isEqualByComparingTo(BigDecimal.valueOf(45.67));
    }

    @Test
    @DisplayName("get an collection value by id and return no results")
    public void givenCollectionValue_whenFindById_thenGetEmptyOptional() {
        // given
        when(collectionValueRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        // when
        final Optional<CollectionValueDTO> collectionValueOptional = collectionValueService.findById(1L);
        // then
        assertThat(collectionValueOptional.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Save a collection")
    public void givenCollectionValue_whenSave_thenGetSavedCollectionValue() {
        // given
        when(collectionValueRepository.save(any(CollectionValue.class)))
                .thenReturn(getSingleCollectionValue(1L));
        final CollectionValueDTO singleCollectionValueDTO = getSingleCollectionValueDTO(1L);
        // when

        final CollectionValueDTO save = collectionValueService.save(singleCollectionValueDTO);

        // then
        assertThat(save.getId()).isNotNull();
    }

    @Test
    @DisplayName("Update a Collection Value")
    public void givenSavedCollection_whenUpdate_theCollectionIsUpdated() {
        // given
        when(collectionValueRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleCollectionValue(1L)));

        when(collectionValueRepository.save(any(CollectionValue.class)))
                .thenReturn(getSingleCollectionValue(2L));

        final CollectionValueDTO toBeUpdatedCollectionValueDTO = getSingleCollectionValueDTO(2L);
        // when
        final CollectionValueDTO update = collectionValueService.update(1L, toBeUpdatedCollectionValueDTO);
        // then
        assertAll("test the properties of the collection value",
                () -> assertThat(update.getId()).isEqualTo(2L),
                () -> assertThat(update.getMaximum()).isEqualTo(BigDecimal.valueOf(100.0D)),
                () -> assertThat(update.getMedian()).isEqualTo(BigDecimal.valueOf(45.67D)),
                () -> assertThat(update.getMinimum()).isEqualTo(BigDecimal.valueOf(22.34D))
                );
    }
}
