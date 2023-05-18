package com.example.Wallet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity//@Entity: This annotation indicates that the Wallet class is an entity and represents a table in the database.
@Table(name="wallet")// This annotation specifies the name of the table in the database where the Wallet entity is mapped. In this case, the table name is "wallet".
public class Wallet {
	 @Id
	 @Column(name="phone")
	 @NotNull
	 @Size(min = 2 , message="First Name should have atelast 2 characters")//If the size constraint is violated, a validation error message will be displayed with the provided message.
	    private Integer phone;
	 @NotBlank// This annotation ensures that the balance attribute is not blank (not null and not empty). It validates that the attribute has a non-empty value.
	 @Column(name="balance")
	    private Integer balance;

	    public Wallet() {
	    }

	    public Wallet(Integer phone, Integer balance) {
	        this.phone = phone;
	        this.balance = balance;
	    }

	    public Integer getPhone() {
	        return phone;
	    }

	    public void setPhone(Integer phone) {
	        this.phone = phone;
	    }

	    public Integer getBalance() {
	        return balance;
	    }

	    public void setBalance(Integer balance) {
	        this.balance = balance;
	    }

	    public void changeBalance(Integer amount) {
	        this.balance += amount;
	    }
}
