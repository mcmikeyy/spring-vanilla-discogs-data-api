package com.connors.discog.springvanilladataapi.service;

import com.connors.discog.springvanilladataapi.controller.dto.CollectionValueDTO;
import com.connors.discog.springvanilladataapi.entity.CollectionValue;
import com.connors.discog.springvanilladataapi.repository.CollectionValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.connors.discog.springvanilladataapi.mapper.CollectionValueMapper.INSTANCE;

@Service
@RequiredArgsConstructor
/**
 * consider adding stream changes
 */
public class CollectionValueService implements CrudService<CollectionValueDTO> {

    private final CollectionValueRepository collectionValueRepository;

    @Override
    public List<CollectionValueDTO> findAll() {
        List<CollectionValueDTO> collectionValueDTOList = new ArrayList<>();
        collectionValueRepository.findAll().forEach(collectionValue -> collectionValueDTOList.add(INSTANCE.collectionValueToDto(collectionValue)));
        return collectionValueDTOList;
    }

    @Override
    public Optional<CollectionValueDTO> findById(Long id) {
        return collectionValueRepository.findById(id).map(INSTANCE::collectionValueToDto);
    }

    @Override
    public CollectionValueDTO save(CollectionValueDTO dto) {
        return INSTANCE.collectionValueToDto(collectionValueRepository.save(INSTANCE.dtoToCollectionValue(dto)));
    }

    @Override
    public void delete(Long id) {
        collectionValueRepository.deleteById(id);
    }

    @Override
    public CollectionValueDTO update(Long id, CollectionValueDTO dto) {
        final CollectionValue savedCollectionValue = collectionValueRepository.findById(id).orElseThrow();
        final CollectionValue collectionValueUpdate = INSTANCE.dtoToCollectionValue(dto);

        savedCollectionValue.setMaximum(collectionValueUpdate.getMaximum());
        savedCollectionValue.setMinimum(collectionValueUpdate.getMinimum());
        savedCollectionValue.setMedian(collectionValueUpdate.getMedian());

        return INSTANCE.collectionValueToDto(collectionValueRepository.save(savedCollectionValue));

    }
}
