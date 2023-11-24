package com.medo.carrestservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.medo.carrestservice.exception.ServiceException;
import com.medo.carrestservice.model.Model;
import com.medo.carrestservice.repository.ModelRepository;

@SpringBootTest(classes = ModelServiceImpl.class)
class ModelServiceImplTest {

    private static final String NOT_FOUND = "Entity under provided id doesn`t exist";

    @Autowired
    private ModelServiceImpl modelServiceImpl;

    @MockBean
    private ModelRepository modelRepository;

    @MockBean
    private Model testModel;

    @Test
    void testModelServiceImpl_saveModel_shouldCallRepository_oneTime() {
        when(modelRepository.save(any(Model.class))).thenReturn(testModel);

        modelServiceImpl.saveBrand(testModel);
        verify(modelRepository, times(1)).save(any(Model.class));
        verifyNoMoreInteractions(modelRepository);
    }

    @Test
    void testModelServiceImpl_getAllModels_shouldCallRepository_oneTime() {
        modelServiceImpl.getAllModels();

        verify(modelRepository, times(1)).findAll();
        verifyNoMoreInteractions(modelRepository);
    }

    @Test
    void testModelServiceImpl_getModelById_shouldCallRepository_oneTime() {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.of(testModel));

        modelServiceImpl.getModelById(anyLong());
        verify(modelRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(modelRepository);
    }

    @Test
    void testModelServiceImpl_getModelById_shouldThrowServiceException_inCaseOfNoEntityFound() {
        Exception exception = assertThrows(ServiceException.class, () -> modelServiceImpl.getModelById(1L));
        String actual = exception.getMessage();
        String expected = NOT_FOUND;

        assertEquals(expected, actual);
    }

    @Test
    void testModelServiceImpl_updateModel_shouldCallRepository_twiceInRightOrder() {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.of(testModel));
        when(modelRepository.save(any(Model.class))).thenReturn(testModel);

        modelServiceImpl.updateModel(testModel, 1L);

        verify(modelRepository, times(1)).findById(anyLong());
        verify(modelRepository, times(1)).save(any(Model.class));
        verifyNoMoreInteractions(modelRepository);

        InOrder inOrder = Mockito.inOrder(modelRepository);
        inOrder.verify(modelRepository).findById(anyLong());
        inOrder.verify(modelRepository).save(any(Model.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void testModelServiceImpl_deleteModel_shouldCallRepository_twiceInRightOrder() {
        when(modelRepository.findById(anyLong())).thenReturn(Optional.of(testModel));
        modelServiceImpl.deleteModel(anyLong());

        verify(modelRepository, times(1)).findById(anyLong());
        verify(modelRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(modelRepository);

        InOrder inOrder = Mockito.inOrder(modelRepository);
        inOrder.verify(modelRepository).findById(anyLong());
        inOrder.verify(modelRepository).deleteById(anyLong());
        inOrder.verifyNoMoreInteractions();
    }

}
