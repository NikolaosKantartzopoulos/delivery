package nik.delivery.api;

import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import nik.delivery.dto.CreateFoodCategoryRequestDto;
import nik.delivery.dto.CreateFoodCategoryResponseDto;

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
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = CreateFoodCategoryResponseDto.class))),
        @ApiResponse(responseCode = "409", description = "Conflict: Food category exists",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProblemDetail.class)))
    }
    )
    public CreateFoodCategoryResponseDto createFoodCategory(
        @Valid @RequestBody CreateFoodCategoryRequestDto requestDto
    ) {
        return foodCategoryApi.createFoodCategory(requestDto);
    }

}
