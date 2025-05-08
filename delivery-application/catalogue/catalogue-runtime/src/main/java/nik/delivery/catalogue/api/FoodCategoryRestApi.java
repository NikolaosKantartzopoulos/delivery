package nik.delivery.catalogue.api;

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

@RestController()
@RequestMapping("/api/food-category")
public class FoodCategoryRestApi {

    private final FoodCategoryApi foodCategoryApi;

    public FoodCategoryRestApi(FoodCategoryApi foodCategoryApi) {
        this.foodCategoryApi = foodCategoryApi;
    }

    @PostMapping()
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Food category added. Database id returned as a string",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreateFoodCategoryResponseDto.class))),
        @ApiResponse(responseCode = "409", description = "Conflict: Food category exists",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))})
    public CreateFoodCategoryResponseDto createFoodCategory(@Valid @RequestBody CreateFoodCategoryRequestDto requestDto) {
        return foodCategoryApi.createFoodCategory(requestDto);
    }

    @GetMapping
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Returns a list of food category items",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = FoodCategoryDto.class))))})
    public FoodCategoryListResponse getFoodCategoryList() {
        return foodCategoryApi.getAllFoodCategories();
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Food category successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Food category not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodCategory(@PathVariable Long id) {
        foodCategoryApi.deleteFoodCategory(id);
    }

    @DeleteMapping
    @ApiResponses({@ApiResponse(responseCode = "204", description = "All food categories successfully deleted")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllFoodCategories() {
        foodCategoryApi.deleteAllFoodCategories();
    }
}
