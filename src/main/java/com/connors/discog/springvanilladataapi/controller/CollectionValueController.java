package com.connors.discog.springvanilladataapi.controller;

import com.connors.discog.springvanilladataapi.controller.dto.CollectionValueDTO;
import com.connors.discog.springvanilladataapi.service.CollectionValueService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collectionValues")
public class CollectionValueController extends CrudController<CollectionValueDTO>{

    public CollectionValueController(CollectionValueService collectionValueService) {
        super(collectionValueService);
    }
}
