package pt.ulusofona.cd.restaurant.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemRequest {
    @NotBlank(message = "O nome do prato é obrigatório")
    @Size(max = 255, message = "O nome do prato não pode ter mais de 255 caracteres")
    private String name;

    @Size(max = 2000, message = "A descrição não pode ter mais de 2000 caracteres")
    private String description;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.00", message = "O preço deve ser maior ou igual a 0.00")
    private BigDecimal price;

    @Size(max = 10, message = "A moeda não pode ter mais de 10 caracteres")
    private String currency;
}
