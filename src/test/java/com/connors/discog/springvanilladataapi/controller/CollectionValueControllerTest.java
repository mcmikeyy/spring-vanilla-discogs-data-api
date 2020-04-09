package com.connors.discog.springvanilladataapi.controller;

import com.connors.discog.springvanilladataapi.controller.dto.CollectionValueDTO;
import com.connors.discog.springvanilladataapi.service.CollectionValueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static com.connors.discog.springvanilladataapi.utils.TestDataFactory.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CollectionValueController.class)
@DisplayName("CollectionValueController Unit tests")
public class CollectionValueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollectionValueService collectionValueService;

    @Test
    @DisplayName("GET an empty list of Collection Values")
    public void givenNoCollectionValues_whenGETfindAll_thenGetEmptyList() throws Exception {
    // given
        when(collectionValueService.findAll())
                .thenReturn(Collections.emptyList());

    // when
        mockMvc.perform(
                get("/collectionValues/")
        )
    // then
        .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET an single list of Collection Values")
    public void giveSingleCollectionValues_whenGETfindAll_thenGetSingleCollectionValueList() throws Exception {
    // given
        when(collectionValueService.findAll())
                .thenReturn(getCollectionValueDTOList(1L));
    // when
mockMvc.perform(
        get("/collectionValues/")
)
    // then
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("[0].median").value(BigDecimal.valueOf(45.67)))
        .andExpect(jsonPath("[0].minimum").value(BigDecimal.valueOf(22.34)))
        .andExpect(jsonPath("[0].maximum").value(BigDecimal.valueOf(100.0)));
    }

    @Test
    @DisplayName("GET an Collection Values by id")
    public void giveCollectionValueId_whenGETById_thenGetSingleCollectionValue() throws Exception {
        // given
        when(collectionValueService.findById(1L))
                .thenReturn(Optional.of(getSingleCollectionValueDTO(1L)));
        // when
mockMvc.perform(
        get("/collectionValues/1")
)
        // then
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.median").value(BigDecimal.valueOf(45.67)))
        .andExpect(jsonPath("$.minimum").value(BigDecimal.valueOf(22.34)))
        .andExpect(jsonPath("$.maximum").value(BigDecimal.valueOf(100.0)));
    }

    @Test
    @DisplayName("GET an Collection Values by id and return 404 not found")
    public void giveIncorrectCollectionValueId_whenGETById_thenGetNotFoundCollectionValue() throws Exception {
        // given
        when(collectionValueService.findById(1L))
                .thenReturn(Optional.empty());
        // when
        mockMvc.perform(
                get("/collectionValues/1")
        )
                // then
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST a collection value to create it")
    public void giveCollectionValue_whenPOSTSave_thenGetSavedCollectionValue() throws Exception {
        // given
        final CollectionValueDTO singleCollectionValueDTO = getSingleCollectionValueDTO(1L);
        singleCollectionValueDTO.setId(null);
        // when
        mockMvc.perform(
                post("/collectionValues/")
                // then
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(singleCollectionValueDTO))
                .characterEncoding("utf-8")
        )
        // then
        .andDo(print()).andExpect(status().isCreated());



    }

    @Test
    @DisplayName("DELETE a collection value by id")
    public void giveCollectionValueId_whenDELETEbyId_thenCollectionValueIsDeleted() throws Exception {
        // given
        when(collectionValueService.findById(1L))
                .thenReturn(Optional.of(getSingleCollectionValueDTO(1L)));
        // when
        mockMvc.perform(
                delete("/collectionValues/1")
        )
        // then
        .andDo(print())
        .andExpect(status().isNoContent())
        .andExpect(content().string("Object with the id 1 was deleted."));
    }

    @Test
    @DisplayName("DELETE a collection value by id and return 404 HTTP Not Found")
    public void giveCollectionValueId_whenDELETEbyId_thenCollectionValueNotFound() throws Exception {
        // given
        when(collectionValueService.findById(1L))
                .thenReturn(Optional.empty());
        // when
        mockMvc.perform(
                delete("/collectionValues/1")
        )
        // then
        .andDo(print())
        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT a collection value by id to update it")
    public void giveCollectionValueIdAndUpdateCollection_whenPUTbyId_thenCollectionValueIsUpdated() throws Exception {
        // given
        final CollectionValueDTO singleCollectionValueDTO = getSingleCollectionValueDTO(1L);

        when(collectionValueService.findById(1L))
                .thenReturn(Optional.of(singleCollectionValueDTO));

        CollectionValueDTO updated = singleCollectionValueDTO;
        updated.setMaximum(BigDecimal.valueOf(300D));
        updated.setMedian(BigDecimal.valueOf(123.34D));
        updated.setMinimum(BigDecimal.valueOf(12.344D));
        // when
        mockMvc.perform(
                put("/collectionValues/1")
                        // then
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(updated))
                        .characterEncoding("utf-8")
        )
        // then
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("Object with id 1 was updated."));
    }

    @Test
    @DisplayName("PUT a collection value by id and return 404 HTTP Not Found")
    public void giveCollectionValueId_whenPUTbyId_thenCollectionValueNotFound() throws Exception {
        // given
        final CollectionValueDTO singleCollectionValueDTO = getSingleCollectionValueDTO(1L);

        when(collectionValueService.findById(1L))
                .thenReturn(Optional.empty());

        CollectionValueDTO updated = singleCollectionValueDTO;
        updated.setMaximum(BigDecimal.valueOf(300D));
        updated.setMedian(BigDecimal.valueOf(123.34D));
        updated.setMinimum(BigDecimal.valueOf(12.344D));
        // when
        mockMvc.perform(
                put("/collectionValues/1")
                        // then
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(updated))
                        .characterEncoding("utf-8")
        )
                // then
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Not Found"));
    }

    private String toJsonString(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
