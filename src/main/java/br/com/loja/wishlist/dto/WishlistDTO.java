package br.com.loja.wishlist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@SuperBuilder
@NoArgsConstructor
@Data
public class WishlistDTO {
    @NotNull(message = "É obrigatório o preenchimento do campo client.")
    private Long client;
    @NotNull(message = "É obrigatório o preenchimento do campo product.")
    private Long product;
    @NotNull(message = "É obrigatório o preenchimento do campo quantity.")
    @Min(value = 0, message = "O campo quantity deve ser maior do que zero")
    private Long quantity;
}
