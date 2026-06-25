package com.example.user.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity @Getter @Setter
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private String mobileNumber;
}
