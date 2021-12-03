package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {
    
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	
	private String moodysRating;
	private String sandPRating;
	private String fitchRating;

	@Min(value = 0L, message="the value must be positive")
	private Integer orderNumber;

	public Rating() {
	}

	public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}
}
