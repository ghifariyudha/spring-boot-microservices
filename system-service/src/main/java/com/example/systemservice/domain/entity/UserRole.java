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
@Table(name = Database.TABLE_USER_ROLE)
public class UserRole extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUserRole;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Database.COLUMN_USER)
    @NotNull
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Database.COLUMN_ROLE)
    @NotNull
    private Role role;
}