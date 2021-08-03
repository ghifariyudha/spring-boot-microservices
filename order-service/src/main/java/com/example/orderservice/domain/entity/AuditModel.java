package com.example.orderservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AuditModel {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createdDate;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date modifiedDate;
    private Boolean isActive;

    @PrePersist
    public void prePersist() {
        createdDate = modifiedDate = new Date();
        isActive = isActive == null ? true : isActive;
    }
    @PreUpdate
    public void preUpdate() {
        modifiedDate = new Date();
    }
}