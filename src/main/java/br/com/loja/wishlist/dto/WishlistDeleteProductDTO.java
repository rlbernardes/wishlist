package br.com.loja.wishlist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@SuperBuilder
@NoArgsConstructor
@Data
public class WishlistDeleteProductDTO {
    @NotNull(message = "É obrigatório o preenchimento do campo client.")
    private Long client;
    @NotNull(message = "É obrigatório o preenchimento do campo product.")
    private Long product;
}
