package com.yashwant.gahlot.Wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long uid;

    @NotNull
    @Size(min = 2, message = "First Name should have atelast 2 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "mobile")
    private Long mobile;

    @NotNull
    @Size(min = 2, message = "Last Name should have atelast 2 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;


    @Column(name = "haswallet")
    private boolean haswallet;

    @Column(name = "balance")
    private long balance;

    @Column(name = "gender")
    private String gender;
}
