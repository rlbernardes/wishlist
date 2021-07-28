package br.com.loja.wishlist.service;

import br.com.loja.wishlist.entity.Product;
import br.com.loja.wishlist.exception.BusinessRunTimeException;
import br.com.loja.wishlist.message.MessageError;
import br.com.loja.wishlist.repository.ProductRepository;
import br.com.loja.wishlist.util.Translator;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@NoArgsConstructor
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product getProduct(Long id_product){
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
}
