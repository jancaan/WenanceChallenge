package com.wenance.challenge.dto;

public class ConsultaPromedioYDiferenciaPorcentualDTO	{
	
	private Double valorPromedio;
	private Double valorMaximo;
	private Double diferenciaPorcentualEntreValorPromedioYMaximo;
	
	public ConsultaPromedioYDiferenciaPorcentualDTO() {
	}
	
	public ConsultaPromedioYDiferenciaPorcentualDTO(Double valorPromedio, Double valorMaximo,
			Double diferenciaPorcentualEntreValorPromedioYMaximo) {
		super();
		this.valorPromedio = valorPromedio;
		this.valorMaximo = valorMaximo;
		this.diferenciaPorcentualEntreValorPromedioYMaximo = diferenciaPorcentualEntreValorPromedioYMaximo;
	}



	public Double getValorMaximo() {
		return valorMaximo;
	}
	
	public void setValorMaximo(Double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public Double getValorPromedio() {
		return valorPromedio;
	}

	public void setValorPromedio(Double valorPromedio) {
		this.valorPromedio = valorPromedio;
	}

	public Double getDiferenciaPorcentualEntreValorPromedioYMaximo() {
		return diferenciaPorcentualEntreValorPromedioYMaximo;
	}

	public void setDiferenciaPorcentualEntreValorPromedioYMaximo(Double diferenciaPorcentualEntreValorPromedioYMaximo) {
		this.diferenciaPorcentualEntreValorPromedioYMaximo = diferenciaPorcentualEntreValorPromedioYMaximo;
	}

}

