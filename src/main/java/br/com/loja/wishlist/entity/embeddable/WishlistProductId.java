package br.com.loja.wishlist.entity.embeddable;

import java.io.Serializable;
import java.util.Objects;


public class WishlistProductId implements Serializable {
    private Long productId;
    private Long wishId;

    public WishlistProductId() {
    }

    public WishlistProductId(Long productId, Long wishId) {
        this.productId = productId;
        this.wishId = wishId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getWishId() {
        return wishId;
    }

    public void setWishId(Long wishId) {
        this.wishId = wishId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishlistProductId that = (WishlistProductId) o;
        return Objects.equals(productId, that.productId) && Objects.equals(wishId, that.wishId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, wishId);
    }
}
