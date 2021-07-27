package br.com.loja.wishlist.repository;

import br.com.loja.wishlist.entity.WishlistProduct;
import br.com.loja.wishlist.entity.embeddable.WishlistProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistProductRepository extends JpaRepository<WishlistProduct, WishlistProductId>{
    Optional<WishlistProduct> findByProductIdAndWishIdAndQuantityGreaterThan(Long productId, Long wishId, Long quantity);
    List<WishlistProduct> findByWishId(Long wishId);
}
