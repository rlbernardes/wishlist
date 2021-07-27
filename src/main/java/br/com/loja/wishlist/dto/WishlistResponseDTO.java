package br.com.loja.wishlist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Data
public class WishlistResponseDTO {
    private List<ProductResponseData> wishlist;
}
