package wishlist;

import br.com.loja.wishlist.dto.ProductResponseData;
import br.com.loja.wishlist.dto.WishlistDTO;
import br.com.loja.wishlist.dto.WishlistDeleteProductDTO;
import br.com.loja.wishlist.entity.Client;
import br.com.loja.wishlist.entity.Product;
import br.com.loja.wishlist.entity.Wishlist;
import br.com.loja.wishlist.entity.WishlistProduct;
import br.com.loja.wishlist.entity.enums.StatusWishlist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DadosTeste {
    
    public Product creatProduct(){
        return Product.builder()
                .id(1l)
                .descrption("Lança Espiritual Chastiefol")
                .price(10.0f)
                .build();
    }
    public Client creatClient(){
        return Client.builder()
                .id(1l)
                .name("king")
                .build();
    }
    public Wishlist creatWishlist(){
        return Wishlist.builder()
                .id(1l)
                .client(creatClient())
                .dateTimeAlter(LocalDateTime.now())
                .status(StatusWishlist.ATIVO)
                .build();
    }

    public WishlistProduct creatWishlistProduct(){
        return WishlistProduct.builder()
                .productId(1l)
                .dateTimeAlter(LocalDateTime.now())
                .quantity(2l)
                .wishId(1l)
                .build();
    }

    public WishlistDTO createWishlistDTO(){
        return WishlistDTO.builder().product(1l).quantity(3l).client(1l).build();
    }

    public List<ProductResponseData> createProductsResponse(){
        Product product1 = Product.builder().id(1l).descrption("Lança Espiritual Chastiefol").price(10.0f).build();
        Product product2 = Product.builder().id(2l).descrption("Nonatsu no taizai").price(50.0f).build();

        ProductResponseData productResponse1 = ProductResponseData.builder().product(product1).quantity(1l).total(10.0f).build();
        ProductResponseData productResponse2 = ProductResponseData.builder().product(product2).quantity(3l).total(150.0f).build();

        List<ProductResponseData> productsResponse = new ArrayList<>();
        productsResponse.add(productResponse1);
        productsResponse.add(productResponse2);

        return productsResponse;
    }

    public WishlistDeleteProductDTO createWishlistDeleteProductDTO(){
        return WishlistDeleteProductDTO.builder().product(1l).client(1l).build();
    }
}
