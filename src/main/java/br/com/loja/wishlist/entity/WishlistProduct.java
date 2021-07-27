package br.com.loja.wishlist.entity;

import br.com.loja.wishlist.entity.embeddable.WishlistProductId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "tb_wishlistproduct")
@IdClass(WishlistProductId.class)
public class WishlistProduct {
    @Id
    private Long productId;
    @Id
    private Long wishId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "dtalter")
    private LocalDateTime dateTimeAlter;
}
