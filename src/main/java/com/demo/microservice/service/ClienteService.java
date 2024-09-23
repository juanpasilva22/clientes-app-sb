package com.demo.microservice.service;

import com.demo.microservice.dto.ClienteDTO;
import com.demo.microservice.dto.KpiClienteDTO;
import com.demo.microservice.model.Cliente;

import java.util.List;
import java.util.Map;

public interface ClienteService {
	String crearCliente(Cliente cliente);

	List<Cliente> listClientes();

	KpiClienteDTO obtenerKpiClientes();

	List<ClienteDTO> listClientesConProbableMuerte();
}
