package wishlist.controller;

import br.com.loja.wishlist.controller.WishListController;
import br.com.loja.wishlist.dto.ProductResponseData;
import br.com.loja.wishlist.dto.WishlistDTO;
import br.com.loja.wishlist.dto.WishlistDeleteProductDTO;
import br.com.loja.wishlist.entity.WishlistProduct;
import br.com.loja.wishlist.service.WishListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wishlist.DadosTeste;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(WishListController.class)
public class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishListService service;

    @Test
    public void shouldReturnSaveProducts() throws Exception {
        DadosTeste dadosTeste = new DadosTeste();
        WishlistDTO wishlistDTO = dadosTeste.createWishlistDTO();
        WishlistProduct wishlistProduct = dadosTeste.creatWishlistProduct();
        List<ProductResponseData> responseDataList = dadosTeste.createProductsResponse();

        Mockito.when(service.saveWishlist(wishlistDTO)).thenReturn(wishlistProduct);

        Mockito.when(service.findWishlist(wishlistDTO.getClient())).thenReturn(responseDataList);

        this.mockMvc.perform(post("/wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(wishlistDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product").exists());
    }

    @Test
    public void shouldReturnAllProducts() throws Exception {
        DadosTeste dadosTeste = new DadosTeste();
        List<ProductResponseData> responseDataList = dadosTeste.createProductsResponse();

        Mockito.when(service.findWishlist(1l)).thenReturn(responseDataList);

        this.mockMvc.perform(get("/wishlist/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product").exists());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        DadosTeste dadosTeste = new DadosTeste();
        WishlistDeleteProductDTO wishlistDTO = dadosTeste.createWishlistDeleteProductDTO();
        WishlistProduct wishlistProduct = dadosTeste.creatWishlistProduct();
        List<ProductResponseData> responseDataList = dadosTeste.createProductsResponse();

        Mockito.when(service.findWishlist(wishlistDTO.getClient())).thenReturn(responseDataList);

        this.mockMvc.perform(delete("/wishlist/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(wishlistDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product").exists());
    }


    @Test
    public void shouldDeleteWishlist() throws Exception {
        this.mockMvc.perform(delete("/wishlist/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
