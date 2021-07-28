package br.com.loja.wishlist.service;

import br.com.loja.wishlist.entity.Wishlist;
import br.com.loja.wishlist.entity.WishlistProduct;
import br.com.loja.wishlist.exception.BusinessRunTimeException;
import br.com.loja.wishlist.message.MessageError;
import br.com.loja.wishlist.repository.ClientRepository;
import br.com.loja.wishlist.repository.ProductRepository;
import br.com.loja.wishlist.repository.WishlistProductRepository;
import br.com.loja.wishlist.repository.WishlistRepository;
import br.com.loja.wishlist.util.Translator;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@NoArgsConstructor
public class WishListProductService {
    private WishlistProductRepository wishlistProductRepository;
    private ProductRepository productRepository;
    private ClientRepository clientRepository;

    @Autowired
    public WishListProductService( WishlistProductRepository wishlistProductRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.wishlistProductRepository = wishlistProductRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    public WishlistProduct getWishlistProduct(Long productId, Wishlist wishlist){
        try {
            Optional<WishlistProduct> wishlistProduct = wishlistProductRepository.findByProductIdAndWishIdAndQuantityGreaterThan(productId, wishlist.getId(), 0l);
            if(wishlistProduct.isPresent()){
                return wishlistProduct.get();
            }
        }catch (BusinessRunTimeException e){
            String messageComplment = "";
            if(wishlist.getClient() != null){
                messageComplment = "\n Cliente: " + wishlist.getClient().getId();
            }
            messageComplment += "\n Produto: " + productId;
            throw new BusinessRunTimeException(Translator.toLocale(MessageError.MSG_WISHLISTDETAIL_FINDFAIL.getMensagem()) + messageComplment , e);
        }
        return null;
    }

    public WishlistProduct saveWishlistProduct(WishlistProduct wishlistProduct){
        return wishlistProductRepository.save(wishlistProduct);
    }

    public List<WishlistProduct> getWishlistProducts (Long wishListID) {
        return wishlistProductRepository.findByWishId(wishListID);
    }

}
