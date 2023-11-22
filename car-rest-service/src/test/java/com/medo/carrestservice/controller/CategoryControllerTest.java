package com.medo.carrestservice.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
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
import com.medo.carrestservice.model.Category;
import com.medo.carrestservice.service.CategoryService;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @MockBean
    CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryController categoryController;

    @Test
    void testCategoryController_getAllCategory_shouldReturnValidResponceEntity() throws Exception {
        List<Category> expected = List.of(new Category("testName1"), new Category("testName2"));
        when(categoryService.getAllCategory()).thenReturn(expected);

        mockMvc.perform(get("/car-rest-service/api/v1/private/categories")
                .with(jwt()))
                .andExpectAll(status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("""
                        [
                            {
                                "categoryName":"testName1"
                            },
                            {
                                "categoryName":"testName2"
                            }
                        ]
                         """));
    }

    @Test
    void testCategoryController_getAllCategory_shouldCallServiceOnce() {
        when(categoryService.getAllCategory()).thenReturn(List.of());

        categoryController.getAllCategory();

        verify(categoryService, times(1)).getAllCategory();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void testCategoryController_getCategoryById_shouldReturnValidResponceEntity() throws Exception {
        Category category = new Category("testName3");
        when(categoryService.getCategoryById(anyLong())).thenReturn(category);

        mockMvc.perform(get("/car-rest-service/api/v1/private-scoped/categories/{id}", anyLong())
                .with(jwt()))
                    .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("""
                        {
                            "categoryName":"testName3"
                        }
                         """));
    }

    @Test
    void testCategoryController_getCategoryById_shouldFail_whenInvalidId() throws Exception {
        long id = 1L;
        when(categoryService.getCategoryById(id)).thenThrow(new ServiceException(anyString()));

        mockMvc.perform(get("/car-rest-service/api/v1/private-scoped/categories/{id}", id)
                    .with(jwt()))
                    .andExpectAll(status().isNotFound());
    }

    @Test
    void testCategoryController_getCategoryById_shouldCallServiceOnce() {
        when(categoryService.getCategoryById(anyLong())).thenReturn(new Category("testName3"));

        categoryController.getCategoryById(anyLong());

        verify(categoryService, times(1)).getCategoryById(anyLong());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void testCategoryController_createCategory_shouldReturnValidResponceEntity() throws Exception {
        mockMvc.perform(post("/car-rest-service/api/v1/private-scoped/categories")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "categoryName": "testName2"
                }
                 """))
                .andExpect(status().isCreated());
    }

    @Test
    void testCategoryController_createCategory_shouldCallServiceOnce() {
        Category category = new Category("testName3");
        categoryController.saveCategory(category);

        verify(categoryService, times(1)).saveCategory(category);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void testCategoryController_updateCategory_shouldReturnValidResponceEntity() throws Exception {
        long id = 1L;
        mockMvc.perform(put("/car-rest-service/api/v1/private-scoped/categories/{id}", id)
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "categoryName": "testName2"
                }
                 """))
                .andExpect(status().isOk());
    }

    @Test
    void testCategoryController_updateCategory_shouldCallServiceOnce() {
        Category category = new Category("testName3");
        long id = category.getId();
        categoryController.updateCategory(id, category);

        verify(categoryService, times(1)).updateCategory(category, id);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void testCategoryController_deleteCategory_shouldReturnValidResponceEntity() throws Exception {
        mockMvc.perform(delete("/car-rest-service/api/v1/private-scoped/categories/{id}", anyLong())
                .with(jwt()))
                .andExpectAll(status().isOk(),
                content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")),
                content().string("Category was successfuly deleted!"));
    }

    @Test
    void testCategoryController_deleteCategory_shouldCallServiceMethodsOnce() {
        categoryController.deleteCategory(anyLong());

        verify(categoryService, times(1)).deleteCategory(anyLong());
        verifyNoMoreInteractions(categoryService);
    }

}
