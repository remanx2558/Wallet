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
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionid;

	@NotNull
	@Column(name = "payerphone", nullable = false)
	private Long payerphone;//sender

	@NotNull
	@Column(name = "payeephone", nullable = false)
	private Long payeephone;//receiver

	@Column(name = "amount")
	private Integer amount;

	@Column(name = "status")
	private boolean status;


	@Column(name = "mode")
	private String mode;

	@Column(name = "time")
	private String time;


	public Transaction(Long payerphone, Long payeephone, Integer amount) {
		this.payerphone = payerphone;
		this.payeephone = payeephone;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction{" +
				"withuser=" + payeephone +
				", amount=" + amount +
				", user=" + payerphone +
				", id='" + transactionid + '\'' +
				", mode='" + mode + '\'' +
				", status='" + status + '\'' +
				", time=" + time +
				'}';
	}
}
