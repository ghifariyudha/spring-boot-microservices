package com.example.orderservice.domain.entity;

import com.example.orderservice.domain.constant.Database;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Database.TABLE_ORDER)
public class Order extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;
    @NotNull
    private Long idUser;
    private String emailUser;
    @NotNull
    private Long idProduct;
    private String productName;
    @NotNull
    private Long qty;
    private Long total;
}
