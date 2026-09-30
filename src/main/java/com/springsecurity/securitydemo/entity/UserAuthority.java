package com.springsecurity.securitydemo.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_authorities")
public class UserAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    /*
    user_authorities.user_id

             ↓
    users.id

        users
    ────────────────────
    id = 2
    username = admin
    role = ADMIN
            │
            │ 1 → many
            ↓
    user_authorities
    ────────────────────────────
    user_id = 2 | PAYMENT_READ
    user_id = 2 | PAYMENT_CREATE
    user_id = 2 | PAYMENT_DELETE
     */

    @Column(nullable = false)
    private String authority;

    public UserAuthority() {
    }

    public UserAuthority(AppUser user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}