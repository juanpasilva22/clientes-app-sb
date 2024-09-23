package com.demo.microservice.dto;

import com.demo.microservice.model.Cliente;

public class ClienteDTO extends Cliente {
	private String fechaProbableMuerte;

	public String getFechaProbableMuerte() {
		return fechaProbableMuerte;
	}

	public void setFechaProbableMuerte(String fechaProbableMuerte) {
		this.fechaProbableMuerte = fechaProbableMuerte;
	}
}
