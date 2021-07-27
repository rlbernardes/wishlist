package br.com.loja.wishlist.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MessageError {
    MSG_PRODUCT_NOTFOUND(1100,"product.notfound"),
    MSG_PRODUCT_FINDFAIL(1101,"product.findfail"),
    MSG_CLIENT_NOTFOUND(1102,"client.notfound"),
    MSG_CLIENT_FINDFAIL(1103, "client.findfail"),
    MSG_WISHLISTDETAIL_FINDFAIL(1104, "wishlistdetail.findfail"),
    MSG_WISHLIST_DELETE_NOTFOUND(1105, "wishlist.delete.notfound"),
    MSG_WISHLISTPRODUCT_DELETE_NOTFOUND(1106, "wishlistProduct.delete.notfound"),
    MSG_WISHLIST_FINDFAIL(1107, "wishlist.findfail");

    private final Integer codigo;
    private final String mensagem;

}
