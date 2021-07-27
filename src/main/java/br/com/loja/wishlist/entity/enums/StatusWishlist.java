package br.com.loja.wishlist.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum StatusWishlist {
    ATIVO('A'),
    INATIVO('I');

    private final char value;

    @JsonValue
    public char getValue() {
        return this.value;
    }

    public static StatusWishlist of(char value) {
        return Arrays.stream(StatusWishlist.values())
                .filter(tc -> value == tc.getValue())
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
