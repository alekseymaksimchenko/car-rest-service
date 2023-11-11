package com.medo.carrestservice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.medo.carrestservice.exception.ServiceException;
import com.medo.carrestservice.model.Model;
import com.medo.carrestservice.repository.ModelRepository;
import com.medo.carrestservice.service.ModelService;

@Service
public class ModelServiceImpl implements ModelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelServiceImpl.class);
    private static final String NOT_FOUND = "Entity under provided id doesn`t exist";
    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        super();
        this.modelRepository = modelRepository;
    }

    @Override
    public Model saveBrand(Model model) {
        LOGGER.debug("Model saved {}", model);
        return modelRepository.save(model);
    }

    @Override
    public List<Model> getAllModels() {
        List<Model> modelList = modelRepository.findAll();
        LOGGER.debug("{} Models in the list", modelList.size());
        return modelList;
    }

    @Override
    public Model getModelById(long modelId) throws ServiceException {
        Model model = modelRepository.findById(modelId).orElseThrow(() -> new ServiceException(NOT_FOUND));
        LOGGER.debug("Model under id=({}) was returned", modelId);
        return model;
    }

    @Override
    public Model updateModel(Model model, long modelId) {
        Model existingModel = getModelById(modelId);
        existingModel.setModelName(model.getModelName());
        existingModel.setBrand(model.getBrand());

        saveBrand(existingModel);
        LOGGER.debug("Model under id=({}) was updated ({} -> {}), ({} -> {})", modelId, existingModel.getModelName(),
                model.getModelName(), existingModel.getBrand(), model.getBrand());

        return existingModel;
    }

    @Override
    public void deleteModel(long modelId) {
        getModelById(modelId);
        modelRepository.deleteById(modelId);
        LOGGER.debug("Model under id=({}) was deleted", modelId);

    }

}
