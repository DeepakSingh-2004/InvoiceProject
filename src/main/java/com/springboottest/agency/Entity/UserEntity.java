package com.springboottest.agency.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // ✅ this ensures table name = users
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // e.g. ROLE_ADMIN or ROLE_USER
}
