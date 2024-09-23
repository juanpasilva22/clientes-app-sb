package com.demo.microservice.dto;

public class KpiClienteDTO {
	private double promedioEdad;
	private double desviacionEstandar;

	public double getPromedioEdad() {
		return promedioEdad;
	}

	public void setPromedioEdad(double promedioEdad) {
		this.promedioEdad = promedioEdad;
	}

	public double getDesviacionEstandar() {
		return desviacionEstandar;
	}

	public void setDesviacionEstandar(double desviacionEstandar) {
		this.desviacionEstandar = desviacionEstandar;
	}
}
