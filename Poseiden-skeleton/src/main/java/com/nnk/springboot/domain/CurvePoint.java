package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
   
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private Integer curveId;

	private Timestamp asOfDate;
	private double term;

	@Min(value = 0L, message="the value must be positive")
	private Double value;

	private Timestamp creationDate;


	public CurvePoint() {
	}

	public CurvePoint(Integer curveId, double term, Double value) {
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}
}
