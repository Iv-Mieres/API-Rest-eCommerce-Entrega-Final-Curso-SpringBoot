package com.bazar.controlventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bazar.controlventas.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long>{
	
	public boolean existsByNombre(String nombre);
	

}
