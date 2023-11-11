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
import com.medo.carrestservice.model.Category;
import com.medo.carrestservice.repository.CategoryRepository;

@SpringBootTest(classes = CategoryServiceImpl.class)
class CategoryServiceImplTest {

    private static final String NOT_FOUND = "Entity under provided id doesn`t exist";

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private Category testCategory;

    @Test
    void testCategoryServiceImpl_saveCategory_shouldCallRepository_oneTime() {
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        categoryServiceImpl.saveCategory(testCategory);
        verify(categoryRepository, times(1)).save(any(Category.class));
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void testCategoryServiceImpl_getAllCategorys_shouldCallRepository_oneTime() {
        categoryServiceImpl.getAllCategory();

        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void testCategoryServiceImpl_getCategoryById_shouldCallRepository_oneTime() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(testCategory));

        categoryServiceImpl.getCategoryById(anyLong());
        verify(categoryRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void testCategoryServiceImpl_getCategoryById_shouldThrowServiceException_inCaseOfNoEntityFound() {
        Exception exception = assertThrows(ServiceException.class, () -> categoryServiceImpl.getCategoryById(1L));
        String actual = exception.getMessage();
        String expected = NOT_FOUND;

        assertEquals(expected, actual);
    }

    @Test
    void testCategoryServiceImpl_updateCategory_shouldCallRepository_twiceInRightOrder() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(testCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        categoryServiceImpl.updateCategory(testCategory, 1L);

        verify(categoryRepository, times(1)).findById(anyLong());
        verify(categoryRepository, times(1)).save(any(Category.class));
        verifyNoMoreInteractions(categoryRepository);

        InOrder inOrder = Mockito.inOrder(categoryRepository);
        inOrder.verify(categoryRepository).findById(anyLong());
        inOrder.verify(categoryRepository).save(any(Category.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void testCategoryServiceImpl_deleteCategory_shouldCallRepository_twiceInRightOrder() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(testCategory));
        categoryServiceImpl.deleteCategory(anyLong());

        verify(categoryRepository, times(1)).findById(anyLong());
        verify(categoryRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(categoryRepository);

        InOrder inOrder = Mockito.inOrder(categoryRepository);
        inOrder.verify(categoryRepository).findById(anyLong());
        inOrder.verify(categoryRepository).deleteById(anyLong());
        inOrder.verifyNoMoreInteractions();
    }

}
