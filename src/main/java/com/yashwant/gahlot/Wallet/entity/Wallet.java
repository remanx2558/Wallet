package com.yashwant.gahlot.Wallet.entity;

// JPA Annotations
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Validation Annotations
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="wallet")// This annotation specifies the name of the table in the database where the Wallet entity is mapped. In this case, the table name is "wallet".
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

	 @Id
	 @Column(name="phone")
	 @NotNull
	 @Size(min = 2 , message="First Name should have atelast 2 characters")//If the size constraint is violated, a validation error message will be displayed with the provided message.
	    private Integer phone;



	 @NotBlank// This annotation ensures that the balance attribute is not blank (not null and not empty). It validates that the attribute has a non-empty value.
	 @Column(name="balance")
	    private Integer balance;

}

/*
1. @Entity: This annotation indicates that the Wallet class is an entity and represents a table in the database.
2. @NoArgsConstructor: No argument in constructor
3. @AllArgsConstructor: All arguments in constructor
* */
