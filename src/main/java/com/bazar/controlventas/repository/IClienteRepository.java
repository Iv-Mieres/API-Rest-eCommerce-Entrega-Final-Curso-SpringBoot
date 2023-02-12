package com.bazar.controlventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bazar.controlventas.model.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{
	
	public boolean existsByDni(String dni);
	
	public boolean existsByUserName(String UserName);
	
	public Cliente findByDni(String dni);
	
	public Cliente findByUserName(String userName);
	
}
