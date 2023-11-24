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
import com.medo.carrestservice.model.Brand;
import com.medo.carrestservice.service.BrandService;

@WebMvcTest(BrandController.class)
class BrandControllerTest {

    @MockBean
    BrandService brandService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BrandController brandController;

    @Test
    void testBrandController_getAllBrands_shouldReturnValidResponceEntity() throws Exception {
        List<Brand> expected = List.of(new Brand("testName1"), new Brand("testName2"));
        when(brandService.getAllBrands()).thenReturn(expected);

        mockMvc.perform(get("/car-rest-service/api/v1/private/brands")
                .with(jwt()))
                .andExpectAll(status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("""
                        [
                            {
                                "brandName":"testName1"
                            },
                            {
                                "brandName":"testName2"
                            }
                        ]
                         """));
    }

    @Test
    void testBrandController_getAllBrands_shouldCallServiceOnce() {
        when(brandService.getAllBrands()).thenReturn(List.of());

        brandController.getAllBrands();

        verify(brandService, times(1)).getAllBrands();
        verifyNoMoreInteractions(brandService);
    }

    @Test
    void testBrandController_getBrandById_shouldReturnValidResponceEntity() throws Exception {
        Brand brand = new Brand("testName3");
        when(brandService.getBrandById(anyLong())).thenReturn(brand);

        mockMvc.perform(get("/car-rest-service/api/v1/private-scoped/brands/{id}", anyLong())
                .with(jwt()))
                .andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json("""
                        {
                            "brandName":"testName3"
                        }
                         """));
    }

    @Test
    void testBrandController_getBrandById_shouldFail_whenInvalidId() throws Exception {
        long id = 1L;
        when(brandService.getBrandById(id)).thenThrow(new ServiceException(anyString()));

        mockMvc.perform(get("/car-rest-service/api/v1/private-scoped/brands/{id}", id)
                 .with(jwt()))
                 .andExpectAll(
                status().isNotFound());
    }

    @Test
    void testBrandController_getBrandById_shouldCallServiceOnce() {
        when(brandService.getBrandById(anyLong())).thenReturn(new Brand("testName3"));

        brandController.getBrandById(anyLong());

        verify(brandService, times(1)).getBrandById(anyLong());
        verifyNoMoreInteractions(brandService);
    }

    @Test
    void testBrandController_createBrand_shouldReturnValidResponceEntity() throws Exception {
        mockMvc.perform(post("/car-rest-service/api/v1/private-scoped/brands")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "brandName": "testName2"
                }
                 """))
                .andExpect(status().isCreated());
    }

    @Test
    void testBrandController_createBrand_shouldCallServiceOnce() {
        Brand brand = new Brand("testName3");
        brandController.saveBrand(brand);

        verify(brandService, times(1)).saveBrand(brand);
        verifyNoMoreInteractions(brandService);
    }

    @Test
    void testBrandController_updateBrand_shouldReturnValidResponceEntity() throws Exception {
        long id = 1L;
        mockMvc.perform(put("/car-rest-service/api/v1/private-scoped/brands/{id}", id)
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "brandName": "testName2"
                }
                 """))
                .andExpect(status().isOk());
    }

    @Test
    void testBrandController_updateBrand_shouldCallServiceOnce() {
        Brand brand = new Brand("testName3");
        long id = brand.getId();
        brandController.updateBrand(id, brand);

        verify(brandService, times(1)).updateBrand(brand, id);
        verifyNoMoreInteractions(brandService);
    }

    @Test
    void testBrandController_deleteBrand_shouldReturnValidResponceEntity() throws Exception {
        mockMvc.perform(delete("/car-rest-service/api/v1/private-scoped/brands/{id}", anyLong())
                .with(jwt()))
                .andExpectAll(status().isOk(),
                content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")),
                content().string("Brand was successfuly deleted!"));
    }

    @Test
    void testBrandController_deleteBrand_shouldCallServiceMethodsOnce() {
        brandController.deleteBrand(anyLong());

        verify(brandService, times(1)).deleteBrand(anyLong());
        verifyNoMoreInteractions(brandService);
    }

}
