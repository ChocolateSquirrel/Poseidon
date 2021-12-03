package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "bidlist")
public class BidList {
    
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer bidListId;

	@NotBlank
	private String account;
	@NotBlank
	private String type;

	@Min(value = 0L, message="the value must be positive")
	private Double bidQuantity;

	@Min(value = 0L, message="the value must be positive")
	private Double askQuantity;

	@Min(value = 0L, message="the value must be positive")
	private Double bid;

	@Min(value = 0L, message="the value must be positive")
	private Double ask;

	private String benchmark;
	private Timestamp bidListDate;
	private String commentary;
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


	public BidList() {
	}

	public BidList(String account, String type, Double bidQuantity) {
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}
}
