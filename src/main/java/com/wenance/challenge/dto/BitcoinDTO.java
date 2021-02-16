package com.wenance.challenge.dto;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BitcoinDTO	{
	private Long id;
	private Double lprice;
	private String curr1;
	private String curr2;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Timestamp createDate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLprice() {
		return lprice;
	}

	public void setLprice(Double lprice) {
		this.lprice = lprice;
	}

	public String getCurr1() {
		return curr1;
	}

	public void setCurr1(String curr1) {
		this.curr1 = curr1;
	}

	public String getCurr2() {
		return curr2;
	}

	public void setCurr2(String curr2) {
		this.curr2 = curr2;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
}

