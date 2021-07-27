package br.com.loja.wishlist.repository;

import br.com.loja.wishlist.entity.Wishlist;
import br.com.loja.wishlist.entity.enums.StatusWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long>{
    Optional<Wishlist> findByClientIdAndStatus(Long clientId, StatusWishlist statusWishlist);
}
