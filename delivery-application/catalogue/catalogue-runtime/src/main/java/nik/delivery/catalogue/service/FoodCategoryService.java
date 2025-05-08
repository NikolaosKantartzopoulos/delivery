package nik.delivery.catalogue.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import nik.delivery.catalogue.domain.FoodCategory;
import nik.delivery.catalogue.dto.CreateFoodCategoryRequestDto;
import nik.delivery.catalogue.dto.CreateFoodCategoryResponseDto;
import nik.delivery.catalogue.dto.FoodCategoryDto;
import nik.delivery.catalogue.dto.FoodCategoryListResponse;
import nik.delivery.catalogue.repository.FoodCategoryRepository;

@Service
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;
    private final ModelMapper modelMapper;

    public FoodCategoryService(FoodCategoryRepository foodCategoryRepository, ModelMapper modelMapper) {
        this.foodCategoryRepository = foodCategoryRepository;
        this.modelMapper = modelMapper;
    }

    public CreateFoodCategoryResponseDto createFoodCategory(CreateFoodCategoryRequestDto requestDto) {
        String displayName = capitalizeFirst(requestDto.getDisplayName()).trim();

        if (foodCategoryRepository.findByDisplayName(displayName).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Food category already exists");
        }

        FoodCategory foodCategory = modelMapper.map(requestDto, FoodCategory.class);
        foodCategory.setDisplayName(displayName);

        FoodCategory savedCategory = foodCategoryRepository.save(foodCategory);

        return new CreateFoodCategoryResponseDto(savedCategory.getFoodCategoryId());
    }

    public FoodCategoryListResponse getAllFoodCategories() {
        List<FoodCategoryDto> dtoList = foodCategoryRepository.findAll().stream()
            .map(el -> modelMapper.map(el, FoodCategoryDto.class))
            .toList();

        FoodCategoryListResponse response = new FoodCategoryListResponse();
        response.setItems(dtoList);
        response.setCount(dtoList.size());

        return response;
    }

    public void deleteFoodCategory(Long id) {
        foodCategoryRepository.deleteById(id);
    }

    public void deleteAllFoodCategories() {
        foodCategoryRepository.deleteAll();
    }

    private String capitalizeFirst(String input) {
        if (input == null || input.isBlank()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
