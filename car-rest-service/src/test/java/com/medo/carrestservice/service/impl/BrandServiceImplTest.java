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
import com.medo.carrestservice.model.Brand;
import com.medo.carrestservice.repository.BrandRepository;

@SpringBootTest(classes = BrandServiceImpl.class)
class BrandServiceImplTest {

    private static final String NOT_FOUND = "Entity under provided id doesn`t exist";

    @Autowired
    private BrandServiceImpl brandServiceImpl;

    @MockBean
    private BrandRepository brandRepository;

    @MockBean
    private Brand testBrand;

    @Test
    void testBrandServiceImpl_saveBrand_shouldCallRepository_oneTime() {
        when(brandRepository.save(any(Brand.class))).thenReturn(testBrand);

        brandServiceImpl.saveBrand(testBrand);
        verify(brandRepository, times(1)).save(any(Brand.class));
        verifyNoMoreInteractions(brandRepository);
    }

    @Test
    void testBrandServiceImpl_getAllBrands_shouldCallRepository_oneTime() {
        brandServiceImpl.getAllBrands();

        verify(brandRepository, times(1)).findAll();
        verifyNoMoreInteractions(brandRepository);
    }

    @Test
    void testBrandServiceImpl_getBrandById_shouldCallRepository_oneTime() {
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(testBrand));

        brandServiceImpl.getBrandById(anyLong());
        verify(brandRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(brandRepository);
    }

    @Test
    void testBrandServiceImpl_getBrandById_shouldThrowServiceException_inCaseOfNoEntityFound() {
        Exception exception = assertThrows(ServiceException.class, () -> brandServiceImpl.getBrandById(1L));
        String actual = exception.getMessage();
        String expected = NOT_FOUND;

        assertEquals(expected, actual);
    }

    @Test
    void testBrandServiceImpl_updateBrand_shouldCallRepository_twiceInRightOrder() {
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(testBrand));
        when(brandRepository.save(any(Brand.class))).thenReturn(testBrand);

        brandServiceImpl.updateBrand(testBrand, 1L);

        verify(brandRepository, times(1)).findById(anyLong());
        verify(brandRepository, times(1)).save(any(Brand.class));
        verifyNoMoreInteractions(brandRepository);

        InOrder inOrder = Mockito.inOrder(brandRepository);
        inOrder.verify(brandRepository).findById(anyLong());
        inOrder.verify(brandRepository).save(any(Brand.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void testBrandServiceImpl_deleteBrand_shouldCallRepository_twiceInRightOrder() {
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(testBrand));
        brandServiceImpl.deleteBrand(anyLong());

        verify(brandRepository, times(1)).findById(anyLong());
        verify(brandRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(brandRepository);

        InOrder inOrder = Mockito.inOrder(brandRepository);
        inOrder.verify(brandRepository).findById(anyLong());
        inOrder.verify(brandRepository).deleteById(anyLong());
        inOrder.verifyNoMoreInteractions();
    }

}
