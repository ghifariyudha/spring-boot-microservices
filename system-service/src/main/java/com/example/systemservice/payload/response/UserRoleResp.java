package com.example.systemservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResp {

    private Long idUserRole;
    private Long idUser;
    private String username;
    private Long idRole;
    private String roleName;
}
