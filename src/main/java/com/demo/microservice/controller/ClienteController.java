package com.demo.microservice.controller;

import com.demo.microservice.dto.ClienteDTO;
import com.demo.microservice.dto.KpiClienteDTO;
import com.demo.microservice.model.Cliente;
import com.demo.microservice.service.ClienteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/clientes")
public class ClienteController {
	private ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@PostMapping("/creacliente")
	public String crearCliente(@RequestBody @Validated Cliente cliente) {
		return clienteService.crearCliente(cliente);
	}

	@GetMapping("/listar")
	public List<Cliente> listar() {
		return clienteService.listClientes();
	}

	@GetMapping("/kpideclientes")
	public KpiClienteDTO obtenerKpiClientes() {
		return clienteService.obtenerKpiClientes();
	}

	@GetMapping("/listclientes")
	public List<ClienteDTO> listClientes() {
		return clienteService.listClientesConProbableMuerte();
	}
}
