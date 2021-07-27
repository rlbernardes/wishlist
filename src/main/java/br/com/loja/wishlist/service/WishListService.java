package br.com.loja.wishlist.service;


import br.com.loja.wishlist.dto.ProductResponseData;
import br.com.loja.wishlist.dto.WishlistDTO;
import br.com.loja.wishlist.dto.WishlistDeleteProductDTO;
import br.com.loja.wishlist.entity.Client;
import br.com.loja.wishlist.entity.Product;
import br.com.loja.wishlist.entity.Wishlist;
import br.com.loja.wishlist.exception.BusinessRunTimeException;
import br.com.loja.wishlist.message.MessageError;
import br.com.loja.wishlist.repository.ClientRepository;
import br.com.loja.wishlist.repository.ProductRepository;
import br.com.loja.wishlist.repository.WishlistRepository;
import br.com.loja.wishlist.util.Translator;
import br.com.loja.wishlist.entity.WishlistProduct;
import br.com.loja.wishlist.entity.enums.StatusWishlist;
import br.com.loja.wishlist.repository.WishlistProductRepository;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Log4j2
@Service
@NoArgsConstructor
public class WishListService {

    private WishlistRepository wishlistRepository;
    private WishlistProductRepository wishlistProductRepository;
    private ProductRepository productRepository;
    private ClientRepository clientRepository;

    @Autowired
    public WishListService(WishlistRepository wishlistRepository, WishlistProductRepository wishlistProductRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistProductRepository = wishlistProductRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    private WishlistProduct processWishlist(WishlistDTO wishlistDTO) {
      Wishlist wishlist = getWishlist(wishlistDTO.getClient());
      if(wishlist == null){
          wishlist = Wishlist.builder()
                  .client(getClient(wishlistDTO.getClient()))
                  .status(StatusWishlist.ATIVO)
                  .dateTimeAlter(LocalDateTime.now())
                  .build();
          wishlist = wishlistRepository.save(wishlist);
      }
      return proccessProductsWishlist(wishlistDTO.getProduct(), wishlistDTO.getQuantity(), wishlist);
    }

    private Wishlist getWishlist(Long idClient){
        try {
            Optional<Wishlist> wishlist = wishlistRepository.findByClientIdAndStatus(idClient, StatusWishlist.ATIVO);
            if(wishlist.isPresent()) {
                return wishlist.get();
            }
        }catch (BusinessRunTimeException e){
            throw new BusinessRunTimeException(Translator.toLocale(MessageError.MSG_WISHLIST_FINDFAIL.getMensagem()) + "\n Client: " + idClient, e);
        }
        return null;
    }

    private Product getProduct(Long id_product){
        try {
            Optional<Product> product = productRepository.findById(id_product);
            if(product.isPresent()){
                return product.get();
            }
        }catch (BusinessRunTimeException e){
            throw new BusinessRunTimeException(Translator.toLocale(MessageError.MSG_PRODUCT_FINDFAIL.getMensagem()) + "\n Produto: " + id_product, e);
        }
       return null;
    }

    private Client getClient(Long id_client){
        try {
            Optional<Client> client = clientRepository.findById(id_client);
            if(client.isPresent()){
                return client.get();
            }
        }catch (BusinessRunTimeException e){
            throw new BusinessRunTimeException(Translator.toLocale(MessageError.MSG_CLIENT_FINDFAIL.getMensagem()) + "\n Cliente: " + id_client, e);
        }

        return null;
    }
    private WishlistProduct getWishlistProduct(Long productId, Wishlist wishlist){
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

    private WishlistProduct proccessProductsWishlist(Long productId, Long quantity, Wishlist wishlist){
        WishlistProduct wishlistProduct = getWishlistProduct(productId, wishlist);

        Long clientId = wishlist.getClient().getId();
        if(wishlistProduct == null){
            if(getProduct(productId) == null){
                throw new BusinessRunTimeException(Translator.toLocale(MessageError.MSG_PRODUCT_NOTFOUND.getMensagem()) + " Produto: " + productId);
            }
            if(clientId == null){
                throw new BusinessRunTimeException(MessageError.MSG_CLIENT_NOTFOUND.getMensagem());
            }
            wishlistProduct =WishlistProduct.builder()
                    .productId(productId)
                    .wishId(wishlist.getId())
                    .quantity(quantity)
                    .dateTimeAlter(LocalDateTime.now())
                    .build();
        }else {
            wishlistProduct.setQuantity(quantity);
            wishlistProduct.setDateTimeAlter(LocalDateTime.now());
        }
        return wishlistProductRepository.save(wishlistProduct);
    }
    public WishlistProduct saveWishlist(WishlistDTO wishlistDTO){
         return processWishlist(wishlistDTO);
    }
    public void saveWishlist(List<WishlistDTO> listWishlistDTO){
        listWishlistDTO.forEach(wishlistDTO -> {
           saveWishlist(wishlistDTO);
        });
    }

    public List<ProductResponseData> findWishlist(Long clientId){
        Wishlist wishlist = getWishlist(clientId);
        List<WishlistProduct> wishlistProducts = wishlistProductRepository.findByWishId(wishlist.getId());
        List<ProductResponseData> products = new ArrayList<>();

        for (WishlistProduct wishlistProduct:wishlistProducts) {
            if(wishlistProduct.getQuantity() <= 0){
                continue;
            }
            Product product = getProduct(wishlistProduct.getProductId());
            float total = product.getPrice() * wishlistProduct.getQuantity();
            ProductResponseData productResponseData = ProductResponseData.builder()
                    .product(product)
                    .quantity(wishlistProduct.getQuantity())
                    .total(total)
                    .build();
            products.add(productResponseData);
        }
        return products;
    }

    public void deleteWishlist(Long clientid){
        Wishlist wishlist = getWishlist(clientid);
        if(wishlist == null){
            throw new BusinessRunTimeException(Translator.toLocale(MessageError.MSG_WISHLIST_DELETE_NOTFOUND.getMensagem()) + "\n Cliente: " + clientid);
        }
        /*
        * Aqui o carrinho de compra é inativado
        * E fica como histórico para analise de dados
        * e tomadas de decisões futuras
        * */
        wishlist.setStatus(StatusWishlist.INATIVO);
        wishlistRepository.save(wishlist);
    }

    public void deleteWishlistProduct(WishlistDeleteProductDTO wishlistDeleteProductDTO){
        Wishlist wishlist = getWishlist(wishlistDeleteProductDTO.getClient());
        if(wishlist == null){
            throw new BusinessRunTimeException(Translator.toLocale(MessageError.MSG_WISHLIST_DELETE_NOTFOUND.getMensagem()) + "\n Cliente: " + wishlistDeleteProductDTO.getClient());
        }
        WishlistProduct wishlistProduct = getWishlistProduct(wishlistDeleteProductDTO.getProduct(), wishlist);

        if(wishlistProduct == null){
            throw new BusinessRunTimeException(Translator.toLocale(MessageError.MSG_WISHLISTPRODUCT_DELETE_NOTFOUND.getMensagem())
                    + "\n Cliente: " + wishlistDeleteProductDTO.getClient()
                    + "\n Produto: " + wishlistDeleteProductDTO.getProduct()
            );
        }
        wishlistProduct.setQuantity(0l);
        /*
        * Produto fica com quantidade 0 mas é mantido
        * no carrinho de compras para análise de dados
        * e tomadas de decisões futuras
        * */
        wishlistProductRepository.save(wishlistProduct);
    }

}