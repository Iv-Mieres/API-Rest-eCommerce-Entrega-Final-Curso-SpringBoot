package com.bazar.controlventas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bazar.controlventas.dto.UserClienteDTO;
import com.bazar.controlventas.model.Cliente;
import com.bazar.controlventas.service.IClienteService;


@RestController
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	// Crear Cliente 

	@PostMapping("/clientes/crear")
	public ResponseEntity<Cliente> CrearCliente(@Valid @RequestBody Cliente cliente) {
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.createCliente(cliente));
	}
	
	// Login Cliente
	
	@PutMapping("/clientes/login")
	public ResponseEntity<Object> loginCliente (@RequestBody UserClienteDTO userClienteDTO){
		clienteService.loginCliente(userClienteDTO);
		return ResponseEntity.status(HttpStatus.OK).body("Login sussceful: Bienvenido " + userClienteDTO.getUserName());
	}
	
	// Mostrar Lista de Clientes

	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> listaClientes() {
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.listClientes());
	}

	// Mostrar Cliente por ID

	@GetMapping("/clientes/{id_cliente}")
	public ResponseEntity<Cliente> getCliente(@PathVariable Long id_cliente){
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.findCliente(id_cliente));
	}

	// Editar Cliente

	@PutMapping("/clientes/editar/{id_cliente}")
	public ResponseEntity<String> editCliente(@PathVariable Long id_cliente, @Valid @RequestBody Cliente cliente) {

			clienteService.editCliente(id_cliente, cliente);
			return ResponseEntity.status(HttpStatus.OK).body("El Cliente se actualizó correctamente");	
	}

	// Eliminar Cliente por ID

	@DeleteMapping("/clientes/eliminar/{id_cliente}")
	public ResponseEntity<String> deleteCliente(@PathVariable Long id_cliente) {
		
			clienteService.deleteCliente(id_cliente);
			return ResponseEntity.status(HttpStatus.OK).body("El cliente se eliminó correctamente");	
	
	}
	
	// Reactivar cuenta Cliente
	
	@PutMapping("/clientes/reactivar")
	public ResponseEntity<Object> reactivarCliente(@RequestBody UserClienteDTO userDTO){
		clienteService.reactivarCliente(userDTO);
		return ResponseEntity.status(HttpStatus.OK).body("El cliente con usuario " + userDTO.getUserName() 
																		           + " ah sido reactivado");
	}

}
