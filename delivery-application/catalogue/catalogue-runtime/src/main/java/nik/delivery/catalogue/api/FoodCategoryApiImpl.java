package nik.delivery.catalogue.api;

import org.springframework.stereotype.Component;

import nik.delivery.catalogue.dto.CreateFoodCategoryRequestDto;
import nik.delivery.catalogue.dto.CreateFoodCategoryResponseDto;
import nik.delivery.catalogue.dto.FoodCategoryListResponse;
import nik.delivery.catalogue.service.FoodCategoryService;

@Component
public class FoodCategoryApiImpl implements FoodCategoryApi {

    private final FoodCategoryService foodCategoryService;

    public FoodCategoryApiImpl(FoodCategoryService foodCategoryService) {
        this.foodCategoryService = foodCategoryService;
    }

    @Override
    public CreateFoodCategoryResponseDto createFoodCategory(CreateFoodCategoryRequestDto requestDto) {
        return foodCategoryService.createFoodCategory(requestDto);
    }

    @Override
    public FoodCategoryListResponse getAllFoodCategories() {
        return foodCategoryService.getAllFoodCategories();
    }

    @Override
    public void deleteFoodCategory(Long id) {
        foodCategoryService.deleteFoodCategory(id);
    }

    @Override
    public void deleteAllFoodCategories() {
        foodCategoryService.deleteAllFoodCategories();
    }

}
