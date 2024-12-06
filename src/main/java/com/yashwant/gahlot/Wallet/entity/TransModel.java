package com.yashwant.gahlot.Wallet.entity;

// JPA Annotations
import jakarta.persistence.*;
// Validation Annotations
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name="transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionid;

	@NotNull
	@Column(name = "payerphone", nullable = false)
	private Integer payerphone;

	@NotNull
	@Column(name = "payeephone", nullable = false)
	private Integer payeephone;

	@Column(name = "amount")
	private Integer amount;


	public TransModel( Integer payerphone, Integer payeephone, Integer amount) {
		this.payerphone = payerphone;
		this.payeephone = payeephone;
		this.amount = amount;
	}
}
