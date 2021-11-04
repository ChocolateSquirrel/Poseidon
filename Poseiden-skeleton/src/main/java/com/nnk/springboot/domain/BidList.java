package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "bidlist")
public class BidList {
    
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer bidListId;

	@NotBlank
	private String account;
	@NotBlank
	private String type;
	private Double bidQuantity;
	private Double askQuantity;
	private Double bid;
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
