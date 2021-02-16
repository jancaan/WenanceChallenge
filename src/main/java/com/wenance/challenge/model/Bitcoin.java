package com.wenance.challenge.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "BITCOIN")
public class Bitcoin implements Serializable {

	private static final long serialVersionUID = -1539576592461493337L;

	@Id
	@Column(name="id", nullable = false)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdSeq")
	//@SequenceGenerator(name = "IdSeq", sequenceName = "SEQ_BITCOIN", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "valor", nullable = false)
	private Double valor;

	@Column(name = "moneda1", nullable = false)
	private String moneda1;

	@Column(name = "moneda2", nullable = false)
	private String moneda2;

	@Column(name = "fecha_creacion", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Timestamp fechaCreacion;
	
	@PrePersist
	protected void onCreate() {
	    fechaCreacion = new Timestamp(new Date().getTime());
	}
	
	public Bitcoin() {
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getMoneda1() {
		return moneda1;
	}

	public void setMoneda1(String moneda1) {
		this.moneda1 = moneda1;
	}

	public String getMoneda2() {
		return moneda2;
	}

	public void setMoneda2(String moneda2) {
		this.moneda2 = moneda2;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}
