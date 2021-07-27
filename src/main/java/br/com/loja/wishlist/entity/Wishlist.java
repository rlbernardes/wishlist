package br.com.loja.wishlist.entity;

import br.com.loja.wishlist.entity.enums.StatusWishlist;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "tb_wishlist")
public class Wishlist {
    private static final long serialVersionUID=202005220557L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusWishlist status;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    @Column(name = "dtalter")
    private LocalDateTime dateTimeAlter;
}
