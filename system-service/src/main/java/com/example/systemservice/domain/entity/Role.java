package com.example.systemservice.domain.entity;

import com.example.systemservice.domain.constant.Database;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Database.TABLE_ROLE)
public class Role extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;
    @NotNull
    private String roleName;
}