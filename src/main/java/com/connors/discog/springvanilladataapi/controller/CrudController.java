package com.connors.discog.springvanilladataapi.controller;

import com.connors.discog.springvanilladataapi.controller.dto.BaseDTO;
import com.connors.discog.springvanilladataapi.controller.dto.CollectionValueDTO;
import com.connors.discog.springvanilladataapi.service.CrudService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public abstract class CrudController<T extends BaseDTO> {

    private final CrudService<T> service;

    public CrudController(CrudService<T> crudService){
        this.service = crudService;
    }

    @GetMapping("/")
    public ResponseEntity<List<T>> getAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation =CollectionValueDTO.class)))
    public ResponseEntity<T> getById(@PathVariable Long id){
        Optional<T> optionalT = service.findById(id);

        return optionalT.map(T ->
                new ResponseEntity<>(T, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<T> save(@RequestBody T body){
        return new ResponseEntity<>(service.save(body), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<T> optional = service.findById(id);
        optional.ifPresent(t -> service.delete(id));

        return optional.map(T ->
                new ResponseEntity<>("Object with the id " + id + " was deleted.",
                        HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(),
                        HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody T body ) {
        Optional<T> optional = service.findById(id);
        optional.ifPresent(n -> service.update(id, body));

        return optional.map(n ->
                new ResponseEntity<>("Object with id " + id + " was updated.", HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(),
                        HttpStatus.NOT_FOUND));
    }
}

