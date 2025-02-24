package nik.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFoodCategoryRequestDto {
    @Schema(description = "The display name of the food category")
    @NotBlank
    String displayName;
}
