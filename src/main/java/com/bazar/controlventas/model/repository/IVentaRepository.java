package com.bazar.controlventas.model.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bazar.controlventas.model.Venta;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long>{
	
	public boolean existsByFecha(LocalDate fecha);
	
}
