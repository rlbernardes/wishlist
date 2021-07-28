package wishlist.service;

import br.com.loja.wishlist.entity.Wishlist;
import br.com.loja.wishlist.entity.WishlistProduct;
import br.com.loja.wishlist.repository.WishlistProductRepository;
import br.com.loja.wishlist.service.WishListProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import wishlist.DadosTeste;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WishlistProductServiceTest {

    @Mock
    WishlistProductRepository wishlistProductRepository;

    @InjectMocks
    WishListProductService wishListProductService;

    DadosTeste dadosTeste = new DadosTeste();

    @Test
    public void should_return_a_WishlistProduct(){

        Optional<WishlistProduct> wishlistProduct = Optional.ofNullable(dadosTeste.creatWishlistProduct());
        Wishlist wishlist = dadosTeste.creatWishlist();

        when(wishlistProductRepository.findByProductIdAndWishIdAndQuantityGreaterThan(1l, 1l, 0l)).thenReturn(wishlistProduct);

        WishlistProduct wp = wishListProductService.getWishlistProduct(1l, wishlist);

        assertThat(wishlistProduct.get().getProductId()).isSameAs(wp.getProductId());

    }

    @Test
    public void when_save_should_return_a_WishlistProduct(){

        Optional<WishlistProduct> wishlistProduct = Optional.ofNullable(dadosTeste.creatWishlistProduct());
        Wishlist wishlist = dadosTeste.creatWishlist();

        when( wishlistProductRepository.save(wishlistProduct.get())).thenReturn(wishlistProduct.get());

        WishlistProduct wp = wishListProductService.saveWishlistProduct(wishlistProduct.get());

        assertThat(wishlistProduct.get().getProductId()).isSameAs(wp.getProductId());

    }

    @Test
    public void when_findByWishId_should_return_a_list_WishlistProduct(){

        WishlistProduct wishlistProduct =dadosTeste.creatWishlistProduct();
        List<WishlistProduct> list = new ArrayList<>();

        list.add(wishlistProduct);

        when( wishlistProductRepository.findByWishId(1l)).thenReturn(list);

        List<WishlistProduct> wp = wishListProductService.getWishlistProducts(1l);

        assertThat(list.get(0).getProductId()).isSameAs(wp.get(0).getProductId());

    }

}
