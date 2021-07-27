package br.com.loja.wishlist.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "tb_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "descrption")
    private String descrption;

    @Column(name = "price")
    private Float price;
}
