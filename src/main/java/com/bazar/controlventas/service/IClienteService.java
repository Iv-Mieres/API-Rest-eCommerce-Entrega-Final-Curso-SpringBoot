package com.bazar.controlventas.service;

import java.util.List;

import com.bazar.controlventas.dto.UserClienteDTO;
import com.bazar.controlventas.model.Cliente;

public interface IClienteService {

	public Cliente createCliente(Cliente cliente);
	
	public List<Cliente> listClientes();
	
	public Cliente findCliente(Long idCliente);
	
	public void editCliente(Long idCliente, Cliente cliente);
	
	public void deleteCliente(Long idCliente);

	public Cliente loginCliente(UserClienteDTO userClienteDTO);

	Cliente reactivarCliente(UserClienteDTO userDTO);	
	
}
