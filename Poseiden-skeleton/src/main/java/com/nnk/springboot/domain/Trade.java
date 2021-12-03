package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {
    
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer tradeId;
	
	private String account;
	private String type;

	@Min(value = 0L, message="the value must be positive")
	private Double buyQuantity;

	@Min(value = 0L, message="the value must be positive")
	private Double sellQuantity;

	@Min(value = 0L, message="the value must be positive")
	private Double buyPrice;

	@Min(value = 0L, message="the value must be positive")
	private Double sellPrice;

	private String benchmark;
	private Timestamp tradeDate;
	private String security;
	private String status;
	private String trader;
	private String book;
	private String creationName;
	private Timestamp creationDate;
	private String revisionName;
	private Timestamp revisionDate;
	private String dealName;
	private String dealType;
	private String sourceListId;
	private String side;

	public Trade() {
	}

	public Trade(String account, String type) {
		this.account = account;
		this.type = type;
	}
}
