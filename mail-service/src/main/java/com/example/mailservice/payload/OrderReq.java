package com.example.mailservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReq {

    private Long idOrder;
    private String emailUser;
    private String productName;
    private Long qty;
    private Long total;
}
