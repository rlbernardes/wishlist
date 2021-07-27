package br.com.loja.wishlist.controller;

import br.com.loja.wishlist.dto.WishlistDTO;
import br.com.loja.wishlist.dto.WishlistDeleteProductDTO;
import br.com.loja.wishlist.dto.ProductResponseData;
import br.com.loja.wishlist.service.WishListService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("wishlist")
@Log4j2
public class WishListController {

    private final WishListService service;

    @Autowired
    public WishListController(WishListService service) {
        this.service = service;
    }

    @ApiOperation(value = "Save Wishlist.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Wishlist salva  com sucesso.")})
    @PostMapping
    public List<ProductResponseData> save(@Valid @RequestBody WishlistDTO wishlistDTO) {
        service.saveWishlist(wishlistDTO);
        return service.findWishlist(wishlistDTO.getClient());
    }

    @ApiOperation(value = "Get list of Wishlist.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem realizada com sucesso.")})
    @GetMapping("/{clientId}")
    public List<ProductResponseData> get(@PathVariable Long clientId) {
        return service.findWishlist(clientId);
    }

    @ApiOperation(value = "Delete a product of Wishlist.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Produto removido do carrinho de compras.")})
    @DeleteMapping("/product")
    public List<ProductResponseData> deleteProduct(@Valid @RequestBody WishlistDeleteProductDTO wishlistDeleteProductDTO) {
        service.deleteWishlistProduct(wishlistDeleteProductDTO);
        return service.findWishlist(wishlistDeleteProductDTO.getClient());
    }

    @ApiOperation(value = "Delete a Wishlist.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Carrinho de compras removido.")})
    @DeleteMapping("/{clientId}")
    public ResponseEntity delete(@PathVariable Long clientId) {
        service.deleteWishlist(clientId);
        return ResponseEntity.ok().build();
    }

}
