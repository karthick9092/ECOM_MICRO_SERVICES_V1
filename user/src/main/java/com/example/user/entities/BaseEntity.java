package com.example.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

//  BaseEntity class is a common superclass for all entities in the application. It can contain common fields and methods that are shared across multiple entities. By using @MappedSuperclass, we indicate that this class is not an entity itself but provides mapping information for its subclasses.

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

}
