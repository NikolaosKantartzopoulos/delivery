package nik.delivery.catalogue.api;

import nik.delivery.catalogue.dto.CreateFoodCategoryRequestDto;
import nik.delivery.catalogue.dto.CreateFoodCategoryResponseDto;
import nik.delivery.catalogue.dto.FoodCategoryListResponse;

public interface FoodCategoryApi {
    CreateFoodCategoryResponseDto createFoodCategory(CreateFoodCategoryRequestDto requestDto);

    FoodCategoryListResponse getAllFoodCategories();

    void deleteFoodCategory(Long id);

    void deleteAllFoodCategories();
}
