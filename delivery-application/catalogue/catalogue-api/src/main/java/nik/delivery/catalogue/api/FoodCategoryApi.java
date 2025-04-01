package nik.delivery.catalogue.api;

import nik.delivery.catalogue.dto.CreateFoodCategoryRequestDto;
import nik.delivery.catalogue.dto.CreateFoodCategoryResponseDto;


public interface FoodCategoryApi {
    CreateFoodCategoryResponseDto createFoodCategory(CreateFoodCategoryRequestDto requestDto);
}
