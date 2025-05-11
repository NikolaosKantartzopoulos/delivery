package nik.delivery.catalogue.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import nik.delivery.catalogue.dto.CreateFoodCategoryRequestDto;
import nik.delivery.catalogue.dto.CreateFoodCategoryResponseDto;
import nik.delivery.catalogue.dto.FoodCategoryDto;
import nik.delivery.catalogue.dto.FoodCategoryListResponse;

@RestController
@RequestMapping("/api/food-category")
public class FoodCategoryRestApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodCategoryRestApi.class);

    private final FoodCategoryApi foodCategoryApi;

    private final Counter createCategoryCounter;
    private final Counter getCategoryListCounter;
    private final Counter deleteCategoryCounter;
    private final Counter deleteAllCategoriesCounter;

    public FoodCategoryRestApi(FoodCategoryApi foodCategoryApi, MeterRegistry meterRegistry) {
        this.foodCategoryApi = foodCategoryApi;

        this.createCategoryCounter = Counter.builder("api_food_category_create")
            .tag("action", "create")
            .description("Create a new food category")
            .register(meterRegistry);

        this.getCategoryListCounter = Counter.builder("api_food_category_get_all")
            .tag("action", "get_all")
            .description("Get all food categories")
            .register(meterRegistry);

        this.deleteCategoryCounter = Counter.builder("api_food_category_delete")
            .tag("action", "delete")
            .description("Delete a specific food category")
            .register(meterRegistry);

        this.deleteAllCategoriesCounter = Counter.builder("api_food_category_delete_all")
            .tag("action", "delete_all")
            .description("Delete all food categories")
            .register(meterRegistry);
    }

    @PostMapping
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Food category added. Database id returned as a string",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreateFoodCategoryResponseDto.class))),
        @ApiResponse(responseCode = "409", description = "Conflict: Food category exists",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    })
    public CreateFoodCategoryResponseDto createFoodCategory(@Valid @RequestBody CreateFoodCategoryRequestDto requestDto) {
        LOGGER.info("Creating food category");
        createCategoryCounter.increment();
        return foodCategoryApi.createFoodCategory(requestDto);
    }

    @GetMapping
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Returns a list of food category items",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = FoodCategoryDto.class))))
    })
    public FoodCategoryListResponse getFoodCategoryList() {
        LOGGER.info("Getting all food categories");
        getCategoryListCounter.increment();
        return foodCategoryApi.getAllFoodCategories();
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Food category successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Food category not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodCategory(@PathVariable Long id) {
        LOGGER.info("Deleting food category with ID: {}", id);
        deleteCategoryCounter.increment();
        foodCategoryApi.deleteFoodCategory(id);
    }

    @DeleteMapping
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "All food categories successfully deleted")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllFoodCategories() {
        LOGGER.info("Deleting all food categories");
        deleteAllCategoriesCounter.increment();
        foodCategoryApi.deleteAllFoodCategories();
    }
}
