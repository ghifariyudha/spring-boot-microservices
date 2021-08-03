package com.example.systemservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResp {

    private Long idUser;
    private String username;
    private String email;
    private List<String> roleList;
    private String token;
}
