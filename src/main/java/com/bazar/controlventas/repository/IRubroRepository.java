package com.bazar.controlventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.bazar.controlventas.model.Rubro;

@Service
public interface IRubroRepository extends JpaRepository<Rubro, Long> {

	public boolean existsBySector(String sector);
}
