package com.medo.carrestservice.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.medo.carrestservice.exception.ServiceException;
import com.medo.carrestservice.model.Model;
import com.medo.carrestservice.service.ModelService;

@WebMvcTest(ModelController.class)
class ModelControllerTest {

    @MockBean
    ModelService modelService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelController modelController;

    @Test
    void testModelController_getAllModels_shouldReturnValidResponceEntity() throws Exception {
        List<Model> expected = List.of(new Model(1, "testName1", null), new Model(2, "testName2", null));
        when(modelService.getAllModels()).thenReturn(expected);

        mockMvc.perform(get("/api/v1/models"))
                .andExpectAll(status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("""
                        [
                            {
                                "id":1,
                                "modelName":"testName1"
                            },
                            {
                                "id":2,
                                "modelName":"testName2"
                            }
                        ]
                         """));
    }

    @Test
    void testModelController_getAllModels_shouldCallServiceOnce() {
        when(modelService.getAllModels()).thenReturn(List.of());

        modelController.getAllModels();

        verify(modelService, times(1)).getAllModels();
        verifyNoMoreInteractions(modelService);
    }

    @Test
    void testModelController_getModelById_shouldReturnValidResponceEntity() throws Exception {
        Model model = new Model(3, "testName3", null);
        when(modelService.getModelById(anyLong())).thenReturn(model);

        mockMvc.perform(get("/api/v1/models/{id}", anyLong()))
                    .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("""
                        {
                            "id":3,
                            "modelName":"testName3"
                        }
                         """));
    }

    @Test
    void testModelController_getModelById_shouldFail_whenInvalidId() throws Exception {
        long id = 1L;
        when(modelService.getModelById(id)).thenThrow(new ServiceException(anyString()));

        mockMvc.perform(get("/api/v1/models/{id}", id))
                    .andExpectAll(
                status().isNotFound());
    }

    @Test
    void testModelController_getModelById_shouldCallServiceOnce() {
        when(modelService.getModelById(anyLong())).thenReturn(new Model(3, "testName3", null));

        modelController.getModelById(anyLong());

        verify(modelService, times(1)).getModelById(anyLong());
        verifyNoMoreInteractions(modelService);
    }

    @Test
    void testModelController_createModel_shouldReturnValidResponceEntity() throws Exception {
        mockMvc.perform(post("/api/v1/models")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "modelName": "testName2"
                }
                 """))
                .andExpect(status().isCreated());
    }

    @Test
    void testModelController_createModel_shouldCallServiceOnce() {
        Model model = new Model(3, "testName3", null);
        modelController.saveModel(model);

        verify(modelService, times(1)).saveBrand(model);
        verifyNoMoreInteractions(modelService);
    }

    @Test
    void testModelController_updateModel_shouldReturnValidResponceEntity() throws Exception {
        long id = 1L;
        mockMvc.perform(put("/api/v1/models/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "modelName": "testName2"
                }
                 """))
                .andExpect(status().isOk());
    }

    @Test
    void testModelController_updateModel_shouldCallServiceOnce() {
        Model model = new Model(3, "testName3", null);
        long id = model.getId();
        modelController.updateModel(id, model);

        verify(modelService, times(1)).updateModel(model, id);
        verifyNoMoreInteractions(modelService);
    }

    @Test
    void testModelController_deleteModel_shouldReturnValidResponceEntity() throws Exception {
        mockMvc.perform(delete("/api/v1/models/{id}", anyLong()))
                .andExpectAll(status().isOk(),
                content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")),
                content().string("Model was successfuly deleted!"));
    }

    @Test
    void testModelController_deleteModel_shouldCallServiceMethodsOnce() {
        modelController.deleteModel(anyLong());

        verify(modelService, times(1)).deleteModel(anyLong());
        verifyNoMoreInteractions(modelService);
    }

}
