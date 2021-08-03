package com.example.orderservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReq {

    private Long idProduct;
    private String productName;
    private Long price;
    private Long stock;
    private String description;
}
