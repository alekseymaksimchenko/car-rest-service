package com.medo.carrestservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medo.carrestservice.exception.ServiceException;
import com.medo.carrestservice.model.Model;
import com.medo.carrestservice.repository.ModelRepository;
import com.medo.carrestservice.service.ModelService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ModelServiceImpl implements ModelService {

    private static final String NOT_FOUND = "Entity under provided id doesn`t exist";
    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        super();
        this.modelRepository = modelRepository;
    }

    @Override
    public Model saveBrand(Model model) {
        log.debug("Model saved {}", model);
        return modelRepository.save(model);
    }

    @Override
    public List<Model> getAllModels() {
        List<Model> modelList = modelRepository.findAll();
        log.debug("{} Models in the list", modelList.size());
        return modelList;
    }

    @Override
    public Model getModelById(long modelId) throws ServiceException {
        Model model = modelRepository.findById(modelId).orElseThrow(() -> new ServiceException(NOT_FOUND));
        log.debug("Model under id=({}) was returned", modelId);
        return model;
    }

    @Override
    public Model updateModel(Model model, long modelId) {
        Model existingModel = getModelById(modelId);
        existingModel.setModelName(model.getModelName());
        existingModel.setBrand(model.getBrand());

        saveBrand(existingModel);
        log.debug("Model under id=({}) was updated {}", modelId, existingModel);

        return existingModel;
    }

    @Override
    public void deleteModel(long modelId) {
        getModelById(modelId);
        modelRepository.deleteById(modelId);
        log.debug("Model under id=({}) was deleted", modelId);

    }

}
