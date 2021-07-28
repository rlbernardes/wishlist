package br.com.loja.wishlist.controller;

import br.com.loja.wishlist.dto.ProductResponseData;
import br.com.loja.wishlist.dto.WishlistDTO;
import br.com.loja.wishlist.entity.Client;
import br.com.loja.wishlist.entity.Product;
import br.com.loja.wishlist.entity.Wishlist;
import br.com.loja.wishlist.entity.WishlistProduct;
import br.com.loja.wishlist.entity.enums.StatusWishlist;
import br.com.loja.wishlist.repository.ClientRepository;
import br.com.loja.wishlist.repository.WishlistRepository;
import br.com.loja.wishlist.service.ProductService;
import br.com.loja.wishlist.service.WishListProductService;
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
    WishListProductService wishListProductService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    ProductService productService;

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

        when(wishListProductService.getWishlistProduct(1l, wishlist.get())).thenReturn(wishlistProductOptional.get());

        when(clientRepository.findById(1l)).thenReturn(client);

        when(wishListProductService.saveWishlistProduct(any(WishlistProduct.class))).thenReturn(new WishlistProduct());

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
        Optional<WishlistProduct> wishlistProductOptional = Optional.ofNullable(dadosTeste.creatWishlistProduct());
        wishlistProducts.add(wishlistProduct);
        when(wishlistRepository.findByClientIdAndStatus(1l, StatusWishlist.ATIVO)).thenReturn(wishlist);

        when(wishListProductService.getWishlistProducts(1l)).thenReturn(wishlistProducts);

        when(productService.getProduct(1l)).thenReturn(product.get());

        when(wishListService.saveWishlist(wishlistDTO)).thenReturn(wishlistProduct);

        when(wishListProductService.getWishlistProduct(1l, wishlist.get())).thenReturn(wishlistProductOptional.get());



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

        when(wishListProductService.getWishlistProduct(1l, wishlist.get())).thenReturn(wishlistProductOptional.get());

        wishListService.deleteWishlistProduct(dadosTeste.createWishlistDeleteProductDTO());

    }

}
