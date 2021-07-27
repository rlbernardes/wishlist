package br.com.loja.wishlist.dto;

import br.com.loja.wishlist.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class ProductResponseData {
    private Product product;
    private Long quantity;
    private float total;
}
