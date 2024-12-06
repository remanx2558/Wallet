package com.yashwant.gahlot.Wallet.entity;

// JPA Annotations
import jakarta.persistence.*;

// Validation Annotations
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="wallet",
		uniqueConstraints = {@UniqueConstraint(columnNames = "phone")}
)// This annotation specifies the name of the table in the database where the Wallet entity is mapped. In this case, the table name is "wallet".
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "phone", nullable = false, unique = true)
	@NotNull(message = "Phone number cannot be null")
	@Digits(integer = 10, fraction = 0, message = "Phone number must be a valid 10-digit number")
	private Long phone;



	 @NotBlank// This annotation ensures that the balance attribute is not blank (not null and not empty). It validates that the attribute has a non-empty value.
	 @Column(name="balance", nullable = false)
	    private Integer balance;


	@Column(name = "haswallet")
	private boolean hasWallet;


	@Column(name = "isCustomer")
	private boolean isCustomer;

	public Wallet(Long phone) {
		this.phone = phone;
		this.balance = 0;           // Default balance
		this.hasWallet = false;     // Default hasWallet status
		this.isCustomer = false;    // Default isCustomer status
	}

}

/*
1. @Entity: This annotation indicates that the Wallet class is an entity and represents a table in the database.
2. @NoArgsConstructor: No argument in constructor
3. @AllArgsConstructor: All arguments in constructor
4. Custom Constructor: Initializes the wallet with only the phone number, setting default values for other fields.
5. 	Id:Surrogate Primary Key This field serves as the primary key for database operations.
	 It's auto-generated and not exposed as a business identifier.
6. phone: Business Identifier Represents the unique phone number associated with the wallet.
     Must be a valid 10-digit number.
* */
