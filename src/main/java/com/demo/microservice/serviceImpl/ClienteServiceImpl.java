package com.demo.microservice.serviceImpl;

import com.demo.microservice.dto.ClienteDTO;
import com.demo.microservice.dto.KpiClienteDTO;
import com.demo.microservice.model.Cliente;
import com.demo.microservice.service.ClienteService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class ClienteServiceImpl implements ClienteService {

	private static final String COLLECTION_NAME = "Clientes";
	@Autowired
	private Firestore dbfirestore;

	/**
	 * Crea un nuevo cliente con los datos suministrados.
	 *
	 * @param cliente
	 * @return String
	 */
	@Override
	public String crearCliente(Cliente cliente) {
		try {
			// validar que la edad sea >= 0
			// la fecha de nacimiento debe ser en formato YYYY-mm-dd
			Map<String, Object> data = setHasMapFields(cliente);
			ApiFuture<DocumentReference> collectionApiFuture =
					this.dbfirestore.collection(COLLECTION_NAME).add(data);
			//document(cliente.getNombre()).set(cliente);
			return collectionApiFuture.get().getId();
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Setea en un hash los datos de Cliente suministrado en el request.
	 * para luego guardar en firestore.
	 *
	 * @param cliente
	 * @return {@code Map<String, Object>}
	 */
	private Map<String, Object> setHasMapFields(Cliente cliente) {
		Map<String, Object> data = new HashMap<>();
		data.put("nombre", cliente.getNombre());
		data.put("apellido", cliente.getApellido());
		data.put("edad", cliente.getEdad());
		data.put("fechaNacimiento", cliente.getFechaNacimiento());
		return data;
	}

	/**
	 * Lista todos los clientes.
	 *
	 * @return {@code List<Cliente>}
	 */
	@Override
	public List<Cliente> listClientes() {
		try {
			Iterable<DocumentReference> documentReference =
					this.dbfirestore.collection(COLLECTION_NAME).listDocuments();
			Iterator<DocumentReference> iterator = documentReference.iterator();

			List<Cliente> clienteList = new ArrayList<>();
			Cliente cliente = null;

			while (iterator.hasNext()) {
				DocumentReference dr = iterator.next();
				ApiFuture<DocumentSnapshot> future = dr.get();
				DocumentSnapshot document = future.get();

				cliente = document.toObject(Cliente.class);
				clienteList.add(cliente);
			}
			return clienteList;
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Obtener el promedio de edad y la desviación estandar de los clientes.
	 *
	 * @return KpiClienteDTO
	 */
	@Override
	public KpiClienteDTO obtenerKpiClientes() {
		Map<String, Double> calculos = this.estadisticasEdad(this.listClientes());
		KpiClienteDTO kpiClienteDTO = new KpiClienteDTO();
		kpiClienteDTO.setPromedioEdad(calculos.get("promedio"));
		kpiClienteDTO.setDesviacionEstandar(calculos.get("desviacionEstandar"));
		return kpiClienteDTO;
	}

	private Map<String, Double> estadisticasEdad(List<Cliente> clientes) {
		OptionalDouble promedio = clientes.stream().mapToInt(Cliente::getEdad).average();
		double promedioEdad = promedio.orElse(0);

		// Desviación estándar
		double desviacionEstandar = Math.sqrt(clientes.stream().
				mapToDouble(cliente -> Math.pow(cliente.getEdad() - promedioEdad, 2)).average().orElse(0));
		// Formatear el numero para que se muestre con 2 decimales.
		double desviacionEstandarFormat = Math.floor(desviacionEstandar * 100) / 100;
		return Map.of("promedio", promedioEdad, "desviacionEstandar", desviacionEstandarFormat);
	}

	/**
	 * Listar los datos de clientes + fecha probable de muerte de cada uno
	 *
	 * @return {@code List<ClienteDTO>}
	 */
	@Override
	public List<ClienteDTO> listClientesConProbableMuerte() {
		List<ClienteDTO> clientes = new ArrayList<>();
		int edadProbableMuerte = 80; // Asumimos 80 años de vida.
		for (Cliente cliente : this.listClientes()) {
			ClienteDTO clienteDTO = new ClienteDTO();
			clienteDTO.setNombre(cliente.getNombre());
			clienteDTO.setApellido(cliente.getApellido());
			clienteDTO.setEdad(cliente.getEdad());
			clienteDTO.setFechaNacimiento(cliente.getFechaNacimiento());
			clienteDTO.setFechaProbableMuerte(LocalDate.parse(cliente.getFechaNacimiento()).
					plusYears(edadProbableMuerte).toString());
			clientes.add(clienteDTO);
		}
		return clientes;
	}
}
