package br.com.loja.wishlist.controller;

import br.com.loja.wishlist.dto.ProductResponseData;
import br.com.loja.wishlist.dto.WishlistDTO;
import br.com.loja.wishlist.entity.Client;
import br.com.loja.wishlist.entity.Product;
import br.com.loja.wishlist.entity.Wishlist;
import br.com.loja.wishlist.entity.WishlistProduct;
import br.com.loja.wishlist.entity.enums.StatusWishlist;
import br.com.loja.wishlist.repository.ClientRepository;
import br.com.loja.wishlist.repository.ProductRepository;
import br.com.loja.wishlist.repository.WishlistProductRepository;
import br.com.loja.wishlist.repository.WishlistRepository;
import br.com.loja.wishlist.service.WishListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WishlistServiceTest {
    @Mock
    WishlistRepository wishlistRepository;

    @Mock
    WishlistProductRepository wishlistProductRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    WishListService wishListService;

    @Test
    public void when_save_whislist_it_should_return_wishlist(){
        DadosTeste dadosTeste = new DadosTeste();
        WishlistProduct wishlistProduct = dadosTeste.creatWishlistProduct();
        WishlistDTO wishlistDTO = dadosTeste.createWishlistDTO();
        Optional<Client> client = Optional.ofNullable(dadosTeste.creatClient());
        Optional<WishlistProduct> wishlistProductOptional = Optional.ofNullable(dadosTeste.creatWishlistProduct());
        Optional<Wishlist> wishlist = Optional.ofNullable(dadosTeste.creatWishlist());

        when(wishlistRepository.findByClientIdAndStatus(1l, StatusWishlist.ATIVO)).thenReturn(wishlist);

        when(wishlistProductRepository.findByProductIdAndWishIdAndQuantityGreaterThan(1l, 1l,0l)).thenReturn(wishlistProductOptional);

        when(clientRepository.findById(1l)).thenReturn(client);

        when(wishlistProductRepository.save(any(WishlistProduct.class))).thenReturn(new WishlistProduct());

        when(wishListService.saveWishlist(wishlistDTO)).thenReturn(wishlistProduct);

        WishlistProduct wishlistCreated = wishListService.saveWishlist(wishlistDTO);

        assertThat(wishlistCreated.getProductId()).isSameAs(wishlistProduct.getProductId());

    }

    @Test
    public void when_get_whislist_it_should_return_wishlist(){
        DadosTeste dadosTeste = new DadosTeste();

        WishlistDTO wishlistDTO = dadosTeste.createWishlistDTO();
        WishlistProduct wishlistProduct = dadosTeste.creatWishlistProduct();
        Optional<Wishlist> wishlist = Optional.ofNullable(dadosTeste.creatWishlist());
        Optional<Product> product = Optional.ofNullable(dadosTeste.creatProduct());
        List<ProductResponseData> productResponseDataList = dadosTeste.createProductsResponse();
        List<WishlistProduct> wishlistProducts = new ArrayList<>();
        wishlistProducts.add(wishlistProduct);
        when(wishlistRepository.findByClientIdAndStatus(1l, StatusWishlist.ATIVO)).thenReturn(wishlist);

        when(wishlistProductRepository.findByWishId(1l)).thenReturn(wishlistProducts);

        when(productRepository.findById(1l)).thenReturn(product);

        when(wishListService.saveWishlist(wishlistDTO)).thenReturn(wishlistProduct);

        List<ProductResponseData> responseDataList = wishListService.findWishlist(1l);

        assertThat(productResponseDataList.get(0).getProduct().getId()).isSameAs(responseDataList.get(0).getProduct().getId());

    }

    @Test
    public void when_delete_whislist_it_should_delete_wishlist(){
        DadosTeste dadosTeste = new DadosTeste();

        Optional<Wishlist> wishlist = Optional.ofNullable(dadosTeste.creatWishlist());

        when(wishlistRepository.findByClientIdAndStatus(1l, StatusWishlist.ATIVO)).thenReturn(wishlist);

        wishListService.deleteWishlist(1l);

    }

    @Test
    public void when_delete_whislist_it_should_delete_wishlistProduct(){
        DadosTeste dadosTeste = new DadosTeste();

        Optional<Wishlist> wishlist = Optional.ofNullable(dadosTeste.creatWishlist());
        Optional<WishlistProduct> wishlistProductOptional = Optional.ofNullable(dadosTeste.creatWishlistProduct());

        when(wishlistRepository.findByClientIdAndStatus(1l, StatusWishlist.ATIVO)).thenReturn(wishlist);

        when(wishlistProductRepository.findByProductIdAndWishIdAndQuantityGreaterThan(1l, 1l,0l)).thenReturn(wishlistProductOptional);

        wishListService.deleteWishlistProduct(dadosTeste.createWishlistDeleteProductDTO());

    }

}
