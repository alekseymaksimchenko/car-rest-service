package com.medo.carrestservice.service;

import java.util.List;

import com.medo.carrestservice.model.Model;

public interface ModelService {

    Model saveBrand(Model model);

    List<Model> getAllModels();

    Model getModelById(long modelId);

    Model updateModel(Model model, long modelId);

    void deleteModel(long modelId);
}
