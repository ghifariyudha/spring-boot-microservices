package com.example.productservice.domain.entity;

import com.example.productservice.domain.constant.Database;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Database.TABLE_PRODUCT)
public class Product extends AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;
    @NotNull
    private String productName;
    @NotNull
    private Long price;
    @NotNull
    private Long stock;
    private String description;
}
