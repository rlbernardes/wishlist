package wishlist.service;

import br.com.loja.wishlist.entity.Product;
import br.com.loja.wishlist.repository.ProductRepository;
import br.com.loja.wishlist.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import wishlist.DadosTeste;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    public void should_return_a_product(){
        DadosTeste dadosTeste = new DadosTeste();
        Optional<Product> product = Optional.ofNullable(dadosTeste.creatProduct());

        when(productRepository.findById(1l)).thenReturn(product);

        Product p = productService.getProduct(1l);

        assertThat(product.get().getId()).isSameAs(p.getId());

    }

}
