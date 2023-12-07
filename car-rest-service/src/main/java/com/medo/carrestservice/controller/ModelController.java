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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("car-rest-service/api/v1")
@Tag(name = "Model")
public class ModelController {

    private static final String DELETE_MASSAGE = "Model was successfuly deleted!";
    private final ModelService modelService;
  
    @Operation(
        summary = "Create model",
        description = "Saves model to Db",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Model.class)) }),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content)            
        }
        )
    @PostMapping("/private-scoped/models")
    public ResponseEntity<Model> saveModel(@RequestBody Model model) {
        return new ResponseEntity<>(modelService.saveBrand(model), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Get all models",
        description = "Returns the list of all existing models",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Model.class)) }),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content)             
        }
        )
    @GetMapping("/private/models")
    public ResponseEntity<List<Model>> getAllModels() {
        return new ResponseEntity<>(modelService.getAllModels(), HttpStatus.OK);
    }

    @Operation(
        summary = "Get model by Id",
        description = "Returns single model",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Model.class)) }),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid ID supplied",
                content = @Content),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content),
            @ApiResponse(
                responseCode = "404",
                description = "Model not found",
                content = @Content)            
        }
        )
    @GetMapping("/private-scoped/models/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable("id") long modelId) {
        return new ResponseEntity<>(modelService.getModelById(modelId), HttpStatus.OK);
    }

    @Operation(
        summary = "Update model",
        description = "Returns updated single model by id",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Model.class)) }),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid ID supplied",
                content = @Content),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content),
            @ApiResponse(
                responseCode = "404",
                description = "Model not found",
                content = @Content)            
        }
        )
    @PutMapping("/private-scoped/models/{id}")
    public ResponseEntity<Model> updateModel(@PathVariable("id") long modelId, @RequestBody Model model) {
        return new ResponseEntity<>(modelService.updateModel(model, modelId), HttpStatus.OK);
    }

    @Operation(
        summary = "Delete model by Id",
        description = "Deletes model single model by id from Db",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = {@Content(mediaType = "application/json", 
                schema = @Schema(implementation = Model.class)) }),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid ID supplied",
                content = @Content),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorised",
                content = @Content),
            @ApiResponse(
                responseCode = "404",
                description = "Model not found",
                content = @Content)            
        }
        )
    @DeleteMapping("/private-scoped/models/{id}")
    public ResponseEntity<String> deleteModel(@PathVariable("id") long modelId) {
        modelService.deleteModel(modelId);
        return new ResponseEntity<>(DELETE_MASSAGE, HttpStatus.OK);
    }

}
