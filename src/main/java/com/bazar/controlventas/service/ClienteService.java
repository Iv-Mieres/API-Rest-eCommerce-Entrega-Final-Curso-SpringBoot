package com.bazar.controlventas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazar.controlventas.dto.UserClienteDTO;
import com.bazar.controlventas.exceptions.BadRequestException;
import com.bazar.controlventas.model.Cliente;
import com.bazar.controlventas.repository.IClienteRepository;

@Service
public class ClienteService implements IClienteService {

	@Autowired
	private IClienteRepository clienteRepository;

	// Crear Clientes

	@Override
	public Cliente createCliente(Cliente cliente) {
		
		if (clienteRepository.existsByDni(cliente.getDni())) {
			throw new BadRequestException("El cliente con dni " + cliente.getDni() + " ya existe. Ingrese un nuevo dni"
							+ " o reactive su cuenta");
		}
		if (clienteRepository.existsByUserName(cliente.getUserName())) {
			throw new BadRequestException(
					"El usuario " + cliente.getUserName() + " ya existe. Ingrese un nuevo nombre de usuario");
		}
			return clienteRepository.save(cliente);
	}
	
	// Reactivar cuenta de cliente que fue eliminada
	
	@Override
	public Cliente reactivarCliente(UserClienteDTO userDTO) {
			Cliente clienteBD = clienteRepository.findByUserName(userDTO.getUserName());
		
		if(Objects.isNull(clienteBD)) {
			throw new BadRequestException(
					"No puede ingresar: Revise que el nombre de usuario y contraseña sean correctos");
		}	
		if(clienteBD.getPassword().equals(userDTO.getPassword()) && clienteBD.isEliminado() == true){
			clienteBD.setEliminado(false);
		}
		else {
			throw new BadRequestException(
					"No puede ingresar: contraseña incorrecta");
		}
			return clienteRepository.save(clienteBD);
	}

	// Login Cliente
	
	@Override
	public Cliente loginCliente(UserClienteDTO userClienteDTO) {
				
			Cliente clienteBD = clienteRepository.findByUserName(userClienteDTO.getUserName());
					
		if(Objects.isNull(clienteBD)) {
			throw new BadRequestException(
					"No puede ingresar: Revise que el nombre de usuario y contraseña sean correctos");
		}
		if(clienteBD.isEliminado() == true) {
			throw new BadRequestException(
					"No puede ingresar: El cliente ah sido eliminado: Debe reactivar la cuenta para poder ingresar: "
																+ "/clientes/reactivar/");
		}			
		if (!clienteBD.getPassword().equals(userClienteDTO.getPassword())) {
			throw new BadRequestException("No puede ingresar: Contraseña incorrecta");
		}				
			clienteBD.setConectado(true);
			return clienteRepository.save(clienteBD);
	}

	// Mostrar Lista de Clientes

	@Override
	public List<Cliente> listClientes() {
		
			List<Cliente> listaClientes = clienteRepository.findAll();
			List<Cliente> mostrarClientes = new ArrayList<>();
		
		for (Cliente cliente : listaClientes) {
			if(cliente.isEliminado() == false) { mostrarClientes.add(cliente); }
		}	
			return mostrarClientes;
	}

	// Mostrar Cliente por ID

	@Override
	public Cliente findCliente(Long idCliente) {
		
			Cliente clienteBD = clienteRepository.findById(idCliente).orElse(null);	
			
		if (!clienteRepository.existsById(idCliente) || clienteBD.isEliminado() == true) {
			throw new BadRequestException("El cliente con id " + idCliente + " no existe. Ingrese un id válido");
		}
		return clienteBD;		
	}

	// Editar Cliente

	@Override
	public void editCliente(Long idCliente, Cliente cliente) {
	
			Cliente clienteBD = this.findCliente(idCliente);
			Cliente editarCliente = cliente;
			
		if (!clienteRepository.existsById(idCliente)) {
			throw new BadRequestException("El id " + idCliente + " no existe. Ingrese un id válido");
		}
		if (clienteBD.isConectado() == false) {
			throw new BadRequestException("Debe inciar sesion para modificar los datos");		
		} 		
			editarCliente.setIdCliente(idCliente);
			editarCliente.setConectado(true);
		
		if(clienteBD.getDni().equals(editarCliente.getDni()) || !clienteRepository.existsByDni(cliente.getDni())) {
			editarCliente.setDni(cliente.getDni());
		}
		else {
			throw new BadRequestException("El dni " + cliente.getDni() + " Ya existe. Ingrese un nuevo dni");
		}	
		if(clienteBD.getUserName().equals(editarCliente.getUserName()) || !clienteRepository.existsByUserName(cliente.getUserName())) {
			editarCliente.setUserName(cliente.getUserName());
		}
		else {
			throw new BadRequestException("El user name " + cliente.getUserName() + " Ya existe. Ingrese un nuevo user name");
		}
	
			clienteRepository.save(editarCliente);
	}

	// Eliminado logico de cliente

	@Override
	public void deleteCliente(Long idCliente) {
		
		if (!clienteRepository.existsById(idCliente)) {
			throw new BadRequestException("El id " + idCliente + " no existe. Ingrese un id válido");
		}
			Cliente clienteBD = this.findCliente(idCliente);
			clienteBD.setEliminado(true);
			clienteBD.setIdCliente(idCliente);

			clienteRepository.save(clienteBD);
	}

}
