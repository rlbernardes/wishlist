package br.com.loja.wishlist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Data
public class WishlistRequestDTO {
    @NotBlank
    List<WishlistDTO> wishlist;
}
