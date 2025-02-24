package nik.delivery.dto;

import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateFoodCategoryRequestDto {
    @Schema(description = "The display name of the food category")
    @NotBlank
    String displayName;
}
