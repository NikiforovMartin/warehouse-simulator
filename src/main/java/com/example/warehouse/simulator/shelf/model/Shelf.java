package com.example.warehouse.simulator.shelf.model;

import com.example.warehouse.simulator.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "shelves")
public class Shelf {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "location_x")
    private int locationX;

    @Column(name = "location_y")
    private int locationY;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private long quantity;
}
