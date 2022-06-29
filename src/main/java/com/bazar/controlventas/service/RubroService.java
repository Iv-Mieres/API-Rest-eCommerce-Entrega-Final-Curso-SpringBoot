package com.bazar.controlventas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bazar.controlventas.model.Rubro;
import com.bazar.controlventas.model.exceptions.BadRequestException;
import com.bazar.controlventas.model.repository.IRubroRepository;

@Service
public class RubroService implements IRubroService {

	@Autowired
	private IRubroRepository rubroRepository;

	// Crear Rubro
	
	@Override
	public Rubro createRubro(Rubro rubro) {
		
		if (rubroRepository.existsBySector(rubro.getSector())) {
			throw new BadRequestException("El rubro con con sector " + rubro.getSector() + " ya existe. Ingrese un sector válido");
		}
			return rubroRepository.save(rubro);
	}

	// Mostrar Rubro por ID 
	
	@Override
	public Rubro findRubro(Long idRubro) {
		
			Rubro rubroBD = rubroRepository.findById(idRubro).orElse(null);
		if (!rubroRepository.existsById(idRubro) || rubroBD.isEliminado() == true ) {
			throw new BadRequestException("El rubro con id " + idRubro + " no existe. Ingrese un id válido");
		}
			return rubroBD;
	}

	// Mostrar lista de rubros
	
	@Override
	public List<Rubro> listaRubros() {
		
			List<Rubro> listaRubros = rubroRepository.findAll();
			List<Rubro> mostrarRubros = new ArrayList<>();
		
		for (Rubro rubro : listaRubros) {
			if(rubro.isEliminado() == false) { mostrarRubros.add(rubro); }
		}
			return mostrarRubros;
	}

	// Editar Rubro por ID
	
	@Override
	public void editNameRubro(Long idRubro, Rubro rubro) {
		
			Rubro rubroBD = this.findRubro(idRubro);
		if (!rubroRepository.existsById(idRubro) || rubroBD.isEliminado() == true ) {
			throw new BadRequestException("El rubro con id " + idRubro + " no existe. Ingrese un id válido");
		}
		if (rubroRepository.existsBySector(rubro.getSector())) {
			throw new BadRequestException("El rubro con sector " + rubro.getSector() + " ya existe. Ingrese un nuevo sector");
		}
			rubroBD = rubro;
			rubroBD.setIdRubro(idRubro);	
			rubroRepository.save(rubroBD);
	}

	// Eliminado logico de rubro
	
	@Override
	public void deleteRubro(Long idRubro) {
		
		if (!rubroRepository.existsById(idRubro)) {
			throw new BadRequestException("El rubro con id " + idRubro + " no existe. Ingrese un id válido");
		}
			Rubro rubroBD = this.findRubro(idRubro);
			rubroBD.setEliminado(true);
			rubroBD.setIdRubro(idRubro);	
			rubroRepository.save(rubroBD);
	}
	
	// Recuperar un rubro que fue eliminado
	
	@Override
	public void recuperarRubro(Long idRubro) {
		
		    Rubro rubroBD = rubroRepository.findById(idRubro).orElse(null);
		    
		if (!rubroRepository.existsById(idRubro)) { 
			throw new BadRequestException("El rubro con id " + idRubro + " no existe. Ingrese un id válido");	
		}
		if(rubroRepository.existsById(idRubro) && rubroBD.isEliminado() == true) {
			rubroBD.setEliminado(false); 
		}
			rubroRepository.save(rubroBD);
	}

	
	
	
	
	
	
}
