package com.medo.carrestservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medo.carrestservice.model.Model;
import com.medo.carrestservice.service.ModelService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/models")
public class ModelController {

    private static final String DELETE_MASSAGE = "Model was successfuly deleted!";
    private final ModelService modelService;

    @PostMapping()
    public ResponseEntity<Model> saveModel(@RequestBody Model model) {
        return new ResponseEntity<>(modelService.saveBrand(model), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Model>> getAllModels() {
        return new ResponseEntity<>(modelService.getAllModels(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable("id") long modelId) {
        return new ResponseEntity<>(modelService.getModelById(modelId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Model> updateModel(@PathVariable("id") long modelId, @RequestBody Model model) {
        return new ResponseEntity<>(modelService.updateModel(model, modelId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModel(@PathVariable("id") long modelId) {
        modelService.deleteModel(modelId);
        return new ResponseEntity<>(DELETE_MASSAGE, HttpStatus.OK);
    }

}
